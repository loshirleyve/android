package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 */
public class MultiSelectActivity extends JupiterFragmentActivity{

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout optionContainer;

    private MultiSelectFormCellBean cellBean;

    private Map<String,String> optionMap;

    private Map<String,String> selectedOptionMap;

    public static void start(Activity activity,int requestCode ,MultiSelectFormCellBean cellBean,Map<String,String> selectedOptionMap) {
        Intent intent = new Intent(activity, DetailFormCellActivity.class);
        intent.putExtra("cellBean", cellBean);
        intent.putExtra("selectedOptionMap", (Serializable) selectedOptionMap);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cellBean = (MultiSelectFormCellBean) this.getIntent().getSerializableExtra("cellBean");
        selectedOptionMap = (Map<String, String>) this.getIntent().getSerializableExtra("selectedOptionMap");
        initView();
        builder();
    }

    private void initView() {
        this.titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        this.optionContainer = (LinearLayout) this.findViewById(R.id.optionContainer);
    }

    private void builder() {

    }

    private void rebuildContainer() {

    }

    @Override
    protected int getContentView() {
        return R.layout.multi_select_cell_page;
    }
}
