package com.annet.mapper;

import com.annet.entity.TbBespokeres;
import com.annet.entity.domain.GetBespokeRes;
import com.annet.entity.domain.GetRecommendation;
import com.annet.entity.operating.HgBespoke;
import com.annet.entity.operating.LogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 * 预约号源
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbBespokeresMapper extends BaseMapper<TbBespokeres> {

    /**
     * @param tbBespokeres 预约号源
     * @return 预约号源集合
     */
    List<TbBespokeres> selectFinAll(TbBespokeres tbBespokeres);

    /**
     //* @param HospitalName 院区名称
     //* @param BespokeDate 预约时间
     //* @param deptName 科室名称（登录账号）
     * @return 预约号源集合
     */
    /*List<TbBespokeres> selectFindAll(@Param("HospitalName") String HospitalName,
                                     @Param("BespokeDate") Date BespokeDate,
                                     @Param("deptName") String deptName);*/
    List<TbBespokeres> selectFindAll(TbBespokeres tbBespokeres);

    /**
     * 添加
     *
     * @param tbBespokeres 号源
     * @return 操作返回码
     */
    Integer insertTbBespokeres(TbBespokeres tbBespokeres);

    /**
     * 修改
     *
     * @param tbBespokeres 号源对象
     * @return 操作成功码
     */
    Integer updateTbBespokeres(TbBespokeres tbBespokeres);

    /**
     * 删除
     *
     * @param ID id
     * @return 操作成功码
     */
    Integer deleteTbBespokeres(@Param("ID") Integer ID);

    /**
     * 调用存储过程返回可预约接口
     *
     * @param paramsMap
     * @return
     */
    List<GetRecommendation> getRecommendation(Map<String, Object> paramsMap);

    /**
     * 自选方案获取号源
     *
     * @param paramsMap
     * @return
     */
    List<GetBespokeRes> getBespokeRes(Map<String, Object> paramsMap);

    /**
     * 对号源表进行修改
     *
     * @param hgBespoke
     * @return
     */
    Integer updateBespokeAddition(HgBespoke hgBespoke);

    /**
     * 通过各个条件查询号源
     *
     * @param tbBespokeres
     * @return
     */
    List<TbBespokeres> selectBespokeres(TbBespokeres tbBespokeres);

    /**
     * 变更的时需要查到原来的数据
     *
     * @param modality 设备
     * @param groupType 分组方式
     * @param groupName 分组名称
     * @param bespokeDate 预约日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 号源集合
     */
    List<TbBespokeres> selectFind(@Param("modality") String modality, @Param("groupType") Integer groupType,
                                  @Param("groupName") String groupName, @Param("bespokeDate") Date bespokeDate,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("hospitalName") String hospitalName);
    /**
     * 变更的时需要查到原来的数据
     *
     * @param modality 设备
     * @param groupType 分组方式
     * @param groupName 分组名称
     * @param bespokeDate 预约日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 号源集合
     */

    List<TbBespokeres> selectFindNew(@Param("modality") String modality, @Param("groupType") Integer groupType,
                                  @Param("groupName") String groupName, @Param("bespokeDate") Date bespokeDate,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("hospitalName") String hospitalName);

    /**
     * 查询添加，修改重复
     *
     * @param tbBespokeres 预约号源
     * @return 号源对象集合
     */
    List<TbBespokeres> selectCondition(TbBespokeres tbBespokeres);

    /**
     * 搜索所需复用的日期号源是否存在
     *
     * @param groupNames   分组名称
     * @param modality     设备类型
     * @param groupType    分组方式
     * @param startDate    复用原始时间
     * @param endDate      复用目标时间
     * @param hospitalName 院区名称
     * @return 号源集合
     */
    List<TbBespokeres> searchBospokerer(@Param("groupNames") List<String> groupNames,
                                        @Param("modality") String modality,
                                        @Param("groupType") Integer groupType,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate,
                                        @Param("hospitalName") String hospitalName);

    /**
     * 搜索所需复用的日期号源是否存在
     *
     * @param groupName   分组名称
     * @param modality     设备类型
     * @param groupType    分组方式
     * @param startDate    复用原始时间
     * @param endDate      复用目标时间
     * @param hospitalName 院区名称
     * @return 号源集合
     */
    List<TbBespokeres> searchBospokererList(@Param("modality") String modality,
                                            @Param("groupType") Integer groupType,
                                            @Param("groupName") String groupName,
                                            @Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("hospitalName") String hospitalName);


    Integer updateTbBespokeresToZero(@Param("OutPatientNum") Integer OutPatientNum,
                                     @Param("InPatientNum") Integer InPatientNum,
                                     @Param("TotalNum") Integer TotalNum,
                                     @Param("ids") Set<Integer> ids);

    List<TbBespokeres> selectAllModality();

    /**
     * 添加
     *
     * @param log
     * @return 操作返回码
     */
    Integer insertTbBespokeresLog(LogEntity log);

    /**
     * 微信端自选方案获取无号源日期
     *
     * @param paramsMap
     * @return
     */
    List<GetBespokeRes> getNotBespokeResDay(Map<String, Object> paramsMap);

    /**
     * 获取自选方案可预约的检查房间
     *
     * @param paramsMap
     * @return
     */
    List<GetBespokeRes> getStudyGroupName(Map<String, Object> paramsMap);

    /**
     * 获取号源ID
     *
     * @param modality     设备类型
     * @param groupType    分组方式
     * @param groupName    分组名称
     * @param bespokeDate    检查日期
     * @param startTime    开始时间段
     * @param endTime      结束时间段
     * @param hospitalName 院区名称
     * @return 操作返回码
     */
    Integer getBespokeresID(@Param("modality") String modality,
                            @Param("groupType") Integer groupType,
                            @Param("groupName") String groupName,
                            @Param("bespokeDate") String bespokeDate,
                            @Param("startTime") String startTime,
                            @Param("endTime") String endTime,
                            @Param("hospitalName") String hospitalName);

    /**
     * 根据号源ID获取号源
     *
     * @param resID     号源ID
     * @return 号源集合
     */
    List<TbBespokeres> selectBespokeresByID(@Param("resID") Integer resID);


}
