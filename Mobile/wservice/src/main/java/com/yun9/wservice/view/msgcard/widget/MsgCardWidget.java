package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.FileUtil;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardAttachment;
import com.yun9.wservice.model.State;
import com.yun9.wservice.widget.AlbumImageGridItem;
import com.yun9.wservice.widget.FileItemWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardWidget extends JupiterRelativeLayout {

    private TextView contentTV;
    private TextView locationTV;
    private TextView timeTV;
    private RelativeLayout praiseRL;
    private RelativeLayout fwRL;
    private RelativeLayout commentRL;
    private RelativeLayout mainRl;

    private TextView praiseNumTV;
    private TextView fwNumTV;
    private TextView commentNumTV;
    private TextView actionNumTV;

    private TextView lastCommentContentTV;

    private ImageView praiseIV;

    private JupiterGridView imageGV;

    private JupiterListView docLV;

    private ImageView isNewIv;

    private MsgCard mMsgCard;

    private List<FileBean> imageAttachments = new ArrayList<>();

    private List<FileBean> docAttachments = new ArrayList<>();

    public MsgCardWidget(Context context) {
        super(context);
    }

    public MsgCardWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

        mainRl = (RelativeLayout) findViewById(R.id.msg_card_rl);

        contentTV = (TextView) this.findViewById(R.id.msg_card_content_tv);
        locationTV = (TextView) this.findViewById(R.id.msg_card_location_tv);
        timeTV = (TextView) this.findViewById(R.id.msg_card_time_tv);

        imageGV = (JupiterGridView) this.findViewById(R.id.images_gv);
        docLV = (JupiterListView) findViewById(R.id.doc_lv);

        praiseRL = (RelativeLayout) this.findViewById(R.id.praise_rl);
        praiseNumTV = (TextView) this.findViewById(R.id.praise_num_tv);
        praiseIV = (ImageView) this.findViewById(R.id.praise_iv);

        fwRL = (RelativeLayout) this.findViewById(R.id.fw_rl);
        fwNumTV = (TextView) this.findViewById(R.id.fw_num_tv);

        commentRL = (RelativeLayout) this.findViewById(R.id.comm_rl);
        commentNumTV = (TextView) this.findViewById(R.id.comm_num_tv);

        isNewIv = (ImageView) this.findViewById(R.id.isnew_iv);

        //actionRL = (RelativeLayout) this.findViewById(R.id.action_rl);

        lastCommentContentTV = (TextView) this.findViewById(R.id.msg_card_lastcomment_content_tv);

        imageGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageBrowerActivity.start(mContext, new ImageBrowerCommand().setFileBeans(imageAttachments).setPosition(position));
            }
        });


        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MsgCardWidget);

        try {
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showAttachment)) {
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showAttachment, false);
                View view = this.findViewById(R.id.images_gv);
                if (showMainImage) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showLocation)) {
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showLocation, false);
                View view = this.findViewById(R.id.location_rl);
                if (showMainImage) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showToolbar)) {
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showToolbar, false);
                View view = this.findViewById(R.id.toolbar);
                if (showMainImage) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showLastComment)) {
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showLastComment, false);
                View view = this.findViewById(R.id.msg_card_lastcomment_ll);
                View line = this.findViewById(R.id.main_line);
                if (showMainImage) {
                    view.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
            }
        } finally {
            typedArray.recycle();
        }
    }


    public void buildWithData(MsgCard msgCard) {
        this.mMsgCard = msgCard;

        if (!AssertValue.isNotNull(msgCard))
            return;

        if (State.MsgCard.UN_READ.equals(msgCard.getState())){
            isNewIv.setVisibility(VISIBLE);
        } else {
            isNewIv.setVisibility(GONE);
        }
        imageAttachments.clear();
        docAttachments.clear();

        if (AssertValue.isNotNullAndNotEmpty(msgCard.getAttachments())) {
            for (MsgCardAttachment msgCardAttachment : msgCard.getAttachments()) {

                if (FileBean.FILE_TYPE_IMAGE.equals(msgCardAttachment.getFileType()) && imageAttachments.size() <= 6) {
                    FileBean fileBean = new FileBean();
                    fileBean.setFilePath(msgCardAttachment.getFileId());
                    fileBean.setThumbnailPath(msgCardAttachment.getFileId());
                    fileBean.setAbsolutePath(msgCardAttachment.getFileId());
                    fileBean.setStorageType(FileBean.FILE_STORAGE_TYPE_YUN);
                    fileBean.setType(FileBean.FILE_TYPE_IMAGE);
                    fileBean.setId(msgCardAttachment.getId());
                    imageAttachments.add(fileBean);
                }

                if (FileBean.FILE_TYPE_DOC.equals(msgCardAttachment.getFileType()) && docAttachments.size() <= 6) {
                    FileBean fileBean = new FileBean();
                    CacheFile cacheFile = FileCache.getInstance().getFile(msgCardAttachment.getFileId());

                    if (AssertValue.isNotNull(cacheFile)) {
                        fileBean.setName(cacheFile.getName());
                        fileBean.setSize(FileUtil.getFileSize(cacheFile.getFilesize()));
                    }

                    fileBean.setFilePath(msgCardAttachment.getFileId());
                    fileBean.setThumbnailPath(msgCardAttachment.getFileId());
                    fileBean.setAbsolutePath(msgCardAttachment.getFileId());
                    fileBean.setStorageType(FileBean.FILE_STORAGE_TYPE_YUN);
                    fileBean.setType(FileBean.FILE_TYPE_DOC);
                    fileBean.setId(msgCardAttachment.getId());
                    docAttachments.add(fileBean);
                }
            }

        }

        this.imageGV.setAdapter(imagesGVAdapter);
        this.docLV.setAdapter(docLVAdapter);


        //content
        if (msgCard.getContent() != null) {
            contentTV.setText(msgCard.getContent());
        }

        //location
        if (msgCard.getLocationlabel() != null) {
            locationTV.setText(msgCard.getLocationlabel());
        }

        //createDate
        if (msgCard.getCreatedate() != null) {
            timeTV.setText(DateUtil.timeAgo(msgCard.getCreatedate()));
        }

        //praiseIv
        if(mMsgCard.isMypraise()){
            praiseIV.setImageResource(R.drawable.some_praise);
        }else {
            praiseIV.setImageResource(R.drawable.some_praise1);
        }
        //Praisecount
        praiseNumTV.setText(String.valueOf(msgCard.getPraisecount()));

        //Sharecount
        fwNumTV.setText(String.valueOf(msgCard.getSharecount()));

        //comment
        commentNumTV.setText(String.valueOf(msgCard.getCommentcount()));

        if (AssertValue.isNotNull(msgCard.getLastComment())) {
            lastCommentContentTV.setText(msgCard.getLastComment().getContent());
        }else{
            lastCommentContentTV.setText("");
        }

    }

    private JupiterAdapter imagesGVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return imageAttachments.size();
        }

        @Override
        public Object getItem(int position) {
            return imageAttachments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumImageGridItem albumImageGridItem = null;
            FileBean fileBean = imageAttachments.get(position);

            if (AssertValue.isNotNull(convertView)) {
                albumImageGridItem = (AlbumImageGridItem) convertView;
            } else {
                albumImageGridItem = new AlbumImageGridItem(mContext);
            }

            ImageLoaderUtil.getInstance(mContext).displayImage(fileBean.getThumbnailPath(), albumImageGridItem.getImageView());
            albumImageGridItem.setTag(fileBean);
            return albumImageGridItem;
        }
    };

    private JupiterAdapter docLVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return docAttachments.size();
        }

        @Override
        public Object getItem(int position) {
            return docAttachments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FileItemWidget fileItemWidget = null;
            FileBean fileBean = docAttachments.get(position);

            if (AssertValue.isNotNull(convertView)) {
                fileItemWidget = (FileItemWidget) convertView;
            } else {
                fileItemWidget = new FileItemWidget(mContext);
            }

            fileItemWidget.getIcoImaveView().setImageResource(fileBean.getIcoResource());
            fileItemWidget.getFileNameTV().setText(fileBean.getName());
            fileItemWidget.getFileSizeTV().setText(fileBean.getSize());
            fileItemWidget.getFileTimeTV().setText(fileBean.getDateAdded());
            fileItemWidget.setTag(fileBean);
            fileItemWidget.getStateLL().setTag(fileBean);
            return fileItemWidget;
        }
    };

    public TextView getContentTV() {
        return contentTV;
    }

    public TextView getLocationTV() {
        return locationTV;
    }

    public TextView getTimeTV() {
        return timeTV;
    }

    public RelativeLayout getPraiseRL() {
        return praiseRL;
    }

    public RelativeLayout getFwRL() {
        return fwRL;
    }

    public RelativeLayout getCommentRL() {
        return commentRL;
    }

//    public RelativeLayout getActionRL() {
//        return actionRL;
//    }

    public TextView getPraiseNumTV() {
        return praiseNumTV;
    }

    public TextView getFwNumTV() {
        return fwNumTV;
    }

    public TextView getCommentNumTV() {
        return commentNumTV;
    }

    public TextView getActionNumTV() {
        return actionNumTV;
    }

    public TextView getLastCommentContentTV() {
        return lastCommentContentTV;
    }

    public ImageView getPraiseIV() {
        return praiseIV;
    }

    public JupiterGridView getImageGV() {
        return imageGV;
    }

    public RelativeLayout getMainRl() {
        return mainRl;
    }

    public ImageView getIsNewIv() {
        return isNewIv;
    }
}
