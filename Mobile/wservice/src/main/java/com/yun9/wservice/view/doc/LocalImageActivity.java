package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.camera.CameraActivity;
import com.yun9.wservice.view.camera.CameraCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageActivity extends JupiterFragmentActivity {

    private List<FileBean> albums;

    private FileBean currFileBean;

    private List<FileBean> currImages = new ArrayList<>();

    private PopupWindow albumsPopW;

    private ListView popuplv;

    private View popupWView;

    private LocalImageAlbumAdapter localImageAlbumAdapter;

    private LocalImageGridViewAdapter localImageGridViewAdapter;

    private LocalImageCommand command;

    private CameraCommand cameraCommand;

    private boolean edit;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_local_gv)
    private GridView imageGV;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeBtn;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardBtn;


    @Override
    protected int getContentView() {
        return R.layout.activity_doc_local_image;
    }

    public static void start(Activity activity, LocalImageCommand command) {
        Intent intent = new Intent(activity, LocalImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (LocalImageCommand) getIntent().getSerializableExtra("command");

        //设置当前是否编辑状态
        if (AssertValue.isNotNull(command)) {
            this.setEdit(command.isEdit());
        }

        if (AssertValue.isNotNull(command)) {
            this.titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
            this.titleBarLayout.getTitleRight().setOnClickListener(onEditClickListener);
        }

        popupWView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.popup_local_album_list, null);
        popuplv = (ListView) popupWView.findViewById(R.id.listview);

        //选择不同相册会有些混乱，所以取消，默认显示全部图片
        //this.titleBarLayout.getTitleCenter().setOnClickListener(onTitleCenterClickListener);
        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        this.completeBtn.setOnClickListener(onCompleteClickListener);
        this.sendMsgCardBtn.setOnClickListener(onCompleteClickListener);

        this.popuplv.setOnItemClickListener(onAlbumItemClickListener);

        localImageGridViewAdapter = new LocalImageGridViewAdapter(getApplicationContext(), edit, currImages);
        imageGV.setAdapter(localImageGridViewAdapter);
        imageGV.setOnItemClickListener(onGridViewItemClickListener);

        this.loadImage();
        this.initPopW();
    }

    private void setEdit(boolean editState) {
        this.edit = editState;

        if (this.edit) {
            this.titleBarLayout.getTitleRightTv().setText(R.string.app_cancel);
        } else {
            this.titleBarLayout.getTitleRightTv().setText(R.string.app_select);
        }

        if (this.edit && AssertValue.isNotNull(command) && LocalImageCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            sendMsgCardBtn.setVisibility(View.VISIBLE);
        } else {
            sendMsgCardBtn.setVisibility(View.GONE);
        }

        if (this.edit && AssertValue.isNotNull(command) && LocalImageCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeBtn.setVisibility(View.VISIBLE);
        } else {
            completeBtn.setVisibility(View.GONE);
        }

        if (AssertValue.isNotNull(localImageGridViewAdapter)) {
            localImageGridViewAdapter.setEdit(edit);
        }

        for (int i = 0; i < imageGV.getCount(); i++) {
            AlbumImageGridItem albumImageGridItem = (AlbumImageGridItem) imageGV.getChildAt(i);
            if (AssertValue.isNotNull(albumImageGridItem) && AssertValue.isNotNull(albumImageGridItem.getTag())) {
                FileBean fileBean = (FileBean) albumImageGridItem.getTag();
                if (this.edit && fileBean.isSelected()) {
                    albumImageGridItem.getSelectBadgeView().show();
                } else {
                    albumImageGridItem.getSelectBadgeView().hide();
                }
            }
        }


    }

    private void loadImage() {
        LocalImageLoadAsyncTask localImageLoadAsyncTask = new LocalImageLoadAsyncTask(getContentResolver(), onImageLoadCallback);
        localImageLoadAsyncTask.execute();
    }

    private void showAllImage() {
        if (AssertValue.isNotNullAndNotEmpty(albums)) {
            currImages.clear();
            currImages.add(createCamera());

            for (FileBean fileBean : albums) {
                //检查是否带入的参数选择状态
                if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectImages())) {
                    for (FileBean selectIB : command.getSelectImages()) {
                        if (selectIB.getId().equals(fileBean.getId())) {
                            fileBean.setSelected(true);
                        }
                    }
                }
                currImages.add(fileBean);
            }

            if (AssertValue.isNotNull(localImageGridViewAdapter)) {
                localImageGridViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private FileBean createCamera() {
        FileBean fileBean = new FileBean();
        fileBean.setCamera(true);
        fileBean.setFilePath("drawable://" + R.drawable.mapp);
        fileBean.setThumbnailPath("drawable://" + R.drawable.mapp);

        return fileBean;
    }

    private LocalImageLoadAsyncTask.OnImageLoadCallback onImageLoadCallback = new LocalImageLoadAsyncTask.OnImageLoadCallback() {
        @Override
        public void onPostExecute(List<FileBean> fileBeans) {
            albums = fileBeans;
            showAllImage();
        }
    };

    private void initPopW() {

        albumsPopW = new PopupWindow(popupWView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        albumsPopW.setOnDismissListener(onPopWDismissListener);
        albumsPopW.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        albumsPopW.setFocusable(true);
        albumsPopW.setOutsideTouchable(true);

        int height = albumsPopW.getHeight();
        int maxHeight = (int) (PublicHelp.getDeviceHeightPixels(this) * 0.5);
        if (height >= maxHeight) {
            height = maxHeight;
        }
        albumsPopW.setHeight(maxHeight);


    }

    private PopupWindow.OnDismissListener onPopWDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (AssertValue.isNotNull(currFileBean) && AssertValue.isNotNullAndNotEmpty(currFileBean.getChilds())) {
                currImages.clear();
                for (FileBean fileBean : currFileBean.getChilds()) {
                    currImages.add(fileBean);
                }
                if (AssertValue.isNotNull(localImageGridViewAdapter)) {
                    localImageGridViewAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    private int getSelectNum() {
        int num = 0;

        if (AssertValue.isNotNullAndNotEmpty(currImages)) {
            for (FileBean fileBean : currImages) {
                if (fileBean.isSelected()) {
                    num++;
                }
            }
        }

        return num;
    }

    private View.OnClickListener onTitleCenterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            albumsPopW.showAsDropDown(titleBarLayout);

            if (!AssertValue.isNotNull(localImageAlbumAdapter)) {
                localImageAlbumAdapter = new LocalImageAlbumAdapter(LocalImageActivity.this, albums);
                popuplv.setAdapter(localImageAlbumAdapter);
            } else {
                localImageAlbumAdapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(LocalImageCommand.RESULT_CODE_CANCEL);
            finish();
        }
    };

    private AdapterView.OnItemClickListener onAlbumItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (AssertValue.isNotNull(view.getTag())) {
                FileBean fileBean = (FileBean) view.getTag();
                currFileBean = fileBean;
                albumsPopW.dismiss();
            }
        }
    };

    private AdapterView.OnItemClickListener onGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FileBean fileBean = (FileBean) view.getTag();
            if (!fileBean.isCamera()) {
                if (edit) {
                    if (!fileBean.isSelected() && AssertValue.isNotNull(command) && command.getMaxSelectNum() > 0) {
                        int selectNum = getSelectNum();
                        if (selectNum >= command.getMaxSelectNum()) {
                            CharSequence charSequence = getResources().getString(R.string.doc_select_num_max, command.getMaxSelectNum());
                            Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }


                    if (AssertValue.isNotNull(fileBean)) {
                        fileBean.setSelected(!fileBean.isSelected());
                        AlbumImageGridItem albumImageGridItem = (AlbumImageGridItem) view;
                        if (fileBean.isSelected()) {
                            albumImageGridItem.getSelectBadgeView().show();
                        } else {
                            albumImageGridItem.getSelectBadgeView().hide();
                        }
                    }
                } else {
                    ImageBrowerActivity.start(LocalImageActivity.this, new ImageBrowerCommand().setFileBeans(currImages).setPosition(position));
                }
            } else {
                if (!AssertValue.isNotNull(cameraCommand)) {
                    cameraCommand = new CameraCommand();
                }
                CameraActivity.start(LocalImageActivity.this, cameraCommand);
            }
        }
    };

    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setEdit(!edit);
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (LocalImageCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<FileBean> onSelectImages = new ArrayList<>();
                    if (AssertValue.isNotNullAndNotEmpty(currImages)) {
                        for (FileBean fileBean : currImages) {
                            if (fileBean.isSelected()) {
                                onSelectImages.add(fileBean);
                            }
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(LocalImageCommand.PARAM_IMAGE, onSelectImages);
                    setResult(LocalImageCommand.RESULT_CODE_OK, intent);
                    finish();
                }

                if (LocalImageCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
                    finish();
                }
            } else {
                setResult(LocalImageCommand.RESULT_CODE_ERROR);
                finish();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AssertValue.isNotNull(cameraCommand) && cameraCommand.getRequestCode() == requestCode && resultCode == CameraCommand.RESULT_CODE_OK) {
            postLoadImage.sendEmptyMessageDelayed(1, 500);
        }

    }

    private Handler postLoadImage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadImage();
            }
        }
    };
}
