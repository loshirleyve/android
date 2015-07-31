package com.yun9.jupiter.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/31/15.
 */
public class LoginUser implements Serializable{
    private User user;
    private List<Inst> insts;

    public User getUser() {
        return user;
    }

    public LoginUser setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Inst> getInsts() {
        return insts;
    }

    public LoginUser setInsts(List<Inst> insts) {
        this.insts = insts;
        return this;
    }
}
