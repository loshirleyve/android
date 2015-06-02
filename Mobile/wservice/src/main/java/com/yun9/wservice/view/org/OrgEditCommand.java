package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/2.
 * 打开组织编辑窗口的参数传递对象
 * 1、edit为是否进入编辑状态，如果没有设置orgid，则自动编辑模式（新增组织状态）
 * 2、orgid 根据此id检索服务器上的组织数据
 */
public class OrgEditCommand extends JupiterCommand {

    public static final int REQUEST_CODE = 1005;

    private boolean edit;

    private String orgid;

    private String instid;

    private String userid;

    public boolean isEdit() {
        return edit;
    }

    public OrgEditCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public String getOrgid() {
        return orgid;
    }

    public OrgEditCommand setOrgid(String orgid) {
        this.orgid = orgid;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}