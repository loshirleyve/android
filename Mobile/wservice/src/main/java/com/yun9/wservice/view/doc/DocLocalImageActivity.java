package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/13.
 */
public class DocLocalImageActivity extends JupiterFragmentActivity {

    private List<LocalImageBean> albums;

    private PopupWindow albumsPopW;

    private ListView popuplv;

    private View popupWView;

    private LocalImageAlbumAdapter localImageAlbumAdapter;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;


    @Override
    protected int getContentView() {
        return R.layout.activity_doc_local_image;
    }

    public static void start(Activity activity, DocLocalImageCommand command) {
        Intent intent = new Intent(activity, DocLocalImageActivity.class);
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

        this.loadImage();
        this.initPopW();
    }

    private void loadImage() {
        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(getContentResolver(), onImageLoadCallback);
        imageLoadAsyncTask.execute("test");
    }

    private ImageLoadAsyncTask.OnImageLoadCallback onImageLoadCallback = new ImageLoadAsyncTask.OnImageLoadCallback() {
        @Override
        public void onPostExecute(List<LocalImageBean> localImageBeans) {
            albums = localImageBeans;
        }
    };

    private void initPopW() {
        if (!AssertValue.isNotNull(localImageAlbumAdapter)) {
            localImageAlbumAdapter = new LocalImageAlbumAdapter(this.getApplicationContext(), albums);
            popuplv.setAdapter(localImageAlbumAdapter);
        }

        albumsPopW = new PopupWindow(popupWView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        albumsPopW.setOnDismissListener(onPopWDismissListener);
        albumsPopW.setBackgroundDrawable(getDrawable(this.getApplicationContext()));
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

        }
    };

    private View.OnClickListener onTitleCenterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            localImageAlbumAdapter.notifyDataSetChanged();
            albumsPopW.showAsDropDown(titleBarLayout);
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
