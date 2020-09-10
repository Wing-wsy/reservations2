package com.annet.xmlEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XStreamAlias("list")
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
 * GetRequestInfo方法返回的XML对应的实体类
 * 根元素
 * @author
 */
public class RequestDeptInfo {

    @XStreamAlias("HISTYPE")
    private String histype;
    @XStreamAlias("REQUESTDEPTPYM")
    private String requestDeptPYM;
    @XStreamAlias("REQUESTDEPTCODE")
    private String requestDeptCode;
    @XStreamAlias("REQUESTDEPTNAME")
    private String requestDeptName;

    public String getRequestDeptCode() { return requestDeptCode; }

    public void setRequestDeptCode(String requestDeptCode) { this.requestDeptCode = requestDeptCode; }

    public String getRequestDeptName() { return requestDeptName; }

    public void setRequestDeptName(String requestDeptName) { this.requestDeptName = requestDeptName; }

    public String getHistype() { return histype; }

    public void setHistype(String histype) { this.histype = histype; }

    public String getRequestDeptPYM() { return requestDeptPYM; }

    public void setRequestDeptPYM(String requestDeptPYM) { this.requestDeptPYM = requestDeptPYM; }
}
