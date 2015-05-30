package com.yun9.wservice.view.org;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgCompositeCommand implements java.io.Serializable{

    private boolean edit;

    public boolean isEdit() {
        return edit;
    }

    public OrgCompositeCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }
}
