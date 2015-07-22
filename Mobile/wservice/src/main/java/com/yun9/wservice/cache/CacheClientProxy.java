package com.yun9.wservice.cache;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/29.
 */
public class CacheClientProxy implements Serializable{

    /**
     * 被代理机构
     */
    private String instId;

    /**
     * 被代理人
     */
    private String userId;

    /**
     * 客户ID
     */
    private String clientId;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public CacheClientProxy setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }
}
