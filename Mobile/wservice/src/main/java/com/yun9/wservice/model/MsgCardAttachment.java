package com.yun9.wservice.model;

public class MsgCardAttachment implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String msgcardid;

    private String fileid;

    private String filetype;

    public MsgCardAttachment() {
    }

    public MsgCardAttachment(String id, String msgcardid, String fileid, String filetype) {
        this.id = id;
        this.msgcardid = msgcardid;
        this.fileid = fileid;
        this.filetype = filetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getMsgcardid() {
        return msgcardid;
    }

    public void setMsgcardid(String msgcardid) {
        this.msgcardid = msgcardid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }
}
