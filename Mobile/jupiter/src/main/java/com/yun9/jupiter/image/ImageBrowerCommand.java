package com.yun9.jupiter.image;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerCommand extends JupiterCommand{
    private int position;

    private List<ImageBean> imageBeans;

    public int getPosition() {
        return position;
    }

    public ImageBrowerCommand setPosition(int position) {
        this.position = position;
        return this;
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public ImageBrowerCommand setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
        return this;
    }

    public ImageBrowerCommand putImageBean(ImageBean imageBean){
        if (!AssertValue.isNotNull(imageBeans)){
            this.imageBeans = new ArrayList<>();
        }

        this.imageBeans.add(imageBean);
        return this;
    }
}
