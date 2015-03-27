package com.yun9.mobile.framework.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.adapter.MySigninListAdapter;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.ModelUserCheckinginInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.view.TitleBarView;

public class MySignActivity extends Activity implements AdapterView.OnItemClickListener
{

    private ListView lv_sign;
    private MySigninListAdapter mAdapter;
    private List<ModelUserCheckinginInfo> scheDulingWorks;
    private TitleBarView titleView;
    private int currentMonth; // 当前月份,0~11
    private int clickMonth; // 0~11
	private ProgressDialog progressDialog;
    
    /**选择月份窗口*/
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign_in);

        lv_sign = (ListView) findViewById(R.id.lv_sign);
        titleView = (TitleBarView) findViewById(R.id.sign_title);
        TextView tvTitle = titleView.getTvTitle();
        ImageButton btn_back = titleView.getBtnLeft();

        lv_sign.setOnItemClickListener(this);
        tvTitle.setText(R.string.mySignin);
        tvTitle.setVisibility(View.VISIBLE);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MySignActivity.this.finish();
            }
        });
        
        ImageButton btn_Func = titleView.getBtnFuncNav();
        btn_Func.setVisibility(View.VISIBLE);
        btn_Func.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            	mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
        				.parseColor("#b0000000")));
        		mPopupWindow.showAtLocation(titleView, Gravity.BOTTOM, 0,
        				0);
        		mPopupWindow.setAnimationStyle(R.style.main_pop);
        		mPopupWindow.setOutsideTouchable(true);
        		mPopupWindow.setFocusable(true);
        		mPopupWindow.update();
            }
        });
        
        // 初始化月份弹出框
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        clickMonth = currentMonth;
        ViewGroup mPopView = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.empty_popupmenu, null);
		appendMonthButton(mPopView);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
        
		// 加载考勤
		loadJob();
    }

    private void appendMonthButton(ViewGroup mPopView) {
    	// 一共显示4个月份
    	for (int i = 0;i < 4;i++) {
    		appendMonthButton(mPopView,currentMonth -i);
    	}
	}

	private void appendMonthButton(ViewGroup mPopView,final int month) {
		TextView txtView = new TextView(this);
		LayoutParams txtThieMonthParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 80);
		if (month == currentMonth) {
			txtThieMonthParams.setMargins(25, 400, 25, 10);
			txtView.setText("本月考勤");
		} else {
			txtThieMonthParams.setMargins(25, 20, 25, 10);
			txtView.setText((month + 1)+"考勤");
		}
		txtView.setLayoutParams(txtThieMonthParams);
		txtView.setGravity(Gravity.CENTER);
		txtView.setBackgroundResource(R.drawable.pop_bg);
		txtView.setTextColor(this.getResources().getColor(R.color.blue));
		txtView.setTextSize(16);
		txtView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickMonth = month;
				if (month == currentMonth) {
					loadJob();
				} else {
					loadHistoryJob(DateUtil.getBeginOfMonth(month), DateUtil.getEndOfMonth(month));
				}
				mPopupWindow.dismiss();
			}
		});
		mPopView.addView(txtView);
	}
	
    private void loadJob() {
    	progressDialog = TipsUtil.openDialog(null, false, MySignActivity.this);
    	Resource resource = ResourceUtil
				.get("BizHrAttendanceService");
    	Date beginOfMonth = DateUtil.getBeginOfMonth(Calendar.getInstance().get(Calendar.MONTH));
    	Date endOfMonth = DateUtil.getEndOfMonth(Calendar.getInstance().get(Calendar.MONTH));
		resource.param("begindate", beginOfMonth.getTime());	
		resource.param("enddate", endOfMonth.getTime());	
		resource.invok(loadJsonCallBack);
	}
    
    private void loadHistoryJob(Date beginOfMonth,Date endOfMonth) {
    	progressDialog = TipsUtil.openDialog(null, false, MySignActivity.this);
    	Resource resource = ResourceUtil
				.get("BizHrAttendanceQueryService");
		resource.param("begindate", beginOfMonth.getTime());	
		resource.param("enddate", endOfMonth.getTime());	
		resource.invok(loadJsonCallBack);
	}

	private AsyncHttpResponseCallback loadJsonCallBack = new AsyncHttpResponseCallback() {

        @Override
        public void onSuccess(Response response) {
        	progressDialog.dismiss();
            scheDulingWorks = (List<ModelUserCheckinginInfo>) response.getPayload();
            if (scheDulingWorks != null && scheDulingWorks.size() > 0)
            {
                Toast.makeText(MySignActivity.this,(clickMonth +1) + "月份有"+scheDulingWorks.size()+"条考勤记录",Toast.LENGTH_SHORT).show();
            }else
            {
            	scheDulingWorks = new ArrayList<ModelUserCheckinginInfo>();
                Toast.makeText(MySignActivity.this,(clickMonth +1) + "月份没有考勤记录",Toast.LENGTH_SHORT).show();
            }
            lv_sign.setVisibility(View.VISIBLE);
            mAdapter = new MySigninListAdapter(MySignActivity.this, scheDulingWorks);
            lv_sign.setAdapter(mAdapter);
        }

        @Override
        public void onFailure(Response response) {
        	progressDialog.dismiss();
            Toast.makeText(MySignActivity.this,"获取考勤记录失败" + response.getCause(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
    {
    	
    }
}
