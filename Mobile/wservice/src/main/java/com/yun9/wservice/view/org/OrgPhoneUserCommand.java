package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.model.PhoneUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgPhoneUserCommand extends JupiterCommand {

    public static final int REQUEST_CODE = 1007;

    private String orgid;

    private List<String> selectUsers;

    private List<PhoneUser> users;

    private String instid;

    private String userid;

    public List<String> getSelectUsers() {
        return selectUsers;
    }

    public OrgPhoneUserCommand setSelectUsers(List<String> selectUsers) {
        this.selectUsers = selectUsers;
        return this;
    }


    public List<PhoneUser> getUsers() {
        return users;
    }

    public void setUsers(List<PhoneUser> users) {
        this.users = users;
    }


    public String getOrgid() {
        return orgid;
    }

    public OrgPhoneUserCommand setOrgid(String orgid) {
        this.orgid = orgid;
        return this;
    }
    public OrgPhoneUserCommand putSelectUser(String userid) {
        if (!AssertValue.isNotNull(selectUsers)) {
            selectUsers = new ArrayList<>();
        }

        selectUsers.add(userid);

        return this;
    }

    public String getInstid() {
        return instid;
    }

    public OrgPhoneUserCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public OrgPhoneUserCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }


}
