package com.annet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XiaYu
 * @since 2019-05-15
 */
@TableName("tb_method_part")
public class TbMethodPart extends Model<TbMethodPart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String Modality;
    private String HISStudyProjectCode;
    private String HISStudyProject;
    private String HISStudyPartCode;
    private String HISStudyPart;
    private String StudyMethod;
    private String StudyPartCode;
    private String StudyPart;
    private Integer TimeCoefficient;
    private String BespokeTimeSpan;
    private String Warning;
    private String HospitalName;
    private Integer IsMain;

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String Modality) {
        this.Modality = Modality;
    }

    public String getHISStudyProjectCode() {
        return HISStudyProjectCode;
    }

    public void setHISStudyProjectCode(String HISStudyProjectCode) {
        this.HISStudyProjectCode = HISStudyProjectCode;
    }

    public String getHISStudyProject() {
        return HISStudyProject;
    }

    public void setHISStudyProject(String HISStudyProject) {
        this.HISStudyProject = HISStudyProject;
    }

    public String getHISStudyPartCode() {
        return HISStudyPartCode;
    }

    public void setHISStudyPartCode(String HISStudyPartCode) {
        this.HISStudyPartCode = HISStudyPartCode;
    }

    public String getHISStudyPart() {
        return HISStudyPart;
    }

    public void setHISStudyPart(String HISStudyPart) {
        this.HISStudyPart = HISStudyPart;
    }

    public String getStudyMethod() {
        return StudyMethod;
    }

    public void setStudyMethod(String StudyMethod) {
        this.StudyMethod = StudyMethod;
    }

    public String getStudyPartCode() {
        return StudyPartCode;
    }

    public void setStudyPartCode(String StudyPartCode) {
        this.StudyPartCode = StudyPartCode;
    }

    public String getStudyPart() {
        return StudyPart;
    }

    public void setStudyPart(String StudyPart) {
        this.StudyPart = StudyPart;
    }

    public Integer getTimeCoefficient() {
        return TimeCoefficient;
    }

    public void setTimeCoefficient(Integer TimeCoefficient) {
        this.TimeCoefficient = TimeCoefficient;
    }

    public String getBespokeTimeSpan() {
        return BespokeTimeSpan;
    }

    public void setBespokeTimeSpan(String BespokeTimeSpan) {
        this.BespokeTimeSpan = BespokeTimeSpan;
    }

    public String getWarning() {
        return Warning;
    }

    public void setWarning(String Warning) {
        this.Warning = Warning;
    }

    public Integer getIsMain() {
        return IsMain;
    }

    public void setIsMain(Integer isMain) {
        this.IsMain = isMain;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbMethodPart{" +
                "id=" + id +
                ", Modality='" + Modality + '\'' +
                ", HISStudyProjectCode='" + HISStudyProjectCode + '\'' +
                ", HISStudyProject='" + HISStudyProject + '\'' +
                ", HISStudyPartCode='" + HISStudyPartCode + '\'' +
                ", HISStudyPart='" + HISStudyPart + '\'' +
                ", StudyMethod='" + StudyMethod + '\'' +
                ", StudyPartCode='" + StudyPartCode + '\'' +
                ", StudyPart='" + StudyPart + '\'' +
                ", TimeCoefficient=" + TimeCoefficient +
                ", BespokeTimeSpan='" + BespokeTimeSpan + '\'' +
                ", Warning='" + Warning + '\'' +
                ", HospitalName='" + HospitalName + '\'' +
                ", IsMain=" + IsMain +
                '}';
    }
}
