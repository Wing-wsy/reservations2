package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.*;
import com.annet.mapper.*;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbDepttableService;
import com.annet.utils.PinyinUtils;
import com.annet.xmlEntity.RequestDeptInfo;
import com.annet.xmlEntity.RequestDeptInfoList;
import com.annet.yml.WebService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.thoughtworks.xstream.XStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 *  服务实现类
 * </p>
 * 科室表
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
@Slf4j
public class TbDepttableServiceImpl extends ServiceImpl<TbDepttableMapper, TbDepttable> implements TbDepttableService {
    /**
     * webservice地址
     */
    private final WebService webService;

    private final TbDepttableMapper tbDepttableMapper;

    /**
     * 根据科室名称去重
     * 科室列表(修改去重)
     * @return 科室集合
     */
    @Override
    public R<List<TbDepttable>> FinAll(String deptName,String hospitalName,String histype,Integer isReserve) {
        List<TbDepttable> tbDepttables = tbDepttableMapper.selectFinAll(hospitalName, histype, isReserve);
        if(tbDepttables.isEmpty()){
            //throw new MyException("科室暂时不存在");
            return new R(1,"科室暂时不存在");
        }
        // 根据数据库DeptName科室去重
        List<TbDepttable> depttables = tbDepttables.stream().collect(
                Collectors.collectingAndThen( Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TbDepttable::getDeptName))),
                        ArrayList::new)).stream().collect(Collectors.toList());
        //将deptName为全部的排在第一列便于前端展示
        for(int i = 0;i < depttables.size();i++){
            String dName = depttables.get(i).getDeptName();
            if(dName.equals("全部")){
                TbDepttable tb1 = depttables.get(i);
                depttables.set(i,depttables.get(0));
                depttables.set(0,tb1);
                break;
            }
        }
//        if(deptName.contains(ConstantClass.ADMINISTRATOR)){
//            return new R(depttables);
//        }else {
//            List<TbDepttable> tbDepttableList = depttables.stream().filter(p -> p.getDeptName().equals(deptName))
//                    .sorted(Comparator.comparing(TbDepttable::getDeptID)).collect(Collectors.toList());
//            return new R(tbDepttableList);
//        }
        return new R(depttables);
    }

    /**
     * 添加科室
     *
     * @param tbDepttable 科室对象
     * @return 科室集合
     */
    @Override
    public R<Integer> addTbDepttable(TbDepttable tbDepttable) {
        List<TbDepttable> deptCodeList = tbDepttableMapper.selectDeptCode(tbDepttable.getDeptCode(),0);
        if(deptCodeList!=null && deptCodeList.size()>0){
            //throw new MyException("添加科室失败,科室代码重复");
            return new R(1,"添加科室失败,科室代码重复");
        }
        List<TbDepttable> deptNameList = tbDepttableMapper.selectDeptName(tbDepttable.getDeptName(),0);
        if(deptNameList!=null && deptNameList.size()>0){
            //throw new MyException("添加科室失败,科室名称重复");
            return new R(1,"添加科室失败,科室名称重复");
        }
        Integer result = tbDepttableMapper.addTbDepttable(tbDepttable);
        if(result < 1){
            //throw new MyException("添加科室失败");
            return new R(1,"添加科室失败");
        }
        return new R(tbDepttable.getDeptID());
    }

    /**
     * 修改科室
     * @param tbDepttable
     * @return
     */
    @Override
    public R<Integer> modifyTbDepttable(TbDepttable tbDepttable) {
        List<TbDepttable> deptCodeList = tbDepttableMapper.selectDeptCode(tbDepttable.getDeptCode(),tbDepttable.getDeptID());
        if(deptCodeList!=null && deptCodeList.size()>0){
            //throw new MyException("修改科室失败,科室代码重复");
            return new R(1,"修改科室失败,科室代码重复");
        }
        List<TbDepttable> deptNameList = tbDepttableMapper.selectDeptName(tbDepttable.getDeptName(),tbDepttable.getDeptID());
        if(deptNameList!=null && deptNameList.size()>0){
            //throw new MyException("修改科室失败,科室名称重复");
            return new R(1,"修改科室失败,科室名称重复");
        }
        Integer result = tbDepttableMapper.updateTbDepttable(tbDepttable);
        if(result < 1){
            //throw new MyException("修改科室失败");
            return new R(1,"修改科室失败");
        }
        return new R(tbDepttable.getDeptID());
    }

    /**
     * @return 筛选科室
     */
    @Override
    public List<TbDepttable> filterDepttable() {
        List<TbDepttable> tbDepttables = tbDepttableMapper.selectFinAll(null,null, null);
        ArrayList<TbDepttable> depttableArrayList = tbDepttables.stream().collect(
                collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(TbDepttable::getDeptName))),
                        ArrayList::new));
        return depttableArrayList;
    }

    @Override
    public R<Integer> delete(Integer deptId) {
        if(null==deptId){
            //throw new MyException("传参的科室编号id不能为空");
            return new R(1,"传参的科室编号id不能为空");
        }
        Integer result = tbDepttableMapper.deleteTbDeptTableByDeptId(deptId);
        return new R(result);
    }

    /**
     *同步科室信息
     */
    @Override
    public R<List<RequestDeptInfo>> synTbDepttable(String hospitalName) {
        System.out.println(webService.getUrl());
        String sParam = "";
        //  创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(webService.getUrl());
        Object[] objects;
        List<RequestDeptInfo> requestDeptInfo = new ArrayList<>();

        try {
            //  invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("HISInterface", "GetRequestDeptInfo", sParam );
            System.out.println("返回数据:" + objects[0]);
            String bojectString = objects[0].toString();
            String returns = bojectString.substring(bojectString.indexOf("<return>"), bojectString.indexOf("</return>")+9);
            log.info(returns);
            if (returns.contains("0")) {
                log.info("his数据不存在");
                return null;
            }
            bojectString = bojectString.substring(bojectString.indexOf("<RequestDeptList>"), bojectString.indexOf("</RequestDeptList>") + 18);
            System.out.println(bojectString);
            XStream xstream = new XStream();
            // 忽略为空的（如果有空不写会报错）
            xstream.ignoreUnknownElements();
            xstream.autodetectAnnotations(true);
            xstream.processAnnotations(RequestDeptInfoList.class);
            RequestDeptInfoList requestDeptInfoList = (RequestDeptInfoList) xstream.fromXML(bojectString);

            requestDeptInfo.addAll(requestDeptInfoList.getRequestDeptInfoList());
            if (requestDeptInfo.isEmpty()) {
                return new R(requestDeptInfo);
            }
            for (RequestDeptInfo deptInfo : requestDeptInfo) {
                TbDepttable tbDepttable = new TbDepttable();
                int histype = Integer.parseInt(deptInfo.getHistype());
                String deptCode = deptInfo.getRequestDeptCode();
                String deptName = deptInfo.getRequestDeptName();
                String deptPYM = deptInfo.getRequestDeptPYM();
                if((deptPYM == null) || (deptPYM == "")){
                    //生成科室拼音码
                    System.out.println(PinyinUtils.getPinYinHeadChar(deptName));
                    deptPYM = PinyinUtils.getPinYinHeadChar(deptName);

                }
                tbDepttable.setHistype(histype);
                tbDepttable.setDeptCode(deptCode);
                tbDepttable.setDeptName(deptName);
                tbDepttable.setDeptPYM(deptPYM);
                tbDepttable.setHospitalName(hospitalName);
                //0是非医技预约科室，1是医技预约科室
                tbDepttable.setIsReserve(0);

                List<TbDepttable> tbDepttables = tbDepttableMapper.selectDeptCodeAndHospitalName(deptCode, hospitalName);
                if (tbDepttables.isEmpty()) {
                    //不存在 插入数据
                    tbDepttableMapper.addTable(tbDepttable);
                }else{
                    //存在 更新数据
                    Integer result = tbDepttableMapper.updateTable(tbDepttable);
                    if(result < 1){
                        //throw new MyException("修改科室失败");
                        return new R(1,"修改科室失败");
                    }
                }
            }

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return new R(requestDeptInfo);
    }

    /**暂时无状态
    @Override
    public Integer stopRestore(Integer id, Integer status) {
        TbDepttable tbDepttable = new TbDepttable();
        tbDepttable.setDeptID(id);
        tbDepttable.set
        Integer result = tbDepttableMapper.updateTbDepttable(tbDepttable);
        if(result < 1){
            throw new MyException("修改科室失败");
        }
        return tbDepttable.getDeptID();
    }*/

}
