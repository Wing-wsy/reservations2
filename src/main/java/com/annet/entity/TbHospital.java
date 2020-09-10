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
 * @since 2019-11-01
 */
@TableName("tb_hospital")
public class TbHospital extends Model<TbHospital> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalID", type = IdType.AUTO)
    private Integer HospitalID;
    private String HospitalName;


    public Integer getHospitalID() {
        return HospitalID;
    }

    public void setHospitalID(Integer HospitalID) {
        this.HospitalID = HospitalID;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    @Override
    protected Serializable pkVal() {
        return this.HospitalID;
    }

    @Override
    public String toString() {
        return "TbHospital{" +
        ", HospitalID=" + HospitalID +
        ", HospitalName=" + HospitalName +
        "}";
    }
}
