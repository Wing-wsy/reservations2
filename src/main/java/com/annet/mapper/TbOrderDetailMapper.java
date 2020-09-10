package com.annet.mapper;

import com.annet.entity.TbOrderDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-28
 */
public interface TbOrderDetailMapper extends BaseMapper<TbOrderDetail> {

    /**
     * 添加
     *
     * @param tbOrderDetail 医嘱明细表对象
     * @return id
     */
    Integer insertTbOrderDetail(TbOrderDetail tbOrderDetail);

    List<TbOrderDetail> selectByBespokeID(@Param("id") Integer id);

    List<TbOrderDetail> selectByIds(@Param("ids") List<Integer> ids);

    List<TbOrderDetail> selectDistinctByBespokeID(@Param("id") Integer id);




}
