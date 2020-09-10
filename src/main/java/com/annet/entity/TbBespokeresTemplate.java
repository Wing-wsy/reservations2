package com.annet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@TableName("tb_bespokeres_template")
public class TbBespokeresTemplate extends Model<TbBespokeresTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String Modality;
    private Integer GroupType;
    private String GroupName;
    private String WeekDay;
    private String StartTime;
    private String EndTime;
    private Integer OutPatientNum;
    private Integer InPatientNum;
    private Integer TotalNum;
    private String HospitalName;
    private Integer ResType;
    // 自定义的参数属性（非表字段）
    private String DeptName;

    public Integer getResType() { return ResType; }

    public void setResType(Integer resType) { ResType = resType; }

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

    public String getWeekDay() {
        return WeekDay;
    }

    public void setWeekDay(String WeekDay) {
        this.WeekDay = WeekDay;
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

    public Integer getInPatientNum() {
        return InPatientNum;
    }

    public void setInPatientNum(Integer InPatientNum) {
        this.InPatientNum = InPatientNum;
    }

    public Integer getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(Integer TotalNum) {
        this.TotalNum = TotalNum;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
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
        return "TbBespokeresTemplate{" +
                "id=" + id +
                ", Modality='" + Modality + '\'' +
                ", GroupType=" + GroupType +
                ", GroupName='" + GroupName + '\'' +
                ", WeekDay='" + WeekDay + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", OutPatientNum=" + OutPatientNum +
                ", InPatientNum=" + InPatientNum +
                ", TotalNum=" + TotalNum +
                ", HospitalName='" + HospitalName + '\'' +
                ", DeptName='" + DeptName + '\'' +
                '}';
    }
}
