package com.yun9.mobile.product.activity;

import java.util.List;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.impls.sale.ImplProductService;
import com.yun9.mobile.framework.interfaces.sale.ProductService;
import com.yun9.mobile.framework.model.server.sale.ModelProductInfo;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.product.adapter.ProductAdapter;
import com.yun9.mobile.product.entity.ProductInfo;
import com.yun9.mobile.product.utils.DialogUtil;
import com.yun9.mobile.product.utils.ProductUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 服务产品界面
 * @author Kass
 */
public class ProductActivity extends Activity {
	// 页面控件
	private TitleBarView titleBar;
	private TextView title;
	private View leftView;
	private ProgressDialog mProgressDialog;
	private ListView mProductInfo;
	
	// 产品信息list相关变量
	private List<ModelProductInfo> list = null;
	private List<ProductInfo> productList = null;
	private ProductAdapter adapter;
	
	private final String TITLE = "服务产品";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		
		int mode = getIntent().getIntExtra("MODE", 1);
		if (mode == 1) {
			initView();
		} else {
			DialogUtil.showToast(ProductActivity.this, "非法启动服务产品");
    		finish();
		}
	}
	
	/**
	 * 初始化View
	 */
	private void initView() {
		titleBar = (TitleBarView) findViewById(R.id.product_title);
		mProductInfo = (ListView) findViewById(R.id.product_info_list);
		
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
		
        getProductInfo();
        
	}

	/**
	 * 获取产品信息
	 */
	private void getProductInfo() {
		mProgressDialog = DialogUtil.showProgressDialog(ProductActivity.this, "获取产品信息");
		mProgressDialog.show();
		ProductService productService = new ImplProductService();
		productService.getProductInfo(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				showProductInfo(response);
				mProgressDialog.dismiss();
			}

			@Override
			public void onFailure(Response response) {
				DialogUtil.showToast(ProductActivity.this, "服务器繁忙，请稍后再试");
				mProgressDialog.dismiss();
			}
		});
	}

	/**
	 * 显示产品信息
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void showProductInfo(Response response) {
		list = (List<ModelProductInfo>) response.getPayload();
		productList = ProductUtil.getProductInfo(list);
		adapter = new ProductAdapter(this, productList);
		mProductInfo.setAdapter(adapter);
		mProductInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
				intent.putExtra("id", productList.get(arg2).getId());
                startActivity(intent);
			}
		});
	}

}
