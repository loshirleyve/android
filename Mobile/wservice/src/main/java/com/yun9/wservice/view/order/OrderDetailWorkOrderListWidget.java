package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.jupiter.listener.OnClickWithNetworkListener;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.WorkorderDto;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailWorkOrderListWidget extends JupiterRelativeLayout {

    private ListView workOrdeLV;

    private OrderListSubItemAdapter adapter;

    private String orderid;

    public OrderDetailWorkOrderListWidget(Context context) {
        super(context);
    }

    public OrderDetailWorkOrderListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailWorkOrderListWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(List<WorkorderDto> workorderDtos,Context mContext,String orderid) {
        adapter=new OrderListSubItemAdapter(workorderDtos, mContext);
        this.orderid=orderid;
        workOrdeLV.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_work_order_list;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        workOrdeLV = (ListView) this.findViewById(R.id.listview);
        workOrdeLV.setOnItemClickListener(new OnClickWithNetworkListener(){
            public void ItemClickWithNetwork(AdapterView<?> parent, View view, int position, long id) {
                WorkorderDto workorderDto = (WorkorderDto) view.getTag();
                WorkOrderDetailActivity.start(view.getContext(),new WorkOrderCommand().setSource("so").setOrderid(orderid).setWorkorderno(workorderDto.getNo()));
            }
        });
    }



}
