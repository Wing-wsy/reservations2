package com.annet.entity.domain;

import lombok.Data;

/**
 * @Author: jh
 * @Date: 2019/12/10 14:30
 * @Version: 1.0
 * @Description: 存储过程查询出报到调用RIS提供的webservice接口入参
 */
@Data
public class GetPacsSelfRegister {

    private Integer ID;

    private String RequestNo;

    private Integer BespokeID;

    private String XMLData;
}
