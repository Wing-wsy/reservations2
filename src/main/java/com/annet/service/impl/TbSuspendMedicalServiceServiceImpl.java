package com.annet.service.impl;

import com.annet.common.MyException;
import com.annet.entity.TbBespokeres;
import com.annet.entity.TbBespokeresTemplate;
import com.annet.entity.TbSuspendMedicalService;
import com.annet.mapper.TbBespokeresMapper;
import com.annet.mapper.TbBespokeresTemplateMapper;
import com.annet.mapper.TbSuspendMedicalServiceMapper;
import com.annet.result.R;
import com.annet.service.TbSuspendMedicalServiceService;
import com.annet.utils.StrUtils;
import com.annet.utils.TimeUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XiaYu
 * @since 2019-11-04
 */
@Service
@AllArgsConstructor
@Slf4j
public class TbSuspendMedicalServiceServiceImpl extends ServiceImpl<TbSuspendMedicalServiceMapper, TbSuspendMedicalService> implements TbSuspendMedicalServiceService {

    private final TbBespokeresMapper tbBespokeresMapper;

    private final TbSuspendMedicalServiceMapper tbSuspendMedicalServiceMapper;

    private final TbBespokeresTemplateMapper tbBespokeresTemplateMapper;

    /**
     * 号源停诊
     *
     * @param modality      设备类型
     * @param groupType     分组方式
     * @param groupName     分组名称
     * @param stopStartDate 号源停诊开始时间
     * @param stopEndDate   号源停诊结束时间
     * @param hospitalName  院区名称
     * @return 号源集合
     */

    @Override
    @Transactional
    public ArrayList stopTbBespokeres(String modality, Integer groupType, String groupName, String stopStartDate, String stopEndDate, String hospitalName) {
        if (StrUtils.isNullOrEmpty(modality, groupType.toString(), groupName, stopStartDate, stopEndDate, hospitalName)) {
            throw new MyException("设备号,分组方式,分组名称，开始时间，结束时间,院区名称均不能为空");
        }
        ArrayList list = new ArrayList();
        // 得到分组名称集合
       List<String> groupNames = StrUtils.stringSplit(groupName, "&&");
        for (String group : groupNames) {
            TbSuspendMedicalService tbSuspendMedicalService = new TbSuspendMedicalService();
            tbSuspendMedicalService.setId(null);
            tbSuspendMedicalService.setModality(modality);
            tbSuspendMedicalService.setGroupType(groupType);
            tbSuspendMedicalService.setGroupName(group);
            tbSuspendMedicalService.setStartDate(stopStartDate);
            tbSuspendMedicalService.setEndDate(stopEndDate);
            tbSuspendMedicalService.setHospitalName(hospitalName);

            tbSuspendMedicalServiceMapper.addTbSuspendMedicalService(tbSuspendMedicalService);
            list.add(tbSuspendMedicalService);
        }
        // 搜索所需停诊的日期号源是否存在
        /** List<TbBespokeres> tbBespokeres = tbBespokeresMapper.searchBospokerer(groupNames, modality, groupType, stopStartDate, stopEndDate, hospitalName);
        if (tbBespokeres.isEmpty()) {
            throw new MyException("停诊号源不存在");
        }
        // 号源存在，则需要清零号源数
        Set<Integer> idSets = tbBespokeres.stream().map(TbBespokeres::getId).collect(Collectors.toSet());
        Integer code = tbBespokeresMapper.updateTbBespokeresToZero(0, 0, 0, idSets);
        if (code < 1) {
            throw new MyException("清零停诊号源的号源数失败");
        }
        // 将需要停诊的的号源写到号源停诊表
        ArrayList list = new ArrayList();
        for (String group : groupNames) {
            List<TbBespokeres> bespokeres = tbBespokeres.stream().filter(p -> p.getGroupName().contains(group)).collect(Collectors.toList());
            if (bespokeres.isEmpty()) {
                continue;
            }
            TbBespokeres tbBespokere = bespokeres.get(0);
            TbSuspendMedicalService tbSuspendMedicalService = new TbSuspendMedicalService();
            tbSuspendMedicalService.setId(null);
            tbSuspendMedicalService.setModality(tbBespokere.getModality());
            tbSuspendMedicalService.setGroupType(tbBespokere.getGroupType());
            tbSuspendMedicalService.setGroupName(tbBespokere.getGroupName());
            tbSuspendMedicalService.setStartDate(stopStartDate);
            tbSuspendMedicalService.setEndDate(stopEndDate);
            tbSuspendMedicalService.setHospitalName(tbBespokere.getHospitalName());
            // 停诊之前先判断号源是否停诊
            List<TbSuspendMedicalService> tbSuspendMedicalServices = tbSuspendMedicalServiceMapper.selectCondition(modality, groupType, group, stopStartDate, stopEndDate, hospitalName);
            if (!tbSuspendMedicalServices.isEmpty()) {
                //log.info(tbSuspendMedicalService.toString());
                log.info("该记录已停诊");
                continue;
            }
            tbSuspendMedicalServiceMapper.addTbSuspendMedicalService(tbSuspendMedicalService);
            list.add(tbSuspendMedicalService);
        }
        if (list.isEmpty()) {
            throw new MyException("该号源已停诊，请勿重复停诊");
        }*/
        return list;
    }

    /**
     * 预约号源停诊列表
     *
     * @param hospitalName 医院名称
     * @return 预约号源停诊集合
     */
    @Override
    public List<TbSuspendMedicalService> finAllStop(String hospitalName) {
        if (StrUtils.isNullOrEmpty(hospitalName)) {
            throw new MyException("院区名称不能为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        List<TbSuspendMedicalService> tbSuspendMedicalServices = tbSuspendMedicalServiceMapper.selectFinAll(format, hospitalName);
        return tbSuspendMedicalServices;
    }


    /**
     * 删除停诊号源(则需要恢复号源表号源数)
     *
     * @param id           id
     * @param modality     设备
     * @param groupType    检查方式
     * @param groupName    检查名称
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param hospitalName 院区名称
     * @return 操作码
     */
    @Override
    @Transactional
    public R<TbBespokeres> deleteStopBespoke(Integer id, String modality,
                                     Integer groupType, String groupName,
                                     String startDate, String endDate,
                                     String hospitalName) {
        if (StrUtils.isNullOrEmpty(id.toString(), modality, groupType.toString(), groupName, startDate, endDate, hospitalName)) {
            throw new MyException("删除号源对象不能为空");
        }
        // 根据删除对象信息获取号源集合
        /** List<TbBespokeres> tbBespokeres = tbBespokeresMapper.searchBospokererList(
                modality, groupType, groupName, startDate, endDate, hospitalName);
        if (tbBespokeres.isEmpty()) {
            //throw new MyException("原始停诊号源不存在，删除停诊号源失败");
            return new R(1,"","原始停诊号源不存在，删除停诊号源失败");
        }
        // 遍历号源表恢复原始预约号源数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TbBespokeres> bespokeresList = new ArrayList<>();
        for (TbBespokeres tbBespokeres1 : tbBespokeres) {
            String toWeek = TimeUtils.dateToWeek(sdf.format(tbBespokeres1.getBespokeDate()));
            List<TbBespokeresTemplate> bespokeTemplates = tbBespokeresTemplateMapper.findBespokeTemplate(
                    modality,
                    groupType,
                    groupName,
                    toWeek,
                    hospitalName);
            if (bespokeTemplates.isEmpty()) {
                log.info("恢复号源初始值失败");
                continue;
            }
            TbBespokeresTemplate tbBespokeresTemplate = bespokeTemplates.get(0);
            Set<Integer> ids = new HashSet<>();
            ids.add(tbBespokeres1.getId());
            // 恢复号源初始数
            Integer integer = tbBespokeresMapper.updateTbBespokeresToZero(tbBespokeresTemplate.getOutPatientNum(),
                    tbBespokeresTemplate.getInPatientNum(),
                    tbBespokeresTemplate.getTotalNum(), ids);
            if (integer < 1) {
                log.info("恢复失败");
                continue;
            }
            bespokeresList.add(tbBespokeres1);
        }
        if (bespokeresList.isEmpty()) {
            throw new MyException("删除停诊号源失败");
        }*/
        //Integer result = tbSuspendMedicalServiceMapper.deleteById(id);
        tbSuspendMedicalServiceMapper.deleteById(id);
        return new R("","删除成功");
    }


}
