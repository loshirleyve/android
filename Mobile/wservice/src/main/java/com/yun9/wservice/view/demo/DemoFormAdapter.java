package com.yun9.wservice.view.demo;

import com.yun9.jupiter.form.Form;
import com.yun9.jupiter.form.FormAdapter;
import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;

/**
 * Created by Leon on 15/5/25.
 */
public class DemoFormAdapter implements FormAdapter {

    private Form form;

    public DemoFormAdapter() {

        form = Form.getInstance();
        form.setTitle("测试表单");
        form.setKey("demoform");

        TextFormCell textFormCell = form.createCell(TextFormCell.class);
        textFormCell.setKey("testText");
        textFormCell.setDefaultValue("hello");
        textFormCell.setLabel("测试文本输入");
        textFormCell.setRequired(true);
        form.putCell(textFormCell);

        ImageFormCell imageFormCell = form.createCell(ImageFormCell.class);
        imageFormCell.setKey("testImage");
        imageFormCell.setLabel("测试图片选择");
        form.putCell(imageFormCell);

        DocFormCell docFormCell= form.createCell(DocFormCell.class);
        docFormCell.setKey("testDoc");
        docFormCell.setLabel("测试文档选择");
        form.putCell(docFormCell);

        DetailFormCell detailFormCell = form.createCell(DetailFormCell.class);
        detailFormCell.setLabel("测试子项目");
        detailFormCell.setKey("testDetail");
        detailFormCell.setForm(this.builderSubForm());
        detailFormCell.setTitlekey("testsubinput");
        detailFormCell.setSutitlekey("testsubinput");
        form.putCell(detailFormCell);

    }

    private Form builderSubForm(){
        Form subform = Form.getInstance();

        subform.setKey("subform");
        subform.setTitle("测试子项目");
        TextFormCell textFormCell = subform.createCell(TextFormCell.class);
        textFormCell.setLabel("测试子项目文本输入");
        textFormCell.setKey("testsubinput");
        subform.putCell(textFormCell);
        return subform;
    }

    @Override
    public Form getForm() {
        return this.form;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onReset() {

    }
}
