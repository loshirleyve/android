package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCardAttachment;
import com.yun9.wservice.view.common.Constants;
import com.yun9.wservice.view.common.SimpleImageActivity;

import java.util.List;

/**
 * Created by huangbinglong on 15/5/21.
 */
public class MsgCardImageLayout extends JupiterRelativeLayout {

    private List<MsgCardAttachment> attachments;

    protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
    protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

    protected AbsListView listView;

    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = true;

    public MsgCardImageLayout(Context context) {
        super(context);
    }

    public MsgCardImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardImageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_msg_card_image_grid;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        listView = (GridView) this.findViewById(R.id.grid);
    }

    /**
     * 必须手动设置这个值，图片才能正常显示
     * @param attachments
     */
    public void buildWithData(List<MsgCardAttachment> attachments) {
        this.attachments =attachments;
        ((GridView) listView).setAdapter(new ImageAdapter(getContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });
        applyScrollListener();
    }

    private class ImageAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        private DisplayImageOptions options;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.turns)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }

        @Override
        public int getCount() {
            return attachments.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.widget_msg_card_image_grid_item, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ImageLoader.getInstance()
                    .displayImage(attachments.get(position).getFileid(), holder.imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            holder.progressBar.setProgress(0);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            holder.progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }

    protected void startImagePagerActivity(int position) {
        Intent intent = new Intent(getContext(), SimpleImageActivity.class);
        intent.putExtra(Constants.IMAGE.IMAGE_POSITION, position);
        String[] images = getImageIdsFromAttachments();
        intent.putExtra(Constants.IMAGE.IMAGE_LIST, images);
        getContext().startActivity(intent);
    }

    private String[] getImageIdsFromAttachments() {
        String[] imageIds = new String[getAttachments().size()];
        for (int i = 0;i < attachments.size();i++) {
            imageIds[i] = attachments.get(i).getFileid();
        }
        return imageIds;
    }

    private void applyScrollListener() {
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
    }

    public List<MsgCardAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MsgCardAttachment> attachments) {
        this.attachments = attachments;
    }
}
