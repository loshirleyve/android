package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rxy on 15/6/1.
 * 文件列表界面
 */
public class DocCompositeActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(DocCompositeActivity.class);

    private DocCompositeCommand command;

    private LocalImageCommand localImageCommand;

    private LocalFileCommand localFileCommand;

    private YunFileCommand yunFileCommand;

    private YunImageCommand yunImageCommand;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonbarLL;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_local)
    private JupiterRowStyleSutitleLayout localImageBtn;

    @ViewInject(id = R.id.file_local)
    private JupiterRowStyleSutitleLayout localFileBtn;

    @ViewInject(id = R.id.file_online)
    private JupiterRowStyleSutitleLayout yunFileBtn;

    @ViewInject(id = R.id.image_online)
    private JupiterRowStyleSutitleLayout yunImageBtn;

    @ViewInject(id = R.id.image_local_gv)
    private JupiterGridView localImagesGV;

    @ViewInject(id = R.id.image_yun_gv)
    private JupiterGridView yunImagesGV;

    @ViewInject(id = R.id.file_local_lv)
    private JupiterListView localFileLV;

    @ViewInject(id = R.id.file_online_lv)
    private JupiterListView yunFileLV;

    @BeanInject
    private SessionManager sessionManager;

    private List<FileBean> onSelectLocalImages = new ArrayList<>();

    private List<FileBean> onSelectLocalFiles = new ArrayList<>();

    private List<FileBean> onSelectYunFiles = new ArrayList<>();

    private List<FileBean> onSelectYunImages = new ArrayList<>();

    private boolean mEdit;

    private int maxSelectNum = 6;

    private String mUserid;
    private String mInstid;


    public static void start(Activity activity, DocCompositeCommand command) {
        Intent intent = new Intent(activity, DocCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_composite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取参数
        command = (DocCompositeCommand) this.getIntent().getSerializableExtra("command");

        if (AssertValue.isNotNull(command)) {
            this.setEdit(command.isEdit());
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            mUserid = command.getUserid();
        } else {
            mUserid = sessionManager.getUser().getId();
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getInstid())) {
            mInstid = command.getInstid();
        } else {
            mInstid = sessionManager.getInst().getId();
        }

        localImageBtn.setOnClickListener(onLocalImageClickListener);
        localFileBtn.setOnClickListener(onLocalFileClickListener);
        yunFileBtn.setOnClickListener(onYunFileClickListener);
        yunImageBtn.setOnClickListener(onYunImageClickListener);

        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        this.titleBarLayout.getTitleRight().setOnClickListener(onEditClickListener);

        completeButton.setOnClickListener(onCompleteClickListener);
        sendMsgCardButton.setOnClickListener(onCompleteClickListener);

        localImagesGV.setAdapter(localImageSelectsGVAdapter);
        localImagesGV.setOnItemClickListener(onLocalImageGridViewItemClickListener);

        yunImagesGV.setAdapter(yunImageSelectsGVAdapter);
        yunImagesGV.setOnItemClickListener(onYunImageGridViewItemClickListener);

        localFileLV.setAdapter(localFileListViewAdapter);
        localFileLV.setOnItemClickListener(onLocalFileListViewItemClickListener);

        yunFileLV.setAdapter(yunFileListViewAdapter);
        yunFileLV.setOnItemClickListener(onYunFileListViewItemClickListener);

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getOnSelectFiles())) {
            for (FileBean fileBean : command.getOnSelectFiles()) {

                if (FileBean.FILE_STORAGE_TYPE_YUN.equals(fileBean.getStorageType())) {
                    onSelectYunFiles.add(fileBean);
                }

                if (FileBean.FILE_STORAGE_TYPE_LOCAL.equals(fileBean.getStorageType())) {
                    onSelectLocalFiles.add(fileBean);
                }
            }

            localFileListViewAdapter.notifyDataSetChanged();
            yunFileListViewAdapter.notifyDataSetChanged();
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getOnSelectImages())) {
            for (FileBean fileBean : command.getOnSelectImages()) {

                if (FileBean.FILE_STORAGE_TYPE_YUN.equals(fileBean.getStorageType())) {
                    onSelectYunImages.add(fileBean);
                }

                if (FileBean.FILE_STORAGE_TYPE_LOCAL.equals(fileBean.getStorageType())) {
                    onSelectLocalImages.add(fileBean);
                }
            }

            localImageSelectsGVAdapter.notifyDataSetChanged();
            yunImageSelectsGVAdapter.notifyDataSetChanged();
        }

    }

    private void setEdit(boolean edit) {
        this.mEdit = edit;

        if (mEdit) {
            titleBarLayout.getTitleRightTv().setText(R.string.app_cancel);
        } else {
            titleBarLayout.getTitleRightTv().setText(R.string.app_select);
        }

        if (this.mEdit && AssertValue.isNotNull(command) && DocCompositeCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            sendMsgCardButton.setVisibility(View.VISIBLE);
        } else {
            sendMsgCardButton.setVisibility(View.GONE);
        }

        if (this.mEdit
                && AssertValue.isNotNull(command) && DocCompositeCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeButton.setVisibility(View.VISIBLE);
        } else {
            completeButton.setVisibility(View.GONE);
        }

    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(DocCompositeCommand.RESULT_CODE_CANCEL);
            finish();
        }
    };

    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setEdit(!mEdit);
        }
    };

    private View.OnClickListener onLocalImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(localImageCommand)) {
                localImageCommand = new LocalImageCommand().setEdit(mEdit).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(maxSelectNum).setUserid(mUserid).setInstid(mInstid);
            }
            localImageCommand.setSelectImages(onSelectLocalImages);
            LocalImageActivity.start(DocCompositeActivity.this, localImageCommand);
        }
    };


    private View.OnClickListener onLocalFileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!AssertValue.isNotNull(localFileCommand)) {
                localFileCommand = new LocalFileCommand().setMaxSelectNum(maxSelectNum).setEdit(mEdit).setCompleteType(LocalFileCommand.COMPLETE_TYPE_CALLBACK).setUserid(mUserid).setInstid(mInstid);
            }
            localFileCommand.setSelectFiles(onSelectLocalFiles);
            LocalFileActivity.start(DocCompositeActivity.this, localFileCommand);

        }
    };

    private View.OnClickListener onYunFileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(yunFileCommand)) {
                yunFileCommand = new YunFileCommand().setMaxSelectNum(maxSelectNum).setEdit(mEdit).setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK).setUserid(mUserid).setInstid(mInstid);
            }
            yunFileCommand.setSelectFiles(onSelectYunFiles);
            YunFileActivity.start(DocCompositeActivity.this, yunFileCommand);
        }
    };

    private View.OnClickListener onYunImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(yunImageCommand)) {
                yunImageCommand = new YunImageCommand().setMaxSelectNum(maxSelectNum).setEdit(mEdit).setCompleteType(YunFileCommand.COMPLETE_TYPE_CALLBACK).setUserid(mUserid).setInstid(mInstid);
            }
            yunImageCommand.setSelectImages(onSelectYunImages);
            YunImageActivity.start(DocCompositeActivity.this, yunImageCommand);

        }
    };


    private AdapterView.OnItemClickListener onLocalImageGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageBrowerActivity.start(DocCompositeActivity.this, new ImageBrowerCommand().setFileBeans(onSelectLocalImages).setPosition(position));
        }
    };

    private AdapterView.OnItemClickListener onYunImageGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageBrowerActivity.start(DocCompositeActivity.this, new ImageBrowerCommand().setFileBeans(onSelectYunImages).setPosition(position));
        }
    };

    private AdapterView.OnItemClickListener onLocalFileListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO 打开文件详情
            Toast.makeText(mContext, "打开文件详情。", Toast.LENGTH_SHORT).show();
        }
    };

    private AdapterView.OnItemClickListener onYunFileListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO 打开文件详情
            Toast.makeText(mContext, "打开文件详情。", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (DocCompositeCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<FileBean> onSelectFiles = new ArrayList<>();
                    ArrayList<FileBean> onSelectImages = new ArrayList<>();

                    if (AssertValue.isNotNullAndNotEmpty(onSelectLocalFiles)) {
                        for (FileBean fileBean : onSelectLocalFiles) {
                            onSelectFiles.add(fileBean);
                        }
                    }

                    if (AssertValue.isNotNullAndNotEmpty(onSelectYunFiles)) {
                        for (FileBean fileBean : onSelectYunFiles) {
                            onSelectFiles.add(fileBean);
                        }
                    }

                    if (AssertValue.isNotNullAndNotEmpty(onSelectLocalImages)) {
                        for (FileBean fileBean : onSelectLocalImages) {
                            onSelectImages.add(fileBean);
                        }
                    }

                    if (AssertValue.isNotNullAndNotEmpty(onSelectYunImages)) {
                        for (FileBean fileBean : onSelectYunImages) {
                            onSelectImages.add(fileBean);
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(DocCompositeCommand.PARAM_FILE, onSelectFiles);
                    intent.putExtra(DocCompositeCommand.PARAM_IMAGE, onSelectImages);
                    setResult(LocalFileCommand.RESULT_CODE_OK, intent);
                    finish();

                }

                if (DocCompositeCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
                    //TODO 调用发送动态
                    finish();
                }
            } else {
                setResult(DocCompositeCommand.RESULT_CODE_ERROR);
                finish();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (AssertValue.isNotNull(localImageCommand) && requestCode == localImageCommand.getRequestCode() && resultCode == LocalImageCommand.RESULT_CODE_OK) {
            onSelectLocalImages = (List<FileBean>) data.getSerializableExtra(LocalImageCommand.PARAM_IMAGE);
            localImageSelectsGVAdapter.notifyDataSetChanged();
        }

        if (AssertValue.isNotNull(localFileCommand) && requestCode == localFileCommand.getRequestCode() && resultCode == LocalFileCommand.RESULT_CODE_OK) {
            onSelectLocalFiles = (List<FileBean>) data.getSerializableExtra(LocalFileCommand.PARAM_FILE);
            localFileListViewAdapter.notifyDataSetChanged();
        }

        if (AssertValue.isNotNull(yunFileCommand) && requestCode == yunFileCommand.getRequestCode() && resultCode == YunFileCommand.RESULT_CODE_OK) {
            onSelectYunFiles = (List<FileBean>) data.getSerializableExtra(YunFileCommand.PARAM_FILE);
            yunFileListViewAdapter.notifyDataSetChanged();
        }

        if (AssertValue.isNotNull(yunImageCommand) && requestCode == yunImageCommand.getRequestCode() && resultCode == YunImageCommand.RESULT_CODE_OK) {
            onSelectYunImages = (List<FileBean>) data.getSerializableExtra(YunImageCommand.PARAM_IMAGE);
            yunImageSelectsGVAdapter.notifyDataSetChanged();
        }

    }

    private JupiterAdapter localImageSelectsGVAdapter = new JupiterAdapter() {

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(onSelectLocalImages)) {
                return onSelectLocalImages.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return onSelectLocalImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumImageGridItem albumImageGridItem = null;
            FileBean fileBean = onSelectLocalImages.get(position);

            if (AssertValue.isNotNull(convertView)) {
                albumImageGridItem = (AlbumImageGridItem) convertView;
            } else {
                albumImageGridItem = new AlbumImageGridItem(DocCompositeActivity.this);
            }

            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(fileBean.getThumbnailPath(), albumImageGridItem.getImageView());
            albumImageGridItem.setTag(fileBean);
            albumImageGridItem.getDeleteBadgeView().setTag(fileBean);
            albumImageGridItem.getDeleteBadgeView().show();
            albumImageGridItem.getDeleteBadgeView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileBean deleteFileBean = (FileBean) v.getTag();
                    onSelectLocalImages.remove(deleteFileBean);
                    localImageSelectsGVAdapter.notifyDataSetChanged();
                }
            });


            return albumImageGridItem;
        }
    };

    private JupiterAdapter localFileListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return onSelectLocalFiles.size();
        }

        @Override
        public Object getItem(int position) {
            return onSelectLocalFiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FileItemWidget fileItemWidget = null;
            FileBean fileBean = onSelectLocalFiles.get(position);

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
            fileItemWidget.getDeleteStateIV().setVisibility(View.VISIBLE);
            fileItemWidget.getStateLL().setTag(fileBean);
            fileItemWidget.getStateLL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileBean deleteFileBean = (FileBean) v.getTag();
                    onSelectLocalFiles.remove(deleteFileBean);
                    localFileListViewAdapter.notifyDataSetChanged();
                }
            });


            return fileItemWidget;
        }
    };

    private JupiterAdapter yunFileListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return onSelectYunFiles.size();
        }

        @Override
        public Object getItem(int position) {
            return onSelectYunFiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FileItemWidget fileItemWidget = null;
            FileBean fileBean = onSelectYunFiles.get(position);

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
            fileItemWidget.getDeleteStateIV().setVisibility(View.VISIBLE);
            fileItemWidget.getStateLL().setTag(fileBean);
            fileItemWidget.getStateLL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileBean deleteFileBean = (FileBean) v.getTag();
                    onSelectYunFiles.remove(deleteFileBean);
                    yunFileListViewAdapter.notifyDataSetChanged();
                }
            });


            return fileItemWidget;
        }
    };

    private JupiterAdapter yunImageSelectsGVAdapter = new JupiterAdapter() {

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(onSelectYunImages)) {
                return onSelectYunImages.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return onSelectYunImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumImageGridItem albumImageGridItem = null;
            FileBean fileBean = onSelectYunImages.get(position);

            if (AssertValue.isNotNull(convertView)) {
                albumImageGridItem = (AlbumImageGridItem) convertView;
            } else {
                albumImageGridItem = new AlbumImageGridItem(DocCompositeActivity.this);
            }

            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(fileBean.getFilePath(), albumImageGridItem.getImageView());
            albumImageGridItem.setTag(fileBean);
            albumImageGridItem.getDeleteBadgeView().setTag(fileBean);
            albumImageGridItem.getDeleteBadgeView().show();
            albumImageGridItem.getDeleteBadgeView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileBean deleteFileBean = (FileBean) v.getTag();
                    onSelectYunImages.remove(deleteFileBean);
                    yunImageSelectsGVAdapter.notifyDataSetChanged();
                }
            });


            return albumImageGridItem;
        }
    };
}
