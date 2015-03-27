package com.yun9.mobile.product.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.sale.ImplProductService;
import com.yun9.mobile.framework.interfaces.sale.ProductService;
import com.yun9.mobile.framework.model.server.sale.ModelProductDetailInfo;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.product.entity.OrderAttachmentsInfo;
import com.yun9.mobile.product.utils.DialogUtil;

/**
 * 订单信息界面
 * @author Kass
 */
public class OrderActivity extends Activity {
	// 页面控件
	private TitleBarView titleBar;
	private TextView title;
	private View leftView;
	private TextView mOrderCompany, mNumber, mClient, mSalesman, mOrderPrice;
	private ImageView mPicture;
	private TextView mProductCompany, mName, mProductPrice, mIntroduce;
	private Button mSubmit;
	
	private ModelProductDetailInfo productDetailInfo;
	private String productid;
	private List<OrderAttachmentsInfo> attachments;
	
	private final String TITLE = "订单信息";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		initData();
		initView();
	}
	
	private void initData() {
		productDetailInfo = (ModelProductDetailInfo) getIntent().getSerializableExtra("ProductDetailInfo");
		productid = productDetailInfo.getId();
	}
	
	private void initView() {
		titleBar = (TitleBarView) findViewById(R.id.order_title);
		mOrderCompany = (TextView) findViewById(R.id.order_company_text);
		mNumber = (TextView) findViewById(R.id.order_number_text);
		mClient = (TextView) findViewById(R.id.order_client_text);
		mSalesman = (TextView) findViewById(R.id.order_salesman_text);
		mOrderPrice = (TextView) findViewById(R.id.order_price_text);
		mPicture = (ImageView) findViewById(R.id.order_product_picture_image);
		mProductCompany = (TextView) findViewById(R.id.order_product_company_text);
		mName = (TextView) findViewById(R.id.order_product_name_text);
		mProductPrice = (TextView) findViewById(R.id.order_product_price_text);
		mIntroduce = (TextView) findViewById(R.id.order_product_introduce_text);
		mSubmit = (Button) findViewById(R.id.order_submit_btn);
		
		title = titleBar.getTvTitle();
        title.setText(TITLE);
        title.setTextColor(Color.BLACK);
        title.setVisibility(View.VISIBLE);
        leftView = titleBar.getBtnLeft();
        leftView.setVisibility(View.VISIBLE);
        leftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        mOrderCompany.setText(productDetailInfo.getInstname());
        mNumber.setText("订单编号：" + System.currentTimeMillis());
        mClient.setText("客户：" + productDetailInfo.getInstname());
        mSalesman.setText("业务员：无");
        mOrderPrice.setText("订单金额：" + productDetailInfo.getSaleprice() * productDetailInfo.getTimes() + "元");
        
        MyImageLoader.getInstance().displayImage(productDetailInfo.getImgid(), mPicture);
        mProductCompany.setText(productDetailInfo.getInstname());
        mName.setText(productDetailInfo.getName());
        mProductPrice.setText(productDetailInfo.getSaleprice() + "元/" + productDetailInfo.getCodename());
        mIntroduce.setText(productDetailInfo.getIntroduce());
        
        mSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setOrderAttachmentsInfo();
				submitDialog();
			}
		});
	}
	
	protected void setOrderAttachmentsInfo() {
		attachments = new ArrayList<OrderAttachmentsInfo>();
		
	}

	private void submitDialog() {
		AlertDialog.Builder builder = new Builder(OrderActivity.this);
		builder.setTitle("提交订单");
		builder.setMessage("确定提交订单吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveOrder();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create().show();
	}
	
	private void saveOrder() {
		ProductService productService = new ImplProductService();
		productService.saveOrder(productid, attachments, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				DialogUtil.showToast(OrderActivity.this, "订单提交成功！");
				finish();
			}

			@Override
			public void onFailure(Response response) {
				DialogUtil.showToast(OrderActivity.this, "订单提交失败！");
			}
		});
	}
	
}
