package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/30.
 */
public class UserFormCell extends FormCell{

    public static final String PARAM_KEY_TYPE = "type";
    public static final String PARAM_KEY_VALUE = "value";

    private JupiterEditIco jupiterEditIco;

    private JupiterEditAdapter adapter;

    private UserFormCellBean cellBean;

    private List<JupiterEditableView> itemList;

    private boolean edit;

    private Context context;

    private FormUtilFactory.LoadValueHandler loadUserValueHandler;
    private FormUtilFactory.LoadValueHandler loadOrgValueHandler;

    public UserFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (UserFormCellBean) cellBean;
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_user, null);
        jupiterEditIco = (JupiterEditIco) rootView.findViewById(R.id.edit_ico);
        itemList = new ArrayList<>();
        loadUserValueHandler = findLoadValueHandler(FormUtilFactory.LoadValueHandler.TYPE_USER);
        loadOrgValueHandler = findLoadValueHandler(FormUtilFactory.LoadValueHandler.TYPE_ORG);
        this.build();
        setupEditIco();
        return rootView;
    }

    private void build() {
        jupiterEditIco.getRowStyleSutitleLayout().getTitleTV().setText(cellBean.getLabel());
    }

    private void setupEditIco() {
        appendAddButton();
        adapter = new BasicJupiterEditAdapter(itemList);
        jupiterEditIco.setAdapter(adapter);
        this.restore();
    }

    private void appendAddButton() {
        JupiterTextIco item = new JupiterTextIcoWithoutCorner(this.context);
        item.setTitle("添加");
        item.getItemImage().setLayoutParams(new FrameLayout.LayoutParams(PublicHelp.dip2px(this.context, 40), PublicHelp.dip2px(this.context, 40)));
        item.setImage("drawable://" + R.drawable.add_user);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceUserOrDept();
            }
        });
        itemList.add(item);
    }

    private void restore() {
        if (cellBean.getValue() != null) {
            List<Map<String, String>> mapList = new ArrayList<>((List<Map<String, String>>) cellBean.getValue());
            reload(mapList);
        }
    }

    private JupiterTextIco createItem(Map<String,String> tag) {
        final JupiterTextIco item = new JupiterTextIco(this.context);
        item.getBadgeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItm(item);
            }
        });
        item.getItemImage().setLayoutParams(new FrameLayout.LayoutParams(PublicHelp.dip2px(this.context, 40), PublicHelp.dip2px(this.context, 40)));
        item.setTag(tag);
        item.setCornerImage(R.drawable.icn_delete);
        itemList.add(item);
        return item;
    }


    private void deleteItm(JupiterTextIco item) {
        itemList.remove(item);
        adapter.notifyDataSetInvalidated();
    }

    /**
     * 激活选择用户Activity
     */
    private void choiceUserOrDept() {
        FormUtilFactory.BizExecutor executor = findBizExecutor(FormUtilFactory.BizExecutor.TYPE_SELECT_USER_OR_DEPT);
        if (executor != null) {
            executor.execute((FormActivity) this.context,this);
        }
    }

    public void reload(List<Map<String,String>> uodMaps) {
        itemList.clear();
        appendAddButton();
        for (int i = 0; i < uodMaps.size(); i++) {
            final JupiterTextIco item = createItem(uodMaps.get(i));
            if (uodMaps.get(i).get(PARAM_KEY_TYPE).equals("user")
                    && loadUserValueHandler != null) {
                loadUserValueHandler.load(uodMaps.get(i).get(PARAM_KEY_VALUE), new FormUtilFactory.LoadValueCompleted() {
                    @Override
                    public void callback(Object data) {
                        User user = (User) data;
                        item.setTitle(user.getName());
                        item.setImage(user.getHeaderfileid());
                    }
                });
            } else if (uodMaps.get(i).get(PARAM_KEY_TYPE).equals("org")
                    && loadOrgValueHandler != null){
                loadOrgValueHandler.load(uodMaps.get(i).get(PARAM_KEY_VALUE), new FormUtilFactory.LoadValueCompleted() {
                    @Override
                    public void callback(Object data) {
                        Org org = (Org) data;
                        item.setTitle(org.getName());
                        item.setImage("drawable://"+R.drawable.fileicon);
                    }
                });
            }
            item.showCorner();
        }
        adapter.notifyDataSetInvalidated();
    }

    @Override
    public void edit(boolean edit) {
        this.edit = edit;
        jupiterEditIco.edit(edit);
    }

    @Override
    public Object getValue() {
        List<Map<String,String>> uodMaps = new ArrayList<>();
        JupiterEditableView item;
        // 过滤第一个，添加按钮
        for (int i = 1; i < itemList.size(); i++) {
            item = itemList.get(i);
            uodMaps.add((Map<String, String>) item.getTag());
        }
        return uodMaps;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }

    @Override
    public String validate() {
        List<Map<String,String>> uodMaps = (List<Map<String, String>>) getValue();
        if (cellBean.isRequired()
                && uodMaps.size() == 0){
            return "请选择 " + cellBean.getLabel();
        }
        if (cellBean.getMinNum() > 0
                && uodMaps.size() < cellBean.getMinNum()){
            return cellBean.getLabel()+" 至少需要 "+cellBean.getMinNum()+" 个";
        }
        if (cellBean.getMaxNum() > 0 &&
                uodMaps.size() > cellBean.getMaxNum()){
            return cellBean.getLabel() + " 至多只能包含 "+cellBean.getMaxNum()+" 个";
        }
        return null;
    }

    @Override
    public void reload(FormCellBean bean) {
        this.cellBean = (UserFormCellBean) bean;
    }
}
