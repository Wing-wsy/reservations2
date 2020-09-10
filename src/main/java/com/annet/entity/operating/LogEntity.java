package com.annet.entity.operating;

import java.util.Date;

/**
 * 用于记录号源修改
 * @author
 */
public class LogEntity {

    private Integer id;

    private String resID;

    private String operaction;

    private String HospitalType;

    private String patientNum;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getOperaction() {
        return operaction;
    }

    public void setOperaction(String operaction) {
        this.operaction = operaction;
    }

    public String getHospitalType() {
        return HospitalType;
    }

    public void setHospitalType(String hospitalType) {
        HospitalType = hospitalType;
    }

    public String getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LogEntity{" +
                "id=" + id +
                ", resID='" + resID + '\'' +
                ", operaction='" + operaction + '\'' +
                ", HospitalType='" + HospitalType + '\'' +
                ", patientNum='" + patientNum + '\'' +
                ", date=" + date +
                '}';
    }
}
