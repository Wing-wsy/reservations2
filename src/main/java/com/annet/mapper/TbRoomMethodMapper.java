package com.annet.mapper;

import com.annet.entity.TbRoomMethod;
import com.annet.vo.TbRoomMethodVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * 房间支持检查方法表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbRoomMethodMapper extends BaseMapper<TbRoomMethod> {

    /**
     * 查看列表
     * @return
     */
    List<TbRoomMethod> selectFinAll(@Param("hospitalName")String hospitalName);
    List<TbRoomMethod> selectFinAllNew(TbRoomMethodVo tbRoomMethodVo);

    /**
     * @param hospitalName 院区名称
     * @param deptName 科室名称
     * @return 检查房间方法列表
     */
    List<TbRoomMethod> selectFindAll(@Param("hospitalName")String hospitalName,
                                     @Param("deptName")String deptName);
    List<TbRoomMethod> selectFindAllNew(TbRoomMethodVo tbRoomMethodVo);
    /**
     * 查询重复
     * @param tbRoomMethod
     * @return
     */
    List<TbRoomMethod> selectCondition(TbRoomMethod tbRoomMethod);

    /**
     * 添加
     * @param tbRoomMethod
     * @return
     */
    Integer insertTbRoomMethod(TbRoomMethod tbRoomMethod);

    /**
     * 修改
     * @param tbRoomMethod
     * @return
     */
    Integer updateTbRoomMethod(TbRoomMethod tbRoomMethod);

    /**
     * 删除
     * @param ID id
     * @return 操作码
     */
    Integer deleteTbRoomMethod(@Param("ID") Integer ID);
}
