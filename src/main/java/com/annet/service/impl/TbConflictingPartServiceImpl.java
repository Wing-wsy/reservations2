package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.Dto.TbConflictingPartDto;
import com.annet.entity.TbConflictingPart;
import com.annet.mapper.TbConflictingPartMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbConflictingPartService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * 检查项目互斥表
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbConflictingPartServiceImpl extends ServiceImpl<TbConflictingPartMapper, TbConflictingPart> implements TbConflictingPartService {

    private final TbConflictingPartMapper tbConflictingPartMapper;

    /**
     * 获取列表
     *
     * @return 检查项目互斥表列表
     */
    @Override
    public List<TbConflictingPartDto> FinAll(String hospitalName,String deptName) {
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return tbConflictingPartMapper.selectFindAllDto(hospitalName,null);
        }else {
            return tbConflictingPartMapper.selectFindAllDto(hospitalName, deptName);
        }
    }

    /**
     * 添加
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    @Override
    public R<Integer> AddTbConflictingPart(TbConflictingPart tbConflictingPart) {
        if(!tbConflictingPart.getModality().equals("") && !tbConflictingPart.getPartID().equals("") && !tbConflictingPart.getConflictingPartID().equals("")){
            List<TbConflictingPart> tbConflictingPartList = tbConflictingPartMapper.selectRepeat(tbConflictingPart);
            if(tbConflictingPartList.size()>0){
                //throw new MyException("检查项目互斥条件重复,无法添加");
                return new R(1,"检查项目互斥条件重复,无法添加");
            }
        }
        Integer result = tbConflictingPartMapper.insertTbConflictingPart(tbConflictingPart);
        if(result < 1){
            //throw new MyException("添加检查项目互斥失败");
            return new R(1,"添加检查项目互斥失败");
        }
        return new R(tbConflictingPart.getId());
    }

    /**
     * 修改
     *
     * @param tbConflictingPart 项目互斥对象
     * @return 项目互斥对象集合
     */
    @Override
    public R<Integer> ModifyTbConflictingPart(TbConflictingPart tbConflictingPart) {
        if(!tbConflictingPart.getModality().equals("") && !tbConflictingPart.getPartID().equals("") && !tbConflictingPart.getConflictingPartID().equals("")){
            List<TbConflictingPart> tbConflictingPartList = tbConflictingPartMapper.selectRepeat(tbConflictingPart);
            if(tbConflictingPartList.size()>0){
                //throw new MyException("检查项目互斥条件重复，无法修改");
                return new R(1,"检查项目互斥条件重复，无法修改");
            }
        }
        Integer result = tbConflictingPartMapper.updateTbConflictingPart(tbConflictingPart);
        if(result < 1){
            //throw new MyException("修改检查项目互斥失败");
            return new R(1,"修改检查项目互斥失败");
        }
        return new R(tbConflictingPart.getId());
    }

    /**
     * 删除
     *
     * @param ID 检查互斥编号id
     * @return 返回码
     */
    @Override
    public R<Integer> DeleteTbConflictingPart(Integer ID) {
        Integer result = tbConflictingPartMapper.deleteTbConflictingPart(ID);
        if(result < 1){
            //throw new MyException("删除检查项目互斥失败");
            return new R(1,"删除检查项目互斥失败");
        }
        return new R(result);
    }
}
