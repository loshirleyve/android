package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by li on 2015/7/13.
 */
public class MdInstScales implements java.io.Serializable {
    private List<MdInstScale> bizMdInstScales;

    public List<MdInstScale> getBizMdInstScales() {
        return bizMdInstScales;
    }

    public MdInstScales setBizMdInstScales(List<MdInstScale> bizMdInstScales) {
        this.bizMdInstScales = bizMdInstScales;
        return this;
    }
}
