package com.annet.vo;

import lombok.Data;

/**
 * @program: work
 * @description:
 * @author: Ghost
 * @create: 2020-03-25 11:06
 **/
@Data
public class TbMethodPartVo {
    private Integer id;
    private String modality;
    private String hisStudyProjectCode;
    private String hisStudyProject;
    private String hisStudyPartCode;
    private String hisStudyPart;
    private String studyMethod;
    private String studyPartCode;
    private String studyPart;
    private Integer timeCoefficient;
    private String bespokeTimeSpan;
    private String warning;
    private String hospitalName;
    private Integer isMain;
    private String deptName;
}
