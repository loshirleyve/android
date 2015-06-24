package com.yun9.wservice.model;

import com.yun9.jupiter.model.Dim;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;

import java.util.List;

/**
 * Created by Leon on 15/6/10.
 */
public class OrgDetailInfoBean implements java.io.Serializable {

    private String id;
    private String parentid;
    private String parentname;
    private String type;
    private String ownerName;
    private int userNum;
    private String name;
    private List<User> users;
    private List<Org> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {return parentid;}

    public void setParentid(String parentid) {this.parentid = parentid;}

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setChildren(List<Org> children) {
        this.children = children;
    }
}
