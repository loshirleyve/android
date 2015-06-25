package com.yun9.wservice.view.msgcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MsgCardListActivity extends JupiterFragmentActivity {


    private LinkedList<MsgCard> msgCards = new LinkedList<>();

    private MsgCardListCommand command;

    private Logger logger = Logger.getLogger(MsgCardListActivity.class);

    @ViewInject(id = R.id.msg_card_list_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.msg_card_lv)
    private ListView msgCardList;

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

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.this.finish();
            }
        });

        msgCardList.setAdapter(msgCardListAdapter);
        msgCardList.setOnItemClickListener(msgCardOnItemClickListener);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
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

            //logger.d("消息卡片点击！" + msgCard.getMain().getFrom());
            logger.d("消息卡片点击！-----------------------");

            Bundle bundle = new Bundle();
            bundle.putSerializable(MsgCardDetailActivity.ARG_MSG_CARD, msgCard);
            MsgCardDetailActivity.start(MsgCardListActivity.this, bundle);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_list;
    }

    private void refresh() {
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid()) && AssertValue.isNotNullAndNotEmpty(command.getFromuserid()) && AssertValue.isNotNullAndNotEmpty(command.getType())){
            Resource resource = resourceFactory.create("QueryMsgCardByScene");
            resource.param("instid",sessionManager.getInst().getId());
            resource.param("userid",command.getUserid());
            resource.param("fromuserid",command.getFromuserid());
            resource.param("sence",command.getType());

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    List<MsgCard> tempMsgCards = (List<MsgCard>) response.getPayload();
                    msgCards.clear();

                    if (AssertValue.isNotNullAndNotEmpty(tempMsgCards)){
                        for (int i = tempMsgCards.size(); i > 0; i--) {
                            MsgCard msgCard = tempMsgCards.get(i - 1);
                            msgCards.addFirst(msgCard);
                        }
                    }

                    msgCardListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext,response.getCause(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    mPtrFrame.refreshComplete();
                }
            });
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
            MsgCard msgCard = msgCards.get(position);

            MsgCardWidget msgCardWidget = null;

            if (convertView == null) {
                msgCardWidget = new MsgCardWidget(mContext);
                msgCardWidget.getPraiseRL().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logger.d("点赞！");
                    }
                });
                msgCardWidget.getFwRL().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logger.d("转发！");
                    }
                });
                msgCardWidget.getCommentRL().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logger.d("评论！");
                    }
                });
                msgCardWidget.getActionRL().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logger.d("动作！");
                    }
                });
            }else{
                msgCardWidget = (MsgCardWidget) convertView;
            }

            msgCardWidget.buildWithData(msgCard);
            msgCardWidget.setTag(msgCard);

            return msgCardWidget;        }
    };
}
