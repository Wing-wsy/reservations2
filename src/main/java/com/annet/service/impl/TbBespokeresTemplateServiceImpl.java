package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbBespokeresTemplate;
import com.annet.mapper.TbBespokeresTemplateMapper;
import com.annet.result.ConstantClass;
import com.annet.result.R;
import com.annet.service.TbBespokeresTemplateService;
import com.annet.utils.StrUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 * 预约号源模板
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@Service
@AllArgsConstructor
public class TbBespokeresTemplateServiceImpl extends ServiceImpl<TbBespokeresTemplateMapper, TbBespokeresTemplate> implements TbBespokeresTemplateService {

    private final TbBespokeresTemplateMapper tbBespokeresTemplateMapper;

    @Override
    public R<List<TbBespokeresTemplate>> finAll(TbBespokeresTemplate tbBespokeresTemplate) {
        if (StrUtils.isNullOrEmpty(tbBespokeresTemplate.getHospitalName())) {
            //throw new MyException("院区名称不能为空");
            return new R(1,"院区名称不能为空");
        }
        if(tbBespokeresTemplate.getDeptName().equals(ConstantClass.ADMINISTRATOR)){
            List<TbBespokeresTemplate> tbBespokeresTemplates = tbBespokeresTemplateMapper.selectFinAll(tbBespokeresTemplate);
            return new R(tbBespokeresTemplates);
        }else {
            return new R(tbBespokeresTemplateMapper.selectFindAll(tbBespokeresTemplate));
        }
    }

    /**
     * 添加
     *
     * @param tbBespokeresTemplate 模板对象
     * @return 操作成功码
     */
    @Override
    public R<Integer> addBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate) {
        if (StrUtils.isNullOrEmpty(tbBespokeresTemplate.getHospitalName())) {
            //throw new MyException("院区名称不能为空");
            return new R(1,null,"院区名称不能为空");
        }
        List<TbBespokeresTemplate> tbBespokeresTemplates = tbBespokeresTemplateMapper.selectCondition(tbBespokeresTemplate);
        if (tbBespokeresTemplates != null && tbBespokeresTemplates.size() > 0) {
            //throw new MyException("号源条件重复，添加预约号源模板失败");
            return new R(1,null,"号源条件重复，添加预约号源模板失败");
        }
        Integer result = tbBespokeresTemplateMapper.insertBespokeresTemplate(tbBespokeresTemplate);
        if (result < 1) {
            //throw new MyException("添加号源模板失败");
            return new R(1,"添加号源模板失败");
        }
        return new R(tbBespokeresTemplate.getId());
    }

    /**
     * 修改
     *
     * @param tbBespokeresTemplate 对象信息
     * @return 修改成功码
     */
    @Override
    public R<Integer> modifyBespokeresTemplate(TbBespokeresTemplate tbBespokeresTemplate) {
        List<TbBespokeresTemplate> tbBespokeresTemplates = tbBespokeresTemplateMapper.selectCondition(tbBespokeresTemplate);
        if (tbBespokeresTemplates != null && tbBespokeresTemplates.size() > 0) {
            //throw new MyException("号源条件重复，修改预约号源模板失败");
            return new R(1,"号源条件重复，修改预约号源模板失败");
        }
        Integer result = tbBespokeresTemplateMapper.updateBespokeresTemplate(tbBespokeresTemplate);
        if (result < 1) {
            //throw new MyException("修改预约号源模板失败");
            return new R(1,"修改预约号源模板失败");
        }
        return new R(tbBespokeresTemplate.getId());
    }

    /**
     * 删除
     *
     * @param id 模板id
     * @return 删除操作码
     */
    @Override
    public R<Integer> deleteBespokeresTemplate(Integer id) {
        Integer result = tbBespokeresTemplateMapper.deleteBespokeresTemplate(id);
        if (result < 1) {
            //throw new MyException("删除预约号源失败");
            return new R(1,"删除预约号源失败");
        }
        return new R(result);
    }

    /**
     * 设备预约排班时间集合
     * @param hospitalName  院区
     * @param modalities 设备
     * @return 设备排班时间集合
     */
    @Override
    public List<TbBespokeresTemplate> findTimeByHospitalNameAndModality(String hospitalName, String modalities) {
        List<String> modalityList = Arrays.asList(modalities.split(",,")).stream().distinct().collect(Collectors.toList());
        List<TbBespokeresTemplate> bespokeresTemplates = tbBespokeresTemplateMapper.findTimeByHospitalNameAndModality(hospitalName, modalityList);
        List<TbBespokeresTemplate> templates = bespokeresTemplates.stream().distinct().collect(Collectors.toList());
        return templates;
    }
}
