package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterBadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class ImageFormCell extends FormCell {

    public static final int DEFAULT_IMAGE_NUM_LIMIT = 3;

    private Context context;

    private LinearLayout imageContainer;

    private ImageButton imageButton;

    private TextView titleDesc;

    private List<ImageView> imageViews;

    private List<JupiterBadgeView> badgeViews;

    private List<String> imageIds;

    private ImageFormCellBean cellBean;

    public ImageFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (ImageFormCellBean) cellBean;
    }


    @Override
    public View getCellView(Context context) {
        this.context = context;
        imageIds = new ArrayList<>(DEFAULT_IMAGE_NUM_LIMIT);
        imageViews = new ArrayList<>(DEFAULT_IMAGE_NUM_LIMIT);
        badgeViews = new ArrayList<>(DEFAULT_IMAGE_NUM_LIMIT);
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_image,null);
        imageButton = (ImageButton) rootView.findViewById(R.id.addimage_ib);
        imageContainer = (LinearLayout) rootView.findViewById(R.id.image_container_ll);
        titleDesc = (TextView) rootView.findViewById(R.id.title_desc);
        titleDesc.setText(cellBean.getLabel());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadImage();
            }
        });
        restore();
        return rootView;
    }

    private void restore() {
        if (cellBean.getValue() == null) {
            return;
        }
        String[] ids = ((String)cellBean.getValue()).split(",");
        rebuildContainer(ids);
    }

    private void rebuildContainer(String[] ids) {
        imageIds.clear();
        imageViews.clear();
        imageContainer.removeAllViews();
        ImageView imageView = null;
        for (int i = 0; i < ids.length; i++) {
            imageIds.add(ids[i]);
            imageView = createCloseableImageView(i);
            imageContainer.addView(imageView);
            imageViews.add(imageView);
        }
        if (imageIds.size() >= DEFAULT_IMAGE_NUM_LIMIT) {
            imageButton.setVisibility(View.GONE);
        } else {
            imageButton.setVisibility(View.VISIBLE);
        }
        appendBadges();
    }

    private void appendBadges() {
        badgeViews.clear();
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(context);
            JupiterBadgeView badgeView = new JupiterBadgeView(context,imageViews.get(i));
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
            badgeView.setBackgroundResource(R.drawable.icn_delete);
            badgeView.setBadgeSize(15,15);
            final int finalI = i;
            badgeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeImageView(finalI);
                }
            });
            badgeView.show();
            badgeViews.add(badgeView);
        }
    }

    private void toggleBadges(boolean isShow) {
        for (int i = 0; i < badgeViews.size(); i++) {
            if (isShow) {
                badgeViews.get(i).show();
            } else {
                badgeViews.get(i).hide();
            }
        }
    }

    private ImageView createCloseableImageView(final int position) {
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(PublicHelp.dip2px(context,60), PublicHelp.dip2px(context,60)));
        imageView.setImageResource(R.drawable.upload_icon);
        return imageView;
    }

    private void removeImageView(int position) {
        imageIds.remove(position);
        rebuildContainer(imageIds.toArray(new String[0]));
    }

    private void startUploadImage() {
        // TODO 调用相册功能，添加图片
        imageIds.add("xx");
        rebuildContainer(imageIds.toArray(new String[0]));
    }

    @Override
    public void edit(boolean edit) {
        if (edit) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startUploadImage();
                }
            });
        } else {
            imageButton.setOnClickListener(null);
        }
        toggleBadges(edit);
    }

    @Override
    public Object getValue() {
        StringBuffer sb = new StringBuffer();
        for (String id : imageIds) {
            sb.append(id).append(",");
        }
        if (sb.length() > 0) {
            return sb.substring(0,sb.length() - 1);
        }
        return sb;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }

}
