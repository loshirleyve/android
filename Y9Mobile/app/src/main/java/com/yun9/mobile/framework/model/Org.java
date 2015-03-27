package com.yun9.mobile.framework.model;

import java.util.ArrayList;
import java.util.List;

import com.yun9.mobile.framework.util.AssertValue;

/**
 * @ClassName: DefaultOrg
 * @Description:
 * @author leon
 * @date 2014年6月17日 下午6:54:46
 * 
 *       copyright:深圳市顶聚科技有限公司
 */
public class Org implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<Org> children;

	private String id;

	private String demid;

	private String no;

	private String name;

	private String desc;

	private String parentid;

	private String type;

	private int sort;

	private int primary;

	private long createdata;

	private String createby;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDemid() {
		return demid;
	}

	public void setDemid(String demid) {
		this.demid = demid;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getPrimary() {
		return primary;
	}

	public void setPrimary(int primary) {
		this.primary = primary;
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

	public List<Org> getChildren() {
		return children;
	}

	public void setChildren(List<Org> children) {
		this.children = children;
	}

	public void pubChildOrg(Org org) {
		if (!AssertValue.isNotNull(this.children)) {
			this.children = new ArrayList<Org>();
		}
		this.children.add(org);
	}
}
