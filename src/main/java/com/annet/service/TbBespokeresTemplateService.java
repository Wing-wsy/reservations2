package com.annet.service;

import com.annet.entity.TbBespokeresTemplate;
import com.annet.result.R;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 预约号源模板
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbBespokeresTemplateService extends IService<TbBespokeresTemplate> {

    /**
     * 模板集合
     *
     * @param tbBespokeresTemplate 号源模板对象
     * @return 号源模板集合
     */
    R<List<TbBespokeresTemplate>> finAll(TbBespokeresTemplate tbBespokeresTemplate);

    /**
     * 添加
     *
     * @param tbBespokeresTemplate 模板对象
     * @return 操作成功码
     */
    R<Integer> addBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate);

    /**
     * 修改
     *
     * @param tbBespokeresTemplate 对象信息
     * @return 修改成功码
     */
    R<Integer> modifyBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate);

    /**
     * 删除
     *
     * @param id 模板id
     * @return 删除操作码
     */
    R<Integer> deleteBespokeresTemplate(Integer id);

    /**
     * 设备预约排班时间集合
     * @param hospitalName  院区
     * @param modalities 设备
     * @return 设备排班时间集合
     */
    List<TbBespokeresTemplate> findTimeByHospitalNameAndModality(String hospitalName,String modalities);
}
