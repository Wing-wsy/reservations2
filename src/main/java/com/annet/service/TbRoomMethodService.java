package com.annet.service;

import com.annet.entity.TbRoomMethod;
import com.annet.result.R;
import com.annet.vo.TbRoomMethodVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 房间支持检查方法表
 *
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbRoomMethodService extends IService<TbRoomMethod> {

    /**
     * @param hospitalName 院区名称
     * @param deptName 科室名称
     * @return 检查房间列表
     */
    List<TbRoomMethod> finAll(String hospitalName,String deptName);

    List<TbRoomMethod> finAllNew(TbRoomMethodVo tbRoomMethodVo);

    /**
     * 添加
     *
     * @param tbRoomMethod 检查房间
     * @return 检查房间对象
     */
    R<Integer> addRoomMethod(TbRoomMethod tbRoomMethod);

    /**
     * 修改
     *
     * @param tbRoomMethod 修改信息
     * @return 房间信息对象
     */
    R<Integer> modifyRoomMethod(TbRoomMethod tbRoomMethod);

    /**
     * 删除
     *
     * @param id 房间id
     * @return 删除结果
     */
    R<Integer> deleteRoomMethod(Integer id);
}
