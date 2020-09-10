package com.annet.mapper;

import com.annet.entity.TbRule;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * 权限
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbRuleMapper extends BaseMapper<TbRule> {

    /**
     * 权限列表
     * @return
     */
    List<TbRule> selectfinAll();

    /**
     * 添加权限
     * @param tbRule
     * @return
     */
    Integer insertTbRule(TbRule tbRule);

    /**
     * 修改权限
     * @param tbRule
     * @return
     */
    Integer updateTbRule(TbRule tbRule);

    /**
     * 删除权限
     * @param RuleKey
     * @return
     */
    Integer delectTbRule(@Param("RuleKey") String RuleKey);
}
