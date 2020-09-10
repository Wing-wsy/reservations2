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
 * @since 2019-11-04
 */
@TableName("tb_suspend_medical_service")
public class TbSuspendMedicalService extends Model<TbSuspendMedicalService> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String Modality;
    private Integer GroupType;
    private String GroupName;
    private String StartDate;
    private String EndDate;
    private String HospitalName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }

    public Integer getGroupType() {
        return GroupType;
    }

    public void setGroupType(Integer groupType) {
        GroupType = groupType;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
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

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbSuspendMedicalService{" +
                "id=" + id +
                ", Modality='" + Modality + '\'' +
                ", GroupType=" + GroupType +
                ", GroupName='" + GroupName + '\'' +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", HospitalName='" + HospitalName + '\'' +
                '}';
    }
}
