package com.yun9.mobile.framework.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.SysMdNavi;
import com.yun9.mobile.imageloader.MyImageLoader;

public class FunNavView extends RelativeLayout {

	private Context mContext;

	private List<SysMdNavi> navItems;

	private GridView gridview;

	public FunNavView(Context context) {
		super(context);
		this.mContext = context;
		this.init();
	}

	public FunNavView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.init();
	}

	public void load(List<SysMdNavi> navItems) {
		this.navItems = navItems;
		this.gridview.setAdapter(new FunNavAdapter());
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.gridview.setOnItemClickListener(onItemClickListener);
	}

	private void init() {
		LayoutInflater.from(mContext).inflate(R.layout.func_nav, this);
		this.gridview = (GridView) findViewById(R.id.gridview);

	}

	public class FunNavAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return navItems.size();
		}

		@Override
		public Object getItem(int position) {
			return navItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Holder holder = null;

			SysMdNavi navItem = navItems.get(position);

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.func_nav_item, parent, false);
				holder = new Holder();
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.image);
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.textView.setText(navItem.getName());
			holder.navItem = navItem;
			MyImageLoader.getInstance().displayImage(navItem.getIcopath(),holder.imageView);
			return convertView;
		}

	}

	public class Holder {
		public ImageView imageView;
		public TextView textView;
		public SysMdNavi navItem;
	}
}
