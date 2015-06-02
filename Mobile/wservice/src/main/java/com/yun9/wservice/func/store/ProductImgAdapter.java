package com.yun9.wservice.func.store;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;

import java.util.List;

/**
 * Created by xia on 2015/6/2.
 */
public class ProductImgAdapter extends PagerAdapter{
    private Context context;
    private List<ProductScrollItemView> mList;

    public ProductImgAdapter(Context context, List<ProductScrollItemView> list){
        this.context = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        if(AssertValue.isNotNullAndNotEmpty(mList)) {
            return mList.size();
        }else
            return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
