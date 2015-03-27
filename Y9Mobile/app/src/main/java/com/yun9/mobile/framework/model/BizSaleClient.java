package com.yun9.mobile.framework.model;

import java.util.ArrayList;
import java.util.List;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   BizSaleClient
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2015-1-8下午6:10:36
 * 修改人：ruanxiaoyu  
 * 修改时间：2015-1-8下午6:10:36  
 * 修改备注：    
 * @version     
 *     
 */
public class BizSaleClient implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String instid;

	private String sn;

	private String name; 

	private String fullname;
	
	private String level;
	
	private String clientinstid;
	
	private String contactman;
	
	private String contactphone;
	
	private String address;
	
	private String createby;
	
	private String updateby;
	
	private long createdate;
	
	private long updatedate;
	
	private int disabled;
	
	private String remark;

	private String region;

	private String source;

	private String type;

	private String industry;

	private String contactposition;

	private List<BizSaleClientSaleman> saleClientSalemanse = new ArrayList<BizSaleClientSaleman>();

	public List<BizSaleClientSaleman> getSaleClientSalemanse() {
		return saleClientSalemanse;
	}

	public void setSaleClientSalemanse(
			List<BizSaleClientSaleman> saleClientSalemanse) {
		this.saleClientSalemanse = saleClientSalemanse;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getClientinstid() {
		return clientinstid;
	}

	public void setClientinstid(String clientinstid) {
		this.clientinstid = clientinstid;
	}

	public String getContactman() {
		return contactman;
	}

	public void setContactman(String contactman) {
		this.contactman = contactman;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}

	public long getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(long updatedate) {
		this.updatedate = updatedate;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getContactposition() {
		return contactposition;
	}

	public void setContactposition(String contactposition) {
		this.contactposition = contactposition;
	}
	
	
}
