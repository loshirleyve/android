package com.yun9.wservice.view.msgcard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.view.JupiterPagerAdapter;
import com.yun9.jupiter.widget.JupiterSegmentedGroup;
import com.yun9.jupiter.widget.JupiterSegmentedGroupAdapter;
import com.yun9.jupiter.widget.JupiterSegmentedItemModel;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardDetailActivity extends JupiterFragmentActivity {

    private MsgCard mMsgCard;

    private MsgCardDetailCommand command;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private String currUserid;

    private String currInstid;

    @ViewInject(id = R.id.msg_card_detail_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.msg_card_info)
    private MsgCardWidget msgCardWidget;

    @ViewInject(id = R.id.msg_card_detail_tab)
    private JupiterSegmentedGroup segmentedGroup;

    private List<JupiterSegmentedItemModel> segmentedItemModels;

    private List<View> segmentListViews;

    private MsgCardCommentListWidget commentView;

    public static void start(Context context, MsgCardDetailCommand command) {
        Intent intent = new Intent(context, MsgCardDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MsgCardListCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (MsgCardDetailCommand) getIntent().getSerializableExtra(MsgCardDetailCommand.PARAM_COMMAND);

        currUserid = sessionManager.getUser().getId();
        currInstid = sessionManager.getInst().getId();

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getMsgCardId()) && AssertValue.isNotNullAndNotEmpty(currUserid)) {
            refresh(command.getMsgCardId(), currUserid);
        }


    }

    private void refresh(final String msgCardId, String userid) {
        Resource resource = resourceFactory.create("QueryMsgCardInfoById");
        resource.param("userid", userid).param("msgcardid", msgCardId);
        final ProgressDialog progressDialog = ProgressDialog.show(MsgCardDetailActivity.this, null, getResources().getString(R.string.app_wating), true);

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                mMsgCard = (MsgCard) response.getPayload();
                if (AssertValue.isNotNull(mMsgCard)) {
                    builderView(mMsgCard);
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                progressDialog.dismiss();
            }
        });
    }


    private void builderView(MsgCard msgCard) {
        msgCardWidget.buildWithData(msgCard);
        this.builderSegmentGroup(msgCard);
        this.builderCommentView(msgCard);
    }


    private void builderSegmentGroup(MsgCard msgCard) {
        this.segmentedItemModels = new ArrayList<>();
        JupiterSegmentedItemModel model =
                new JupiterSegmentedItemModel(R.string.msg_card_comment, R.drawable.com111, R.drawable.com222);

        model.setDesc(msgCard.getCommentcount());
        this.segmentedItemModels.add(model);
        model =
                new JupiterSegmentedItemModel(R.string.msg_card_praise, R.drawable.star1, R.drawable.star2);
        model.setDesc(msgCard.getPraisecount());
        this.segmentedItemModels.add(model);
        model =
                new JupiterSegmentedItemModel(R.string.msg_card_share, R.drawable.fw1, R.drawable.fw2);
        model.setDesc(msgCard.getSharecount());
        this.segmentedItemModels.add(model);

        segmentedGroup.setAdapter(msgCardDetailSegmentedItemAdapter);
    }

    private void builderCommentView(MsgCard msgCard) {
        commentView = new MsgCardCommentListWidget(mContext);
        commentView.buildWithData(msgCard);

        View praiseView = LayoutInflater.from(mContext).inflate(R.layout.widget_msg_card_in_detail_praise, null);
        View shareView = LayoutInflater.from(mContext).inflate(R.layout.widget_msg_card_in_detail_share, null);

        segmentListViews = new ArrayList<>();

        segmentListViews.add(commentView);
        segmentListViews.add(praiseView);
        segmentListViews.add(shareView);


        segmentedGroup.setTabItemAdapter(msgCardDetailViewPagerAdapter);
    }

    private JupiterSegmentedGroupAdapter msgCardDetailSegmentedItemAdapter = new JupiterSegmentedGroupAdapter() {
        @Override
        public int getCount() {
            return segmentedItemModels.size();
        }

        @Override
        public JupiterSegmentedItemModel getTabInfo(int position) {
            return segmentedItemModels.get(position);
        }
    };

    public JupiterPagerAdapter msgCardDetailViewPagerAdapter = new JupiterPagerAdapter() {


        @Override
        public int getCount() {
            return segmentListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方提示这样写
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(segmentListViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(segmentListViews.get(position));//添加页卡
            return segmentListViews.get(position);
        }
    };

}
