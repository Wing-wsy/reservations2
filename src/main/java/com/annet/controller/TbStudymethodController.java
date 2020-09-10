package com.annet.controller;


import com.annet.entity.TbStudymethod;
import com.annet.result.R;
import com.annet.service.TbStudymethodService;

import com.annet.vo.TbStudymethodVo;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@Api(value = "检查方法接口", tags = {"检查方法接口"})
@AllArgsConstructor
@RequestMapping("/tbStudymethod")
public class TbStudymethodController {

    private final TbStudymethodService tbStudymethodService;

    /**
     * 检查方法列表
     *
     * @param tbStudymethodVo 院区名称
     * @return 检查方法集合
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "检查方法列表", notes = "获取检查方法的列表", httpMethod = "GET", response = TbStudymethod.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query")})
    /*public R<List<TbStudymethod>> finAll(String hospitalName,String deptName) {
        return new R(tbStudymethodService.finAll(hospitalName,deptName));
    }*/
    public R<List<TbStudymethod>> finAll(TbStudymethodVo tbStudymethodVo) {
        return tbStudymethodService.finAllNew(tbStudymethodVo);
    }

    /**
     * 检查方法添加
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    @PostMapping("/add")
    @ApiOperation(value = "检查方法添加", notes = "检查方法添加", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbStudymethod", value = "检查方法添加", required = true, dataType = "TbStudymethod", paramType = "query")
    public R<Integer> addTbStudymethod(TbStudymethod tbStudymethod) {
        return tbStudymethodService.AddStudymethod(tbStudymethod);
    }

    /**
     * 检查方法修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    @PutMapping("/modify")
    @ApiOperation(value = "检查方法修改", notes = "修改检查方法", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbStudymethod", value = "=修改检查方法", required = true, dataType = "TbStudymethod", paramType = "query")
    public R<Integer> modifyTbStudymethod(TbStudymethod tbStudymethod) {
        return tbStudymethodService.modifyStudymethod(tbStudymethod);
    }

    /**
     * 检查方法状态修改
     *
     * @param tbStudymethod 检查方法
     * @return 检查方法集合
     */
    @PutMapping("/modifyStatus")
    @ApiOperation(value = "检查方法状态修改", notes = "检查方法状态修改", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbStudymethod", value = "=修改检查方法", required = true, dataType = "TbStudymethod", paramType = "query")
    public R<Integer> modifyStatus(TbStudymethod tbStudymethod) {
        return tbStudymethodService.modifyStatus(tbStudymethod);
    }
    /**
     * 检查方法删除
     *
     * @param methodId 检查方法id
     * @return 检查方法集合
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "检查方法删除", notes = "删除检查方法", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "methodId", value = "检查方法id", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteTbStudymethod(Integer methodId) {
        return tbStudymethodService.deleteStudymethod(methodId);
    }


}

