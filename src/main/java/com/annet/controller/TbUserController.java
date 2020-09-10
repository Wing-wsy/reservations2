package com.annet.controller;


import com.annet.common.MyException;
import com.annet.entity.TbUser;
import com.annet.result.R;
import com.annet.service.TbUserService;

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
 * 用户表
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/tbUser")
@Api(value = "用户表接口", tags = {"用户表接口"})
@AllArgsConstructor
public class TbUserController {

    private final TbUserService tbUserService;

    /**
     * 登录
     *
     * @param UserID   用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    @GetMapping("/login")
    @ApiOperation(value = "登录", notes = "使用帐号和密码来登录，获取用户信息", httpMethod = "GET", response = TbUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserID", value = "用户登录帐号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "Password", value = "用户登录密码", required = true, dataType = "String", paramType = "query")
    })
    public R<TbUser> login(String UserID, String Password) throws MyException {
        return tbUserService.login(UserID, Password);
    }

    /**
     * 登录
     *
     * @param UserID   用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    @GetMapping("/pseudoLogin")
    @ApiOperation(value = "伪登录", notes = "使用帐号和密码来登录，获取用户信息", httpMethod = "GET", response = TbUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserID", value = "用户登录帐号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "Password", value = "用户登录密码", required = true, dataType = "String", paramType = "query")
    })
    public R<TbUser> pseudoLogin(String UserID, String Password) throws MyException {
        return tbUserService.pseudoLogin(UserID, Password);
    }


    /**
     * 用户列表
     *
     * @return 用户列表
     */
    @GetMapping("/finAll")
    @ApiOperation(value = "用户管理列表", notes = "获取用户管理的列表", httpMethod = "GET", response = TbUser.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<TbUser> finAll(String hospitalName,String deptName) {
        return new R(tbUserService.FinAll(hospitalName,deptName));
    }

    /**
     * 添加用户
     *
     * @param tbUser 用户
     * @return 修改后用户信息
     * @throws MyException 异常信息
     */
    @PostMapping("/add")
    @ApiOperation(value = "用户添加", notes = "添加用户", httpMethod = "POST", response = String.class)
    @ApiImplicitParam(name = "tbUser", value = "添加用户", required = true, dataType = "TbUser", paramType = "query")
    public R<String> addTbUser(TbUser tbUser) {
        return tbUserService.addTbUser(tbUser);
    }

    /**
     * 用户修改
     *
     * @param tbUser 用户
     * @return 修改后用户对象
     */
    @PutMapping("/modify")
    @ApiOperation(value = "用户修改", notes = "修改用户", httpMethod = "PUT", response = String.class)
    @ApiImplicitParam(name = "tbUser", value = "修改用户", required = true, dataType = "TbUser", paramType = "query")
    public R<String> modifyTbUser(TbUser tbUser) {
        return tbUserService.modifyTbUser(tbUser);
    }

    /**
     * 通过ID获取用户
     *
     * @param id 用户名
     * @return 用户信息
     */
    @GetMapping("/finId")
    @ApiOperation(value = "通过ID获取用户", notes = "通过ID获取用户", httpMethod = "GET", response = TbUser.class)
    @ApiImplicitParam(name = "id", value = "用户账号", required = true, dataType = "String", paramType = "query")
    public R<TbUser> finId(String id) {
        return new R(tbUserService.FinId(id));
    }

}

