package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.SysFileBean;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterPagingListView;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.widget.FileItemWidget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Leon on 15/6/17.
 */
public class YunFileInfoActivity extends JupiterFragmentActivity {

    private YunFileInfoCommand command;
    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.file_name)
    private TextView fileName;

    @ViewInject(id = R.id.file_from)
    private TextView fileFrom;

    @ViewInject(id = R.id.file_uploaddate)
    private TextView fileUploadDate;

    @ViewInject(id = R.id.file_size)
    private TextView fileSize;

    @ViewInject(id = R.id.fileopenway)
    private Button fileopenway;

    @ViewInject(id = R.id.fileshare)
    private Button fileshare;

    public static void start(Activity activity, YunFileInfoCommand command) {
        Intent intent = new Intent(activity, YunFileInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(YunFileInfoCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_yun_fileinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (YunFileInfoCommand) getIntent().getSerializableExtra(YunFileInfoCommand.PARAM_COMMAND);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        initView();

    }

    public void initView()
    {
        if(AssertValue.isNotNull(command) && AssertValue.isNotNull(command.getFileBean()))
        {
            FileBean fileBean=command.getFileBean();
            fileName.setText(fileBean.getName());
            fileFrom.setText(fileBean.getLevel());
            fileUploadDate.setText(fileBean.getDateAdded());
            fileSize.setText(fileBean.getSize());
        }
    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
