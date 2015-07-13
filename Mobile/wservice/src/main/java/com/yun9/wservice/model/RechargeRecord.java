package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/13/15.
 */
public class RechargeRecord implements Serializable{
    private String id;
    private String own;
    private String accounttype;
    private String ticketid;
    private double amount;
    private String typeid;
    private String state;
    private Long expirydate;
    private String rechargeno;
    private String rechargename;
    private String ticketname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwn() {
        return own;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Long expirydate) {
        this.expirydate = expirydate;
    }

    public String getRechargeno() {
        return rechargeno;
    }

    public void setRechargeno(String rechargeno) {
        this.rechargeno = rechargeno;
    }

    public String getRechargename() {
        return rechargename;
    }

    public void setRechargename(String rechargename) {
        this.rechargename = rechargename;
    }

    public String getTicketname() {
        return ticketname;
    }

    public void setTicketname(String ticketname) {
        this.ticketname = ticketname;
    }
}
