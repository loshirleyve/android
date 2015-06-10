package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgCompositeCommand extends JupiterCommand {

    public static final String COMPLETE_TYPE_SENDMSGCARD = "sendmsgcard";
    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    public static final String PARAM_USER = "user";
    public static final String PARAM_ORG = "org";

    public static final String DIM_TYPE_GROUP = "group";

    public static final String DIM_TYPE_HR = "hr";

    public static final int REQUEST_CODE = 1001;

    private List<String> selectUsers;

    private List<String> selectOrgs;

    private String instid;

    private String userid;

    private boolean edit;

    private String completeType = COMPLETE_TYPE_SENDMSGCARD;

    public boolean isEdit() {
        return edit;
    }

    public OrgCompositeCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public String getCompleteType() {
        return completeType;
    }

    public OrgCompositeCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }

    public List<String> getSelectUsers() {
        return selectUsers;
    }

    public void setSelectUsers(List<String> selectUsers) {
        this.selectUsers = selectUsers;
    }

    public List<String> getSelectOrgs() {
        return selectOrgs;
    }

    public void setSelectOrgs(List<String> selectOrgs) {
        this.selectOrgs = selectOrgs;
    }

    public String getInstid() {
        return instid;
    }

    public OrgCompositeCommand setInstid(String instid) {
        this.instid = instid;

        return this;
    }

    public String getUserid() {
        return userid;
    }

    public OrgCompositeCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public OrgCompositeCommand putSelectUser(String userid){
        if (!AssertValue.isNotNull(selectUsers)){
            selectUsers = new ArrayList<>();
        }

        selectUsers.add(userid);

        return this;
    }

    public OrgCompositeCommand putSelectOrgs(String orgid){
        if(!AssertValue.isNotNull(selectOrgs)){
            selectOrgs = new ArrayList<>();
        }
        selectOrgs.add(orgid);
        return this;
    }
}
