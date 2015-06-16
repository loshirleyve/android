package com.yun9.wservice.view.doc;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageCommand extends JupiterCommand {
    public static final String COMPLETE_TYPE_SENDMSGCARD = "sendmsgcard";
    public static final String COMPLETE_TYPE_CALLBACK = "callback";

    public static final String PARAM_IMAGE = "images";

    private int maxSelectNum = 0;

    private boolean edit;

    private List<LocalFileBean> selectImages;

    private String completeType = COMPLETE_TYPE_SENDMSGCARD;


    public boolean isEdit() {
        return edit;
    }

    public LocalImageCommand setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }


    public List<LocalFileBean> getSelectImages() {
        return selectImages;
    }

    public LocalImageCommand setSelectImages(List<LocalFileBean> selectImages) {
        this.selectImages = selectImages;
        return this;
    }

    public LocalImageCommand putSelectImage(LocalFileBean localFileBean) {
        if (!AssertValue.isNotNull(selectImages)) {
            this.selectImages = new ArrayList<>();
        }

        this.selectImages.add(localFileBean);
        return this;
    }

    public String getCompleteType() {
        return completeType;
    }

    public LocalImageCommand setCompleteType(String completeType) {
        this.completeType = completeType;
        return this;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public LocalImageCommand setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }
}
