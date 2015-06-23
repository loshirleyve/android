package com.yun9.wservice.view.dynamic;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgSession;
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

    private ResideMenu resideMenu;

    private LinkedList<MsgSession> msgSessions = new LinkedList<>();


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
                MsgSession msgSession = (MsgSession) view.getTag();
                if (AssertValue.isNotNull(msgSession)) {
                    MsgCardListActivity.start(getActivity(), new MsgCardListCommand().setType(MsgCardListCommand.TYPE_USER_GIVEME).setFromuserid(msgSession.getFromuserid()).setUserid(msgSession.getTouserid()));
                }
            }
        });

        this.titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        this.titleBar.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewDynamicActivity.start(getActivity(), new NewDynamicCommand());
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
        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrClassicFrameLayout.autoRefresh();
            }
        }, 100);

        // this.setUpMenu();
    }

    private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this.getActivity());
        //resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this.getActivity());
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        for (int i = 0; i < 6; i++) {
            ResideMenuItem itemMenu = new ResideMenuItem(this.getActivity(), R.drawable.write, "话题" + i);
            resideMenu.addMenuItem(itemMenu, ResideMenu.DIRECTION_LEFT);
        }
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
                    List<MsgSession> tempMsgSessions = (List<MsgSession>) response.getPayload();
                    msgSessions.clear();
                    if (AssertValue.isNotNullAndNotEmpty(tempMsgSessions)) {
                        for (int i = tempMsgSessions.size(); i > 0; i--) {
                            MsgSession msgSession = tempMsgSessions.get(i - 1);
                            msgSessions.addFirst(msgSession);
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


//    private int dpToPx(int dp) {
//        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
//    }


    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private JupiterAdapter dynamicSessionAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return msgSessions.size();
        }

        @Override
        public Object getItem(int position) {
            return msgSessions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MsgSession msgSession = msgSessions.get(position);
            JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = null;

            if (convertView == null) {
                jupiterRowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
            } else {
                jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }

            jupiterRowStyleSutitleLayout.setTag(msgSession);
            jupiterRowStyleSutitleLayout.getTitleTV().setText(msgSession.getFromuserid());
            jupiterRowStyleSutitleLayout.getSutitleTv().setText(msgSession.getLastcontent());

            //获取用户信息
            CacheUser cacheUser = UserCache.getInstance().getUser(msgSession.getFromuserid());

            if (AssertValue.isNotNull(cacheUser)) {
                ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), jupiterRowStyleSutitleLayout.getMainIV());
                jupiterRowStyleSutitleLayout.getTitleTV().setText(cacheUser.getName());
            }

            return jupiterRowStyleSutitleLayout;
        }
    };
}
