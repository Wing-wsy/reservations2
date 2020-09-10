package com.annet.xmlEntity;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@XStreamAlias("QueueInfo")
/**
 * 自动为所有字段添加@ToString, @EqualsAndHashCode, @Getter方法，为非final字段添加@Setter,和@RequiredArgsConstructor
 */
@Data
/**
 * 自动生成全参数构造函数
 */
@AllArgsConstructor
/**
 * 自动生成无参数构造函数
 */
@NoArgsConstructor
/**
 * @Author: jh
 * @Date: 2019/12/10 15:11
 * @Version: 1.0
 * @Description: 自助报到的xml
 */
public class QueueInfo {
    // RIS检查号
    @XStreamAlias("RISStudyNO")
    private String risStudyNo;
    // RIS检查唯一号
    @XStreamAlias("RISStudyIdentity")
    private String risStudyIdentity;
    // RIS排队号
    @XStreamAlias("RISQueueNO")
    private String risQueueNo;
    // 等待人数
    @XStreamAlias("RISWaitNum")
    private String risWaitNum;
}
