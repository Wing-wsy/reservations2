package com.annet.controller;


import com.annet.entity.TbHospital;
import com.annet.result.R;
import com.annet.service.TbHospitalService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/tbHospital")
@Api(value = "院区列表",tags ={"院区列表接口"} )
@AllArgsConstructor
public class TbHospitalController {

    private final TbHospitalService tbHospitalService;

    @GetMapping(value = "/finAll")
    @ApiOperation(value = "院区",notes="获取所有的院区科室",httpMethod = "GET",response = TbHospital.class)
    public R<TbHospital> finAll(){
        return new R(tbHospitalService.selectFinAll());
    }
}

