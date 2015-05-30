package com.yun9.wservice.view.org;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgUserBean implements java.io.Serializable{

    private String id;

    private String name;

    private String no;

    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
