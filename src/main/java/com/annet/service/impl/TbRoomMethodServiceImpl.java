package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbRoomMethod;
import com.annet.mapper.TbRoomMethodMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbRoomMethodService;
import com.annet.vo.TbRoomMethodVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 * 房间支持检查方法表
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbRoomMethodServiceImpl extends ServiceImpl<TbRoomMethodMapper, TbRoomMethod> implements TbRoomMethodService {

    private final TbRoomMethodMapper tbRoomMethodMapper;

    /**
     * 列表
     *
     * @param hospitalName 院区名称
     * @return 房间列表
     */
    @Override
    public List<TbRoomMethod> finAll(String hospitalName,String deptName) {
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return tbRoomMethodMapper.selectFinAll(hospitalName);
        }else {
            // 科室人员
            return tbRoomMethodMapper.selectFindAll(hospitalName,deptName);
        }
    }

    /**
     * 列表(搜索条件)
     *
     * @param tbRoomMethodVo 院区名称
     * @return 房间列表
     */
    @Override
    public List<TbRoomMethod> finAllNew(TbRoomMethodVo tbRoomMethodVo) {
        if(tbRoomMethodVo.getDeptName().equals(ConstantClass.ADMINISTRATOR)){
            return tbRoomMethodMapper.selectFinAllNew(tbRoomMethodVo);
        }else {
            // 科室人员
            return tbRoomMethodMapper.selectFindAllNew(tbRoomMethodVo);
        }
    }

    /**
     * 添加
     * @param tbRoomMethod 检查房间
     * @return 检查房间对象
     */
    @Override
    public R<Integer> addRoomMethod(TbRoomMethod tbRoomMethod) {
        List<TbRoomMethod> tbRoomMethodList = tbRoomMethodMapper.selectCondition(tbRoomMethod);
        if (tbRoomMethodList != null && tbRoomMethodList.size() > 0) {
            //throw new MyException("添加房间支持检查方法失败,数据重复");
            return new R(1,"添加房间支持检查方法失败,数据重复");
        }
        Integer result = tbRoomMethodMapper.insertTbRoomMethod(tbRoomMethod);
        if (result < 1) {
            //throw new MyException("添加房间支持检查方法失败");
            return new R(1,"添加房间支持检查方法失败");
        }
        return new R(tbRoomMethod.getId());
    }

    /**
     * 修改
     * @param tbRoomMethod 修改信息
     * @return 房间信息对象
     */
    @Override
    public R<Integer> modifyRoomMethod(TbRoomMethod tbRoomMethod) {
        List<TbRoomMethod> tbRoomMethodList = tbRoomMethodMapper.selectCondition(tbRoomMethod);
        if (tbRoomMethodList != null && tbRoomMethodList.size() > 0) {
            //throw new MyException("修改房间支持检查方法失败,数据重复");
            return new R(1,"修改房间支持检查方法失败,数据重复");
        }
        Integer result = tbRoomMethodMapper.updateTbRoomMethod(tbRoomMethod);
        if (result < 1) {
            //throw new MyException("修改房间支持检查方法失败");
            return new R(1,"修改房间支持检查方法失败");
        }
        return new R(tbRoomMethod.getId());
    }

    /**
     * 删除
     *
     * @param id 房间id
     * @return 删除结果
     */
    @Override
    public R<Integer> deleteRoomMethod(Integer id) {
        Integer result = tbRoomMethodMapper.deleteTbRoomMethod(id);
        if (result < 1) {
            //throw new MyException("删除房间支持检查方法失败");
            return new R(1,"删除房间支持检查方法失败");
        }
        return new R(result);
    }
}
