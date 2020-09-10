package com.annet.controller;


import com.annet.entity.TbMethodPart;
import com.annet.result.R;
import com.annet.service.TbMethodPartService;

import com.annet.vo.TbMethodPartVo;
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
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@Api(value = "检查方法部位对照接口", tags = {"检查方法部位对照接口"})
@AllArgsConstructor
@RequestMapping("/tbMethodPart")
public class TbMethodPartController {

    private final TbMethodPartService tbMethodPartService;


    /**
     * 检查部位列表
     *
     * @param tbMethodPartVo 院区名称
     * @return 检查方法部位列表
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "检查方法部位对照列表", notes = "获取检查方法部位对照的列表", httpMethod = "GET", response = TbMethodPart.class)
    @ApiImplicitParam(name = "tbMethodPart", value = "=修改预约", required = true, dataType = "TbMethodPartVo", paramType = "query")
    public R<TbMethodPart> finAllNew(TbMethodPartVo tbMethodPartVo) {
        return new R(tbMethodPartService.finAllNew(tbMethodPartVo));
    }


    /**
     * 添加检查部位
     *
     * @param tbMethodPart 检查部位
     * @return 部位集合
     */
    @PostMapping("/add")
    @ApiOperation(value = "检查方法部位对照添加", notes = "添加检查方法部位对照", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbMethodPart", value = "添加检查方法部位对照", required = true, dataType = "TbMethodPart", paramType = "query")
    public R<Integer> addRoomMethod(TbMethodPart tbMethodPart) {
        return tbMethodPartService.AddTbMethodPart(tbMethodPart);
    }

    /**
     * 修改
     *
     * @param tbMethodPart 检查部位对象
     * @return 检查部位
     */
    @PutMapping("/modify")
    @ApiOperation(value = "检查方法部位对照修改", notes = "修改检查方法部位对照", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbMethodPart", value = "=修改检查方法部位对照", required = true, dataType = "TbMethodPart", paramType = "query")
    public R<Integer> modifyRoomMethod(TbMethodPart tbMethodPart) {
        return tbMethodPartService.ModifyTbMethodPart(tbMethodPart);
    }

    /**
     * 删除
     *
     * @param id 检查方法部位id
     * @return 操作码
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "检查方法部位对照删除", notes = "删除检查方法部位对照", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "id", value = "=删除检查方法部位对照", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteRoomMethod(Integer id) {
        return tbMethodPartService.DeleteTbMethodPart(id);
    }

    /**
     * 根据设备查找部位
     *
     * @param modality 设备
     * @return 检查部位集合
     */
    @GetMapping("/modalityPart")
    @ApiOperation(value = "根据设备查询检查部位", notes = "根据设备查询检查部位", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "modality", value = "设备", required = true, dataType = "String", paramType = "query")
    public R<TbMethodPart> modalityPart(String modality) {
        return new R(tbMethodPartService.selectModality(modality));
    }

    /**
     * 根据设备和检查方法查找检查部位
     *
     * @param modality 设备
     * @param method 方法
     * @return 检查部位集合
     */
    @PostMapping("/modalityAndMethodPart")
    @ApiOperation(value = "根据设备和检查方法查找检查部位", notes = "根据设备和检查方法查找检查部位", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modality", value = "设备", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "method", value = "检查方法", required = true, dataType = "String", paramType = "query")
    })

    public R<TbMethodPart> modalityAndMethodPart(String modality , String method , String hospitalName,String deptName ) {
        return new R(tbMethodPartService.selectModalityAndMethod(modality ,method ,hospitalName ,deptName));
    }
}

