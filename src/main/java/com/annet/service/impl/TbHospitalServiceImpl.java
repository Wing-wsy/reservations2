package com.annet.service.impl;

import com.annet.entity.TbHospital;
import com.annet.mapper.TbHospitalMapper;
import com.annet.service.TbHospitalService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-01
 */
@Service
@AllArgsConstructor
public class TbHospitalServiceImpl extends ServiceImpl<TbHospitalMapper, TbHospital> implements TbHospitalService {

    private final TbHospitalMapper tbHospitalMapper;

    @Override
    public List<TbHospital> selectFinAll() {
        return tbHospitalMapper.finAll();
    }
}
