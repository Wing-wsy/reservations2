package com.annet.entity.domain;

import java.util.Date;

import javax.persistence.Id;

/**
 * @Entity
 * @NamedStoredProcedureQueries({
 * @NamedStoredProcedureQuery( name = "getRecommendation",
 * procedureName = "PROC_GetRecommendation",
 * resultClasses = { GetRecommendation.class },
 * parameters = {
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "RequestXml",type = String.class),
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "Preference",type = Integer.class),
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "StartDate",type = String.class),
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "EndDate",type = String.class),
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "TimeFrame",type = Integer.class)
 * }
 * )
 * })
 * @Data
 * @NoArgsConstructor
 * @AllArgsConstructor
 */

/**
 * 生成预约推荐方案的存储过程
 * @author
 */
public class GetRecommendation {
    @Id
    /**
     * @GeneratedValue
     */
    private Integer PartID;
    private String HospitalType;
    private Integer GroupType;
    private String GroupName;
    private Date BespokeDate;
    private String StartTime;
    private String EndTime;
    private Integer ResID;
    private String HospitalName;

    private String Modality;

    public Integer getPartID() {
        return PartID;
    }

    public void setPartID(Integer partID) {
        PartID = partID;
    }

    public String getHospitalType() {
        return HospitalType;
    }

    public void setHospitalType(String hospitalType) {
        HospitalType = hospitalType;
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

    public Integer getResID() {
        return ResID;
    }

    public void setResID(Integer resID) {
        ResID = resID;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }
}
