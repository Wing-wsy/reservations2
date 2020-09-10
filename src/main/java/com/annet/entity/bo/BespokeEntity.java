package com.annet.entity.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author: wsy
 * @Date: 2020/8/6 9:43
 * @Version: 1.0
 * @Description: 用于判断是否取消预约三次
 */
@Data
public class BespokeEntity {

    private List<String> execDeptList;

    private String id;

    private String idType;

    private String execDept;



}
