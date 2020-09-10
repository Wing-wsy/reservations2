package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbConflictingPart;
import com.annet.entity.TbMethodPart;
import com.annet.mapper.TbConflictingPartMapper;
import com.annet.mapper.TbMethodPartMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbMethodPartService;
import com.annet.utils.StrUtils;
import com.annet.vo.TbMethodPartVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 * 检查方法部位对照
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbMethodPartServiceImpl extends ServiceImpl<TbMethodPartMapper, TbMethodPart> implements TbMethodPartService {

    private final TbMethodPartMapper tbMethodPartMapper;

    private final TbConflictingPartMapper tbConflictingPartMapper;

    /**
     * 检查部位列表
     *
     * @param hospitalName 院区名称
     * @return 检查方法部位列表
     */
    @Override
    public List<TbMethodPart> FinAll(String hospitalName,String deptName) {
        // 管理员
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return tbMethodPartMapper.selectFinAll(hospitalName);
        }else{
            // 科室人员
            return tbMethodPartMapper.selectFindAll(hospitalName, deptName);
        }
    }

    @Override
    public List<TbMethodPart> finAllNew(TbMethodPartVo tbMethodPartVo) {
        // 管理员
        if(tbMethodPartVo.getDeptName().equals(ConstantClass.ADMINISTRATOR)){
            return tbMethodPartMapper.selectFinAllNew(tbMethodPartVo);
        }else{
            // 科室人员
            return tbMethodPartMapper.selectFindAllNew(tbMethodPartVo);
        }
    }

    /**
     * 添加检查部位
     *
     * @param tbMethodPart 检查部位
     * @return 部位集合
     */
    @Override
    public R<Integer> AddTbMethodPart(TbMethodPart tbMethodPart) {
        if (StrUtils.isNullOrEmpty(tbMethodPart.getHospitalName())) {
            //throw new MyException("添加检查方法部位，院区名称参数不能为空");
            return new R(1,"添加检查方法部位，院区名称参数不能为空");
        }
        List<TbMethodPart> tbMethodPartList = tbMethodPartMapper.selectCondition(tbMethodPart);
        if (tbMethodPartList != null && tbMethodPartList.size() > 0) {
            //throw new MyException("添加检查方法部位对照失败,条件重复");
            return new R(1,"添加检查方法部位对照失败,条件重复");
        }
        Integer result = tbMethodPartMapper.insertTbMethodPart(tbMethodPart);
        if (result < 1) {
            //throw new MyException("添加检查方法部位对照失败");
            return new R(1,"添加检查方法部位对照失败");
        }
        return new R(tbMethodPart.getId());
    }

    /**
     * 修改
     *
     * @param tbMethodPart 检查部位对象
     * @return 检查部位
     */
    @Override
    public R<Integer> ModifyTbMethodPart(TbMethodPart tbMethodPart) {
        if(StrUtils.isNullOrEmpty(tbMethodPart.getHospitalName())){
            //throw new MyException("院区名称参数必传不能为空");
            return new R(1,"院区名称参数必传不能为空");
        }
        List<TbMethodPart> tbMethodPartList = tbMethodPartMapper.selectCondition(tbMethodPart);
        if (tbMethodPartList != null && tbMethodPartList.size() > 0) {
            //throw new MyException("修改检查方法部位对照失败,条件重复");
            return new R(1,"修改检查方法部位对照失败,条件重复");
        }
        Integer result = tbMethodPartMapper.updateTbMethodPart(tbMethodPart);
        if (result < 1) {
            //throw new MyException("修改检查方法部位对照失败");
            return new R(1,"修改检查方法部位对照失败");
        }
        return new R(tbMethodPart.getId());
    }

    /**
     * 删除
     *
     * @param ID 检查方法部位id
     * @return 操作码
     */
    @Override
    public R<Integer> DeleteTbMethodPart(int ID) {
        Integer result = tbMethodPartMapper.deleteTbMethodPart(ID);
        if (result < 1) {
            //throw new MyException("删除检查方法部位对照失败");
            return new R(1,"删除检查方法部位对照失败");
        }
        List<TbConflictingPart> tbConflictingPartList = tbConflictingPartMapper.selectSCFinAll(ID);
        if (tbConflictingPartList.size() > 0) {
            for (int i = 0; i < tbConflictingPartList.size(); i++) {
                TbConflictingPart tbConflictingPart = tbConflictingPartList.get(i);
                tbConflictingPartMapper.deleteTbConflictingPart(tbConflictingPart.getId());
            }
        }
        return new R(result);
    }

    /**
     * 根据设备查找部位
     *
     * @param modality 设备
     * @return 检查部位集合
     */
    @Override
    public List<TbMethodPart> selectModality(String modality) {
        return tbMethodPartMapper.selectModality(modality);
    }
    /**
     * 根据设备和方法查找部位
     *
     * @param modality 设备
     * @param method 方法
     * @return 检查部位集合
     */
    @Override
    public List<TbMethodPart> selectModalityAndMethod(String modality , String method ,String hospitalName,String deptName) {
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return tbMethodPartMapper.selectModalityAndMethod1(modality , method ,hospitalName);
        }else{
            return tbMethodPartMapper.selectModalityAndMethod(modality , method ,hospitalName ,deptName);
        }
    }
}
