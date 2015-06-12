package com.yun9.wservice.view.location;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.location.PoiInfoBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;

import java.util.List;

/**
 * Created by Leon on 15/6/12.
 */
public class LocationSelectAdapter extends JupiterAdapter {

    private Context mContext;

    private List<PoiInfoBean> mPoiInfoBean;

    private OnSelectListener onSelectListener;

    public LocationSelectAdapter(Context context, List<PoiInfoBean> poiInfoBeans) {
        this.mContext = context;
        this.mPoiInfoBean = poiInfoBeans;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public int getCount() {
        return mPoiInfoBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mPoiInfoBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JupiterRowStyleSutitleLayout rowStyleSutitleLayout = null;

        PoiInfoBean poiInfoBean = mPoiInfoBean.get(position);

        if (AssertValue.isNotNull(convertView)) {
            rowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        } else {
            rowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
            rowStyleSutitleLayout.setSelectMode(true);
            rowStyleSutitleLayout.setShowTime(false);
            rowStyleSutitleLayout.setShowMainImage(false);
            rowStyleSutitleLayout.setShowArrow(false);
            if (AssertValue.isNotNull(onSelectListener)){
                rowStyleSutitleLayout.setOnSelectListener(onSelectListener);
            }
        }

        rowStyleSutitleLayout.setTag(poiInfoBean);
        rowStyleSutitleLayout.getTitleTV().setText(poiInfoBean.getName());
        rowStyleSutitleLayout.getSutitleTv().setText(poiInfoBean.getAddress());

        return rowStyleSutitleLayout;
    }
}
