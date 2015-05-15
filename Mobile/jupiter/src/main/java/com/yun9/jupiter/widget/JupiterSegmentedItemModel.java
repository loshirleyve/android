package com.yun9.jupiter.widget;

/**
 * Created by huangbinglong on 15/5/15.
 */
public class JupiterSegmentedItemModel {

    private int textColor;

    private int textColorSelected;

    private int icoImage;

    private int icoImageSelected;

    private int title;

    private int desc;

    public JupiterSegmentedItemModel(){

    }

    public JupiterSegmentedItemModel(int title, int icoImage, int icoImageSelected) {
        this.title = title;
        this.icoImage = icoImage;
        this.icoImageSelected = icoImageSelected;
    }

    // getter and setter
    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColorSelected() {
        return textColorSelected;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public int getIcoImage() {
        return icoImage;
    }

    public void setIcoImage(int icoImage) {
        this.icoImage = icoImage;
    }

    public int getIcoImageSelected() {
        return icoImageSelected;
    }

    public void setIcoImageSelected(int icoImageSelected) {
        this.icoImageSelected = icoImageSelected;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
