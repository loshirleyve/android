package com.yun9.wservice.func.store;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductListAdapter extends JupiterAdapter {

    private Context context;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = null;
        Product product = products.get(position - 1);

        if (AssertValue.isNotNull(convertView) && convertView instanceof JupiterRowStyleSutitleLayout) {
            jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        } else {
            jupiterRowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(context);
        }

        jupiterRowStyleSutitleLayout.getTitleTV().setText(product.getProductImg());
        jupiterRowStyleSutitleLayout.getSutitleTv().setText(product.getProductImg());
        jupiterRowStyleSutitleLayout.setShowTime(false);

        return jupiterRowStyleSutitleLayout;

    }
}
