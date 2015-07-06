package com.yun9.wservice.view.dynamic;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;
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

    private List<FileBean> onSelectFiles;

    private List<FileBean> onSelectImages;

    private String userid;

    private String instid;

    private String type;

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

    public NewDynamicCommand putUser(User user) {

        if (!AssertValue.isNotNull(this.selectUsers)) {
            this.selectUsers = new ArrayList<>();
        }
        this.selectUsers.add(user);
        return this;
    }

    public NewDynamicCommand putOrg(Org org) {
        if (!AssertValue.isNotNull(selectOrgs)) {
            this.selectOrgs = new ArrayList<>();
        }

        this.selectOrgs.add(org);
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public NewDynamicCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public NewDynamicCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public List<FileBean> getOnSelectImages() {
        return onSelectImages;
    }

    public NewDynamicCommand setOnSelectImages(List<FileBean> onSelectImages) {
        this.onSelectImages = onSelectImages;
        return this;
    }

    public List<FileBean> getOnSelectFiles() {
        return onSelectFiles;
    }

    public NewDynamicCommand setOnSelectFiles(List<FileBean> onSelectFiles) {
        this.onSelectFiles = onSelectFiles;
        return this;
    }
}
