package com.annet.xmlEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("RequestDeptList")
/**
 * @Author
 */
public class RequestDeptInfoList {

    @XStreamImplicit(itemFieldName = "RequestDeptInfo")
    private List<RequestDeptInfo> requestDeptInfoList;

    public List<RequestDeptInfo> getRequestDeptInfoList() {
        return requestDeptInfoList;
    }

    public void setRequestDeptInfoList(List<RequestDeptInfo> requestDeptInfoList) {
        this.requestDeptInfoList = requestDeptInfoList;
    }
}
