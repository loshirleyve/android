package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.DateSection;
import com.yun9.wservice.model.PayRegisterAnalysis;
import com.yun9.wservice.model.PayRegisterCollectAnalysis;
import com.yun9.wservice.model.WorkorderAnalysis;
import com.yun9.wservice.model.WorkorderAnalysisUser;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by huangbinglong on 15/8/28.
 */
public class WorkorderAnalysisActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.selected_time_line_tv)
    private TextView selectedTimeLineTv;

    @ViewInject(id=R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id=R.id.analysis_list_ptr)
    private PagingListView analysisListView;

    @ViewInject(id=R.id.allnums_tv)
    private TextView allNumsTv;

    @ViewInject(id=R.id.complete_nums_tv)
    private TextView completeNumsTv;

    @ViewInject(id=R.id.in_service_nums)
    private TextView inserviceNumsTv;

    @ViewInject(id=R.id.completerate_tv)
    private TextView completerateTv;

    @ViewInject(id=R.id.wait_nums_tv)
    private TextView waitNumsTv;

    @ViewInject(id=R.id.laterate_tv)
    private TextView laterateTv;

    private PopupWindow timeLineWindow;

    private ShowTimeLinesWidget showTimeLinesWidget;

    private DateSection selectedDateSection;

    private List<WorkorderAnalysisUser> analysisList;

    private String pullRowid = null;

    private String pushRowid = null;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Context context) {
        Intent intent = new Intent(context, WorkorderAnalysisActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analysisList = new ArrayList<>();
        buildView();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkorderAnalysisActivity.this.finish();
            }
        });

        analysisListView.setAdapter(adapter);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                analysisListView.setHasMoreItems(true);
                pullRowid = null;
                analysisList.clear();
                refresh(pullRowid, Page.PAGE_DIR_PULL);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        analysisListView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(pushRowid)) {
                    refresh(pushRowid, Page.PAGE_DIR_PUSH);
                } else {
                    analysisListView.onFinishLoading(true);
                }
            }
        });

        // 初始化选择时间段窗口
        initPopWindow();
        selectedTimeLineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWindow();
            }
        });
    }

    private void refresh(final String rowid, final String dir) {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshAnalysis(rowid, dir);
            }
        }, 100);
    }

    private void autoRefresh(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private void refreshAnalysis(String rowid, final String dir) {
        if (selectedDateSection == null){
            return;
        }
        final Resource resource = resourceFactory.create("QueryWorkorderAnalysisUserService");
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("beginDate",selectedDateSection.getBegindate());
        resource.param("endDate",selectedDateSection.getEnddate());
        resource.page().setRowid(rowid).setDir(dir);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<WorkorderAnalysisUser> temps = (List<WorkorderAnalysisUser>) response.getPayload();
                if (temps != null && temps.size() > 0) {
                    if (Page.PAGE_DIR_PULL.equals(dir)) {
                        pullRowid = temps.get(0).getId();
                        analysisList.addAll(0, temps);
                        if (!AssertValue.isNotNullAndNotEmpty(pushRowid)) {
                            pushRowid = temps.get(temps.size() - 1).getId();
                        }
                        if (temps.size() < Integer.valueOf(resource.page().getSize())) {
                            analysisListView.setHasMoreItems(false);
                        }
                    } else {
                        pushRowid = temps.get(temps.size() - 1).getId();
                        analysisList.addAll(temps);
                    }
                } else if (Page.PAGE_DIR_PULL.equals(dir)) {
                    showToast("没有新数据");
                } else if (Page.PAGE_DIR_PUSH.equals(dir)) {
                    analysisListView.setHasMoreItems(false);
                    showToast(R.string.app_no_more_data);
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                ptrClassicFrameLayout.refreshComplete();
                analysisListView.onFinishLoading(true);
            }
        });

    }

    // 打开选择框
    private void openWindow() {
        if (timeLineWindow != null) {
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.alpha = 1f;
            this.getWindow().setAttributes(lp);
            showTimeLinesWidget.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = showTimeLinesWidget.getMeasuredWidth();
            int[] location = new int[2];
            selectedTimeLineTv.getLocationOnScreen(location);
            timeLineWindow.showAtLocation(selectedTimeLineTv, Gravity.NO_GRAVITY,
                    PublicHelp.dip2px(getApplicationContext(),(titleBarLayout.getWidth() - popupWidth) / 2),
                    location[1]+selectedTimeLineTv.getHeight()+3);
        }
    }

    // 初始化选择框
    private void initPopWindow() {
        showTimeLinesWidget = new ShowTimeLinesWidget(mContext);
        timeLineWindow = new PopupWindow(showTimeLinesWidget, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        timeLineWindow.setOnDismissListener(onDismissListener);
        timeLineWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timeLineWindow.setOutsideTouchable(true);
        timeLineWindow.setFocusable(true);
        showTimeLinesWidget.setCallback(new SelectedTimeLineCallback() {
            @Override
            public void callback(DateSection timeLine) {
                if (!(selectedDateSection != null && timeLine != null
                        && selectedDateSection.getId().equals(timeLine.getId()))) {
                    selectedDateSection = timeLine;
                    loadBaseInfo();
                    autoRefresh();
                }
                selectedDateSection = timeLine;
                refreshDateSection();
            }
        });
        loadTimeLines();
    }

    private void loadTimeLines() {
        Resource resource = resourceFactory.create("QueryDateSectionByNameService");
        resource.param("name", "GD");
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<DateSection> dateSections = (List<DateSection>) response.getPayload();
                if (dateSections != null) {
                    showTimeLinesWidget.buildWithData(dateSections);
                    // 找到默认的一个
                    setupDefaultDateSection(dateSections);
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void setupDefaultDateSection(List<DateSection> dateSections) {
        for (DateSection section : dateSections){
            if (section.getIsdefault() == 1){
                selectedDateSection = section;
                loadBaseInfo();
                autoRefresh();
            }
        }
        refreshDateSection();
    }

    private void refreshDateSection() {
        if (selectedDateSection == null){
            selectedTimeLineTv.setText("");
        } else {
            selectedTimeLineTv.setText(selectedDateSection.getLabel());
        }
        if (timeLineWindow.isShowing()){
            timeLineWindow.dismiss();
        }
    }

    private void loadBaseInfo() {
        final Resource resource = resourceFactory.create("QueryWorkorderAnalysisService");
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("beginDate",selectedDateSection.getBegindate());
        resource.param("endDate",selectedDateSection.getEnddate());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                WorkorderAnalysis analysis = (WorkorderAnalysis) response.getPayload();
                if (analysis != null) {
                    allNumsTv.setText(analysis.getAllNums() + "单");
                    completeNumsTv.setText(analysis.getCompleteNums() + "单");
                    inserviceNumsTv.setText(analysis.getInserviceNums() + "单");
                    completerateTv.setText(analysis.getComleterate()*100 + "%");
                    waitNumsTv.setText(analysis.getWaitNums() + "单");
                    laterateTv.setText(analysis.getLaterate()*100 + "%");
                } else {
                    cleanBaseInfo();
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
                cleanBaseInfo();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void cleanBaseInfo() {
        allNumsTv.setText("");
        completeNumsTv.setText("");
        inserviceNumsTv.setText("");
        completerateTv.setText("");
        waitNumsTv.setText("");
        laterateTv.setText("");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_workorder_analysis;
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (analysisList != null){
                return analysisList.size();
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
            WorkorderAnalysisUser analysis = analysisList.get(position);
            WorkorderAnalysisUserWidget widget = null;
            if (convertView == null){
                widget = new WorkorderAnalysisUserWidget(WorkorderAnalysisActivity.this);
                convertView = widget;
            } else {
                widget = (WorkorderAnalysisUserWidget) convertView;
            }
            buildWidgetWithData(widget,analysis);
            return convertView;
        }
    };

    private void buildWidgetWithData(WorkorderAnalysisUserWidget widget, final WorkorderAnalysisUser analysis) {
        CacheUser cacheUser = UserCache.getInstance().getUser(analysis.getUserid());
        if (cacheUser != null){
            widget.getUserNameTv().setText(cacheUser.getName());
        }
        widget.getAllNumsTv().setText(analysis.getAllNums()+"");
        widget.getCompleteNumsTv().setText(analysis.getCompleteNums()+"");
        widget.getWaitNumsTv().setText(analysis.getWaitNums()+"");
        widget.getCompleteRateTv().setText(analysis.getComleterate()*100+"%");

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = WorkorderAnalysisActivity.this.getWindow().getAttributes();
            lp.alpha = 1f;
            WorkorderAnalysisActivity.this.getWindow().setAttributes(lp);
        }
    };
}
