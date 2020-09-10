package com.annet.service;

import com.annet.entity.TbStudyroom;
import com.annet.result.R;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 检查房间
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbStudyroomService extends IService<TbStudyroom> {

    /**
     * 检查房间列表
     * @param hospitalName 院区名称
     * @return 检查房间列表
     */
    R<List<TbStudyroom>> finAll(String hospitalName, String deptName);

    /**
     * 添加检查房间
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    R<Integer> addStudyroom(TbStudyroom tbStudyroom);

    /**
     * 修改检查房间
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    R<Integer> modifyStudyroom(TbStudyroom tbStudyroom);

    /**
     * 停诊，恢复
     * @param id id
     * @param status 状态值
     * @return 成功标志码
     */
    R<Integer> stopRestore(Integer id,Integer status);

    /**
     * 获取去重的设备类型
     * @return 设备集合
     */
    List<TbStudyroom> selectGroupByModality(String hospitalName,String deptName);
}
