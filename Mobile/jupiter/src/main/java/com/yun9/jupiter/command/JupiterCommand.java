package com.yun9.jupiter.command;

/**
 * Created by Leon on 15/6/1.
 */
public abstract class JupiterCommand implements java.io.Serializable {
    public static final int RESULT_CODE_CANCEL = 0;

    public static final int RESULT_CODE_OK = 1;

    public static final int RESULT_CODE_ERROR = -1;

    private static int REQUEST_INDEX = 1000;

    public static final String PARAM_COMMAND = "command";

    public static final String RESULT_PARAM = "data";

    private int requestCode = -1;

    public int getRequestCode() {
        synchronized (this) {
            if (requestCode < 0) {
                REQUEST_INDEX++;
                requestCode = REQUEST_INDEX;
            }
        }
        return requestCode;
    }


}
