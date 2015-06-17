package com.yun9.jupiter.image;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerCommand extends JupiterCommand{
    private int position;

    private List<FileBean> fileBeans;

    public int getPosition() {
        return position;
    }

    public ImageBrowerCommand setPosition(int position) {
        this.position = position;
        return this;
    }

    public List<FileBean> getFileBeans() {
        return fileBeans;
    }

    public ImageBrowerCommand setFileBeans(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
        return this;
    }

    public ImageBrowerCommand putImageBean(FileBean fileBean){
        if (!AssertValue.isNotNull(fileBeans)){
            this.fileBeans = new ArrayList<>();
        }

        this.fileBeans.add(fileBean);
        return this;
    }
}
