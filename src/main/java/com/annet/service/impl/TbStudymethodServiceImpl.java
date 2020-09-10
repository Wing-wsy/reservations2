package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbStudymethod;
import com.annet.mapper.TbStudymethodMapper;
import com.annet.mapper.TbStudyroomMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbStudymethodService;
import com.annet.utils.StrUtils;
import com.annet.vo.TbStudymethodVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 * 检查方法
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbStudymethodServiceImpl extends ServiceImpl<TbStudymethodMapper, TbStudymethod> implements TbStudymethodService {

    private final TbStudymethodMapper tbStudymethodMapper;

    private final TbStudyroomMapper tbStudyroomMapper;

    /**
     * 检查方法列表
     *
     * @param hospitalName 院区名称
     * @return 检查方法集合
     */
    @Override
    public R<List<TbStudymethod>> finAll(String hospitalName, String deptName) {
        if (StrUtils.isNullOrEmpty(hospitalName)) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        List<TbStudymethod> tbStudymethods = tbStudymethodMapper.selectfinAll(hospitalName);
        if (deptName.equals(ConstantClass.ADMINISTRATOR)) {
            return new R(tbStudymethods);
        } else {
            List<TbStudymethod> tbStudymethodList = tbStudymethodMapper.selectFindAll(hospitalName, deptName);
            return new R(tbStudymethodList);
        }
    }

    @Override
    public R<List<TbStudymethod>> finAllNew(TbStudymethodVo tbStudymethodVo) {
        if (StrUtils.isNullOrEmpty(tbStudymethodVo.getHospitalName())) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        if (tbStudymethodVo.getDeptName().equals(ConstantClass.ADMINISTRATOR)) {
            // 全部
            return new R(tbStudymethodMapper.selectfinAllNew(tbStudymethodVo));
        } else {
            // 科室人员
            return new R(tbStudymethodMapper.selectFindAllNew(tbStudymethodVo));
        }
    }

    /**
     * 检查方法添加
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    @Override
    public R<Integer> AddStudymethod(TbStudymethod tbStudymethod) {
        if (StrUtils.isNullOrEmpty(tbStudymethod.getHospitalName())) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        List<TbStudymethod> tbStudymethodList = tbStudymethodMapper.selectCondition(tbStudymethod);
        if (tbStudymethodList != null && tbStudymethodList.size() > 0) {
            //throw new MyException("添加检查方法失败,设备，检查方法重复");
            return new R(1,"添加检查方法失败,设备，检查方法重复");
        }
        if(tbStudymethod.getStatus() == null){
            tbStudymethod.setStatus(1);
        }
        Integer result = tbStudymethodMapper.insertTbStudymethod(tbStudymethod);
        if (result < 1) {
            //throw new MyException("添加检查方法失败");
            return new R(1,"添加检查方法失败");
        }
        return new R(tbStudymethod.getMethodID());
    }

    /**
     * 检查方法修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    @Override
    public R<Integer> modifyStudymethod(TbStudymethod tbStudymethod) {
        if (StrUtils.isNullOrEmpty(tbStudymethod.getHospitalName())) {
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"修改检查方法失败");
        }
        List<TbStudymethod> tbStudymethodList = tbStudymethodMapper.selectCondition(tbStudymethod);
        if (tbStudymethodList != null && tbStudymethodList.size() > 0) {
            //throw new MyException("修改检查方法失败,设备，检查方法重复");
            return new R(1,"修改检查方法失败");
        }
        Integer result = tbStudymethodMapper.updateTbStudymethod(tbStudymethod);
        if (result < 1) {
            //throw new MyException("修改检查方法失败");
            return new R(1,"修改检查方法失败");
        }
        return new R(tbStudymethod.getMethodID());
    }

    /**
     * 检查方法状态修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法状态修改
     */
    @Override
    public R<Integer> modifyStatus(TbStudymethod tbStudymethod) {

        Integer result = tbStudymethodMapper.updateTbStudymethod(tbStudymethod);
        if (result < 1) {
            return new R(1,"修改状态失败");
        }
        return new R(tbStudymethod.getMethodID());
    }

    /**
     * @param methodId 检查方法id
     * @return 操作是否成功的操作码
     */
    @Override
    public R<Integer> deleteStudymethod(Integer methodId) {
        Integer result = tbStudymethodMapper.deleteStudymethod(methodId);
        if(result < 1){
            //throw new MyException("删除检查方法失败");
            return new R(1,"删除检查方法失败");
        }
        return new R(result);
    }
}
