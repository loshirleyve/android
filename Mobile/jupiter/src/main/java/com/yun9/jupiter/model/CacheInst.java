package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/25.
 */
public class CacheInst implements java.io.Serializable {

    private String instid;
    private String instname;
    private String tel;
    private String logo;
    private String logourl;
    private String simplename;

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

    public String getLogo() {
        return logo;
    }

    public CacheInst setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public String getLogourl() {
        return logourl;
    }

    public CacheInst setLogourl(String logourl) {
        this.logourl = logourl;
        return this;
    }

    public String getSimplename() {
        return simplename;
    }

    public CacheInst setSimplename(String simplename) {
        this.simplename = simplename;
        return this;
    }
}
