package com.yun9.wservice.model;

import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/23.
 */
public class NewMsgCard implements java.io.Serializable {
    private String instid;
    private String userid;
    private String topic;
    private String subject;
    private String content;
    private String source = "none";
    private String scope = "private";
    private List<NewMsgCardUser> users;
    private List<NewMsgCardAction> actions;
    private List<NewMsgCardAttachment> attachments;

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<NewMsgCardUser> getUsers() {
        return users;
    }

    public void setUsers(List<NewMsgCardUser> users) {
        this.users = users;
    }

    public List<NewMsgCardAction> getActions() {
        return actions;
    }

    public void setActions(List<NewMsgCardAction> actions) {
        this.actions = actions;
    }

    public List<NewMsgCardAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<NewMsgCardAttachment> attachments) {
        this.attachments = attachments;
    }

    public void putUser(NewMsgCardUser user){
        if (!AssertValue.isNotNull(users)){
            users = new ArrayList<>();
        }

        users.add(user);
    }

    public void putAction(NewMsgCardAction action){
        if (!AssertValue.isNotNull(actions)){
            actions = new ArrayList<>();
        }

        this.actions.add(action);
    }

    public void putAttachment(NewMsgCardAttachment attachment){
        if (!AssertValue.isNotNull(this.attachments)){
            this.attachments = new ArrayList<>();
        }

        this.attachments.add(attachment);
    }
}
