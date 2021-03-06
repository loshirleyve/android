package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yun9.jupiter.R;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.CustomCallbackActivity;
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

    private List<FileBean> fileBeans;

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
        restore((String[]) cellBean.getValue());
    }

    public void restore(List<FileBean> docs) {
        this.fileBeans = docs;
        List<String> ids =  new ArrayList<String>();
        for (FileBean fileBean : fileBeans){
            if (FileBean.FILE_STORAGE_TYPE_LOCAL.equals(fileBean.getStorageType())){
                ids.add(fileBean.getFilePath());
            } else if (FileBean.FILE_STORAGE_TYPE_YUN.equals(fileBean.getStorageType())){
                ids.add(fileBean.getId());
            }
        }
        restore(ids.toArray(new String[0]));
    }

    public void restore(String[] ids) {
        itemList.clear();
        for (int i = 0; i < ids.length; i++) {
            createItem(ids[i]);
        }
        adapter.notifyDataSetInvalidated();
    }

    private void startUploadDoc() {
        FormUtilFactory.BizExecutor bizExecutor =
                                        findBizExecutor(FormUtilFactory.BizExecutor.TYPE_SELECT_DOC);
        if (bizExecutor != null){
            bizExecutor.execute((CustomCallbackActivity) this.context,this);
        }

    }

    private JupiterItem createItem(String id) {
        final JupiterItem item = new JupiterItem(this.context);
        CacheFile cacheFile = FileCache.getInstance().getFile(id);
        if (cacheFile != null){
            item.setTitle(cacheFile.getName());
        }
        item.setImage(id);
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
    public String getStringValue() {
        String[] ids = (String[]) getValue();
        StringBuffer sb = new StringBuffer();
        for (String id : ids) {
            sb.append(",").append(id);
        }
        if (sb.length() > 0){
            return sb.substring(1);
        }
        return "";
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

    public List<FileBean> getFileBeans() {
        return fileBeans;
    }

    public DocFormCell setFileBeans(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
        return this;
    }
}
