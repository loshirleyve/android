package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.SysFileBean;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
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
public class YunFileActivity extends JupiterFragmentActivity {

    private LinkedList<FileBean> mFileBeans = new LinkedList<>();

    private YunFileCommand command;

    private boolean mEdit;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mFrame;

    @ViewInject(id = R.id.file_lv)
    private PagingListView fileLV;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeBtn;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardBtn;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    public static void start(Activity activity, YunFileCommand command) {
        Intent intent = new Intent(activity, YunFileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(YunFileCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_yun_file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (YunFileCommand) getIntent().getSerializableExtra(YunFileCommand.PARAM_COMMAND);

        if (AssertValue.isNotNull(command)) {
            setEdit(command.isEdit());
        }

        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        titleBarLayout.getTitleRightTv().setOnClickListener(onEditClickListener);

        completeBtn.setOnClickListener(onCompleteClickListener);
        sendMsgCardBtn.setOnClickListener(onCompleteClickListener);

        fileLV.setAdapter(fileListViewAdapter);
        fileLV.setOnItemClickListener(onFileListViewItemClickListener);

        mFrame.setLastUpdateTimeRelateObject(this);
        mFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                    FileBean fileBean = mFileBeans.get(0);
                    refresh(fileBean.getId(), Page.PAGE_DIR_PULL);
                } else {
                    refresh(null, Page.PAGE_DIR_PULL);
                }
            }
        });

        fileLV.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                    FileBean fileBean = mFileBeans.get(mFileBeans.size() - 1);
                    refresh(fileBean.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    fileLV.onFinishLoading(true);
                }
            }
        });

        mFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFrame.autoRefresh();
            }
        }, 100);
    }

    private void refresh(String rowid, final String dir) {
        final Resource resource = resourceFactory.create("QueryFile");

        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("level", "user");
        resource.param("filetype", "doc");
        resource.page().setRowid(rowid);
        resource.page().setDir(dir);


        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<SysFileBean> sysFileBeans = (List<SysFileBean>) response.getPayload();

                if (AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = sysFileBeans.size(); i > 0; i--) {
                        FileBean fileBean = new FileBean(sysFileBeans.get(i - 1));
                        mFileBeans.addFirst(fileBean);
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (SysFileBean sysFileBean : sysFileBeans) {
                        FileBean fileBean = new FileBean(sysFileBean);
                        mFileBeans.addLast(fileBean);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
                    fileLV.onFinishLoading(false);
                }

                fileListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                mFrame.refreshComplete();
                fileLV.onFinishLoading(true);
            }
        });
    }

    private void setEdit(boolean edit) {
        mEdit = edit;

        if (mEdit) {
            titleBarLayout.getTitleRightTv().setText(R.string.app_cancel);
        } else {
            titleBarLayout.getTitleRightTv().setText(R.string.app_select);
        }

        if (this.mEdit && AssertValue.isNotNull(command) && YunFileCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            sendMsgCardBtn.setVisibility(View.VISIBLE);
        } else {
            sendMsgCardBtn.setVisibility(View.GONE);
        }

        if (this.mEdit
                && AssertValue.isNotNull(command) && YunFileCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeBtn.setVisibility(View.VISIBLE);
        } else {
            completeBtn.setVisibility(View.GONE);
        }

        fileListViewAdapter.notifyDataSetChanged();

    }

    private int getSelectNum() {
        int num = 0;
        for (FileBean fileBean : mFileBeans) {
            if (fileBean.isSelected()) {
                num++;
            }
        }
        return num;
    }

    private AdapterView.OnItemClickListener onFileListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FileBean fileBean = (FileBean) view.getTag();

            if (mEdit) {
                if (AssertValue.isNotNull(command) && command.getMaxSelectNum() > 0 && !fileBean.isSelected()) {
                    int num = getSelectNum();
                    if (num >= command.getMaxSelectNum()) {
                        CharSequence charSequence = getResources().getString(R.string.doc_select_num_max, command.getMaxSelectNum());
                        Toast.makeText(mContext, charSequence, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                FileItemWidget fileItemWidget = (FileItemWidget) view;
                fileBean.setSelected(!fileBean.isSelected());
                fileItemWidget.selected(fileBean.isSelected());
            } else {
                FileInfoActivity.start(YunFileActivity.this, new FileInfoCommand().setFileBean(fileBean));
            }

        }
    };


    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setEdit(!mEdit);
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(DocCompositeCommand.PARAM_EDIT, mEdit);
            setResult(YunFileCommand.RESULT_CODE_CANCEL,intent);
            finish();
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (YunFileCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<FileBean> onSelectFiles = new ArrayList<>();

                    if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                        for (FileBean fileBean : mFileBeans) {
                            if (fileBean.isSelected()) {
                                onSelectFiles.add(fileBean);
                            }
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(YunFileCommand.PARAM_FILE, onSelectFiles);
                    intent.putExtra(DocCompositeCommand.PARAM_EDIT, mEdit);
                    setResult(YunFileCommand.RESULT_CODE_OK, intent);
                    finish();
                }

                if (YunFileCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
                    //TODO 调用发送动态
                    finish();
                }
            } else {
                setResult(YunFileCommand.RESULT_CODE_ERROR);
                finish();
            }
        }
    };


    private JupiterAdapter fileListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return mFileBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return mFileBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FileItemWidget fileItemWidget = null;
            FileBean fileBean = mFileBeans.get(position);

            if (AssertValue.isNotNull(convertView)) {
                fileItemWidget = (FileItemWidget) convertView;
            } else {
                fileItemWidget = new FileItemWidget(mContext);
            }

            fileItemWidget.getIcoImaveView().setImageResource(fileBean.getIcoResource());
            fileItemWidget.getFileNameTV().setText(fileBean.getName());
            fileItemWidget.getFileSizeTV().setText(fileBean.getSize());
            fileItemWidget.getFileTimeTV().setText(fileBean.getDateAdded());
            fileItemWidget.setTag(fileBean);
            if (mEdit) {
                fileItemWidget.selected(fileBean.isSelected());
            } else {
                fileItemWidget.selectMode(false);
            }
            return fileItemWidget;
        }
    };
}
