package com.yun9.jupiter.form;

import android.content.Context;
import android.view.View;

import com.yun9.jupiter.form.model.FormCellBean;

/**
 * Created by Leon on 15/5/25.
 */
public abstract class FormCell implements java.io.Serializable{

    public FormCell(FormCellBean cellBean) {
    }

    public void init(){

    };

    public abstract View getCellView(Context context);

    public abstract void edit(boolean edit);

    public abstract Object getValue();

    public abstract FormCellBean getFormCellBean();

    public abstract String validate();
}
