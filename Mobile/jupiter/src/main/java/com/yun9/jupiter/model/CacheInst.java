package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/25.
 */
public class CacheInst implements java.io.Serializable {

    private String instid;
    private String instname;
    private String tel;

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getInstname() {
        return instname;
    }

    public void setInstname(String instname) {
        this.instname = instname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
