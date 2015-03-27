package com.yun9.mobile.calendar.fragment;

import java.util.List;

import com.yun9.mobile.R;
import com.yun9.mobile.calendar.adapter.ShiftAdapter;
import com.yun9.mobile.calendar.util.TimeUtils;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.hr.ImplInstShiftInfo;
import com.yun9.mobile.framework.impls.hr.ImplUserScheduleInfo;
import com.yun9.mobile.framework.interfaces.hr.InstShiftInfo;
import com.yun9.mobile.framework.interfaces.hr.UserScheduleInfo;
import com.yun9.mobile.framework.model.server.hr.ModelFindInstShiftInfo;
import com.yun9.mobile.framework.model.server.hr.ModelSaveUserScheduleInfo;
import com.yun9.mobile.framework.view.TitleBarView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择班次界面
 * @author Kass
 *
 */
public class SelectShiftFragment extends Fragment {
	// 页面控件
	private View view;
	private ProgressDialog mProgressDialog;
	private TitleBarView shiftSelectTitleBar;
	private TextView title;
	private View leftView;
	private LinearLayout mShiftInfo;
	private ListView shiftSelect;
	private TextView mDate;
	
	private String currentDate = null;
	private ShiftAdapter shiftAdapter = null;
	private List<ModelFindInstShiftInfo> list = null;
	
	private final String TITLE = "选择班次";
	
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
		view = inflater.inflate(R.layout.shift_select, container, false);
		
		initDate();
		initView();
		
		return view;
	}
	
	/**
	 * 初始化数据
	 */
	private void initDate() {
		currentDate = (String) getArguments().get("date");
	}
	
	/**
	 * 初始化View
	 */
	private void initView() {
		shiftSelectTitleBar = (TitleBarView) view.findViewById(R.id.shift_Select_Title);
		mShiftInfo = (LinearLayout) view.findViewById(R.id.shift_Info_LL);
		shiftSelect = (ListView) view.findViewById(R.id.shift_Select_List);
		mDate = (TextView) view.findViewById(R.id.shift_Date_Text);
		
        title = shiftSelectTitleBar.getTvTitle();
        title.setText(TITLE);
        title.setTextColor(Color.BLACK);
        title.setVisibility(View.VISIBLE);
        leftView = shiftSelectTitleBar.getBtnLeft();
        leftView.setVisibility(View.VISIBLE);
        leftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
        
        getInstShiftInfo();
	}
	
	/**
	 * 获取机构班次信息
	 */
	private void getInstShiftInfo() {
		showProgressDialog("获取机构班次信息");
		InstShiftInfo instShiftInfo = new ImplInstShiftInfo();
		instShiftInfo.getInstShiftInfo(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				mProgressDialog.dismiss();
				showShiftList(response);
			}
			
			@Override
			public void onFailure(Response response) {
				mProgressDialog.dismiss();
				showToast("服务器繁忙，请稍后再试");
			}
		});
	}
	
	/**
	 * 显示班次信息
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void showShiftList(Response response) {
		mShiftInfo.setVisibility(View.VISIBLE);
		mDate.setText(currentDate);
		list = (List<ModelFindInstShiftInfo>) response.getPayload();
		shiftAdapter = new ShiftAdapter(getActivity(), list);
		shiftSelect.setAdapter(shiftAdapter);
		shiftSelect.setOnItemClickListener(new ShiftOnItemClickListener());
	}
	
	/**
	 * 点击班次，触发事件
	 *
	 */
	public class ShiftOnItemClickListener implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			saveShiftDialog(arg2);
		}
		
	}
	
	/**
	 * 是否修改班次信息对话框
	 * @param position
	 */
	private void saveShiftDialog(final int position) {
		String label = list.get(position).getLabel();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择班次(" + currentDate + ")");
        builder.setMessage("'" + label + "',确定选择此班次吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveUserScheduleInfo(position);
			}
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
        });
        builder.create().show();
	}
	
	/**
	 * 新增/修改用户排班信息
	 * @param i
	 */
	private void saveUserScheduleInfo(int i) {
		showProgressDialog("设置班次信息");
		long workDate = TimeUtils.getStringToDate(currentDate);
		String type = list.get(i).getSchedulingtype();
		String shiftno = list.get(i).getShiftno();
		final String label = list.get(i).getLabel();
		UserScheduleInfo userScheduleInfo = new ImplUserScheduleInfo();
		userScheduleInfo.saveUserScheduleInfo(workDate, type, shiftno, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				mProgressDialog.dismiss();
				ModelSaveUserScheduleInfo modelSaveUserScheduleInfo = (ModelSaveUserScheduleInfo) response.getPayload();
				long workdate = modelSaveUserScheduleInfo.getWorkdate();
				String date = TimeUtils.getDateToString(workdate);
				showToast(date + "设置为'" + label + "'成功");
				getActivity().finish();
			}

			@Override
			public void onFailure(Response response) {
				mProgressDialog.dismiss();
				showToast("设置失败，请重新选择班次");
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
