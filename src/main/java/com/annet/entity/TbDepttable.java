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
@TableName("tb_depttable")
public class TbDepttable extends Model<TbDepttable> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DeptID", type = IdType.AUTO)
    private Integer DeptID;
    private String DeptCode;
    private String DeptName;
    private String HospitalName;
    private Integer IsReserve;
    private Integer histype;
    private String DeptPYM;


    public Integer getDeptID() {
        return DeptID;
    }

    public void setDeptID(Integer DeptID) {
        this.DeptID = DeptID;
    }

    public String getDeptCode() {
        return DeptCode;
    }

    public void setDeptCode(String DeptCode) {
        this.DeptCode = DeptCode;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public Integer getIsReserve() {
        return IsReserve;
    }

    public void setIsReserve(Integer isReserve) {
        IsReserve = isReserve;
    }

    @Override
    protected Serializable pkVal() {
        return this.DeptID;
    }

    public Integer getHistype() { return histype; }

    public void setHistype(Integer histype) { this.histype = histype; }

    public String getDeptPYM() { return DeptPYM; }

    public void setDeptPYM(String deptPYM) { DeptPYM = deptPYM; }

    @Override
    public String toString() {
        return "TbDepttable{" +
                "DeptID=" + DeptID +
                ", DeptCode='" + DeptCode + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", HospitalName='" + HospitalName + '\'' +
                ", IsReserve=" + IsReserve +
                ", histype=" + histype +
                ", DeptPYM='" + DeptPYM + '\'' +
                '}';
    }
}
