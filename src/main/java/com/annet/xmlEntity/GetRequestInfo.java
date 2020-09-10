package com.annet.xmlEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@XStreamAlias("list")
/**
 * 自动为所有字段添加@ToString, @EqualsAndHashCode, @Getter方法，为非final字段添加@Setter,和@RequiredArgsConstructor
 */
@Data
/**
 * 自动生成全参数构造函数
 */
@AllArgsConstructor
/**
 * 自动生成无参数构造函数
 */
@NoArgsConstructor
/**
 * GetRequestInfo方法返回的XML对应的实体类
 * 根元素
 * @author
 */
public class GetRequestInfo {

    @XStreamAlias("RequestNo")
    private String requestNo;
    @XStreamAlias("OrdRowID")
    private String ordRowID;
    @XStreamAlias("AdmSerialNum")
    private String admSerialNum;
    @XStreamAlias("DiagnosticCardNO")
    private String diagnosticCardNO;
    @XStreamAlias("HISPatientID")
    //private String hISPatientID;
    private String hISPatientID;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Sex")
    private String sex;
    @XStreamAlias("Birthday")
    private String birthday;
    @XStreamAlias("Age")
    private String age;
    @XStreamAlias("InsuranceID")
    private String insuranceID;
    @XStreamAlias("Address")
    private String address;
    @XStreamAlias("Phone")
    private String phone;
    @XStreamAlias("HospitalizeType")
    private String hospitalizeType;
    @XStreamAlias("Priority")
    private String priority;
    @XStreamAlias("HospitalizeTimes")
    private String hospitalizeTimes;
    @XStreamAlias("HospitalizeNo")
    private String hospitalizeNo;
    @XStreamAlias("Room")
    private String room;
    @XStreamAlias("Bed")
    private String bed;
    @XStreamAlias("ChiefComplaint")
    private String chiefComplaint;
    @XStreamAlias("ClinicReport")
    private String clinicReport;
    @XStreamAlias("OperationInfo")
    private String operationInfo;
    @XStreamAlias("OtherInfo")
    private String otherInfo;
    @XStreamAlias("RequestDeptCode")
    private String requestDeptCode;
    @XStreamAlias("RequestDept")
    private String requestDept;
    @XStreamAlias("RequestDoctorCode")
    private String requestDoctorCode;
    @XStreamAlias("RequestDoctor")
    private String requestDoctor;
    @XStreamAlias("RequestDate")
    private String requestDate;
    @XStreamAlias("Modality")
    private String modality;
    @XStreamAlias("StudyProjectCode")
    private String studyProjectCode;
    @XStreamAlias("StudyProject")
    private String studyProject;
    @XStreamAlias("Fee")
    private String fee;
    @XStreamAlias("StudyPartCode")
    private String studyPartCode;
    @XStreamAlias("StudyPart")
    private String studyPart;
    @XStreamAlias("ExecDeptCode")
    private String execDeptCode;
    @XStreamAlias("ExecDept")
    private String execDept;
    @XStreamAlias("VHISJPGFile")
    private String vHISJPGFile;

    /**
     * 状态，新加字段
     */
    private String status;
    /**
     * PartID,tb_method_part表主键，用于获取推荐方案时RequestXml
     */
    private String partID;

    /**
     * 当前检查所需耗时数
     */
    private String timeCoefficient;

    /**
     * 检查方法
     */
    private String studyMethod;

    /**
     * 匹配到的检查部位编码
     */
    private String xstudyPartCode;

    /**
     * 匹配到的检查部位
     */
    private String xstudyPart;

    /**
     * 匹配到的检查院区
     */
    private String hospitalName;

    /**
     * 已预约的时间
     */
    private Date brokerDate;

    /**
     * 医生工号
     */
    private String operatorCode;


    private String preference;
    private String timeFrame;
    private String startDate;
    private String endDate;

    // 申请单号与设备集合
    private String requestNoAndModality;
    private String warning;
    private String requestDateTime;
    private String deptName;

    //新加methodpartcount（大于0,部位可预约）、bespokecount（大于0已预约）
    private int methodPartCount;
    private int bespokeCount;
    private Date bespokeStudyDate;
    private String StudyStatus;

    private String aRequestDate;
    private String zhuanyun;
    private int onlineBespoke;

    public int getOnlineBespoke() {return onlineBespoke;}

    public void setOnlineBespoke(int onlineBespoke) {this.onlineBespoke = onlineBespoke;}

    public String getZhuanyun() {return zhuanyun;}

    public void setZhuanyun(String zhuanyun) {this.zhuanyun = zhuanyun;}

    public String getaRequestDate() { return aRequestDate; }

    public void setaRequestDate(String aRequestDate) { this.aRequestDate = aRequestDate; }

    public String getStudyStatus() {
        return StudyStatus;
    }

    public void setStudyStatus(String studyStatus) {
        StudyStatus = studyStatus;
    }

    public int getMethodPartCount() { return methodPartCount; }

    public void setMethodPartCount(int methodPartCount) { this.methodPartCount = methodPartCount; }

    public int getBespokeCount() { return bespokeCount; }

    public void setBespokeCount(int bespokeCount) { this.bespokeCount = bespokeCount; }

    public Date getBespokeStudyDate() { return bespokeStudyDate; }

    public void setBespokeStudyDate(Date bespokeStudyDate) { this.bespokeStudyDate = bespokeStudyDate; }

    public String getRequestNoAndModality() {
        return requestNoAndModality;
    }

    public void setRequestNoAndModality(String requestNoAndModality) {
        this.requestNoAndModality = requestNoAndModality;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Date getBrokerDate() {
        return brokerDate;
    }

    public void setBrokerDate(Date brokerDate) {
        this.brokerDate = brokerDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getOrdRowID() {
        return ordRowID;
    }

    public void setOrdRowID(String ordRowID) {
        this.ordRowID = ordRowID;
    }

    public String getAdmSerialNum() {
        return admSerialNum;
    }

    public void setAdmSerialNum(String admSerialNum) {
        this.admSerialNum = admSerialNum;
    }

    public String getDiagnosticCardNO() {
        return diagnosticCardNO;
    }

    public void setDiagnosticCardNO(String diagnosticCardNO) {
        this.diagnosticCardNO = diagnosticCardNO;
    }

    public String gethISPatientID() {
        return hISPatientID;
    }

    public void sethISPatientID(String hISPatientID) {
        this.hISPatientID = hISPatientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHospitalizeType() {
        return hospitalizeType;
    }

    public void setHospitalizeType(String hospitalizeType) {
        this.hospitalizeType = hospitalizeType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getHospitalizeTimes() {
        return hospitalizeTimes;
    }

    public void setHospitalizeTimes(String hospitalizeTimes) {
        this.hospitalizeTimes = hospitalizeTimes;
    }

    public String getHospitalizeNo() {
        return hospitalizeNo;
    }

    public void setHospitalizeNo(String hospitalizeNo) {
        this.hospitalizeNo = hospitalizeNo;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getClinicReport() {
        return clinicReport;
    }

    public void setClinicReport(String clinicReport) {
        this.clinicReport = clinicReport;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getRequestDeptCode() {
        return requestDeptCode;
    }

    public void setRequestDeptCode(String requestDeptCode) {
        this.requestDeptCode = requestDeptCode;
    }

    public String getRequestDept() {
        return requestDept;
    }

    public void setRequestDept(String requestDept) {
        this.requestDept = requestDept;
    }

    public String getRequestDoctorCode() {
        return requestDoctorCode;
    }

    public void setRequestDoctorCode(String requestDoctorCode) {
        this.requestDoctorCode = requestDoctorCode;
    }

    public String getRequestDoctor() {
        return requestDoctor;
    }

    public void setRequestDoctor(String requestDoctor) {
        this.requestDoctor = requestDoctor;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getStudyProjectCode() {
        return studyProjectCode;
    }

    public void setStudyProjectCode(String studyProjectCode) {
        this.studyProjectCode = studyProjectCode;
    }

    public String getStudyProject() {
        return studyProject;
    }

    public void setStudyProject(String studyProject) {
        this.studyProject = studyProject;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStudyPartCode() {
        return studyPartCode;
    }

    public void setStudyPartCode(String studyPartCode) {
        this.studyPartCode = studyPartCode;
    }

    public String getStudyPart() {
        return studyPart;
    }

    public void setStudyPart(String studyPart) {
        this.studyPart = studyPart;
    }

    public String getExecDeptCode() {
        return execDeptCode;
    }

    public void setExecDeptCode(String execDeptCode) {
        this.execDeptCode = execDeptCode;
    }

    public String getExecDept() {
        return execDept;
    }

    public void setExecDept(String execDept) {
        this.execDept = execDept;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        this.partID = partID;
    }

    public String getTimeCoefficient() {
        return timeCoefficient;
    }

    public void setTimeCoefficient(String timeCoefficient) {
        this.timeCoefficient = timeCoefficient;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getXstudyPartCode() {
        return xstudyPartCode;
    }

    public void setXstudyPartCode(String xstudyPartCode) {
        this.xstudyPartCode = xstudyPartCode;
    }

    public String getXstudyPart() {
        return xstudyPart;
    }

    public void setXstudyPart(String xstudyPart) {
        this.xstudyPart = xstudyPart;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getvHISJPGFile() {
        return vHISJPGFile;
    }

    public void setvHISJPGFile(String vHISJPGFile) {
        this.vHISJPGFile = vHISJPGFile;
    }
}
