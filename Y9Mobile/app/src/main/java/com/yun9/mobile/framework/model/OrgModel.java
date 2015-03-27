package com.yun9.mobile.framework.model;

import com.yun9.mobile.treenode.bean.TreeNodeId;
import com.yun9.mobile.treenode.bean.TreeNodeLabel;
import com.yun9.mobile.treenode.bean.TreeNodePid;

/**
 * @ClassName: DefaultOrg
 * @Description:为了对org组织进行排序才创建的一个类
 * @author leon
 * @date 2014年6月17日 下午6:54:46
 * 
 *       copyright:深圳市顶聚科技有限公司
 */
public class OrgModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@TreeNodeId
	private long id;
	@TreeNodePid
	private long parentid;
	@TreeNodeLabel
	private String name;
	
	private Org org;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	
	public OrgModel(long id,long parentid,String name)
	{
		this.id=id;
		this.parentid=parentid;
		this.name=name;
	}
	
}
