package com.annet.service;

import com.annet.entity.TbHospital;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-01
 */
public interface TbHospitalService extends IService<TbHospital> {

    List<TbHospital> selectFinAll();
}
