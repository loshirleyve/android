package com.yun9.wservice.view.dynamic;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/11.
 */
public class NewDynamicCommand extends JupiterCommand {

    private List<Org> selectOrgs;

    private List<User> selectUsers;

    public List<Org> getSelectOrgs() {
        return selectOrgs;
    }

    public NewDynamicCommand setSelectOrgs(List<Org> selectOrgs) {
        this.selectOrgs = selectOrgs;
        return this;
    }

    public List<User> getSelectUsers() {
        return selectUsers;
    }

    public NewDynamicCommand setSelectUsers(List<User> selectUsers) {
        this.selectUsers = selectUsers;
        return this;
    }

    public NewDynamicCommand putUser(User user){

        if (!AssertValue.isNotNull(this.selectUsers)){
            this.selectUsers = new ArrayList<>();
        }
        this.selectUsers.add(user);
        return this;
    }

    public NewDynamicCommand putOrg(Org org){
        if (!AssertValue.isNotNull(selectOrgs)){
            this.selectOrgs = new ArrayList<>();
        }

        this.selectOrgs.add(org);
        return this;
    }
}
