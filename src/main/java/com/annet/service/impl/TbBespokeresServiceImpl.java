package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.*;
import com.annet.entity.domain.GetBespokeRes;
import com.annet.entity.domain.GetRecommendation;
import com.annet.entity.operating.HgBespoke;
import com.annet.entity.operating.LogEntity;
import com.annet.mapper.TbBespokeresMapper;
import com.annet.mapper.TbBespokeresTemplateMapper;
import com.annet.mapper.TbConflictingPartMapper;
import com.annet.mapper.TbMethodPartMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbBespokeresLsbService;
import com.annet.service.TbBespokeresService;
import com.annet.utils.StrUtils;
import com.annet.utils.TimeUtils;
import com.annet.vo.GetRecommendationVo;
import com.annet.xmlEntity.GetRequestInfo;
import com.annet.yml.Family;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
@Slf4j
public class TbBespokeresServiceImpl extends ServiceImpl<TbBespokeresMapper, TbBespokeres> implements TbBespokeresService {

    private final TbBespokeresMapper tbBespokeresMapper;

    private final HttpSession httpSession;

    private final TbMethodPartMapper tbMethodPartMapper;

    private final TbBespokeresLsbService tbBespokeresLsbService;

    private final TbConflictingPartMapper tbConflictingPartMapper;

    private final TbBespokeresTemplateMapper tbBespokeresTemplateMapper;


    /**
     * @param tbBespokeres 预约号源
     * @return 预约号源集合
     */
    @Override
    public List<TbBespokeres> finAll(TbBespokeres tbBespokeres, String deptName) {
        if (tbBespokeres.getBespokeDate() == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                tbBespokeres.setBespokeDate(sdf.parse(sdf.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (tbBespokeres.getDeptName().equals(ConstantClass.ADMINISTRATOR)) {
            return tbBespokeresMapper.selectFinAll(tbBespokeres);
        } else {
            return tbBespokeresMapper.selectFindAll(tbBespokeres);
        }
    }

    /**
     * 添加号源
     *
     * @param tbBespokeres 预约号源
     * @return 号源对象
     */
    @Override
    public R<String> addTbBespokeres(TbBespokeres tbBespokeres) {
        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectCondition(tbBespokeres);
        if (tbBespokeresList != null && tbBespokeresList.size() > 0) {
            //throw new MyException("号源条件重复，添加预约号源失败");
            return new R(1,null,"号源条件重复，添加预约号源失败");
        }
        tbBespokeres.setOutPatientBespokeNum(0);
        tbBespokeres.setInPatientBespokeNum(0);
        tbBespokeres.setTotalBespokeNum(0);
        Integer result = tbBespokeresMapper.insertTbBespokeres(tbBespokeres);
//        if (result < 1) {
////            throw new MyException("添加预约号源失败");
////            return new R(1,null,"添加预约号源失败");
////        }
        return new R(tbBespokeres.getId().toString());
    }

    /**
     * 修改
     *
     * @param tbBespokeres 预约号源
     * @return 操作返回码
     */
    @Override
    public R<String> modifyTbBespokeres(TbBespokeres tbBespokeres) {
        List<TbBespokeres> tbBespokeresList = tbBespokeresMapper.selectCondition(tbBespokeres);
        if (tbBespokeresList != null && tbBespokeresList.size() > 0) {
            //throw new MyException("号源条件重复，修改预约号源失败");
            return new R(1,null,"号源条件重复，修改预约号源失败");
        }
        Integer result = tbBespokeresMapper.updateTbBespokeres(tbBespokeres);
        /**
        if (result < 1) {
            //throw new MyException("修改预约号源失败");
            return new R(1,null,"修改预约号源失败");
        }
         */
        return new R(tbBespokeres.getId().toString());
    }

    /**
     * 删除
     *
     * @param id 预约号源id
     * @return 操作返回码
     */
    @Override
    public R<String> deteleTbBespokeres(Integer id) {
        Integer result = tbBespokeresMapper.deleteTbBespokeres(id);
//        if (result < 1) {
//            throw new MyException("删除预约号源失败");
//            return new R(1,null,"删除预约号源失败");
//        }
        return new R(result.toString());
    }


    /**
     * 推荐预约方案(暂停)
     *
     * @param requestXml xml
     * @param preference 偏好
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param timeFrame  时段
     * @param hospitalName 园区名称
     * @return 推荐列表
     */
    //@Override
    /*public List<GetRecommendation> getRecommendation(String[] requestXml, Integer preference, String startDate,
                                                     String endDate, Integer timeFrame, String hospitalName) {
        //用于判断是否是重新生成推荐时间，用于回滚
        *//**
         Map<String,HgBespoke> hgBespokeMap = (Map<String,HgBespoke>)httpSession.getAttribute("hgBespokesMap");
         if(hgBespokeMap!=null){
         for (String key : hgBespokeMap.keySet()) {
         HgBespoke hgBespoke = hgBespokeMap.get(key);
         if(hgBespoke!=null){
         hgBespoke.setPatientNum(0 - hgBespoke.getPatientNum());
         tbBespokeresMapper.updateBespokeAddition(hgBespoke);
         }
         }
         }
         *//*
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        //回滚号源
        //List<TbBespokeresLsb> tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);

        List<Integer> partIds = new ArrayList<>();
        for (int i = 0; i < requestXml.length; i++) {
            String[] str = requestXml[i].split(":");
            String s = str[0];
            if (!StrUtils.isNullOrEmpty(s)) {
                partIds.add(Integer.parseInt(s));
            }
            continue;
        }
        List<TbBespokeresLsb> tbBespokeresLsbList;
        if (tbUser == null) {
            tbBespokeresLsbList = tbBespokeresLsbService.selectByPartId(partIds);
        } else {
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        }
        this.rollback(tbBespokeresLsbList);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));
        //调用存储过程获取推荐时间
        StringBuilder requestXmlString = new StringBuilder();
        requestXmlString.append("<Root>");
        for (int i = 0; i < requestXml.length; i++) {
            String[] str = requestXml[i].split(":");
            if (StrUtils.isNullOrEmpty(str[0], str[1])) {
                continue;
            }
            requestXmlString.append("<Record><PartID>" + str[0] + "</PartID><HospitalType>" + str[1] + "</HospitalType></Record>");
        }
        requestXmlString.append("</Root>");
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("RequestXml", requestXmlString.toString());
        params.put("Preference", preference);
        if (startDate != null && !startDate.equals("")) {
            params.put("StartDate", startDate);
        } else {
            params.put("StartDate", df.format(new Date()));
        }

        params.put("EndDate", endDate);
        params.put("TimeFrame", timeFrame);
        // 新增的院区区分
        params.put("HospitalName", hospitalName);
        //调用存储过程获取推荐时间
        List<GetRecommendation> getRecommendationsList = tbBespokeresMapper.getRecommendation(params);
        System.out.println(getRecommendationsList);
        //获取到推荐时间，进行号源占用
        //Map<String,HgBespoke> hgBespokesMap1 = new HashMap<String,HgBespoke>();
        for (int i = 0; i < getRecommendationsList.size(); i++) {

            if (getRecommendationsList.get(i).getGroupName() != null && !"".equals(getRecommendationsList.get(i).getGroupName())) {
                TbMethodPart tbMethodPart = tbMethodPartMapper.selectById(getRecommendationsList.get(i).getPartID());
                HgBespoke hgBespoke = new HgBespoke();
                hgBespoke.setResID(getRecommendationsList.get(i).getResID());
                hgBespoke.setHospitalType(getRecommendationsList.get(i).getHospitalType());
                hgBespoke.setPatientNum(tbMethodPart.getTimeCoefficient());
                tbBespokeresMapper.updateBespokeAddition(hgBespoke);
                //hgBespokesMap1.put(getRecommendationsList.get(i).getPartID().toString(),hgBespoke);
                //将数据存到临时表
                TbBespokeresLsb tbBespokeresLsb = new TbBespokeresLsb();
                tbBespokeresLsb.setHospitalType(getRecommendationsList.get(i).getHospitalType());
                String[] str = requestXml[i].split(":");
                tbBespokeresLsb.setPartID(Integer.parseInt(str[0]));
                tbBespokeresLsb.setPatientNum(tbMethodPart.getTimeCoefficient());
                tbBespokeresLsb.setResID(getRecommendationsList.get(i).getResID());
                if (!(tbUser == null)) {
                    tbBespokeresLsb.setUserID(tbUser.getUserID());
                } else {
                    tbBespokeresLsb.setUserID(null);
                }
                tbBespokeresLsbService.inserTbespokeresLsb(tbBespokeresLsb);
            }
        }
        //将号源进行存储（用于数据回滚）
        //httpSession.setAttribute("hgBespokesMap",hgBespokesMap1);
        return getRecommendationsList;
    }*/
    @Autowired
    Family family;
    /**
     * 推荐预约方案（在用)
     *
     * @param requestXml xml
     * @param preference 偏好
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param timeFrame  时段
     * @param hospitalName 园区名称
     * @return 推荐列表
     */
    @Override
    @Transactional
    public List<GetRecommendation> getRecommendationNew(String[] requestXml, Integer preference, String startDate,
                                                        String endDate, Integer timeFrame, String hospitalName, String hospitalizeNo,
                                                        String userID) {
        log.info("进入推荐预约方案接口getRecommendationNew");
        //用于判断是否是重新生成推荐时间，用于回滚
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            tbUser = new TbUser();
            tbUser.setUserID(userID);
        }
        if(tbUser == null){
            throw new MyException("登录用户已过期");
        }
        //获取临时表号源
        List<TbBespokeresLsb> tbBespokeresLsbList;
        tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        //回滚号源，并且删除临时表数据
        this.rollback(tbBespokeresLsbList);
        //记录号源日志
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                LogEntity logEntity1 = new LogEntity();
                logEntity1.setResID(String.valueOf(tbBespokeresLsb.getResID()));
                logEntity1.setOperaction("获取推荐方案前，先获取临时表释放号源");
                logEntity1.setHospitalType(tbBespokeresLsb.getHospitalType());
                logEntity1.setPatientNum(String.valueOf(0 - tbBespokeresLsb.getPatientNum()));
                logEntity1.setDate(new Date());
                tbBespokeresMapper.insertTbBespokeresLog(logEntity1);
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));
        //调用存储过程获取推荐时间
        StringBuilder requestXmlString = new StringBuilder();
        requestXmlString.append("<Root>");
         for (int i = 0; i < requestXml.length; i++) {
            String[] str = requestXml[i].split(":");
            if (StrUtils.isNullOrEmpty(str[0], str[1])) {
                continue;
            }
            requestXmlString.append("<Record><PartID>" + str[0] + "</PartID><TimeCoefficient>" + str[2] + "</TimeCoefficient><HospitalType>" + str[1] + "</HospitalType><HospitalizeNo>" + hospitalizeNo + "</HospitalizeNo></Record>");
        }
        requestXmlString.append("</Root>");
        Map<String, Object> params = new HashMap<>(16);
        params.put("RequestXml", requestXmlString.toString());
        params.put("Preference", preference);
        if (startDate != null && !startDate.equals("")) {
            params.put("StartDate", startDate);
        } else {
            params.put("StartDate", df.format(new Date()));
        }

        params.put("EndDate", endDate);
        params.put("TimeFrame", timeFrame);
        // 新增的院区区分
        params.put("HospitalName", hospitalName);
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            params.put("ResType", 2);
        }else{
            params.put("ResType", 1);
        }

        log.info("调用存储过程获取推荐时间接口之前");
        // 调用存储过程获取推荐时间
        List<GetRecommendation> getRecommendationsList = tbBespokeresMapper.getRecommendation(params);
        log.info("调用存储过程获取推荐时间接口之后");
        //获取到推荐时间，进行号源占用
        for (int i = 0; i < getRecommendationsList.size(); i++) {

            if (getRecommendationsList.get(i).getGroupName() != null && !"".equals(getRecommendationsList.get(i).getGroupName())) {
                //修改后
                String[] str = requestXml[i].split(":");
                HgBespoke hgBespoke = new HgBespoke();
                hgBespoke.setResID(getRecommendationsList.get(i).getResID());
                hgBespoke.setHospitalType(getRecommendationsList.get(i).getHospitalType());
                //修改后
                hgBespoke.setPatientNum(Integer.parseInt(str[2]));
                tbBespokeresMapper.updateBespokeAddition(hgBespoke);
                //记录号源日志
                LogEntity logEntity = new LogEntity();
                logEntity.setResID(String.valueOf(getRecommendationsList.get(i).getResID()));
                logEntity.setOperaction("获取推荐方案成功占用号源");
                logEntity.setHospitalType(getRecommendationsList.get(i).getHospitalType());
                logEntity.setPatientNum(str[2]);
                logEntity.setDate(new Date());
                tbBespokeresMapper.insertTbBespokeresLog(logEntity);
                //将数据存到临时表
                TbBespokeresLsb tbBespokeresLsb = new TbBespokeresLsb();
                tbBespokeresLsb.setHospitalType(getRecommendationsList.get(i).getHospitalType());
                //修改后
                tbBespokeresLsb.setPatientNum(Integer.parseInt(str[2]));
                tbBespokeresLsb.setPartID(Integer.parseInt(str[0]));
                tbBespokeresLsb.setResID(getRecommendationsList.get(i).getResID());
                if (tbUser != null) {
                    tbBespokeresLsb.setUserID(tbUser.getUserID());
                } else {
                    throw new MyException("TbUser为null");
                }
                tbBespokeresLsbService.inserTbespokeresLsb(tbBespokeresLsb);
            }
        }
        log.info("调用存储过程获取推荐时间接口结束");
        return getRecommendationsList;
    }


    /**
     * 调用推荐方案与自选方案接口之前先掉预约合并接口
     *
     * @param getRequestInfos 申请单集合
     * @return 合并后的预约对象
     */
    @Override
    public List<GetRecommendationVo> mergeAppointment(List<GetRequestInfo> getRequestInfos) throws ParseException {
        log.info("进入调用合并接口mergeAppointment");
        // 预约对象（预约对象有一个属性——>申请单集合）
        List<GetRecommendationVo> listBespokes = new ArrayList<>();
        for (int i = 0; i < getRequestInfos.size(); i++) {
            // 申请单对象
            GetRequestInfo getRequestInfo = getRequestInfos.get(i);
            // 是否合并
            boolean isUnion = false;
            for (int j = 0; j < listBespokes.size(); j++) {
                // 预约对象
                GetRecommendationVo getRecommendationVo = listBespokes.get(j);
                TbBespoke bespoke = getRecommendationVo.getTbBespoke();
                List<TbOrderDetail> tbOrderDetailList = getRecommendationVo.getTbOrderDetailList();
                // 同一个申请单号的同设备类型部位需合并（相同医生工号，相同设备,同一申请日期）
                if ((bespoke.getRequestDoctorCode().equals(getRequestInfo.getRequestDoctorCode()))
                        && (bespoke.getModality().equals(getRequestInfo.getModality()))
                &&(bespoke.getRequestDateTime().equals(getRequestInfo.getRequestDateTime()))) {
                    // 是否互斥
                    boolean bConflicting = false;
                    log.info("合并接口mergeAppointment里判断是否互斥");
                    for (int k = 0; k < tbOrderDetailList.size(); k++) {
                        TbOrderDetail orderDetail = tbOrderDetailList.get(k);
                        String partID = getRequestInfo.getPartID();
                        String modality = getRequestInfo.getModality();
                        String partID1 = orderDetail.getPartId();
                        String hospitalName = bespoke.getHospitalName();
                        //判断当前部位跟预约下的各部位是否互斥
                        boolean flag = isConflicting(modality, partID, partID1 + "", hospitalName);
                        if (flag) {
                            bConflicting = true;
                            break;
                        }
                    }
                    // 如果都不互斥则合并
                    if (!bConflicting) {
                        // 在这里面判断是否是主项目
                        Integer isMain = bespoke.getIsMain();
                        // 1是主部位，0不是主部位
                        // 对象对象是否是有主项目
                        TbMethodPart tbMethodPart1 = tbMethodPartMapper.selectYJReservation(getRequestInfo.getModality(), getRequestInfo.getStudyProjectCode(), getRequestInfo.getStudyProject(),
                                getRequestInfo.getStudyPartCode(), getRequestInfo.getStudyPart(), getRequestInfo.getHospitalName()).get(0);
                        if(isMain==0){
                            // 申请单中是否有主项目部位
                            if(tbMethodPart1.getIsMain()==1){
                                bespoke.setIsMain(1);
                                bespoke.setPartID(tbMethodPart1.getId());
                                bespoke.setStudyMethod(getRequestInfo.getStudyMethod());
                            }
                        }else {
                            if(tbMethodPart1.getIsMain()==1){
                                //主部位冲突，不往下执行，去新建一条新纪录
                                break;
                            }else{
                                System.out.println("不做任何操作");
                            }
                        }
                        TbOrderDetail tbOrderDetail = new TbOrderDetail();
                        tbOrderDetail.setHISStudyPartCode(getRequestInfo.getStudyPartCode());
                        tbOrderDetail.setHISStudyPart(getRequestInfo.getStudyPart());
                        tbOrderDetail.setHISStudyProjectCode(getRequestInfo.getStudyProjectCode());
                        tbOrderDetail.setHISStudyProject(getRequestInfo.getStudyProject());
                        tbOrderDetail.setPartId(getRequestInfo.getPartID());
                        tbOrderDetail.setTimeCoefficient(Integer.parseInt(getRequestInfo.getTimeCoefficient()));
                        tbOrderDetail.setOrdRowID(getRequestInfo.getOrdRowID());
                        if(getRequestInfo.getFee() != null && !"".equals(getRequestInfo.getFee())){
                            tbOrderDetail.setFee(new BigDecimal(getRequestInfo.getFee()));
                        }else{
                            tbOrderDetail.setFee(new BigDecimal("0"));
                        }
                        //tbOrderDetail.setFee(new BigDecimal(getRequestInfo.getFee()));
                        tbOrderDetail.setWarning(getRequestInfo.getWarning());
                        tbOrderDetail.setvHISJPGFile(getRequestInfo.getvHISJPGFile());
                        //System.out.println(getRequestInfo);
                        //String requestNo = getRequestInfo.getRequestNo();
                        //System.out.println(requestNo);
                        tbOrderDetail.setRequestNo(getRequestInfo.getRequestNo());
                        tbOrderDetail.setModality(getRequestInfo.getModality());
                        //System.out.println(tbOrderDetail.getRequestNo());
                        tbOrderDetailList.add(tbOrderDetail);
                        List<BigDecimal> feeBigDecimals = tbOrderDetailList.stream().map(TbOrderDetail::getFee).collect(Collectors.toList());
                        int sum = 0;
                        if(!tbOrderDetailList.get(0).getModality().equals("CT")){
                            sum = tbOrderDetailList.stream().
                                    mapToInt(TbOrderDetail::getTimeCoefficient).sum();
                        }else{
                            sum = tbOrderDetailList.get(0).getTimeCoefficient();
                        }

                        BigDecimal bigss = BigDecimal.ZERO;
                        for (BigDecimal big : feeBigDecimals) {
                            bigss = bigss.add(big);
                        }
                        bespoke.setTotalFee(bigss);
                        bespoke.setTimeCoefficient(sum);
                        List<String> studyParts = tbOrderDetailList.stream().map(TbOrderDetail::getHISStudyPart).collect(Collectors.toList());
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int m = 0; m < studyParts.size(); m++) {
                            if (m == (studyParts.size() - 1)) {
                                stringBuilder.append(studyParts.get(m));
                                continue;
                            }
                            stringBuilder.append(studyParts.get(m) + ",");

                        }
                        bespoke.setStudyParts(stringBuilder.toString());
                        bespoke.setStudyPart(stringBuilder.toString());
                        //listBespokes.add(getRecommendationVo);
                        isUnion = true;
                        break;
                    }
                }
            }
            // 判断是否合并，不合并就进入存入一条新纪录
            if (!isUnion) {
                log.info("调用合并接口mergeAppointment不合并处理");
                GetRecommendationVo getRecommendationVo = new GetRecommendationVo();
                // 存储 预约对象
                TbBespoke tbBespoke = new TbBespoke();
                tbBespoke.setBespokeNo(null);
                tbBespoke.setRequestNo(getRequestInfo.getRequestNo());
                tbBespoke.setHospitalizeType(getRequestInfo.getHospitalizeType());
                if (StrUtils.isNullOrEmpty(getRequestInfo.getHospitalizeTimes())) {
                    tbBespoke.setHospitalizeTimes(null);
                } else {
                    tbBespoke.setHospitalizeTimes(Integer.parseInt(getRequestInfo.getHospitalizeTimes()));
                }
                tbBespoke.setHospitalizeNo(getRequestInfo.getHospitalizeNo());
                tbBespoke.setInsuranceID(getRequestInfo.getInsuranceID());
                tbBespoke.setAdmSerialNum(getRequestInfo.getAdmSerialNum());
                tbBespoke.setDiagnosticCardNO(getRequestInfo.getDiagnosticCardNO());
                tbBespoke.setHISPatientID(getRequestInfo.gethISPatientID());
                tbBespoke.setName(getRequestInfo.getName());
                tbBespoke.setSex(getRequestInfo.getSex());
                tbBespoke.setBirthday(getRequestInfo.getBirthday());
                tbBespoke.setAge(getRequestInfo.getAge());
                tbBespoke.setAddress(getRequestInfo.getAddress());
                tbBespoke.setPhone(getRequestInfo.getPhone());
                tbBespoke.setChiefComplaint(getRequestInfo.getChiefComplaint());
                tbBespoke.setOperationInfo(getRequestInfo.getOperationInfo());
                tbBespoke.setClinicReport(getRequestInfo.getClinicReport());
                tbBespoke.setOtherInfo(getRequestInfo.getOtherInfo());
                tbBespoke.setRequestDeptCode(getRequestInfo.getRequestDeptCode());
                tbBespoke.setRequestDept(getRequestInfo.getRequestDept());
                tbBespoke.setRoom(getRequestInfo.getRoom());
                tbBespoke.setBed(getRequestInfo.getBed());
                tbBespoke.setRequestDoctorCode(getRequestInfo.getRequestDoctorCode());
                tbBespoke.setRequestDoctor(getRequestInfo.getRequestDoctor());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(getRequestInfo.getRequestDate());
                int result1 = getRequestInfo.getRequestDate().indexOf("T");
                if(result1 != -1){
                    String replace = getRequestInfo.getRequestDate().replace("T", " ");
                    getRequestInfo.setRequestDate(replace);
                }
                if(getRequestInfo.getRequestDate().length() == 10){
                    String str = getRequestInfo.getRequestDate();
                    str += " 00:00:00";
                    getRequestInfo.setRequestDate(str);
                }

                tbBespoke.setRequestDate(sdf.parse(getRequestInfo.getRequestDate()));
                tbBespoke.setModality(getRequestInfo.getModality());
                // 总费用
                if(getRequestInfo.getFee() != null && !"".equals(getRequestInfo.getFee())){
                    tbBespoke.setTotalFee(new BigDecimal(getRequestInfo.getFee()));
                }else{
                    tbBespoke.setTotalFee(new BigDecimal("0"));
                }

                tbBespoke.setExecDeptCode(getRequestInfo.getExecDeptCode());
                tbBespoke.setExecDept(getRequestInfo.getExecDept());
                tbBespoke.setStudyMethod(getRequestInfo.getStudyMethod());
                tbBespoke.setStudyRoom(getRequestInfo.getRoom());
                tbBespoke.setDevice(null);
                tbBespoke.setStudyPartCode(getRequestInfo.getStudyPartCode());
                tbBespoke.setStudyPart(getRequestInfo.getStudyPart());
                tbBespoke.setSpecialInspection(null);
                tbBespoke.setBespokeType(null);
                tbBespoke.setBespokeOpreatorCode(getRequestInfo.getOperatorCode());
                tbBespoke.setBespokeOpreator(null);
                tbBespoke.setFeeStatus(null);
                tbBespoke.setArriveDate(null);
                tbBespoke.setQueueNo(null);
                tbBespoke.setRisExamNo(null);
                tbBespoke.setRisAccessionNumber(null);
                tbBespoke.setGroupType(null);
                tbBespoke.setBespokeDate(getRequestInfo.getBrokerDate());
                tbBespoke.setStartTime(null);
                tbBespoke.setEndTime(null);
                tbBespoke.setTimeCoefficient(Integer.parseInt(getRequestInfo.getTimeCoefficient()));
                tbBespoke.setHospitalName(getRequestInfo.getHospitalName());
                tbBespoke.setWarning(getRequestInfo.getWarning());
                tbBespoke.setPartID(Integer.parseInt(getRequestInfo.getPartID()));
                // 新增自定义字段
                tbBespoke.setRequestDateTime(getRequestInfo.getRequestDateTime());
                tbBespoke.setStudyParts(getRequestInfo.getStudyPart());
                tbBespoke.setDeptName(getRequestInfo.getDeptName());
                //加入转运标识
                tbBespoke.setZhuanyun(getRequestInfo.getZhuanyun());
                log.info("调用判断预约对象是否有主项目方法之前");
                // 判断预约对象是否有主项目
                TbMethodPart tbMethodPart3 = tbMethodPartMapper.selectYJReservation(getRequestInfo.getModality(), getRequestInfo.getStudyProjectCode(), getRequestInfo.getStudyProject(),
                        getRequestInfo.getStudyPartCode(), getRequestInfo.getStudyPart(), getRequestInfo.getHospitalName()).get(0);
                log.info("调用判断预约对象是否有主项目方法之后");
                // 存在主项目为1,没有为0
                if(tbMethodPart3.getIsMain()==1){
                    tbBespoke.setIsMain(1);
                }else {
                    tbBespoke.setIsMain(0);
                }
                // 存储申请单对象
                List<TbOrderDetail> tbOrderDetails = new ArrayList<>();
                TbOrderDetail tbOrderDetail = new TbOrderDetail();
                tbOrderDetail.setHISStudyPartCode(getRequestInfo.getStudyPartCode());
                tbOrderDetail.setHISStudyPart(getRequestInfo.getStudyPart());
                tbOrderDetail.setHISStudyProjectCode(getRequestInfo.getStudyProjectCode());
                tbOrderDetail.setHISStudyProject(getRequestInfo.getStudyProject());
                tbOrderDetail.setPartId(getRequestInfo.getPartID());
                tbOrderDetail.setTimeCoefficient(Integer.parseInt(getRequestInfo.getTimeCoefficient()));
                tbOrderDetail.setRequestNo(getRequestInfo.getRequestNo());
                tbOrderDetail.setOrdRowID(getRequestInfo.getOrdRowID());
                if(getRequestInfo.getFee() != null && !"".equals(getRequestInfo.getFee())){
                    tbOrderDetail.setFee(new BigDecimal(getRequestInfo.getFee()));
                }else{
                    tbOrderDetail.setFee(new BigDecimal("0"));
                }
                //tbOrderDetail.setFee(new BigDecimal(getRequestInfo.getFee()));
                tbOrderDetail.setWarning(getRequestInfo.getWarning());
                tbOrderDetail.setvHISJPGFile(getRequestInfo.getvHISJPGFile());
                tbOrderDetail.setModality(getRequestInfo.getModality());
                tbOrderDetails.add(tbOrderDetail);
                getRecommendationVo.setTbBespoke(tbBespoke);
                getRecommendationVo.setTbOrderDetailList(tbOrderDetails);
                listBespokes.add(getRecommendationVo);
            }
        }
        log.info("调用合并接口mergeAppointment结束");
        // List<GetRecommendationVo> getRecommendationVoList = listBespokes.stream().distinct().collect(Collectors.toList());
        return listBespokes;
    }

    /**
     * 判断两个申请单部位是否互斥
     *
     * @param modality          设备
     * @param partId            部位id
     * @param conflictingPartId 互斥部位
     * @param hospitalName      院区名称
     * @return true or false
     */
    private boolean isConflicting(String modality, String partId, String conflictingPartId, String hospitalName) {
        // 查询互斥表,是否互斥
        List<TbConflictingPart> conflicting = tbConflictingPartMapper.isConflicting(modality, partId, conflictingPartId, conflictingPartId, partId, hospitalName);
        // 返回true表示互斥
        return !conflicting.isEmpty();
        //返回false表示不互斥
    }


    /**
     * 获取自选方案时间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    @Override
    public List<GetBespokeRes> getOptionalDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient, String studyRoom) {
        log.info("进入获取自选方案时间接口getOptionalDate");
        //获取方案时间，清除对应的占用和session
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));
        // TbMethodPart tbMethodPart = tbMethodPartMapper.selectById(Integer.parseInt(partID));
        Map<String, Object> params = new HashMap<>(16);
        params.put("PartID", partID);
        params.put("TimeCoefficient", timeCoefficient);
        params.put("HospitalType", hospitalType);
        if (startDate != null && !startDate.equals("")) {
            params.put("StartDate", startDate);
        } else {
            params.put("StartDate", df.format(new Date()));
        }

        if (studyRoom != null && !studyRoom.equals("")) {
            params.put("StudyRoom", studyRoom);
        } else {
            params.put("StudyRoom", "全部");
        }
        params.put("EndDate", endDate);
        params.put("HospitalName", hospitalName);
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            params.put("ResType", 2);
        }else{
            params.put("ResType", 1);
        }
        log.info("调用获取自选方案时间存储过程之前");
        List<GetBespokeRes> getRecommendationsList = tbBespokeresMapper.getBespokeRes(params);
        log.info("调用获取自选方案时间存储过程之后");
        return getRecommendationsList;
    }

    /**
     * 选择预约时间
     *
     * @param resID        号源表ID
     * @param hospitalType 就诊类型
     * @param partID       tb_method_part表主键(部位ID)
     * @return 预约表
     */
    @Override
    @Transactional
    public R<TbBespokeres> selectionPeriod(Integer resID, String hospitalType, String partID, Integer timeCoefficient,String userID) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //回滚号源
        TbUser tbUser = (TbUser) request.getSession().getAttribute("user");
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            tbUser = new TbUser();
            tbUser.setUserID(userID);
        }
        if(tbUser == null){
            return new R(1,"","登录用户已过期");
        }
        //当前超时时间
        int time=request.getSession().getMaxInactiveInterval();
        System.out.println("session过期时间：" + time);
        List<TbBespokeresLsb> tbBespokeresLsbList;
//        List<Integer> partIds = new ArrayList<>();
//        partIds.add(Integer.parseInt(partID));

        tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), Integer.parseInt(partID) );

        this.rollback(tbBespokeresLsbList);

        //记录号源日志
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                LogEntity logEntity2 = new LogEntity();
                logEntity2.setResID(String.valueOf(tbBespokeresLsb.getResID()));
                logEntity2.setOperaction("自选方案选择预约时间（或变更预约选择预约时间）前获取临时表释放号源");
                logEntity2.setHospitalType(tbBespokeresLsb.getHospitalType());
                logEntity2.setPatientNum(String.valueOf(0 - tbBespokeresLsb.getPatientNum()));
                logEntity2.setDate(new Date());
                //记录号源日志
                tbBespokeresMapper.insertTbBespokeresLog(logEntity2);
            }
        }


        /**
         System.out.println("sessionID="+httpSession.getId());
         //回滚号源
         Map<String,HgBespoke> hgBespokesMap = (Map<String,HgBespoke>)httpSession.getAttribute("hgBespokesMap");
         if(hgBespokesMap!=null){
         HgBespoke hgBespokes = hgBespokesMap.get(partID);
         if(hgBespokes!=null){
         hgBespokes.setPatientNum(0-hgBespokes.getPatientNum());
         tbBespokeresMapper.updateBespokeAddition(hgBespokes);
         hgBespokesMap.remove(partID);
         }
         }else{
         hgBespokesMap = new HashMap<String,HgBespoke>(16);
         }*/
        //判断是否选择了同一天同一个时间段
        if (resID != null) {
            List<TbBespokeresLsb> tbBespokeresLsbLists;

            tbBespokeresLsbLists = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);

            TbMethodPart tbMethodPart = tbMethodPartMapper.selectById(partID);
            TbBespokeres tbBespokeres = tbBespokeresMapper.selectById(resID);
            if (tbBespokeresLsbLists != null && tbBespokeresLsbLists.size() > 0) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbLists) {
                    TbBespokeres tbBespokeress = tbBespokeresMapper.selectById(tbBespokeresLsb.getResID());
                    System.out.println(fmt.format(tbBespokeres.getBespokeDate()).equals(fmt.format(tbBespokeress.getBespokeDate())));
                    System.out.println(tbBespokeres.getStartTime().equals(tbBespokeress.getStartTime()));
                    System.out.println(tbBespokeres.getEndTime().equals(tbBespokeress.getEndTime()));
                    if (fmt.format(tbBespokeres.getBespokeDate()).equals(fmt.format(tbBespokeress.getBespokeDate())) && tbBespokeres.getStartTime().equals(tbBespokeress.getStartTime()) && tbBespokeres.getEndTime().equals(tbBespokeress.getEndTime())) {
                        //throw new MyException("请不要选择相同时间段的检查");
                        return new R(1,"请不要选择相同时间段的检查");
                    }
                }
            }
            HgBespoke hgBespoke = new HgBespoke();
            hgBespoke.setResID(resID);
            hgBespoke.setPartID(Integer.parseInt(partID));
            hgBespoke.setHospitalType(hospitalType);
            // 就诊方式：住院：Y 门诊:N
            //修改后
            hgBespoke.setPatientNum(timeCoefficient);
            tbBespokeresMapper.updateBespokeAddition(hgBespoke);

            //记录号源日志
            LogEntity logEntity = new LogEntity();
            logEntity.setResID(String.valueOf(resID));
            logEntity.setOperaction("自选方案（或变更预约自选）占用号源");
            logEntity.setHospitalType(hospitalType);
            logEntity.setPatientNum(String.valueOf(timeCoefficient));
            logEntity.setDate(new Date());
            tbBespokeresMapper.insertTbBespokeresLog(logEntity);

            //将数据存到临时表
            TbBespokeresLsb tbBespokeresLsb = new TbBespokeresLsb();
            tbBespokeresLsb.setHospitalType(hospitalType);
            tbBespokeresLsb.setPartID(Integer.parseInt(partID));
            tbBespokeresLsb.setPatientNum(timeCoefficient);
            tbBespokeresLsb.setResID(resID);
            if(tbUser != null){
                tbBespokeresLsb.setUserID(tbUser.getUserID());
            }else{
                return new R(1,"","登录用户过期");
            }

            tbBespokeresLsbService.inserTbespokeresLsb(tbBespokeresLsb);
            TbBespokeres tbBespokeres1 = tbBespokeresMapper.selectById(resID);
            return new R(tbBespokeres1);
        }
        return null;
    }

    /**
     * 推荐预约方案后，清除号源占用
     *
     * @param partID 部位id
     * @return 操作成功码
     */
    @Override
    @Transactional
    public Integer clearSource(String partID,String userID) {
        /**
         System.out.println("sessionID="+httpSession.getId());
         Map<String,HgBespoke> hgBespokesMap = (Map<String,HgBespoke>)httpSession.getAttribute("hgBespokesMap");
         if(partID!=null && !partID.equals("")){
         if(hgBespokesMap!=null){
         HgBespoke hgBespokes = hgBespokesMap.get(partID);
         if(hgBespokes!=null){
         hgBespokes.setPatientNum(0-hgBespokes.getPatientNum());
         tbBespokeresMapper.updateBespokeAddition(hgBespokes);
         }
         hgBespokesMap.remove(partID);
         }
         }else{
         if(hgBespokesMap!=null && hgBespokesMap.size()>0){
         for (String key : hgBespokesMap.keySet()) {
         HgBespoke hgBespoke = hgBespokesMap.get(key);
         if(hgBespoke!=null){
         hgBespoke.setPatientNum(0 - hgBespoke.getPatientNum());
         tbBespokeresMapper.updateBespokeAddition(hgBespoke);
         }
         }
         }
         httpSession.removeAttribute("hgBespokesMap");
         }
         */
        TbUser tbUser = (TbUser) httpSession.getAttribute("user");
        //判断用户是否过期
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            tbUser = new TbUser();
            tbUser.setUserID(userID);
        }
        if(tbUser == null){
            throw new MyException("登录用户已过期");
        }
        List<TbBespokeresLsb> tbBespokeresLsbList = new ArrayList<TbBespokeresLsb>();
        if(partID == null || "".equals(partID)){
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), 0);
        }else{
            tbBespokeresLsbList = tbBespokeresLsbService.selectUserID(tbUser.getUserID(), Integer.parseInt(partID));
        }

        this.rollback(tbBespokeresLsbList);
        //记录号源日志
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                LogEntity logEntity2 = new LogEntity();
                logEntity2.setResID(String.valueOf(tbBespokeresLsb.getResID()));
                logEntity2.setOperaction("推荐方案点击取消| |自选方案点击取消| |变更预约点击取消| |推荐方案界面切换到自选界面");
                logEntity2.setHospitalType(tbBespokeresLsb.getHospitalType());
                logEntity2.setPatientNum(String.valueOf(0 - tbBespokeresLsb.getPatientNum()));
                logEntity2.setDate(new Date());
                //记录号源日志
                tbBespokeresMapper.insertTbBespokeresLog(logEntity2);
            }
        }
        return 1;
    }

    /**
     * 号源复用
     *
     * @param modality     设备类型
     * @param groupType    分组方式
     * @param groupName    分组名称
     * @param startDate    复用原始时间
     * @param endDate      复用目标时间
     * @param hospitalName 院区名称
     * @return 号源集合
     */
    @Override
    public R<List<TbBespokeres>> multiplexTbBespokeres(String modality, Integer groupType, String groupName, String startDate, String endDate,
                                                    String multiplexStartDate, String multiplexEndDate, String hospitalName) throws ParseException {
        if (StrUtils.isNullOrEmpty(modality, groupType.toString(), groupName, startDate, endDate)) {
            //throw new MyException("设备号,分组方式,分组名称，开始时间，结束时间均不能为空");
            return new R(1,"设备号,分组方式,分组名称，开始时间，结束时间均不能为空");
        }
        // 得到分组名称集合
        List<String> groupNames = stringSplit(groupName, "&&");
        // 原始复用时间集合
        List<String> originalDates = TimeUtils.conversionDateList(startDate, endDate);
        // 目标复用时间集合
        List<String> multiplexDates = TimeUtils.conversionDateList(multiplexStartDate, multiplexEndDate);
        // 将时间集合变成map,键值对应
        Map<String, String> dateMap = TimeUtils.dateMap(originalDates, multiplexDates);
        // 搜索所需复用的日期号源是否存在
        List<TbBespokeres> tbBespokeres = tbBespokeresMapper.searchBospokerer(groupNames, modality, groupType, startDate, endDate, hospitalName);
        if (tbBespokeres.isEmpty()) {
            //throw new MyException("复用号源不存在，请重新选择");
            return new R(1,"复用号源不存在，请重新选择");
        }
        // 复用目标日期号源是否已存在
        List<TbBespokeres> bespokeresList = tbBespokeresMapper.searchBospokerer(groupNames, modality, groupType, multiplexStartDate, multiplexEndDate, hospitalName);
        if (!bespokeresList.isEmpty()) {
            //throw new MyException("复用日期号源已存在，请重新操作");
            return new R(1,"复用日期号源已存在，请重新操作");
        }
        List<TbBespokeres> tbBespokerList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TbBespokeres bespoker : tbBespokeres) {
            String format = sdf.format(bespoker.getBespokeDate());
            Date resultDate = sdf.parse(dateMap.get(format));
            bespoker.setBespokeDate(resultDate);
            bespoker.setId(null);
            tbBespokerList.add(bespoker);
            tbBespokeresMapper.insertTbBespokeres(bespoker);
        }
        return new R(tbBespokerList);
    }


    /**
     * @param string    字符串
     * @param character 切割字符标志位
     * @return 字符串集合
     */
    public List<String> stringSplit(String string, String character) {
        return Arrays.asList(string.split(character));
    }


    public void rollback(List<TbBespokeresLsb> tbBespokeresLsbList) {
        if (tbBespokeresLsbList != null && tbBespokeresLsbList.size() > 0) {
            for (TbBespokeresLsb tbBespokeresLsb : tbBespokeresLsbList) {
                //删除号源临时表数据
                Integer integer = tbBespokeresLsbService.daleteUserID(tbBespokeresLsb.getId());
                log.info("删除号源临时表数据成功："+ integer);
                if(integer > 0){
                    HgBespoke hgBespoke = new HgBespoke();
                    hgBespoke.setResID(tbBespokeresLsb.getResID());
                    hgBespoke.setPatientNum(0 - tbBespokeresLsb.getPatientNum());
                    hgBespoke.setHospitalType(tbBespokeresLsb.getHospitalType());
                    hgBespoke.setPartID(tbBespokeresLsb.getPartID());
                    //回滚号源
                    tbBespokeresMapper.updateBespokeAddition(hgBespoke);
                    log.info("回滚号源成功");
                }
            }
        }
    }

    /**
     * 预约号源情况查询
     * @param tbBespokeres 号源表
     * @return 号源情况查询集合
     */
    @Override
    public List<TbBespokeres> queryTbBespokeres(TbBespokeres tbBespokeres) {
        //查询当前时间之后的号源
        if (null == tbBespokeres.getBespokeDate()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                tbBespokeres.setBespokeDate(sdf.parse(sdf.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (tbBespokeres.getDeptName().equals(ConstantClass.ADMINISTRATOR)) {
            return tbBespokeresMapper.selectFinAll(tbBespokeres);
        } else {
            return tbBespokeresMapper.selectFindAll(tbBespokeres);
        }
    }

    /**
     * 微信端获取自选方案无号源时间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    @Override
    public String getAppOptionalNotDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient) {
        //获取方案时间，清除对应的占用和session
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));
        // TbMethodPart tbMethodPart = tbMethodPartMapper.selectById(Integer.parseInt(partID));
        Map<String, Object> params = new HashMap<>(16);
        params.put("PartID", partID);
        params.put("TimeCoefficient", timeCoefficient);
        params.put("HospitalType", hospitalType);
        if (startDate != null && !startDate.equals("")) {
            params.put("StartDate", startDate);
        } else {
            params.put("StartDate", df.format(new Date()));
        }
        params.put("EndDate", endDate);
        //
        params.put("HospitalName", hospitalName);
        JsonArray jsonArray = new JsonArray();
        List<GetBespokeRes> getRecommendationsList = tbBespokeresMapper.getNotBespokeResDay(params);
        for (GetBespokeRes getBespokeRes : getRecommendationsList) {
            jsonArray.add(getBespokeRes.getNotBespokeResDay()+"");
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("flag", "0");
        jo.addProperty("msg", "ok");
        jo.add("notBespokeResDay",jsonArray);
        return jo.toString();

//        return getRecommendationsList;
    }

    /**
     * 获取自选方案可预约的检查房间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    @Override
    public String getStudyGroupName(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));

        Map<String, Object> params = new HashMap<>(16);
        params.put("PartID", partID);
        params.put("TimeCoefficient", timeCoefficient);
        params.put("HospitalType", hospitalType);
        if (startDate != null && !startDate.equals("")) {
            params.put("StartDate", startDate);
        } else {
            params.put("StartDate", df.format(new Date()));
        }
        params.put("EndDate", endDate);
        params.put("HospitalName", hospitalName);
        JsonArray jsonArray = new JsonArray();

        List<GetBespokeRes> getRecommendationsList = tbBespokeresMapper.getStudyGroupName(params);
        for (GetBespokeRes getBespokeRes : getRecommendationsList) {
            jsonArray.add(getBespokeRes.getGroupName());
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("flag", "0");
        jo.addProperty("msg", "ok");
        jo.add("studyGroupName",jsonArray);
        return jo.toString();
    }


}
