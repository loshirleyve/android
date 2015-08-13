package com.yun9.jupiter.model;

import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/6/23.
 */
public class CacheUser implements java.io.Serializable{
    private String id;
    private String no;
    private String name;
    private String head;
    private String url;
    private String phone;
    private String instname;
    private String instsimplename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public CacheUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String[] getPhones() {
        if (AssertValue.isNotNullAndNotEmpty(this.phone)){
            return phone.split(";");
        }
        return null;
    }

    public String getFirstPhone() {
        String[] phones = getPhones();

        if (phones != null
                && phones.length > 0){
            return phones[0];
        }
        return null;
    }

    public String getBriefSimpleInstname() {
        if (AssertValue.isNotNullAndNotEmpty(instsimplename)){
            String[] is = instsimplename.split("\\s");
            if (is.length > 1){
                return is[0];
            }
        }
        return instsimplename;
    }

    public String getBriefInstname() {
        if (AssertValue.isNotNullAndNotEmpty(instname)){
            String[] is = instname.split("\\s");
            if (is.length > 1){
                return is[0];
            }
        }
        return instname;
    }

    public String getInstname() {
        return instname;
    }

    public CacheUser setInstname(String instname) {
        this.instname = instname;
        return this;
    }

    public String getInstsimplename() {
        return instsimplename;
    }

    public CacheUser setInstsimplename(String instsimplename) {
        this.instsimplename = instsimplename;
        return this;
    }
}
