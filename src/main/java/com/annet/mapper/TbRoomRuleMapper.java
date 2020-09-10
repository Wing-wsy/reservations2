package com.annet.mapper;

import com.annet.entity.TbRoomRule;
import com.annet.vo.TbRoomRuleVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jh
 * @since 2020-01-13
 */
public interface TbRoomRuleMapper extends BaseMapper<TbRoomRule> {

    Integer insertTbRoomRule(TbRoomRule tbRoomRule);

    Integer updateTbRoomRule(TbRoomRule tbRoomRule);

    Integer deleteTbRoomRule(@Param("id") Integer id);

    List<TbRoomRule> findAll(@Param("hospitalName") String hospitalName);

    List<TbRoomRule> findAllNew(TbRoomRuleVo tbRoomRuleVo);

    List<TbRoomRule> findAllByDeptName(@Param("hospitalName") String hospitalName,
                                       @Param("deptName") String deptName);
    List<TbRoomRule> findAllByDeptNameNew(TbRoomRuleVo tbRoomRuleVo);
}
