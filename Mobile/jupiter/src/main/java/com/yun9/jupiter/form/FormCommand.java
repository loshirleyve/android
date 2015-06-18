package com.yun9.jupiter.form;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.form.model.FormBean;

/**
 * Created by huangbinglong on 15/6/18.
 */
public class FormCommand extends JupiterCommand{

    private FormBean formBean;

    private String configJson;

    private String valueJson;

    private FormCompleteCallback callback;

    public FormBean getFormBean() {
        return formBean;
    }

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public String getValueJson() {
        return valueJson;
    }

    public void setValueJson(String valueJson) {
        this.valueJson = valueJson;
    }

    public FormCompleteCallback getCallback() {
        return callback;
    }

    public void setCallback(FormCompleteCallback callback) {
        this.callback = callback;
    }

    public interface FormCompleteCallback {
        public boolean callback(FormBean formBean);
    }
}
