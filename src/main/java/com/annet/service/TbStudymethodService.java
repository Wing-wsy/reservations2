package com.annet.service;

import com.annet.entity.TbStudymethod;
import com.annet.result.R;
import com.annet.vo.TbStudymethodVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 检查方法
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbStudymethodService extends IService<TbStudymethod> {

    /**
     * 检查方法列表
     *
     * @param hospitalName 院区名称
     * @return 检查方法集合
     */
    R<List<TbStudymethod>> finAll(String hospitalName, String deptName);

    R<List<TbStudymethod>> finAllNew(TbStudymethodVo tbStudymethodVo);

    /**
     * 检查方法添加
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    R<Integer> AddStudymethod(TbStudymethod tbStudymethod);

    /**
     * 检查方法修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    R<Integer> modifyStudymethod(TbStudymethod tbStudymethod);
    /**
     * 检查方法状态修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法状态修改
     */
    R<Integer> modifyStatus(TbStudymethod tbStudymethod);

    R<Integer> deleteStudymethod(Integer methodId);
}
