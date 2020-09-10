package com.annet.xmlEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@XStreamAlias("lists")
/**
 * @Author
 */
public class GetRequestInfoList {

    @XStreamImplicit(itemFieldName = "list")
    private List<GetRequestInfo> getRequestInfoList;

    public List<GetRequestInfo> getGetRequestInfoList() {
        return getRequestInfoList;
    }

    public void setGetRequestInfoList(List<GetRequestInfo> getRequestInfoList) {
        this.getRequestInfoList = getRequestInfoList;
    }
}
