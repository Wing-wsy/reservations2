package com.annet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @since 2019-11-28
 */
@TableName("tb_order_detail")
public class TbOrderDetail extends Model<TbOrderDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private Integer BespokeID;
    private String OrdRowID;
    private String HISStudyProjectCode;
    private String HISStudyProject;
    private String HISStudyPartCode;
    private String HISStudyPart;
    private BigDecimal Fee;
    private String RequestNo;
    // 自定义的字段
    private String Modality;
    private String PartId;
    private Integer TimeCoefficient;
    private String Warning;
    //
    private String vHISJPGFile;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbOrderDetail{" + "id=" + id + ", BespokeID=" + BespokeID + ", OrdRowID='" + OrdRowID + '\'' + ", HISStudyProjectCode='" + HISStudyProjectCode + '\'' + ", HISStudyProject='" + HISStudyProject + '\'' + ", HISStudyPartCode='" + HISStudyPartCode + '\'' + ", HISStudyPart='" + HISStudyPart + '\'' + ", Fee=" + Fee + ", RequestNo='" + RequestNo + '\'' + ", Modality='" + Modality + '\'' + ", PartId='" + PartId + '\'' + ", TimeCoefficient=" + TimeCoefficient + ", Warning='" + Warning + '\'' + ", vHISJPGFile='" + vHISJPGFile + '\'' + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBespokeID() {
        return BespokeID;
    }

    public void setBespokeID(Integer bespokeID) {
        BespokeID = bespokeID;
    }

    public String getOrdRowID() {
        return OrdRowID;
    }

    public void setOrdRowID(String ordRowID) {
        OrdRowID = ordRowID;
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

    public BigDecimal getFee() {
        return Fee;
    }

    public void setFee(BigDecimal fee) {
        Fee = fee;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }

    public String getRequestNo() {
        return RequestNo;
    }

    public void setRequestNo(String requestNo) {
        RequestNo = requestNo;
    }

    public String getPartId() {
        return PartId;
    }

    public void setPartId(String partId) {
        PartId = partId;
    }

    public Integer getTimeCoefficient() {
        return TimeCoefficient;
    }

    public void setTimeCoefficient(Integer timeCoefficient) {
        TimeCoefficient = timeCoefficient;
    }

    public String getWarning() {
        return Warning;
    }

    public void setWarning(String warning) {
        Warning = warning;
    }

    public String getvHISJPGFile() {
        return vHISJPGFile;
    }

    public void setvHISJPGFile(String vHISJPGFile) {
        this.vHISJPGFile = vHISJPGFile;
    }
}
