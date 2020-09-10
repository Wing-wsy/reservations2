package com.annet.mapper;

import com.annet.entity.TbSuspendMedicalService;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-04
 */
public interface TbSuspendMedicalServiceMapper extends BaseMapper<TbSuspendMedicalService> {


    Integer addTbSuspendMedicalService(TbSuspendMedicalService tbBespokeresService);

    List<TbSuspendMedicalService> selectFinAll(@Param("EndDate") String endDate,
                                               @Param("HospitalName") String hospitalName);

    List<TbSuspendMedicalService> selectCondition(@Param("Modality") String Modality,
                                                  @Param("GroupType") Integer groupType,
                                                  @Param("GroupName") String groupName,
                                                  @Param("StartDate") String startDate,
                                                  @Param("EndDate") String endDate,
                                                  @Param("HospitalName") String hospitalName);
}
