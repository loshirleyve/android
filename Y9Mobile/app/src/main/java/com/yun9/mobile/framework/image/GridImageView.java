package com.yun9.mobile.framework.image;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.image.GridImageAdapter.Holder;
import com.yun9.mobile.framework.util.AssertValue;

public class GridImageView extends BaseRelativeLayout {

	private Context mContext;

	private GridView gridImageView;

	private List<DmImageItem> imageItems;

	private OnImageClickListener onImageClickListener;

	public GridImageView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public GridImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public void setOnImageClickListener(
			OnImageClickListener onImageClickListener) {
		this.onImageClickListener = onImageClickListener;
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.grid_image, this);
		gridImageView = (GridView) findViewById(R.id.grid_image);

		if (AssertValue.isNotNull(onItemClickListener)) {
			gridImageView.setOnItemClickListener(onItemClickListener);
		}

	}

	private void initImage() {
		GridImageAdapter gridImageAdapter = new GridImageAdapter(this.mContext,
				imageItems, gridImageView);
		gridImageView.setAdapter(gridImageAdapter);
	}

	public void load(List<DmImageItem> imageItems) {
		this.imageItems = imageItems;
		this.initImage();
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			GridImageAdapter.Holder holder = (Holder) view.getTag();

			if (AssertValue.isNotNull(onImageClickListener)) {
				onImageClickListener.onImageClick(holder.imageItem, view,
						position);
				;
			}

		}
	};

	public interface OnImageClickListener {
		public void onImageClick(DmImageItem imageItem, View view, int position);
	}

}
