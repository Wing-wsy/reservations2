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
@TableName("tb_studymethod")
public class TbStudymethod extends Model<TbStudymethod> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MethodID", type = IdType.AUTO)
    private Integer MethodID;
    private String Modality;
    private String MethodName;
    private Integer BespokeDays;
    private String HospitalName;
    private Integer Status;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getMethodID() {
        return MethodID;
    }

    public void setMethodID(Integer MethodID) {
        this.MethodID = MethodID;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String Modality) {
        this.Modality = Modality;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName = MethodName;
    }

    public Integer getBespokeDays() {
        return BespokeDays;
    }

    public void setBespokeDays(Integer BespokeDays) {
        this.BespokeDays = BespokeDays;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    @Override
    protected Serializable pkVal() {
        return this.MethodID;
    }

    @Override
    public String toString() {
        return "TbStudymethod{" +
                "MethodID=" + MethodID +
                ", Modality='" + Modality + '\'' +
                ", MethodName='" + MethodName + '\'' +
                ", BespokeDays=" + BespokeDays +
                ", HospitalName='" + HospitalName + '\'' +
                '}';
    }
}
