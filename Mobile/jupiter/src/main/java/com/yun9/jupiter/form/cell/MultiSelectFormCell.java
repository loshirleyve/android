package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterTag;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/1.
 */
public class MultiSelectFormCell extends FormCell {

    private JupiterEditIco editIco;

    private MultiSelectFormCellBean cellBean;

    private Context context;

    private List<JupiterEditableView> itemList;

    private JupiterEditAdapter adapter;

    public MultiSelectFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (MultiSelectFormCellBean) cellBean;
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_multi_select, null);
        editIco = (JupiterEditIco) rootView.findViewById(R.id.edit_ico);
        this.buildView();
        this.setupEditIco();
        return rootView;
    }

    private void buildView() {
        editIco.getRowStyleSutitleLayout().getTitleTV().setText(cellBean.getLabel());
        editIco.getRowStyleSutitleLayout().getArrowRightIV().setVisibility(View.VISIBLE);
        editIco.getRowStyleSutitleLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    private void setupEditIco() {
        itemList = new ArrayList<>();
        adapter = new BasicJupiterEditAdapter(itemList);
        editIco.setAdapter(adapter);
        restore();
    }

    private void restore() {
        if (cellBean.getValue() != null) {
            Map<String,String> selectedMap = (Map<String, String>) cellBean.getValue();
            reload(selectedMap);
        }
    }

    private void openActivity() {
        // 打开选择器
    }

    private void reload(Map<String,String> map) {
        itemList.clear();
        Iterator<String> iterator = map.keySet().iterator();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();
            createItem(key,map.get(key));
        }
        adapter.notifyDataSetChanged();
    }

    private void createItem(String key,String label) {
        JupiterTag item = new JupiterTag(this.context);
        item.setTitle(label);
        item.setTag(Collections.singletonMap(key,label));
        itemList.add(item);
    }

    @Override
    public void edit(boolean edit) {
        editIco.edit(edit);
    }

    @Override
    public Object getValue() {
        Map<String,String> map = new HashMap<>();
        for (JupiterEditableView view : itemList) {
            map.putAll((Map<? extends String, ? extends String>) view.getTag());
        }
        return map;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
