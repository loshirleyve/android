package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.yun9.jupiter.R;
import com.yun9.jupiter.form.DetailFormCellActivity;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class DetailFormCell extends FormCell {

    private JupiterRowStyleTitleLayout titleView;

    private Context context;

    private DetailFormCellBean cellBean;

    List<FormBean> detailFormBeans;

    public DetailFormCell(FormCellBean cellBean) {
        super(cellBean);
        detailFormBeans = new ArrayList<>();
        this.cellBean = (DetailFormCellBean) cellBean;
    }

    private void restore() {
        if (cellBean.getValue() == null){
            return;
        }
        List<Map<String,Object>> maps = (List<Map<String, Object>>) cellBean.getValue();
        FormBean bean;
        for (int i = 0; i < maps.size(); i++) {
            bean = new FormBean();
            bean.setValue(maps.get(i));
            detailFormBeans.add(bean);
        }
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_detail, null);
        titleView = (JupiterRowStyleTitleLayout) rootView.findViewById(R.id.title);
        titleView.getTitleTV().setText(cellBean.getLabel());
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetailForm();
            }
        });
        this.restore();
        return rootView;
    }

    @Override
    public void edit(boolean edit) {
        titleView.setEnabled(edit);
    }

    @Override
    public Object getValue() {
        List<Map<String,Object>> maps = new ArrayList<>();
        if (detailFormBeans.size() > 0) {
            for (FormBean bean : detailFormBeans) {
                maps.add(bean.getValue());
            }
            return maps;
        }
        return null;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }

    /**
     * 进入二级表单
     */
    private void gotoDetailForm() {
        FormActivity formActivity = (FormActivity) this.context;
        int requestCode = formActivity.addActivityCallback(new FormActivity.IFormActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                if (resultCode == DetailFormCellActivity.RESPONSE_CODE.COMPLETE) {
                    detailFormBeans = (List<FormBean>) data.getSerializableExtra("forms");
                }
            }
        });
        DetailFormCellActivity.start(formActivity, requestCode, cellBean,detailFormBeans);
    }

}