package com.yun9.wservice.view.common;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 15/5/22.
 */
public class Constants {

    public static class IMAGE {
        public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
        public static final String IMAGE_LIST = "com.nostra13.example.universalimageloader.IMAGE_LIST";
    }

    public static class ACTIVITY_RETURN {
        public static final int SUCCESS = JupiterCommand.RESULT_CODE_OK;
        public static final int ERROR = JupiterCommand.RESULT_CODE_ERROR;
    }
}
