package com.annet.entity.operating;


import java.util.Date;

public class ResultTJ {
    // 预约单ID
    private String bespokeId;
    // 检查部位
    private String studyPart;
    // 申请单号
    private String requestNo;
    // 预约单号
    private String BespokeNo;
    //
    private String groupType;
    private String studyMethod;
    private String studyRoom;
    private String requestDept;
    private String requestDoctor;
    private String warning;
    private String bespokeDate;
    private String startTime;
    private String endTime;

    // 检查项目
    private String studyProject;
    // 报到时间
    private String ArriveTime;
    // 检查地点
    private String address;
    // 房间代码
    private String roomCode;
    //执行科室
    private String execDept;

    public String getBespokeId() {return bespokeId;}

    public void setBespokeId(String bespokeId) {this.bespokeId = bespokeId;}

    public String getStudyPart() {return studyPart;}

    public void setStudyPart(String studyPart) {
        this.studyPart = studyPart;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getBespokeNo() {
        return BespokeNo;
    }

    public void setBespokeNo(String bespokeNo) {
        BespokeNo = bespokeNo;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getStudyRoom() {
        return studyRoom;
    }

    public void setStudyRoom(String studyRoom) {
        this.studyRoom = studyRoom;
    }

    public String getRequestDept() {
        return requestDept;
    }

    public void setRequestDept(String requestDept) {
        this.requestDept = requestDept;
    }

    public String getRequestDoctor() {
        return requestDoctor;
    }

    public void setRequestDoctor(String requestDoctor) {
        this.requestDoctor = requestDoctor;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getBespokeDate() {
        return bespokeDate;
    }

    public void setBespokeDate(String bespokeDate) {
        this.bespokeDate = bespokeDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStudyProject() {
        return studyProject;
    }

    public void setStudyProject(String studyProject) {
        this.studyProject = studyProject;
    }

    public String getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.ArriveTime = arriveTime;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getRoomCode() { return roomCode; }

    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public String getExecDept() { return execDept; }

    public void setExecDept(String execDept) { this.execDept = execDept; }
}
