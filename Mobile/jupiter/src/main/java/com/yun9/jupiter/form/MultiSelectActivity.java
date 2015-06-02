package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 */
public class MultiSelectActivity extends JupiterFragmentActivity{

    public static final String RETURN_PARAM = "selectedOptionMap";

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout optionContainer;

    private MultiSelectFormCellBean cellBean;

    private Map<String,String> optionMap;

    private Map<String,String> selectedOptionMap;

    public static void start(Activity activity,int requestCode ,MultiSelectFormCellBean cellBean,Map<String,String> selectedOptionMap) {
        Intent intent = new Intent(activity, MultiSelectActivity.class);
        intent.putExtra("cellBean", cellBean);
        intent.putExtra("selectedOptionMap", (Serializable) selectedOptionMap);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cellBean = (MultiSelectFormCellBean) this.getIntent().getSerializableExtra("cellBean");
        selectedOptionMap = (Map<String, String>) this.getIntent().getSerializableExtra("selectedOptionMap");
        if (selectedOptionMap == null) {
            selectedOptionMap = new HashMap<>();
        }
        initView();
        builder();
    }

    private void initView() {
        this.titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        this.optionContainer = (LinearLayout) this.findViewById(R.id.optionContainer);
    }

    private void builder() {
        this.titleBarLayout.getTitleTv().setText(cellBean.getLabel());
        if (cellBean.getMaxNum() == 1){
            this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_single_choice);
        } else {
            this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_multi_choice);
        }
        
        this.titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        getIntent().putExtra(RETURN_PARAM, (Serializable) this.selectedOptionMap);
        this.setResult(RESPONSE_CODE.COMPLETE,getIntent());
        this.finish();
    }

    private void rebuildContainer() {
        Map<String,String> options = null;
        if (cellBean.getOptionMap() != null
                && !cellBean.getOptionMap().isEmpty()) {
            options = cellBean.getOptionMap();
        }else if (AssertValue.isNotNullAndNotEmpty(cellBean.getCtrlCode())) {

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.multi_select_cell_page;
    }

    public class RESPONSE_CODE {
        public static final int COMPLETE = 100;
        public static final int CANCEL = 200;
    }
}
