package com.yun9.mobile.framework.base.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.annotation.view.EventListener;
import com.yun9.mobile.framework.annotation.view.Select;
import com.yun9.mobile.framework.annotation.view.ViewInject;
import com.yun9.mobile.framework.util.AssertValue;

public abstract class FinalActivity extends Activity {
	private static boolean isShowToast = true;
	private ProgressDialog progressDialog;

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initInjectedView(this);
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initInjectedView(this);
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initInjectedView(this);
	}

	public static void initInjectedView(Activity activity) {
		initInjectedView(activity, activity.getWindow().getDecorView());
	}

	public static void initInjectedView(Object injectedSource, View sourceView) {
		Field[] fields = injectedSource.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);

					if (field.get(injectedSource) != null)
						continue;

					ViewInject viewInject = field
							.getAnnotation(ViewInject.class);
					if (viewInject != null) {

						int viewId = viewInject.id();
						field.set(injectedSource,
								sourceView.findViewById(viewId));

						setListener(injectedSource, field, viewInject.click(),
								Method.Click);
						setListener(injectedSource, field,
								viewInject.longClick(), Method.LongClick);
						setListener(injectedSource, field,
								viewInject.itemClick(), Method.ItemClick);
						setListener(injectedSource, field,
								viewInject.itemLongClick(),
								Method.itemLongClick);

						Select select = viewInject.select();
						if (!TextUtils.isEmpty(select.selected())) {
							setViewSelectListener(injectedSource, field,
									select.selected(), select.noSelected());
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void setViewSelectListener(Object injectedSource,
			Field field, String select, String noSelect) throws Exception {
		Object obj = field.get(injectedSource);
		if (obj instanceof View) {
			((AbsListView) obj).setOnItemSelectedListener(new EventListener(
					injectedSource).select(select).noSelect(noSelect));
		}
	}

	private static void setListener(Object injectedSource, Field field,
			String methodName, Method method) throws Exception {
		if (methodName == null || methodName.trim().length() == 0)
			return;

		Object obj = field.get(injectedSource);

		switch (method) {
		case Click:
			if (obj instanceof View) {
				((View) obj).setOnClickListener(new EventListener(
						injectedSource).click(methodName));
			}
			break;
		case ItemClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj).setOnItemClickListener(new EventListener(
						injectedSource).itemClick(methodName));
			}
			break;
		case LongClick:
			if (obj instanceof View) {
				((View) obj).setOnLongClickListener(new EventListener(
						injectedSource).longClick(methodName));
			}
			break;
		case itemLongClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj)
						.setOnItemLongClickListener(new EventListener(
								injectedSource).itemLongClick(methodName));
			}
			break;
		default:
			break;
		}
	}

	public enum Method {
		Click, LongClick, ItemClick, itemLongClick
	}

	protected void showToast(String msg) {
		if (isShowToast) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	protected void showToast(int msg) {
		if (isShowToast) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	protected void openDialog() {
		this.openDialog(null);
	}

	protected void openDialog(String msg) {
		this.openDialog(msg, false);
	}

	protected void openDialog(String msg, boolean cancel) {
		if (!AssertValue.isNotNullAndNotEmpty(msg)) {
			msg = this.getApplicationContext().getResources()
					.getText(R.string.default_progress_dialog_msg).toString();
		}

		if (!AssertValue.isNotNull(progressDialog)) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(msg);
			progressDialog.setCancelable(cancel);
		}

		progressDialog.show();
	}

	protected void hideDialog() {
		if (AssertValue.isNotNull(progressDialog)) {
			// progressDialog.hide();
			progressDialog.dismiss();
		}
	}
}
