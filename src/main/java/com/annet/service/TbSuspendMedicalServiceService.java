package com.annet.service;


import com.annet.entity.TbBespokeres;
import com.annet.entity.TbSuspendMedicalService;
import com.annet.result.R;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-04
 */
public interface TbSuspendMedicalServiceService extends IService<TbSuspendMedicalService> {

    /**
     * 号源停诊
     *
     * @param modality      设备类型
     * @param groupType     分组方式
     * @param groupName     分组名称
     * @param stopStartDate 号源停诊开始时间
     * @param stopEndDate   号源停诊结束时间
     * @param hospitalName  院区名称
     * @return 号源集合
     */
    List<TbSuspendMedicalService> stopTbBespokeres(String modality, Integer groupType, String groupName,
                                                   String stopStartDate, String stopEndDate, String hospitalName) throws ParseException;

    /**
     * 预约号源停诊列表
     *
     * @param hospitalName 预约号源
     * @return 预约号源停诊集合
     */
    List<TbSuspendMedicalService> finAllStop(String hospitalName);


    /**
     * 删除停诊号源(则需要恢复号源表号源数)
     *
     * @param id           id
     * @param modality     设备
     * @param groupType    检查方式
     * @param groupName    检查名称
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param hospitalName 院区名称
     * @return 操作码
     */
    R<TbBespokeres> deleteStopBespoke(Integer id, String modality,
                                      Integer groupType, String groupName,
                                      String startDate, String endDate,
                                      String hospitalName);



}
