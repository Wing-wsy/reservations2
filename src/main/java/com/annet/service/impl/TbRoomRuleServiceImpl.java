package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbRoomRule;
import com.annet.mapper.TbRoomRuleMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbRoomRuleService;
import com.annet.utils.StrUtils;
import com.annet.vo.TbRoomRuleVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jh
 * @since 2020-01-13
 */
@Service
@AllArgsConstructor
public class TbRoomRuleServiceImpl extends ServiceImpl<TbRoomRuleMapper, TbRoomRule> implements TbRoomRuleService {

    private final TbRoomRuleMapper tbRoomRuleMapper;

    /**
     * 查询房间预约规则对象列表
     *
     * @param hospitalName 院区名称
     * @param deptName     科室名称
     * @return 房间预约规则对象集合
     */
    @Override
    public R<List<TbRoomRule>> findAll(String hospitalName, String deptName) {
        if (hospitalName == null) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
            List<TbRoomRule> tbRoomRules = tbRoomRuleMapper.findAll(hospitalName);
            return new R(tbRoomRules);
        } else {
            // 科室人员
            return new R(tbRoomRuleMapper.findAllByDeptName(hospitalName, deptName));
        }
    }

    @Override
    public R<List<TbRoomRule>> findAllNew(TbRoomRuleVo tbRoomRuleVo) {
        if (tbRoomRuleVo.getHospitalName() == null) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        if (tbRoomRuleVo.getDeptName().equals(ConstantClass.ADMINISTRATOR)) {
            return new R(tbRoomRuleMapper.findAllNew(tbRoomRuleVo));
        } else {
            // 科室人员
            return new R(tbRoomRuleMapper.findAllByDeptNameNew(tbRoomRuleVo));
        }
    }

    /**
     * 新增房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    @Override
    @Transactional
    public R<TbRoomRule> add(TbRoomRule tbRoomRule) {
        if (StrUtils.isNullOrEmpty(tbRoomRule.getHospitalName())) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        Integer result = tbRoomRuleMapper.insertTbRoomRule(tbRoomRule);
        if (result == null) {
            //throw new MyException("新增失败");
            return new R(1,"新增失败");
        }
        return new R(tbRoomRule);
    }

    /**
     * 修改房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    @Override
    @Transactional
    public R<TbRoomRule> update(TbRoomRule tbRoomRule) {
        if (StrUtils.isNullOrEmpty(tbRoomRule.getHospitalName())) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        Integer result = tbRoomRuleMapper.updateTbRoomRule(tbRoomRule);
        if (result == null) {
            //throw new MyException("修改失败");
            return new R(1,"修改失败");
        }
        return new R(tbRoomRule);
    }

    @Override
    @Transactional
    public R<Integer> delete(Integer integer) {
        if (integer == null) {
            //throw new MyException("参数必传不能为空");
            return new R(1,"参数必传不能为空");
        }
        Integer result = tbRoomRuleMapper.deleteTbRoomRule(integer);
        if (result == null) {
            //throw new MyException("删除失败");
            return new R(1,"删除失败");
        }
        return new R(result);
    }
}
