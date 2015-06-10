package com.yun9.wservice.view.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.wservice.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/8.
 */
public class MultiSelectActivity extends JupiterFragmentActivity {

    public static final String CACEL_ITEM_ID = "-1";
    public static final String CACEL_ITEM_NAME = "取消选择";

    private JupiterTitleBarLayout titleBarLayout;
    private ListView listView;
    private TextView submitTV;
    private JupiterAdapter adapter;
    private String ctrlCode;
    private List<SerialableEntry<String, String>> selectedList;
    private List<SerialableEntry<String, String>> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.title_bar);
        listView = (ListView) this.findViewById(R.id.listview);
        submitTV = (TextView) this.findViewById(R.id.submit);
        initData();
        this.build();
        this.setupAdapter();
    }

    private void initData() {

        Serializable tmp;
        tmp = getIntent().getSerializableExtra("options");
        if (tmp != null) {
            options = (List<SerialableEntry<String, String>>) tmp;
        } else {
            options = new ArrayList<>();
        } 
        tmp = getIntent().getSerializableExtra("selectedList");
        if (tmp != null) {
            selectedList = (List<SerialableEntry<String, String>>) tmp;
        } else {
            selectedList = new ArrayList<>();
        }
        tmp = getIntent().getStringExtra("ctrlCode");
        if (tmp != null) {
            ctrlCode = (String) tmp;
        }
        boolean isCancelable = getIntent().getBooleanExtra("isCancelable", false);
        if (isCancelable) {
            options.add(0, new SerialableEntry<String, String>(CACEL_ITEM_ID,CACEL_ITEM_NAME));
        }
    }
    
    private void build() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherResult();
                setResult(Constants.ACTIVITY_RETURN.SUCCESS, getIntent());
                MultiSelectActivity.this.finish();
            }
        };
        titleBarLayout.getTitleLeft().setOnClickListener(clickListener);
        submitTV.setOnClickListener(clickListener);
    }

    private void setupAdapter() {
        adapter = new JupiterAdapter() {
            @Override
            public int getCount() {
                return options.size();
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
            public View getView(int position, View convertView, ViewGroup parent) {
                JupiterRowStyleTitleLayout item = null;
                final SerialableEntry<String, String> param = options.get(position);
                if (convertView == null) {
                    final JupiterRowStyleTitleLayout temp = new JupiterRowStyleTitleLayout(MultiSelectActivity.this);
                    temp.getMainIV().setVisibility(View.GONE);
                    temp.setSelectMode(true);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickItem(temp, param);
                        }
                    });
                    temp.getArrowRightIV().setVisibility(View.GONE);
                    item = temp;
                    convertView = temp;
                } else {
                    item = (JupiterRowStyleTitleLayout) convertView;
                }
                item.getTitleTV().setText(param.getValue());
                item.setTag(param);
                if (selectedList.indexOf(param) != -1) {
                    item.select(true);
                } else {
                    item.select(false);
                }
                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }

    private void onClickItem(JupiterRowStyleTitleLayout itemLayout,SerialableEntry<String, String> param) {

        if (param.getKey().equals(CACEL_ITEM_ID)) {
            selectedList.clear();
            selectedList.add(param);
            adapter.notifyDataSetChanged();
            return;
        }

        // 删除取消的选择
        int len = selectedList.size();
        for (int i = 0; i < len; i++) {
            if (selectedList.get(i).getKey().equals(CACEL_ITEM_ID)){
                selectedList.remove(selectedList.get(i));
                break;
            }
        }

        if (selectedList.indexOf(param) != -1) {
            itemLayout.select(false);
            selectedList.remove(param);
        } else {
            itemLayout.select(true);
            selectedList.add(param);
        }
        adapter.notifyDataSetChanged();
    }

    private void gatherResult() {
        int len = selectedList.size();
        for (int i = 0; i < len; i++) {
            if (selectedList.get(i).getKey().equals(CACEL_ITEM_ID)){
                selectedList.remove(selectedList.get(i));
                break;
            }
        }
        Intent intent = getIntent();
        intent.putExtra("selectedList", (Serializable) selectedList);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_multi_select;
    }
}
