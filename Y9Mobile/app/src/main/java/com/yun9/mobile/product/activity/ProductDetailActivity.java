package com.yun9.mobile.product.activity;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.sale.ImplProductService;
import com.yun9.mobile.framework.interfaces.sale.ProductService;
import com.yun9.mobile.framework.model.server.sale.ModelProductDetailInfo;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.product.utils.DialogUtil;
import com.yun9.mobile.product.utils.ProductUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 产品资料界面
 * @author Kass
 */
public class ProductDetailActivity extends Activity {
	// 页面控件
	private TitleBarView titleBar;
	private TextView title;
	private View leftView;
	private ProgressDialog mProgressDialog;
	private RelativeLayout mDetail;
	private ImageView mLicense;
	private TextView mCompany, mName, mPrice, mInfo, mDemands, mIntroduce, mTime, mType, mPayRequire;
	private LinearLayout mInfoView, mDemandsView;
	private Button mBuy;
	
	// 产品详细信息相关变量
	private String id = null;
	private ModelProductDetailInfo productDetailInfo;
	
	private final String TITLE = "产品资料";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		
		id = getIntent().getStringExtra("id");
		initView();
	}
	
	/**
	 * 初始化View
	 */
	private void initView() {
		titleBar = (TitleBarView) findViewById(R.id.product_detail_title);
		mDetail = (RelativeLayout) findViewById(R.id.product_detail_rl);
		mLicense = (ImageView) findViewById(R.id.product_detail_license_image);
		mCompany = (TextView) findViewById(R.id.product_detail_company_text);
		mName = (TextView) findViewById(R.id.product_detail_name_text);
		mPrice = (TextView) findViewById(R.id.product_detail_price_text);
		mInfo = (TextView) findViewById(R.id.product_detail_info_text);
		mDemands = (TextView) findViewById(R.id.product_detail_demands_text);
		mIntroduce = (TextView) findViewById(R.id.product_detail_introduce_text);
		mTime = (TextView) findViewById(R.id.product_detail_time_text);
		mType = (TextView) findViewById(R.id.product_detail_type_text);
		mPayRequire = (TextView) findViewById(R.id.product_detail_payrequire_text);
		mInfoView = (LinearLayout) findViewById(R.id.product_detail_info_ll);
		mDemandsView = (LinearLayout) findViewById(R.id.product_detail_demands_ll);
		mBuy = (Button) findViewById(R.id.product_detail_buy_btn);
		
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
        
        getProductDetailInfo();
        
	}

	/**
	 * 获取产品详细信息
	 */
	private void getProductDetailInfo() {
		mProgressDialog = DialogUtil.showProgressDialog(ProductDetailActivity.this, "获取产品详细信息");
		mProgressDialog.show();
		ProductService productService = new ImplProductService();
		productService.getProductDetailInfo(id, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				showProductDetail(response);
				mProgressDialog.dismiss();
			}

			@Override
			public void onFailure(Response response) {
				DialogUtil.showToast(ProductDetailActivity.this, "服务器繁忙，请稍后再试");
				mProgressDialog.dismiss();
			}
		});
	}

	private void showProductDetail(Response response) {
		productDetailInfo = (ModelProductDetailInfo) response.getPayload();
		String productTimes = ProductUtil.getProductTimes(productDetailInfo.getTimes(), productDetailInfo.getCycle());
		String productType = ProductUtil.getProductType(productDetailInfo.getType());
		String productPayRequire = ProductUtil.getProductPayRequire(productDetailInfo.getPayrequire());
		mDetail.setVisibility(View.VISIBLE);
		mBuy.setVisibility(View.VISIBLE);
		MyImageLoader.getInstance().displayImage(productDetailInfo.getImgid(), mLicense);
		mCompany.setText(productDetailInfo.getInstname());
		mName.setText(productDetailInfo.getName());
		mPrice.setText(productDetailInfo.getSaleprice() + "元/" + productDetailInfo.getCodename());
		mIntroduce.setText(productDetailInfo.getIntroduce());
		mTime.setText("服务时间：" + productTimes);
		mType.setText("产品类型：" + productType);
		mPayRequire.setText("付款要求：" + productPayRequire);
		mInfo.setTextColor(Color.RED);
		mDemands.setTextColor(Color.BLACK);
		mInfo.setOnClickListener(new ViewOnClickListener());
		mDemands.setOnClickListener(new ViewOnClickListener());
		mBuy.setOnClickListener(new ViewOnClickListener());
	}
	
	/**
	 * 页面控件点击事件
	 */
	public class ViewOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.product_detail_info_text:
					mInfoView.setVisibility(View.VISIBLE);
					mDemandsView.setVisibility(View.GONE);
					mInfo.setTextColor(Color.RED);
					mDemands.setTextColor(Color.BLACK);
					break;
					
				case R.id.product_detail_demands_text:
					mInfoView.setVisibility(View.GONE);
					mDemandsView.setVisibility(View.VISIBLE);
					mInfo.setTextColor(Color.BLACK);
					mDemands.setTextColor(Color.RED);
					break;
					
				case R.id.product_detail_buy_btn:
					Intent intent = new Intent(ProductDetailActivity.this, OrderActivity.class);
					intent.putExtra("ProductDetailInfo", productDetailInfo);
					startActivity(intent);
					break;
	
				default:
					break;
			}
		}
	}
	
}
