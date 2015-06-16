package com.yun9.wservice.view.doc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;

import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class LocalImageGridViewAdapter extends JupiterAdapter {

    private Context mContext;
    private List<ImageBean> mImageBeans;
    private boolean edit;

    public LocalImageGridViewAdapter(Context context,boolean edit, List<ImageBean> imageBeans) {
        this.mContext = context;
        this.mImageBeans = imageBeans;
        this.edit = edit;

    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(mImageBeans)) {
            return mImageBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mImageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageGridItem albumImageGridItem = null;
        ImageBean imageBean = mImageBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            albumImageGridItem = (AlbumImageGridItem) convertView;
        } else {
            albumImageGridItem = new AlbumImageGridItem(mContext);
        }

        if (edit && imageBean.isSelected()){
            albumImageGridItem.getSelectBadgeView().show();
        }else{
            albumImageGridItem.getSelectBadgeView().hide();
        }

        ImageLoaderUtil.getInstance(mContext).displayImage("file://"+ imageBean.getThumbnailPath(), albumImageGridItem.getImageView());
        albumImageGridItem.setTag(imageBean);

        return albumImageGridItem;
    }
}
