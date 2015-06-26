package com.yun9.wservice.view.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductPhase;
import com.yun9.wservice.model.ProductProfile;

/**
 * Created by Leon on 15/6/26.
 */
public class ProductActivity extends JupiterFragmentActivity {

    private ProductCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.product_iv)
    private ImageView productIV;

    @ViewInject(id = R.id.inst_name_tv)
    private TextView instnameTV;

    @ViewInject(id = R.id.product_name_tv)
    private TextView productNameTV;

    @ViewInject(id = R.id.product_desc_tv)
    private TextView productDescTV;

    @ViewInject(id = R.id.product_tips_tv)
    private TextView productTipsTV;

    @ViewInject(id = R.id.product_price_tv)
    private TextView productPriceTV;

    @ViewInject(id = R.id.buy_btn)
    private Button buyBtn;

    @ViewInject(id = R.id.select_category_layout)
    private JupiterRowStyleSutitleLayout selectCategoryLayout;

    @ViewInject(id = R.id.product_detail_content_lv)
    private ListView productContentLV;

    @ViewInject(id = R.id.product_detail_phases_lv)
    private ListView productPhasesLV;

    @ViewInject(id = R.id.comment_num)
    private TextView commentNum;

    @ViewInject(id = R.id.detail_page_layout)
    private JupiterRowStyleSutitleLayout detailPageLayout;

    @BeanInject
    private ResourceFactory resourceFactory;

    private Product product;

    @Override
    protected int getContentView() {
        return R.layout.activity_product;
    }

    public static void start(Activity activity, ProductCommand command) {
        Intent intent = new Intent(activity, ProductActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (ProductCommand) getIntent().getSerializableExtra(ProductCommand.PARAM_COMMAND);

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getProductid())) {
            refresh(command.getProductid());
        }

        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        productContentLV.setAdapter(productContentLVAdapter);
        productPhasesLV.setAdapter(productPhasesLVAdapter);
        detailPageLayout.setOnClickListener(onProductDetailClickListener);
        selectCategoryLayout.setOnClickListener(onSelectCategoryClickListener);
        buyBtn.setOnClickListener(onBuyClickListener);


    }

    private void refresh(String productid) {
        if (AssertValue.isNotNullAndNotEmpty(productid)) {
            Resource resource = resourceFactory.create("QueryProductInfoById");
            resource.param("productid", productid);

            final ProgressDialog progressDialog = ProgressDialog.show(this, null, mContext.getResources().getString(R.string.app_wating), true);

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    product = (Product) response.getPayload();
                    builder(product);
                    productPhasesLVAdapter.notifyDataSetChanged();
                    productContentLVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });

        }
    }

    private void builder(Product tempProduct) {
        String imageURL = FileCache.getInstance().getFileUrl(tempProduct.getImageid());
        if (AssertValue.isNotNullAndNotEmpty(imageURL)) {
            ImageLoaderUtil.getInstance(mContext).displayImage(imageURL, productIV);
        }
        CacheInst cacheInst = InstCache.getInstance().getInst(tempProduct.getInstid());
        if (AssertValue.isNotNull(cacheInst)) {
            instnameTV.setText(getResources().getString(R.string.product_detail_instname, cacheInst.getInstname()));
        }

        //TODO 待服务器返回数据后完善
        productNameTV.setText(product.getName());
        productDescTV.setText(product.getIntroduce());
        productTipsTV.setText("待服务器返回数据");
        productPriceTV.setText("待服务器返回数据");

        //TODO 待服务器返回分类数据后，根据是否存在分类显示选择分类栏目

        //TODO 待服务器返回最新评论数据,处理评论

        //TODO 待服务器返回服务内容

        //TODO 待服务器返回服务阶段

        //TODO 待服务器返回要求的办里资料数据

    }

    private View.OnClickListener onProductDetailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onSelectCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onBuyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO 检查是否已经登录，如果没有登录弹出登录界面

            //TODO 检查是否存在分类选择，如果存在弹出分类选择(用户在分类选择界面继续点击购买)

        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private JupiterAdapter productContentLVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNull(product) && AssertValue.isNotNullAndNotEmpty(product.getProfiles())) {
                return product.getProfiles().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return product.getProfiles().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductProfileItemWidget productProfileItemWidget = null;
            ProductProfile productProfile = product.getProfiles().get(position);

            if (AssertValue.isNotNull(convertView)) {
                productProfileItemWidget = (ProductProfileItemWidget) convertView;
            } else {
                productProfileItemWidget = new ProductProfileItemWidget(mContext);
            }

            //TODO 服务器还没有返回数据
            productProfileItemWidget.setTag(productProfile);
            productProfileItemWidget.getProfileTV().setText("待服务器返回数据");

            return productProfileItemWidget;
        }
    };

    private JupiterAdapter productPhasesLVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNull(product) && AssertValue.isNotNullAndNotEmpty(product.getPhases())) {
                return product.getPhases().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return product.getPhases().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductPhaseItemWidget productPhaseItemWidget = null;
            ProductPhase productPhase = product.getPhases().get(position);

            if (AssertValue.isNotNull(convertView)) {
                productPhaseItemWidget = (ProductPhaseItemWidget) convertView;
            } else {
                productPhaseItemWidget = new ProductPhaseItemWidget(mContext);
            }

            productPhaseItemWidget.setTag(productPhase);
            productPhaseItemWidget.getPhaseTV().setText(productPhase.getDescription());

            return productPhaseItemWidget;
        }
    };
}
