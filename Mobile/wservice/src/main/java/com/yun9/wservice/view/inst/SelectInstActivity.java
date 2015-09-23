package com.yun9.wservice.view.inst;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
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

    private InstCommand instCommand;
    private SelectInstCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.inst_lv)
    private ListView instListView;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

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
                if (AssertValue.isNotNull(command) && AssertValue.isNotNull(command.getUser())) {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            }
        }, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == InstCommand.RESULT_CODE_OK) {
            refresh();
        }
    }

    private void refresh() {

        Resource resource = resourceFactory.create("QueryUserInsts");
        resource.param("userno", command.getUser().getNo());

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<Inst> tempInsts = (List<Inst>) response.getPayload();
                completeRefresh(tempInsts);
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(SelectInstActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                mPtrClassicFrameLayout.refreshComplete();

            }
        });
    }

    private void completeRefresh(List<Inst> tempInsts) {

        if (!AssertValue.isNotNull(insts)) {
            this.insts = new ArrayList<>();
        }

        this.insts.clear();

        if (AssertValue.isNotNullAndNotEmpty(tempInsts)) {
            for (Inst inst : tempInsts) {
                if (AssertValue.isNotNull(inst)) {
                    this.insts.add(inst);
                }
            }
        }
        if (insts.size() == 0) {
            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_notice))
                    .setMessage(getString(R.string.init_inst_notice))
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton(getString(R.string.yes), onYesClickListener)
                    .setNegativeButton(getString(R.string.no), onNoClickListener).create();
            alertDialog.show();

        } else {
            if (!AssertValue.isNotNull(selectInstAdapter)) {
                this.selectInstAdapter = new SelectInstAdapter(this, insts);
                this.selectInstAdapter.setOnSelectListener(onInstSelectListener);
                Inst currInst = sessionManager.getInst(command.getUser().getId());
                if (AssertValue.isNotNull(currInst)) {
                    selectInstAdapter.setCurrInst(currInst);
                }
                instListView.setAdapter(selectInstAdapter);
            } else {
                selectInstAdapter.notifyDataSetChanged();
            }
        }
    }

    private OnSelectListener onInstSelectListener = new OnSelectListener() {
        @Override
        public void onSelect(View view, boolean mode) {
            Inst inst = (Inst) view.getTag();
            if (AssertValue.isNotNull(inst)) {
                Intent intent = new Intent();
                intent.putExtra(SelectInstCommand.PARAM_INST, inst);
                setResult(SelectInstCommand.RESULT_CODE_OK, intent);
                SelectInstActivity.this.finish();
            }

        }
    };

    private DialogInterface.OnClickListener onYesClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (!AssertValue.isNotNull(instCommand)) {
                instCommand = new InstCommand();
            }
            instCommand.setUserid(command.getUser().getId());
            InitInstActivity.start(SelectInstActivity.this, instCommand);
        }
    };
    private DialogInterface.OnClickListener onNoClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

}
