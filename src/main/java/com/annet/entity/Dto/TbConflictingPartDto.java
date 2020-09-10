package com.annet.entity.Dto;

import lombok.Data;

@Data
public class TbConflictingPartDto {
    // id
    private Integer cpID;
    // 设备
    private String cpModality;
    // 互斥设备
    private String cpCModality;
    // 检查部位id
    private Integer cpPartID;
    //检查部位名称
    private String mpStudyPart;
    //检查方法
    private String mpStudyMethod;
    // 互斥部位id
    private Integer cpCPaerID;
    // 互斥部位名称
    private String mpsStudyPart;
    // 互斥方法
    private String mpsStudyMethod;
    // 互斥类型
    private Integer conflictingType;
    // 互斥时间
    private Integer conflictingHour;


    public Integer getCpID() {
        return cpID;
    }

    public void setCpID(Integer cpID) {
        this.cpID = cpID;
    }

    public String getCpModality() {
        return cpModality;
    }

    public void setCpModality(String cpModality) {
        this.cpModality = cpModality;
    }

    public String getCpCModality() {
        return cpCModality;
    }

    public void setCpCModality(String cpCModality) {
        this.cpCModality = cpCModality;
    }

    public Integer getCpPartID() {
        return cpPartID;
    }

    public void setCpPartID(Integer cpPartID) {
        this.cpPartID = cpPartID;
    }

    public String getMpStudyPart() {
        return mpStudyPart;
    }

    public void setMpStudyPart(String mpStudyPart) {
        this.mpStudyPart = mpStudyPart;
    }

    public Integer getCpCPaerID() {
        return cpCPaerID;
    }

    public void setCpCPaerID(Integer cpCPaerID) {
        this.cpCPaerID = cpCPaerID;
    }

    public String getMpsStudyPart() {
        return mpsStudyPart;
    }

    public void setMpsStudyPart(String mpsStudyPart) {
        this.mpsStudyPart = mpsStudyPart;
    }

    public Integer getConflictingType() {
        return conflictingType;
    }

    public void setConflictingType(Integer conflictingType) {
        this.conflictingType = conflictingType;
    }

    public Integer getConflictingHour() {
        return conflictingHour;
    }

    public void setConflictingHour(Integer conflictingHour) {
        this.conflictingHour = conflictingHour;
    }

    public String getMpStudyMethod() { return mpStudyMethod; }

    public void setMpStudyMethod(String mpStudyMethod) { this.mpStudyMethod = mpStudyMethod; }

    public String getMpsStudyMethod() { return mpsStudyMethod; }

    public void setMpsStudyMethod(String mpsStudyMethod) { this.mpsStudyMethod = mpsStudyMethod; }
}
