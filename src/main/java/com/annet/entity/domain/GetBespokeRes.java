package com.annet.entity.domain;

import java.sql.Date;

/**
 * 获取自选方案存储过程
 * @author
 */
public class GetBespokeRes {
    private Integer ResID;
    private Integer GroupType;
    private String GroupName;
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
    private Date NotBespokeResDay;

    public Date getNotBespokeResDay() { return NotBespokeResDay; }

    public void setNotBespokeResDay(Date notBespokeResDay) { NotBespokeResDay = notBespokeResDay; }

    public Integer getResID() {
        return ResID;
    }

    public void setResID(Integer resID) {
        ResID = resID;
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

    public Date getBespokeDate() {
        return BespokeDate;
    }

    public void setBespokeDate(Date bespokeDate) {
        BespokeDate = bespokeDate;
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

    public Integer getOutPatientNum() {
        return OutPatientNum;
    }

    public void setOutPatientNum(Integer outPatientNum) {
        OutPatientNum = outPatientNum;
    }

    public Integer getOutPatientBespokeNum() {
        return OutPatientBespokeNum;
    }

    public void setOutPatientBespokeNum(Integer outPatientBespokeNum) {
        OutPatientBespokeNum = outPatientBespokeNum;
    }

    public Integer getInPatientNum() {
        return InPatientNum;
    }

    public void setInPatientNum(Integer inPatientNum) {
        InPatientNum = inPatientNum;
    }

    public Integer getInPatientBespokeNum() {
        return InPatientBespokeNum;
    }

    public void setInPatientBespokeNum(Integer inPatientBespokeNum) {
        InPatientBespokeNum = inPatientBespokeNum;
    }

    public Integer getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(Integer totalNum) {
        TotalNum = totalNum;
    }

    public Integer getTotalBespokeNum() {
        return TotalBespokeNum;
    }

    public void setTotalBespokeNum(Integer totalBespokeNum) {
        TotalBespokeNum = totalBespokeNum;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }
}

