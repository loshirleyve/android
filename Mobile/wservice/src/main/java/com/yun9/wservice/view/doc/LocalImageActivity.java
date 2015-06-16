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
import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageActivity extends JupiterFragmentActivity {

    private List<ImageBean> albums;

    private ImageBean currImageBean;

    private List<ImageBean> currImages = new ArrayList<>();

    private PopupWindow albumsPopW;

    private ListView popuplv;

    private View popupWView;

    private LocalImageAlbumAdapter localImageAlbumAdapter;

    private LocalImageGridViewAdapter localImageGridViewAdapter;

    private LocalImageCommand command;

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
            this.titleBarLayout.getTitleRightTv().setText(R.string.app_edit);
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
                ImageBean imageBean = (ImageBean) albumImageGridItem.getTag();
                if (this.edit && imageBean.isSelected()) {
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
            for (ImageBean imageBean : albums) {
                if (AssertValue.isNotNull(imageBean) && AssertValue.isNotNullAndNotEmpty(imageBean.getChilds())) {
                    for (ImageBean imageBean1 : imageBean.getChilds()) {

                        //检查是否带入的参数选择状态
                        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectImages())) {
                            for (ImageBean selectIB : command.getSelectImages()) {
                                if (selectIB.getId() == imageBean1.getId()) {
                                    imageBean1.setSelected(true);
                                }
                            }
                        }

                        currImages.add(imageBean1);
                    }
                }
            }

            if (AssertValue.isNotNull(localImageGridViewAdapter)) {
                localImageGridViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private LocalImageLoadAsyncTask.OnImageLoadCallback onImageLoadCallback = new LocalImageLoadAsyncTask.OnImageLoadCallback() {
        @Override
        public void onPostExecute(List<ImageBean> imageBeans) {
            albums = imageBeans;
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
        int maxHeight = (int) (this.getDeviceHeightPixels(this) * 0.5);
        if (height >= maxHeight) {
            height = maxHeight;
        }
        albumsPopW.setHeight(maxHeight);


    }

    private PopupWindow.OnDismissListener onPopWDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (AssertValue.isNotNull(currImageBean) && AssertValue.isNotNullAndNotEmpty(currImageBean.getChilds())) {
                currImages.clear();
                for (ImageBean imageBean : currImageBean.getChilds()) {
                    currImages.add(imageBean);
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
            for (ImageBean imageBean : currImages) {
                if (imageBean.isSelected()) {
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
            finish();
        }
    };

    private AdapterView.OnItemClickListener onAlbumItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (AssertValue.isNotNull(view.getTag())) {
                ImageBean imageBean = (ImageBean) view.getTag();
                currImageBean = imageBean;
                albumsPopW.dismiss();
            }
        }
    };

    private AdapterView.OnItemClickListener onGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (edit) {
                ImageBean imageBean = (ImageBean) view.getTag();

                if (!imageBean.isSelected() && AssertValue.isNotNull(command) && command.getMaxSelectNum() > 0) {
                    int selectNum = getSelectNum();
                    if (selectNum >= command.getMaxSelectNum()) {
                        CharSequence charSequence = getResources().getString(R.string.doc_select_num_max, command.getMaxSelectNum());
                        Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if (AssertValue.isNotNull(imageBean)) {
                    imageBean.setSelected(!imageBean.isSelected());
                    AlbumImageGridItem albumImageGridItem = (AlbumImageGridItem) view;
                    if (imageBean.isSelected()) {
                        albumImageGridItem.getSelectBadgeView().show();
                    } else {
                        albumImageGridItem.getSelectBadgeView().hide();
                    }
                }
            } else {
                ImageBrowerActivity.start(LocalImageActivity.this, new ImageBrowerCommand().setImageBeans(currImages).setPosition(position));
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
                    ArrayList<ImageBean> onSelectImages = new ArrayList<>();
                    if (AssertValue.isNotNullAndNotEmpty(currImages)) {
                        for (ImageBean imageBean : currImages) {
                            if (imageBean.isSelected()) {
                                onSelectImages.add(imageBean);
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

    private Drawable getDrawable(Context context) {
        ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(context.getResources().getColor(android.R.color.transparent));
        return bgdrawable;
    }

    /**
     * @param activity
     * @return 屏幕像素高度
     */
    public int getDeviceHeightPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素）
        return height;
    }
}