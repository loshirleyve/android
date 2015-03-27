package com.yun9.mobile.framework.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.calendar.impl.ImplScheduleEntrance;
import com.yun9.mobile.calendar.interfaces.ScheduleEntrance;
import com.yun9.mobile.framework.activity.DaKaActiviy.ListAdapter.ViewMonth;
import com.yun9.mobile.framework.fragment.DaKaFragment;
import com.yun9.mobile.framework.util.DateUtil;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： DaKaActiviy 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-7下午2:41:29 修改人：ruanxiaoyu 修改时间：2015-1-7下午2:41:29 修改备注：
 * 
 * @version
 * 
 */
public class DaKaActiviy extends FragmentActivity {
	private Context mContext;
	private ImageButton returnButton;
	private TextView title_txt;
	private ListView monthgroupListView;
	private TextView day;
	private List<String> days;
	private TextView postion;
	private TextView setup;
	// 选择考勤日期
	private PopupWindow mPopupWindow;
	private View popupContentView;
	private LayoutInflater listContainer; // 视图容器

	// 打卡的
	private LinearLayout contactLayout;
	private TextView contactCommit;

	private Map<String, Object> params;
	private EditText searchText;
	private ImageView delimage;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daka);
		initWeight();
	}

	public void initWeight() {
		this.days = getDays();
		mContext = getApplicationContext();
		params = getParams(0);
		returnButton = (ImageButton) findViewById(R.id.return_btn);
		title_txt = (TextView) findViewById(R.id.title_txt);
		setup = (TextView) findViewById(R.id.setup);
		searchText = (EditText) findViewById(R.id.edit_operator_name);
		delimage = (ImageView) findViewById(R.id.del);
		setup.setOnClickListener(setuponclick);
		postion = (TextView) findViewById(R.id.postion);
		// 当前的月份，日，星期 默认为系统时间
		title_txt.setText(Calendar.getInstance().get(Calendar.MONTH) + 1 + "月"
				+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "日 星期"
				+ DateUtil.getDayOfWeek());
		contactLayout = (LinearLayout) findViewById(R.id.commitContactLayout);
		contactLayout.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b0000000")));
		contactCommit = (TextView) findViewById(R.id.commit_contact);
		contactCommit.setText("打卡");
		replace();
		bindEvn();
	}

	private void bindEvn() {
		title_txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPopupWindow == null) {
					LayoutInflater mLayoutInflater = LayoutInflater
							.from(mContext);
					popupContentView = mLayoutInflater.inflate(
							R.layout.select_attendance_month, null);
					day = (TextView) popupContentView.findViewById(R.id.day);
					day.setOnClickListener(dayonclick);
					monthgroupListView = (ListView) popupContentView
							.findViewById(R.id.month_group);
					monthgroupListView.setAdapter(new ListAdapter());

					mPopupWindow = new PopupWindow(popupContentView, 300,
							LayoutParams.WRAP_CONTENT, true);
				}
				mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
				mPopupWindow.setOutsideTouchable(true);
				int xPos = mPopupWindow.getWidth() / 2;

				mPopupWindow.showAsDropDown(postion, -xPos, 0);
			}

		});

		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		searchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				delimage.setVisibility(View.VISIBLE);
				String searchStr = searchText.getText().toString();
				params.put("username", searchStr);
				if (searchText.getText().length() == 0) {
					delimage.setVisibility(View.GONE);
				}
				replace();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		});
		delimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText.getText().clear();
				delimage.setVisibility(View.GONE);
			}
		});

		contactCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(mContext, SignActivity.class);
				startActivity(intent);

			}
		});

	}

	class ListAdapter extends BaseAdapter {
		public final class ViewMonth {
			public TextView month;
			public TextView month_shu;
		}

		ViewMonth viewMonth;

		public ListAdapter() {
			listContainer = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return days.size();
		}

		@Override
		public Object getItem(int position) {
			return days.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = listContainer.inflate(
						R.layout.select_month_listview_items, null);
				viewMonth = new ViewMonth();
				viewMonth.month = (TextView) convertView
						.findViewById(R.id.month);
				viewMonth.month_shu = (TextView) convertView
						.findViewById(R.id.month_shu);
				convertView.setTag(viewMonth);
				convertView.setOnClickListener(onitemclick);
			} else {
				viewMonth = (ViewMonth) convertView.getTag();
			}
			viewMonth.month
					.setText(Calendar.getInstance().get(Calendar.MONTH)
							+ 1
							+ "月"
							+ days.get(position)
							+ "日 星期"
							+ DateUtil.getDayOfWeek(Integer.parseInt(days
									.get(position))));
			viewMonth.month_shu.setText(days.get(position));
			return convertView;
		}

	}

	OnClickListener onitemclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ViewMonth month = (ViewMonth) v.getTag();
			int pmonth = Integer.parseInt(month.month_shu.getText().toString()
					.trim());// 比实际月份少1
			title_txt.setText(month.month.getText());
			params = getParams(pmonth);
			replace();
			mPopupWindow.dismiss();
		}
	};

	public void replace() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DaKaFragment daKaFragment = new DaKaFragment(this, params);
		ft.replace(R.id.fl_content, daKaFragment, DaKaActiviy.class.getName());
		ft.commit();
	}

	public List<String> getDays() {
		List<String> days = new ArrayList<String>();
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		for (int m = day - 1; m > 0; m--) {
			days.add(String.valueOf(m));
		}
		return days;
	}

	// 加载时显示当天考勤
	@SuppressLint("SimpleDateFormat")
	public Map<String, Object> getParams(int day) {
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date daystart = new Date();
		Date dayend = new Date();
		if (day != 0) {
			daystart = DateUtil.getStartDay(day);
			dayend = DateUtil.getEndDay(day);

		}
		try {
			daystart = formater2.parse(formater.format(daystart) + " 00:00:00");
			dayend = formater2.parse(formater.format(dayend) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date startdate = daystart;
		Date enddate = dayend;
		params.put("startdate", startdate.getTime());
		params.put("enddate", enddate.getTime());
		return params;
	}

	OnClickListener dayonclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			title_txt.setText(Calendar.getInstance().get(Calendar.MONTH) + 1
					+ "月 " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
					+ "日 星期" + DateUtil.getDayOfWeek());
			params = getParams(0);
			replace();
			mPopupWindow.dismiss();
		}
	};

	OnClickListener setuponclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ScheduleEntrance schedule = new ImplScheduleEntrance(
					DaKaActiviy.this);
			schedule.checkScheduleInfo();
		}
	};

}
