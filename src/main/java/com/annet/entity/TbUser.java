package com.annet.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
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
@TableName("tb_user")
@ApiModel(description = "TbUser")
public class TbUser extends Model<TbUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserID", type = IdType.AUTO)
    private String UserID;
    private String UserName;
    private String Password;
    private String DeptName;
    private String RuleList;
    private Integer Status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date AddDate;
    private String HospitalName;

    //
    private String DeptCode;
    private String UserCode;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getRuleList() {
        return RuleList;
    }

    public void setRuleList(String ruleList) {
        RuleList = ruleList;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Date getAddDate() {
        return AddDate;
    }

    public void setAddDate(Date addDate) {
        AddDate = addDate;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getDeptCode() {
        return DeptCode;
    }

    public void setDeptCode(String DeptCode) {
        this.DeptCode = DeptCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.UserID;
    }

    @Override
    public String toString() {
        return "TbUser{" +
                "UserID='" + UserID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", RuleList='" + RuleList + '\'' +
                ", Status=" + Status +
                ", AddDate=" + AddDate +
                ", HospitalName='" + HospitalName + '\'' +
                ", DeptCode='" + DeptCode + '\'' +
                ", UserCode='" + UserCode + '\'' +
                '}';
    }
}
