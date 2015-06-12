package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterTag;

import java.util.ArrayList;
import java.util.List;

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
            List<SerialableEntry<String,String>> selectedList = (List<SerialableEntry<String, String>>) cellBean.getValue();
            reload(selectedList);
        }
    }

    private void openActivity() {
       FormUtilFactory.BizExecutor executor = findBizExecutor(FormUtilFactory.BizExecutor.TYPE_MULTI_SELECT);
        if (executor != null) {
            FormActivity activity = (FormActivity) this.context;
            executor.execute(activity, this);
        }
    }

    public void reload(List<SerialableEntry<String,String>> list) {
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

    @Override
    public String validate() {
        if (cellBean.isRequired()
                && itemList.size() == 0){
            return "请选择 " + cellBean.getLabel();
        }
        if (cellBean.getMinNum() > 0
                && itemList.size() < cellBean.getMinNum()){
            return cellBean.getLabel()+" 至少需要 "+cellBean.getMinNum()+" 个";
        }
        if (cellBean.getMaxNum() > 0 &&
                itemList.size() > cellBean.getMaxNum()){
            return cellBean.getLabel() + " 至多只能包含 "+cellBean.getMaxNum()+" 个";
        }
        return null;
    }

    @Override
    public void reload(FormCellBean bean) {
        this.cellBean = (MultiSelectFormCellBean) bean;
    }
}
