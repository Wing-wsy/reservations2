package com.annet.service.impl;


import com.annet.common.MyException;
import com.annet.entity.*;
import com.annet.entity.bo.BespokeEntity;
import com.annet.entity.bo.ReportInfo;
import com.annet.entity.domain.GetBespokeRes;
import com.annet.entity.domain.GetPacsSelfRegister;
import com.annet.entity.operating.*;
import com.annet.mapper.*;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbBespokeService;
import com.annet.service.TbBespokeresLsbService;
import com.annet.utils.AuthUtil;
import com.annet.utils.StrUtils;
import com.annet.utils.TimeUtils;
import com.annet.vo.GetRecommendationVo;
import com.annet.xmlEntity.GetRequestInfo;
import com.annet.xmlEntity.GetRequestInfoList;
import com.annet.xmlEntity.QueueInfo;
import com.annet.yml.Family;
import com.annet.yml.WebService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.thoughtworks.xstream.XStream;

import org.apache.cxf.endpoint.Client;

//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import com.annet.config.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import net.sf.json.JSONObject;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XiaYu
 * @since 2019-05-15
 */
@Service
@AllArgsConstructor
@Slf4j
public class TbBespokeServiceImpl extends ServiceImpl<TbBespokeMapper, TbBespoke> implements TbBespokeService {

    /**
     * webservice地址
     */
    private final WebService webService;

    private final TbMethodPartMapper tbMethodPartMapper;

    private final HttpSession httpSession;

    private final TbBespokeMapper tbBespokeMapper;

    private final TbBespokeresMapper tbBespokeresMapper;

    private final TbBespokeresLsbService tbBespokeresLsbService;

    private final TbStudyroomMapper tbStudyroomMapper;

    private final TbOrderDetailMapper tbOrderDetailMapper;

    private final TbOrderFileMapper tbOrderFileMapper;

    // 保存当前登录的所有用户
//    public static List<String> loginAppList = new ArrayList<>();
    /**
     * 医技预约(匹配)
     *
     * @param idType id类型
     * @param id     id
     */
    @Override
    public List<GetRequestInfo> mTReservation(String idType, String id, String hospitalName, String operatorCode, String execdeptcode) {
        if (StrUtils.isNullOrEmpty(hospitalName)) {
            throw new MyException("医院名称参数不能为空");

        }
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        System.out.println(webService.getUrl());
        //  创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(webService.getUrl());
        String sParam =
                "<root>\n" +
                        "<request>\n" +
                        "<idtype>" + idType + "</idtype> \n" +
                        "<id>" + id + "</id>\n" +
                        "<execdeptcode>" + execdeptcode + "</execdeptcode>\n" +
                        "</request>\n" +
                        "</root>";
        System.out.println(sParam);
        Object[] objects;
        List<GetRequestInfo> getRequestInfo = new ArrayList<>();
        try {
            //  invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("HISInterface", "GetRequestInfo", sParam);
            System.out.println("返回数据1:" + objects[0]);
            String bojectString = objects[0].toString();
            String returns = bojectString.substring(bojectString.indexOf("<return>"), bojectString.indexOf("</return>") + 9);
            log.info(returns);
            if (returns.contains("0")) {
                log.info("his数据不存在");
                return null;
            }
            bojectString = bojectString.substring(bojectString.indexOf("<lists>"), bojectString.indexOf("</lists>") + 8);
            System.out.println(bojectString);
            XStream xstream = new XStream();
            // 忽略为空的（如果有空不写会报错）
            xstream.ignoreUnknownElements();
            xstream.autodetectAnnotations(true);
            xstream.processAnnotations(GetRequestInfoList.class);
            GetRequestInfoList getRequestInfoList = (GetRequestInfoList) xstream.fromXML(bojectString);

            System.out.println(getRequestInfoList);
            getRequestInfo.addAll(getRequestInfoList.getGetRequestInfoList());
            // getRequestInfo.addAll(getRequestInfoList.getGetRequestInfoList().stream().filter(p -> p.getRequestDept().equals(hospitalName)).collect(Collectors.toList()));
            if (getRequestInfo.isEmpty()) {
                return getRequestInfo;
            }

            //New Code
            //调用存储过程查询是否匹配和是否预约
            StringBuilder requestXmlString = new StringBuilder();
            requestXmlString.append("<root>");
            for (GetRequestInfo getinfo : getRequestInfo) {
                getinfo.setRequestDate(getinfo.getRequestDate().replace("/", "-"));
                String param = "<request>\n" +
                        " <requestno>" + getinfo.getRequestNo() + "</requestno>\n" +
                        " <ordrowid>" + getinfo.getOrdRowID() + "</ordrowid>\n" +
                        " <modality>" + getinfo.getModality() + "</modality>\n" +
                        " <studypart>" + getinfo.getStudyPart() + "</studypart>\n" +
                        " <studypartcode>" + getinfo.getStudyPartCode() + "</studypartcode>\n" +
                        " <studyproject>" + getinfo.getStudyProject() + "</studyproject>\n" +
                        " <studyprojectcode>" + getinfo.getStudyProjectCode() + "</studyprojectcode>\n" +
                        " </request>";

                requestXmlString.append(param);


            }
            requestXmlString.append("</root>");
            Map<String, Object> params = new HashMap<>(16);
            params.put("RequestXml", requestXmlString.toString());
            params.put("HospitalName", hospitalName);
            List<GetRequestInfo> list2 = tbMethodPartMapper.getMethodAndBespokeCount(params);
            System.out.println(list2);

            for (int i = 0; i < getRequestInfo.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    if (getRequestInfo.get(i).getRequestNo().equals(list2.get(j).getRequestNo()) &&
                            getRequestInfo.get(i).getOrdRowID().equals(list2.get(j).getOrdRowID()) &&
                            getRequestInfo.get(i).getModality().equals(list2.get(j).getModality()) &&
                            getRequestInfo.get(i).getStudyPart().equals(list2.get(j).getStudyPart()) &&
                            getRequestInfo.get(i).getStudyPartCode().equals(list2.get(j).getStudyPartCode()) &&
                            getRequestInfo.get(i).getStudyProject().equals(list2.get(j).getStudyProject()) &&
                            getRequestInfo.get(i).getStudyProjectCode().equals(list2.get(j).getStudyProjectCode())) {

                        if(list2.get(j).getMethodPartCount() == 0){
                            getRequestInfo.get(i).setStatus("未匹配");
                            getRequestInfo.get(i).setPartID("0");
                        }else {
                            if(list2.get(j).getBespokeCount() > 0){
                                getRequestInfo.get(i).setStatus("已预约");
                                //getRequestInfo.get(i).setWarning(list2.get(j).getWarning());
                                getRequestInfo.get(i).setBrokerDate(list2.get(j).getBespokeStudyDate());
                            }else{
                                //getRequestInfo.get(i).setWarning(list2.get(j).getWarning());
                                getRequestInfo.get(i).setStatus("未预约");
                            }
                            // 主键ID
                            getRequestInfo.get(i).setPartID(list2.get(j).getPartID());
                            // 所需要时间
                            getRequestInfo.get(i).setTimeCoefficient(list2.get(j).getTimeCoefficient());
                            // 检查方法
                            getRequestInfo.get(i).setStudyMethod(list2.get(j).getStudyMethod());
                            // RIS检查部位
                            getRequestInfo.get(i).setXstudyPart(list2.get(j).getStudyPart());
                            // RIS检查部位编码
                            getRequestInfo.get(i).setXstudyPartCode(list2.get(j).getStudyPartCode());
                            if (null == tbUser) {
                                getRequestInfo.get(i).setOperatorCode(operatorCode);
                            } else {
                                getRequestInfo.get(i).setOperatorCode(tbUser.getUserID());
                            }
                        }
                        break;
                    }
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return getRequestInfo;

    }

    @Override
    public boolean insertAllColumn(TbBespoke entity) {
        return super.insertAllColumn(entity);
    }

    /**
     *精确查询和批量查询
     */
    @Override
    public R<List<GetRequestInfo>> mTReservationNew(String histype,String idType, String id, String hospitalName, String operatorCode,
                                                    String execdeptcode, String requestdeptcode,String startdate, String enddate,
                                                    String zhuanyun) {
        if(zhuanyun == null){
            zhuanyun = "";
        }
        String deptCode = "";
        if(requestdeptcode != null){
            deptCode = requestdeptcode;
        }
        long time1=System.currentTimeMillis();
        System.out.println("进入获取申请单接口时间：0s");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (StrUtils.isNullOrEmpty(hospitalName)) {
            return new R(1,"","医院名称参数不能为空");
        }
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        long time2=System.currentTimeMillis();
        System.out.println("创建动态客户端之前时间：" + (float)(time2-time1)/1000 + "s");
        //  创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        log.info("webService接口："+webService.getUrl());
        if(dcf == null){
            System.out.println("dcf为空");
        }
        Client client = dcf.createClient(webService.getUrl());
        long time3=System.currentTimeMillis();
        System.out.println("创建动态客户端之后时间：" + (float)(time3-time1)/1000 + "s");
        // 医院现场
        String sParam = "";
        if(id != null){
            //精确查询
             sParam =
                    "<root>\n" +
                            "<request>\n" +
                            "<histype>" + histype + "</histype> \n"+
                            "<idtype>" + idType + "</idtype> \n" +
                            "<id>" + id + "</id>\n" +
                            "<execdeptcode>" + execdeptcode + "</execdeptcode>\n" +
                            "<requestdeptcode>" + deptCode + "</requestdeptcode>\n" +
                            "<startdate></startdate>\n" +
                            "<enddate></enddate>\n" +
                            "<zhuanyun></zhuanyun>\n" +
                            "</request>\n" +
                            "</root>";
            System.out.println(sParam);
        }else{
            //批量查询
            sParam =
                    "<root>\n" +
                            "<request>\n" +
                            "<histype>" + histype + "</histype> \n"+
                            "<idtype></idtype> \n" +
                            "<id></id>\n" +
                            "<execdeptcode>" + execdeptcode + "</execdeptcode>\n" +
                            "<requestdeptcode>" + requestdeptcode + "</requestdeptcode>\n" +
                            "<startdate>" + startdate + "</startdate>\n" +
                            "<enddate>" + enddate + "</enddate>\n" +
                            "<zhuanyun>" + zhuanyun + "</zhuanyun>\n" +
                            "</request>\n" +
                            "</root>";
        }

        Object[] objects;
        List<GetRequestInfo> getRequestInfo = new ArrayList<>();
        try {
            long time4=System.currentTimeMillis();
            System.out.println("调用HIS接口前时间：" + (float)(time4-time1)/1000 + "s");
            //  invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("HISInterface", "GetRequestInfo", sParam);
            String bojectString = objects[0].toString();
            long time5=System.currentTimeMillis();
            System.out.println("调用HIS接口后时间：" + (float)(time5-time1)/1000 + "s");
            String returns = "";
            if(bojectString.contains("<return>")){
                 returns = bojectString.substring(bojectString.indexOf("<return>"), bojectString.indexOf("</return>")+9);
            }else{
                return new R(1,"","GetRequestInfo接口无数据返回");
            }

            if (returns.contains("0")) {
                log.info("his数据不存在");
                return new R(1,"","his数据不存在");
            }
            bojectString = bojectString.substring(bojectString.indexOf("<lists>"), bojectString.indexOf("</lists>") + 8);
            XStream xstream = new XStream();
            // 忽略为空的（如果有空不写会报错）
            xstream.ignoreUnknownElements();
            xstream.autodetectAnnotations(true);
            xstream.processAnnotations(GetRequestInfoList.class);
            GetRequestInfoList getRequestInfoList = (GetRequestInfoList) xstream.fromXML(bojectString);
            getRequestInfo.addAll(getRequestInfoList.getGetRequestInfoList());
            if (getRequestInfo.isEmpty()) {
                return new R(getRequestInfo);
            }
            for (GetRequestInfo getinfo : getRequestInfo) {
                String str1 = " 00:00:00";
                String str2 = "";
                int result1 = getinfo.getRequestDate().indexOf("T");
                if(result1 != -1){
                    String replace = getinfo.getRequestDate().replace("T", " ");
                    getinfo.setaRequestDate(replace);
                }else if(getinfo.getRequestDate().length() == 10){
                    //日期格式为yyyy-MM-dd 要做处理
                    str2 = getinfo.getRequestDate() + str1;
                    getinfo.setaRequestDate(str2);
                }else{
                    getinfo.setaRequestDate(getinfo.getRequestDate());
                }
            }

            String configKey = "ReservationDate";
            String configNum = tbBespokeMapper.getBespokeNum(configKey);
            if(configNum == null || configNum.equals("")){
                return new R(1,"获取半年时间内的申请单未配置，请联系服务人员！");
            }
            int bespokeNum = Integer.parseInt(configNum);
             getRequestInfo = getRequestInfo.stream().filter((e) -> {
                Date date1 = TimeUtils.parseDate(e.getaRequestDate(), "yyyy-MM-dd HH:mm:ss");
                Date date2 = new Date();

                // 获取相差的天数
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                long timeInMillis1 = calendar.getTimeInMillis();
                calendar.setTime(date2);
                long timeInMillis2 = calendar.getTimeInMillis();

                long betweenDays = (timeInMillis2 - timeInMillis1) / (1000L * 3600L * 24L);
                System.out.println(betweenDays);
                 return Integer.parseInt(betweenDays + "") < bespokeNum;
             }).collect(Collectors.toList());

            //调用存储过程查询是否匹配和是否预约
            StringBuilder requestXmlString = new StringBuilder();
            requestXmlString.append("<root>");
            long time6=System.currentTimeMillis();
            System.out.println("调用是否匹配存储过程拼接参数之前时间：" + (float)(time6-time1)/1000 + "s");
            for (GetRequestInfo getinfo : getRequestInfo) {
                getinfo.setRequestDate(getinfo.getRequestDate().replace("/", "-"));
                String param = "<request>\n" +
                        " <requestno>" + getinfo.getRequestNo() + "</requestno>\n" +
                        " <ordrowid>" + getinfo.getOrdRowID() + "</ordrowid>\n" +
                        " <modality>" + getinfo.getModality() + "</modality>\n" +
                        " <studypart>" + getinfo.getStudyPart() + "</studypart>\n" +
                        " <studypartcode>" + getinfo.getStudyPartCode() + "</studypartcode>\n" +
                        " <studyproject>" + getinfo.getStudyProject() + "</studyproject>\n" +
                        " <studyprojectcode>" + getinfo.getStudyProjectCode() + "</studyprojectcode>\n" +
                        " </request>";
                requestXmlString.append(param);

                //年龄为空时，用生日计算年龄
                if(getinfo.getAge() == null || getinfo.getAge().equals("")){
                    if(getinfo.getBirthday() != null){
                        String birthday = getinfo.getBirthday();
                        String substring = birthday.substring(0, 10);
                        //System.out.println(substring);
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        Date parse = f.parse(substring);
                        int age = TimeUtils.getAgeByBirth(parse);
                        getinfo.setAge(String.valueOf(age));
                    }
                }

                List<String> alist = Arrays.asList("N","Y","E");
                boolean flag1 = alist.contains(getinfo.getHospitalizeType());
                if(!flag1){
                    if(getinfo.getHospitalizeType().equals("1")){
                        getinfo.setHospitalizeType("N");
                    }else if(getinfo.getHospitalizeType().equals("3")){
                        getinfo.setHospitalizeType("Y");
                    }else{
                        getinfo.setHospitalizeType("E");
                    }
                }
            }
            requestXmlString.append("</root>");
            Map<String, Object> params = new HashMap<>(16);
            params.put("RequestXml", requestXmlString.toString());
            params.put("HospitalName", hospitalName);
            long time7=System.currentTimeMillis();
            System.out.println("调用是否匹配存储过程之前时间：" + (float)(time7-time1)/1000 + "s");
            List<GetRequestInfo> list1 = tbMethodPartMapper.getMethodAndBespokeCount(params);
            long time8=System.currentTimeMillis();
            System.out.println("调用是否匹配存储过程之后时间：" + (float)(time8-time1)/1000 + "s");

            for (int i = 0; i < getRequestInfo.size(); i++) {
                for (int j = 0; j < list1.size(); j++) {
                    if (getRequestInfo.get(i).getRequestNo().equals(list1.get(j).getRequestNo()) &&
                            getRequestInfo.get(i).getOrdRowID().equals(list1.get(j).getOrdRowID()) &&
                            getRequestInfo.get(i).getModality().equals(list1.get(j).getModality()) &&
                            getRequestInfo.get(i).getStudyPart().equals(list1.get(j).getStudyPart()) &&
                            getRequestInfo.get(i).getStudyPartCode().equals(list1.get(j).getStudyPartCode()) &&
                            getRequestInfo.get(i).getStudyProject().equals(list1.get(j).getStudyProject()) &&
                            getRequestInfo.get(i).getStudyProjectCode().equals(list1.get(j).getStudyProjectCode())) {

                        getRequestInfo.get(i).setOnlineBespoke(list1.get(j).getOnlineBespoke());

                        if(list1.get(j).getMethodPartCount() == 0){
                            getRequestInfo.get(i).setStatus("未匹配");
                            getRequestInfo.get(i).setPartID("0");
                        }else {
                            if(list1.get(j).getBespokeCount() > 0){
                                getRequestInfo.get(i).setStatus("已预约");
                                getRequestInfo.get(i).setWarning(list1.get(j).getWarning());
                                getRequestInfo.get(i).setBrokerDate(list1.get(j).getBespokeStudyDate());

                            }else{
                                getRequestInfo.get(i).setWarning(list1.get(j).getWarning());
                                getRequestInfo.get(i).setStatus("未预约");
                            }
                            // 主键ID
                            getRequestInfo.get(i).setPartID(list1.get(j).getPartID());
                            // 所需要时间
                            getRequestInfo.get(i).setTimeCoefficient(list1.get(j).getTimeCoefficient());
                            // 检查方法
                            getRequestInfo.get(i).setStudyMethod(list1.get(j).getStudyMethod());
                            // RIS检查部位
                            getRequestInfo.get(i).setXstudyPart(list1.get(j).getStudyPart());
                            // RIS检查部位编码
                            getRequestInfo.get(i).setXstudyPartCode(list1.get(j).getStudyPartCode());

                            //是否报到
                            getRequestInfo.get(i).setStudyStatus(list1.get(j).getStudyStatus());
                            if (null == tbUser) {
                                getRequestInfo.get(i).setOperatorCode(operatorCode);
                            } else {
                                getRequestInfo.get(i).setOperatorCode(tbUser.getUserID());
                            }
                        }
                        break;
                    }
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        long time9=System.currentTimeMillis();
        System.out.println("方法全部执行完时间：" + (float)(time9-time1)/1000 + "s");
        List<GetRequestInfo> returnGetRequestInfo = getRequestInfo.stream().filter((e) -> !"1".equals(e.getStudyStatus())).collect(Collectors.toList());
        return new R(returnGetRequestInfo);
    }

    /**
     * 提交预约(已弃用)
     *
     * @param tbBespokeList 预约集合
     * @return 预约信息
     */
    @Override
    public List<ResultTJ> reservation(List<TbBespoke> tbBespokeList) {
        httpSession.removeAttribute("hgBespokeList");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String bespokeNo = df.format(new Date());
        String in = "000";
        String max = tbBespokeMapper.selectMaxID(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), tbBespokeList.get(0).getHospitalName());
        if (max != null) {
            String result = max.substring(max.length() - 4);
            in = "1";
            in += result;
            Integer intResult = Integer.parseInt(in) + 1;
            String ins = intResult.toString();
            in = ins.substring(ins.length() - 4);
        } else {
            in = in + "1";
        }
        bespokeNo += in;
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");

        List<ResultTJ> resultTJList = new ArrayList<>();

        for (int i = 0; i < tbBespokeList.size(); i++) {
            // 预约编号201904010001
            tbBespokeList.get(i).setBespokeNo(bespokeNo);
            // 预约方式 1：临床预约 2：预约中心预约 3：医技科室预约 4：自助预约
            tbBespokeList.get(i).setBespokeType(3);
            // 缴费状态 0:未缴费 1：已缴费
            tbBespokeList.get(i).setFeeStatus(1);
            // 预约状态 1：已预约 2：已取消
            tbBespokeList.get(i).setBespokeStatus(1);
            // 检查状态 0:未报到 1：已报到 2：已检查
            tbBespokeList.get(i).setStudyStatus(0);
            // 预约员工号
            tbBespokeList.get(i).setBespokeOpreatorCode(tbUser.getUserID());
            // 预约员姓名
            tbBespokeList.get(i).setBespokeOpreator(tbUser.getUserName());
            // 预约时间
            tbBespokeList.get(i).setBespokeDate(new Date());
            Integer result = tbBespokeMapper.insertTbBespoke(tbBespokeList.get(i));
            if (result < 1) {
                throw new MyException(tbBespokeList.get(i).getName() + "预约失败");
            }

            // 返回检查部位注意事项

            ResultTJ resultTJ = new ResultTJ();
            // 检查部位
            resultTJ.setStudyPart(tbBespokeList.get(i).getStudyPart());
            resultTJ.setGroupType(tbBespokeList.get(i).getGroupType().toString());
            if (tbBespokeList.get(i).getGroupType() == 1) {
                // 检查方法
                resultTJ.setStudyMethod(tbBespokeList.get(i).getStudyMethod());
            } else {
                // 检查房间
                resultTJ.setStudyRoom(tbBespokeList.get(i).getStudyRoom());
            }
            // 申请科室
            resultTJ.setRequestDept(tbBespokeList.get(i).getRequestDept());
            // 申请医生
            resultTJ.setRequestDoctor(tbBespokeList.get(i).getRequestDoctor());

            // 注意事项(新增院区)
            List<TbMethodPart> tbMethodParts = tbMethodPartMapper.selectYJReservation(tbBespokeList.get(i).getModality(),
                    tbBespokeList.get(i).getHISStudyProjectCode(), tbBespokeList.get(i).getHISStudyProject(),
                    tbBespokeList.get(i).getHISStudyPartCode(), tbBespokeList.get(i).getHISStudyPart(), tbBespokeList.get(i).getHospitalName());
            if (tbMethodParts != null && tbMethodParts.size() > 0) {
                resultTJ.setWarning(tbMethodParts.get(0).getWarning());
            }
            resultTJ.setBespokeDate(new SimpleDateFormat("yyyy-MM-dd").format(tbBespokeList.get(i).getBespokeStudyDate()));
            resultTJ.setStartTime(tbBespokeList.get(i).getStartTime());
            resultTJ.setEndTime(tbBespokeList.get(i).getEndTime());
            // 申请单号
            resultTJ.setRequestNo(tbBespokeList.get(i).getRequestNo());
            resultTJ.setBespokeNo(bespokeNo);
            resultTJList.add(resultTJ);
        }
        // httpSession.removeAttribute("hgBespokesMap");
        // 清除号源
        List<TbBespokeresLsb> tbBespokeresLsbList;
        if (tbUser == null) {
            List<Integer> partIds = tbBespokeList.stream().map(TbBespoke::getPartID).collect(Collectors.toList());
            tbBespokeresLsbList = tbBespokeresLsbService.selectByPartId(partIds);
        } else {
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        }
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                // 删除号源临时表数据
                tbBespokeresLsbService.daleteUserID(tbBespokeresLsb.getId());
            }
        }
        return resultTJList;
    }

    /**
     * 提交预约（待弃用）
     *
     * @param tbBespokeList 预约集合
     * @return 预约信息
     */
    @Override
    public List<ResultTJ> reservationNew(List<TbBespoke> tbBespokeList) {
        httpSession.removeAttribute("hgBespokeList");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String bespokeNo = df.format(new Date());
        String in = "000";
        String max = tbBespokeMapper.selectMaxID(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), tbBespokeList.get(0).getHospitalName());
        if (max != null) {
            String result = max.substring(max.length() - 4);
            in = "1";
            in += result;
            Integer intresult = Integer.parseInt(in) + 1;
            String ins = intresult.toString();
            in = ins.substring(ins.length() - 4);
        } else {
            in = in + "1";
        }
        bespokeNo += in;
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");

        List<ResultTJ> resultTJList = new ArrayList<>();

        for (int i = 0; i < tbBespokeList.size(); i++) {
            TbBespoke tbBespoke = tbBespokeList.get(i);
            // 预约编号201904010001
            tbBespoke.setBespokeNo(bespokeNo);
            // 缴费状态 0:未缴费 1：已缴费
            tbBespoke.setFeeStatus(1);
            // 预约状态 1：已预约 2：已取消
            tbBespoke.setBespokeStatus(1);
            // 检查状态 0:未报到 1：已报到 2：已检查
            tbBespoke.setStudyStatus(0);
            if (tbUser == null) {
                tbBespoke.setBespokeOpreatorCode(tbBespoke.getOperatorCode());
                tbBespoke.setBespokeType(1);
            } else {
                // 预约员工号
                tbBespoke.setBespokeOpreatorCode(tbUser.getUserID());
                // 预约员姓名
                tbBespoke.setBespokeOpreator(tbUser.getUserName());
                String deptName = tbBespoke.getDeptName();
                if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
                    // 预约方式 1：临床预约 2：预约中心预约 3：医技科室预约 4：自助预约
                    tbBespoke.setBespokeType(2);
                } else {
                    // 预约方式 1：临床预约 2：预约中心预约 3：医技科室预约 4：自助预约
                    tbBespoke.setBespokeType(3);
                }
            }
            // 预约时间
            tbBespoke.setBespokeDate(new Date());

            // 2019-11-19新增代码

            //  若提交的预约是检查房间，则需要将预约时间精准到分钟（1：按检查方法分组； 2：按检查房间分组）
            TbStudyroom tbStudyrooms = tbStudyroomMapper.selectWhetherPreciseTime(tbBespoke.getExecDept(), tbBespoke.getModality(),
                    tbBespoke.getStudyRoom(), tbBespoke.getHospitalName());
            if ((tbBespoke.getGroupType().equals(2)) && (!(null == tbStudyrooms))) {
                //  得到检查房间检查耗时数
                Integer timeCoefficient = tbBespoke.getTimeCoefficient();
                //  查询提交条件已预约的检查预约,execDept(执行科室),studyRoom(检查房间),groupType(分组方式1：按检查方法分组； 2：按检查房间分组)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(tbBespoke.getBespokeStudyDate());
                List<TbBespoke> tbBespokes = tbBespokeMapper.find(tbBespoke.getExecDept(), tbBespoke.getStudyRoom(),
                        tbBespoke.getModality(), tbBespoke.getHospitalName(),
                        tbBespoke.getStartTime(), tbBespoke.getEndTime(), date, 2)
                        .stream().sorted(Comparator.comparing(TbBespoke::getStartTime)).collect(Collectors.toList());
                // 若预约集合为空，就无须考虑空挡时间插入
                if (tbBespokes.isEmpty()) {
                    String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                    tbBespoke.setEndTime(startTimeNew);
                }
                // 若预约集合大小为1，看那个时间段空缺就插入到那个时间段
                else if (tbBespokes.size() == 1) {
                    if (tbBespokes.get(0).getStartTime().equals(tbBespoke.getStartTime())) {
                        String endTime = tbBespokes.get(0).getEndTime();
                        tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                        String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    } else {
                        String startTime = tbBespokes.get(0).getStartTime();
                        int difference = TimeUtils.calculateTime(tbBespoke.getStartTime(), startTime);
                        if (difference > 1) {
                            String startTimeNew = TimeUtils.addMinute(startTime, timeCoefficient);
                            tbBespoke.setEndTime(startTimeNew);
                        } else {
                            String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                            tbBespoke.setEndTime(startTimeNew);
                        }
                    }
                } else if (tbBespokes.size() > 1) {
                    if (!tbBespokes.get(0).getStartTime().equals(tbBespoke.getStartTime())) {
                        String endTime = tbBespokes.get(0).getEndTime();
                        tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                        String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    } else {
                        for (int j = 0; j <= tbBespokes.size() - 1; j++) {
                            if (!(j == (tbBespokes.size() - 1))) {
                                String endTime = tbBespokes.get(j).getEndTime();
                                String startTime = tbBespokes.get(j + 1).getStartTime();
                                int difference = TimeUtils.calculateTime(endTime, startTime);
                                if (difference >= 2) {
                                    String endTime2 = tbBespokes.get(j).getEndTime();
                                    tbBespoke.setStartTime(TimeUtils.addMinute(endTime2, 1));
                                    String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                                    tbBespoke.setEndTime(startTimeNew);
                                    break;
                                }
                            } else {
                                if (!(j == (tbBespokes.size() - 1))) {
                                    continue;
                                }
                                String endTime3 = tbBespokes.get(tbBespokes.size() - 1).getEndTime();
                                tbBespoke.setStartTime(TimeUtils.addMinute(endTime3, 1));
                                String startTimeNew2 = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                                tbBespoke.setEndTime(startTimeNew2);
                            }
                        }
                    }
                }
            }
            String ordRowID = tbBespoke.getOrdRowID();
            // 表结构更改，插入式这些字段现置位空
            TbOrderDetail tbOrderDetail = new TbOrderDetail();
            tbOrderDetail.setOrdRowID(tbBespoke.getOrdRowID());
            tbOrderDetail.setHISStudyProjectCode(tbBespoke.getHISStudyProjectCode());
            tbOrderDetail.setHISStudyProject(tbBespoke.getHISStudyProject());
            tbOrderDetail.setHISStudyPartCode(tbBespoke.getHISStudyPartCode());
            tbOrderDetail.setHISStudyPart(tbBespoke.getHISStudyPart());
            tbOrderDetail.setFee(new BigDecimal(tbBespoke.getFee()));
            tbBespoke.setOrdRowID(null);
            tbBespoke.setHISStudyPart(null);
            tbBespoke.setHISStudyPartCode(null);
            tbBespoke.setHISStudyProjectCode(null);
            tbBespoke.setHISStudyProject(null);
            tbBespoke.setFee(null);
            // 插入预约到预约表，并获取新增的主键id
            tbBespokeMapper.insertTbBespoke(tbBespoke);
            Integer id = tbBespoke.getId();
            if (tbBespoke.getId() == null) {
                throw new MyException(tbBespoke.getName() + "预约失败");
            }
            // 紧接着将医嘱明细信息存入医嘱明细表
            tbOrderDetail.setBespokeID(id);
            Integer integer = tbOrderDetailMapper.insertTbOrderDetail(tbOrderDetail);
            if (integer < 1) {
                throw new MyException(tbBespoke.getName() + "预约失败");
            }
            // 返回检查部位注意事项
            ResultTJ resultTJ = new ResultTJ();
            // 检查部位
            resultTJ.setStudyPart(tbBespoke.getStudyPart());
            resultTJ.setGroupType(tbBespoke.getGroupType().toString());
            if (tbBespoke.getGroupType() == 1) {
                // 检查方法
                resultTJ.setStudyMethod(tbBespoke.getStudyMethod());
            } else {
                // 检查房间
                resultTJ.setStudyRoom(tbBespoke.getStudyRoom());
            }
            // 申请科室
            resultTJ.setRequestDept(tbBespoke.getRequestDept());
            // 申请医生
            resultTJ.setRequestDoctor(tbBespoke.getRequestDoctor());

            // 注意事项(新增院区)
            List<TbMethodPart> tbMethodParts = tbMethodPartMapper.selectYJReservation(tbBespoke.getModality(),
                    tbBespoke.getHISStudyProjectCode(), tbBespoke.getHISStudyProject(),
                    tbBespoke.getHISStudyPartCode(), tbBespoke.getHISStudyPart(), tbBespoke.getHospitalName());
            if (tbMethodParts != null && tbMethodParts.size() > 0) {
                resultTJ.setWarning(tbMethodParts.get(0).getWarning());
            }
            resultTJ.setBespokeDate(new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeStudyDate()));
            resultTJ.setStartTime(tbBespoke.getStartTime());
            resultTJ.setEndTime(tbBespoke.getEndTime());
            // 申请单号
            resultTJ.setRequestNo(tbBespoke.getRequestNo());
            // 预约单号
            resultTJ.setBespokeNo(bespokeNo);
            resultTJList.add(resultTJ);
        }

        // httpSession.removeAttribute("hgBespokesMap");
        // 清除号源
        List<TbBespokeresLsb> tbBespokeresLsbList;
        if (tbUser == null) {
            List<Integer> partIds = tbBespokeList.stream().map(TbBespoke::getPartID).collect(Collectors.toList());
            tbBespokeresLsbList = tbBespokeresLsbService.selectByPartId(partIds);
        } else {
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        }
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                // 删除号源临时表数据
                tbBespokeresLsbService.daleteUserID(tbBespokeresLsb.getId());
            }
        }
        return resultTJList;
    }

    @Autowired
    Family family;

    /**
     * 提交预约（正使用）
     * @param getRecommendationVoLists 预约信息集合
     * @return 预约信息和医嘱信息
     */
    @Override
    @Transactional
    public R<List<ResultTJ>> reservationNew2(List<GetRecommendationVo> getRecommendationVoLists) throws ParseException {
        long time1=System.currentTimeMillis();

        httpSession.removeAttribute("hgBespokeList");
        List<GetRecommendationVo> getRecommendationVoList;
        if(getRecommendationVoLists.isEmpty()){
            return new R(1,null,"提交预约对象不能为空");
        }else {
            getRecommendationVoList = getRecommendationVoLists.
                    stream().filter(p -> p.getTbBespoke().getAppointmentStatus().equals("1")).collect(Collectors.toList());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String bespokeNo = df.format(new Date());
        String in = "000";
        String max = tbBespokeMapper.selectMaxID(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), getRecommendationVoList.get(0).getTbBespoke().getHospitalName());
        if (max != null) {
            String result = max.substring(max.length() - 4);
            in = "1";
            in += result;
            Integer intresult = Integer.parseInt(in) + 1;
            String ins = intresult.toString();
            in = ins.substring(ins.length() - 4);
        } else {
            in = in + "1";
        }
        bespokeNo += in;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TbUser tbUser = (TbUser) request.getSession().getAttribute("user");
        //微信端
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            tbUser = new TbUser();
            if(getRecommendationVoLists.size()>0){
                tbUser.setUserID(getRecommendationVoLists.get(0).getUserID());
            }
        }
        //判断用户是否过期
        if(tbUser == null){
            return new R(1,null,"登录用户已过期");
        }
        List<ResultTJ> resultTJList = new ArrayList<>();

        String str = "";
        for (int i = 0; i < getRecommendationVoList.size(); i++) {
            TbBespoke tbBespoke = getRecommendationVoList.get(i).getTbBespoke();
            log.info("ArriveDate>>>>"+tbBespoke.getsArriveDate());
            tbBespoke.setArriveDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tbBespoke.getsArriveDate()));
            List<TbOrderDetail> tbOrderDetailList = getRecommendationVoList.get(i).getTbOrderDetailList();

            if(family.getIsTrue()!=null && family.getIsTrue().equals("1")){
                for(TbOrderDetail tbOrderDetail:tbOrderDetailList){
                    //提交预约先判断是否已经预约，避免重复提交
                    Integer BespokeNum = tbBespokeMapper.getBespokeInfo(tbBespoke.getRequestNo(), tbOrderDetail.getOrdRowID(),
                            tbOrderDetail.getHISStudyPartCode(), tbOrderDetail.getHISStudyProjectCode(),
                            tbBespoke.getModality(), tbBespoke.getHospitalName());
                    if(BespokeNum > 0){
                        return new R(1,null,"申请单已预约");
                    }
                }
            }


            // 预约编号201904010001
            tbBespoke.setBespokeNo(bespokeNo);
            // 缴费状态 0:未缴费 1：已缴费
            tbBespoke.setFeeStatus(1);
            // 预约状态 1：已预约 2：已取消
            tbBespoke.setBespokeStatus(1);
            // 检查状态 0:未报到 1：已报到 2：已检查
            tbBespoke.setStudyStatus(0);
            if (tbUser == null) {
                tbBespoke.setBespokeOpreatorCode(tbBespoke.getOperatorCode());
                tbBespoke.setBespokeType(1);
            } else {
                // 预约员工号
                tbBespoke.setBespokeOpreatorCode(tbUser.getUserCode());
                // 预约员姓名
                tbBespoke.setBespokeOpreator(tbUser.getUserName());
                String deptName = tbBespoke.getDeptName();
                if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
                    if(family.getFlage()!=null && family.getFlage().equals("1")){
                        //微信端
                        tbBespoke.setBespokeType(4);
                    }else{
                        // 预约方式 1：临床预约 2：预约中心预约 3：医技科室预约 4：自助预约
                        tbBespoke.setBespokeType(2);
                    }

                } else {
                    // 预约方式 1：临床预约 2：预约中心预约 3：医技科室预约 4：自助预约
                    tbBespoke.setBespokeType(3);
                }
            }
            // 预约时间
            tbBespoke.setBespokeDate(new Date());

            /*
            * 2019-11-19新增代码
            */
            //  若提交的预约是检查房间，则需要将预约时间精准到分钟（1：按检查方法分组； 2：按检查房间分组）
            TbStudyroom tbStudyrooms = tbStudyroomMapper.selectWhetherPreciseTime(tbBespoke.getExecDept(), tbBespoke.getModality(),
                    tbBespoke.getStudyRoom(), tbBespoke.getHospitalName());
            if (  (!(null == tbStudyrooms)) && (tbBespoke.getGroupType().equals(2))) {
                // 检查时间系数取合并后的和
                Integer timeCoefficient = tbOrderDetailList.stream().mapToInt(TbOrderDetail::getTimeCoefficient).sum();
                // 查询提交条件已预约的检查预约,execDept(执行科室),studyRoom(检查房间),groupType(分组方式1：按检查方法分组； 2：按检查房间分组)
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String date = sdf.format(tbBespoke.getBespokeStudyDate());
                List<TbBespoke> tbBespokes = tbBespokeMapper.find(tbBespoke.getExecDept(), tbBespoke.getStudyRoom(),
                        tbBespoke.getModality(), tbBespoke.getHospitalName(),
                        tbBespoke.getStartTime(), tbBespoke.getEndTime(), date, 2)
                        .stream().sorted(Comparator.comparing(TbBespoke::getStartTime)).collect(Collectors.toList());
                // 若预约集合为空，就无须考虑空挡时间插入
                if (tbBespokes.isEmpty()) {
                    String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                    tbBespoke.setEndTime(startTimeNew);
                }
                // 若预约集合大小为1，看那个时间段空缺就插入到那个时间段
                else if (tbBespokes.size() == 1) {
                    if (tbBespokes.get(0).getStartTime().equals(tbBespoke.getStartTime())) {
                        String endTime = tbBespokes.get(0).getEndTime();
                        tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                        String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    } else {
                        String startTime = tbBespokes.get(0).getStartTime();
                        int difference = TimeUtils.calculateTime(tbBespoke.getStartTime(), startTime);
                        if (difference > 1) {
                            String startTimeNew = TimeUtils.addMinute(startTime, timeCoefficient);
                            tbBespoke.setEndTime(startTimeNew);
                        } else {
                            String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                            tbBespoke.setEndTime(startTimeNew);
                        }
                    }
                } else if (tbBespokes.size() > 1) {
                    if (!tbBespokes.get(0).getStartTime().equals(tbBespoke.getStartTime())) {
                        String endTime = tbBespokes.get(0).getEndTime();
                        tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                        String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    } else {
                        for (int j = 0; j <= tbBespokes.size() - 1; j++) {
                            if (!(j == (tbBespokes.size() - 1))) {
                                String endTime = tbBespokes.get(j).getEndTime();
                                String startTime = tbBespokes.get(j + 1).getStartTime();
                                int difference = TimeUtils.calculateTime(endTime, startTime);
                                if (difference >= 2) {
                                    String endTime2 = tbBespokes.get(j).getEndTime();
                                    tbBespoke.setStartTime(TimeUtils.addMinute(endTime2, 1));
                                    String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                                    tbBespoke.setEndTime(startTimeNew);
                                    break;
                                }
                            } else {
                                if (!(j == (tbBespokes.size() - 1))) {
                                    continue;
                                }
                                String endTime3 = tbBespokes.get(tbBespokes.size() - 1).getEndTime();
                                tbBespoke.setStartTime(TimeUtils.addMinute(endTime3, 1));
                                String startTimeNew2 = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                                tbBespoke.setEndTime(startTimeNew2);
                            }
                        }
                    }
                }
            }
            String ordRowID = tbBespoke.getOrdRowID();
            // 表结构更改，插入式这些字段现置位空
            tbBespoke.setOrdRowID(null);
            tbBespoke.setHISStudyPart(null);
            tbBespoke.setHISStudyPartCode(null);
            tbBespoke.setHISStudyProjectCode(null);
            tbBespoke.setHISStudyProject(null);
            tbBespoke.setFee(null);
            tbBespoke.setStudyParts(null);
            tbBespoke.setRequestDateTime(null);
            //tbBespoke.setPartID(null);

            String warning="";
            String requestNo="";
            String part="";
            for(TbOrderDetail tbOrderDetail:tbOrderDetailList){
                part=part+tbOrderDetail.getHISStudyPart()+",";
                if(tbOrderDetail.getWarning() != null){
                    if(warning.contains(tbOrderDetail.getWarning())){
                        System.out.println("不拼接");
                    }else {
                        warning+=tbOrderDetail.getWarning()+",";
                    }
                }
                if(requestNo.contains(tbOrderDetail.getRequestNo())){
                    continue;
                }
                requestNo=requestNo+tbOrderDetail.getRequestNo()+",";
            }
            String requestNo2 = StrUtils.deleteLastChar(requestNo, ",");
            //requestNo
            tbBespoke.setWarning(warning);
            tbBespoke.setRequestNo(requestNo2);
            //获取号源ID
//            Integer bespokeresID = tbBespokeresMapper.getBespokeresID(tbBespoke.getModality(), tbBespoke.getGroupType(), tbBespoke.getStudyRoom(),
//                    new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeStudyDate()), tbBespoke.getStartTime(),
//                    tbBespoke.getEndTime(), tbBespoke.getHospitalName());
//            tbBespoke.setResID(bespokeresID);
            // 插入预约到预约表，并获取新增的主键id
            tbBespokeMapper.insertTbBespoke(tbBespoke);
            long time2 = System.currentTimeMillis();
            float excTime=(float)(time2-time1)/1000;
            System.out.println("第" + (i+1) + "执行时间："+excTime+"s");
            Integer id = tbBespoke.getId();

            if (tbBespoke.getId() == null) {
                return new R(1,null,"预约失败");
            }
            String studyRoom = tbBespoke.getStudyRoom();

            // 返回检查部位注意事项
            ResultTJ resultTJ = new ResultTJ();
            resultTJ.setBespokeId(id+"");
            // 检查部位
            String s2 = part.replaceAll(" ", "");
            String substring1 = s2.substring(0, s2.length() - 1);
            resultTJ.setStudyPart(substring1);
            /*resultTJ.setStudyPart(tbBespoke.getStudyPart());*/
            resultTJ.setGroupType(tbBespoke.getGroupType().toString());
            if (tbBespoke.getGroupType() == 1) {
                // 检查方法
                resultTJ.setStudyMethod(tbBespoke.getStudyMethod());
            } else {
                // 检查房间
                resultTJ.setStudyRoom(tbBespoke.getStudyRoom());
            }
            // 报到时间
           // String time = TimeUtils.timeToString(tbBespoke.getArriveDate());
            resultTJ.setArriveTime(tbBespoke.getsArriveDate());
            // 申请科室
            resultTJ.setRequestDept(tbBespoke.getRequestDept());
            // 申请医生
            resultTJ.setRequestDoctor(tbBespoke.getRequestDoctor());
            // 检查项目
            if(tbOrderDetailList.size()==1){
                resultTJ.setStudyProject(tbOrderDetailList.get(0).getHISStudyProject());
            }else if(tbOrderDetailList.size()>1){
                StringBuilder stringBuilder = new StringBuilder();
                for(TbOrderDetail tbOrderDetail:tbOrderDetailList){
                    stringBuilder.append(tbOrderDetail.getHISStudyProject()).append(",");
                }
                String s = stringBuilder.toString();
                String s1 = s.replaceAll(" ", "");
                String substring = s1.substring(0, s1.length() - 1);
                resultTJ.setStudyProject(substring);
            }
            // 注意事项(新增院区)
            List<TbMethodPart> tbMethodParts = tbMethodPartMapper.selectYJReservation(tbBespoke.getModality(),
                    tbBespoke.getHISStudyProjectCode(), tbBespoke.getHISStudyProject(),
                    tbBespoke.getHISStudyPartCode(), tbBespoke.getHISStudyPart(), tbBespoke.getHospitalName());
            if (tbMethodParts != null && tbMethodParts.size() > 0) {
                resultTJ.setWarning(tbMethodParts.get(0).getWarning());
            }
            resultTJ.setBespokeDate(new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeStudyDate()));
            resultTJ.setStartTime(tbBespoke.getStartTime());
            resultTJ.setEndTime(tbBespoke.getEndTime());

            // 申请单号
            resultTJ.setRequestNo(tbBespoke.getRequestNo());
            // 预约单号
            resultTJ.setBespokeNo(bespokeNo);
            resultTJ.setWarning(tbBespoke.getWarning());
            resultTJ.setExecDept(tbBespoke.getExecDept());
            //房间代码
            String roomCode = "";
            //检查地点
            String address = "";
            if(tbBespoke.getGroupType() == 2 ){
                List<TbStudyroom> tbStudyroom = tbStudyroomMapper.selectRoomCodeAndAddress(studyRoom);
                if(tbStudyroom.get(0) != null){
                    roomCode = tbStudyroom.get(0).getRoomCode();
                    address = tbStudyroom.get(0).getAddress();
                }
                resultTJ.setRoomCode(roomCode);
                resultTJ.setAddress(address);
            }
            resultTJList.add(resultTJ);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(tbBespoke.getBespokeStudyDate());
            date = date +" "+ tbBespoke.getStartTime() + "-" + tbBespoke.getEndTime();

            // 紧接着将医嘱明细信息存入医嘱明细表

            for(TbOrderDetail orderDetail:tbOrderDetailList){
                //医嘱ID,用于回写预约信息
                String tbOrderDetailID = "";
                //申请单号,用于回写预约信息
                String tbOrderDetailRequestNo = "";
                //New Code
                String tbOrderDetailStudyPart = "";
                String tbOrderDetailStudyPartCode = "";

                orderDetail.setPartId(null);
                orderDetail.setTimeCoefficient(null);
                orderDetail.setWarning(null);
                orderDetail.setBespokeID(id);
                Integer integer = tbOrderDetailMapper.insertTbOrderDetail(orderDetail);
                if (integer < 1) {
                    return new R(1,null,tbBespoke.getName() + "预约失败");

                }
                tbOrderDetailID = orderDetail.getOrdRowID();
                tbOrderDetailRequestNo = orderDetail.getRequestNo();

                if(orderDetail.getHISStudyPart() != null){
                    tbOrderDetailStudyPart = orderDetail.getHISStudyPart();
                }
                if(orderDetail.getHISStudyPartCode() != null){
                    tbOrderDetailStudyPartCode = orderDetail.getHISStudyPartCode();
                }
                /**
                 * <histype>1 门诊 2住院 3体检</histype>
                 * <RequestNo>申请单号</RequestNo>
                 * <OrdRowID>医嘱ID/OrdRowID>
                 * <HISStudyPart>部位名称</HISStudyPart>
                 * <HISStudyPartCode>部位ID</HISStudyPartCode>
                 * <ConsultingRoomCode>诊室代码</ ConsultingRoomCode >
                 * <ConsultingRoomName>诊室名称</ ConsultingRoomName >
                 * <BespokeNO>预约单编号</BespokeNO>
                 * <BespokeStudySpan>预约时间段2020-03-11  08:00-08:59</BespokeStudySpan>
                 * <BespokeArriveTime>报到时间2020-03-11 07:45:00</BespokeArriveTime >
                 * <BespokeNotice>注意事项</BespokeNotice>
                 * <UserID>操作人ID</UserID>
                 */
                String sHisType = "1";
                if (tbBespoke.getHospitalizeType().equals("Y")) {
                    sHisType = "2";
                }
                else if (tbBespoke.getHospitalizeType().equals("E")) {
                    sHisType = "3";
                }
                String parm=
                        "<request>\n" +
                                "<histype>"+sHisType+"</histype>" +
                                "<RequestNo>"+tbOrderDetailRequestNo+"</RequestNo>"+
                                "<OrdRowID>"+tbOrderDetailID+"</OrdRowID>"+
                                "<HISStudyPart>"+tbOrderDetailStudyPart+"</HISStudyPart>"+
                                "<HISStudyPartCode>"+tbOrderDetailStudyPartCode+"</HISStudyPartCode>"+
                                "<ConsultingRoomCode>"+roomCode+"</ConsultingRoomCode>"+
                                "<ConsultingRoomName>"+studyRoom+"</ConsultingRoomName>"+
                                "<BespokeNO>"+bespokeNo+"</BespokeNO>"+
                                "<BespokeStudySpan>"+date+"</BespokeStudySpan>"+
                                "<BespokeArriveTime>"+tbBespoke.getsArriveDate()+"</BespokeArriveTime >"+
                                "<BespokeNotice>"+resultTJ.getWarning()+"</BespokeNotice>"+
                                "<UserID>"+tbUser.getUserCode()+"</UserID>" +
                                "</request>\n" ;
                str+=parm;
            }
        }

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(webService.getUrl());
        //预约成功后回写预约信息
        String sParam = "<root>\n" + str + "</root>";
        log.info("回写预约信息参数   "+sParam);
        System.out.println("回写预约信息参数11111111111111111111111111111111111111111111111111111111111111111111   "+sParam);
        Object[] obj;
        try {
            obj= client.invoke("HISInterface", "SendBespokeInfo",sParam);
            log.info("返回数据2:" + obj[0]);
        } catch (Exception e) {
            log.error("回写预约信息执行错误");
        }

        // 清除号源
        List<TbBespokeresLsb> tbBespokeresLsbList;
        // 获取登录人员选择的部位临时号院表数据
        tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                // 删除号源临时表数据
                tbBespokeresLsbService.daleteUserID(tbBespokeresLsb.getId());
            }
        }
        // 预约保存的时候，如果vHISJPGFile这个字段不为空保存到新建的tb_Order_file,RequestNo相同的只需要保存一次

        for(GetRecommendationVo grv:getRecommendationVoList){
            TbOrderFile tbOrderFile = new TbOrderFile();
            tbOrderFile.setBespokeID(grv.getTbBespoke().getId());
            List<TbOrderDetail> tbOrderDetailList = grv.getTbOrderDetailList();
            for (TbOrderDetail td:tbOrderDetailList){
                if(StrUtils.isNullOrEmpty(td.getvHISJPGFile())){
                    continue;
                }
                tbOrderFile.setvHISJPGFile(td.getvHISJPGFile());
                tbOrderFile.setOperatDate(new Date());
                tbOrderFileMapper.insertTbOrderFile(tbOrderFile);
            }
        }
        return new R(resultTJList);
    }

    /**
     * @param hospitalizeNo    住院号/门诊号/体检号
     * @param bespokeStatus    预约状态
     * @param feeStatus        缴费状态
     * @param studyStatus      检查状态
     * @param requestDeptCode  申请科室代码
     * @param startDate        时间段开始时间
     * @param endDate          时间段结束时间
     * @param bespokeStartDate 预约时间（开始）（暂时不需要）
     * @param bespokeEndDate   预约时间（结束）（暂时不需要）
     * @param hospitalName     院区名称
     * @param deptName         登录人员科室名称
     * @return 预约集合
     */
    @Override
    public List<TbBespoke> finAll(String hospitalizeNo, Integer bespokeStatus, Integer feeStatus,
                                  Integer studyStatus, Integer requestDeptCode, String startDate,
                                  String endDate, String bespokeStartDate, String bespokeEndDate,
                                  String hospitalName, String deptName, String bespokeNo,
                                  String modality, String studyRoom, String startTime, String endTime,
                                  String idtype, String id, Integer resType,String requestDeptCode1) {

        if ((null == hospitalName) || ("".equals(hospitalName))) {
            throw new MyException("医院参数必传参数，不能为空，请重传");
        }
        List<TbBespoke> tbBespokeList;
        if (StrUtils.isNullOrEmpty(bespokeStartDate, bespokeEndDate)) {
            String date = null;
//            if ((null == idtype) || ("".equals(idtype))) {
//                //  PC端如果没传
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                date = sdf.format(new Date());
//                date += " 00:00:00";
//            }
            if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
                tbBespokeList = tbBespokeMapper.selectTbBespoke(hospitalizeNo, bespokeStatus, feeStatus, studyStatus,
                        requestDeptCode, startDate, endDate, bespokeStartDate, bespokeEndDate, hospitalName, date,
                        bespokeNo, modality, studyRoom, startTime, endTime, idtype, id, resType, requestDeptCode1);
            } else {
                tbBespokeList = tbBespokeMapper.selectTbBespokeByDeptName(hospitalizeNo, bespokeStatus, feeStatus, studyStatus,
                        requestDeptCode, startDate, endDate, bespokeStartDate, bespokeEndDate, hospitalName, date,
                        deptName, bespokeNo, modality, studyRoom, startTime, endTime, idtype, id, resType, requestDeptCode1);
            }
        } else {
            bespokeStartDate += " 00:00:00";
            bespokeEndDate += " 23:59:59";
            if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
                tbBespokeList = tbBespokeMapper.selectTbBespoke(hospitalizeNo, bespokeStatus, feeStatus, studyStatus,
                        requestDeptCode, startDate, endDate, bespokeStartDate, bespokeEndDate, hospitalName, null,
                        bespokeNo, modality, studyRoom, startTime, endTime, idtype, id, resType, requestDeptCode1);
            } else {
                tbBespokeList = tbBespokeMapper.selectTbBespokeByDeptName(hospitalizeNo, bespokeStatus, feeStatus, studyStatus,
                        requestDeptCode, startDate, endDate, bespokeStartDate, bespokeEndDate, hospitalName, null,
                        deptName, bespokeNo, modality, studyRoom, startTime, endTime, idtype, id, resType, requestDeptCode1);
            }
        }
        if(tbBespokeList.isEmpty()){
            return tbBespokeList;
        }
        /**
        // 所有的id集合
        List<Integer> idLists = tbBespokeList.stream().map(TbBespoke::getId).collect(Collectors.toList());
        List<TbOrderDetail> tbOrderDetails = tbOrderDetailMapper.selectByIds(idLists);
        for (int i = 0; i < tbBespokeList.size(); i++) {
            TbBespoke tbBespoke = tbBespokeList.get(i);
            TbMethodPart tbMethodPart = new TbMethodPart();
            tbMethodPart.setModality(tbBespoke.getModality());
            tbMethodPart.setHISStudyProjectCode(tbBespoke.getHISStudyProjectCode());
            tbMethodPart.setHISStudyProject(tbBespoke.getHISStudyProject());
            tbMethodPart.setHISStudyPartCode(tbBespoke.getHISStudyPartCode());
            tbMethodPart.setHospitalName(tbBespoke.getHospitalName());
            tbMethodPart.setHISStudyPart(tbBespoke.getHISStudyPart());
            List<TbMethodPart> tbMethodPartList = tbMethodPartMapper.selectCondition(tbMethodPart);
            if (tbMethodPartList != null && tbMethodPartList.size() > 0) {
                tbBespoke.setPartID(tbMethodPartList.get(0).getId());
            }
            List<TbOrderDetail> detailList = tbOrderDetails.stream().filter(m -> m.getBespokeID().equals(tbBespoke.getId())).collect(Collectors.toList());
            if(!detailList.isEmpty()){
                tbBespoke.setTbOrderDetails(detailList);
                List<String> hisStudyProjects = detailList.stream().map(TbOrderDetail::getHISStudyProject).distinct().collect(Collectors.toList());
                String hisStudyProject="";
                for(int m=0;m<=hisStudyProjects.size()-1;m++){
                    if(m==(hisStudyProjects.size()-1)){
                        hisStudyProject=hisStudyProject+hisStudyProjects.get(m);
                    }else {
                        hisStudyProject=hisStudyProject+hisStudyProjects.get(m)+",";
                    }
                }
                tbBespoke.setHISStudyProject(hisStudyProject);
            }
            //tbOrderDetailMapper
        }
        */
        return tbBespokeList;
    }

    /**
     * 预约修改
     *
     * @param tbBespoke 预约对象
     * @return 操作返回码
     */
    @Override
    public Integer updateTbBespoke(TbBespoke tbBespoke) {
        Integer result = tbBespokeMapper.updateTbBespoke(tbBespoke);
        if (result < 1) {
            throw new MyException("修改失败");
        }
        return tbBespoke.getId();
    }

    /**
     * 获取取消预约次数
     *
     * @param bespokeEntity
     * @return 操作码
     */
    @Override
    @Transactional
    public R<List<BespokeEntity>> selectCancelBespoke(BespokeEntity bespokeEntity) {
        List<BespokeEntity> lists = new ArrayList<>();

        if(bespokeEntity.getId() == null || "".equals(bespokeEntity.getId())){
            return new R(1, "", "未选择检查项目！");
        }
        String id = bespokeEntity.getId();
        String idtype = bespokeEntity.getIdType();
        //执行科室集合
        List<String> execDeptList = bespokeEntity.getExecDeptList();
        for (String execDept : execDeptList) {
            BespokeEntity be = new BespokeEntity();
            //移动端取消预约
            //bespokeNum:数据库配置最大可预约次数
            String configKey1 = "CancelBespokeTimes";
            int bespokeNum = Integer.parseInt(tbBespokeMapper.getBespokeNum(configKey1));
            String configKey2 = "CancelBespokeDate";
            String cancelDate = tbBespokeMapper.getBespokeNum(configKey2);
            //bespokeTimes:已取消预约的次数
            Integer bespokeTimes = tbBespokeMapper.getBespokeTimes(id, idtype, cancelDate, execDept);
            //0:可预约；1：不可预约
            if (bespokeTimes >= bespokeNum) {
               //超过最大取消预约次数，无法继续进行预约
                be.setId("1");
            }else{
                be.setId("0");
            }
            be.setExecDept(execDept);
            lists.add(be);
        }
        return new R(lists);
    }
    /**
     * 取消预约
     *
     * @param id 预约id
     * @return 操作码
     */
    @Override
    @Transactional
    public R<Integer> delete(Integer id,String userID,String uid,String idtype,String execDept) {
        if(uid != null && !"".equals(uid)){
            if(execDept == null){
                log.error("取消预约执行科室参数为空");
            }
            //移动端取消预约
            //bespokeNum:数据库配置最大可预约次数
            String configKey1 = "CancelBespokeTimes";
            int bespokeNum = Integer.parseInt(tbBespokeMapper.getBespokeNum(configKey1));
            String configKey2 = "CancelBespokeDate";
            String cancelDate = tbBespokeMapper.getBespokeNum(configKey2);

            //bespokeTimes:已取消预约的次数
            Integer bespokeTimes = tbBespokeMapper.getBespokeTimes(uid, idtype, cancelDate,execDept);

            if(bespokeTimes >= bespokeNum){
                return new R(1,"","超过最大取消次数，无法继续取消预约");
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("取消开始：" + df.format(new Date()));
        TbBespoke tbBespoke = new TbBespoke();
        tbBespoke.setId(id);
        tbBespoke.setBespokeStatus(2);
        Integer result = tbBespokeMapper.updateTbBespoke(tbBespoke);
        if (result < 1) {
            return new R(1,"","取消失败");
        }
        //移动端操作成功保存到操作记录表
        if(uid != null && !"".equals(uid)){
            ConfigOperate co = new ConfigOperate();
            co.setOperateType(1);
            co.setBespokeID(id);
            co.setOperateDate(new Date());
            tbBespokeMapper.insertOperateRecord(co);
        }

        TbBespoke tbBespokes = tbBespokeMapper.selectById(id);
        // 取消预约时判断是否已报到，如果已报到先退费再取消，如果未报到则直接取消。
        // 检查状态 0:未报到 1：已报到 2：已检查
        if(tbBespokes.getStudyStatus().equals(1)){
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(webService.getUrl());
            if(tbBespoke.getHospitalizeType().equals("N")){
                tbBespoke.setHospitalizeType("1");
            }
            else if(tbBespoke.getHospitalizeType().equals("Y")){
                tbBespoke.setHospitalizeType("2");
            }
            else if(tbBespoke.getHospitalizeType().equals("E")){
                tbBespoke.setHospitalizeType("3");
            }else {
                tbBespoke.setHospitalizeType(null);
            }
            List<TbOrderDetail> tbOrderDetails = tbOrderDetailMapper.selectDistinctByBespokeID(tbBespoke.getId());
            if(tbOrderDetails.isEmpty()){
                log.error("医嘱为空");
                return new R(1,"","取消医嘱执行失败");
            }
            String str="";
            for(TbOrderDetail tbOrderDetail :tbOrderDetails){
                String parm=
                        "<request>\n" +
                                "<histype>" + tbBespoke.getHospitalizeType()+"</histype> \n" +
                                "<RequestNo>" + tbOrderDetail.getRequestNo() + "</RequestNo>\n" +
                                "<BespokeNo>" + tbOrderDetail.getBespokeID() + "</BespokeNo>\n" +
                                "<OrdRowID>" + tbOrderDetail.getOrdRowID() + "</OrdRowID>\n" +
                                "<UserID>" + userID + "</UserID>\n" +
                                "</request>\n" ;
                str+=parm;
            }
            String sParam = "<root>\n" +
                    str+
                    "</root>";
            log.info("医嘱参数   "+sParam);
            Object[] obj;
            try {
                obj= client.invoke("HISInterface", "CancelRequestInfo", sParam);
                log.info("返回数据3:" + obj[0]);
            } catch (Exception e) {
                log.error("取消医嘱执行错误");
            }
        }
//        TbBespokeres tbBespokeres = new TbBespokeres();
//        if (tbBespokes.getGroupType() == 1) {
//            // 检查方法
//            tbBespokeres.setGroupType(1);
//            tbBespokeres.setGroupName(tbBespokes.getStudyMethod());
//            tbBespokeres.setBespokeDate(tbBespokes.getBespokeStudyDate());
//            tbBespokeres.setStartTime(tbBespokes.getStartTime());
//            tbBespokeres.setEndTime(tbBespokes.getEndTime());
//            tbBespokeres.setHospitalName(tbBespoke.getHospitalName());
//
//        } else if (tbBespokes.getGroupType() == 2) {
//            // 检查房间
//            tbBespokeres.setGroupType(2);
//            tbBespokeres.setGroupName(tbBespokes.getStudyRoom());
//            tbBespokeres.setBespokeDate(tbBespokes.getBespokeStudyDate());
//            tbBespokeres.setStartTime(tbBespokes.getStartTime());
//            tbBespokeres.setEndTime(tbBespokes.getEndTime());
//            tbBespokeres.setHospitalName(tbBespoke.getHospitalName());
//        }
//        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectBespokeres(tbBespokeres);
        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectBespokeresByID(tbBespokes.getResID());
        System.out.println("开始回滚号源：" + df.format(new Date()));
        if (tbBespokeresList != null) {
            for (int i = 0; i < tbBespokeresList.size(); i++) {
                HgBespoke hgBespoke = new HgBespoke();
                hgBespoke.setResID(tbBespokeresList.get(i).getId());
                hgBespoke.setHospitalType(tbBespokes.getHospitalizeType());
                hgBespoke.setPatientNum(0 - tbBespokes.getTimeCoefficient());
                tbBespokeresMapper.updateBespokeAddition(hgBespoke);
                //记录号源日志
                LogEntity logEntity = new LogEntity();
                logEntity.setResID(String.valueOf(tbBespokeresList.get(i).getId()));
                logEntity.setOperaction("取消预约释放号源（预约成功后）");
                logEntity.setHospitalType(tbBespokes.getHospitalizeType());
                logEntity.setPatientNum(String.valueOf(0 - tbBespokes.getTimeCoefficient()));
                logEntity.setDate(new Date());
                tbBespokeresMapper.insertTbBespokeresLog(logEntity);
            }
        }
        System.out.println("取消完毕：" + df.format(new Date()));
        return new R(id);
    }

    /**
     * 预约变更(不用)
     *
     * @param id    预约id
     * @param resid 预约号源主键ID
     * @return 结果
     */
    @Override
    @Transactional
    public ResultBG change(Integer id, Integer resid) {
        TbBespoke tbBespoke = tbBespokeMapper.selectById(id);
        String GroupName = "";
        tbBespoke.setBespokeDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeDate())));
        if (tbBespoke.getGroupType() == 1) {
            GroupName = tbBespoke.getStudyMethod();
        } else {
            GroupName = tbBespoke.getStudyRoom();
        }
        TbBespokeres tbBespokeres = tbBespokeresMapper.selectById(resid);
        java.sql.Date date1 = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(tbBespokeres.getBespokeDate()));
        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectFind(tbBespoke.getModality(), tbBespoke.getGroupType(), GroupName, date1, tbBespoke.getStartTime(), tbBespoke.getEndTime(), tbBespoke.getHospitalName());
        if (tbBespokeresList != null && tbBespokeresList.size() > 0) {
            HgBespoke hgBespoke = new HgBespoke();
            hgBespoke.setResID(tbBespokeresList.get(0).getId());
            hgBespoke.setHospitalType(tbBespoke.getHospitalizeType());
            hgBespoke.setPatientNum(0 - tbBespoke.getTimeCoefficient());
            //NewOneCode
            tbBespokeresMapper.updateBespokeAddition(hgBespoke);

            LogEntity logEntity2 = new LogEntity();
            logEntity2.setResID(String.valueOf(tbBespokeresList.get(0).getId()));
            logEntity2.setOperaction("变更预约回滚号源");
            logEntity2.setHospitalType(tbBespoke.getHospitalizeType());
            logEntity2.setPatientNum(String.valueOf(0 - tbBespoke.getTimeCoefficient()));
            logEntity2.setDate(new Date());
            //记录号源日志
            tbBespokeresMapper.insertTbBespokeresLog(logEntity2);
        } else {
            throw new MyException("未找到对应的预约，无法回滚，变更失败");
        }

        // 预约检查时间
        tbBespoke.setBespokeStudyDate(tbBespokeres.getBespokeDate());
        // 预约时间
        tbBespoke.setBespokeDate(new Date());
        // 预约检查开始时段
        tbBespoke.setStartTime(tbBespokeres.getStartTime());
        // 预约检查结束时段
        tbBespoke.setEndTime(tbBespokeres.getEndTime());
        if (tbBespokeres.getGroupType() == 1) {
            // 检查方法分组
            tbBespoke.setGroupType(tbBespokeres.getGroupType());
            // 检查方法
            tbBespoke.setStudyMethod(tbBespokeres.getGroupName());
            // 检查房间
            tbBespoke.setStudyRoom("");
        } else {
            // 检查房间分组
            tbBespoke.setGroupType(tbBespokeres.getGroupType());
            // 检查方法
            tbBespoke.setStudyMethod("");
            // 检查房间
            tbBespoke.setStudyRoom(tbBespokeres.getGroupName());
        }
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        // 预约员工号
        tbBespoke.setBespokeOpreatorCode(tbUser.getUserID());
        // 预约员姓名
        tbBespoke.setBespokeOpreator(tbUser.getUserName());
        tbBespokeMapper.updateTbBespoke(tbBespoke);
        ResultBG resultBG = new ResultBG();

        resultBG.setBespokeDate(new SimpleDateFormat("yyyy-MM-dd").format(tbBespokeres.getBespokeDate()));
        resultBG.setStartTime(tbBespokeres.getStartTime());
        resultBG.setEndTime(tbBespokeres.getEndTime());
        return resultBG;
    }


    public static Date date(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        try {
            Date parse = sdf.parse(format);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 预约变更
     *
     * @param id    预约id
     * @param resid 预约号源主键ID
     * @return 结果
     */
    @Override
    @Transactional
    public R<ResultBG> changeNew(Integer id, Integer resid,String uid,String idtype) {
        if(uid != null && !"".equals(uid)){
            //移动端变更预约
            //changeBespokeTimes:已变更预约的次数
            Integer changeBespokeTimes = tbBespokeMapper.getChangeBespokeTimes(uid, idtype);
            //bespokeNum:数据库配置最大可预约次数
            String configKey = "ChangeBespokeTimes";
            int bespokeNum = Integer.parseInt(tbBespokeMapper.getBespokeNum(configKey));
            if(changeBespokeTimes >= bespokeNum){
                return new R(1,"","超过最大取消次数，无法继续变更预约");
            }
        }

        TbBespoke tbBespoke = tbBespokeMapper.selectById(id);

        String GroupName = "";
        tbBespoke.setBespokeDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeDate())));
        if (tbBespoke.getGroupType() == 1) {
            GroupName = tbBespoke.getStudyMethod();
        } else {
            GroupName = tbBespoke.getStudyRoom();
        }
        TbBespokeres tbBespokeres = tbBespokeresMapper.selectById(resid);
        // 根据条件查询预约号源是否存在
        //java.sql.Date date1 = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(tbBespokeres.getBespokeDate()));
//        java.sql.Date date2 = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(tbBespoke.getBespokeStudyDate()));
//        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectFindNew
//                (tbBespoke.getModality(), tbBespoke.getGroupType(), GroupName,
//                        date2, tbBespoke.getStartTime(),
//                        tbBespoke.getEndTime(), tbBespoke.getHospitalName());
        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectBespokeresByID(tbBespoke.getResID());

        // 修改
        if (tbBespokeresList != null && tbBespokeresList.size() > 0) {
            HgBespoke hgBespoke = new HgBespoke();
            hgBespoke.setResID(tbBespokeresList.get(0).getId());
            hgBespoke.setHospitalType(tbBespoke.getHospitalizeType());
            hgBespoke.setPatientNum(0 - tbBespoke.getTimeCoefficient());
            //这里进行号源回滚
            tbBespokeresMapper.updateBespokeAddition(hgBespoke);
            //记录号源日志
            LogEntity logEntity1 = new LogEntity();
            logEntity1.setResID(String.valueOf(tbBespokeresList.get(0).getId()));
            logEntity1.setOperaction("变更预约回滚号源");
            logEntity1.setHospitalType(tbBespoke.getHospitalizeType());
            logEntity1.setPatientNum(String.valueOf(0 - tbBespoke.getTimeCoefficient()));
            logEntity1.setDate(new Date());
            //记录号源日志
            tbBespokeresMapper.insertTbBespokeresLog(logEntity1);

        } else {
            return new R(1,"","未找到对应的预约，无法回滚，变更失败");
        }
        // 预约检查时间
        tbBespoke.setBespokeStudyDate(tbBespokeres.getBespokeDate());
        // 预约时间
        tbBespoke.setBespokeDate(new Date());
        // 预约检查开始时段
        tbBespoke.setStartTime(tbBespokeres.getStartTime());
        // 预约检查结束时段
        tbBespoke.setEndTime(tbBespokeres.getEndTime());
        if (tbBespokeres.getGroupType() == 1) {
            // 检查方法分组
            tbBespoke.setGroupType(tbBespokeres.getGroupType());
            // 检查方法
            tbBespoke.setStudyMethod(tbBespokeres.getGroupName());
            // 检查房间
            tbBespoke.setStudyRoom("");
        } else {
            // 检查房间分组
            tbBespoke.setGroupType(tbBespokeres.getGroupType());
            // 检查方法
            tbBespoke.setStudyMethod("");
            // 检查房间
            tbBespoke.setStudyRoom(tbBespokeres.getGroupName());
        }
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        // 预约员工号
        tbBespoke.setBespokeOpreatorCode(tbUser.getUserID());
        // 预约员姓名
        tbBespoke.setBespokeOpreator(tbUser.getUserName());

        /**
         * 精准检查房间预约时间
         */
        //  若提交的预约是检查房间，则需要将预约时间精准到分钟（1：按检查方法分组； 2：按检查房间分组）
        TbStudyroom tbStudyrooms = tbStudyroomMapper.selectWhetherPreciseTime(tbBespoke.getExecDept(), tbBespoke.getModality(),
                tbBespoke.getStudyRoom(), tbBespoke.getHospitalName());

        if ((tbBespoke.getGroupType().equals(2)) && (!(null == tbStudyrooms))) {
            //  得到检查房间检查耗时数
            Integer timeCoefficient = tbBespoke.getTimeCoefficient();
            //  查询提交条件已预约的检查预约,execDept(执行科室),studyRoom(检查房间),groupType(分组方式1：按检查方法分组； 2：按检查房间分组)
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String date = sdf.format(tbBespoke.getBespokeStudyDate());
            List<TbBespoke> tbBespokes = tbBespokeMapper.find(tbBespoke.getExecDept(), tbBespoke.getStudyRoom(),
                    tbBespoke.getModality(), tbBespoke.getHospitalName(),
                    tbBespoke.getStartTime(), tbBespoke.getEndTime(), date, 2)
                    .stream().sorted(Comparator.comparing(TbBespoke::getStartTime)).collect(Collectors.toList());
            // 若预约集合为空，就无须考虑空挡时间插入
            if (tbBespokes.isEmpty()) {
                String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                tbBespoke.setEndTime(startTimeNew);
            }
            // 若预约集合大小为1，看那个时间段空缺就插入到那个时间段
            else if (tbBespokes.size() == 1) {
                if (tbBespokes.get(0).getStartTime().equals(tbBespokeres.getStartTime())) {
                    String endTime = tbBespokes.get(0).getEndTime();
                    tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                    String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                    tbBespoke.setEndTime(startTimeNew);
                } else {
                    String startTime = tbBespokes.get(0).getStartTime();
                    int difference = TimeUtils.calculateTime(tbBespoke.getStartTime(), startTime);
                    if (difference > 1) {
                        String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    } else {
                        String startTimeNew = TimeUtils.addMinute(startTime, timeCoefficient);
                        tbBespoke.setEndTime(startTimeNew);
                    }
                }
            } else if (tbBespokes.size() > 1) {
                if (!tbBespokes.get(0).getStartTime().equals(tbBespoke.getStartTime())) {
                    String endTime = tbBespokes.get(0).getEndTime();
                    tbBespoke.setStartTime(TimeUtils.addMinute(endTime, 1));
                    String startTimeNew = TimeUtils.addMinute(TimeUtils.addMinute(endTime, 1), timeCoefficient);
                    tbBespoke.setEndTime(startTimeNew);
                } else {
                    for (int j = 0; j <= tbBespokes.size() - 1; j++) {
                        if (!(j == (tbBespokes.size() - 1))) {
                            String endTime = tbBespokes.get(j).getEndTime();
                            String startTime = tbBespokes.get(j + 1).getStartTime();
                            int difference = TimeUtils.calculateTime(endTime, startTime);
                            if (difference >= 2) {
                                String endTime2 = tbBespokes.get(j).getEndTime();
                                tbBespoke.setStartTime(TimeUtils.addMinute(endTime2, 1));
                                String startTimeNew = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                                tbBespoke.setEndTime(startTimeNew);
                                break;
                            }
                        } else {
                            if (!(j == (tbBespokes.size() - 1))) {
                                continue;
                            }
                            String endTime3 = tbBespokes.get(tbBespokes.size() - 1).getEndTime();
                            tbBespoke.setStartTime(TimeUtils.addMinute(endTime3, 1));
                            String startTimeNew2 = TimeUtils.addMinute(tbBespoke.getStartTime(), timeCoefficient);
                            tbBespoke.setEndTime(startTimeNew2);
                        }
                    }
                }
            }
        }
        tbBespoke.setResID(resid);
        tbBespokeMapper.updateTbBespoke(tbBespoke);
        // 清除号源
        List<TbBespokeresLsb> tbBespokeresLsbList ;
        if (tbUser != null) {
            // 获取登录人员选择的部位临时号院表数据
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
            if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
                for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                    // 删除号源临时表数据
                    tbBespokeresLsbService.daleteUserID(tbBespokeresLsb.getId());
                }
            }
        }
        //移动端操作成功保存到操作记录表
        if(uid != null && !"".equals(uid)){
            ConfigOperate co = new ConfigOperate();
            co.setOperateType(2);
            co.setBespokeID(id);
            co.setOperateDate(new Date());
            tbBespokeMapper.insertOperateRecord(co);
        }

        ResultBG resultBG = new ResultBG();
        resultBG.setBespokeDate(new SimpleDateFormat("yyyy-MM-dd").format(tbBespokeres.getBespokeDate()));
        resultBG.setStartTime(tbBespoke.getStartTime());
        resultBG.setEndTime(tbBespoke.getEndTime());
        return new R(resultBG);
    }

    /**
     * 预约系统人工报到
     * @param id
     * @return
     */
    @Override
    @Transactional
    public R<TbBespoke> selfReport(Integer id,String userID) {
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        // 根据预约id查询预约对象
        TbBespoke tbBespoke = tbBespokeMapper.findById(id);
        // bespoke中ID值调PROC_GetPacsSelfRegister存储过程查询出报到调用RIS提供的webservice接口入参
        Map<String, Object> params = new HashMap<>(5);
        params.put("ID",id+"");
        // tbBespoke.getExecDeptCode()
        params.put("ExecModality",tbBespoke.getModality());
        params.put("BespokeNo",tbBespoke.getBespokeNo());
        List<GetPacsSelfRegister> pacsSelfRegister = tbBespokeMapper.getPacsSelfRegister(params);
        if(pacsSelfRegister.isEmpty()){
            log.error("pacsSelfRegister为空");
            System.out.println("自助报到失败");
            return new R(1,"","自助报到失败");

        }
        GetPacsSelfRegister getPacsSelfRegister = pacsSelfRegister.get(0);

        String xmlData = getPacsSelfRegister.getXMLData();
        // 得到webService入参调自助报到接口
        //  创建动态客户端
        log.info("xml的测试呀");
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(webService.getUrl());
        Object[] objects;
        List<QueueInfo> getQueueInfos = new ArrayList<>();
        try {
            //  invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("RISInterface", "SelfRegister", xmlData);
            log.info("返回数据4:" + objects[0]);
            String bojectString = objects[0].toString();
            String returns = bojectString.substring(bojectString.indexOf("<return>"), bojectString.indexOf("</return>") + 9);
            //log.info(returns);
            if (returns.contains("0")) {
                log.info("his数据不存在");
                System.out.println("his数据不存在");
                return new R(1,"","his数据不存在");
            }
            bojectString = bojectString.substring(bojectString.indexOf("<QueueInfo>"), bojectString.indexOf("</QueueInfo>") + 12);
            log.info(bojectString);
            XStream xstream = new XStream();
            xstream.ignoreUnknownElements();
            xstream.autodetectAnnotations(true);
            xstream.processAnnotations(QueueInfo.class);
            // webservice返回的数据转化为对象
            QueueInfo queueInfo = (QueueInfo) xstream.fromXML(bojectString);
            // RIS排队号
            tbBespoke.setQueueNo(queueInfo.getRisQueueNo());
            // RIS检查号
            tbBespoke.setRisExamNo(queueInfo.getRisStudyNo());
            // RIS检查唯一号
            tbBespoke.setRisAccessionNumber(queueInfo.getRisStudyIdentity());
            // 等待人数
            tbBespoke.setRisWaitNum(queueInfo.getRisWaitNum());
            // 改变检查状态
            tbBespoke.setStudyStatus(1);
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //更新报到时间
            tbBespoke.setArriveDate(new Date());
            Integer integer = tbBespokeMapper.updateTbBespoke(tbBespoke);
            if(integer<1){
                System.out.println("自助报到失败");
                return new R(1,"","自助报到失败");
            }else {
                /*@姜皓 确费退费的节点改下。
                1、确费接口在报到成功后调用。
                2、取消预约时判断是否已报到，如果已报到先退费再取消，如果未报到则直接取消。*/
                log.info("自助报到成功");

                List<TbOrderDetail> tbOrderDetails = tbOrderDetailMapper.selectDistinctByBespokeID(tbBespoke.getId());
                if(tbOrderDetails.isEmpty()){
                    log.error("医嘱为空");
                    System.out.println("取消医嘱执行失败");
                    return new R(1,"","取消医嘱执行失败");
                }

                /*
                 * 就诊方式 住院：Y 门诊:N 体检:E
                 * <histype>1 门诊 2住院 3体检</histype>
                 */
                String sHisType = "1";
                if (tbBespoke.getHospitalizeType().equals("Y")) {
                    sHisType = "2";
                }
                else if (tbBespoke.getHospitalizeType().equals("E")) {
                    sHisType = "3";
                }
                String str = "";
                for(TbOrderDetail tbOrderDetail :tbOrderDetails){
                    String parm =
                            "<request>\n" +
                                    "<histype>" + sHisType +"</histype>\n" +
                                    "<RequestNo>" + tbOrderDetail.getRequestNo() + "</RequestNo>\n" +
                                    "<BespokeID>" + tbOrderDetail.getBespokeID() + "</BespokeID>\n" +
                                    "<OrdRowID>" + tbOrderDetail.getOrdRowID() + "</OrdRowID>\n" +
                                    "<UserID>" + userID + "</UserID >\n" +
                                    "</request>\n" ;
                    str+=parm;

                }
                String sParam = "<root>\n" +
                        str+
                        "</root>";
                log.info("医嘱参数   "+sParam);
                Object[] obj;
                try {
                    obj= client.invoke("HISInterface", "RegRequestInfo", sParam);
                    log.info("返回数据5:" + obj[0]);
                } catch (Exception e) {
                    return new R(tbBespoke);
                }
            }
            return new R(tbBespoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new R(tbBespoke);
    }

    /**
     * 预约系统人工报到
     * @param reportInfo
     * @return
     */
    @Override
    @Transactional
    public R<List<TbBespoke>> quickReport(ReportInfo reportInfo) {
        List<TbBespoke> lists = new ArrayList<>();

        String userID = reportInfo.getUserID();
        List<String> ids = reportInfo.getIds();
        for (String id : ids) {
            System.out.println("快速报到ID=" + id);
            R<TbBespoke> tbBespokeR = selfReport(Integer.parseInt(id), userID);
            if(tbBespokeR.getCode() != 1){
                //报到成功
                lists.add(tbBespokeR.getData());
            }
        }
        if(lists.size() == 0){
            //报到失败
            return new R(1,"","快速报到失败！");
        }
        return new R(lists);
    }



    /**
     * 自助确费
     * @param id
     * @return
     */
    @Override
    @Transactional
    public R<TbBespoke> regRequest(Integer id,String userID) {
        // 根据预约id查询预约对象
        TbBespoke tbBespoke = tbBespokeMapper.findById(id);

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(webService.getUrl());

        try {
                List<TbOrderDetail> tbOrderDetails = tbOrderDetailMapper.selectDistinctByBespokeID(tbBespoke.getId());
                if(tbOrderDetails.isEmpty()){
                    log.error("医嘱为空");
                    return new R(1,"","取消医嘱执行失败");
                }

                /*
                 * 就诊方式 住院：Y 门诊:N 体检:E
                 * <histype>1 门诊 2住院 3体检</histype>
                 */
                String sHisType = "1";
                if (tbBespoke.getHospitalizeType().equals("Y")) {
                    sHisType = "2";
                }
                else if (tbBespoke.getHospitalizeType().equals("E")) {
                    sHisType = "3";
                }
                String str = "";
                for(TbOrderDetail tbOrderDetail :tbOrderDetails){
                    String parm =
                            "<request>\n" +
                                    "<histype>" + sHisType +"</histype>\n" +
                                    "<RequestNo>" + tbOrderDetail.getRequestNo() + "</RequestNo>\n" +
                                    "<BespokeID>" + tbOrderDetail.getBespokeID() + "</BespokeID>\n" +
                                    "<OrdRowID>" + tbOrderDetail.getOrdRowID() + "</OrdRowID>\n" +
                                    "<UserID>" + userID + "</UserID >\n" +
                                    "</request>\n" ;
                    str+=parm;

                }
                String sParam = "<root>\n" +
                        str+
                        "</root>";
                log.info("医嘱参数   "+sParam);
                Object[] obj;
                obj= client.invoke("HISInterface", "RegRequestInfo", sParam);
                log.info("返回数据:" + obj[0]);
                String bojectString = obj[0].toString();
                String returns = "";
                String msg = "";
                if(bojectString.contains("<return>")){
                    returns = bojectString.substring(bojectString.indexOf("<return>"), bojectString.indexOf("</return>")+9);
                }else{
                    return new R(1,"","RegRequestInfo接口无数据返回");
                }

                if (returns.contains("0")) {
                    msg = bojectString.substring(bojectString.indexOf("<msg>"), bojectString.indexOf("</msg>")+6);
                    msg = msg.replace("<msg>", "");
                    msg = msg.replace("</msg>", "");
                    log.info(msg);
                    return new R(1,"",msg);
                }

        } catch (Exception e) {
            return new R(1,"","确费失败");
        }
//        return new R(tbBespoke);
        return new R(0,"","确费成功");
    }

    /**
     * 获取检查状态和排队号
     *
     * @param bespokeId       预约ID
     * @return 方案集合
     */
    @Override
    public String getBespokeStatusAndQueueNo(String bespokeId) {
        JsonObject jo = new JsonObject();
        TbBespoke tbBespoke = tbBespokeMapper.getBespokeStatusAndQueueNo(bespokeId);
        if(tbBespoke == null){
            jo.addProperty("flag", "1");
            jo.addProperty("msg", "error");
            return jo.toString();
        }
        Integer studyStatus = tbBespoke.getStudyStatus();
        String queueNo = tbBespoke.getQueueNo();

        jo.addProperty("flag", "0");
        jo.addProperty("msg", "ok");
        jo.addProperty("studyStatus", studyStatus);
        jo.addProperty("queueNo", queueNo);
        return jo.toString();
    }

}
