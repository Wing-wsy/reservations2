package com.annet.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author XiaYu
 * @since 2019-05-15
 */
@TableName("tb_bespoke")
public class TbBespoke extends Model<TbBespoke> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String BespokeNo;
    private String RequestNo;
    // 删除
    //private String OrdRowID;
    private String HospitalizeType;
    private String HospitalizeNo;
    private Integer HospitalizeTimes;
    private String InsuranceID;
    private String AdmSerialNum;
    private String DiagnosticCardNO;
    private String HISPatientID;
    private String Name;
    private String Sex;
    private String Birthday;
    private String Age;
    private String Address;
    private String Phone;
    private String ChiefComplaint;
    private String OperationInfo;
    private String ClinicReport;
    private String OtherInfo;
    private String RequestDeptCode;
    private String RequestDept;
    private String Room;
    private String Bed;
    private String RequestDoctorCode;
    private String RequestDoctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date RequestDate;
    private String Modality;

    // 新增
    private BigDecimal TotalFee;

    private String ExecDeptCode;
    private String ExecDept;
    private String StudyMethod;
    private String StudyRoom;
    private String Device;
    private String StudyPartCode;
    private String StudyPart;
    private String SpecialInspection;
    private Integer BespokeType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date BespokeDate;
    private String BespokeOpreatorCode;
    private String BespokeOpreator;
    private Integer FeeStatus;
    private Integer BespokeStatus;
    private Integer StudyStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ArriveDate;
    private String sArriveDate;
    private String QueueNo;

    // 新增
    private String RisExamNo;
    private String RisAccessionNumber;

    private Integer GroupType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date BespokeStudyDate;
    private String StartTime;
    private String EndTime;
    private Integer TimeCoefficient;
    private String HospitalName;
    private String Warning;
    private Integer PartID;

    /*自定义字段，非数据库字段*/
    private String DeptName;
    /*自定义字段，非数据库字段,员工编号*/
    private String operatorCode;
    private Integer isMain;
    private String studyParts;
    private String requestDateTime;
    private String OrdRowID;
    private String HISStudyProjectCode;
    private String HISStudyProject;
    private String HISStudyPartCode;
    private String HISStudyPart;
    private String Fee;
    // 预约提交的状态码（0表示预约提交不成功，1表示预约有成功）
    private String AppointmentStatus;

    // 推荐方案
    private String Preference;
    private String TimeFrame;
    private String StartDate;
    private String EndDate;
    //
    private List<TbOrderDetail> tbOrderDetails;

    // 等待人数
    private String risWaitNum;
    //预约号源ID
    private Integer ResID;
    private String zhuanyun;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbBespoke{" +
                "id=" + id +
                ", BespokeNo='" + BespokeNo + '\'' +
                ", RequestNo='" + RequestNo + '\'' +
                ", HospitalizeType='" + HospitalizeType + '\'' +
                ", HospitalizeNo='" + HospitalizeNo + '\'' +
                ", HospitalizeTimes=" + HospitalizeTimes +
                ", InsuranceID='" + InsuranceID + '\'' +
                ", AdmSerialNum='" + AdmSerialNum + '\'' +
                ", DiagnosticCardNO='" + DiagnosticCardNO + '\'' +
                ", HISPatientID='" + HISPatientID + '\'' +
                ", Name='" + Name + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Birthday='" + Birthday + '\'' +
                ", Age='" + Age + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", ChiefComplaint='" + ChiefComplaint + '\'' +
                ", OperationInfo='" + OperationInfo + '\'' +
                ", ClinicReport='" + ClinicReport + '\'' +
                ", OtherInfo='" + OtherInfo + '\'' +
                ", RequestDeptCode='" + RequestDeptCode + '\'' +
                ", RequestDept='" + RequestDept + '\'' +
                ", Room='" + Room + '\'' +
                ", Bed='" + Bed + '\'' +
                ", RequestDoctorCode='" + RequestDoctorCode + '\'' +
                ", RequestDoctor='" + RequestDoctor + '\'' +
                ", RequestDate=" + RequestDate +
                ", Modality='" + Modality + '\'' +
                ", TotalFee=" + TotalFee +
                ", ExecDeptCode='" + ExecDeptCode + '\'' +
                ", ExecDept='" + ExecDept + '\'' +
                ", StudyMethod='" + StudyMethod + '\'' +
                ", StudyRoom='" + StudyRoom + '\'' +
                ", Device='" + Device + '\'' +
                ", StudyPartCode='" + StudyPartCode + '\'' +
                ", StudyPart='" + StudyPart + '\'' +
                ", SpecialInspection='" + SpecialInspection + '\'' +
                ", BespokeType=" + BespokeType +
                ", BespokeDate=" + BespokeDate +
                ", BespokeOpreatorCode='" + BespokeOpreatorCode + '\'' +
                ", BespokeOpreator='" + BespokeOpreator + '\'' +
                ", FeeStatus=" + FeeStatus +
                ", BespokeStatus=" + BespokeStatus +
                ", StudyStatus=" + StudyStatus +
                ", ArriveDate=" + ArriveDate +
                ", sArriveDate='" + sArriveDate + '\'' +
                ", QueueNo='" + QueueNo + '\'' +
                ", RisExamNo='" + RisExamNo + '\'' +
                ", RisAccessionNumber='" + RisAccessionNumber + '\'' +
                ", GroupType=" + GroupType +
                ", BespokeStudyDate=" + BespokeStudyDate +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", TimeCoefficient=" + TimeCoefficient +
                ", HospitalName='" + HospitalName + '\'' +
                ", Warning='" + Warning + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", partID=" + PartID +
                ", isMain=" + isMain +
                ", studyParts='" + studyParts + '\'' +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", OrdRowID='" + OrdRowID + '\'' +
                ", HISStudyProjectCode='" + HISStudyProjectCode + '\'' +
                ", HISStudyProject='" + HISStudyProject + '\'' +
                ", HISStudyPartCode='" + HISStudyPartCode + '\'' +
                ", HISStudyPart='" + HISStudyPart + '\'' +
                ", Fee='" + Fee + '\'' +
                ", AppointmentStatus='" + AppointmentStatus + '\'' +
                ", Preference='" + Preference + '\'' +
                ", TimeFrame='" + TimeFrame + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", tbOrderDetails=" + tbOrderDetails +
                ", risWaitNum='" + risWaitNum + '\'' +
                '}';
    }

    public Integer getResID() { return ResID; }

    public void setResID(Integer resID) { ResID = resID; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBespokeNo() {
        return BespokeNo;
    }

    public void setBespokeNo(String bespokeNo) {
        BespokeNo = bespokeNo;
    }

    public String getRequestNo() {
        return RequestNo;
    }

    public void setRequestNo(String requestNo) {
        RequestNo = requestNo;
    }

    public String getHospitalizeType() {
        return HospitalizeType;
    }

    public void setHospitalizeType(String hospitalizeType) {
        HospitalizeType = hospitalizeType;
    }

    public String getHospitalizeNo() {
        return HospitalizeNo;
    }

    public void setHospitalizeNo(String hospitalizeNo) {
        HospitalizeNo = hospitalizeNo;
    }

    public Integer getHospitalizeTimes() {
        return HospitalizeTimes;
    }

    public void setHospitalizeTimes(Integer hospitalizeTimes) {
        HospitalizeTimes = hospitalizeTimes;
    }

    public String getInsuranceID() {
        return InsuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        InsuranceID = insuranceID;
    }

    public String getAdmSerialNum() {
        return AdmSerialNum;
    }

    public void setAdmSerialNum(String admSerialNum) {
        AdmSerialNum = admSerialNum;
    }

    public String getDiagnosticCardNO() {
        return DiagnosticCardNO;
    }

    public void setDiagnosticCardNO(String diagnosticCardNO) {
        DiagnosticCardNO = diagnosticCardNO;
    }

    public String getHISPatientID() {
        return HISPatientID;
    }

    public void setHISPatientID(String HISPatientID) {
        this.HISPatientID = HISPatientID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getChiefComplaint() {
        return ChiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        ChiefComplaint = chiefComplaint;
    }

    public String getOperationInfo() {
        return OperationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        OperationInfo = operationInfo;
    }

    public String getClinicReport() {
        return ClinicReport;
    }

    public void setClinicReport(String clinicReport) {
        ClinicReport = clinicReport;
    }

    public String getOtherInfo() {
        return OtherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        OtherInfo = otherInfo;
    }

    public String getRequestDeptCode() {
        return RequestDeptCode;
    }

    public void setRequestDeptCode(String requestDeptCode) {
        RequestDeptCode = requestDeptCode;
    }

    public String getRequestDept() {
        return RequestDept;
    }

    public void setRequestDept(String requestDept) {
        RequestDept = requestDept;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getBed() {
        return Bed;
    }

    public void setBed(String bed) {
        Bed = bed;
    }

    public String getRequestDoctorCode() {
        return RequestDoctorCode;
    }

    public void setRequestDoctorCode(String requestDoctorCode) {
        RequestDoctorCode = requestDoctorCode;
    }

    public String getRequestDoctor() {
        return RequestDoctor;
    }

    public void setRequestDoctor(String requestDoctor) {
        RequestDoctor = requestDoctor;
    }

    public Date getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(Date requestDate) {
        RequestDate = requestDate;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }

    public BigDecimal getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        TotalFee = totalFee;
    }

    public String getExecDeptCode() {
        return ExecDeptCode;
    }

    public void setExecDeptCode(String execDeptCode) {
        ExecDeptCode = execDeptCode;
    }

    public String getExecDept() {
        return ExecDept;
    }

    public void setExecDept(String execDept) {
        ExecDept = execDept;
    }

    public String getStudyMethod() {
        return StudyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        StudyMethod = studyMethod;
    }

    public String getStudyRoom() {
        return StudyRoom;
    }

    public void setStudyRoom(String studyRoom) {
        StudyRoom = studyRoom;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getStudyPartCode() {
        return StudyPartCode;
    }

    public void setStudyPartCode(String studyPartCode) {
        StudyPartCode = studyPartCode;
    }

    public String getStudyPart() {
        return StudyPart;
    }

    public void setStudyPart(String studyPart) {
        StudyPart = studyPart;
    }

    public String getSpecialInspection() {
        return SpecialInspection;
    }

    public void setSpecialInspection(String specialInspection) {
        SpecialInspection = specialInspection;
    }

    public Integer getBespokeType() {
        return BespokeType;
    }

    public void setBespokeType(Integer bespokeType) {
        BespokeType = bespokeType;
    }

    public Date getBespokeDate() {
        return BespokeDate;
    }

    public void setBespokeDate(Date bespokeDate) {
        BespokeDate = bespokeDate;
    }

    public String getBespokeOpreatorCode() {
        return BespokeOpreatorCode;
    }

    public void setBespokeOpreatorCode(String bespokeOpreatorCode) {
        BespokeOpreatorCode = bespokeOpreatorCode;
    }

    public String getBespokeOpreator() {
        return BespokeOpreator;
    }

    public void setBespokeOpreator(String bespokeOpreator) {
        BespokeOpreator = bespokeOpreator;
    }

    public Integer getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(Integer feeStatus) {
        FeeStatus = feeStatus;
    }

    public Integer getBespokeStatus() {
        return BespokeStatus;
    }

    public void setBespokeStatus(Integer bespokeStatus) {
        BespokeStatus = bespokeStatus;
    }

    public Integer getStudyStatus() {
        return StudyStatus;
    }

    public void setStudyStatus(Integer studyStatus) {
        StudyStatus = studyStatus;
    }

    public Date getArriveDate() {
        return ArriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        ArriveDate = arriveDate;
    }

    public String getQueueNo() {
        return QueueNo;
    }

    public void setQueueNo(String queueNo) {
        QueueNo = queueNo;
    }

    public String getRisExamNo() {
        return RisExamNo;
    }

    public void setRisExamNo(String risExamNo) {
        RisExamNo = risExamNo;
    }

    public String getRisAccessionNumber() {
        return RisAccessionNumber;
    }

    public void setRisAccessionNumber(String risAccessionNumber) {
        RisAccessionNumber = risAccessionNumber;
    }

    public Integer getGroupType() {
        return GroupType;
    }

    public void setGroupType(Integer groupType) {
        GroupType = groupType;
    }

    public Date getBespokeStudyDate() {
        return BespokeStudyDate;
    }

    public void setBespokeStudyDate(Date bespokeStudyDate) {
        BespokeStudyDate = bespokeStudyDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Integer getTimeCoefficient() {
        return TimeCoefficient;
    }

    public void setTimeCoefficient(Integer timeCoefficient) {
        TimeCoefficient = timeCoefficient;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getWarning() {
        return Warning;
    }

    public void setWarning(String warning) {
        Warning = warning;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPartID() {
        return PartID;
    }

    public void setPartID(Integer partID) {
        this.PartID = partID;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public String getStudyParts() {
        return studyParts;
    }

    public void setStudyParts(String studyParts) {
        this.studyParts = studyParts;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getOrdRowID() {
        return OrdRowID;
    }

    public void setOrdRowID(String ordRowID) {
        OrdRowID = ordRowID;
    }

    public String getHISStudyProjectCode() {
        return HISStudyProjectCode;
    }

    public void setHISStudyProjectCode(String HISStudyProjectCode) {
        this.HISStudyProjectCode = HISStudyProjectCode;
    }

    public String getHISStudyProject() {
        return HISStudyProject;
    }

    public void setHISStudyProject(String HISStudyProject) {
        this.HISStudyProject = HISStudyProject;
    }

    public String getHISStudyPartCode() {
        return HISStudyPartCode;
    }

    public void setHISStudyPartCode(String HISStudyPartCode) {
        this.HISStudyPartCode = HISStudyPartCode;
    }

    public String getHISStudyPart() {
        return HISStudyPart;
    }

    public void setHISStudyPart(String HISStudyPart) {
        this.HISStudyPart = HISStudyPart;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getAppointmentStatus() {
        return AppointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        AppointmentStatus = appointmentStatus;
    }

    public String getPreference() {
        return Preference;
    }

    public void setPreference(String preference) {
        Preference = preference;
    }

    public String getTimeFrame() {
        return TimeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        TimeFrame = timeFrame;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public List<TbOrderDetail> getTbOrderDetails() {
        return tbOrderDetails;
    }

    public void setTbOrderDetails(List<TbOrderDetail> tbOrderDetails) {
        this.tbOrderDetails = tbOrderDetails;
    }

    public String getRisWaitNum() { return risWaitNum; }

    public void setRisWaitNum(String risWaitNum) { this.risWaitNum = risWaitNum; }

    public String getsArriveDate() {
        return sArriveDate;
    }

    public void setsArriveDate(String sArriveDate) {
        this.sArriveDate = sArriveDate;
    }

    public String getZhuanyun() {return zhuanyun;}

    public void setZhuanyun(String zhuanyun) {this.zhuanyun = zhuanyun;}
}
