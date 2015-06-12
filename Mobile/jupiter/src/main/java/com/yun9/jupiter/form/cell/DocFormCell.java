package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditItem;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class DocFormCell extends FormCell {

    private Context context;

    private JupiterEditItem editItem;

    private List<JupiterEditableView> itemList;

    private DocFormCellBean cellBean;

    private JupiterEditAdapter adapter;

    public DocFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (DocFormCellBean) cellBean;
    }


    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_doc,null);
        editItem = (JupiterEditItem) rootView.findViewById(R.id.edit_item);
        this.buildView();
        setupEditItem();
        return rootView;
    }

    private void setupEditItem() {
        itemList = new ArrayList<>();
        adapter = new BasicJupiterEditAdapter(itemList);
        editItem.setAdapter(adapter);
        restore();
    }

    private void buildView() {
        editItem.getRowStyleTitleLayout().getTitleTV().setText(cellBean.getLabel());
        editItem.getRowStyleTitleLayout().getArrowRightIV().getLayoutParams().width = PublicHelp.dip2px(this.context,50);
        editItem.getRowStyleTitleLayout().getArrowRightIV().getLayoutParams().height = PublicHelp.dip2px(this.context,30);
        editItem.getRowStyleTitleLayout().getArrowRightIV().setImageDrawable(this.context.getResources().getDrawable(R.drawable.tianjia));
        bindEvent();
    }

    private void bindEvent() {
        editItem.getRowStyleTitleLayout().getArrowRightIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadDoc();
            }
        });
    }

    private void restore() {
        itemList.clear();
        if (cellBean.getValue() == null) {
            return;
        }
        String[] ids = (String[]) cellBean.getValue();
        for (int i = 0; i < ids.length; i++) {
           createItem(ids[i]);
        }
        adapter.notifyDataSetInvalidated();
    }

    private void startUploadDoc() {
        createItem("1");
        adapter.notifyDataSetInvalidated();
    }

    private JupiterItem createItem(String id) {
        final JupiterItem item = new JupiterItem(this.context);
        item.setTitle("顶聚科技"+id+".doc");
        item.setImage("drawable://" + R.drawable.fileicon);
        item.setTag(id);
        item.setCornerImage(R.drawable.icn_delete);
        item.getArrowIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItm(item);
            }
        });
        itemList.add(item);
        return item;
    }

    private void deleteItm(JupiterItem item) {
        itemList.remove(item);
        adapter.notifyDataSetInvalidated();
    }

    @Override
    public void edit(boolean edit) {
        editItem.getRowStyleTitleLayout().getArrowRightIV().setEnabled(edit);
        editItem.edit(edit);
    }

    @Override
    public Object getValue() {
        List<String> ids = new ArrayList<>();
        for (JupiterEditableView item : itemList) {
            ids.add((String) item.getTag());
        }
        return ids.toArray(new String[0]);
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
        this.cellBean = (DocFormCellBean) bean;
    }

}
