package com.yun9.wservice.view.org;

import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgCompositeUserListBean implements java.io.Serializable{
    private User user;

    private boolean selected;

    private boolean top;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
