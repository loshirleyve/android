package com.yun9.wservice.view.dynamic;

import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;

/**
 * Created by Leon on 15/6/11.
 */
public class OrgAndUserBean implements java.io.Serializable {

    public static final String TYPE_ORG = "org";
    public static final String TYPE_USER = "user";

    private String type;

    private Org org;

    private User user;

    public OrgAndUserBean(Org org){
        this.org = org;
        type = TYPE_ORG;
    }

    public OrgAndUserBean(User user){
        this.user = user;
        type =TYPE_USER;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
