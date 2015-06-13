package com.yun9.wservice.view.doc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

import org.w3c.dom.Text;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageAlbumItemWidget extends JupiterRelativeLayout{

    private ImageView imageView;
    private TextView infoTV;
    private TextView numTV;

    public LocalImageAlbumItemWidget(Context context) {
        super(context);
    }

    public LocalImageAlbumItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalImageAlbumItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_local_album_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        this.infoTV = (TextView) this.findViewById(R.id.info);
        this.numTV = (TextView) this.findViewById(R.id.num);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getInfoTV() {
        return infoTV;
    }

    public TextView getNumTV() {
        return numTV;
    }
}
