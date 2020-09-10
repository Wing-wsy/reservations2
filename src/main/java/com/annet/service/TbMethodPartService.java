package com.annet.service;

import com.annet.entity.TbMethodPart;
import com.annet.result.R;
import com.annet.vo.TbMethodPartVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 检查方法部位对照
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbMethodPartService extends IService<TbMethodPart> {


    /**
     * 检查部位列表
     *
     * @param hospitalName 院区名称
     * @return 检查方法部位列表
     */
    List<TbMethodPart> FinAll(String hospitalName,String deptName);


    List<TbMethodPart> finAllNew(TbMethodPartVo tbMethodPartVo);


    /**
     * 添加检查部位
     *
     * @param tbMethodPart 检查部位
     * @return 部位集合
     */
    R<Integer> AddTbMethodPart(TbMethodPart tbMethodPart);

    /**
     * 修改
     *
     * @param tbMethodPart 检查部位对象
     * @return 检查部位
     */
    R<Integer> ModifyTbMethodPart(TbMethodPart tbMethodPart);

    /**
     * 删除
     *
     * @param id 检查方法部位id
     * @return 操作码
     */
    R<Integer> DeleteTbMethodPart(int id);

    /**
     * 根据设备查找部位
     *
     * @param modality 设备
     * @return 检查部位集合
     */
    List<TbMethodPart> selectModality(String modality);
    /**
     * 根据设备和方法查找部位
     *
     * @param modality 设备
     * @param method 方法
     * @return 检查部位集合
     */
    List<TbMethodPart> selectModalityAndMethod(String modality , String method ,String hospitalName,String deptName);
}
