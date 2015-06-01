package com.yun9.wservice.view.org;

import com.yun9.jupiter.model.Org;

/**
 * Created by Leon on 15/6/1.
 */
public class OrgListBean implements java.io.Serializable {

    private boolean selected;

    private Org org;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
