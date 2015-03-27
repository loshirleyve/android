package com.yun9.mobile.framework.view;


import com.yun9.mobile.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageAndText extends RelativeLayout {
	
	private TextView tvDesc;
	private ImageView ivImage;
	
	private int imageSrc;
	private String text;
	
	public ImageAndText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView(context, attrs);
	}
	
	public ImageAndText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}
	
	
	private void initView(Context context, AttributeSet attrs){
		View.inflate(context, R.layout.view_imagetext, this);
		tvDesc = (TextView) this.findViewById(R.id.tvDesc);
		ivImage = (ImageView) this.findViewById(R.id.ivImage);
		
//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.imageText);
//		imageSrc = a.getResourceId(R.styleable.imageText_view_image, -1);
//		a.recycle();
		
//		text = attrs.getAttributeValue(R.styleable.imageText_view_text);
//		imageSrc = attrs.getAttributeResourceValue(R.styleable.imageText_view_image, -1);
		
		text = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yun9.mobile", "view_text");
		imageSrc = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.yun9.mobile", "view_image", -1);
	

		tvDesc.setText(text);
		ivImage.setBackgroundResource(imageSrc);
	}
	
	public void setITText(String text){
		if(tvDesc != null){
			tvDesc.setText(text);
		}
	}
	
	public void setITImage(int imageID){
		ivImage.setBackgroundResource(imageID);
	}
}
