package com.annet.mapper;

import com.annet.entity.TbStudymethod;
import com.annet.vo.TbStudymethodVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * 检查方法
 * @author XiaYu
 * @since 2019-04-28
 */
public interface TbStudymethodMapper extends BaseMapper<TbStudymethod> {

    /**
     * 检查方法列表
     * @param hospitalName 院区名称
     * @return 检查方法集合
     */
    List<TbStudymethod> selectfinAll(@Param("hospitalName") String hospitalName);

    List<TbStudymethod> selectfinAllNew(TbStudymethodVo tbStudymethodVo);

    /**
     * 设备，检查方法判断重复
     * @param tbStudymethod 检查方法
     * @return 检查方法
     */
    List<TbStudymethod> selectCondition(TbStudymethod tbStudymethod);

    /**
     * 添加检查方法
     * @param tbStudymethod 检查方法
     * @return 操作码
     */
    Integer insertTbStudymethod(TbStudymethod tbStudymethod);

    /**
     * 修改检查方法
     * @param tbStudymethod 检查方法
     * @return 操作返回码
     */
    Integer updateTbStudymethod(TbStudymethod tbStudymethod);

    List<TbStudymethod> selectFindAll(@Param("hospitalName") String hospitalName,@Param("deptName") String deptName);

    List<TbStudymethod> selectFindAllNew(TbStudymethodVo tbStudymethodVo);
    /**
     * @param MethodID 检查方法id
     * @return 操作是否成功的操作码
     */
    Integer deleteStudymethod(@Param("MethodID") Integer MethodID);
}
