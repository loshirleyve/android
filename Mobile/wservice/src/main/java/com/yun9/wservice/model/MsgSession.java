package com.yun9.wservice.model;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgSession {

    private String fromuserid;
    private String fromuserheadid;
    private String fromusername;
    private String lastmsgconttext;
    private long lastmsgdate;
    private int unreadmsgnum;

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getFromuserheadid() {
        return fromuserheadid;
    }

    public void setFromuserheadid(String fromuserheadid) {
        this.fromuserheadid = fromuserheadid;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    public String getLastmsgconttext() {
        return lastmsgconttext;
    }

    public void setLastmsgconttext(String lastmsgconttext) {
        this.lastmsgconttext = lastmsgconttext;
    }

    public long getLastmsgdate() {
        return lastmsgdate;
    }

    public void setLastmsgdate(long lastmsgdate) {
        this.lastmsgdate = lastmsgdate;
    }

    public int getUnreadmsgnum() {
        return unreadmsgnum;
    }

    public void setUnreadmsgnum(int unreadmsgnum) {
        this.unreadmsgnum = unreadmsgnum;
    }
}
