package com.annet.controller;


import com.annet.entity.TbRoomRule;
import com.annet.result.R;
import com.annet.service.TbRoomRuleService;

import com.annet.vo.TbRoomRuleVo;
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
 * @author jh
 * @since 2020-01-13
 */
@RestController
@Api(value = "检查项目维护", tags = {"房间预约规则"})
@AllArgsConstructor
@RequestMapping("/tbRoomRule")
public class TbRoomRuleController {

    private final TbRoomRuleService tbRoomRuleService;

    /**
     * 查询房间预约规则对象列表
     *
     * @return 房间预约规则对象集合
     */
    @ApiOperation(value = "预约列表", notes = "预约列表", httpMethod = "POST", response = TbRoomRule.class)
    @RequestMapping("/findAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<List<TbRoomRule>> findAll(TbRoomRuleVo tbRoomRuleVo) {
        return tbRoomRuleService.findAllNew(tbRoomRuleVo);
    }

    /**
     * 新增房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    @RequestMapping("/add")
    @ApiImplicitParams({@ApiImplicitParam(name = "tbRoomRule", value = "院区名称", required = true, dataType = "TbRoomRule", paramType = "query")})
    public R<TbRoomRule> add(TbRoomRule tbRoomRule) {
        return tbRoomRuleService.add(tbRoomRule);
    }

    /**
     * 修改房间预约规则
     *
     * @param tbRoomRule 房间预约规则
     * @return 房间预约规则对象信息
     */
    @RequestMapping("/update")
    @ApiImplicitParams({@ApiImplicitParam(name = "tbRoomRule", value = "房间预约规则", required = true, dataType = "TbRoomRule", paramType = "query")})
    public R<TbRoomRule> update(TbRoomRule tbRoomRule) {
        return tbRoomRuleService.update(tbRoomRule);
    }

    @RequestMapping("/delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "tbRoomRule", value = "房间预约规则", required = true, dataType = "Integer", paramType = "query")})
    public R<Integer> delete(Integer id) {
        return tbRoomRuleService.delete(id);
    }
}

