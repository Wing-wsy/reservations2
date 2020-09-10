package com.annet.controller;


import com.annet.entity.TbDepttable;
import com.annet.result.R;
import com.annet.service.TbDepttableService;

import com.annet.xmlEntity.RequestDeptInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/tbDepttable")
@Api(value = "科室表接口", tags = {"科室表接口"})
@AllArgsConstructor
public class TbDepttableController {

    private final TbDepttableService tbDepttableService;

    /**
     * 科室列表
     *
     * @return 科室列表集合
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "科室管理列表", notes = "获取科室管理的列表", httpMethod = "GET", response = TbDepttable.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isReserve", value = "是否是医技预约科室", required = true, dataType = "Integer", paramType = "query")
    })
    public R<List<TbDepttable>> finAll(String deptName, String hospitalName, String histype, Integer isReserve) {
        return tbDepttableService.FinAll(deptName,hospitalName,histype,isReserve);
    }

    /**
     * 添加科室
     *
     * @param tbDepttable 科室对象
     * @return 科室集合
     */
    @PostMapping("/add")
    @ApiOperation(value = "科室添加", notes = "添加科室", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbDepttable", value = "添加科室", required = true, dataType = "TbDepttable", paramType = "query")
    public R<Integer> addTbDepttable(TbDepttable tbDepttable) {
        return tbDepttableService.addTbDepttable(tbDepttable);
    }


    /**
     * 修改科室
     *
     * @param tbDepttable
     * @return
     */
    @PutMapping("/modify")
    @ApiOperation(value = "科室修改", notes = "修改科室", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbDepttable", value = "=修改科室", required = true, dataType = "TbDepttable", paramType = "query")
    public R<Integer> modifyTbDepttable(TbDepttable tbDepttable) {
        return tbDepttableService.modifyTbDepttable(tbDepttable);
    }

    /**
     * @return 科室列表集合
     */
    @GetMapping("/filterDepttable")
    @ApiOperation(value = "去重下拉科室", notes = "去重科室", httpMethod = "GET", response = Integer.class)
    public R<Integer> filterDepttable() {
        return new R(tbDepttableService.filterDepttable());
    }


    /**
     * 删除
     *
     * @param deptId 检查方法部位id
     * @return 操作码
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "科室删除", notes = "科室删除", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "deptId", value = "=科室编号", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteRoomMethod(Integer deptId) {
        return tbDepttableService.delete(deptId);
    }


    /**
     * 同步科室信息
     * @return 申请科室集合
     */
    @GetMapping("/syn")
    @ApiOperation(value = "同步科室信息", notes = "同步科室信息", httpMethod = "GET", response = RequestDeptInfo.class)
    @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")
    public void synTbDepttable(String hospitalName) {
        tbDepttableService.synTbDepttable(hospitalName);
    }

    /**无status字段，暂时不用
     @PutMapping("/stop")
     @ApiOperation(value="科室禁用，启用", notes="科室禁用，启用",httpMethod = "PUT",response = Integer.class)
     @ApiImplicitParams({
     @ApiImplicitParam(name = "id", value = "科室ID", required = true, dataType = "Integer", paramType = "query"),
     @ApiImplicitParam(name = "status", value = "状态：1为启用，0为禁用", required = true, dataType = "Integer", paramType = "query")
     })
     public R<Integer> stopRestore(Integer id,Integer status){
     return new R(tbDepttableService.stopRestore(id,status));
     }*/
}

