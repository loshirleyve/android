package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditIcoAdapter;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithCorner;
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

    private JupiterEditIcoAdapter adapter;

    private UserFormCellBean cellBean;

    private List<JupiterTextIco> itemList;

    private boolean edit;

    private Context context;

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
        this.build();
        setupEditIco();
        return rootView;
    }

    private void build() {
        jupiterEditIco.getRowStyleSutitleLayout().getTitleTV().setText(cellBean.getLabel());
    }

    private void setupEditIco() {
        appendAddButton();
        adapter = new JupiterEditIcoAdapter() {

            @Override
            public int getCount() {
                return itemList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public JupiterTextIco getItemView(int position, View convertView, ViewGroup parent) {
                itemList.get(position).setEnabled(edit);
                return itemList.get(position);
            }
        };
        jupiterEditIco.setAdapter(adapter);
        this.restore();
    }

    private void appendAddButton() {
        JupiterTextIco item = new JupiterTextIcoWithoutCorner(this.context);
        item.setTitle("添加");
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
            JupiterTextIco item;
            for (int i = 0; i < mapList.size(); i++) {
                item = createItem(i+1,mapList.get(i));// 前面已经有个addButton了
                item.setTitle("成员名称");
                item.setImage(mapList.get(i).get(PARAM_KEY_VALUE));
                item.showCorner();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private JupiterTextIco createItem(final int position,Map<String,String> tag) {
        // 测试
        if (position%2 == 0) {
            final JupiterTextIco item = new JupiterTextIcoWithCorner(this.context);
            item.setTag(tag);
            item.setCornerImage(R.drawable.newpost);
            itemList.add(item);
            return item;
        }
        final JupiterTextIco item = new JupiterTextIco(this.context);
        item.getBadgeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItm(item);
            }
        });
        item.setTag(tag);
        item.setCornerImage(R.drawable.icn_delete);
        itemList.add(item);
        return item;
    }


    private void deleteItm(JupiterTextIco item) {
        itemList.remove(item);
        adapter.notifyDataSetChanged();
    }

    /**
     * 激活选择用户Activity
     */
    private void choiceUserOrDept() {
        Map<String,String> map = new HashMap<>();
        map.put(PARAM_KEY_TYPE,UserFormCellBean.MODE.USER+"");
        map.put(PARAM_KEY_VALUE, "drawable://" + R.drawable.user_head);
        JupiterTextIco item = createItem(itemList.size(),map);
        item.setImage(map.get(PARAM_KEY_VALUE))
                                    .setTitle("成员名称").showCorner();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void edit(boolean edit) {
        this.edit = edit;
        jupiterEditIco.edit(edit);
    }

    @Override
    public Object getValue() {
        List<Map<String,String>> uodMaps = new ArrayList<>();
        JupiterTextIco item;
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
}
