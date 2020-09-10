package com.annet.entity.bo;

import lombok.Data;

/**
 * @Author: jh
 * @Date: 2019/12/3 9:43
 * @Version: 1.0
 * @Description: 申请单相信对象
 */
@Data
public class RequestInfo {

    private Integer requestNo;

    private Integer BespokeID;

    private String OrdRowID;

    private String HISStudyProjectCode;

    private String HISStudyProject;

    private String HISStudyPartCode;

    private String HISStudyPart;

    private String fee;

}
