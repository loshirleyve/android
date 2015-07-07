package com.yun9.wservice.view.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.CtrlCode;

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

    private MultiSelectCommand command;

    public static void start(Activity activity, MultiSelectCommand command) {
        Intent intent = new Intent(activity,MultiSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MultiSelectCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.title_bar);
        listView = (ListView) this.findViewById(R.id.listview);
        submitTV = (TextView) this.findViewById(R.id.submit);
        initData();
        this.build();
        this.loadOptions();
    }

    private void initData() {
        command = (MultiSelectCommand) getIntent().getSerializableExtra(MultiSelectCommand.PARAM_COMMAND);
        options = command.getOptions();
        if (options == null) {
            options = new ArrayList<>();
        }
        selectedList = command.getSelectedList();
        if (selectedList == null) {
            selectedList = new ArrayList<>();
        }
        ctrlCode = command.getCtrlCode();
        boolean isCancelable = command.isCancelable();
        if (isCancelable) {
            options.add(0, new SerialableEntry<String, String>(CACEL_ITEM_ID, CACEL_ITEM_NAME));
        }

    }

    private void loadOptions() {
        if (!AssertValue.isNotNullAndNotEmpty(ctrlCode)) {
            this.setupAdapter();
            return;
        }
        ResourceFactory resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
        Resource resource = resourceFactory.create("QueryCtrlCode");
        resource.param("defno", ctrlCode);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getPayload() == null) {
                    return;
                }
                List<CtrlCode> ctrlCodes = (List<CtrlCode>) response.getPayload();
                CtrlCode ctrlCode;
                for (int i = 0; i < ctrlCodes.size(); i++) {
                    ctrlCode = ctrlCodes.get(i);
                    options.add(new SerialableEntry<String, String>(ctrlCode.getNo()
                            , ctrlCode.getName()));
                }
            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {
                setupAdapter();
            }
        });
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
        if (command.getMaxNum() > 0){
            titleBarLayout.getTitleRightTv().setTextSize(13);
            titleBarLayout.getTitleRightTv().setText("最多选择"+command.getMaxNum()+"个");
        } else {
            titleBarLayout.getTitleRightTv().setVisibility(View.GONE);
        }
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

        if (command.getMaxNum() > 0
                && command.getMaxNum() != 1
                && selectedList.size() >= command.getMaxNum()){
            showToast("最多选择"+command.getMaxNum()+"个");
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

        // 如果选中则取消，反之则选中
        if (selectedList.indexOf(param) != -1) {
            itemLayout.select(false);
            selectedList.remove(param);
        } else {
            itemLayout.select(true);
            selectedList.add(param);
        }

        // 如果是单选
        if (command.getMaxNum() == 1){
            selectedList.clear();
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
