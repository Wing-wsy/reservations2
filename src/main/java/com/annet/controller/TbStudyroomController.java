package com.annet.controller;


import com.annet.entity.TbStudyroom;
import com.annet.result.R;
import com.annet.service.TbStudyroomService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * 检查房间
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RequestMapping("/tbStudyroom")
@RestController
@Api(value = "检查房间表接口", tags = {"检查房间表接口"})
@AllArgsConstructor
public class TbStudyroomController {

    private final TbStudyroomService tbStudyroomService;


    /**
     * 检查房间列表
     *
     * @param hospitalName 院区名称
     * @return 检查房间列表
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "检查房间列表", notes = "获取检查房间的列表", httpMethod = "GET", response = TbStudyroom.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<List<TbStudyroom>> finAll(String hospitalName,String deptName) {
        return tbStudyroomService.finAll(hospitalName,deptName);
    }

    /**
     * 添加
     *
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加检查房间", notes = "添加检查房间", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbStudyroom", value = "添加检查房间", required = true, dataType = "TbStudyroom", paramType = "query")
    public R<Integer> addStudyroom(TbStudyroom tbStudyroom) {
        return tbStudyroomService.addStudyroom(tbStudyroom);
    }

    /**
     * 修改检查房间
     *
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    @PutMapping("/modify")
    @ApiOperation(value = "修改检查房间", notes = "修改检查房间", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbStudyroom", value = "=修改检查房间", required = true, dataType = "TbStudyroom", paramType = "query")
    public R<Integer> modifyStudyroom(TbStudyroom tbStudyroom) {
        return tbStudyroomService.modifyStudyroom(tbStudyroom);
    }


    /**
     * 停诊，恢复
     *
     * @param id     id
     * @param status 状态值
     * @return 成功标志码
     */
    @PutMapping("/stop")
    @ApiOperation(value = "停诊，恢复", notes = "停诊，恢复", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "检查房间ID", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：1为恢复，0为停诊", required = true, dataType = "Integer", paramType = "query")
    })
    public R<Integer> stopRestore(Integer id, Integer status) {
        return tbStudyroomService.stopRestore(id, status);
    }

    /**
     * 获取去重的设备类型
     *
     * @return 设备集合
     */
    @GetMapping("/groupByM")
    @ApiOperation(value = "获取去重的设备类型", notes = "获取去重的设备类型", httpMethod = "GET", response = TbStudyroom.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<TbStudyroom> groupByModality(String hospitalName,String deptName) {
        return new R(tbStudyroomService.selectGroupByModality(hospitalName,deptName));
    }
}

