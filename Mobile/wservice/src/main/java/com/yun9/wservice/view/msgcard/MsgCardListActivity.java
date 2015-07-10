package com.yun9.wservice.view.msgcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardPraise;
import com.yun9.wservice.view.msgcard.widget.MsgCardWidget;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MsgCardListActivity extends JupiterFragmentActivity {
    private String userid;
    private LinkedList<MsgCard> msgCards = new LinkedList<>();

    private MsgCardListCommand command;
    private Logger logger = Logger.getLogger(MsgCardListActivity.class);

    @ViewInject(id = R.id.msg_card_list_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.msg_card_lv)
    private PagingListView msgCardList;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrFrame;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    public static void start(Activity activity, MsgCardListCommand command) {
        Intent intent = new Intent(activity, MsgCardListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MsgCardListCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (MsgCardListCommand) getIntent().getSerializableExtra(MsgCardListCommand.PARAM_COMMAND);
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            userid = command.getUserid();
        } else {
            userid = sessionManager.getUser().getId();
        }

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.this.finish();
            }
        });

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getTitle())) {
            titleBar.getTitleTv().setText(command.getTitle());
        }

        msgCardList.setAdapter(msgCardListAdapter);
        msgCardList.setOnItemClickListener(msgCardOnItemClickListener);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (AssertValue.isNotNullAndNotEmpty(msgCards)) {
                    refresh(msgCards.get(0).getId(), Page.PAGE_DIR_PULL);
                } else {
                    refresh(null, Page.PAGE_DIR_PULL);
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        msgCardList.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(msgCards)) {
                    MsgCard msgCard = msgCards.get(msgCards.size() - 1);
                    refresh(msgCard.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    msgCardList.onFinishLoading(true);
                }
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    private AdapterView.OnItemClickListener msgCardOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MsgCard msgCard = (MsgCard) view.getTag();
            MsgCardDetailActivity.start(MsgCardListActivity.this, new MsgCardDetailCommand().setMsgCardId(msgCard.getId()));
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_list;
    }

    private void refresh(String rowid, final String dir) {
        if (AssertValue.isNotNull(command)
                && AssertValue.isNotNullAndNotEmpty(command.getUserid())
                && AssertValue.isNotNullAndNotEmpty(command.getType())) {
            Resource resource = resourceFactory.create("QueryMsgCardByScene");
            resource.param("instid", sessionManager.getInst().getId());
            resource.param("userid", command.getUserid());
            resource.param("fromuserid", command.getFromuserid());
            resource.param("sence", command.getType());
            resource.param("topic", command.getTopic());
            resource.page().setDir(dir).setRowid(rowid);


            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    List<MsgCard> tempMsgCards = (List<MsgCard>) response.getPayload();

                    if (AssertValue.isNotNullAndNotEmpty(tempMsgCards) && Page.PAGE_DIR_PULL.equals(dir)) {
                        for (int i = tempMsgCards.size(); i > 0; i--) {
                            MsgCard msgCard = tempMsgCards.get(i - 1);
                            msgCards.addFirst(msgCard);
                        }
                    }

                    if (AssertValue.isNotNullAndNotEmpty(tempMsgCards) && Page.PAGE_DIR_PUSH.equals(dir)) {
                        for (MsgCard msgCard : tempMsgCards) {
                            msgCards.addLast(msgCard);
                        }
                    }

                    if (!AssertValue.isNotNullAndNotEmpty(tempMsgCards) && Page.PAGE_DIR_PUSH.equals(dir)) {
                        Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
                        msgCardList.onFinishLoading(false);
                    }

                    msgCardListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    mPtrFrame.refreshComplete();
                    msgCardList.onFinishLoading(true);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == MsgCardDetailCommand.RESULT_CODE_OK) {
            mPtrFrame.autoRefresh();
        }
    }

    private JupiterAdapter msgCardListAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return msgCards.size();
        }

        @Override
        public Object getItem(int position) {
            return msgCards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MsgCard msgCard = msgCards.get(position);
            final MsgCardWidget msgCardWidget;
            if (convertView == null) {
                msgCardWidget = new MsgCardWidget(mContext);


                msgCardWidget.getPraiseRL().setTag(msgCard);
                msgCardWidget.getPraiseRL().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardPraiseLikeByMsgCardId(msgCard, msgCardWidget);
                        logger.d("点赞！");
                    }
                });
                msgCardWidget.getFwRL().setTag(msgCard);
                msgCardWidget.getCommentRL().setTag(msgCard);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 20, 20, 20);
                msgCardWidget.getMainRl().setLayoutParams(params);
            } else {
                msgCardWidget = (MsgCardWidget) convertView;
            }
            msgCardWidget.setTag(msgCard);
            msgCardWidget.buildWithData(msgCard);
            msgCardWidget.getFwRL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logger.d("转发！");
                }
            });
            msgCardWidget.getCommentRL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsgCard msgCard = (MsgCard) v.getTag();
                    if (AssertValue.isNotNull(msgCard)) {
                        MsgCardDetailActivity.start(MsgCardListActivity.this, new MsgCardDetailCommand().setMsgCardId(msgCard.getId()).setScrollComment(true));
                    }
                }
            });
            return msgCardWidget;
        }
    };

    private void cardPraiseLikeByMsgCardId(final MsgCard msgCard, final MsgCardWidget msgCardWidget) {
        String msgcardId = msgCard.getId();
        if (AssertValue.isNotNull(sessionManager.getUser())) {
            Resource resource = resourceFactory.create("AddPraiseLikeByMsgCardId");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("msgcardid", msgcardId);
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    MsgCardPraise msgCardPraise = (MsgCardPraise) response.getPayload();
                    if (AssertValue.isNotNull(msgCardPraise)) {
                        int praiseNum = Integer.parseInt(msgCardWidget.getPraiseNumTV().getText().toString());
                        if (msgCardPraise.getPraise() == 0) {
                            msgCardWidget.getPraiseIV().setImageResource(R.drawable.some_praise1);
                            if(praiseNum != 0) {
                                msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum - 1));
                            }else {
                                msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum + 1));
                            }
                        } else {
                            msgCardWidget.getPraiseIV().setImageResource(R.drawable.some_praise);
                            msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum + 1));
                        }
                    }
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
