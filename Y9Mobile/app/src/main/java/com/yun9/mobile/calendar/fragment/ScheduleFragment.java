package com.yun9.mobile.calendar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.yun9.mobile.R;
import com.yun9.mobile.calendar.activity.SelectShiftActivity;
import com.yun9.mobile.calendar.adapter.CalendarAdapter;
import com.yun9.mobile.calendar.adapter.ScheduleAdapter;
import com.yun9.mobile.calendar.entity.DateInfo;
import com.yun9.mobile.calendar.entity.ShiftInfo;
import com.yun9.mobile.calendar.util.DataUtils;
import com.yun9.mobile.calendar.util.ScheduleUtils;
import com.yun9.mobile.calendar.util.TimeUtils;
import com.yun9.mobile.calendar.widget.CalendarViewPager;
import com.yun9.mobile.calendar.widget.ShiftListView;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.hr.ImplUserScheduleInfo;
import com.yun9.mobile.framework.interfaces.hr.UserScheduleInfo;
import com.yun9.mobile.framework.model.server.hr.ModelQueryUserScheduleInfo;
import com.yun9.mobile.framework.view.TitleBarView;

/**
 * 日历排班界面
 * Created by Kass on 2014/12/8.
 */
public class ScheduleFragment extends Fragment {
	// 页面控件
	private View view;
	private ProgressDialog mProgressDialog;
    private TitleBarView scheduleTitleBar;
	private TextView title;
	private View leftView;
	private LinearLayout mScheduleInfo;
	private TextView mTop;
	private Button mToday;
	private ScrollView mScrollView;
	private TextView mDate, mHint;
	private ShiftListView mScheduleDetail;
	private Button mSelect;

    // ViewPager相关变量
    public CalendarViewPager viewPager = null;
    public MyPagerAdapter pagerAdapter = null;
    private int currentPager = 500;

    // 日历GridView相关变量
    private GridView gridView = null;
    public CalendarAdapter adapter = null;
    private GridView currentView = null;
    public List<DateInfo> currentList = null;
    
    // 排班信息ListView相关变量
    private List<ModelQueryUserScheduleInfo> list = null;
    private List<ShiftInfo> shiftList = null;
    private ScheduleAdapter scheduleAdapter;
    
    // 今天的日期
    private int thisYear;
    private int thisMonth;
    private int today;
    private int todayPosition;	// 标记今天的位置
    
    // 当前的日期
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    
    // 排班日期
    private String formatDate;	// 格式化后的日期"yyyy-MM-dd"
    private String beginDate;	// 开始日期
    private String endDate;		// 结束日期
    private long beginTime;		// 开始时间戳
    private long endTime;		// 结束时间戳
    
    private final String TITLE = "我的排班";
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule, container, false);

        init();

        return view;
    }
    
    @Override
	public void onResume() {
		super.onResume();
		getUserScheduleInfo();
	}
    
    private void init() {
    	int mode = getArguments().getInt("MODE");
    	if (mode == 1) {
    		initDate();
    		initView();
    	}
    	else {
    		showToast("非法启动排班");
    		getActivity().finish();
    	}
    }
    
    /**
     * 初始化数据
     */
    private void initDate() {
        thisYear = TimeUtils.getCurrentYear();
        thisMonth = TimeUtils.getCurrentMonth();
        today = TimeUtils.getCurrentDay();
        currentDay = TimeUtils.getCurrentDay();
        beginDate = TimeUtils.getTimeByCurrentDate(thisYear, thisMonth, "begin");
    	endDate = TimeUtils.getTimeByCurrentDate(thisYear, thisMonth, "end");
    	beginTime = TimeUtils.getStringToDate(beginDate);
    	endTime = TimeUtils.getStringToDate(endDate);
    }
    		
    /**
     * 初始化View
     */
    private void initView() {
    	scheduleTitleBar = (TitleBarView) view.findViewById(R.id.schedule_Title);
    	mScheduleInfo = (LinearLayout) view.findViewById(R.id.schedule_Info_LL);
        mTop = (TextView) view.findViewById(R.id.schedule_Top_Text);
        mToday = (Button) view.findViewById(R.id.schedule_Today_Btn);
        mScrollView = (ScrollView) view.findViewById(R.id.schedule_ScrollView);
        viewPager = (CalendarViewPager) view.findViewById(R.id.schedule_Date_VP);
        mDate = (TextView) view.findViewById(R.id.schedule_Date_Text);
        mHint = (TextView) view.findViewById(R.id.schedule_Hint_Text);
        mScheduleDetail = (ShiftListView) view.findViewById(R.id.schedule_Info_List);
        mSelect = (Button) view.findViewById(R.id.schedule_Select_Btn);
        
        title = scheduleTitleBar.getTvTitle();
        title.setText(TITLE);
        title.setTextColor(Color.BLACK);
        title.setVisibility(View.VISIBLE);
        leftView = scheduleTitleBar.getBtnLeft();
        leftView.setVisibility(View.VISIBLE);
        leftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
        
    }
    
    /**
     * 获取用户排班信息
     */
    private void getUserScheduleInfo() {
    	showProgressDialog("获取用户排班信息");
    	UserScheduleInfo userScheduleInfo = new ImplUserScheduleInfo();
    	userScheduleInfo.getUserScheduleInfo(beginTime, endTime, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				mProgressDialog.dismiss();
				showScheduleInfo(response);
			}
			
			@Override
			public void onFailure(Response response) {
				mProgressDialog.dismiss();
				showToast("服务器繁忙，请稍后再试");
			}
		});
    }
    
    /**
     * 显示用户排班信息
     * @param response
     */
    @SuppressWarnings("unchecked")
	private void showScheduleInfo(Response response) {
    	mScheduleInfo.setVisibility(View.VISIBLE);
    	list = (List<ModelQueryUserScheduleInfo>) response.getPayload();
    	showCalendar();
    	showShiftInfo(thisYear, thisMonth, today);
    	mToday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(500);
				adapter.setSelectPosition(todayPosition);
				adapter.notifyDataSetInvalidated();
				currentDay = today;
				showShiftInfo(thisYear, thisMonth, today);
			}
		});
	}
    
    /**
     * 显示日历
     */
    private void showCalendar() {
    	mTop.setText(String.format("%04d年%02d月", thisYear, thisMonth));
    	
    	pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(500);
        viewPager.setPageMargin(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                int year = TimeUtils.getTimeByPosition(position, thisYear, thisMonth, "year");
                int month = TimeUtils.getTimeByPosition(position, thisYear, thisMonth, "month");
                mTop.setText(String.format("%04d年%02d月", year, month));
                currentPager = position;
                currentView = (GridView) viewPager.findViewById(currentPager);
                if (currentView != null) {
                    adapter = (CalendarAdapter) currentView.getAdapter();
                    currentList = adapter.getList();
                    int pos = DataUtils.getDayFlag(currentList, currentDay);
                    adapter.setSelectPosition(pos);
                    adapter.notifyDataSetInvalidated();
                }
                int day = TimeUtils.getDaysOfMonth(year, month);
                if (currentDay > day) {
                	showShiftInfo(year, month, day);
                }
                else {
                	showShiftInfo(year, month, currentDay);
                }
            }

            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
        });
    }
    
	/**
     * ViewPager的适配器，从第500页开始，最多支持0-1000页
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentView = (GridView) object;
            adapter = (CalendarAdapter) currentView.getAdapter();
        }

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	GridView gv = initCalendarView(position);
            gv.setId(position);
            container.addView(gv);
            return gv;
        }
    }
    
    /**
     * 初始化日历的GridView
     * @param position
     * @return
     */
    private GridView initCalendarView(int position) {
        int year = TimeUtils.getTimeByPosition(position, thisYear, thisMonth, "year");
        int month = TimeUtils.getTimeByPosition(position, thisYear, thisMonth, "month");
        String formatDate = TimeUtils.getFormatFirstDay(year, month);
        try {
            currentList = TimeUtils.initCalendar(formatDate, month);
        } catch (Exception e) {
            getActivity().finish();
        }
        gridView = new GridView(getActivity());
        adapter = new CalendarAdapter(getActivity(), currentList);
        if (position == 500) {
            todayPosition = DataUtils.getDayFlag(currentList, today);
            adapter.setTodayPosition(todayPosition);
            adapter.setSelectPosition(todayPosition);
        }
        gridView.setAdapter(adapter);
        gridView.setNumColumns(7);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setGravity(Gravity.CENTER);
        gridView.setOnItemClickListener(new ItemOnItemClickListener());
        return gridView;
    }

    /**
     * 点击日历，触发事件
     */
    public class ItemOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapter.isListener(i) == false) {
                return;
            }
            adapter.setSelectPosition(i);
            adapter.notifyDataSetInvalidated();
            currentYear = TimeUtils.getTimeByPosition(currentPager, thisYear, thisMonth, "year");
            currentMonth = TimeUtils.getTimeByPosition(currentPager, thisYear, thisMonth, "month");
            currentDay = adapter.getDay(i);
            showShiftInfo(currentYear, currentMonth, currentDay);
        }
    }
    
    /**
     * 显示这天的班次信息
     * @param year
     * @param month
     * @param day
     */
    private void showShiftInfo(int year, int month, int day) {
    	mScrollView.smoothScrollTo(0, 0);
    	formatDate = TimeUtils.getFormatDate(year, month, day);
    	shiftList = ScheduleUtils.getShiftInfo(formatDate, list);
    	mDate.setText(formatDate);
    	int num = shiftList.size();
    	if (num == 0) {
    		mHint.setText("还没有选定班次");
    	}
    	else {
    		mHint.setText("有" + num + "个班次信息");
    	}
    	
    	scheduleAdapter = new ScheduleAdapter(getActivity(), shiftList);
    	mScheduleDetail.setAdapter(scheduleAdapter);
        
        mSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ScheduleUtils.isBeforeMarkDate(formatDate, TimeUtils.getFormatDate(thisYear, thisMonth, today))) {
					showToast(formatDate + "已经过了,不能选择班次!");
				}
				else if (ScheduleUtils.isAfterMarkDate(formatDate, endDate)) {
					showToast("不能修改" + endDate + "之后的日期的班次信息");
				}
				else {
					mScheduleInfo.setVisibility(View.INVISIBLE);
					Intent intent = new Intent(getActivity(), SelectShiftActivity.class);
					intent.putExtra("date", formatDate);
					startActivity(intent);
				}
			}
		});
    }
    
	/**
	 * 显示加载对话框
	 * @param title
	 */
	private void showProgressDialog(String title) {
    	mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(title);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正加载，请稍候....");
        mProgressDialog.show();
    }
    
    /**
     * @param msg
     */
    private void showToast(String msg) {
    	Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
