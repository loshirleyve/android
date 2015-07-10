package com.yun9.wservice.view.dynamic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgsGroup;
import com.yun9.wservice.model.TopicBean;
import com.yun9.wservice.view.msgcard.MsgCardListActivity;
import com.yun9.wservice.view.msgcard.MsgCardListCommand;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *
 */
public class DynamicSessionFragment extends JupiterFragment {

    private static final Logger logger = Logger.getLogger(DynamicSessionFragment.class);

    public static final String TOPIC_LIMIT_ROW = "5";

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @ViewInject(id = R.id.dynamic_title_tb)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.dynamic_sessions_lv)
    private ListView dynamicSessionList;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    private LinkedList<MsgsGroup> msgsGroups = new LinkedList<>();

    private List<TopicBean> topicBeans;

    private PopupWindow scenePopW;

    private ScenePopListLayout scenePopListLayout;

    private NewDynamicCommand newDynamicCommand;

    /**
     * 使用工厂方法创建一个新的动态实例，
     * 这个动态的使用必须使用此方法创建实例
     */
    public static DynamicSessionFragment newInstance(Bundle args) {
        DynamicSessionFragment fragment = new DynamicSessionFragment();
        if (AssertValue.isNotNull(args)) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }


    @Override
    protected void initViews(View view) {

        this.dynamicSessionList.setAdapter(dynamicSessionAdapter);
        this.dynamicSessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MsgsGroup msgsGroup = (MsgsGroup) view.getTag();
                if (AssertValue.isNotNull(msgsGroup)) {
                    MsgCardListCommand msgCardListCommand = new MsgCardListCommand()
                            .setType(MsgCardListCommand.TYPE_USER_GIVEME)
                            .setFromuserid(msgsGroup.getFromuserid())
                            .setUserid(msgsGroup.getTouserid());
                    //获取用户信息
                    CacheUser cacheUser = UserCache.getInstance().getUser(msgsGroup.getFromuserid());
                    if (AssertValue.isNotNull(cacheUser) && AssertValue.isNotNullAndNotEmpty(cacheUser.getName())) {
                        msgCardListCommand.setTitle(cacheUser.getName());
                    }
                    MsgCardListActivity.start(getActivity(), msgCardListCommand);
                }
            }
        });

        this.titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxHeight = dynamicSessionList.getHeight();
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.4f;
                getActivity().getWindow().setAttributes(lp);
                scenePopW.setHeight(maxHeight);
                scenePopW.showAsDropDown(titleBar);
            }
        });

        this.titleBar.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AssertValue.isNotNull(newDynamicCommand)) {
                    newDynamicCommand = new NewDynamicCommand();
                }
                NewDynamicActivity.start(getActivity(), newDynamicCommand);
            }
        });

        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //暂时不支持分页，全部数据检索
                refresh(null, null);
            }
        });

        this.initPopMenu();

        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private void initPopMenu() {
        scenePopListLayout = new ScenePopListLayout(mContext);
        scenePopW = new PopupWindow(scenePopListLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scenePopW.setOnDismissListener(onDismissListener);
        scenePopW.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        scenePopW.setOutsideTouchable(true);
        scenePopW.setFocusable(true);
        int maxHeight = PublicHelp.getDeviceHeightPixels(getActivity());
        int scWidht = PublicHelp.getDeviceWidthPixels(getActivity());
        scenePopW.setHeight(maxHeight);
        scenePopW.setWidth((scWidht / 3) * 2);
        scenePopW.setAnimationStyle(R.style.secen_popwindow);

        scenePopListLayout.getHotTopicLV().setAdapter(topicAdapter);
        loadTopicData();
    }

    private void loadTopicData() {
        Resource resource = resourceFactory.create("QueryTopicsService");
        resource.param("instid", sessionManager.getInst().getId());
        resource.header(Resource.HEADER.LIMITROW, TOPIC_LIMIT_ROW);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                topicBeans  = (List<TopicBean>) response.getPayload();
            }

            @Override
            public void onFailure(Response response) {
                topicBeans = null;
            }

            @Override
            public void onFinally(Response response) {
                topicAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refresh(String lastupid, String lastdownid) {
        String userid = sessionManager.getUser().getId();
        String instid = sessionManager.getInst().getId();

        if (AssertValue.isNotNullAndNotEmpty(userid) && AssertValue.isNotNullAndNotEmpty(instid)) {
            Resource resource = resourceFactory.create("QueryMsgsGroup");
            resource.param("instid", instid).param("userid", userid);

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    List<MsgsGroup> tempMsgsGroups = (List<MsgsGroup>) response.getPayload();
                    msgsGroups.clear();
                    if (AssertValue.isNotNullAndNotEmpty(tempMsgsGroups)) {
                        for (int i = tempMsgsGroups.size(); i > 0; i--) {
                            MsgsGroup msgsGroup = tempMsgsGroups.get(i - 1);
                            msgsGroups.addFirst(msgsGroup);
                        }
                    }
                    dynamicSessionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    mPtrClassicFrameLayout.refreshComplete();
                }
            });
        }
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 1f;
            getActivity().getWindow().setAttributes(lp);
        }
    };

    private JupiterAdapter dynamicSessionAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return msgsGroups.size();
        }

        @Override
        public Object getItem(int position) {
            return msgsGroups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MsgsGroup msgsGroup = msgsGroups.get(position);
            JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = null;

            if (convertView == null) {
                jupiterRowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
            } else {
                jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }

            jupiterRowStyleSutitleLayout.setTag(msgsGroup);
            jupiterRowStyleSutitleLayout.getTitleTV().setText(msgsGroup.getFromuserid());
            jupiterRowStyleSutitleLayout.getSutitleTv().setText(msgsGroup.getLastcontent());
            jupiterRowStyleSutitleLayout.getTimeTv().setText(DateUtil.timeAgo(msgsGroup.getLastmsgdate()));

            //获取用户信息
            CacheUser cacheUser = UserCache.getInstance().getUser(msgsGroup.getFromuserid());

            if (AssertValue.isNotNull(cacheUser)) {
                ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), jupiterRowStyleSutitleLayout.getMainIV());
                jupiterRowStyleSutitleLayout.getTitleTV().setText(cacheUser.getName());
            }

            return jupiterRowStyleSutitleLayout;
        }
    };

    private JupiterAdapter topicAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (topicBeans != null){
                return topicBeans.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JupiterRowStyleTitleLayout titleLayout;
            if (convertView == null){
                titleLayout = new JupiterRowStyleTitleLayout(DynamicSessionFragment.this.getActivity());
                titleLayout.getArrowRightIV().setVisibility(View.GONE);
                titleLayout.getHotNitoceTV().setVisibility(View.GONE);
                titleLayout.getMainIV().setVisibility(View.GONE);
                final TopicBean bean = topicBeans.get(position);
                titleLayout.getTitleTV().setText(bean.getName());
                titleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MsgCardListCommand msgCardListCommand = new MsgCardListCommand()
                                .setType(MsgCardListCommand.TYPE_TOPIC)
                                .setUserid(sessionManager.getUser().getId());
                        msgCardListCommand.setTitle(bean.getName());
                        msgCardListCommand.setTopic(bean.getName());
                        MsgCardListActivity.start(getActivity(), msgCardListCommand);
                    }
                });
                convertView = titleLayout;
            }
            return convertView;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (AssertValue.isNotNull(newDynamicCommand) && newDynamicCommand.getRequestCode() == requestCode && resultCode == NewDynamicCommand.RESULT_CODE_OK) {
            mPtrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            }, 100);
        }

    }
}
