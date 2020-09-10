package com.annet.vo;

import lombok.Data;

/**
 * @program: work
 * @description:
 * @author: Ghost
 * @create: 2020-03-25 14:15
 **/
@Data
public class TbRoomRuleVo {

    private String modality;
    private String roomName;
    private String weekDay;
    private String startTime;
    private String endTime;
    private String methodName;
    private String studyPart;
    private String hospitalName;
    private String deptName;

}
