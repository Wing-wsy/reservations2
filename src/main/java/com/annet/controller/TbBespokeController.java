package com.annet.controller;


import com.annet.entity.TbBespoke;
import com.annet.entity.TbBespokeresTemplate;
import com.annet.entity.bo.BespokeEntity;
import com.annet.entity.bo.ReportInfo;
import com.annet.entity.operating.ResultBG;
import com.annet.entity.operating.ResultTJ;
import com.annet.result.R;
import com.annet.service.TbBespokeService;
import com.annet.service.TbBespokeresTemplateService;
import com.annet.vo.GetRecommendationVo;
import com.annet.xmlEntity.GetRequestInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author XiaYu
 * @since 2019-05-15
 */
@RestController
@AllArgsConstructor                          //该注解可以给成员变量生成构造函数，这样注入时可以省去@Autowired
@Api(value = "预约接口", tags = {"预约接口"})
@RequestMapping("/tbBespoke")
@Slf4j
public class TbBespokeController {


    private TbBespokeService tbBespokeService;

    private TbBespokeresTemplateService tbBespokeresTemplateService;

    @GetMapping(value = "/finAll")
    @ApiOperation(value = "预约列表", notes = "预约列表", httpMethod = "GET", response = TbBespoke.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalizeNo", value = "住院门诊号", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "bespokeStatus", value = "缴费状态", required = true, dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "feeStatus", value = "预约状态", required = true, dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "studyStatus", value = "检查状态", required = true, dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "requestDeptCode", value = "申请科室代码", required = true, dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "startDate", value = "开始时间：2019-6-5", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "endDate", value = "结束时间：2019-6-5", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "bespokeStartDate", value = "预约开始时间：2019-6-5", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "bespokeEndDate", value = "预约结束时间：2019-6-5", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "bespokeNo", value = "预约编号", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "modality", value = "设备类型", required = true, dataType = "String", paramType = "query")

    })
    public R<TbBespoke> finAll(String hospitalizeNo, Integer bespokeStatus, Integer feeStatus, Integer studyStatus, Integer requestDeptCode, String startDate, String endDate, String bespokeStartDate, String bespokeEndDate, String hospitalName, String deptName, String bespokeNo, String modality, String studyRoom, String startTime, String endTime, String idtype, String id, Integer resType, String requestDeptCode1) {
        return new R(tbBespokeService.finAll(hospitalizeNo, bespokeStatus, feeStatus, studyStatus, requestDeptCode, startDate, endDate, bespokeStartDate, bespokeEndDate, hospitalName, deptName, bespokeNo, modality, studyRoom, startTime, endTime, idtype, id, resType,requestDeptCode1));
    }

    /**
     * @PostMapping("/add")
     * @ApiOperation(value="预约添加", notes="添加预约",httpMethod = "POST",response = Integer.class)
     * @ApiImplicitParam(name = "tbBespoke", value = "添加预约", required = true, dataType = "TbBespoke", paramType = "query")
     * public R<Integer> addTbBespoke(TbBespoke tbBespoke){
     * return new R();
     * }
     */


    /**
     * 取消预约
     *
     * @param id 预约id
     * @return 操作码
     */
    @PostMapping(value = "/delete")
    //@DeleteMapping(value = "/delete")
    @ApiOperation(value = "取消预约", notes = "取消预约", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "操作人id", required = true, dataType = "String", paramType = "query")
    })
    public R<Integer> deleteTbBespoke(Integer id,String userID,String uid,String idtype,String execDept) {
        return tbBespokeService.delete(id,userID,uid, idtype,execDept);
    }

    /**
     * 获取取消预约次数
     *
     * @param bespokeEntity
     * @return 操作码
     */
    @PostMapping(value = "/selectCancelBespoke")
    @ApiOperation(value = "获取取消预约次数", notes = "获取取消预约次数", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "idtype", value = "id类型", required = true, dataType = "String", paramType = "query")
    })
    public  R<List<BespokeEntity>> selectCancelBespoke(@RequestBody BespokeEntity bespokeEntity ) {
        return tbBespokeService.selectCancelBespoke(bespokeEntity);
    }


    /**
     * 预约修改
     *
     * @param tbBespoke 修改预约对象
     * @return 操作返回码
     */
    @PutMapping(value = "modify")
    @ApiOperation(value = "预约修改", notes = "修改预约", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbBespoke", value = "=修改预约", required = true, dataType = "TbBespoke", paramType = "query")
    public R<Integer> modifyTbBespoke(TbBespoke tbBespoke) {
        return new R(tbBespokeService.updateTbBespoke(tbBespoke));
    }

    /**
     * 临床预约
     @GetMapping("/LCReservation")
     @ApiOperation(value="临床预约", notes="临床预约",httpMethod = "GET",response = TbBespoke.class)
     public void clinicalReservation(){
     }*/

    /**
     * 医技预约
     */
    @GetMapping(value = "/YJReservation")
    @ApiOperation(value = "医技预约", notes = "医技预约", httpMethod = "GET", response = GetRequestInfo.class)
    public R<List<GetRequestInfo>> MTReservation(String histype,String idType, String id, String hospitalName, String operatorCode, String execdeptcode, String requestdeptcode,String startdate, String enddate, String zhuanyun) {
        return tbBespokeService.mTReservationNew(histype, idType, id, hospitalName, operatorCode, execdeptcode, requestdeptcode, startdate, enddate, zhuanyun);
    }

    /**
     * 设备预约排班时间集合
     *
     * @param hospitalName 院区
     * @param modalities   设备
     * @return 设备排班时间集合
     */
    @PostMapping(value = "/reservationTime")
    @ApiOperation(value = "院区设备排班时间段", notes = "院区设备排班时间段", httpMethod = "POST", response = TbBespokeresTemplate.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = false, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "modalities", value = "设备名称", required = false, dataType = "String", paramType = "query")})
    public R<TbBespokeresTemplate> reservationTime(String hospitalName, String modalities) {
        return new R(tbBespokeresTemplateService.findTimeByHospitalNameAndModality(hospitalName, modalities));
    }


    /**
     * 提交预约(已弃用)
     *
     * @param tbBespokeList 预约集合
     * @return 返回码
     */
    @PostMapping(value = "/reservation")
    @ApiOperation(value = "提交预约", notes = "提交预约", httpMethod = "POST", response = ResultTJ.class)
    @ApiImplicitParam(name = "tbBespokeList", value = "修改预约", required = true, dataType = "List<TbBespoke>", paramType = "query")
    public R<List<ResultTJ>> reservation(@RequestBody List<TbBespoke> tbBespokeList) {
        return new R(tbBespokeService.reservationNew(tbBespokeList));
    }

    /**
     * 提交预约（正在使用）
     *
     * @param getRecommendationVoList 预约集合
     * @return 返回码
     */
    @PostMapping(value = "/reservation2")
    @ApiOperation(value = "提交预约", notes = "提交预约", httpMethod = "POST", response = ResultTJ.class)
    @ApiImplicitParam(name = "GetRecommendationVoList", value = "修改预约", required = true, dataType = "List<GetRecommendationVo>", paramType = "query")
    public R<List<ResultTJ>> reservation2(@RequestBody List<GetRecommendationVo> getRecommendationVoList) throws ParseException {
        return tbBespokeService.reservationNew2(getRecommendationVoList);
    }

    /**
     * 变更预约
     *
     * @param id    预约id
     * @param resid 预约号源主键ID
     * @return 操作返回码
     */
    @PostMapping(value = "/change")
    @ApiOperation(value = "变更预约", notes = "变更预约", httpMethod = "POST", response = ResultBG.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "预约主键ID", required = true, dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "resid", value = "预约号源主键ID", required = true, dataType = "Integer", paramType = "query")})
    public R<ResultBG> change(Integer id, Integer resid,String uid,String idtype) {
        return tbBespokeService.changeNew(id, resid, uid, idtype);
    }


    /**
     * 预约系统人工报到
     *
     * @param id 预约id
     * @return 数据集合
     */
    @PostMapping(value = "/selfReport")
    @ApiOperation(value = "自主报到", notes = "自主报到", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "操作人id", required = true, dataType = "String", paramType = "query")
    })
    public R<TbBespoke> selfReport(Integer id,String userID) {
        return tbBespokeService.selfReport(id,userID);
    }

    /**
     * 预约系统快速报到
     *
     * @param reportInfo 预约reportInfo
     * @return 数据集合
     */
    @PostMapping(value = "/quickReport")
    @ApiOperation(value = "快速报到", notes = "快速报到", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约集合ids", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "操作人id", required = true, dataType = "String", paramType = "query")
    })
    public R<List<TbBespoke>> quickReport(@RequestBody ReportInfo reportInfo) {
        return tbBespokeService.quickReport(reportInfo);
    }

    /**
     * 确费
     *
     * @param id 预约id
     * @return 数据集合
     */
    @PostMapping(value = "/regRequest")
    @ApiOperation(value = "确费", notes = "确费", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "操作人id", required = true, dataType = "String", paramType = "query")
    })
    public R<TbBespoke> regRequest(Integer id,String userID) {
        return tbBespokeService.regRequest(id,userID);
    }

    /**
     * 获取检查状态和排队号
     */
    @PostMapping(value = "/getBespokeStatusAndQueueNo")
    @ApiOperation(value = "获取检查状态和排队号", notes = "获取检查状态和排队号", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bespokeId", value = "预约ID", required = true, dataType = "Integer", paramType = "query")
    })
    public String getBespokeStatusAndQueueNo(String bespokeId) {
        return tbBespokeService.getBespokeStatusAndQueueNo(bespokeId);
    }

}

