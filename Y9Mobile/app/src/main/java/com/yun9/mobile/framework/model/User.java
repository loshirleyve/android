package com.yun9.mobile.framework.model;


public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String no;
	private String sex;
	private String idcard;
	private long birthday;
	private String state;
	private String onlinestate;
	private long registerdate;
	private String headerfileid;;
	private String signature;
	private long createdata;
	private String createby;
	private String remark;
	

	

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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

	public String getOnlinestate() {
		return onlinestate;
	}

	public void setOnlinestate(String onlinestate) {
		this.onlinestate = onlinestate;
	}

	public long getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(long registerdate) {
		this.registerdate = registerdate;
	}

	public String getHeaderfileid() {
		return headerfileid;
	}

	public void setHeaderfileid(String headerfileid) {
		this.headerfileid = headerfileid;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public long getCreatedata() {
		return createdata;
	}

	public void setCreatedata(long createdata) {
		this.createdata = createdata;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
