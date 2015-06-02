package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/29.
 */
public class OrgListCommand extends JupiterCommand {
    private String dimType;

    private String title;

    private boolean edit;

    private boolean newAction;

    public static final String PARAM_ORG = "org";

    public static final String PARAM_DIMTYPE = "dimtype";

    public static final int REQUEST_CODE = 1002;

    private String instid;

    private List<String> selectOrgs;

    public String getDimType() {
        return dimType;
    }

    public OrgListCommand setDimType(String dimType) {
        this.dimType = dimType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OrgListCommand setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isEdit() {
        return edit;
    }

    public OrgListCommand setEdit(boolean edit) {
        this.edit = edit;

        return this;
    }

    public boolean isNewAction() {
        return newAction;
    }

    public OrgListCommand setNewAction(boolean newAction) {
        this.newAction = newAction;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public OrgListCommand setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public List<String> getSelectOrgs() {
        return selectOrgs;
    }

    public void setSelectOrgs(List<String> selectOrgs) {
        this.selectOrgs = selectOrgs;
    }

    public OrgListCommand putSelectOrgs(String orgid) {
        if (!AssertValue.isNotNull(selectOrgs)) {
            selectOrgs = new ArrayList<>();
        }
        selectOrgs.add(orgid);
        return this;
    }
}
