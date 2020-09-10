package com.annet.service;

import com.annet.entity.TbRoomRule;
import com.annet.result.R;
import com.annet.vo.TbRoomRuleVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jh
 * @since 2020-01-13
 */
public interface TbRoomRuleService extends IService<TbRoomRule> {

    /**
     * 查询房间预约规则对象列表
     *
     * @param hospitalName 院区名称
     * @param deptName     科室名称
     * @return 房间预约规则对象集合
     */
    R<List<TbRoomRule>> findAll(String hospitalName, String deptName);

    R<List<TbRoomRule>> findAllNew(TbRoomRuleVo tbRoomRuleVo);
    /**
     * 新增房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    R<TbRoomRule> add(TbRoomRule tbRoomRule);

    /**
     * 修改房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    R<TbRoomRule> update(TbRoomRule tbRoomRule);

    R<Integer> delete(Integer integer);
}
