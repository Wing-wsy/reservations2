package com.annet.entity.operating;

/**
 * 用于回滚号源的
 * @author
 */
public class HgBespoke{
    /**
     * 号源主键
     */
    private Integer resID;
    /**
     * 就诊方式：住院：Y 门诊:N 体检:E
     */
    private String hospitalType;
    /**
     * 所占用号源
     */
    private Integer patientNum;
    /**
     * 方法部位ID
     */
    private Integer partID;

    public Integer getResID() {
        return resID;
    }

    public void setResID(Integer resID) {
        this.resID = resID;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public Integer getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(Integer patientNum) {
        this.patientNum = patientNum;
    }

    public Integer getPartID() {
        return partID;
    }

    public void setPartID(Integer partID) {
        this.partID = partID;
    }
}
