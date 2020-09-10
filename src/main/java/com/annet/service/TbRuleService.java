package com.annet.service;

import com.annet.entity.TbRule;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 权限
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbRuleService extends IService<TbRule> {

    /**
     * 权限列表
     * @return
     */
    List<TbRule> finall();
}
