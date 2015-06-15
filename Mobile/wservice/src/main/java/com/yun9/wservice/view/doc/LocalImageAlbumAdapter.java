package com.yun9.wservice.view.doc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageAlbumAdapter extends JupiterAdapter {

    private List<LocalImageBean> mAlbums;

    private Context mContext;

    public LocalImageAlbumAdapter(Context context, List<LocalImageBean> albums) {
        this.mAlbums = albums;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNull(mAlbums)) {
            return mAlbums.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mAlbums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalImageAlbumItemWidget localImageAlbumItemWidget = null;
        LocalImageBean localImageBean = mAlbums.get(position);

        if (AssertValue.isNotNull(convertView)) {
            localImageAlbumItemWidget = (LocalImageAlbumItemWidget) convertView;
        } else {
            localImageAlbumItemWidget = new LocalImageAlbumItemWidget(mContext);
        }

        localImageAlbumItemWidget.setTag(localImageBean);
        localImageAlbumItemWidget.getInfoTV().setText(localImageBean.getName());

        ImageLoaderUtil.getInstance(mContext).displayImage("file://" + localImageBean.getThumbnailPath(), localImageAlbumItemWidget.getImageView());

        if (AssertValue.isNotNullAndNotEmpty(localImageBean.getChilds())) {
            localImageAlbumItemWidget.getNumTV().setText(localImageBean.getChilds().size() + "张图片");
        } else {
            localImageAlbumItemWidget.getNumTV().setText("0张图片");
        }


        return localImageAlbumItemWidget;
    }
}
