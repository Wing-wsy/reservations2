package com.annet.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XiaYu
 * @since 2019-08-01
 */
@TableName("tb_bespokeres_lsb")
public class TbBespokeresLsb extends Model<TbBespokeresLsb> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String UserID;
    private Integer ResID;
    private String HospitalType;
    private Integer PatientNum;
    private Integer PartID;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public Integer getResID() {
        return ResID;
    }

    public void setResID(Integer ResID) {
        this.ResID = ResID;
    }

    public String getHospitalType() {
        return HospitalType;
    }

    public void setHospitalType(String HospitalType) {
        this.HospitalType = HospitalType;
    }

    public Integer getPatientNum() {
        return PatientNum;
    }

    public void setPatientNum(Integer PatientNum) {
        this.PatientNum = PatientNum;
    }

    public Integer getPartID() {
        return PartID;
    }

    public void setPartID(Integer PartID) {
        this.PartID = PartID;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "TbBespokeresLsb{" +
        ", id=" + id +
        ", UserID=" + UserID +
        ", ResID=" + ResID +
        ", HospitalType=" + HospitalType +
        ", PatientNum=" + PatientNum +
        ", PartID=" + PartID +
        "}";
    }
}
