package com.yun9.wservice.view.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.CompositeProduct;
import com.yun9.wservice.model.ProductProfile;
import com.yun9.wservice.model.WorkOrderComment;
import com.yun9.wservice.view.common.SimpleBrowserActivity;
import com.yun9.wservice.view.common.SimpleBrowserCommand;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;
import com.yun9.wservice.view.order.OrderCartActivity;
import com.yun9.wservice.view.order.OrderCartCommand;
import com.yun9.wservice.view.order.OrderWorkOrderSubCommentWidget;
import com.yun9.wservice.widget.ShowCommentWidget;

import java.util.ArrayList;
import java.util.List;

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

    @ViewInject(id = R.id.buy_ll)
    private LinearLayout buyLl;

    @ViewInject(id = R.id.select_category_layout)
    private JupiterRowStyleSutitleLayout selectCategoryLayout;

    @ViewInject(id = R.id.product_detail_content_lv)
    private ListView productContentLV;

    @ViewInject(id = R.id.product_detail_phases_lv)
    private ListView productPhasesLV;

    @ViewInject(id = R.id.comment_num)
    private TextView commentNum;

    @ViewInject(id = R.id.detail_page_layout)
    private JupiterRowStyleTitleLayout detailPageLayout;

    @ViewInject(id = R.id.show_comment_widget)
    private ShowCommentWidget showCommentWidget;

    @ViewInject(id=R.id.sub_comment_lv)
    private ListView subCommentLV;

    @ViewInject(id=R.id.comment_ll)
    private LinearLayout commentLl;

    @ViewInject(id=R.id.more_comment)
    private Button moreComment;

    @ViewInject(id=R.id.product_detail_content_ll)
    private LinearLayout productDetailContentLl;

    @ViewInject(id=R.id.product_detail_phases_ll)
    private LinearLayout productDetailPhasesLl;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id=R.id.main)
    private RelativeLayout mainRl;

    private PopupWindow classifyWindow;

    private ProductClassifyPopLayout classifyPopLayout;

    private CompositeProduct product;

    private CompositeProduct.ProductClassify selectedClassify;

    private String priceDesc;

    @Override
    protected int getContentView() {
        return R.layout.activity_product;
    }

    public static void start(Activity activity, ProductCommand command) {
        Intent intent = new Intent(activity, ProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainRl.setVisibility(View.GONE);
        command = (ProductCommand) getIntent().getSerializableExtra(ProductCommand.PARAM_COMMAND);

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getProductid())) {
            refresh(command.getProductid());
        }

        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        productContentLV.setAdapter(productContentLVAdapter);
        productPhasesLV.setAdapter(productPhasesLVAdapter);
        detailPageLayout.setOnClickListener(onProductDetailClickListener);
        selectCategoryLayout.setMainContentGravity(Gravity.CENTER);
        selectCategoryLayout.setOnClickListener(onSelectCategoryClickListener);
        buyLl.setOnClickListener(onBuyClickListener);
        moreComment.setOnClickListener(onMoreCommenClickListener);
    }

    /**
     * 数据完成时才调用
     */
    private void initPopWindow() {
        classifyPopLayout = new ProductClassifyPopLayout(mContext);
        classifyWindow = new PopupWindow(classifyPopLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        classifyWindow.setOnDismissListener(onDismissListener);
        classifyWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        classifyWindow.setOutsideTouchable(true);
        classifyWindow.setFocusable(true);
        classifyWindow.setAnimationStyle(R.style.bottom2top_top2bottom);

        ImageLoaderUtil.getInstance(this).displayImage(product.getProduct().getImgid(),
                classifyPopLayout.getProducImage());
        classifyPopLayout.getProductPriceTv().setText(priceDesc );
        classifyPopLayout.getConfirmLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sessionManager.isLogin()) {
                    LoginMainActivity.start(ProductActivity.this, new LoginCommand());
                    return;
                }
                if (selectedClassify == null
                        && product.getBizProductClassifies() != null
                        && product.getBizProductClassifies().size() > 0) {
                    showToast(R.string.please_select_product_classify);
                } else {
                    buyNow();
                }
            }
        });
        classifyPopLayout.getCloseIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifyWindow.dismiss();
            }
        });
        classifyPopLayout.getClassifyLv().setAdapter(classifyAdapter);
        refreshSelectedClassify();

    }

    private void refreshSelectedClassify() {
        if (selectedClassify != null) {
            selectCategoryLayout.getTitleTV().setText(R.string.selected_classify);
            selectCategoryLayout.getSutitleTv().setVisibility(View.VISIBLE);
            selectCategoryLayout.getSutitleTv().setText(selectedClassify.getClassifyname());
        } else {
            productPriceTV.setText(priceDesc);
            selectCategoryLayout.getTitleTV().setText(R.string.please_select_product_classify);
            selectCategoryLayout.getSutitleTv().setVisibility(View.GONE);
            return;
        }
        productPriceTV.setText("￥"+selectedClassify.getPrice());
        classifyPopLayout.getProductPriceTv().setText("￥" + selectedClassify.getPrice());
        classifyPopLayout.getClassifyNameTv().setText(selectedClassify.getClassifyname());
    }

    private void refresh(String productid) {
        if (AssertValue.isNotNullAndNotEmpty(productid)) {
            Resource resource = resourceFactory.create("QueryProductInfoById");
            resource.param("productid", productid);

            final ProgressDialog progressDialog = ProgressDialog.show(this, null, mContext.getResources().getString(R.string.app_wating), true);

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    CompositeProduct tempProduct = (CompositeProduct) response.getPayload();
                    builder(tempProduct);
                    initPopWindow();
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
                    mainRl.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    private void builder(CompositeProduct tempProduct) {
        this.product = tempProduct;
        if (product.getBizProductClassifies() == null
                || product.getBizProductClassifies().size() == 0) {
            selectCategoryLayout.setVisibility(View.GONE);
        }
        String imageURL = FileCache.getInstance().getFileUrl(product.getProduct().getImgid());
        if (AssertValue.isNotNullAndNotEmpty(imageURL)) {
            ImageLoaderUtil.getInstance(mContext).displayImage(imageURL, productIV);
        }
        CacheInst cacheInst = InstCache.getInstance().getInst(product.getProduct().getInstid());
        if (AssertValue.isNotNull(cacheInst)) {
            instnameTV.setText(getResources().getString(R.string.product_detail_instname, cacheInst.getInstname()));
        }

        if (product.getProduct().getMaxprice() == product.getProduct().getMinprice()){
            priceDesc = "￥" + product.getProduct().getMinprice();
        } else {
            priceDesc = "￥" + product.getProduct().getMinprice()
                    +" ~ "+product.getProduct().getMaxprice();
        }

        productNameTV.setText(product.getProduct().getName());
        productDescTV.setText(product.getProduct().getIntroduce());
        productTipsTV.setText(product.getProduct().getProductdescr());
        productPriceTV.setText(priceDesc);

        if (product.getWorkorderComment() == null){
            commentLl.setVisibility(View.GONE);
        } else {
            WorkOrderComment comment = product.getWorkorderComment();
            CacheUser user = UserCache.getInstance().getUser(comment.getSenderid());
            if (user != null){
                showCommentWidget.getTitleTv().setText(user.getName());
            }
            showCommentWidget.getContentTv().setText(comment.getCommenttext());
            showCommentWidget.setRating((float) comment.getScore());
            showCommentWidget.setTime(comment.getCreatedate());
            subCommentLV.setAdapter(subCommentAdapter);
        }
        // 不存在详情URL，则隐藏
        if (!AssertValue.isNotNullAndNotEmpty(product.getProduct().getIntroduceurl())) {
            detailPageLayout.setVisibility(View.GONE);
        }

        if (!AssertValue.isNotNullAndNotEmpty(product.getBizProductProfiles())){
            productDetailContentLl.setVisibility(View.GONE);
        }

        // 服务阶段不显示
        productDetailPhasesLl.setVisibility(View.GONE);

        commentNum.setText("("+product.getProduct().getCommentnums()+")");

    }

    private View.OnClickListener onProductDetailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleBrowserActivity.start(ProductActivity.this,
                    new SimpleBrowserCommand(product.getProduct().getName(),
                            product.getProduct().getIntroduceurl()));
        }
    };

    private View.OnClickListener onSelectCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showClassifyWindow();
        }
    };

    private void showClassifyWindow() {
        if (classifyWindow != null) {
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.alpha = 0.4f;
            this.getWindow().setAttributes(lp);
            classifyWindow.showAtLocation(mainRl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private View.OnClickListener onBuyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gonnaBuy();
        }
    };

    private View.OnClickListener onMoreCommenClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProductCommentListActivity.start(ProductActivity.this,command.getProductid());
        }
    };

    private void gonnaBuy() {
        if (!sessionManager.isLogin()) {
            LoginMainActivity.start(ProductActivity.this, new LoginCommand());
            return;
        }

        if (selectedClassify == null
                && product.getBizProductClassifies() != null
                && product.getBizProductClassifies().size() > 0) {
            showClassifyWindow();
        } else {
            buyNow();
        }
    }

    private void buyNow() {
        OrderCartCommand command = new OrderCartCommand();
        List<OrderCartCommand.OrderProductView> orderProductViews = new ArrayList<>();
        OrderCartCommand.OrderProductView productView = new OrderCartCommand.OrderProductView();
        productView.setProductid(product.getProduct().getId());
        if (selectedClassify != null){
            productView.setClassifyid(selectedClassify.getId());
            productView.setPrice(selectedClassify.getPrice());
        } else {
            productView.setPrice(product.getProduct().getSaleprice());
        }
        orderProductViews.add(productView);
        command.setOrderProductViews(orderProductViews);
        OrderCartActivity.start(this, command);
        this.finish();
    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private JupiterAdapter productContentLVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNull(product) && AssertValue.isNotNullAndNotEmpty(product.getBizProductProfiles())) {
                return product.getBizProductProfiles().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return product.getBizProductProfiles().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductProfileItemWidget productProfileItemWidget = null;
            ProductProfile productProfile = product.getBizProductProfiles().get(position);

            if (AssertValue.isNotNull(convertView)) {
                productProfileItemWidget = (ProductProfileItemWidget) convertView;
            } else {
                productProfileItemWidget = new ProductProfileItemWidget(mContext);
            }

            productProfileItemWidget.setTag(productProfile);
            productProfileItemWidget.getProfileTV().setText(productProfile.getSynopsis());

            return productProfileItemWidget;
        }
    };

    private JupiterAdapter productPhasesLVAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNull(product) && AssertValue.isNotNullAndNotEmpty(product.getProductPhases())) {
                return product.getProductPhases().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return product.getProductPhases().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductPhaseItemWidget productPhaseItemWidget = null;
            CompositeProduct.ProductPhase productPhase = product.getProductPhases().get(position);
            if (convertView == null) {
                productPhaseItemWidget = new ProductPhaseItemWidget(mContext);
                productPhaseItemWidget.setTag(productPhase);
                productPhaseItemWidget.getPhaseTV().setText(productPhase.getName()+": "+productPhase.getPhasedescr());
                convertView = productPhaseItemWidget;
            }

            return convertView;
        }
    };

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = ProductActivity.this.getWindow().getAttributes();
            lp.alpha = 1f;
            ProductActivity.this.getWindow().setAttributes(lp);
        }
    };

    private JupiterAdapter classifyAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (product != null
                    && product.getBizProductClassifies() != null) {
                return product.getBizProductClassifies().size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CompositeProduct.ProductClassify classify =
                                                product.getBizProductClassifies().get(position);
            JupiterRowStyleTitleLayout titleLayout;
            if (convertView == null) {
                titleLayout = new JupiterRowStyleTitleLayout(ProductActivity.this);
                titleLayout.getArrowRightIV().setVisibility(View.GONE);
                titleLayout.getMainIV().setVisibility(View.GONE);
                titleLayout.getHotNitoceTV().setVisibility(View.GONE);
                titleLayout.getTitleTV().setText(classify.getClassifyname());
                titleLayout.setSelectMode(true);
                titleLayout.setTag(classify);
                titleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CompositeProduct.ProductClassify tag =
                                (CompositeProduct.ProductClassify) v.getTag();
                        // 如果点击了选中项
                        if (selectedClassify != null
                                && selectedClassify.getId().equals(tag.getId())) {
                            ((JupiterRowStyleTitleLayout)v).select(false);
                            selectedClassify = null;
                            refreshSelectedClassify();
                            return;
                        }
                        // 点击了非选中项
                        int count = classifyAdapter.getCount();
                        for (int i = 0; i < count; i++) {
                            JupiterRowStyleTitleLayout view =
                                    (JupiterRowStyleTitleLayout) classifyPopLayout
                                            .getClassifyLv()
                                            .getChildAt(i);
                            if (i == position) {
                                view.select(true);
                                selectedClassify = (CompositeProduct.ProductClassify) view.getTag();
                            } else {
                                view.select(false);
                            }
                        }
                        refreshSelectedClassify();
                    }
                });
                convertView = titleLayout;
            } else {
                titleLayout = (JupiterRowStyleTitleLayout) convertView;
            }

            if (selectedClassify != null
                    && selectedClassify.getId().equals(classify.getId())){
                titleLayout.select(true);
            } else {
                titleLayout.select(false);
            }
            return convertView;
        }
    };

    private JupiterAdapter subCommentAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (product != null
                    && product.getWorkorderComment() != null
                    && product.getWorkorderComment().getAddcomments() != null) {
                return product.getWorkorderComment().getAddcomments().size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderWorkOrderSubCommentWidget subCommentWidget;
            if (convertView == null) {
                subCommentWidget = new OrderWorkOrderSubCommentWidget(ProductActivity.this);
                convertView = subCommentWidget;
            } else {
                subCommentWidget = (OrderWorkOrderSubCommentWidget) convertView;
            }
            subCommentWidget.buildWithData(product.getWorkorderComment().getAddcomments().get(position));
            return convertView;
        }
    };
}
