package com.yun9.wservice.func.store;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductListAdapter extends JupiterAdapter{

    private ProductScrollListView productScrollListView;
    private List<ProductMainItemWidget> productMainItemWidgets;
    private Context context;
    private List<Product> products;

    public ProductListAdapter(Context context,List<Product> products){
        this.context = context;
        this.products = products;
        init();
    }

    private void init(){
        productScrollListView = new ProductScrollListView(context);
        productMainItemWidgets = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            productMainItemWidgets.add(new ProductMainItemWidget(context));
        }

    }
    @Override
    public int getCount() {
        return this.products.size()+1;
    }

    @Override
    public Object getItem(int position) {
        if(position == 0){
            return productScrollListView;
        }
        return this.products.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0){
            productScrollListView.buildWithData(products);
            return productScrollListView;
        }else {
            productMainItemWidgets.get(position-1).buildWithData(products.get(position - 1));
            return productMainItemWidgets.get(position-1);
        }
    }
}
