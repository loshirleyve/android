package com.yun9.wservice.view.myself;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.User;

/**
 * Created by li on 2015/6/26.
 */
public class UserSignatureCommand extends JupiterCommand {

    private String userid;
    private String instid;
    public static final String PARAM_SIGNATURE_COMMAND = "signature";

<<<<<<< HEAD
    public static final String PARAM_SIGNATURE = "signature";


    public String getSignature() {
        return signature;
=======

    public String getInstid() {
        return instid;
>>>>>>> dev-shirley
    }

    public UserSignatureCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public UserSignatureCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }
}
