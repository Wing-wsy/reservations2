package com.annet.mapper;

import com.annet.entity.TbBespoke;
import com.annet.entity.domain.GetPacsSelfRegister;
import com.annet.entity.operating.ConfigOperate;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 * 预约接口
 *
 * @author XiaYu
 * @since 2019-05-15
 */
public interface TbBespokeMapper extends BaseMapper<TbBespoke> {

    /**
     * 获取列表
     *
     * @param hospitalizeNo
     * @param bespokeStatus
     * @param feeStatus
     * @param studyStatus
     * @param requestDeptCode
     * @param startDate
     * @param endDate
     * @return
     */
    List<TbBespoke> selectTbBespoke(@Param("hospitalizeNo") String hospitalizeNo, @Param("bespokeStatus") Integer bespokeStatus,
                                    @Param("feeStatus") Integer feeStatus, @Param("studyStatus") Integer studyStatus,
                                    @Param("requestDeptCode") Integer requestDeptCode, @Param("startDate") String startDate,
                                    @Param("endDate") String endDate, @Param("bespokeStartDate") String bespokeStartDate,
                                    @Param("bespokeEndDate") String bespokeEndDate, @Param("hospitalName") String hospitalName,
                                    @Param("bespokeStudyDate") String bespokeStudyDate, @Param("bespokeNo") String bespokeNo,
                                    @Param("modality") String modality, @Param("studyRoom") String studyRoom,
                                    @Param("startTime") String startTime, @Param("endTime") String endTime,
                                    @Param("idtype") String idtype, @Param("id") String id, @Param("resType") Integer resType,
                                    @Param("requestDeptCode1") String requestDeptCode1);

    /**
     * 暂未使用
     */
    List<TbBespoke> selectTbBespokeByDeptName(@Param("hospitalizeNo") String hospitalizeNo, @Param("bespokeStatus") Integer bespokeStatus,
                                              @Param("feeStatus") Integer feeStatus, @Param("studyStatus") Integer studyStatus,
                                              @Param("requestDeptCode") Integer requestDeptCode, @Param("startDate") String startDate,
                                              @Param("endDate") String endDate, @Param("bespokeStartDate") String bespokeStartDate,
                                              @Param("bespokeEndDate") String bespokeEndDate, @Param("hospitalName") String hospitalName,
                                              @Param("bespokeStudyDate") String bespokeStudyDate, @Param("deptName") String deptName,
                                              @Param("bespokeNo") String bespokeNo, @Param("modality") String modality, @Param("studyRoom") String studyRoom,
                                              @Param("startTime") String startTime, @Param("endTime") String endTime,
                                              @Param("idtype") String idtype, @Param("id") String id, @Param("resType") Integer resType,
                                              @Param("requestDeptCode1") String requestDeptCode1);

    /**
     * 判断预约是否已预约
     *
     * @param RequestNo 预约单号
     * @param OrdRowID 医嘱ID,多个用,分割
     * @return 集合
     */
    List<TbBespoke> selectTbBespokeByRequestNo(@Param("RequestNo") String RequestNo,@Param("OrdRowID") String OrdRowID,@Param("StudyPartCode") String StudyPartCode);

    /**
     * 添加
     *
     * @param tbBespoke
     * @return
     */
    Integer insertTbBespoke(TbBespoke tbBespoke);

    /**
     * 修改
     *
     * @param tbBespoke
     * @return
     */
    Integer updateTbBespoke(TbBespoke tbBespoke);

    /**
     * 删除
     *
     * @param ID
     * @return
     */
    Integer deleteTbBespoke(@Param("ID") String ID);

    /**
     * 统计时间
     *
     * @param bespokeDate
     * @return
     */
    String selectMaxID(@Param("bespokeDate") String bespokeDate,
                       @Param("hospitalName") String hospitalName);

    //execDept(执行科室),studyRoom(检查房间),groupType
    List<TbBespoke> find(@Param("execDept") String execDept, @Param("studyRoom") String studyRoom,
                         @Param("modality") String modality, @Param("hospitalName") String hospitalName,
                         @Param("startTime") String startTime, @Param("endTime") String endTime,
                         @Param("bespokeStudyDate") String bespokeStudyDate,@Param("groupType") Integer groupType);

    /**
     * getPacsSelfRegister
     * @param paramsMap 参数
     * @return 人工报到参数对象
     */
    List<GetPacsSelfRegister> getPacsSelfRegister(Map<String, Object> paramsMap);

    TbBespoke findById(@Param("ID") Integer ID);

    Integer getBespokeTimes(@Param("uid") String uid,@Param("idtype") String idtype,
                            @Param("cancelDate") String cancelDate,@Param("execDept") String execDept);

    Integer getChangeBespokeTimes(@Param("uid") String uid,@Param("idtype") String idtype);

    String getBespokeNum(@Param("configKey") String configKey);

    Integer insertOperateRecord(ConfigOperate co);

    Integer getBespokeInfo(@Param("requestNo") String requestNo, @Param("ordRowID") String ordRowID,
                         @Param("HISStudyPartCode") String HISStudyPartCode, @Param("HISStudyProjectCode") String HISStudyProjectCode,
                           @Param("modality") String modality,@Param("hospitalName") String hospitalName);

    TbBespoke getBespokeStatusAndQueueNo(@Param("bespokeId") String bespokeId);

}
