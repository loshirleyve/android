package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgPhoneUserCommand extends JupiterCommand {

    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    public static final String PARAM_USER = "user";

    public static final int REQUEST_CODE = 1007;

    private List<String> selectUsers;

    private String completeType = COMPLETE_TYPE_CALLBACK;

    public String getCompleteType() {
        return completeType;
    }

    public OrgPhoneUserCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }

    public List<String> getSelectUsers() {
        return selectUsers;
    }

    public void setSelectUsers(List<String> selectUsers) {
        this.selectUsers = selectUsers;
    }


    public OrgPhoneUserCommand putSelectUser(String userid) {
        if (!AssertValue.isNotNull(selectUsers)) {
            selectUsers = new ArrayList<>();
        }

        selectUsers.add(userid);

        return this;
    }

}
