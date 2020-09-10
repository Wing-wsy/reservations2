package com.annet.entity.bo;

import com.annet.entity.TbBespoke;
import com.annet.entity.TbOrderDetail;
import com.annet.xmlEntity.GetRequestInfo;

import java.util.List;

import lombok.Data;

/**
 * @Author: jh
 * @Date: 2019/12/3 9:38
 * @Version: 1.0
 * @Description:
 */
@Data
public class TbBespokeBo {

    // 预约对象
    private TbBespoke tbBespoke;
    // 部位id
    private String NPartId;
    // 耗时
    private String nTimeCoefficient;

    private GetRequestInfo getRequestInfo;

    // 细节详情对象集合
    private List<TbOrderDetail> tbOrderDetails;
}
