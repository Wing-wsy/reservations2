package com.annet.service;

import com.annet.entity.Dto.TbConflictingPartDto;
import com.annet.entity.TbConflictingPart;
import com.annet.result.R;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 检查项目互斥表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbConflictingPartService extends IService<TbConflictingPart> {

    /**
     * 获取列表
     *
     * @return 检查项目互斥表列表
     */
    List<TbConflictingPartDto> FinAll(String hospitalName,String deptName);

    /**
     * 添加
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    R<Integer> AddTbConflictingPart(TbConflictingPart tbConflictingPart);

    /**
     * 修改
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    R<Integer> ModifyTbConflictingPart(TbConflictingPart tbConflictingPart);

    /**
     * 删除
     *
     * @param ID 检查互斥编号id
     * @return 返回码
     */
    R<Integer> DeleteTbConflictingPart(Integer ID);
}
