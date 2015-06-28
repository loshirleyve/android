package com.yun9.wservice.model;

public class MsgCardAttachment implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String msgCardId;

    private String fileId;

    private String fileType;

    private String desc;

    private String createby;

    private long createdate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsgCardId() {
        return msgCardId;
    }

    public void setMsgCardId(String msgCardId) {
        this.msgCardId = msgCardId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
