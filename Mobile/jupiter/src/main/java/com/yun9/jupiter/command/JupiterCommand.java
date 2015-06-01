package com.yun9.jupiter.command;

/**
 * Created by Leon on 15/6/1.
 */
public abstract class JupiterCommand  implements  java.io.Serializable{
    public static final int RESULT_CODE_CANCEL = 0;

    public static final int RESULT_CODE_OK = 1;

    public static final int RESULT_CODE_ERROR = - 1;

    private int requestCode;

    protected abstract int getRequestCode();
}
