package com.yun9.wservice.view.msgcard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterSegmentedGroup;
import com.yun9.jupiter.widget.JupiterSegmentedItem;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardComment;
import com.yun9.wservice.model.MsgCardPraise;
import com.yun9.wservice.model.MsgCardProcessAction;
import com.yun9.wservice.model.MsgCardShare;
import com.yun9.wservice.view.msgcard.model.MsgCardPanelActionItem;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailCommentWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailPraiseWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailShareWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailToolbarPanelPageWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailToolbarPanelWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardDetailToolbarTabWidget;
import com.yun9.wservice.view.msgcard.widget.MsgCardWidget;

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

    @ViewInject(id = R.id.scrollView)
    private ScrollView scrollView;

    @ViewInject(id = R.id.msg_card_detail_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.msg_card_info)
    private MsgCardWidget msgCardWidget;

    @ViewInject(id = R.id.segmented_group)
    private JupiterSegmentedGroup segmentedGroup;

    @ViewInject(id = R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(id = R.id.common_item)
    private JupiterSegmentedItem commonItem;

    @ViewInject(id = R.id.share_item)
    private JupiterSegmentedItem shareItem;

    @ViewInject(id = R.id.praise_item)
    private JupiterSegmentedItem praiseItem;

    @ViewInject(id = R.id.msg_card_detail_bottom_toolbar)
    private MsgCardDetailToolbarTabWidget toolbarTabWidget;

    @ViewInject(id = R.id.msg_card_detail_bottom_toolbar_panel)
    private MsgCardDetailToolbarPanelWidget toolbarPanelWidget;

    private List<View> segmentListViews;


    private MsgCardDetailCommentWidget commentView;
    private MsgCardDetailPraiseWidget praiseView;
    private MsgCardDetailShareWidget shareView;

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

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getTitle())) {
            titleBar.getTitleTv().setText(command.getTitle());
        }

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commentView = new MsgCardDetailCommentWidget(mContext);
        praiseView = new MsgCardDetailPraiseWidget(mContext);
        shareView = new MsgCardDetailShareWidget(mContext);

        segmentedGroup.setOnItemClickListener(new JupiterSegmentedGroup.OnItemClickListener() {
            @Override
            public void onItemClick(JupiterSegmentedItem view, int position) {
                viewPager.setCurrentItem(position);
            }
        });

        segmentedGroup.selectItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                segmentedGroup.selectItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        toolbarTabWidget.getActionLayout().setOnClickListener(onActionClickListener);
        toolbarTabWidget.getCommentLayout().setOnClickListener(onCommentClickListener);
        toolbarTabWidget.getForwardLayout().setOnClickListener(onForwardClickListener);
        toolbarTabWidget.getPraiseLayout().setOnClickListener(onPraiseClickListener);

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getMsgCardId()) && AssertValue.isNotNullAndNotEmpty(currUserid)) {
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    refresh(command.getMsgCardId(), currUserid);
                }
            };
            handler.sendEmptyMessageDelayed(0, 500);
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
                    refreshComplete();
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

    private void refreshComplete() {
        //TODO 由于还没有返回流程数据，暂时使用假数据
        fakeDataProcessAction(mMsgCard);
        builderView(mMsgCard);

        if (AssertValue.isNotNull(command) && command.isScrollComment()) {
            scrollToComment();
            //只是滚动一次
            command.setScrollComment(false);
        }
    }


    private void builderView(MsgCard msgCard) {
        msgCardWidget.buildWithData(msgCard);

        commonItem.getDescTextTV().setText(msgCard.getCommentcount() + "");
        shareItem.getDescTextTV().setText(msgCard.getSharecount() + "");
        praiseItem.getDescTextTV().setText(msgCard.getPraisecount() + "");


        commentView.getCommonLl().removeAllViews();
        shareView.getShareLl().removeAllViews();
        praiseView.getPraiseLl().removeAllViews();

        if (AssertValue.isNotNullAndNotEmpty(msgCard.getComments())) {
            for (MsgCardComment msgCardComment : msgCard.getComments()) {
                commentView.getCommonLl().addView(this.createCommonItem(msgCardComment));
            }
        }

        if (AssertValue.isNotNullAndNotEmpty(msgCard.getPraises())) {
            for (MsgCardPraise msgCardPraise : msgCard.getPraises()) {
                praiseView.getPraiseLl().addView(this.createPraiseItem(msgCardPraise));
            }
        }

        if (AssertValue.isNotNullAndNotEmpty(msgCard.getShares())) {
            for (MsgCardShare msgCardShare : msgCard.getShares()) {
                shareView.getShareLl().addView(this.createShareItem(msgCardShare));
            }
        }
        segmentListViews = new ArrayList<>();
        segmentListViews.add(commentView);
        segmentListViews.add(shareView);
        segmentListViews.add(praiseView);
        viewPager.setAdapter(viewPagerAdapter);
        builderPanelPage(msgCard);

    }

    private JupiterRowStyleSutitleLayout createCommonItem(MsgCardComment msgCardComment) {
        JupiterRowStyleSutitleLayout itemWidget = new JupiterRowStyleSutitleLayout(mContext);
        CacheUser cacheUser = UserCache.getInstance().getUser(msgCardComment.getFrom());
        if (AssertValue.isNotNull(cacheUser)) {
            itemWidget.getTitleTV().setText(cacheUser.getName());
            ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), itemWidget.getMainIV());
        }

        itemWidget.getTimeTv().setText(DateUtil.timeAgo(msgCardComment.getCreatedate()));
        itemWidget.getSutitleTv().setText(msgCardComment.getContent());
        itemWidget.setShowArrow(false);
        return itemWidget;
    }

    private JupiterRowStyleSutitleLayout createPraiseItem(MsgCardPraise msgCardPraise) {
        JupiterRowStyleSutitleLayout itemWidget = new JupiterRowStyleSutitleLayout(mContext);

        CacheUser cacheUser = UserCache.getInstance().getUser(msgCardPraise.getUserid());

        if (AssertValue.isNotNull(cacheUser)) {
            itemWidget.getTitleTV().setText(cacheUser.getName());
            ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), itemWidget.getMainIV());
        }
        itemWidget.getTimeTv().setText(DateUtil.timeAgo(msgCardPraise.getCreatedate()));
        itemWidget.setShowArrow(false);
        itemWidget.setShowSutitleText(false);
        return itemWidget;
    }

    private JupiterRowStyleSutitleLayout createShareItem(MsgCardShare msgCardShare) {
        JupiterRowStyleSutitleLayout itemWidget = new JupiterRowStyleSutitleLayout(mContext);

        CacheUser cacheUser = UserCache.getInstance().getUser(msgCardShare.getFromuserid());
        CacheUser cacheToUser = UserCache.getInstance().getUser(msgCardShare.getTouserid());
        if (AssertValue.isNotNull(cacheUser)) {
            itemWidget.getTitleTV().setText(cacheUser.getName());
            ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), itemWidget.getMainIV());
        }
        itemWidget.getTimeTv().setText(DateUtil.timeAgo(msgCardShare.getCreatedate()));
        if (AssertValue.isNotNull(cacheToUser) && AssertValue.isNotNullAndNotEmpty(cacheToUser.getName())) {
            itemWidget.getSutitleTv().setText(getResources().getString(R.string.msg_card_fw_to, cacheToUser.getName()));
        }
        itemWidget.setShowArrow(false);

        return itemWidget;
    }

    private void scrollToComment() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    scrollView.scrollTo(0, segmentedGroup.getTop());
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 500);
    }

    private PagerAdapter viewPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return segmentListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(segmentListViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            View view = segmentListViews.get(position);
            container.addView(view);//添加页卡
            return view;
        }
    };

    private void builderPanelPage(MsgCard msgCard) {
        List<MsgCardPanelActionItem> panelActionItems = new ArrayList<>();
        List<MsgCardDetailToolbarPanelPageWidget> msgCardDetailToolbarPanelPageWidgets = new ArrayList<>();

        //默认添加掷筛子功能
        panelActionItems.add(new MsgCardPanelActionItem(getResources().getString(R.string.msg_card_sieve), R.drawable.turns, MsgCardPanelActionItem.ActionType.TYPE_TURNS));

        if (AssertValue.isNotNullAndNotEmpty(msgCard.getProcess())) {
            for (MsgCardProcessAction msgCardProcessAction : msgCard.getProcess()) {
                panelActionItems.add(new MsgCardPanelActionItem(msgCardProcessAction));
            }
        }

        //将对象分成8个一页
        if (AssertValue.isNotNullAndNotEmpty(panelActionItems)) {
            int pageNum = panelActionItems.size() / 8 + 1;

            for (int i = 0; i < pageNum; i++) {
                MsgCardDetailToolbarPanelPageWidget page = new MsgCardDetailToolbarPanelPageWidget(mContext);
                List<MsgCardPanelActionItem> tempActionItems = new ArrayList<>();

                int beginIndex = i * 8;
                int endIndex = beginIndex + 8;
                for (int j = beginIndex; j < endIndex; j++) {
                    if (j < panelActionItems.size()) {
                        tempActionItems.add(panelActionItems.get(j));
                    }
                }
                page.buildView(tempActionItems);
                msgCardDetailToolbarPanelPageWidgets.add(page);
            }
        }
        toolbarPanelWidget.builder(msgCardDetailToolbarPanelPageWidgets);
    }

    private View.OnClickListener onActionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (toolbarPanelWidget.getVisibility() == View.VISIBLE) {
                toolbarPanelWidget.setVisibility(View.GONE);
            } else {
                toolbarPanelWidget.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener onCommentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scrollView.scrollTo(0, segmentedGroup.getTop());
        }
    };

    private View.OnClickListener onForwardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onPraiseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getMsgCardId())){
                cardPraiseLikeByMsgCardId(command.getMsgCardId());
            }
            if(mMsgCard.isMypraise()){
                toolbarTabWidget.getMsgCardPraiseIv().setImageResource(R.drawable.star_sel);
            }else {
                toolbarTabWidget.getMsgCardPraiseIv().setImageResource(R.drawable.star1);
            }
        }
    };

    private void fakeDataProcessAction(MsgCard msgCard) {
        msgCard.setProcess(new ArrayList<MsgCardProcessAction>());
        msgCard.getProcess().add(new MsgCardProcessAction("保存表单", "save"));
        msgCard.getProcess().add(new MsgCardProcessAction("同意", "agreed"));
        msgCard.getProcess().add(new MsgCardProcessAction("驳回", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销1", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销2", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销3", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销4", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销5", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销6", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销7", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销8", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销9", "rejected"));
        msgCard.getProcess().add(new MsgCardProcessAction("撤销10", "rejected"));
    }
    private void cardPraiseLikeByMsgCardId(String msgcardId){
        if(AssertValue.isNotNull(sessionManager.getUser())){
            final Resource resource = resourceFactory.create("AddPraiseLikeByMsgCardId");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("msgcardid", msgcardId);
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Toast.makeText(mContext, getString(R.string.msg_card_praise_success), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {

                }
            });
        }
    }
}
