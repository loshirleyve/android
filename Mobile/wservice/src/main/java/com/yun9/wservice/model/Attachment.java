package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * 订单附件
 * @see com.yun9.wservice.enums.AttachmentInputType
 * Created by huangbinglong on 7/10/15.
 */
public class Attachment implements Serializable{

    private String id;
    private String inputvalue;
    private String attachkey;
    private String attachname;
    private String inputtype;
    private String inputdesc;
    private String demovalue;
    private String transfertype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputvalue() {
        return inputvalue;
    }

    public void setInputvalue(String inputvalue) {
        this.inputvalue = inputvalue;
    }

    public String getAttachkey() {
        return attachkey;
    }

    public void setAttachkey(String attachkey) {
        this.attachkey = attachkey;
    }

    public String getAttachname() {
        return attachname;
    }

    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public String getInputdesc() {
        return inputdesc;
    }

    public void setInputdesc(String inputdesc) {
        this.inputdesc = inputdesc;
    }

    public String getDemovalue() {
        return demovalue;
    }

    public void setDemovalue(String demovalue) {
        this.demovalue = demovalue;
    }

    public String getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype;
    }
}
