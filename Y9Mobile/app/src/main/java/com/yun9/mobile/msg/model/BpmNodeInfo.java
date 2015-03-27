package com.yun9.mobile.msg.model;

import java.io.Serializable;

/**
 * BPM节点信息
 * @author yun9
 *
 */
public class BpmNodeInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String createOrgId;
	private String taskId;
	private String instId;
	private String nodeId;
	private String nodeName;
	private String mcId;	// 消息卡片ID
	private String id;
	
	public String getCreateOrgId() {
		return createOrgId;
	}
	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getMcId() {
		return mcId;
	}
	public void setMcId(String mcId) {
		this.mcId = mcId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
