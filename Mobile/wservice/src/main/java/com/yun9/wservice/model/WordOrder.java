package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rxy on 15/8/27.
 */
public class WordOrder implements Serializable {

    private String id;
    private String no;
    private String instid;
    private String sn;
    private String name;
    private String workorderid;
    private String state;
    private String model;
    private String source;
    private long processdate;
    private String sourcevalue;
    private String assignedid;
    private String processid;
    private long assigneddate;
    private long expirydate;
    private long completedate;
    private int readstate;
    private int expriyday;
    private long createdate;
    private String clientinstid;
    private String duty;
    private List<WorkOrderComment> workorderCommentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getWorkorderid() {
        return workorderid;
    }

    public void setWorkorderid(String workorderid) {
        this.workorderid = workorderid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getProcessdate() {
        return processdate;
    }

    public void setProcessdate(long processdate) {
        this.processdate = processdate;
    }

    public String getSourcevalue() {
        return sourcevalue;
    }

    public void setSourcevalue(String sourcevalue) {
        this.sourcevalue = sourcevalue;
    }

    public String getAssignedid() {
        return assignedid;
    }

    public void setAssignedid(String assignedid) {
        this.assignedid = assignedid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public long getAssigneddate() {
        return assigneddate;
    }

    public void setAssigneddate(long assigneddate) {
        this.assigneddate = assigneddate;
    }

    public long getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(long expirydate) {
        this.expirydate = expirydate;
    }

    public long getCompletedate() {
        return completedate;
    }

    public void setCompletedate(long completedate) {
        this.completedate = completedate;
    }

    public int getReadstate() {
        return readstate;
    }

    public void setReadstate(int readstate) {
        this.readstate = readstate;
    }

    public int getExpriyday() {
        return expriyday;
    }

    public void setExpriyday(int expriyday) {
        this.expriyday = expriyday;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public String getClientinstid() {
        return clientinstid;
    }

    public void setClientinstid(String clientinstid) {
        this.clientinstid = clientinstid;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }


    public List<WorkOrderComment> getWorkorderCommentList() {
        return workorderCommentList;
    }

    public void setWorkorderCommentList(List<WorkOrderComment> workorderCommentList) {
        this.workorderCommentList = workorderCommentList;
    }
}
