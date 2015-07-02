package com.yun9.wservice.model;

/**
 * Created by li on 2015/6/13.
 */
public class PhoneUser implements java.io.Serializable {

    private String username;
    private String usernumber;

    private boolean selected;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
