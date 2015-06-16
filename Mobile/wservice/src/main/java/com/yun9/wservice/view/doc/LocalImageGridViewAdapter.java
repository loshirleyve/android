package com.yun9.wservice.view.doc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.model.LocalFileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;

import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class LocalImageGridViewAdapter extends JupiterAdapter {

    private Context mContext;
    private List<LocalFileBean> mLocalFileBeans;
    private boolean edit;

    public LocalImageGridViewAdapter(Context context,boolean edit, List<LocalFileBean> localFileBeans) {
        this.mContext = context;
        this.mLocalFileBeans = localFileBeans;
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
        if (AssertValue.isNotNullAndNotEmpty(mLocalFileBeans)) {
            return mLocalFileBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mLocalFileBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageGridItem albumImageGridItem = null;
        LocalFileBean localFileBean = mLocalFileBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            albumImageGridItem = (AlbumImageGridItem) convertView;
        } else {
            albumImageGridItem = new AlbumImageGridItem(mContext);
        }

        if (edit && localFileBean.isSelected()){
            albumImageGridItem.getSelectBadgeView().show();
        }else{
            albumImageGridItem.getSelectBadgeView().hide();
        }

        ImageLoaderUtil.getInstance(mContext).displayImage("file://"+ localFileBean.getThumbnailPath(), albumImageGridItem.getImageView());
        albumImageGridItem.setTag(localFileBean);

        return albumImageGridItem;
    }
}
