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
@TableName("tb_conflicting_part")
public class TbConflictingPart extends Model<TbConflictingPart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String Modality;
    private Integer PartID;
    private String ConflictingModality;
    private Integer ConflictingPartID;
    private Integer ConflictingType;
    private Integer ConflictingHour;
    private String HospitalName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }

    public Integer getPartID() {
        return PartID;
    }

    public void setPartID(Integer partID) {
        PartID = partID;
    }

    public String getConflictingModality() {
        return ConflictingModality;
    }

    public void setConflictingModality(String conflictingModality) {
        ConflictingModality = conflictingModality;
    }

    public Integer getConflictingPartID() {
        return ConflictingPartID;
    }

    public void setConflictingPartID(Integer conflictingPartID) {
        ConflictingPartID = conflictingPartID;
    }

    public Integer getConflictingType() {
        return ConflictingType;
    }

    public void setConflictingType(Integer conflictingType) {
        ConflictingType = conflictingType;
    }

    public Integer getConflictingHour() {
        return ConflictingHour;
    }

    public void setConflictingHour(Integer conflictingHour) {
        ConflictingHour = conflictingHour;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbConflictingPart{" +
                "id=" + id +
                ", Modality='" + Modality + '\'' +
                ", PartID=" + PartID +
                ", ConflictingModality='" + ConflictingModality + '\'' +
                ", ConflictingPartID=" + ConflictingPartID +
                ", ConflictingType=" + ConflictingType +
                ", ConflictingHour=" + ConflictingHour +
                ", HospitalName='" + HospitalName + '\'' +
                '}';
    }
}
