package com.annet.mapper;

import com.annet.entity.TbBespokeresTemplate;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 * 预约号源模板
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbBespokeresTemplateMapper extends BaseMapper<TbBespokeresTemplate> {

    /**
     * 模板集合
     *
     * @param tbBespokeresTemplate 院区名称
     * @return 号源模板集合
     */
    List<TbBespokeresTemplate> selectFinAll(TbBespokeresTemplate tbBespokeresTemplate);

    /**
     * @param tbBespokeresTemplate 号源模板对象
     * @return 号源模板集合
     */
    List<TbBespokeresTemplate> selectFindAll(TbBespokeresTemplate tbBespokeresTemplate);
    /**
     * 添加
     *
     * @param tbConflictingPart 对象信息
     * @return 添加成功码
     */
    Integer insertBespokeresTemplate(TbBespokeresTemplate tbConflictingPart);

    /**
     * 修改
     *
     * @param tbConflictingPart 对象信息
     * @return 修改成功码
     */
    Integer updateBespokeresTemplate(TbBespokeresTemplate tbConflictingPart);

    /**
     * 删除
     *
     * @param ID 模板id
     * @return 删除操作码
     */
    Integer deleteBespokeresTemplate(@Param("ID") Integer ID);

    /**
     * 查询条件重复的
     *
     * @param tbConflictingPart 对象
     * @return 集合
     */
    List<TbBespokeresTemplate> selectCondition(TbBespokeresTemplate tbConflictingPart);

    List<TbBespokeresTemplate> findBespokeTemplate(@Param("Modality") String Modality,
                                                   @Param("GroupType") Integer GroupType,
                                                   @Param("GroupName") String GroupName,
                                                   @Param("WeekDay") String WeekDay,
                                                   @Param("HospitalName") String HospitalName);

    List<TbBespokeresTemplate> findTimeByHospitalNameAndModality(@Param("hospitalName") String hospitalName,
                                                                 @Param("modalities") List<String> modalities);

    /**
     * @param GroupName 房间名称
     * @return 号源模板集合
     */
    List<TbBespokeresTemplate> selectGroupTypeByGroupName(@Param("GroupName") String GroupName);
}
