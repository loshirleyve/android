package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterBadgeView;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditItem;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        if (cellBean.getValue() == null) {
            return;
        }
        String[] ids = ((String)cellBean.getValue()).split(",");
        for (int i = 0; i < ids.length; i++) {
           createItem(ids[i]);
        }
        adapter.notifyDataSetChanged();
    }

    private void startUploadDoc() {
        createItem("1");
        adapter.notifyDataSetChanged();
    }

    private JupiterItem createItem(String id) {
        final JupiterItem item = new JupiterItem(this.context);
        item.setTitle("顶聚科技"+id+".doc");
        item.setImage("drawable://" + R.drawable.fileicon);
        item.setTag(id);
        item.setCornerImage(R.drawable.icn_delete);
        item.getBadgeView().setOnClickListener(new View.OnClickListener() {
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void edit(boolean edit) {
        editItem.getRowStyleTitleLayout().getArrowRightIV().setEnabled(edit);
        editItem.edit(edit);
    }

    @Override
    public Object getValue() {
        StringBuffer sb = new StringBuffer();
        for (JupiterEditableView item : itemList) {
            sb.append(item.getTag()).append(",");
        }
        if (sb.length() > 0) {
            return sb.substring(0,sb.length() - 1);
        }
        return sb;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }

}
