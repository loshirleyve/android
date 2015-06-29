package com.yun9.wservice.view.camera;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/26.
 */
public class CameraCommand extends JupiterCommand {
    public static final String PARAM_IMAGE = "image";


    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public CameraCommand setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }
}
