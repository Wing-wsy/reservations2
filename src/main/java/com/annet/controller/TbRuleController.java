package com.annet.controller;


import com.annet.entity.TbRule;
import com.annet.result.R;
import com.annet.service.TbRuleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RequestMapping("/tbRule")
@RestController
@Api(value="权限表接口",tags={"权限表接口"})
@AllArgsConstructor
public class TbRuleController {

    private final TbRuleService tbRuleService;

    /**
     * 权限列表
     * @return
     */
    @GetMapping("/finAll")
    @ApiOperation(value="权限列表", notes="获取权限的列表",httpMethod = "GET",response = TbRule.class)
    public R<List<TbRule>> finAll(){
        return new R(tbRuleService.finall());
    }

    //添加权限

    //修改权限

    //删除权限
}

