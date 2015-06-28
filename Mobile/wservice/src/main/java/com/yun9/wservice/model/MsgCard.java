package com.yun9.wservice.model;

import android.view.View;

import com.yun9.jupiter.util.AssertValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MsgCard implements java.io.Serializable {

    public static final String PARAMS_MSG_CARD = "msgCard";

    public static class Source {
        public static final String USER = "none";
    }

    private String id;
    // 创建人
    private String createby;
    // 更新人
    private String updateby;
    // 创建时间
    private Long createdate;
    // 更新时间
    private Long updatedate;
    private String remark;
    private String instid;
    private String from;     // 来自谁的评论
    private String subject;
    private String content;      //评论内容
    private String state;
    private String topic;
    private String devicetype;     //设备类型
    private String devicename;     //设备名称
    private String source;     //设备名称
    private String sourceid;     //设备名称
    private String scope;     //设备名称
    private String locationx;
    private String locationy;
    private String locationlabel;
    private String locationscale;
    private static final long serialVersionUID = 1L;
    private int praisecount;
    private int sharecount;
    private int commentcount;
    private boolean mypraise;

    private MsgCardComment lastComment;

    private List<MsgCardAttachment> attachments;
    private List<MsgCardAction> actions;
    private List<MsgCardComment> comments;
    private List<MsgCardActor> actors;
    //praises
    //shares

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPraisecount() {
        return praisecount;
    }

    public void setPraisecount(int praisecount) {
        this.praisecount = praisecount;
    }

    public int getSharecount() {
        return sharecount;
    }

    public void setSharecount(int sharecount) {
        this.sharecount = sharecount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public boolean isMypraise() {
        return mypraise;
    }

    public void setMypraise(boolean mypraise) {
        this.mypraise = mypraise;
    }

    public MsgCardComment getLastComment() {
        return lastComment;
    }

    public void setLastComment(MsgCardComment lastComment) {
        this.lastComment = lastComment;
    }

    public List<MsgCardAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MsgCardAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<MsgCardAction> getActions() {
        return actions;
    }

    public void setActions(List<MsgCardAction> actions) {
        this.actions = actions;
    }

    public List<MsgCardActor> getActors() {
        return actors;
    }

    public void setActors(List<MsgCardActor> actors) {
        this.actors = actors;
    }

    public List<MsgCardComment> getComments() {
        return comments;
    }

    public void setComments(List<MsgCardComment> comments) {
        this.comments = comments;
    }
}
