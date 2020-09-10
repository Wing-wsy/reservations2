package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbStudyroom;
import com.annet.mapper.TbStudyroomMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbStudyroomService;
import com.annet.utils.StrUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 * 检查房间
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbStudyroomServiceImpl extends ServiceImpl<TbStudyroomMapper, TbStudyroom> implements TbStudyroomService {

    private final TbStudyroomMapper tbStudyroomMapper;

    /**
     * 检查房间列表
     * @param hospitalName 院区名称
     * @return 检查房间列表
     */
    @Override
    public R<List<TbStudyroom>> finAll(String hospitalName,String deptName) {
        if(StrUtils.isNullOrEmpty(hospitalName)){
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        List<TbStudyroom> tbStudyrooms = tbStudyroomMapper.selectfinAll(hospitalName);
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            return new R(tbStudyrooms);
        }else {
            return new R(tbStudyrooms.stream().filter(p->p.getDeptName().equals(deptName)).collect(Collectors.toList()));
        }
    }

    /**
     * 添加检查房间
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    @Override
    public R<Integer> addStudyroom(TbStudyroom tbStudyroom) {
        if(StrUtils.isNullOrEmpty(tbStudyroom.getHospitalName())){
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        //状态默认为1
        List<TbStudyroom> tbStudyroomList = tbStudyroomMapper.selectRepeat(tbStudyroom);
        if(tbStudyroomList!=null && tbStudyroomList.size()>0){
            //throw new MyException("该房间记录已存在");
            return new R(1,"该房间记录已存在");
        }
        tbStudyroom.setStatus(1);
        Integer result = tbStudyroomMapper.insertTbStudyroom(tbStudyroom);
        if(result < 1){
            //throw new MyException("添加检查房间失败");
            return new R(1,"添加检查房间失败");
        }
        return new R(tbStudyroom.getRoomID());
    }

    /**
     * 修改检查房间
     * @param tbStudyroom 检查房间
     * @return 检查房间集合
     */
    @Override
    public R<Integer> modifyStudyroom(TbStudyroom tbStudyroom) {
        if(StrUtils.isNullOrEmpty(tbStudyroom.getHospitalName())){
            //throw new MyException("院区参数必传不能为空");
            return new R(1,"院区参数必传不能为空");
        }
        List<TbStudyroom> tbStudyroomList = tbStudyroomMapper.selectRepeat(tbStudyroom);
        if(tbStudyroomList!=null && tbStudyroomList.size()>0){
            //throw new MyException("房间记录重复修改失败");
            return new R(1,"房间记录重复修改失败");
        }
        Integer result = tbStudyroomMapper.updateTbStudyroom(tbStudyroom);
        if(result < 1){
            //throw new MyException("修改检查房间失败");
            return new R(1,"修改检查房间失败");
        }
        return new R(tbStudyroom.getRoomID());
    }

    /**
     * 停诊，恢复
     * @param id id
     * @param status 状态值
     * @return 成功标志码
     */
    @Override
    public R<Integer> stopRestore(Integer id, Integer status) {
        TbStudyroom tbStudyroom = new TbStudyroom();
        tbStudyroom.setRoomID(id);
        tbStudyroom.setStatus(status);
        Integer result = tbStudyroomMapper.updateTbStudyroom(tbStudyroom);
        if(result < 1){
            //throw new MyException("停诊恢复失败");
            return new R(1,"停诊恢复失败");
        }
        return new R(tbStudyroom.getRoomID());
    }

    /**
     * 获取去重的设备类型
     * @return 设备集合
     */
    @Override
    public List<TbStudyroom> selectGroupByModality(String hospitalName,String deptName) {
        // 根据院区和登录权限筛选
        if(deptName.equals(ConstantClass.ADMINISTRATOR)){
            List<TbStudyroom> tbStudyrooms = tbStudyroomMapper.selectGroupByModalityAndDeptName(hospitalName);
            System.out.println(tbStudyrooms.size());
            return tbStudyrooms;
        }else {
            List<TbStudyroom> studyrooms = tbStudyroomMapper.selectGroupByModalityAndDeptName(hospitalName).stream().filter(p -> p.getDeptName().contains(deptName)).collect(Collectors.toList());
            System.out.println(studyrooms.size());
            return studyrooms;
        }
    }
}
