package com.annet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jh
 * @since 2020-01-13
 */
@TableName("tb_room_rule")
public class TbRoomRule extends Model<TbRoomRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    //@JsonProperty("id")
    private Integer id;
    //@JsonProperty("Modality")
    private String Modality;
    //@JsonProperty("RoomName")
    private String RoomName;
    //@JsonProperty("WeekDay")
    private String WeekDay;
    //@JsonProperty("StartTime")
    private String StartTime;
    //@JsonProperty("EndTime")
    private String EndTime;
    //@JsonProperty("MethodName")
    private String MethodName;
   // @JsonProperty("StudyPart")
    private String StudyPart;
   // @JsonProperty("HospitalName")
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

    public void setModality(String Modality) {
        this.Modality = Modality;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }

    public String getWeekDay() {
        return WeekDay;
    }

    public void setWeekDay(String WeekDay) {
        this.WeekDay = WeekDay;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName = MethodName;
    }

    public String getStudyPart() {
        return StudyPart;
    }

    public void setStudyPart(String StudyPart) {
        this.StudyPart = StudyPart;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbRoomRule{" +
        ", id=" + id +
        ", Modality=" + Modality +
        ", RoomName=" + RoomName +
        ", WeekDay=" + WeekDay +
        ", StartTime=" + StartTime +
        ", EndTime=" + EndTime +
        ", MethodName=" + MethodName +
        ", StudyPart=" + StudyPart +
        ", HospitalName=" + HospitalName +
        "}";
    }
}
