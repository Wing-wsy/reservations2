package com.annet.service;

import com.annet.common.MyException;
import com.annet.entity.TbUser;
import com.annet.result.R;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 用户表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbUserService extends IService<TbUser> {
    /**
     * 登录
     * @param UserID 用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    R<TbUser> login(String UserID, String Password) throws MyException;
    /**
     * 伪登录
     * @param UserID 用户名
     * @param Password 密码
     * @return 用户信息
     * @throws MyException 异常
     */
    R<TbUser> pseudoLogin(String UserID, String Password) throws MyException;


    /**
     * 用户列表
     * @param hospitalName 院区名称
     * @return 用户列表
     */
    List<TbUser> FinAll(String hospitalName,String deptName);

    /**
     * 添加用户
     * @param tbUser 用户
     * @return 修改后用户信息
     * @throws MyException 异常信息
     */
    R<String> addTbUser(TbUser tbUser) throws MyException;

    /**
     * 用户修改
     * @param tbUser 用户
     * @return 修改后用户对象
     */
    R<String> modifyTbUser(TbUser tbUser) throws MyException;

    /**
     * 通过ID获取用户
     * @param id 用户名
     * @return 用户信息
     */
    List<TbUser> FinId(String id);
}
