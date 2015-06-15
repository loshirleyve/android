package com.yun9.wservice.view.doc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;

import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class LocalImageGridViewAdapter extends JupiterAdapter {

    private Context mContext;
    private List<LocalImageBean> mLocalImageBeans;

    public LocalImageGridViewAdapter(Context context, List<LocalImageBean> localImageBeans) {
        this.mContext = context;
        this.mLocalImageBeans = localImageBeans;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(mLocalImageBeans)) {
            return mLocalImageBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mLocalImageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageGridItem albumImageGridItem = null;
        LocalImageBean localImageBean = mLocalImageBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            albumImageGridItem = (AlbumImageGridItem) convertView;
        } else {
            albumImageGridItem = new AlbumImageGridItem(mContext);
        }

        ImageLoaderUtil.getInstance(mContext).displayImage("file://"+localImageBean.getThumbnailPath(), albumImageGridItem.getImageView());
        albumImageGridItem.setTag(localImageBean);

        return albumImageGridItem;
    }
}
