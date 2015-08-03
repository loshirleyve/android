package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.RechargeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择充值方式界面
 * Created by huangbinglong on 15/6/23.
 */
public class RechargeChoiceWaysActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_ways_lv)
    private ListView listView;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLL;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private RechargeChoiceWaysCommand command;

    private List<RechargeType> rechargeTypes;

    private RechargeType selectedType;

    public static void start(Activity activity,RechargeChoiceWaysCommand command) {
        Intent intent = new Intent(activity,RechargeChoiceWaysActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RechargeChoiceWaysCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (RechargeChoiceWaysCommand) getIntent()
                                                .getSerializableExtra(
                                                        RechargeChoiceWaysCommand.PARAM_COMMAND);
        buildView();
        loadData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge_choice_way;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeChoiceWaysActivity.this.finish();
            }
        });

        confirmLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        listView.setAdapter(adapter);
    }

    private void confirm() {
        Intent intent = new Intent();
        if (selectedType != null) {
            intent.putExtra("type", (Serializable) selectedType);
        }
        this.setResult(JupiterCommand.RESULT_CODE_OK, intent);
        this.finish();
        return;
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryRechargeTypeService");
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                rechargeTypes = (List<RechargeType>) response.getPayload();
            }

            @Override
            public void onFailure(Response response) {
                rechargeTypes = null;
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                registerDialog.dismiss();
            }
        });
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (rechargeTypes != null) {
                return rechargeTypes.size();
            }
            return 0;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            JupiterRowStyleSutitleLayout sutitleLayout;
            if (convertView == null) {
                sutitleLayout = new JupiterRowStyleSutitleLayout(RechargeChoiceWaysActivity.this);
                sutitleLayout.setSelectMode(true);
                sutitleLayout.getArrowRightIV().setVisibility(View.GONE);
                sutitleLayout.getTitleTV().setText(rechargeTypes.get(position).getRechargename());
                sutitleLayout.getSutitleTv().setText(R.string.recharge_type_hint);
                sutitleLayout.getTimeTv().setVisibility(View.GONE);
                sutitleLayout.getMainIV().setVisibility(View.GONE);
                sutitleLayout.setTag(rechargeTypes.get(position));
                sutitleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = adapter.getCount();
                        for (int i = 0; i < count; i++) {
                            JupiterRowStyleSutitleLayout sutitleLayout = (JupiterRowStyleSutitleLayout) listView.getChildAt(i);
                            if (i == position) {
                                selectedType = (RechargeType) sutitleLayout.getTag();
                                sutitleLayout.select(true);
                            } else {
                                sutitleLayout.select(false);
                            }
                        }
                    }
                });
                convertView = sutitleLayout;
            }else {
                sutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            return convertView;
        }
    };
}
