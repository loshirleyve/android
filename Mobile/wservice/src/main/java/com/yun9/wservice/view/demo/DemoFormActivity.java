package com.yun9.wservice.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yun9.jupiter.form.Form;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class DemoFormActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.demoform)
    private JupiterRowStyleSutitleLayout formitem;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.content)
    private TextView contentTV;

    @Override
    protected int getContentView() {
        return R.layout.activity_demo_form;
    }


    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DemoFormActivity.class);
        if (AssertValue.isNotNull(bundle)) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoFormActivity.this.finish();
            }
        });

        formitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.start(DemoFormActivity.this, FormActivity.REQUEST_CODE.NORMAL, fakeData());
            }
        });

    }

    public FormBean fakeData() {
        FormBean formBean = FormBean.getInstance();
        formBean.setTitle("测试表单");
        formBean.setKey("demoform");

        TextFormCellBean textFormCell = new TextFormCellBean();
        textFormCell.setType(TextFormCell.class.getName());
        textFormCell.setKey("testText");
        textFormCell.setDefaultValue("hello");
        textFormCell.setLabel("测试文本输入");
        textFormCell.setRequired(true);
        formBean.putCellBean(textFormCell);

        ImageFormCellBean imageFormCell = new ImageFormCellBean();
        imageFormCell.setKey("testImage");
        imageFormCell.setValue("1,2");
        imageFormCell.setType(ImageFormCell.class.getName());
        imageFormCell.setLabel("测试图片选择");
        formBean.putCellBean(imageFormCell);

        DocFormCellBean docFormCell= new DocFormCellBean();
        docFormCell.setKey("testDoc");
        docFormCell.setType(DocFormCell.class.getName());
        docFormCell.setValue("1");
        docFormCell.setLabel("测试文档选择");
        formBean.putCellBean(docFormCell);

        UserFormCellBean userFormCellBean = new UserFormCellBean();
        userFormCellBean.setKey("testUser");
        userFormCellBean.setType(UserFormCell.class.getName());
        userFormCellBean.setLabel("测试选择用户");
        userFormCellBean.setValue(userValue());
        formBean.putCellBean(userFormCellBean);

        DetailFormCellBean detailFormCell = new DetailFormCellBean();
        detailFormCell.setLabel("测试子项目");
        detailFormCell.setKey("testDetail");
        detailFormCell.setFormBean(this.builderSubForm());
        detailFormCell.setType(DetailFormCell.class.getName());
        detailFormCell.setTitlekey("testsubinput");
        detailFormCell.setSubtitlekey("testsubinput2");
        formBean.putCellBean(detailFormCell);

        MultiSelectFormCellBean multiSelectFormCellBean = new MultiSelectFormCellBean();
        multiSelectFormCellBean.setLabel("测试多选");
        multiSelectFormCellBean.setKey("testMultiSelect");
        multiSelectFormCellBean.setType(MultiSelectFormCell.class.getName());
        multiSelectFormCellBean.setMaxNum(2);
        multiSelectFormCellBean.setMinNum(1);
        Map<String,Object> map = new HashMap<>();
        map.put("1", "深圳顶聚科技");
        map.put("2", "深圳顶聚科技2");
        multiSelectFormCellBean.setValue(map);
        multiSelectFormCellBean.setOptionMap(this.builderOptions());
        formBean.putCellBean(multiSelectFormCellBean);
        return formBean;
    }

    private List<Map<String,String>> userValue() {
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> mm = new HashMap<>();
        mm.put("type","user");
        mm.put("value","drawable://"+R.drawable.tianjia);
        Map<String,String> mm2 = new HashMap<>();
        mm2.put("type","user");
        mm2.put("value","drawable://"+R.drawable.bpush_gray_logo);
        maps.add(mm);
        maps.add(mm2);
        return maps;
    }

    private Map<String, String> builderOptions() {
        Map<String,String> map = new HashMap<>();
        map.put("1","深圳顶聚科技");
        map.put("2","深圳顶聚科技2");
        map.put("3","深圳顶聚科技3");
        map.put("4","深圳顶聚科技4");
        return map;
    }

    private FormBean builderSubForm(){
        FormBean subform = FormBean.getInstance();
        subform.setEditableWhenLoaded(true);
        subform.setKey("subform");
        subform.setTitle("测试子项目");
        TextFormCellBean textFormCell = new TextFormCellBean();
        textFormCell.setLabel("测试子项目文本输入");
        textFormCell.setKey("testsubinput");
        textFormCell.setType(TextFormCell.class.getName());
        subform.putCellBean(textFormCell);

        TextFormCellBean textFormCell2 = new TextFormCellBean();
        textFormCell2.setLabel("测试子项目文本输入Sub");
        textFormCell2.setKey("testsubinput2");
        textFormCell2.setType(TextFormCell.class.getName());
        subform.putCellBean(textFormCell2);
        return subform;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FormActivity.REQUEST_CODE.NORMAL
                && resultCode == FormActivity.RESPONSE_CODE.COMPLETE) {
            FormBean formBean = (FormBean) data.getSerializableExtra("form");
            contentTV.setText(beanToJson(formBean.getValue()));
        }
    }

    private String beanToJson(Object bean) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(bean);
    }
}
