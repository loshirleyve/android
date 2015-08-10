package com.yun9.wservice.view.msgcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.MsgFromType;
import com.yun9.wservice.manager.MsgManager;
import com.yun9.wservice.model.Msg;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.view.dynamic.NewDynamicCommand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MsgCardListActivity extends CustomCallbackActivity {
    private String userid;
    private LinkedList<Msg> msgCards = new LinkedList<>();

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

    @BeanInject
    private MsgManager msgManager;

    private NewDynamicCommand newDynamicCommand;

    private Map<Integer, IActivityCallback> activityCallbackMap = new HashMap<>();

    private int baseRequestCode = 10000;

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

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getFromuserid())) {
            CacheUser cacheUser = UserCache.getInstance().getUser(command.getFromuserid());
            if (cacheUser != null && AssertValue.isNotNullAndNotEmpty(cacheUser.getBriefInstname())){
                titleBar.getTitleSutitleTv().setVisibility(View.VISIBLE);
                titleBar.getTitleSutitleTv().setText(cacheUser.getBriefInstname());
            }
            // 如果来自机构
            if (MsgCardListCommand.TYPE_INST_GIVEME.equals(command.getType())
                &&AssertValue.isNotNullAndNotEmpty(command.getInstid())){
                CacheInst inst = InstCache.getInstance().getInst(command.getInstid());
                if (inst != null){
                    titleBar.getTitleSutitleTv().setVisibility(View.GONE);
                    titleBar.getTitleTv().setTextSize(16);
                    titleBar.getTitleTv().setText(inst.getInstname());
                }
            }
        }

        msgCardList.setAdapter(msgCardListAdapter);
        msgCardList.setOnItemClickListener(msgCardOnItemClickListener);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                msgCards.clear();
                msgCardList.setHasMoreItems(true);
                refresh(null, Page.PAGE_DIR_PULL);
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
                    Msg msg = msgCards.get(msgCards.size() - 1);
                    refresh(msg.getId(), Page.PAGE_DIR_PUSH);
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
            Resource resource = resourceFactory.create("QueryMsgBySceneService");
            resource.param("userid", command.getUserid());
            resource.param("fromuserid", command.getFromuserid());
            resource.param("sence", command.getType());
            resource.param("topic", command.getTopic());
            resource.param("instid",command.getInstid());
            resource.page().setDir(dir).setRowid(rowid);


            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    List<Msg> tempMsgs = (List<Msg>) response.getPayload();

                    if (AssertValue.isNotNullAndNotEmpty(tempMsgs) && Page.PAGE_DIR_PULL.equals(dir)) {
                        for (int i = tempMsgs.size(); i > 0; i--) {
                            Msg msgCard = tempMsgs.get(i - 1);
                            msgCards.addFirst(msgCard);
                        }
                    }

                    if (AssertValue.isNotNullAndNotEmpty(tempMsgs) && Page.PAGE_DIR_PUSH.equals(dir)) {
                        for (Msg msg : tempMsgs) {
                            msgCards.addLast(msg);
                        }
                    }

                    if (!AssertValue.isNotNullAndNotEmpty(tempMsgs) && Page.PAGE_DIR_PUSH.equals(dir)) {
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
        IActivityCallback callback = activityCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
            activityCallbackMap.remove(requestCode);
        } else if(resultCode == JupiterCommand.RESULT_CODE_OK){
            setResult(JupiterCommand.RESULT_CODE_OK);
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
            Msg msg = msgCards.get(position);
            return msgManager.getView(MsgCardListActivity.this,convertView,msg);
        }
    };

    private int generateRequestCode() {
        return ++baseRequestCode;
    }

    public int addActivityCallback(IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        int requestCode = generateRequestCode();
        activityCallbackMap.put(requestCode, callback);
        return requestCode;
    }

    public void addActivityCallback(int requestCode,IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        activityCallbackMap.put(requestCode, callback);
    }

}
