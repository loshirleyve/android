package com.yun9.wservice.view.org;

/**
 * Created by Leon on 15/5/29.
 */
public class OrgListCommand implements java.io.Serializable {
    private String dimType;

    private String title;

    public String getDimType() {
        return dimType;
    }

    public OrgListCommand setDimType(String dimType) {
        this.dimType = dimType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OrgListCommand setTitle(String title) {
        this.title = title;
        return this;
    }
}
