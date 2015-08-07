package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/7/15.
 */
public class Msg implements Serializable{

    private String id;
    private String page;
    private String touserid;
    private String usertype;
    private String fromuserid;
    private String type;
    private String source;
    private String content;
    private int state;
    private String instid;
    private MsgCard msgCard;

    public String getId() {
        return id;
    }

    public Msg setId(String id) {
        this.id = id;
        return this;
    }

    public String getPage() {
        return page;
    }

    public Msg setPage(String page) {
        this.page = page;
        return this;
    }

    public String getTouserid() {
        return touserid;
    }

    public Msg setTouserid(String touserid) {
        this.touserid = touserid;
        return this;
    }

    public String getUsertype() {
        return usertype;
    }

    public Msg setUsertype(String usertype) {
        this.usertype = usertype;
        return this;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public Msg setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
        return this;
    }

    public String getType() {
        return type;
    }

    public Msg setType(String type) {
        this.type = type;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Msg setSource(String source) {
        this.source = source;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Msg setContent(String content) {
        this.content = content;
        return this;
    }

    public int getState() {
        return state;
    }

    public Msg setState(int state) {
        this.state = state;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public Msg setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public MsgCard getMsgCard() {
        return msgCard;
    }

    public Msg setMsgCard(MsgCard msgCard) {
        this.msgCard = msgCard;
        return this;
    }
}
