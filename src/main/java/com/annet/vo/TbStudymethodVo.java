package com.annet.vo;

import lombok.Data;

/**
 * @program: work
 * @description:
 * @author: Ghost
 * @create: 2020-03-25 13:23
 **/
@Data
public class TbStudymethodVo {
    private Integer methodId;
    private String modality;
    private String methodName;
    private Integer bespokeDays;
    private String hospitalName;
    private String deptName;
}
