package com.annet.controller;


import com.annet.entity.TbBespokeresTemplate;
import com.annet.result.R;
import com.annet.service.TbBespokeresTemplateService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
 * 预约号源
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@Api(value = "预约号源模板接口", tags = {"预约号源模板接口"})
@AllArgsConstructor
@RequestMapping("/tbBespokeresTemplate")
public class TbBespokeresTemplateController {

    private final TbBespokeresTemplateService tbBespokeresTemplateService;

    /**
     * 模板集合
     *
     * @param tbBespokeresTemplate 号源模板对象
     * @return 号源模板集合
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "预约号源模板列表", notes = "预约号源模板列表", httpMethod = "GET", response = TbBespokeresTemplate.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "tbBespokeresTemplate", value = "号源模板对象", required = true, dataType = "TbBespokeresTemplate", paramType = "query")})
    public R<List<TbBespokeresTemplate>> finAll(TbBespokeresTemplate tbBespokeresTemplate) {
        return tbBespokeresTemplateService.finAll(tbBespokeresTemplate);
    }

    /**
     * 添加
     *
     * @param tbBespokeresTemplate 模板对象
     * @return 操作成功对象
     */
    @PostMapping("/add")
    @ApiOperation(value = "预约号源模板添加", notes = "添加预约号源模板", httpMethod = "POST", response = Integer.class)
    @ApiImplicitParam(name = "tbBespokeresTemplate", value = "添加预约号源模板", required = true, dataType = "TbBespokeresTemplate", paramType = "query")
    public R<Integer> addBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate) {
        return tbBespokeresTemplateService.addBespokeresTemplate(tbBespokeresTemplate);
    }

    /**
     * 修改
     *
     * @param tbBespokeresTemplate 对象信息
     * @return 修改成功码
     */
    @PutMapping("/modify")
    @ApiOperation(value = "预约号源模板修改", notes = "修改预约号源模板", httpMethod = "PUT", response = Integer.class)
    @ApiImplicitParam(name = "tbBespokeresTemplate", value = "=修改预约号源模板", required = true, dataType = "TbBespokeresTemplate", paramType = "query")
    public R<Integer> modifyBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate) {
        return tbBespokeresTemplateService.modifyBespokeresTemplate(tbBespokeresTemplate);
    }

    /**
     * 删除
     *
     * @param id 模板id
     * @return 删除操作码
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "预约号源模板删除", notes = "预约号源模板对照", httpMethod = "DELETE", response = Integer.class)
    @ApiImplicitParam(name = "id", value = "=删除预约号源模板", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteBespokeresTemplate(Integer id) {
        return tbBespokeresTemplateService.deleteBespokeresTemplate(id);
    }


}

