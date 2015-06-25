package com.yun9.wservice.model;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgsGroup {

    private int unreadnum;
    private String fromuserid;
    private long lastmsgdate;
    private String lastcontent;
    private String touserid;

    public int getUnreadnum() {
        return unreadnum;
    }

    public void setUnreadnum(int unreadnum) {
        this.unreadnum = unreadnum;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public long getLastmsgdate() {
        return lastmsgdate;
    }

    public void setLastmsgdate(long lastmsgdate) {
        this.lastmsgdate = lastmsgdate;
    }

    public String getLastcontent() {
        return lastcontent;
    }

    public void setLastcontent(String lastcontent) {
        this.lastcontent = lastcontent;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }
}
