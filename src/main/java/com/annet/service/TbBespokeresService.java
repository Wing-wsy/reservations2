package com.annet.service;

import com.annet.entity.TbBespokeres;
import com.annet.entity.domain.GetBespokeRes;
import com.annet.entity.domain.GetRecommendation;
import com.annet.result.R;
import com.annet.vo.GetRecommendationVo;
import com.annet.xmlEntity.GetRequestInfo;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 预约号源
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbBespokeresService extends IService<TbBespokeres> {

    /**
     * @param tbBespokeres 预约浩源
     * @return 预约号源集合
     */
    List<TbBespokeres> finAll(TbBespokeres tbBespokeres, String deptName);

    /**
     * 添加
     *
     * @param tbBespokeres 预约号源
     * @return 操作返回码
     */
    R<String> addTbBespokeres(TbBespokeres tbBespokeres);

    /**
     * 修改
     *
     * @param tbBespokeres 预约号源
     * @return 操作返回码
     */
    R<String> modifyTbBespokeres(TbBespokeres tbBespokeres);

    /**
     * 删除
     *
     * @param id 预约号源id
     * @return 操作返回码
     */
    R<String> deteleTbBespokeres(Integer id);


    /**
     * 推荐预约方案
     *
     * @param requestXml xml
     * @param preference 偏好
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param timeFrame  时段
     * @return 推荐列表
     */
    //List<GetRecommendation> getRecommendation(String[] requestXml, Integer preference, String startDate, String endDate, Integer timeFrame, String hospitalName);

    List<GetRecommendation> getRecommendationNew(String[] requestXml, Integer preference, String startDate, String endDate, Integer timeFrame, String hospitalName, String hospitalizeNo,String userID);


    /**
     * 可预约申请的判断合并
     *
     * @param getRequestInfoList 可预约申请单集合
     * @return 合并的新预约对象
     */
    List<GetRecommendationVo> mergeAppointment(List<GetRequestInfo> getRequestInfoList) throws ParseException;

    /**
     * 获取自选方案时间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    List<GetBespokeRes> getOptionalDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName,String timeCoefficient, String studyRoom);

    /**
     * 选择预约时间
     *
     * @param resID        号源表ID
     * @param hospitalType 就诊类型
     * @param partID       tb_method_part表主键(部位ID)
     * @return 预约表
     */
    R<TbBespokeres> selectionPeriod(Integer resID, String hospitalType, String partID,Integer timeCoefficient,String userID);

    /**
     * 清除号源占用
     *
     * @param partID 占用号源的partID
     * @return 操作返回码
     */
    Integer clearSource(String partID,String userID);

    /**
     * 号源复用
     *
     * @param modality     设备类型
     * @param groupType    分组方式
     * @param groupName    分组名称
     * @param startDate    复用原始时间
     * @param endDate      复用目标时间
     * @param hospitalName 院区名称
     * @return 号源集合
     */
    R<List<TbBespokeres>> multiplexTbBespokeres(String modality, Integer groupType, String groupName,
                                             String startDate, String endDate, String multiplexStartDate,
                                             String multiplexEndDate, String hospitalName) throws ParseException;


    /**
     * 预约号源情况查询
     * @param tbBespokeres 号源表
     * @return 号源情况查询集合
     */
    List<TbBespokeres> queryTbBespokeres(TbBespokeres tbBespokeres);

    /**
     * 微信端获取自选方案无号源时间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    String getAppOptionalNotDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName,String timeCoefficient);

    /**
     * 获取自选方案可预约的检查房间
     *
     * @param partID       占用号源的partID
     * @param hospitalType 类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 方案集合
     */
    String getStudyGroupName(String partID, String hospitalType, String startDate, String endDate, String hospitalName,String timeCoefficient);
}
