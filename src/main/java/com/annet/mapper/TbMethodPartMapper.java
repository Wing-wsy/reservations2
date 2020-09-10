package com.annet.mapper;

import com.annet.entity.TbMethodPart;
import com.annet.vo.TbMethodPartVo;
import com.annet.xmlEntity.GetRequestInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 * 检查方法部位对照
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbMethodPartMapper extends BaseMapper<TbMethodPart> {

    /**
     * 检查部位列表
     *
     //* @param hospitalName 院区名称
     * @return 检查方法部位列表
     */
    List<TbMethodPart> selectFinAll(@Param("hospitalName") String hospitalName);

    List<TbMethodPart> selectFinAllNew(TbMethodPartVo tbMethodPartVo);

    /**
     * @param hospitalName 院区名称
     * @param deptName     科室名称
     * @return 检查方法部位列表
     */
    List<TbMethodPart> selectFindAll(@Param("hospitalName") String hospitalName,
                                     @Param("deptName") String deptName);

    List<TbMethodPart> selectFindAllNew(TbMethodPartVo tbMethodPartVo);


    /**
     * 判断重复
     *
     * @param tbMethodPart 检查方法部位
     * @return 检查方法集合
     */
    List<TbMethodPart> selectCondition(TbMethodPart tbMethodPart);

    /**
     * 添加检查部位
     *
     * @param tbMethodPart 检查部位
     * @return 部位集合
     */
    Integer insertTbMethodPart(TbMethodPart tbMethodPart);

    /**
     * 修改
     *
     * @param tbMethodPart 检查部位对象
     * @return 检查部位
     */
    Integer updateTbMethodPart(TbMethodPart tbMethodPart);

    /**
     * 删除
     *
     * @param ID:主键ID
     * @return
     */
    Integer deleteTbMethodPart(@Param("ID") int ID);

    /**
     * 预约匹配判断是否可以预约
     *
     * @param Modality            设备类型
     * @param HISStudyProjectCode HIS检查项目代码
     * @param HISStudyProject     HIS检查项目
     * @param HISStudyPartCode    HIS检查部位代码
     * @param HISStudyPart        HIS检查部位
     * @param HospitalName        检查预约的医院
     * @return
     */
    List<TbMethodPart> selectYJReservation(@Param("Modality") String Modality, @Param("HISStudyProjectCode") String HISStudyProjectCode,
                                           @Param("HISStudyProject") String HISStudyProject, @Param("HISStudyPartCode") String HISStudyPartCode,
                                           @Param("HISStudyPart") String HISStudyPart, @Param("HospitalName") String HospitalName);

    /**
     * 预约匹配判断是否可以预约
     *
     * @param Modality     设备类型
     * @param HISStudyPart HIS检查部位
     * @param HospitalName 检查预约的医院
     * @return
     */
    List<TbMethodPart> selectYJReservationNew(@Param("Modality") String Modality,
                                              @Param("HISStudyPart") String HISStudyPart,
                                              @Param("HospitalName") String HospitalName);


    /**
     * 根据设备查找部位
     *
     * @param modality 设备
     * @return 检查部位集合
     */
    List<TbMethodPart> selectModality(@Param("modality") String modality);
    /**
     * 根据设备和方法查找部位
     *
     * @param modality 设备
     * @param method 方法
     * @return 检查部位集合
     */
    List<TbMethodPart> selectModalityAndMethod(@Param("modality") String modality ,
                                               @Param("method") String method,
                                               @Param("hospitalName") String hospitalName,
                                               @Param("deptName")  String deptName);

    List<TbMethodPart> selectModalityAndMethod1(@Param("modality") String modality ,
                                               @Param("method") String method,
                                               @Param("hospitalName") String hospitalName);
    /**
     * 调用存储过程返回是否匹配和可预约
     *
     * @param paramsMap
     * @return
     */
    List<GetRequestInfo> getMethodAndBespokeCount(Map<String, Object> paramsMap);
}
