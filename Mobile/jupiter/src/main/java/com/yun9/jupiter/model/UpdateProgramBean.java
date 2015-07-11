package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/7/8.
 */
public class UpdateProgramBean implements java.io.Serializable {
    private String id;
    private long activatedatetime;
    private String os;
    private String type;
    private int focus;
    private String log;
    private String version;
    private int versioncode;
    private String url;
    private int disabled;
    private String createby;
    private long createdate;
    private String updateby;
    private long updatedate;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getActivatedatetime() {
        return activatedatetime;
    }

    public void setActivatedatetime(long activatedatetime) {
        this.activatedatetime = activatedatetime;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(long updatedate) {
        this.updatedate = updatedate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
