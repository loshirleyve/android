package com.yun9.wservice.view.doc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.widget.AlbumImageGridItem;

import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class LocalImageGridViewAdapter extends JupiterAdapter {

    private Context mContext;
    private List<FileBean> mFileBeans;
    private boolean edit;

    public LocalImageGridViewAdapter(Context context, boolean edit, List<FileBean> fileBeans) {
        this.mContext = context;
        this.mFileBeans = fileBeans;
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
        if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
            return mFileBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mFileBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageGridItem albumImageGridItem = null;
        FileBean fileBean = mFileBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            albumImageGridItem = (AlbumImageGridItem) convertView;
        } else {
            albumImageGridItem = new AlbumImageGridItem(mContext);
        }

        if (edit && fileBean.isSelected()) {
            albumImageGridItem.getSelectBadgeView().show();
        } else {
            albumImageGridItem.getSelectBadgeView().hide();
        }
        ImageLoaderUtil.getInstance(mContext).displayImage(fileBean.getThumbnailPath(), albumImageGridItem.getImageView());
        albumImageGridItem.setTag(fileBean);

        return albumImageGridItem;
    }
}
