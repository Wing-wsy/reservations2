package com.annet.service.impl;

import com.annet.entity.TbBespokeresLsb;
import com.annet.mapper.TbBespokeresLsbMapper;
import com.annet.service.TbBespokeresLsbService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XiaYu
 * @since 2019-08-01
 */
@Service
@AllArgsConstructor
public class TbBespokeresLsbServiceImpl extends ServiceImpl<TbBespokeresLsbMapper, TbBespokeresLsb> implements TbBespokeresLsbService {

    private final TbBespokeresLsbMapper tbBespokeresLsbMapper;

    @Override
    public List<TbBespokeresLsb> selectUserID(String UserID,int PartID) {
        return tbBespokeresLsbMapper.selectUserID(UserID,PartID);
    }

    @Override
    public List<TbBespokeresLsb> selectByPartId(List<Integer> partIds) {
        return tbBespokeresLsbMapper.selectByPartId(partIds);
    }

    @Override
    public Integer daleteUserID(int ID) {
        return tbBespokeresLsbMapper.daleteUserID(ID);
    }

    @Override
    public Integer deletePartId(int partId) {
        return tbBespokeresLsbMapper.deletePartId(partId);
    }

    /**
     * @param tbBespokeresLsb 号源临时表
     * @return
     */
    @Override
    public Integer inserTbespokeresLsb(TbBespokeresLsb tbBespokeresLsb) {
        return tbBespokeresLsbMapper.inserTbespokeresLsb(tbBespokeresLsb);
    }
}
