package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/2.
 * 打开组织编辑窗口的参数传递对象
 * 1、edit为是否进入编辑状态，如果没有设置orgid，则自动编辑模式（新增组织状态）
 * 2、orgid 根据此id检索服务器上的组织数据
 */
public class OrgChooseAddUserCommand extends JupiterCommand {

    public static final int REQUEST_CODE = 1006;


    private String orgid;

    private String orgname;

    private String instid;

    private String userid;

    private String parentorgid;

    private String parentorgname;

    private String parentorgtype;




    public String getOrgid() {
        return orgid;
    }

    public OrgChooseAddUserCommand setOrgid(String orgid) {
        this.orgid = orgid;
        return this;
    }

    public String getOrgname() {
        return orgname;
    }

    public OrgChooseAddUserCommand setOrgname(String orgname) {
        this.orgname = orgname;
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


    public String getParentorgid() {
        return parentorgid;
    }

    public OrgChooseAddUserCommand setParentorgid(String parentorgid) {
        this.parentorgid = parentorgid;
        return this;
    }

    public String getParentorgname() {
        return parentorgname;
    }

    public OrgChooseAddUserCommand setParentorgname(String parentorgname) {
        this.parentorgname = parentorgname;
        return this;
    }

    public String getParentorgtype() {
        return parentorgtype;
    }

    public OrgChooseAddUserCommand setParentorgtype(String parentorgtype) {
        this.parentorgtype = parentorgtype;
        return this;
    }
}
