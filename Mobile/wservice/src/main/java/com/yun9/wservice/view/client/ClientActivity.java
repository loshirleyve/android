package com.yun9.wservice.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.view.dynamic.NewDynamicActivity;
import com.yun9.wservice.view.main.MainActivity;
import com.yun9.wservice.view.myself.UserFragment;
import com.yun9.wservice.view.register.UserRegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2015/6/11.
 */
public class ClientActivity extends JupiterFragmentActivity{

    private EditText clientSearchEdt;
    private JupiterTitleBarLayout titleBarLayout;
    private EditText searchEdt;

    private LinearLayout searchLL;
    private ImageView searchFIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setClientSearchTextChanged();
        titleBarLayout = (JupiterTitleBarLayout)findViewById(R.id.client_title);
        titleBarLayout.getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                //这里跳转的对象到时候要换成表单
                intent.setClass(ClientActivity.this, NewDynamicActivity.class);
                startActivity(intent);*/
                FormActivity.start(ClientActivity.this, 1, fakeData());
            }
        });
        titleBarLayout.getTitleLeftIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client;
    }


    private void setClientSearchTextChanged(){
        clientSearchEdt = (EditText)findViewById(R.id.searchEdt);
        searchLL = (LinearLayout)findViewById(R.id.searchLL);
        searchFIV = (ImageView)findViewById(R.id.searchFIV);
        searchEdt = (EditText)findViewById(R.id.searchEdt);

        clientSearchEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setCursorVisible(true);
                searchLL.setVisibility(View.GONE);
                searchFIV.setVisibility(View.VISIBLE);

            }
        });
        clientSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchFIV.setVisibility(View.VISIBLE);
                if(s.length() == 0){
                    searchLL.setVisibility(View.VISIBLE);

                }else {
                    searchLL.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchFIV.setVisibility(View.VISIBLE);
                if(s.length() == 0){
                    searchLL.setVisibility(View.VISIBLE);

                }else {
                    searchLL.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                searchFIV.setVisibility(View.VISIBLE);
                searchLL.setVisibility(View.GONE);
            }
        });
    }

    public FormBean fakeData() {
        FormBean formBean = FormBean.getInstance();
        formBean.setTitle("测试表单");
        formBean.setKey("demoform");

        TextFormCellBean textFormCell = new TextFormCellBean();
        textFormCell.setType(TextFormCell.class.getSimpleName());
        textFormCell.setKey("testText");
        textFormCell.setDefaultValue("hello");
        textFormCell.setLabel("测试文本输入");
        textFormCell.setRequired(true);
        formBean.putCellBean(textFormCell);

        ImageFormCellBean imageFormCell = new ImageFormCellBean();
        imageFormCell.setMaxNum(3);
        imageFormCell.setKey("testImage");
        imageFormCell.setValue(new String[]{"1","2"});
        imageFormCell.setType(ImageFormCell.class.getSimpleName());
        imageFormCell.setLabel("测试图片选择");
        formBean.putCellBean(imageFormCell);

        DocFormCellBean docFormCell= new DocFormCellBean();
        docFormCell.setKey("testDoc");
        docFormCell.setType(DocFormCell.class.getSimpleName());
        //docFormCell.setValue("1");
        docFormCell.setMaxNum(3);
        docFormCell.setMinNum(1);
        docFormCell.setLabel("测试文档选择");
        formBean.putCellBean(docFormCell);

        UserFormCellBean userFormCellBean = new UserFormCellBean();
        userFormCellBean.setKey("testUser");
        userFormCellBean.setType(UserFormCell.class.getSimpleName());
        userFormCellBean.setLabel("测试选择用户");
       // userFormCellBean.setValue(userValue());
        formBean.putCellBean(userFormCellBean);

        DetailFormCellBean detailFormCell = new DetailFormCellBean();
        detailFormCell.setLabel("测试子项目");
        detailFormCell.setKey("testDetail");
        //detailFormCell.setFormBean(this.builderSubForm());
        detailFormCell.setType(DetailFormCell.class.getSimpleName());
        detailFormCell.setTitlekey("testsubinput");
        detailFormCell.setSubtitlekey("testsubinput2");
        formBean.putCellBean(detailFormCell);

        MultiSelectFormCellBean multiSelectFormCellBean = new MultiSelectFormCellBean();
        multiSelectFormCellBean.setLabel("测试多选");
        multiSelectFormCellBean.setKey("testMultiSelect");
        multiSelectFormCellBean.setType(MultiSelectFormCell.class.getSimpleName());
        multiSelectFormCellBean.setMaxNum(2);
        multiSelectFormCellBean.setMinNum(1);
        List<SerialableEntry<String,String>> list = new ArrayList<>();
        list.add(new SerialableEntry<String, String>("1", "深圳顶聚科技"));
        list.add(new SerialableEntry<String, String>("2", "深圳顶聚科技2"));
        multiSelectFormCellBean.setValue(list);
        //multiSelectFormCellBean.setOptionMap(this.builderOptions());
        formBean.putCellBean(multiSelectFormCellBean);
        return formBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        FormBean formBean = (FormBean) data.getSerializableExtra("form");
        formBean.getCellBeanValue("testText");
    }
}
