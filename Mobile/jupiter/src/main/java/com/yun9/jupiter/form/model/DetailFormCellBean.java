package com.yun9.jupiter.form.model;

import com.yun9.jupiter.form.Form;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class DetailFormCellBean extends FormCellBean{

    private FormBean formBean;

    private String titlekey;

    private String subtitlekey;

    public FormBean getFormBean() {
        return formBean;
    }

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }

    public String getTitlekey() {
        return titlekey;
    }

    public void setTitlekey(String titlekey) {
        this.titlekey = titlekey;
    }

    public String getSubtitlekey() {
        return subtitlekey;
    }

    public void setSubtitlekey(String subtitlekey) {
        this.subtitlekey = subtitlekey;
    }
}
