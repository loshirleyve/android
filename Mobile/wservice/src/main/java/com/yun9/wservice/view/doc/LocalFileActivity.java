package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class LocalFileActivity extends JupiterFragmentActivity {


    private LocalFileCommand command;

    private List<LocalFileBean> mLocalFileBeans = new ArrayList<>();

    @ViewInject(id = R.id.file_lv)
    private ListView fileListView;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeBtn;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardBtn;

    private boolean mEdit;

    public static void start(Activity activity, LocalFileCommand command) {
        Intent intent = new Intent(activity, LocalFileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LocalFileCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (LocalFileCommand) getIntent().getSerializableExtra(LocalFileCommand.PARAM_COMMAND);

        fileListView.setAdapter(localFileListViewAdapter);
        fileListView.setOnItemClickListener(onFileListViewItemClickListener);

        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        titleBarLayout.getTitleRight().setOnClickListener(onEditClickListener);

        completeBtn.setOnClickListener(onCompleteClickListener);
        sendMsgCardBtn.setOnClickListener(onCompleteClickListener);

        if (AssertValue.isNotNull(command)) {
            setEdit(command.isEdit());
        }


        LoadFileAsyncTask loadFileAsyncTask = new LoadFileAsyncTask(onFileLoadCallback, this);
        loadFileAsyncTask.execute();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_local_file;
    }

    private int getSelectNum() {
        int num = 0;
        for (LocalFileBean localFileBean : mLocalFileBeans) {
            if (localFileBean.isSelected()) {
                num++;
            }
        }
        return num;
    }

    private void setEdit(boolean edit) {
        mEdit = edit;

        if (mEdit) {
            titleBarLayout.getTitleRightTv().setText(R.string.app_cancel);
        } else {
            titleBarLayout.getTitleRightTv().setText(R.string.app_select);
        }

        if (this.mEdit && AssertValue.isNotNull(command) && LocalFileCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            sendMsgCardBtn.setVisibility(View.VISIBLE);
        } else {
            sendMsgCardBtn.setVisibility(View.GONE);
        }

        if (this.mEdit
                && AssertValue.isNotNull(command) && LocalFileCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeBtn.setVisibility(View.VISIBLE);
        } else {
            completeBtn.setVisibility(View.GONE);
        }


        localFileListViewAdapter.notifyDataSetChanged();
    }

    private LoadFileAsyncTask.OnFileLoadCallback onFileLoadCallback = new LoadFileAsyncTask.OnFileLoadCallback() {
        @Override
        public void onPostExecute(List<LocalFileBean> localFileBeans) {
            if (AssertValue.isNotNullAndNotEmpty(localFileBeans)) {
                mLocalFileBeans.clear();
                for (LocalFileBean localFileBean : localFileBeans) {

                    //检查是否被选中
                    if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectFiles())) {
                        for (LocalFileBean selectFile : command.getSelectFiles()) {
                            if (AssertValue.isNotNull(selectFile) && AssertValue.isNotNullAndNotEmpty(selectFile.getFilePath()) && selectFile.getFilePath().equals(localFileBean.getFilePath())) {
                                localFileBean.setSelected(true);
                            }
                        }
                    }

                    mLocalFileBeans.add(localFileBean);

                }

                localFileListViewAdapter.notifyDataSetChanged();
            }
        }
    };

    private AdapterView.OnItemClickListener onFileListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LocalFileBean localFileBean = (LocalFileBean) view.getTag();

            if (mEdit) {
                if (AssertValue.isNotNull(command) && command.getMaxSelectNum() > 0 && !localFileBean.isSelected()) {
                    int num = getSelectNum();
                    if (num >= command.getMaxSelectNum()) {
                        CharSequence charSequence = getResources().getString(R.string.doc_select_num_max, command.getMaxSelectNum());
                        Toast.makeText(mContext, charSequence, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                FileItemWidget fileItemWidget = (FileItemWidget) view;
                localFileBean.setSelected(!localFileBean.isSelected());
                fileItemWidget.selected(localFileBean.isSelected());
            } else {
                //TODO 打开文件按详情

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
            setResult(LocalFileCommand.RESULT_CODE_CANCEL);
            finish();
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (LocalFileCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<LocalFileBean> onSelectFiles = new ArrayList<>();

                    if (AssertValue.isNotNullAndNotEmpty(mLocalFileBeans)) {
                        for (LocalFileBean localFileBean : mLocalFileBeans) {
                            if (localFileBean.isSelected()) {
                                onSelectFiles.add(localFileBean);
                            }
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(LocalFileCommand.PARAM_FILE, onSelectFiles);
                    setResult(LocalFileCommand.RESULT_CODE_OK, intent);
                    finish();
                }

                if (LocalFileCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
                    //TODO 调用发送动态
                    finish();
                }
            } else {
                setResult(LocalFileCommand.RESULT_CODE_ERROR);
                finish();
            }
        }
    };


    private JupiterAdapter localFileListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return mLocalFileBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return mLocalFileBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FileItemWidget fileItemWidget = null;
            LocalFileBean localFileBean = mLocalFileBeans.get(position);

            if (AssertValue.isNotNull(convertView)) {
                fileItemWidget = (FileItemWidget) convertView;
            } else {
                fileItemWidget = new FileItemWidget(mContext);
            }

            fileItemWidget.getIcoImaveView().setImageResource(localFileBean.getIcoResource());
            fileItemWidget.getFileNameTV().setText(localFileBean.getName());
            fileItemWidget.getFileSizeTV().setText(localFileBean.getSize());
            fileItemWidget.getFileTimeTV().setText(localFileBean.getDateAdded());
            fileItemWidget.setTag(localFileBean);

            if (mEdit) {
                fileItemWidget.selected(localFileBean.isSelected());
            } else {
                fileItemWidget.selectMode(false);
            }

            return fileItemWidget;
        }
    };
}
