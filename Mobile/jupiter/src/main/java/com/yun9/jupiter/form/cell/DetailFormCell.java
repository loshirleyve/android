package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.View;

import com.yun9.jupiter.form.Form;
import com.yun9.jupiter.form.FormCell;

import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class DetailFormCell extends FormCell {

    private Form form;

    private String titlekey;

    private String sutitlekey;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public void init() {

    }

    @Override
    public View getCellView(Context context) {
        return null;
    }

    @Override
    public void edit(boolean edit) {

    }

    public String getTitlekey() {
        return titlekey;
    }

    public void setTitlekey(String titlekey) {
        this.titlekey = titlekey;
    }

    public String getSutitlekey() {
        return sutitlekey;
    }

    public void setSutitlekey(String sutitlekey) {
        this.sutitlekey = sutitlekey;
    }
}
