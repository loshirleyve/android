package com.yun9.jupiter.model;

import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/13.
 */
public class ImageBean implements java.io.Serializable {

    private int id;
    private int parentid = 0;
    private String filePath;
    private String absolutePath;
    private String thumbnailPath;
    private String name;
    private String dateAdded;
    private boolean selected = false;

    private List<ImageBean> childs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<ImageBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ImageBean> childs) {
        this.childs = childs;
    }

    public void putChild(ImageBean imageBean) {
        if (!AssertValue.isNotNull(childs)) {
            childs = new ArrayList<>();
        }

        childs.add(imageBean);

    }
}
