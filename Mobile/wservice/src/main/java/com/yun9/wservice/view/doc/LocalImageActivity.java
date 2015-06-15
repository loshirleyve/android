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

import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
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

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_local_gv)
    private GridView imageGV;


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
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popupWView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.popup_local_album_list, null);
        popuplv = (ListView) popupWView.findViewById(R.id.listview);

        this.titleBarLayout.getTitleCenter().setOnClickListener(onTitleCenterClickListener);
        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        this.popuplv.setOnItemClickListener(onAlbumItemClickListener);

        localImageGridViewAdapter = new LocalImageGridViewAdapter(getApplicationContext(), currImages);
        imageGV.setAdapter(localImageGridViewAdapter);
        imageGV.setOnItemClickListener(onGridViewItemClickListener);

        this.loadImage();
        this.initPopW();
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
            ImageBrowerActivity.start(LocalImageActivity.this,new ImageBrowerCommand().setImageBeans(currImages).setPosition(position));
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
