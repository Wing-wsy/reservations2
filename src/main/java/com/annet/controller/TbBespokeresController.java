package com.annet.controller;


import com.annet.entity.TbBespokeres;
import com.annet.entity.domain.GetBespokeRes;
import com.annet.entity.domain.GetRecommendation;
import com.annet.result.R;
import com.annet.service.TbBespokeresService;
import com.annet.service.TbSuspendMedicalServiceService;
import com.annet.vo.GetRecommendationVo;
import com.annet.xmlEntity.GetRequestInfo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 前端控制器
 * </p>
 * 预约号源
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@AllArgsConstructor
@Api(value = "预约号源接口", tags = {"预约号源接口"})
@RequestMapping("/tbBespokeres")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class TbBespokeresController {

    private final TbBespokeresService tbBespokeresService;

    private final TbSuspendMedicalServiceService tbSuspendMedicalServiceService;

    /**
     * 预约号源列表
     *
     * @param tbBespokeres 预约号源
     * @return 预约号源集合
     */
    @GetMapping(value = "/finAll")
    @ApiOperation(value = "预约号源列表", notes = "预约号源列表", httpMethod = "GET", response = TbBespokeres.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbBespokeres", value = "预约号源查询", required = true, dataType = "TbBespokeres", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")
    })
    public R<TbBespokeres> finAll(TbBespokeres tbBespokeres, String deptName) {
        return new R(tbBespokeresService.finAll(tbBespokeres, deptName));
    }

    /**
     * 预约号源添加
     *
     * @param tbBespokeres 预约号源
     * @return 号源对象
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "预约号源添加", notes = "添加预约号源", httpMethod = "POST", response = String.class)
    @ApiImplicitParam(name = "tbBespokeres", value = "添加预约号源", required = true, dataType = "TbBespokeres", paramType = "query")
    public R<String> addTbBespokeres(TbBespokeres tbBespokeres) {
        return tbBespokeresService.addTbBespokeres(tbBespokeres);
    }

    /**
     * 预约号源修改
     *
     * @param tbBespokeres 预约号源
     * @return 号源
     */
    @PutMapping(value = "modify")
    @ApiOperation(value = "预约号源修改", notes = "修改预约号源", httpMethod = "PUT", response = String.class)
    @ApiImplicitParam(name = "tbBespokeres", value = "=修改预约号源", required = true, dataType = "TbBespokeres", paramType = "query")
    public R<String> modifyTbBespokeres(TbBespokeres tbBespokeres) {
        return tbBespokeresService.modifyTbBespokeres(tbBespokeres);
    }

    /**
     * @param id 号源id
     * @return 操作返回码
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "预约号源删除", notes = "预约号源对照", httpMethod = "DELETE", response = String.class)
    @ApiImplicitParam(name = "id", value = "=删除预约号源", required = true, dataType = "Integer", paramType = "query")
    public R<String> deteleTbBespokeres(Integer id) {
        return tbBespokeresService.deteleTbBespokeres(id);
    }

    /**
     * 获取推荐时间
     *
     * @param requestXml xml
     * @param preference 偏好
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param timeFrame  时段
     * @return 推荐列表
     */
    @GetMapping(value = "/recommendDate")
    @ApiOperation(value = "获取推荐时间", notes = "获取推荐时间", httpMethod = "GET", response = GetRecommendation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestXml", value = "tb_method_part表主键和就诊方式", required = true, dataType = "String []", paramType = "query"),
            @ApiImplicitParam(name = "preference", value = "偏好 0：最快  1:同一天  2：周末  3：非周末", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "预约时间起 格式：2019-05-15", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "预约时间止 格式：2019-05-15", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "timeFrame", value = "时段 0：全天 1：上午 2：下午", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "用户ID", required = true, dataType = "String", paramType = "query")
    })
    public R<GetRecommendation> recommendDate(String[] requestXml, Integer preference, String startDate,
                                              String endDate, Integer timeFrame, String hospitalName, String hospitalizeNo, String userID) {
        return new R(tbBespokeresService.getRecommendationNew(requestXml, preference, startDate, endDate, timeFrame, hospitalName, hospitalizeNo, userID));
    }

    /**
     * 调用推荐方案与自选方案接口之前先掉预约合并接口
     * 可预约申请的判断合并
     *
     * @param getRequestInfoList 可预约申请单集合
     * @return 合并的新预约对象
     */
    @PostMapping(value = "/mergeAppointment")
    @ApiOperation(value = "可预约申请的判断合并", notes = "可预约申请的判断合并", httpMethod = "POST", response = GetRecommendation.class)
    @ApiImplicitParam(name = "getRequestInfoList", value = "可预约申请单集合", required = true, dataType = "List<GetRequestInfo>", paramType = "query")
    public R<GetRecommendationVo> mergeAppointment(@RequestBody List<GetRequestInfo> getRequestInfoList) throws ParseException {
        if(getRequestInfoList.size() > 0){
            return new R(tbBespokeresService.mergeAppointment(getRequestInfoList));
        }
        return new R(1,new ArrayList<>(),"请至少勾选一个申请单！");
    }

    /**
     * 获取可选时间(检查设备，检查部位，检查项目, 检查方法)
     */
    @GetMapping(value = "/getOptionalDate")
    @ApiOperation(value = "获取可选时间", notes = "获取可选时间", httpMethod = "GET", response = GetBespokeRes.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partID", value = "tb_method_part表主键(部位ID)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalType", value = "就诊类型 Y住院 N门诊", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "timeCoefficient", value = "检查时间系数", required = true, dataType = "String", paramType = "query")
    })
    public R<GetBespokeRes> getOptionalDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient, String studyGroupName) {
        return new R(tbBespokeresService.getOptionalDate(partID, hospitalType, startDate, endDate, hospitalName, timeCoefficient, studyGroupName));
    }


    /**
     * 选择预约时间
     *
     * @param resID        号源表ID
     * @param hospitalType 就诊类型
     * @param partID       tb_method_part表主键(部位ID)
     * @return 预约表
     */
    @PostMapping(value = "/Period")
    //@PutMapping(value = "/Period")
    @ApiOperation(value = "选择预约时间", notes = "选择预约时间", httpMethod = "PUT", response = TbBespokeres.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resID", value = "号源表ID", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "hospitalType", value = "就诊类型 Y住院 N门诊", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "partID", value = "tb_method_part表主键(部位ID)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "timeCoefficient", value = "时间系数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userID", value = "用户ID", required = true, dataType = "String", paramType = "query")
    })
    public R<TbBespokeres> selectionPeriod(Integer resID, String hospitalType, String partID,Integer timeCoefficient,String userID) {
        return tbBespokeresService.selectionPeriod(resID, hospitalType, partID,timeCoefficient,userID);
    }

    /**
     * 推荐预约方案后，清除号源占用
     */
    @PostMapping(value = "/deleteSource")
    //@DeleteMapping(value = "/deleteSource")
    @ApiOperation(value = "清除号源占用", notes = "清除号源占用", httpMethod = "DELETE", response = Integer.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "partID", value = "占用号源的partID,可不传，不传清除所有的号源", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "String", paramType = "query")
//    })
    public R<Integer> clearSource(String partID,String userID) {
        return new R(tbBespokeresService.clearSource(partID,userID));
    }


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
     * @throws ParseException 时间转化异常
     */
    @PostMapping(value = "/multiplex")
    @ApiOperation(value = "预约号源复用", notes = "预约号源复用", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modality", value = "检查设备", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupType", value = "分组方式", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "分组名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "复用原始开始时间,格式：2019-11-06 ~ 2019-11-07", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "复用原始结束时间,格式：2019-11-12 ~ 2019-11-13", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "multiplexStartDate", value = "复用目标开始时间,格式：2019-11-06 ~ 2019-11-07", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "multiplexEndDate", value = "复用目标结束时间,格式：2019-11-12 ~ 2019-11-13", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")
    })
    public R<List<TbBespokeres>> multiplexTbBespokeres(String modality, Integer groupType,
                                            String groupName, String startDate,
                                            String endDate, String multiplexStartDate,
                                            String multiplexEndDate, String hospitalName) throws ParseException {
        return tbBespokeresService.multiplexTbBespokeres(modality, groupType, groupName, startDate, endDate, multiplexStartDate, multiplexEndDate, hospitalName);
    }

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
    @PostMapping(value = "/stop")
    @ApiOperation(value = "预约号源停诊", notes = "预约号源停用", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modality", value = "检查设备", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupType", value = "分组方式", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "分组名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stopStartDate", value = "号源停诊开始时间,格式：2019-11-06 ~ 2019-11-07", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stopEndDate", value = "号源停诊结束时间,格式：2019-11-12 ~ 2019-11-13", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")
    })
    public R<Integer> stopTbBespokeres(String modality, Integer groupType, String groupName,
                                       String stopStartDate, String stopEndDate, String hospitalName) throws ParseException {
        return new R(tbSuspendMedicalServiceService.stopTbBespokeres(modality, groupType, groupName, stopStartDate, stopEndDate, hospitalName));
    }


    /**
     * 预约号源停诊列表
     *
     * @param hospitalName 预约号源
     * @return 预约号源停诊集合
     */
    @GetMapping(value = "/finAllStop")
    @ApiOperation(value = "停诊预约号源列表", notes = "停诊预约号源列表", httpMethod = "GET", response = TbBespokeres.class)
    @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")
    public R<TbBespokeres> finAllStop(String hospitalName) {
        return new R(tbSuspendMedicalServiceService.finAllStop(hospitalName));
    }

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
    @GetMapping(value = "/deleteStopBespoke")
    @ApiOperation(value = "删除停诊号源", notes = "删除停诊号源", httpMethod = "GET", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "检查设备ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "modality", value = "检查设备", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupType", value = "分组方式", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "分组名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "号源停诊开始时间,格式：2019-11-07", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "号源停诊结束时间,格式：2019-11-13", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")
    })
    public R<TbBespokeres> deleteStopBespoke(Integer id, String modality, Integer groupType, String groupName, String startDate, String endDate, String hospitalName) {
        return tbSuspendMedicalServiceService.deleteStopBespoke(id, modality, groupType, groupName, startDate, endDate, hospitalName);
    }


    /**
     * 预约号源情况查询
     * @param tbBespokeres 号源表
     * @return 号源情况查询集合
     */
    @PostMapping("/queryTbBespokeres")
    @ApiOperation(value = "预约号源情况查询", notes = "预约号源情况查询", httpMethod = "POST",response = TbBespokeres.class)
    @ApiImplicitParam(name = "tbBespokeres", value = "预约号源情况查询", required = true, dataType = "TbBespokeres", paramType = "query")
    public R<TbBespokeres> queryTbBespokeres(TbBespokeres tbBespokeres) {
        return new R(tbBespokeresService.queryTbBespokeres(tbBespokeres));
    }

    /**
     * 微信端获取不可选时间(检查设备，检查部位，检查项目, 检查方法)
     */
    @GetMapping(value = "/getAppOptionalNotDate")
    @ApiOperation(value = "获取可选时间", notes = "获取可选时间", httpMethod = "GET", response = GetBespokeRes.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partID", value = "tb_method_part表主键(部位ID)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalType", value = "就诊类型 Y住院 N门诊", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "timeCoefficient", value = "检查时间系数", required = true, dataType = "String", paramType = "query")
    })
    public String getAppOptionalNotDate(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient) {
        return tbBespokeresService.getAppOptionalNotDate(partID, hospitalType, startDate, endDate, hospitalName, timeCoefficient);
    }

    /**
     * 获取自选方案可预约的检查房间
     */
    @GetMapping(value = "/getStudyGroupName")
    @ApiOperation(value = "获取可选时间", notes = "获取可选时间", httpMethod = "GET", response = GetBespokeRes.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partID", value = "tb_method_part表主键(部位ID)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalType", value = "就诊类型 Y住院 N门诊", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,格式：2019-5-25", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "timeCoefficient", value = "检查时间系数", required = true, dataType = "String", paramType = "query")
    })
    public String getStudyGroupName(String partID, String hospitalType, String startDate, String endDate, String hospitalName, String timeCoefficient) {
        return tbBespokeresService.getStudyGroupName(partID, hospitalType, startDate, endDate, hospitalName, timeCoefficient);
    }

}

