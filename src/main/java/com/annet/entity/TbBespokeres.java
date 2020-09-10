package com.annet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;



/**
 * <p>
 * 
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@TableName("tb_bespokeres")
public class TbBespokeres extends Model<TbBespokeres> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String Modality;
    private Integer GroupType;
    private String GroupName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date BespokeDate;
    private String StartTime;
    private String EndTime;
    private Integer OutPatientNum;
    private Integer OutPatientBespokeNum;
    private Integer InPatientNum;
    private Integer InPatientBespokeNum;
    private Integer TotalNum;
    private Integer TotalBespokeNum;
    private String HospitalName;
    private Integer ResType;

    // 自定义参数字段
    private String DeptName;

    public Integer getResType() { return ResType; }

    public void setResType(Integer resType) { ResType = resType; }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String Modality) {
        this.Modality = Modality;
    }

    public Integer getGroupType() {
        return GroupType;
    }

    public void setGroupType(Integer GroupType) {
        this.GroupType = GroupType;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public Date getBespokeDate() {
        return BespokeDate;
    }

    public void setBespokeDate(Date BespokeDate) {
        this.BespokeDate = BespokeDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public Integer getOutPatientNum() {
        return OutPatientNum;
    }

    public void setOutPatientNum(Integer OutPatientNum) {
        this.OutPatientNum = OutPatientNum;
    }

    public Integer getOutPatientBespokeNum() {
        return OutPatientBespokeNum;
    }

    public void setOutPatientBespokeNum(Integer OutPatientBespokeNum) {
        this.OutPatientBespokeNum = OutPatientBespokeNum;
    }

    public Integer getInPatientNum() {
        return InPatientNum;
    }

    public void setInPatientNum(Integer InPatientNum) {
        this.InPatientNum = InPatientNum;
    }

    public Integer getInPatientBespokeNum() {
        return InPatientBespokeNum;
    }

    public void setInPatientBespokeNum(Integer InPatientBespokeNum) {
        this.InPatientBespokeNum = InPatientBespokeNum;
    }

    public Integer getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(Integer TotalNum) {
        this.TotalNum = TotalNum;
    }

    public Integer getTotalBespokeNum() {
        return TotalBespokeNum;
    }

    public void setTotalBespokeNum(Integer TotalBespokeNum) {
        this.TotalBespokeNum = TotalBespokeNum;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbBespokeres{" +
                "id=" + id +
                ", Modality='" + Modality + '\'' +
                ", GroupType=" + GroupType +
                ", GroupName='" + GroupName + '\'' +
                ", BespokeDate=" + BespokeDate +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", OutPatientNum=" + OutPatientNum +
                ", OutPatientBespokeNum=" + OutPatientBespokeNum +
                ", InPatientNum=" + InPatientNum +
                ", InPatientBespokeNum=" + InPatientBespokeNum +
                ", TotalNum=" + TotalNum +
                ", TotalBespokeNum=" + TotalBespokeNum +
                ", HospitalName='" + HospitalName + '\'' +
                ", DeptName='" + DeptName + '\'' +
                '}';
    }
}
