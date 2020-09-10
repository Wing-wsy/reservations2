package com.annet.mapper;

import com.annet.entity.TbUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * 用户表
 * @author XiaYu
 * @since 2019-04-28
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    /**
     * 判断用户是否存在
     * @param UserID
     * @return
     */
    TbUser selectUserID(@Param("UserID") String UserID);

    /**
     * 登录
     * @param UserID 用户名
     * @param Password 密码
     * @return 用户信息
     */
    List<TbUser> login(@Param("UserID") String UserID, @Param("Password") String Password);

    /**
     * 用户列表
     *
     */
    List<TbUser> selectFinAll(@Param("hospitalName") String hospitalName);

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    List<TbUser> selectFinId(@Param("id") String id);

    /**
     * 添加用户
     * @param tbUser
     * @return
     */
    Integer insertTbUser(TbUser tbUser);

    /**
     * 修改用户
     * @param tbUser
     * @return
     */
    Integer updateTbUser(TbUser tbUser);
}
