package com.yun9.wservice.view.doc;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/2.
 */
public class DocCompositeCommand extends JupiterCommand {
    public static final String COMPLETE_TYPE_SENDMSGCARD = "sendmsgcard";
    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    private boolean edit;

    private String instid;

    private String userid;

    private String completeType = COMPLETE_TYPE_SENDMSGCARD;


    public boolean isEdit() {
        return edit;
    }

    public DocCompositeCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public DocCompositeCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public DocCompositeCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getCompleteType() {
        return completeType;
    }

    public DocCompositeCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }
}
