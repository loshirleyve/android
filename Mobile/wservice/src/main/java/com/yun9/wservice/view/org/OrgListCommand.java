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

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
