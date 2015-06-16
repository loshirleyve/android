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
import com.yun9.jupiter.model.LocalFileBean;
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

    @ViewInject(id = R.id.image_local_gv)
    private JupiterGridView localImagesGV;

    @ViewInject(id = R.id.file_local_lv)
    private JupiterListView localFileLV;

    private List<LocalFileBean> onSelectLocalImages = new ArrayList<>();

    private List<LocalFileBean> onSelectLocalFiles = new ArrayList<>();

    private boolean mEdit;

    private int maxSelectNum = 6;


    public static void start(Activity activity, DocCompositeCommand command) {
        Intent intent = new Intent(activity, DocCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivity(intent);
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
            mEdit = command.isEdit();
        }

        localImageBtn.setOnClickListener(onLocalImageClickListener);
        localFileBtn.setOnClickListener(onLocalFileClickListener);

        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        localImagesGV.setAdapter(localImageSelectsGVAdapter);
        localImagesGV.setOnItemClickListener(onLocalImageGridViewItemClickListener);

        localFileLV.setAdapter(localFileListViewAdapter);
        localFileLV.setOnItemClickListener(onLocalFileListViewItemClickListener);

    }

    private void initLocalImageGV() {

    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onLocalImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(localImageCommand)) {
                localImageCommand = new LocalImageCommand().setEdit(mEdit).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(maxSelectNum);
            }
            localImageCommand.setSelectImages(onSelectLocalImages);
            LocalImageActivity.start(DocCompositeActivity.this, localImageCommand);
        }
    };


    private View.OnClickListener onLocalFileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!AssertValue.isNotNull(localFileCommand)) {
                localFileCommand = new LocalFileCommand().setMaxSelectNum(maxSelectNum).setEdit(mEdit).setCompleteType(LocalFileCommand.COMPLETE_TYPE_CALLBACK);
            }
            localFileCommand.setSelectFiles(onSelectLocalFiles);
            LocalFileActivity.start(DocCompositeActivity.this, localFileCommand);

        }
    };


    private AdapterView.OnItemClickListener onLocalImageGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageBrowerActivity.start(DocCompositeActivity.this, new ImageBrowerCommand().setLocalFileBeans(onSelectLocalImages).setPosition(position));
        }
    };

    private AdapterView.OnItemClickListener onLocalFileListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO 打开文件详情
            Toast.makeText(mContext,"打开文件详情。",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (AssertValue.isNotNull(localImageCommand) && requestCode == localImageCommand.getRequestCode() && resultCode == LocalImageCommand.RESULT_CODE_OK) {
            onSelectLocalImages = (List<LocalFileBean>) data.getSerializableExtra(LocalImageCommand.PARAM_IMAGE);
            localImageSelectsGVAdapter.notifyDataSetChanged();
        }

        if (AssertValue.isNotNull(localFileCommand) && requestCode == localFileCommand.getRequestCode() && resultCode == LocalFileCommand.RESULT_CODE_OK) {
            onSelectLocalFiles = (List<LocalFileBean>) data.getSerializableExtra(LocalFileCommand.PARAM_FILE);
            localFileListViewAdapter.notifyDataSetChanged();
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
            LocalFileBean localFileBean = onSelectLocalImages.get(position);

            if (AssertValue.isNotNull(convertView)) {
                albumImageGridItem = (AlbumImageGridItem) convertView;
            } else {
                albumImageGridItem = new AlbumImageGridItem(DocCompositeActivity.this);
            }

            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage("file://" + localFileBean.getThumbnailPath(), albumImageGridItem.getImageView());
            albumImageGridItem.setTag(localFileBean);
            albumImageGridItem.getDeleteBadgeView().setTag(localFileBean);
            albumImageGridItem.getDeleteBadgeView().show();
            albumImageGridItem.getDeleteBadgeView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalFileBean deleteLocalFileBean = (LocalFileBean) v.getTag();
                    onSelectLocalImages.remove(deleteLocalFileBean);
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
            LocalFileBean localFileBean = onSelectLocalFiles.get(position);

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
            fileItemWidget.getDeleteStateIV().setVisibility(View.VISIBLE);
            fileItemWidget.getStateLL().setTag(localFileBean);
            fileItemWidget.getStateLL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalFileBean deleteLocalFileBean = (LocalFileBean) v.getTag();
                    onSelectLocalFiles.remove(deleteLocalFileBean);
                    localFileListViewAdapter.notifyDataSetChanged();
                }
            });


            return fileItemWidget;
        }
    };
}
