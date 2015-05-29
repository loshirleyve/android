package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/5/25.
 */
public class TextFormCell extends FormCell {

    private TextView labelTV;

    private EditText inputTextET;

    private TextFormCellBean cellBean;

    public TextFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (TextFormCellBean) cellBean;
    }


    @Override
    public void init() {

    }

    @Override
    public View getCellView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.form_cell_textinput,null);
        this.labelTV = (TextView) view.findViewById(R.id.label);
        this.inputTextET = (EditText) view.findViewById(R.id.textInput);

        //检查是否存在默认值
        if (AssertValue.isNotNullAndNotEmpty(cellBean.getDefaultValue())){
            inputTextET.setText(cellBean.getDefaultValue());
        }
        //设置label
        labelTV.setText(cellBean.getLabel());
        restore();
        return view;
    }

    private void restore() {
        if (cellBean.getValue() == null) {
            return;
        }
        inputTextET.setText((String) cellBean.getValue());
    }

    @Override
    public void edit(boolean edit) {
        inputTextET.setEnabled(edit);
    }

    @Override
    public Object getValue() {
        return String.valueOf(inputTextET.getText());
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
