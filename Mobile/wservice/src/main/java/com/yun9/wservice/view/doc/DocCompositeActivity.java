package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rxy on 15/6/1.
 * 文件列表界面
 */
public class DocCompositeActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(DocCompositeActivity.class);

    private DocCompositeCommand command;

    private LocalImageCommand localImageCommand;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonbarLL;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_location)
    private JupiterRowStyleSutitleLayout localImageBtn;

    @ViewInject(id = R.id.image_location_gv)
    private JupiterGridView localImagesGV;

    private List<ImageBean> onSelectLocalImages = new ArrayList<>();


    public static void start(Activity activity, DocCompositeCommand command) {
        Intent intent = new Intent(activity, DocCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_composite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取参数
        command = (DocCompositeCommand) this.getIntent().getSerializableExtra("command");

        localImageBtn.setOnClickListener(onLocalFileClickListener);
        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        localImagesGV.setAdapter(localImageSelectsGVAdapter);
        localImagesGV.setOnItemClickListener(onLocalImageGridViewItemClickListener);

    }

    private void initLocalImageGV() {

    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onLocalFileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(localImageCommand)) {
                localImageCommand = new LocalImageCommand().setEdit(true).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(6);
            }

            if (AssertValue.isNotNullAndNotEmpty(onSelectLocalImages)) {
                localImageCommand.setSelectImages(onSelectLocalImages);
            }

            LocalImageActivity.start(DocCompositeActivity.this, localImageCommand);
        }
    };

    private AdapterView.OnItemClickListener onLocalImageGridViewItemClickListener  = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageBrowerActivity.start(DocCompositeActivity.this,new ImageBrowerCommand().setImageBeans(onSelectLocalImages).setPosition(position));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == localImageCommand.getRequestCode() && resultCode == LocalImageCommand.RESULT_CODE_OK) {
            onSelectLocalImages = (List<ImageBean>) data.getSerializableExtra(LocalImageCommand.PARAM_IMAGE);
            localImageSelectsGVAdapter.notifyDataSetChanged();
        }
    }

    private JupiterAdapter localImageSelectsGVAdapter = new JupiterAdapter() {

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(onSelectLocalImages)) {
                return onSelectLocalImages.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return onSelectLocalImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumImageGridItem albumImageGridItem = null;
            ImageBean imageBean = onSelectLocalImages.get(position);

            if (AssertValue.isNotNull(convertView)) {
                albumImageGridItem = (AlbumImageGridItem) convertView;
            } else {
                albumImageGridItem = new AlbumImageGridItem(DocCompositeActivity.this);
            }

            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage("file://" + imageBean.getThumbnailPath(), albumImageGridItem.getImageView());
            albumImageGridItem.setTag(imageBean);
            albumImageGridItem.getDeleteBadgeView().setTag(imageBean);
            albumImageGridItem.getDeleteBadgeView().show();
            albumImageGridItem.getDeleteBadgeView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageBean deleteImageBean = (ImageBean) v.getTag();
                    onSelectLocalImages.remove(deleteImageBean);
                    localImageSelectsGVAdapter.notifyDataSetChanged();
                }
            });


            return albumImageGridItem;
        }
    };
}
