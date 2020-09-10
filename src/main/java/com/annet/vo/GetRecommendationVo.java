package com.annet.vo;

import com.annet.entity.TbBespoke;
import com.annet.entity.TbOrderDetail;
import com.annet.entity.domain.GetRecommendation;
import com.annet.xmlEntity.GetRequestInfo;

import java.util.List;

import lombok.Data;

/**
 * @Author: jh
 * @Date: 2019/12/2 21:18
 * @Version: 1.0
 * @Description:
 */
@Data
public class GetRecommendationVo {

    private List<GetRecommendation> getRecommendationList;
    // 申请单集合
    private List<GetRequestInfo> getRequestInfoList;

    // 预约对象
    private TbBespoke tbBespoke;

    private List<TbOrderDetail> tbOrderDetailList;

    private String userID;

}
