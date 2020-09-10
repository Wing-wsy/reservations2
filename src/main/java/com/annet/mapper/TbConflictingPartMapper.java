package com.annet.mapper;

import com.annet.entity.Dto.TbConflictingPartDto;
import com.annet.entity.TbConflictingPart;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * 检查项目互斥表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbConflictingPartMapper extends BaseMapper<TbConflictingPart> {

    /**
     * 列表
     * @return 列表
     */
    List<TbConflictingPart> selectFinAll();

    /**
     * 添加
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    Integer insertTbConflictingPart(TbConflictingPart tbConflictingPart);

    /**
     * 修改
     * @param tbConflictingPart 检查项目互斥对象
     * @return 返回码
     */
    Integer updateTbConflictingPart(TbConflictingPart tbConflictingPart);

    /**
     * 删除
     *
     * @param ID 检查互斥编号id
     * @return 返回码
     */
    Integer deleteTbConflictingPart(@Param("ID") Integer ID);

    /**
     * 获取列表
     *
     * @return 检查项目互斥表列表
     */
    List<TbConflictingPartDto> selectFinAllDto();


    /**
     * @param hospitalName 院区名称
     * @param deptName 科室名称
     * @return 互斥对象的dto
     */
    List<TbConflictingPartDto> selectFindAllDto(@Param("hospitalName") String hospitalName,
                                                @Param("deptName") String deptName);


    /**
     * 查询重复
     */
    List<TbConflictingPart> selectRepeat(TbConflictingPart tbConflictingPart);

    /**
     * 查询需要删除的
     */
    List<TbConflictingPart> selectSCFinAll(@Param("PartID")Integer PartID);

    /**
     * 判断项目是否互斥
     * @param modality 设备
     * @param partId 部位id
     * @param conflictingPartId 互斥部位id
     * @param partId2 部位id
     * @param conflictingPartId2 互斥部位id
     * @param hospitalName 院区名称
     * @return 互斥集合
     */
    List<TbConflictingPart> isConflicting(@Param("modality")String modality,@Param("partId")String partId,
                                          @Param("conflictingPartId")String conflictingPartId,@Param("partId2")String partId2,
                                          @Param("conflictingPartId2")String conflictingPartId2,@Param("hospitalName")String hospitalName);
}
