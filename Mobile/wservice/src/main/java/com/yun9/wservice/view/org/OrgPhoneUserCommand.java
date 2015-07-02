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

    private List<String> selectUsers;

    private List<PhoneUser> users;


    public List<String> getSelectUsers() {
        return selectUsers;
    }

    public void setSelectUsers(List<String> selectUsers) {
        this.selectUsers = selectUsers;
    }


    public List<PhoneUser>  getUsers() {
        return users;
    }

    public void setUsers(List<PhoneUser> users) {
        this.users = users;
    }



    public OrgPhoneUserCommand putSelectUser(String userid) {
        if (!AssertValue.isNotNull(selectUsers)) {
            selectUsers = new ArrayList<>();
        }

        selectUsers.add(userid);

        return this;
    }

}
