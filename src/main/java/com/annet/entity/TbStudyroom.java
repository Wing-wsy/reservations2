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
@TableName("tb_studyroom")
public class TbStudyroom extends Model<TbStudyroom> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RoomID", type = IdType.AUTO)
    private Integer RoomID;
    private String DeptName;
    private String Modality;
    private String RoomName;
    private String Device;
    private Integer BespokeDays;
    private Integer Status;
    private Integer PreciseTime;
    private String HospitalName;
    private String RoomCode;
    private String Address;


    public Integer getRoomID() {
        return RoomID;
    }

    public void setRoomID(Integer RoomID) {
        this.RoomID = RoomID;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
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

    public String getDevice() {
        return Device;
    }

    public void setDevice(String Device) {
        this.Device = Device;
    }

    public Integer getBespokeDays() {
        return BespokeDays;
    }

    public void setBespokeDays(Integer BespokeDays) {
        this.BespokeDays = BespokeDays;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public Integer getPreciseTime() {
        return PreciseTime;
    }

    public void setPreciseTime(Integer preciseTime) {
        PreciseTime = preciseTime;
    }

    public String getRoomCode() { return RoomCode; }

    public void setRoomCode(String roomCode) { RoomCode = roomCode; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    @Override
    protected Serializable pkVal() {
        return this.RoomID;
    }

    @Override
    public String toString() {
        return "TbStudyroom{" +
                "RoomID=" + RoomID +
                ", DeptName='" + DeptName + '\'' +
                ", Modality='" + Modality + '\'' +
                ", RoomName='" + RoomName + '\'' +
                ", Device='" + Device + '\'' +
                ", BespokeDays=" + BespokeDays +
                ", Status=" + Status +
                ", PreciseTime=" + PreciseTime +
                ", HospitalName='" + HospitalName + '\'' +
                ", RoomCode='" + RoomCode + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
