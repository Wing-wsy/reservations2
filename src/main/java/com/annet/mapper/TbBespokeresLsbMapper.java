package com.annet.mapper;

import com.annet.entity.TbBespokeresLsb;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XiaYu
 * @since 2019-08-01
 */
public interface TbBespokeresLsbMapper extends BaseMapper<TbBespokeresLsb> {

    /**
     * 通过医生ID查询
     * @param UserID
     * @return
     */
    List<TbBespokeresLsb> selectUserID(@Param("UserID")String UserID,@Param("PartID")int PartID);

    List<TbBespokeresLsb> selectByPartId(@Param("partIds")List<Integer> partIds);

    /**
     * 通过ID删除
     * @param ID
     * @return
     */
    int daleteUserID(@Param("ID")int ID);

    /**
     * 通过ID删除
     * @param partId
     * @return
     */
    Integer deletePartId(@Param("partId")int partId);

    /**
     * 添加
     * @param tbBespokeresLsb
     * @return
     */
    Integer inserTbespokeresLsb(TbBespokeresLsb tbBespokeresLsb);
}
