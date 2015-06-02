package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 */
public class MultiSelectFormCell extends FormCell {

    private JupiterRowStyleSutitleLayout titleView;

    private MultiSelectFormCellBean cellBean;

    private Map<String, String> selectedMap;

    private Context context;

    public MultiSelectFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (MultiSelectFormCellBean) cellBean;
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_multi_select, null);
        titleView = (JupiterRowStyleSutitleLayout) rootView.findViewById(R.id.title);
        titleView.getTitleTV().setText(cellBean.getLabel());
        titleView.getSutitleTv().setVisibility(View.GONE);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.restore();
        return rootView;
    }

    private void restore() {
        if (cellBean.getValue() != null) {
            selectedMap = (Map<String, String>) cellBean.getValue();
        }
        rebuild();
    }

    private void rebuild() {
        titleView.getSutitleTv().setVisibility(View.GONE);
        titleView.getHotNitoceTV().setVisibility(View.VISIBLE);
        titleView.getHotNitoceTV().setBackgroundColor(this.context.getResources().getColor(R.color.whites));
        titleView.getHotNitoceTV().setText("未选择");
        titleView.getHotNitoceTV().setTextColor(this.context.getResources().getColor(R.color.red));
        if (selectedMap != null && !selectedMap.isEmpty()) {
            titleView.getHotNitoceTV().setText("已选");
            titleView.getHotNitoceTV().setTextColor(this.context.getResources().getColor(R.color.drak));
            titleView.showSubItems(selectedMap.values());
        }
    }

    @Override
    public void edit(boolean edit) {
        titleView.setEnabled(edit);
    }

    @Override
    public Object getValue() {
        return selectedMap;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
