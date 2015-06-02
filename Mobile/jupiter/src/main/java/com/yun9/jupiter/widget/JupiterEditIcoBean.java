package com.yun9.jupiter.widget;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterEditIcoBean {

    private String title;

    private boolean edit;

    private String image;

    private String cornerImage;

    public String getTitle() {
        return title;
    }

    public JupiterEditIcoBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCornerImage() {
        return cornerImage;
    }

    public void setCornerImage(String cornerImage) {
        this.cornerImage = cornerImage;
    }
}
