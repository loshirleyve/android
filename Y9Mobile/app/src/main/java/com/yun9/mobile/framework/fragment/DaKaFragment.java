package com.yun9.mobile.framework.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.AttendanceInfo;
import com.yun9.mobile.framework.personelservice.AsyncHttpAttendanceInfoCallback;
import com.yun9.mobile.framework.personelservice.BizHrAttendanceService;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.framework.util.TipsUtil;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： DaKaFragment 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-7下午6:07:56 修改人：ruanxiaoyu 修改时间：2015-1-7下午6:07:56 修改备注：
 * 
 * @version
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DaKaFragment extends Fragment {

	private Context mContext;
	private View baseView;
	private TableLayout tablelayout;
	private List<AttendanceInfo> singlist;
	private Map<String, Object> params;
	private ProgressDialog progressDialog;

	public DaKaFragment(Context context, Map<String, Object> params) {
		this.mContext = context;
		this.params = params;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_daka, null);
		loadSinglist();
		return baseView;
	}

	private void loadSinglist() {
		progressDialog = TipsUtil.openDialog(null, false, mContext);
		final BizHrAttendanceService service = new BizHrAttendanceService();
		service.getSingListCallBack(params,
				new AsyncHttpAttendanceInfoCallback() {

					@Override
					public void handler(List<AttendanceInfo> attendances) {
						if (AssertValue.isNotNullAndNotEmpty(attendances)) {
							singlist = attendances;
							initWeightTabLayout();
						} else {
							params.remove("username");
							loadSinglist();
						}

					}
				});
	}

	@SuppressLint("ResourceAsColor")
	public void initWeightTabLayout() {
		int rowWidth = screenWidth() / 3;
		tablelayout = (TableLayout) baseView.findViewById(R.id.tablelayout);
		// 全部列自动填充空白处
		tablelayout.setStretchAllColumns(true);
		// 生成10行，8列的表格
		if (AssertValue.isNotNullAndNotEmpty(singlist)) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// .LayoutParams(rowWidth, 150, 1);
			lp.setMargins(10, 10, 10, 10);
			for (int i = 0; i < singlist.size(); i++) {
				{
					TableRow tableRow = new TableRow(mContext);
					// 第一列
					LinearLayout layout = new LinearLayout(mContext);
					layout.setPadding(0, 0, 1, 1);
					layout.setBackgroundColor(Color.GRAY);
					layout.setGravity(Gravity.CENTER);
					layout.setOrientation(LinearLayout.VERTICAL);
					// 用户名称
					TextView tv1 = new TextView(mContext);
					tv1.setBackgroundColor(Color.WHITE);
					tv1.setWidth(rowWidth);
					tv1.setHeight(50);
					tv1.setGravity(Gravity.CENTER);
					tv1.setText(singlist.get(i).getName());
					// 用户班次
					TextView tv12 = new TextView(mContext);
					tv12.setBackgroundColor(Color.WHITE);
					tv12.setWidth(rowWidth);
					tv12.setHeight(85);
					tv12.setGravity(Gravity.CENTER);
					tv12.setText(singlist.get(i).getLabel());

					layout.addView(tv1);
					layout.addView(tv12);

					tableRow.addView(layout);
					// 第二列
					LinearLayout layout2 = new LinearLayout(mContext);
					layout2.setPadding(0, 0, 1, 1);
					layout2.setBackgroundColor(Color.GRAY);
					layout2.setGravity(Gravity.CENTER);
					layout2.setOrientation(LinearLayout.VERTICAL);
					// 用户上班的打卡时间
					TextView tv2 = new TextView(mContext);
					tv2.setBackgroundColor(Color.WHITE);
					tv2.setWidth(rowWidth);
					tv2.setHeight(50);
					tv2.setGravity(Gravity.CENTER);
					// 用户下班的打卡地点
					TextView tv22 = new TextView(mContext);
					tv22.setBackgroundColor(Color.WHITE);
					tv22.setWidth(rowWidth);
					tv22.setHeight(85);
					tv22.setGravity(Gravity.CENTER);
					// 上班打卡地点
					// 上班打卡地点
					if (AssertValue.isNotNullAndNotEmpty(singlist.get(i)
							.getCheckstartlocationlabel())) {
						tv22.setText(String.valueOf(singlist.get(i)
								.getCheckstartlocationlabel()));
					}
					// 上班打卡时间
					Date checkstartdatetime = null;
					if (singlist.get(i).getCheckstartdatetime() != 0) {
						checkstartdatetime = new Date(singlist.get(i)
								.getCheckstartdatetime());
						tv2.setText(sdf.format(checkstartdatetime).toString());
					} else
						tv2.setText("未上班");

					layout2.addView(tv2);
					layout2.addView(tv22);

					tableRow.addView(layout2);
					// 第三列
					LinearLayout layout3 = new LinearLayout(mContext);
					layout3.setPadding(0, 0, 1, 1);
					layout3.setBackgroundColor(Color.GRAY);
					layout3.setGravity(Gravity.CENTER);
					layout3.setOrientation(LinearLayout.VERTICAL);
					// 用户下班打卡时间
					TextView tv3 = new TextView(mContext);
					tv3.setBackgroundColor(Color.WHITE);
					tv3.setWidth(rowWidth);
					tv3.setHeight(50);
					tv3.setGravity(Gravity.CENTER);
					// 用户下班打卡地点
					TextView tv32 = new TextView(mContext);
					tv32.setBackgroundColor(Color.WHITE);
					tv32.setWidth(rowWidth);
					tv32.setHeight(85);
					tv32.setGravity(Gravity.CENTER);
					// 下班打卡地点
					if (AssertValue.isNotNullAndNotEmpty(singlist.get(i)
							.getCheckendlocationlabel())) {
						tv32.setText(String.valueOf(singlist.get(i)
								.getCheckendlocationlabel()));
					}

					// 下班打卡地点
					Date Checkenddatetime = null;
					if (singlist.get(i).getCheckenddatetime() != 0) {
						Checkenddatetime = new Date(singlist.get(i)
								.getCheckenddatetime());
						tv3.setText(sdf.format(Checkenddatetime).toString());
						if (tv2.getText().equals("未上班")) {
							tv2.setText("未打卡");
						}
					} else {
						if (tv2.getText().equals("未上班")) {
							tv3.setText("/");
						}
						if (AssertValue.isNotNull(checkstartdatetime)) {
							if (DateUtil.getDayByDate(checkstartdatetime) == DateUtil
									.getDayByDate(new Date())) {
								tv3.setText("工作中");
							} else {
								tv3.setText("/");
							}
						}
					}
					layout3.addView(tv3);
					layout3.addView(tv32);
					tableRow.addView(layout3);
					// 新建的TableRow添加到TableLayout
					tablelayout.addView(tableRow, new TableLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
				}
			}
			progressDialog.dismiss();
		} else
			progressDialog.dismiss();
	}

	// 获取屏幕的宽度
	private int screenWidth() {
		DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
		Rect rect = new Rect();
		getActivity().getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(rect);
		// dm.heightPixels屏幕高度
		// rect.top; //状态栏高度
		return dm.widthPixels;
	}



}
