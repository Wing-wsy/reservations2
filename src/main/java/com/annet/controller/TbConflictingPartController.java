package com.annet.controller;


import com.annet.entity.TbConflictingPart;
import com.annet.result.R;
import com.annet.service.TbConflictingPartService;

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

/**
 * <p>
 * 前端控制器
 * </p>
 * 检查项目互斥表
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@Api(value = "检查项目互斥表接口", tags = {"检查项目互斥表接口"})
@AllArgsConstructor
@RequestMapping("/tbConflictingPart")
public class TbConflictingPartController {

    private final TbConflictingPartService tbConflictingPartService;

    /**
     * 获取列表
     *
     * @return 检查项目互斥表列表
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "检查项目互斥表列表", notes = "获取检查项目互斥表的列表", httpMethod = "GET", response = TbConflictingPart.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<TbConflictingPart> finAll(String hospitalName,String deptName) {
        return new R(tbConflictingPartService.FinAll(hospitalName,deptName));
    }

    /**
     * 添加
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    @PostMapping("/add")
    @ApiOperation(value = "检查项目互斥添加", notes = "添加检查项目互斥", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbConflictingPart", value = "添加检查项目互斥", required = true, dataType = "TbConflictingPart", paramType = "query")
    public R<Integer> addTbConflictingPart(TbConflictingPart tbConflictingPart) {
        return tbConflictingPartService.AddTbConflictingPart(tbConflictingPart);
    }

    /**
     * 修改
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    @PutMapping("/modify")
    @ApiOperation(value = "检查项目互斥修改", notes = "修改检查项目互斥", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbConflictingPart", value = "=修改检查项目互斥", required = true, dataType = "TbConflictingPart", paramType = "query")
    public R<Integer> modifyTbConflictingPart(TbConflictingPart tbConflictingPart) {
        return tbConflictingPartService.ModifyTbConflictingPart(tbConflictingPart);
    }

    /**
     * 删除
     *
     * @param id 检查互斥编号id
     * @return 返回码
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "检查项目互斥删除", notes = "检查项目互斥对照", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "id", value = "=删除检查方法部位对照", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteTbConflictingPart(Integer id) {
        return tbConflictingPartService.DeleteTbConflictingPart(id);
    }
}

