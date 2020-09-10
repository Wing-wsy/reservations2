package com.annet.service;

import com.annet.entity.TbBespokeresLsb;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XiaYu
 * @since 2019-08-01
 */
public interface TbBespokeresLsbService extends IService<TbBespokeresLsb> {

    /**
     * 通过医生ID查询
     * @param UserID
     * @return
     */
    List<TbBespokeresLsb> selectUserID(String UserID,int PartID);

    List<TbBespokeresLsb> selectByPartId(List<Integer> partIds);

    /**
     * 通过ID删除
     * @param ID
     * @return
     */
    Integer daleteUserID(int ID);

    /**
     * 通过ID删除
     * @param partId 部位id
     * @return 执行操作码
     */
    Integer deletePartId(int partId);

    /**
     * 添加
     * @param tbBespokeresLsb 号源临时表
     * @return 执行操作码
     */
    Integer inserTbespokeresLsb(TbBespokeresLsb tbBespokeresLsb);

}
