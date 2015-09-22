package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.widget.FileItemWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class LocalFileActivity extends JupiterFragmentActivity {


    private LocalFileCommand command;

    private List<FileBean> mFileBeans = new ArrayList<>();

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
        for (FileBean fileBean : mFileBeans) {
            if (fileBean.isSelected()) {
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
        public void onPostExecute(List<FileBean> fileBeans) {
            if (AssertValue.isNotNullAndNotEmpty(fileBeans)) {
                mFileBeans.clear();
                for (FileBean fileBean : fileBeans) {

                    //检查是否被选中
                    if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectFiles())) {
                        for (FileBean selectFile : command.getSelectFiles()) {
                            if (AssertValue.isNotNull(selectFile) && AssertValue.isNotNullAndNotEmpty(selectFile.getFilePath()) && selectFile.getFilePath().equals(fileBean.getFilePath())) {
                                fileBean.setSelected(true);
                            }
                        }
                    }

                    mFileBeans.add(fileBean);

                }

                localFileListViewAdapter.notifyDataSetChanged();
            }
        }
    };

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
            } else{
                FileInfoActivity.start(LocalFileActivity.this, new FileInfoCommand().setFileBean(fileBean));
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
            setResult(LocalFileCommand.RESULT_CODE_CANCEL,intent);
            finish();
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (LocalFileCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<FileBean> onSelectFiles = new ArrayList<>();

                    if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                        for (FileBean fileBean : mFileBeans) {
                            if (fileBean.isSelected()) {
                                onSelectFiles.add(fileBean);
                            }
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(LocalFileCommand.PARAM_FILE, onSelectFiles);
                    intent.putExtra(DocCompositeCommand.PARAM_EDIT, mEdit);
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
