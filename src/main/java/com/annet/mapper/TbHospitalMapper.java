package com.annet.mapper;

import com.annet.entity.TbHospital;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-01
 */
public interface TbHospitalMapper extends BaseMapper<TbHospital> {

    List<TbHospital> finAll();
}
