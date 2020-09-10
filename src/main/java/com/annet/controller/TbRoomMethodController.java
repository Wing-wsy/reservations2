package com.annet.controller;


import com.annet.entity.TbRoomMethod;
import com.annet.result.R;
import com.annet.service.TbRoomMethodService;

import com.annet.vo.TbRoomMethodVo;
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
 *  前端控制器
 * </p>
 * 房间支持检查方法表
 * @author XiaYu
 * @since 2019-04-28
 */
@RestController
@Api(value="房间支持检查方法表接口",tags={"房间支持检查方法表接口"})
@AllArgsConstructor
@RequestMapping("/tbRoomMethod")
public class TbRoomMethodController {

    private final TbRoomMethodService tbRoomMethodService;


    /**
     * @param tbRoomMethodVo 院区名称
     * @return 检查房间列表
     */
    @GetMapping("/finAll")
    @ApiOperation(value="房间支持检查方法列表", notes="获取房间支持检查方法的列表",httpMethod = "GET",response = TbRoomMethod.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "hospitalName", value = "院区名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptName", value = "科室名称", required = true, dataType = "String", paramType = "query")})
    public R<TbRoomMethod> finAll(TbRoomMethodVo tbRoomMethodVo){
        return new R(tbRoomMethodService.finAllNew(tbRoomMethodVo));
    }

    /**
     * 添加
     * @param tbRoomMethod 检查房间
     * @return 检查房间对象
     */
    @PostMapping("/add")
    @ApiOperation(value="房间支持检查方法添加", notes="添加房间支持检查方法",httpMethod = "POST",response = Integer.class)
    @ApiImplicitParam(name = "tbRoomMethod", value = "添加房间支持检查方法", required = true, dataType = "TbRoomMethod", paramType = "query")
    public R<Integer> addRoomMethod(TbRoomMethod tbRoomMethod){
        return tbRoomMethodService.addRoomMethod(tbRoomMethod);
    }

    /**
     * 修改
     * @param tbRoomMethod 修改信息
     * @return 房间信息对象
     */
    @PutMapping("/modify")
    @ApiOperation(value="房间支持检查方法修改", notes="修改房间支持检查方法",httpMethod = "PUT",response = Integer.class)
    @ApiImplicitParam(name = "tbRoomMethod", value = "=修改房间支持检查方法", required = true, dataType = "TbRoomMethod", paramType = "query")
    public R<Integer> modifyRoomMethod(TbRoomMethod tbRoomMethod){
        return tbRoomMethodService.modifyRoomMethod(tbRoomMethod);
    }

    /**
     * 删除
     * @param id 房间id
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @ApiOperation(value="房间支持检查方法删除", notes="删除房间支持检查方法",httpMethod = "DELETE",response = Integer.class)
    @ApiImplicitParam(name = "id", value = "=删除房间支持检查方法", required = true, dataType = "Integer", paramType = "query")
    public R<Integer> deleteRoomMethod(Integer id){
        return tbRoomMethodService.deleteRoomMethod(id);
    }
}

