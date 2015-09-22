package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.image.ImageBrowerActivity;
import com.yun9.jupiter.image.ImageBrowerCommand;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.SysFileBean;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.gridview.PagingGridView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.widget.AlbumImageGridItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Leon on 15/6/17.
 */
public class YunImageActivity extends JupiterFragmentActivity {

    private static Logger logger = Logger.getLogger(YunImageActivity.class);

    private boolean mEdit;

    private YunImageCommand command;

    private LinkedList<FileBean> mFileBeans = new LinkedList<>();

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_yun_gv)
    private PagingGridView imageGV;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mFrame;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeBtn;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardBtn;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    public static void start(Activity activity, YunImageCommand command) {
        Intent intent = new Intent(activity, YunImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_yun_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.d("开始初始化云相册");

        command = (YunImageCommand) getIntent().getSerializableExtra(YunFileCommand.PARAM_COMMAND);

        imageGV.setOnItemClickListener(onGridViewItemClickListener);

        titleBarLayout.getTitleRight().setOnClickListener(onEditClickListener);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        completeBtn.setOnClickListener(onCompleteClickListener);
        sendMsgCardBtn.setOnClickListener(onCompleteClickListener);


        imageGV.setHasMoreItems(true);
        imageGV.setPagingableListener(new PagingGridView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                    logger.d("达到加载更多条件执行加载");
                    FileBean fileBean = mFileBeans.get(mFileBeans.size() - 1);
                    refresh(fileBean.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    imageGV.onFinishLoading(true);
                }
            }
        });

        imageGV.setAdapter(gridViewAdapter);

        //设置当前是否编辑状态
        if (AssertValue.isNotNull(command)) {
            this.setEdit(command.isEdit());
        }

        mFrame.setLastUpdateTimeRelateObject(this);
        mFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                    FileBean fileBean = mFileBeans.get(0);
                    refresh(fileBean.getId(), Page.PAGE_DIR_PULL);
                } else {
                    refresh(null, Page.PAGE_DIR_PULL);
                }
            }
        });

        mFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFrame.autoRefresh();
            }
        }, 50);
    }

    private void refresh(String rowid, final String dir) {
        final Resource resource = resourceFactory.create("QueryFile");

        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("level", "user");
        resource.param("filetype", "image");
        resource.page().setRowid(rowid).setDir(dir);

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<SysFileBean> sysFileBeans = (List<SysFileBean>) response.getPayload();

                if (AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = sysFileBeans.size(); i > 0; i--) {
                        FileBean fileBean = new FileBean(sysFileBeans.get(i - 1));

                        //检查当前文件是否已经被选中
                        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectImages())) {
                            for (FileBean onSelectFileBean : command.getSelectImages()) {
                                if (fileBean.getId().equals(onSelectFileBean.getId())) {
                                    fileBean.setSelected(true);
                                }
                            }
                        }

                        mFileBeans.addFirst(fileBean);
                    }

                    gridViewAdapter.notifyDataSetChanged();
                }

                if (AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (SysFileBean sysFileBean : sysFileBeans) {
                        FileBean fileBean = new FileBean(sysFileBean);

                        //检查当前文件是否已经被选中
                        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectImages())) {
                            for (FileBean onSelectFileBean : command.getSelectImages()) {
                                if (fileBean.getId().equals(onSelectFileBean.getId())) {
                                    fileBean.setSelected(true);
                                }
                            }
                        }

                        mFileBeans.addLast(fileBean);
                    }
                    gridViewAdapter.notifyDataSetChanged();
                }

                if (!AssertValue.isNotNullAndNotEmpty(sysFileBeans) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    logger.d("加载更多没有数据。关闭加载更多。");
                    imageGV.onFinishLoading(false);
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinally(Response response) {
                mFrame.refreshComplete();
                imageGV.onFinishLoading(true);
            }
        });
    }

    private int getSelectNum() {
        int num = 0;

        if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
            for (FileBean fileBean : mFileBeans) {
                if (fileBean.isSelected()) {
                    num++;
                }
            }
        }
        return num;
    }

    private void setEdit(boolean editState) {
        this.mEdit = editState;

        if (this.mEdit) {
            this.titleBarLayout.getTitleRightTv().setText(R.string.app_cancel);
        } else {
            this.titleBarLayout.getTitleRightTv().setText(R.string.app_select);
        }

        if (this.mEdit && AssertValue.isNotNull(command) && LocalImageCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            sendMsgCardBtn.setVisibility(View.VISIBLE);
        } else {
            sendMsgCardBtn.setVisibility(View.GONE);
        }

        if (this.mEdit && AssertValue.isNotNull(command) && LocalImageCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeBtn.setVisibility(View.VISIBLE);
        } else {
            completeBtn.setVisibility(View.GONE);
        }

        for (int i = 0; i < imageGV.getCount(); i++) {
            if (imageGV.getChildAt(i) instanceof AlbumImageGridItem) {
                AlbumImageGridItem albumImageGridItem = (AlbumImageGridItem) imageGV.getChildAt(i);
                if (AssertValue.isNotNull(albumImageGridItem) && AssertValue.isNotNull(albumImageGridItem.getTag())) {
                    FileBean fileBean = (FileBean) albumImageGridItem.getTag();
                    if (this.mEdit && fileBean.isSelected()) {
                        albumImageGridItem.getSelectBadgeView().show();
                    } else {
                        albumImageGridItem.getSelectBadgeView().hide();
                    }
                }
            }
        }


    }

    private AdapterView.OnItemClickListener onGridViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mEdit) {
                FileBean fileBean = (FileBean) view.getTag();

                if (!fileBean.isSelected() && AssertValue.isNotNull(command) && command.getMaxSelectNum() > 0) {
                    int selectNum = getSelectNum();
                    if (selectNum >= command.getMaxSelectNum()) {
                        CharSequence charSequence = getResources().getString(R.string.doc_select_num_max, command.getMaxSelectNum());
                        Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (AssertValue.isNotNull(fileBean)) {
                    fileBean.setSelected(!fileBean.isSelected());
                    AlbumImageGridItem albumImageGridItem = (AlbumImageGridItem) view;
                    if (fileBean.isSelected()) {
                        albumImageGridItem.getSelectBadgeView().show();
                    } else {
                        albumImageGridItem.getSelectBadgeView().hide();
                    }
                }
            } else {
                ImageBrowerActivity.start(YunImageActivity.this, new ImageBrowerCommand().setFileBeans(mFileBeans).setPosition(position));
            }
        }
    };


    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setEdit(!mEdit);
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(DocCompositeCommand.PARAM_EDIT, mEdit);
            setResult(YunImageCommand.RESULT_CODE_CANCEL,intent);
            finish();
        }
    };

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(command)) {

                if (YunImageCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
                    ArrayList<FileBean> onSelectImages = new ArrayList<>();
                    if (AssertValue.isNotNullAndNotEmpty(mFileBeans)) {
                        for (FileBean fileBean : mFileBeans) {
                            if (fileBean.isSelected()) {
                                onSelectImages.add(fileBean);
                            }
                        }
                    }

                    Intent intent = new Intent();
                    intent.putExtra(YunImageCommand.PARAM_IMAGE, onSelectImages);
                    intent.putExtra(DocCompositeCommand.PARAM_EDIT, mEdit);
                    setResult(YunImageCommand.RESULT_CODE_OK, intent);
                    finish();
                }

                if (YunImageCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
                    finish();
                }
            } else {
                setResult(YunImageCommand.RESULT_CODE_ERROR);
                finish();
            }
        }
    };

    private JupiterAdapter gridViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return mFileBeans.size();
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

            FileBean tempTag = (FileBean) albumImageGridItem.getTag();
            if (AssertValue.isNotNull(tempTag) && tempTag.getId().equals(fileBean.getFilePath())) {
                //logger.d("相同的图片文件不需要加载图片！");
            } else {
                ImageLoaderUtil.getInstance(mContext).displayImage(fileBean.getFilePath(), albumImageGridItem.getImageView());
            }

            if (mEdit && fileBean.isSelected()) {
                albumImageGridItem.getSelectBadgeView().show();
            } else {
                albumImageGridItem.getSelectBadgeView().hide();
            }

            albumImageGridItem.setTag(fileBean);
            return albumImageGridItem;
        }
    };


}
