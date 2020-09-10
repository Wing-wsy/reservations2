package com.annet.service;

import com.annet.entity.TbDepttable;
import com.annet.result.R;
import com.annet.xmlEntity.RequestDeptInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 科室表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbDepttableService extends IService<TbDepttable> {

    /**
     * 科室列表
     *
     * @return 科室列表集合
     */
    R<List<TbDepttable>> FinAll(String deptName,String hospitalName,String histype,Integer isReserve);

    /**
     * 添加科室
     *
     * @param tbDepttable 科室对象
     * @return 科室集合
     */
    R<Integer> addTbDepttable(TbDepttable tbDepttable);

    /**
     * 修改科室
     * @param tbDepttable
     * @return
     */
    R<Integer> modifyTbDepttable(TbDepttable tbDepttable);

    /**
     * @return 筛选科室列表
     */
    List<TbDepttable> filterDepttable();

    R<Integer> delete(Integer deptId);

    /**
     * @return 同步科室信息
     */
    R<List<RequestDeptInfo>> synTbDepttable(String hospitalName);

    /**
     * 科室禁用启用
     * @param id
     * @param status
     * @return
    Integer stopRestore(Integer id,Integer status);*/
}
