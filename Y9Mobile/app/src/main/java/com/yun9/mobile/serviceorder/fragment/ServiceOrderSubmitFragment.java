package com.yun9.mobile.serviceorder.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maoye.form.factory.model.form.FactoryModelViewForm;
import com.maoye.form.factory.model.form.FactoryModelViewFormDefault;
import com.maoye.form.factory.view.form.FactoryViewForm;
import com.maoye.form.interfaces.FormInputType;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.view.form.ViewForm;
import com.maoye.form.view.form.ViewFormShell;
import com.yun9.mobile.R;
import com.yun9.mobile.form.factory.FactoryViewFromYiDianTong;
import com.yun9.mobile.framework.model.BizSaleServiceOrderAttachment;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.serviceorder.activity.ServiceOrderActivity;

/**
 * 提交订单的fragment
 * 
 * @author rxy
 *
 */
public class ServiceOrderSubmitFragment extends Fragment {

	private Context mContext;
	private View baseView;

	private ImageButton returnButton;
	private TextView titleview;
	private Map<String, Object> params;

	private ServiceOrder order;
	private TextView commit;

	private ViewForm form1;
	private ViewFormShell formShell1;

	public ServiceOrderSubmitFragment(Context context,
			ImageButton returnButton, TextView titleview, ServiceOrder order,
			Map<String, Object> params, TextView commit) {
		this.mContext = context;
		this.returnButton = returnButton;
		this.titleview = titleview;
		this.params = params;
		this.commit = commit;
		this.order = order;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater
				.inflate(R.layout.fragment_serviceorder_submit, null);
		initweight();
		bindEvn();
		return baseView;
	}

	public void initweight() {
		titleview.setText("附件资料");
		commit.setVisibility(View.VISIBLE);
		formShell1 = (ViewFormShell) baseView.findViewById(R.id.form1);
		load();
	}

	private void bindEvn() {
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replace(params);
			}
		});

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (form1.getModelViewForm() == null) {
					return;
				} else
					getFromValue();
			}
		});

	}

	public void load() {
		FactoryViewForm factory = new FactoryViewFromYiDianTong();
		form1 = factory.creatViewForm(getActivity());
		ModelViewForm model = getModelViewForm();
		form1.initWithModel(model);
		formShell1.loadForm(form1);

	}

	public void replace(Map<String, Object> params) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		titleview.setText(order.getOrderstatecodename() + "订单");
		ServiceOrderListItemsFragment fragment = new ServiceOrderListItemsFragment(
				mContext, params, returnButton, titleview, commit);
		ft.replace(R.id.fl_content, fragment,
				ServiceOrderActivity.class.getName());
		ft.commit();
	}

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}

	protected ModelViewForm getModelViewForm() {
		List<BizSaleServiceOrderAttachment> attachments = order.getAttahchs();
		// 服务端配置表单
		FactoryModelViewForm factory = new FactoryModelViewFormDefault();
		ModelViewForm model = factory.creatModelViewForm("form");
		if (AssertValue.isNotNullAndNotEmpty(attachments)) {

			model.setName("需提供如下资料");
			for (int i = 0; i < attachments.size(); i++) {
				if (!attachments.get(i).getInputtype().equals("none")) {
					ModelViewFormRow row = factory.creatModelViewFormRow("row");
					if (attachments.get(i).getInputtype().equals("text")) {
						ModelViewFormEditTextCell cell11 = factory
								.creatModelViewFormEditTextCell("cell11");
						cell11.setLabel(attachments.get(i).getAttachname());
						cell11.setInputType(FormInputType.TYPE_CLASS_TEXT);
						cell11.setTag(attachments.get(i).getId());
						row.addCell(cell11);
					} else if (attachments.get(i).getInputtype()
							.equals("number")) {
						ModelViewFormEditTextCell cell12 = factory
								.creatModelViewFormEditTextCell("cell12");
						cell12.setLabel(attachments.get(i).getAttachname());
						cell12.setInputType(FormInputType.TYPE_CLASS_NUMBER
								| FormInputType.TYPE_NUMBER_FLAG_DECIMAL);
						cell12.setTag(attachments.get(i).getId());
						row.addCell(cell12);
					}
					model.addRow(row);
				} else {
					ModelViewFormRow attachmentrow = factory
							.creatModelViewFormRow("attachmentrow");
					ModelViewFormAttchmentCell cell = factory
							.creatModelViewFormAttchmentCell("cell");
					cell.setLabel(attachments.get(i).getAttachname());
					cell.setTag(attachments.get(i).getId());
					attachmentrow.addCell(cell);
					model.addRow(attachmentrow);
				}

			}
		}
		return model;
	}

	// 获取表单数据
	protected HashMap<String, String> getFromValue() {
		List<ModelViewFormRow> rows = form1.getModelViewForm().getRows();
		HashMap<String, String> cellvalue = new HashMap<String, String>();
		if (AssertValue.isNotNullAndNotEmpty(rows)) {
			for (ModelViewFormRow modelViewFormRow : rows) {
				cellvalue.put(modelViewFormRow.getCells().get(0).getTag(),
						modelViewFormRow.getCells().get(0).getValue());

			}
		}
		showToast("表单数据：" + cellvalue);
		return cellvalue;
	}

}
