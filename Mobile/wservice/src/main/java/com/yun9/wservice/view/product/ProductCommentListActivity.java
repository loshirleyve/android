package com.yun9.wservice.view.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.WorkOrderComment;
import com.yun9.wservice.view.order.OrderWorkOrderSubCommentWidget;
import com.yun9.wservice.widget.ShowCommentWidget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rxy on 15/7/15.
 */
public class ProductCommentListActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id = R.id.product_comment_list)
    private PagingListView productCommentList;

    @BeanInject
    private ResourceFactory resourceFactory;

    private LinkedList<WorkOrderComment> productComments;

    private String prodctid;


    public static void start(Activity activity, String prodctid) {
        Intent intent = new Intent(activity, ProductCommentListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("prodctid", prodctid);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product_comment_list;
    }

    private void buildView() {
        prodctid = getIntent().getStringExtra("prodctid");
        productComments = new LinkedList<>();
        productCommentList.setAdapter(adapter);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCommentListActivity.this.finish();
            }
        });
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                productComments.clear();
                productCommentList.setHasMoreItems(true);
                refreshProductCommet(null, Page.PAGE_DIR_PULL);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);

        productCommentList.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(productComments)) {
                    WorkOrderComment comment = productComments.get(productComments.size() - 1);
                    refreshProductCommet(comment.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    productCommentList.onFinishLoading(true);
                }
            }
        });
    }


    private void refreshProductCommet(String rowid, final String dir) {
        Resource resource = resourceFactory.create("QueryWorkCommentsByProductId");
        resource.param("productid", prodctid);
        resource.page().setDir(dir).setRowid(rowid);
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<WorkOrderComment> comments = (List<WorkOrderComment>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(comments) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = comments.size(); i > 0; i--) {
                        WorkOrderComment comment = comments.get(i - 1);
                        productComments.addFirst(comment);
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(comments) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (WorkOrderComment comment : comments) {
                        productComments.addLast(comment);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(comments) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
                    productCommentList.onFinishLoading(false);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(ProductCommentListActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                productCommentList.onFinishLoading(true);
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(productComments)) {
                return productComments.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return productComments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductCommentWidget pcw = null;
            if (convertView == null) {
                pcw = new ProductCommentWidget(mContext);
                convertView = pcw;
            } else {
                pcw = (ProductCommentWidget) convertView;
            }
            CacheInst cacheInst = InstCache.getInstance().getInst(productComments.get(position).getBuyerinstid());
            if (AssertValue.isNotNull(cacheInst))
                pcw.getShowCommentWidget().getTitleTv().setText(cacheInst.getInstname());
            pcw.getShowCommentWidget().getContentTv().setText(productComments.get(position).getCommenttext());
            pcw.getShowCommentWidget().setTime(productComments.get(position).getCreatedate());
            pcw.getShowCommentWidget().setRating((float) productComments.get(position).getScore());
            Subadapter subadapter = new Subadapter(productComments.get(position).getAddcomments());
            pcw.getSubCommentLv().setAdapter(subadapter);
            return convertView;
        }
    };


    private class Subadapter extends JupiterAdapter {

        private List<WorkOrderComment.SubComment> subComments;

        public Subadapter(List<WorkOrderComment.SubComment> subComments) {
            this.subComments = subComments;
        }

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(subComments)) {
                return subComments.size();
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
            OrderWorkOrderSubCommentWidget subCommentWidget = null;
            if (convertView == null) {
                subCommentWidget = new OrderWorkOrderSubCommentWidget(mContext);
                convertView = subCommentWidget;
            } else {
                subCommentWidget = (OrderWorkOrderSubCommentWidget) convertView;
            }
            subCommentWidget.buildWithData(subComments.get(position));
            return convertView;
        }
    }

    ;
}

