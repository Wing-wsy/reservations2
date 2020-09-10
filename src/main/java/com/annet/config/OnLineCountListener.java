package com.annet.config;

import com.annet.entity.TbBespokeres;
import com.annet.entity.TbBespokeresLsb;
import com.annet.entity.TbUser;
import com.annet.entity.operating.HgBespoke;
import com.annet.entity.operating.LogEntity;
import com.annet.mapper.TbBespokeresLsbMapper;
import com.annet.mapper.TbBespokeresMapper;

import com.annet.service.TbBespokeService;
import com.annet.service.TbBespokeresLsbService;
import com.annet.service.TbBespokeresService;
import com.annet.service.impl.TbBespokeServiceImpl;
import com.annet.service.impl.TbBespokeresServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session创建和失效操作
 *
 * @author ：xy
 * @date ：Created in 2019/7/8 11:13
 * @description： session管理
 * @modified By：
 * @version: $
 */
@WebListener
@Slf4j
public class OnLineCountListener implements HttpSessionListener {

    private AtomicInteger onLineCount = new AtomicInteger(0);

    private TbBespokeresLsbMapper tbBespokeresLsbMapper;

    private TbBespokeresServiceImpl tbBespokeresService;

    private  TbBespokeresMapper tbBespokeresMapper;

    // 保存当前登录的所有用户
    public static Map<String,HttpSession> loginUserList = new HashMap<>();

    // 用这个作为session中的key
    public static String SESSION_LOGIN_NAME = "beenLoginUser";

    @Autowired
    public OnLineCountListener(TbBespokeresLsbMapper tbBespokeresLsbMapper,TbBespokeresServiceImpl tbBespokeresService,TbBespokeresMapper tbBespokeresMapper) {
        this.tbBespokeresLsbMapper = tbBespokeresLsbMapper;
        this.tbBespokeresService = tbBespokeresService;
        this.tbBespokeresMapper = tbBespokeresMapper;
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("创建Session：" + event.getSession().getId() + ",时间:" + df.format(new Date()));
        //event.getSession().setMaxInactiveInterval(5);
        System.out.println("Session存在时间：" + event.getSession().getMaxInactiveInterval());
        event.getSession().getServletContext().setAttribute("onLineCount", onLineCount.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println(event.getSession().getMaxInactiveInterval());
        TbUser tbUser = (TbUser)event.getSession().getAttribute("user");

        if(tbUser != null){
            loginUserList.remove(tbUser.getUserID());
            //session失效写入日志
            log.info("账号："+tbUser.getUserName()+";sessionID:"+event.getSession().getId()+";session创建时间:"+event.getSession().getCreationTime());
            List<TbBespokeresLsb> tbBespokeresLsbList = tbBespokeresLsbMapper.selectUserID(tbUser.getUserID(), 0);
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("销毁Session：" + event.getSession().getId() + ",时间:" + df.format(new Date()));
            //调用清除号源
            tbBespokeresService.rollback(tbBespokeresLsbList);

            //记录号源日志
            if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
                for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                    LogEntity logEntity1 = new LogEntity();
                    logEntity1.setResID(String.valueOf(tbBespokeresLsb.getResID()));
                    logEntity1.setOperaction("过期销毁Session获取临时表释放号源");
                    logEntity1.setHospitalType(tbBespokeresLsb.getHospitalType());
                    logEntity1.setPatientNum(String.valueOf(0 - tbBespokeresLsb.getPatientNum()));
                    logEntity1.setDate(new Date());
                    //记录号源日志
                    tbBespokeresMapper.insertTbBespokeresLog(logEntity1);
                }
            }

        }
        event.getSession().getServletContext().setAttribute("onLineCount", onLineCount.decrementAndGet());
    }
}
