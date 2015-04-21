package com.yun9.wservice.model;

public class SampleUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String sex;
	private String no;
	private String name;
	private String headerfileid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeaderfileid() {
		return headerfileid;
	}

	public void setHeaderfileid(String headerfileid) {
		this.headerfileid = headerfileid;
	}

	public SampleUser(String name)
	{
		this.name=name;
	}
	
	public SampleUser(){}

}