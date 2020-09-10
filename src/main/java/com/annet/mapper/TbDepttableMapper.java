package com.annet.mapper;

import com.annet.entity.TbDepttable;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *科室表
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbDepttableMapper extends BaseMapper<TbDepttable> {
    /**
     * 科室列表
     * @return
     */
    List<TbDepttable> selectFinAll(@Param("HospitalName") String HospitalName,@Param("histype") String histype,@Param("IsReserve") Integer IsReserve);

    /**
     * 科室代码查重
     * @param DeptCode
     * @return
     */
    List<TbDepttable> selectDeptCode(@Param("DeptCode") String DeptCode,@Param("DeptID") Integer DeptID);

    /**
     * 科室名称查重
     * @param DeptName
     * @return
     */
    List<TbDepttable> selectDeptName(@Param("DeptName") String DeptName,@Param("DeptID") Integer DeptID);

    /**
     * 添加科室
     * @param tbDepttable
     * @return
     */
    Integer addTbDepttable(TbDepttable tbDepttable);

    Integer addTable(TbDepttable tbDepttable);

    /**
     * 修改科室
     * @param tbDepttable
     * @return
     */
    Integer updateTbDepttable(TbDepttable tbDepttable);

    Integer updateTable(TbDepttable tbDepttable);

    Integer deleteTbDeptTableByDeptId(@Param("DeptID") Integer DeptID);

    List<TbDepttable> selectDeptCodeAndHospitalName(@Param("deptCode") String deptCode ,@Param("hospitalName") String hospitalName);
}
