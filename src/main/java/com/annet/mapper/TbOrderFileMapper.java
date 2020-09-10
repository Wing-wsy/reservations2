package com.annet.mapper;

import com.annet.entity.TbOrderFile;
import com.annet.entity.TbRoomMethod;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jh
 * @since 2020-03-17
 */
public interface TbOrderFileMapper extends BaseMapper<TbOrderFile> {

    /**
     * 添加
     * @return
     */
    Integer insertTbOrderFile(TbOrderFile tbOrderFile);

}
