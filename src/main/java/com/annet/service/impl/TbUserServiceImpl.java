package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.config.OnLineCountListener;
import com.annet.entity.TbUser;
import com.annet.mapper.TbUserMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbUserService;
import com.annet.utils.StrUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 * 用户表
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
@Slf4j
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    private final TbUserMapper tbUserMapper;

    private final HttpSession httpSession;

    /**
     * 登录
     * @param UserID 用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    @Override
    public R<TbUser> login(String UserID, String Password) throws MyException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        TbUser tbUser1 = tbUserMapper.selectUserID(UserID);
        if(tbUser1==null){
            //throw new MyException("没有找到该用户，请确定账号是否正确");
            return new R(1,"没有找到该用户，请确定账号是否正确");
        }
        //判断账号是否被禁用
        if(tbUser1.getStatus()!=1){
            //throw new MyException("该账号已被禁用，请找管理员解禁");
            return new R(1,"该账号已被禁用，请找管理员解禁");
        }

        String passwordMd5 = DigestUtils.md5Hex(Password);
        TbUser tbUser2 = tbUserMapper.login(UserID,passwordMd5).get(0);
        if(tbUser2==null){
            //throw new MyException("密码错误");
            return new R(1,"密码错误");
        }

        System.out.println(tbUser2.getUserID()+":"+request.getSession().getId());
        Map<String,HttpSession> loginMap = OnLineCountListener.loginUserList;
        if(loginMap==null){
            loginMap = new HashMap<String,HttpSession>();
        }
        for(String key:loginMap.keySet()) {
            if (tbUser2.getUserID().equals(key)) {
                System.out.println(request.getSession().getId());
                System.out.println(loginMap.get(key).getId());
                HttpSession session = loginMap.get(key);
                if(session!=null){
                    if(request.getSession().getId().equals(session.getId())) {
                        System.out.println(tbUser2.getUserName()+"在同一地点多次登录！");
                    }else{
                        System.out.println(tbUser2.getUserName()+"在不同电脑重复登陆，挤下去！");
                        try{
                            if(session!=null && session.getAttribute("user")!=null){
                                session.removeAttribute("user");
                                log.info("在不同电脑重复登陆，挤下去！"+tbUser2.getUserName()+";sessionID:"+session.getId());
                            }
                        }catch(Exception e){
                            OnLineCountListener.loginUserList.remove(tbUser2.getUserID());
                        }
                    }
                }
            }
        }
        //当前超时时间
        int time=request.getSession().getMaxInactiveInterval();
        System.out.println("session过期时间：" + time);
        loginMap.put(tbUser2.getUserID(),request.getSession());
        //将新登陆的账号写出ID
        log.info("新账号登陆："+tbUser2.getUserName()+";sessionID:"+request.getSession().getId()+";session存在时间:"+time);
        request.getSession().setAttribute("loginMap", loginMap);
        request.getSession().setAttribute("user",tbUser2);
        TbUser tbUser3 = (TbUser)request.getSession().getAttribute("user");
        System.out.println(tbUser3);
        return new R(tbUser2);
    }

    /**
     * 伪登录
     * @param UserID 用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    @Override
    public R<TbUser> pseudoLogin(String UserID, String Password) throws MyException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        TbUser tbUser3 = new TbUser();
        tbUser3.setUserID(UserID);
        tbUser3.setPassword(Password);

        //将新登陆的账号写出ID
        request.getSession().setAttribute("user",tbUser3);
        TbUser tbUser4 = (TbUser)request.getSession().getAttribute("user");
        System.out.println(tbUser4);
        return new R(tbUser4);
    }


    /**
     * 用户列表
     * @param hospitalName 院区名称
     * @return 用户列表
     */
    @Override
    public List<TbUser> FinAll(String hospitalName,String deptName) {
        List<TbUser> users = tbUserMapper.selectFinAll(hospitalName);
        // 按登录人员筛选人员列表
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return users;
        }else {
            List<TbUser> tbUsers = users.stream().filter(tbUser -> (tbUser.getDeptName().equals(deptName))).collect(Collectors.toList());
            return tbUsers;
        }
    }

    /**
     * 添加用户
     * @param tbUser 用户
     * @return 修改后用户信息
     * @throws MyException 异常信息
     */
    @Override
    public R<String> addTbUser(TbUser tbUser) throws MyException {
        TbUser tbUser1 = tbUserMapper.selectUserID(tbUser.getUserID());
        if(tbUser1!=null){
            //throw new MyException("用户账号重复");
            return new R(1,"密码错误");
        }
        if(tbUser.getPassword()==null || tbUser.getPassword().equals("")){
            //throw new MyException("密码不能为空");
            return new R(1,"密码不能为空");
        }
        if(tbUser.getDeptName()==null || tbUser.getDeptName().equals("")){
            //throw new MyException("请选择科室");
            return new R(1,"请选择科室");
        }
        if(tbUser.getUserCode()==null || tbUser.getUserCode().equals("")){
            //throw new MyException("用户工号不能为空");
            return new R(1,"用户工号不能为空");
        }
        if(StrUtils.isNullOrEmpty(tbUser.getHospitalName())){
            //throw new MyException("请选择院区");
            return new R(1,"请选择院区");
        }
        //密码MD5加密
        String MD5Password = DigestUtils.md5Hex(tbUser.getPassword());
        tbUser.setPassword(MD5Password);
        tbUser.setStatus(1);
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbUser.setAddDate(new Date());
        Integer result = tbUserMapper.insertTbUser(tbUser);
        if(result < 1){
            //throw new MyException("添加用户失败");
            return new R(1,"添加用户失败");
        }
        return new R(tbUser.getUserID());
    }

    /**
     * 用户修改
     * @param tbUser 用户
     * @return 修改后用户对象
     */
    @Override
    public R<String> modifyTbUser(TbUser tbUser) throws MyException {
        if(StrUtils.isNullOrEmpty(tbUser.getHospitalName())){
            //throw new MyException("院区参数不能为空");
            return new R(1,"院区参数不能为空");
        }
        if(tbUser.getPassword()!=null && !"".equals(tbUser.getPassword())){
            String passwordMd5 = DigestUtils.md5Hex(tbUser.getPassword());
            tbUser.setPassword(passwordMd5);
        }
        Integer result = tbUserMapper.updateTbUser(tbUser);
        if(result < 1){
            //throw new MyException("修改用户失败");
            return new R(1,"修改用户失败");
        }
        return new R(tbUser.getUserID());
    }

    /**
     * 通过ID获取用户
     * @param id 用户名
     * @return 用户信息
     */
    @Override
    public List<TbUser> FinId(String id) {
        return tbUserMapper.selectFinId(id);
    }
}
