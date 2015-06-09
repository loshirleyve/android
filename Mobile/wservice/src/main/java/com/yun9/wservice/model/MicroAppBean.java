package com.yun9.wservice.model;

import android.view.View;

import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppBean  {

    public static final String TYPE_ITEM  = "item";

    public static final String TYPE_GROUP = "group";


    private String id;
    private String name;
    private String no;
    private String type = TYPE_ITEM;
    private String parentid;
    private String actiontype;
    private String icopath;
    private List<MicroAppBean> children;

    public MicroAppBean(String id,String name,String type){
        this.id= id;
        this.name = name;
        this.type = type;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }


    public String getIcopath() {
        return icopath;
    }

    public void setIcopath(String icopath) {
        this.icopath = icopath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<MicroAppBean> getChildren() {
        return children;
    }

    public void setChildren(List<MicroAppBean> children) {
        this.children = children;
    }

    public void putChildren(MicroAppBean microAppBean){
        if (!AssertValue.isNotNull(children)){
            this.children = new ArrayList<>();
        }

        this.children.add(microAppBean);
    }
}
