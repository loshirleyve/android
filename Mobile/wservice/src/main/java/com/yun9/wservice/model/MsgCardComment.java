package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * 消息卡片评论
 * @author yun9
 *
 */
public class MsgCardComment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public class Type {
		public static final String NORMAL = "normal";
		public static final String DICE = "dice";
	}

    private String inform;
	private String id;
	private String msgcardid;	// 消息卡片ID
	private String from;	// 来自谁的评论
	private String content; 	//评论内容
	private String type; 	//	类型 normal,dice
	private String devicetype;	//设备类型
	private String devicename;	//设备名称
	private String locationx;
	private String locationy;
	private String locationlabel;
	private String locationscale;
	// 创建人
	private String createby;
	// 更新人
	private String updateby;
	// 创建时间
	private Long createdate;
	// 更新时间
	private Long updatedate;

    public String getInform() {
        return inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getLocationx() {
		return locationx;
	}
	public void setLocationx(String locationx) {
		this.locationx = locationx;
	}
	public String getLocationy() {
		return locationy;
	}
	public void setLocationy(String locationy) {
		this.locationy = locationy;
	}
	public String getLocationlabel() {
		return locationlabel;
	}
	public void setLocationlabel(String locationlabel) {
		this.locationlabel = locationlabel;
	}
	public String getLocationscale() {
		return locationscale;
	}
	public void setLocationscale(String locationscale) {
		this.locationscale = locationscale;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	public Long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}
	public Long getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Long updatedate) {
		this.updatedate = updatedate;
	}
	public String getMsgcardid() {
		return msgcardid;
	}
	public void setMsgcardid(String msgcardid) {
		this.msgcardid = msgcardid;
	}
	
	public MsgCardComment(String content)
	{
		this.content=content;
	}

	public MsgCardComment(){}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
