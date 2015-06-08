package com.yun9.jupiter.form.cell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.util.Constants;
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

    private FormUtilFactory.BizExecutor executor;

    public MultiSelectFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (MultiSelectFormCellBean) cellBean;
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_multi_select, null);
        editIco = (JupiterEditIco) rootView.findViewById(R.id.edit_ico);
        executor = FormUtilFactory.getInstance().getBizExcutor(FormUtilFactory.BizExecutor.TYPE_MULTI_SELECT);
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
            List<SerialableEntry<String,String>> selectedList = (List<SerialableEntry<String, String>>) cellBean.getValue();
            reload(selectedList);
        }
    }

    private void openActivity() {
        if (executor != null) {
            Map<String,Object> config = new HashMap<>();
            config.put("isCacelable",true);
            config.put("ctrlCode",cellBean.getCtrlCode());
            config.put("options", cellBean.getOptionMap());
            config.put("selectedList", getValue());
            FormActivity activity = (FormActivity) this.context;
            int requestCode = activity.addActivityCallback(new FormActivity.IFormActivityCallback() {
                @Override
                public void onActivityResult(int resultCode, Intent data) {
                    if (resultCode == Constants.ACTIVITY_RETURN.SUCCESS) {
                        reload((List<SerialableEntry<String, String>>) data.getSerializableExtra("selectedList"));
                    }
                }
            });
            executor.execute(activity,requestCode,config);
        }
    }

    private void reload(List<SerialableEntry<String,String>> list) {
        itemList.clear();
        for (int i = 0; i < list.size(); i++) {
            createItem(list.get(i).getKey(),list.get(i).getValue());
        }
        adapter.notifyDataSetChanged();
    }

    private void createItem(String key,String label) {
        JupiterTag item = new JupiterTag(this.context);
        item.setTitle(label);
        item.setTag(new SerialableEntry<String,String>(key,label));
        itemList.add(item);
    }

    @Override
    public void edit(boolean edit) {
        editIco.edit(edit);
    }

    @Override
    public Object getValue() {
        List<SerialableEntry<String,String>> list = new ArrayList<>();
        for (JupiterEditableView view : itemList) {
            list.add((SerialableEntry<String, String>) view.getTag());
        }
        return list;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
