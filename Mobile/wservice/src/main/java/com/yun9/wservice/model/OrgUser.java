package com.yun9.wservice.model;

import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.model.User;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/9/15.
 */
public class OrgUser extends User implements Serializable{

    private Inst inst;

    private String userActivateResponse;

    public Inst getInst() {
        return inst;
    }

    public void setInst(Inst inst) {
        this.inst = inst;
    }

    public String getUserActivateResponse() {
        return userActivateResponse;
    }

    public void setUserActivateResponse(String userActivateResponse) {
        this.userActivateResponse = userActivateResponse;
    }
}
