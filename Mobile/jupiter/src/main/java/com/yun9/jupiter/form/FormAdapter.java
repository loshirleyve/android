package com.yun9.jupiter.form;

/**
 * Created by Leon on 15/5/25.
 */
public interface FormAdapter extends java.io.Serializable {

    public Form getForm();

    public void onComplete();

    public void onCancel();

    public void onReset();

}
