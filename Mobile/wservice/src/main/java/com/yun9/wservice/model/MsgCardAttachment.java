package com.yun9.wservice.model;

public class MsgCardAttachment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String msgcardid;

	private String fileid;

	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
