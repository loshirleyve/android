package com.yun9.wservice.view.inst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Leon on 15/6/9.
 */
public class SelectInstActivity extends JupiterFragmentActivity {

    private SelectInstCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.inst_lv)
    private ListView instListView;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    private List<Inst> insts;

    private SelectInstAdapter selectInstAdapter;

    public static void start(Activity activity, SelectInstCommand command) {
        Intent intent = new Intent(activity, SelectInstActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);

        activity.startActivityForResult(intent, SelectInstCommand.REQUEST_CODE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (SelectInstCommand) this.getIntent().getSerializableExtra("command");

        this.titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(SelectInstCommand.RESULT_CODE_CANCEL);
                SelectInstActivity.this.finish();
            }
        });

        this.instListView.setOnItemClickListener(onInstItemClickListener);

        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });

        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private void refresh() {
        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh();
            }
        }, 1000);
    }

    private void completeRefresh() {

        if (!AssertValue.isNotNull(insts)) {
            this.insts = new ArrayList<>();
        }

        this.insts.clear();

        Inst tempInst = new Inst();
        tempInst.setName("深圳市顶聚科技有限公司");
        tempInst.setId("123123");
        this.insts.add(tempInst);

        if (!AssertValue.isNotNull(selectInstAdapter)) {
            this.selectInstAdapter = new SelectInstAdapter(this, insts);
            instListView.setAdapter(selectInstAdapter);
        } else {
            selectInstAdapter.notifyDataSetChanged();
        }

        mPtrClassicFrameLayout.refreshComplete();
    }

    private AdapterView.OnItemClickListener onInstItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Inst inst = (Inst) view.getTag();

            if (AssertValue.isNotNull(inst)) {
                Intent intent = new Intent();
                intent.putExtra(SelectInstCommand.PARAM_INST, inst);
                setResult(SelectInstCommand.RESULT_CODE_OK, intent);
                SelectInstActivity.this.finish();
            }
        }
    };
}
