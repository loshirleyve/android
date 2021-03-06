package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class DetailFormCellActivity extends JupiterActivity {

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout formLL;

    private DetailFormCellBean cellBean;

    private Form form;

    private List<FormBean> formBeans;

    private FormCommand command;

    public static void start(Activity activity,int requestCode ,DetailFormCellBean cellBean,List<FormBean> formBeans) {
        Intent intent = new Intent(activity, DetailFormCellActivity.class);
        intent.putExtra("cellBean", cellBean);
        intent.putExtra("forms", (Serializable) formBeans);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.detail_cell_page);
        cellBean = (DetailFormCellBean) this.getIntent().getSerializableExtra("cellBean");
        formBeans = (List<FormBean>) this.getIntent().getSerializableExtra("forms");
        form = Form.getInstance(cellBean.getFormBean());
        this.initView();
        this.builder();
        this.rebuildContainer();
    }

    private void initView(){
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        formLL = (LinearLayout) this.findViewById(R.id.formpage);
    }

    private void builder() {
        if (!AssertValue.isNotNull(form)) {
            return;
        }

        //设置返回键行为
        this.titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("forms", (Serializable) formBeans);
                DetailFormCellActivity.this.setResult(RESPONSE_CODE.COMPLETE, getIntent());
                DetailFormCellActivity.this.finish();
            }
        });

        //设置新增按钮
        this.titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new FormCommand();
                command.setFormBean(DetailFormCellActivity.this.cellBean.getFormBean());
                FormActivity.start(DetailFormCellActivity.this, command);

            }
        });

        //设置标题
        this.titleBarLayout.getTitleTv().setText(form.getTitle());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == command.getRequestCode()
                && resultCode == FormActivity.RESPONSE_CODE.COMPLETE) {
            FormBean formBean = (FormBean) data.getSerializableExtra("form");
            appendItem(formBeans.size(),formBean);
            formBeans.add(formBean);
        }
    }

    private void appendItem(final int position,FormBean nfb) {
        JupiterRowStyleSutitleLayout sutitleLayout = new JupiterRowStyleSutitleLayout(this);
        sutitleLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sutitleLayout.setShowMainImage(false);
        sutitleLayout.setShowTime(false);
        sutitleLayout.setShowArrow(false);
        Object titleText = "";
        Object subTitleText = "";
        if (AssertValue.isNotNullAndNotEmpty(cellBean.getTitlekey())){
            titleText = nfb.getCellBeanValue(cellBean.getTitlekey());
        }
        if (AssertValue.isNotNullAndNotEmpty(cellBean.getSubtitlekey())){
            subTitleText = nfb.getCellBeanValue(cellBean.getSubtitlekey());
        }
        sutitleLayout.setTitleText((String) titleText);
        sutitleLayout.setSubTitleText((String) subTitleText);
        formLL.addView(sutitleLayout);
        sutitleLayout.getArrowRightIV().setVisibility(View.VISIBLE);
        sutitleLayout.getArrowRightIV().getLayoutParams().width = PublicHelp.dip2px(this, 30);
        sutitleLayout.getArrowRightIV().getLayoutParams().height = PublicHelp.dip2px(this, 30);
        sutitleLayout.getArrowRightIV().setImageDrawable(getResources().getDrawable(R.drawable.icn_delete));
        sutitleLayout.getArrowRightIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

    }

    private void removeItem(int position) {
        formBeans.remove(position);
        rebuildContainer();
    }

    private void rebuildContainer() {
        formLL.removeAllViews();
        FormBean formBean;
        for (int i = 0;i < formBeans.size();i++) {
            formBean = formBeans.get(i);
            appendItem(i,formBean);
        }
    }

    public class RESPONSE_CODE {
        public static final int COMPLETE = 100;
        public static final int CANCEL = 200;
    }

}
