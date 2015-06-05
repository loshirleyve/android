package com.yun9.wservice.view.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterPagingListView;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgSession;
import com.yun9.wservice.view.msgcard.MsgCardListActivity;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.LinkedList;

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

    @ViewInject(id=R.id.dynamic_title_tb)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id=R.id.dynamic_sessions_lv)
    private JupiterPagingListView dynamicSessionList;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    private MenuDrawer mMenuDrawer;

    private DynamicSessionAdapter dynamicSessionAdapter;

    private LinkedList<MsgSession> msgSessions;


    /**
     * 使用工厂方法创建一个新的动态实例，
     * 这个动态的使用必须使用此方法创建实例
     *
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
        mMenuDrawer = (MenuDrawer) view.findViewById(R.id.drawerMenu);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

        this.dynamicSessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MsgSession msgSession = (MsgSession) view.getTag();

                logger.d("动态会话表格点击。" + msgSession.getFromusername());

                Bundle bundle = new Bundle();
                bundle.putString(MsgCardListActivity.ARG_TYPE, "toUser");
                bundle.putString(MsgCardListActivity.ARG_VALUE, "186");

                MsgCardListActivity.start(DynamicSessionFragment.this.mContext, bundle);
            }
        });

        this.titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuDrawer.toggleMenu();
            }
        });

        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh(false);
            }
        });
        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrClassicFrameLayout.autoRefresh();
            }
        },100);

        //设置加载更多
        dynamicSessionList.setPagingableListener(new JupiterPagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                refresh(true);
            }
        });

        dynamicSessionList.setHasMoreItems(true);
    }

    private void refresh(final boolean loadMore){
        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh(loadMore);
            }
        }, 2000);
    }

    private void completeRefresh(boolean loadMore){
        this.initMsgSession(loadMore);

        if (!AssertValue.isNotNull(dynamicSessionAdapter)){
            dynamicSessionAdapter = new DynamicSessionAdapter(mContext,msgSessions);
            dynamicSessionList.setAdapter(dynamicSessionAdapter);
        }else{
            dynamicSessionAdapter.notifyDataSetChanged();
        }

        if (loadMore){
            dynamicSessionList.onFinishLoading(true);
        }else{
            mPtrClassicFrameLayout.refreshComplete();
        }

    }

    private int dpToPx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }


    private void initMsgSession(boolean loadMore){
        if (!AssertValue.isNotNull(msgSessions)){
            msgSessions = new LinkedList<>();
        }

        int size = msgSessions.size();
        int maxsize = size + 5;


        for(int i = size;i< maxsize;i++){
            MsgSession msgSession = new MsgSession();
            msgSession.setFromusername("Leon" + i);
            if (loadMore){
                msgSessions.addLast(msgSession);
            }else{
                msgSessions.addFirst(msgSession);
            }

        }
    }


}
