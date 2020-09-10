package com.annet.service.impl;

import com.annet.entity.TbRule;
import com.annet.mapper.TbRuleMapper;
import com.annet.service.TbRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * 权限
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbRuleServiceImpl extends ServiceImpl<TbRuleMapper, TbRule> implements TbRuleService {

    private final TbRuleMapper tbRuleMapper;

    /**
     * 权限列表
     * @return
     */
    @Override
    public List<TbRule> finall() {
        return tbRuleMapper.selectfinAll();
    }
}
