package com.yun9.jupiter.image;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerCommand extends JupiterCommand{
    private int position;

    private List<LocalFileBean> localFileBeans;

    public int getPosition() {
        return position;
    }

    public ImageBrowerCommand setPosition(int position) {
        this.position = position;
        return this;
    }

    public List<LocalFileBean> getLocalFileBeans() {
        return localFileBeans;
    }

    public ImageBrowerCommand setLocalFileBeans(List<LocalFileBean> localFileBeans) {
        this.localFileBeans = localFileBeans;
        return this;
    }

    public ImageBrowerCommand putImageBean(LocalFileBean localFileBean){
        if (!AssertValue.isNotNull(localFileBeans)){
            this.localFileBeans = new ArrayList<>();
        }

        this.localFileBeans.add(localFileBean);
        return this;
    }
}
