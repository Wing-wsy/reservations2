package com.annet.entity.operating;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 获取移动端取消或变更预约操作记录
 * @author
 */
public class ConfigOperate {

    private Integer ID;
    /**
     * 操作类型 1：微信端取消预约 2：微信端变更预约
     */
    private Integer OperateType;
    /**
     * 预约流水号，tb_bespoke主键
     */
    private Integer BespokeID;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date OperateDate;
    /**
     * 配置项名称
     */
    private String ConfigKey;
    /**
     * 配置项值
     */
    private String ConfigValue;
    /**
     * 配置项说明
     */
    private String Remarks;

    public Integer getID() { return ID; }

    public void setID(Integer ID) { this.ID = ID; }

    public Integer getOperateType() { return OperateType; }

    public void setOperateType(Integer operateType) { OperateType = operateType; }

    public Integer getBespokeID() { return BespokeID; }

    public void setBespokeID(Integer bespokeID) { BespokeID = bespokeID; }

    public Date getOperateDate() { return OperateDate; }

    public void setOperateDate(Date operateDate) { OperateDate = operateDate; }

    public String getConfigKey() { return ConfigKey; }

    public void setConfigKey(String configKey) { ConfigKey = configKey; }

    public String getConfigValue() { return ConfigValue; }

    public void setConfigValue(String configValue) { ConfigValue = configValue; }

    public String getRemarks() { return Remarks; }

    public void setRemarks(String remarks) { Remarks = remarks; }
}
