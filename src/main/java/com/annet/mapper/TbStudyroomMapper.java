package com.annet.mapper;

import com.annet.entity.TbStudyroom;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 * 检查房间
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbStudyroomMapper extends BaseMapper<TbStudyroom> {

    /**
     * 检查房间列表
     *
     * @param hospitalName 院区名称
     * @return 检查房间列表
     */
    List<TbStudyroom> selectfinAll(@Param("hospitalName") String hospitalName);

    /**
     * 检查房间去重，重复不允许添加
     *
     * @param tbStudyroom 检查房间
     * @return 检查房间列表
     */
    List<TbStudyroom> selectRepeat(TbStudyroom tbStudyroom);

    /**
     * 添加检查房间
     *
     * @param tbStudyroom
     * @return
     */
    Integer insertTbStudyroom(TbStudyroom tbStudyroom);

    /**
     * 修改检查房间
     *
     * @param tbStudyroom
     * @return
     */
    Integer updateTbStudyroom(TbStudyroom tbStudyroom);

    /**
     * 根据设备类型去重
     *
     * @return
     */
    List<TbStudyroom> selectGroupByModality();

    /**
     * 根据设备类型去重（修改）
     *
     * @return 检查房间集合
     */
    List<TbStudyroom> selectGroupByModalityAndDeptName(@Param("hospitalName") String hospitalName);

    //deptName,modality,studyRoom,hospitalName
    TbStudyroom selectWhetherPreciseTime(@Param("deptName") String deptName, @Param("modality") String modality,
                                     @Param("studyRoom") String studyRoom, @Param("hospitalName") String hospitalName);
    /**
     * 根据诊室名称获取诊室代码和检查地点
     * @return
     */
    List<TbStudyroom> selectRoomCodeAndAddress(@Param("studyRoom") String studyRoom);

}
