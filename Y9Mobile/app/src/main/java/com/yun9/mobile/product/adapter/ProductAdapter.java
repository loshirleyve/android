package com.yun9.mobile.product.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yun9.mobile.R;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.product.entity.ProductInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {

	private Context context;
	private List<ProductInfo> list = null;
	private DisplayImageOptions options;

    public ProductAdapter(Context context, List<ProductInfo> list) {
        this.context = context;
        this.list = list;
    }
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 通过ViewHolder做一些优化
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            viewHolder.mLicense = (ImageView) convertView.findViewById(R.id.product_license_image);
            viewHolder.mCompany = (TextView) convertView.findViewById(R.id.product_company_text);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.product_name_text);
            viewHolder.mPrice = (TextView) convertView.findViewById(R.id.product_price_text);
            viewHolder.mIntroduce = (TextView) convertView.findViewById(R.id.product_introduce_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        // 根据数据源设置单元格显示的内容
        options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.flat_picture)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
        MyImageLoader.getInstance().displayImage(list.get(position).getImgid(), viewHolder.mLicense, options);
        viewHolder.mCompany.setText(list.get(position).getCompany());
        viewHolder.mName.setText(list.get(position).getName());
        viewHolder.mPrice.setText(list.get(position).getPrice());
        viewHolder.mIntroduce.setText(list.get(position).getIntroduce());
        
        return convertView;
	}
	
	private static class ViewHolder {
		ImageView mLicense;
		TextView mCompany;
		TextView mName;
		TextView mPrice;
		TextView mIntroduce;
    }

}
