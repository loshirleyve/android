package com.yun9.wservice.func.store;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;

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

        ProductItemLayout productItemLayout = null;
        Product product = products.get(position);

        if (AssertValue.isNotNull(convertView)) {
            productItemLayout = (ProductItemLayout) convertView;
        } else {
            productItemLayout = new ProductItemLayout(context);
        }
        switch (position){
            case 0:
                productItemLayout.getTitleTV().setText("人事服务包");
                productItemLayout.getSutitleTV().setText("人事服务精选：入职、离职、变动");
                productItemLayout.getMainIV().setImageResource(R.drawable.user_org);
                productItemLayout.getHotnoticeTV().setText("每月100元");
                break;
            case 1:
                productItemLayout.getTitleTV().setText("代发工资");
                productItemLayout.getSutitleTV().setText("代替企业自己，为员工发工资");
                productItemLayout.getMainIV().setImageResource(R.drawable.mapp5);
                productItemLayout.getHotnoticeTV().setText("每月200元");
                break;
            case 2:
                productItemLayout.getTitleTV().setText("缴交社保");
                productItemLayout.getSutitleTV().setText("社保申报及缴纳");
                productItemLayout.getMainIV().setImageResource(R.drawable.mapp4);
                productItemLayout.getHotnoticeTV().setText("每月300元");
                break;
            case 3:
                productItemLayout.getTitleTV().setText("员工手册拟定");
                productItemLayout.getSutitleTV().setText("拟定有效的员工手册，保证其程序合法、内容合法");
                productItemLayout.getMainIV().setImageResource(R.drawable.mapp6);
                productItemLayout.getHotnoticeTV().setText("每月400元");
                break;
            default:
                productItemLayout.getTitleTV().setText("员工手册拟定");
                productItemLayout.getSutitleTV().setText("拟定有效的员工手册，保证其程序合法、内容合法");
                productItemLayout.getMainIV().setImageResource(R.drawable.mapp2);
                productItemLayout.getHotnoticeTV().setText("每月400元");
        }
/*        productItemLayout.getTitleTV().setText(product.getProductImg());
        productItemLayout.getSutitleTV().setText(product.getProductImg());*/

        return productItemLayout;

    }
}
