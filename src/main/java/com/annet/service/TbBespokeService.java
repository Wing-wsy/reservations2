package com.annet.service;

import com.annet.entity.TbBespoke;
import com.annet.entity.bo.BespokeEntity;
import com.annet.entity.bo.ReportInfo;
import com.annet.entity.operating.ResultBG;
import com.annet.entity.operating.ResultTJ;
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
 *
 * @author XiaYu
 * @since 2019-05-15
 */
public interface TbBespokeService extends IService<TbBespoke> {
    /**
     * 医技预约
     *
     * @param idType       id类型
     * @param id           患者id
     * @param hospitalName 院区名称
     * @param operatorCode 医生工号
     * @param execdeptcode 执行科室编码
     * @return 已经预约的集合
     */
    List<GetRequestInfo> mTReservation(String idType, String id, String hospitalName, String operatorCode, String execdeptcode);

    /**
     * 医技预约（正在使用）
     *
     * @param idType       id类型
     * @param id           患者id
     * @param hospitalName 院区名称
     * @param operatorCode 医生工号
     * @param execdeptcode 执行科室编码
     * @return 已经预约的集合
     */
    R<List<GetRequestInfo>> mTReservationNew(String histype,String idType, String id, String hospitalName, String operatorCode, String execdeptcode, String requestdeptcode,String startdate, String enddate, String zhuanyun);

    /**
     * 提交预约(弃用)
     *
     * @param tbBespokeList 预约信息集合
     * @return 预约
     */
    List<ResultTJ> reservation(List<TbBespoke> tbBespokeList);

    /**
     * 提交预约(已弃用2)
     *
     * @param tbBespokeList 预约信息集合
     * @return 预约
     */
    List<ResultTJ> reservationNew(List<TbBespoke> tbBespokeList);

    /**
     * 提交预约(正使用)
     *
     * @param getRecommendationVoList 预约信息集合
     * @return 预约
     */
    R<List<ResultTJ>> reservationNew2(List<GetRecommendationVo> getRecommendationVoList) throws ParseException;

    /**
     * 预约查询
     *
     * @param hospitalizeNo   住院号/门诊号/体检号
     * @param bespokeStatus   预约状态
     * @param feeStatus       缴费状态
     * @param studyStatus     检查状态
     * @param requestDeptCode 申请科室代码
     * @param startDate       时间段开始时间
     * @param endDate         时间段结束时间
     * @return 预约信息集合
     */
    List<TbBespoke> finAll(String hospitalizeNo, Integer bespokeStatus, Integer feeStatus, Integer studyStatus,
                           Integer requestDeptCode, String startDate, String endDate, String bespokeStartDate,
                           String bespokeEndDate, String hospitalName, String deptName, String bespokeNo,
                           String modality,String studyRoom, String startTime, String endTime, String idtype, String id, Integer resType, String requestDeptCode1);

    /**
     * 预约修改
     *
     * @param tbBespoke 修改预约对象
     * @return 操作返回码
     */
    Integer updateTbBespoke(TbBespoke tbBespoke);

    /**
     * 取消预约
     *
     * @param id 预约id
     * @return 操作码
     */
    R<Integer> delete(Integer id,String userID,String uid,String idtype,String execDept);
    /**
     * 获取取消预约次数
     *
     * @param bespokeEntity
     * @return 操作码
     */
    R<List<BespokeEntity>> selectCancelBespoke(BespokeEntity bespokeEntity );

    /**
     * 变更预约（已弃用）
     *
     * @param id    预约id
     * @param resid 预约号源主键ID
     * @return 操作返回码
     */
    ResultBG change(Integer id, Integer resid);


    /**
     * 变更预约(正在用)
     *
     * @param id 预约id
     * @return 操作返回码
     */
    R<ResultBG> changeNew(Integer id, Integer resid,String uid,String idtype);

    /**
     * @param id 预约id
     * @return 预约对象
     */
    R<TbBespoke> selfReport(Integer id,String userID);
    /**
     * @param reportInfo reportInfo
     * @return 预约对象
     */
    R<List<TbBespoke>> quickReport(ReportInfo reportInfo);

    /**
     * @param id 预约id
     * @return 预约对象
     */
    R<TbBespoke> regRequest(Integer id,String userID);
    /**
     * @param bespokeId 预约ID
     * @return 预约对象
     */
    String getBespokeStatusAndQueueNo(String bespokeId);
}
