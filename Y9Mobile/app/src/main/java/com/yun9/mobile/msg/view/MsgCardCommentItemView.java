package com.yun9.mobile.msg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.msg.bean.MsgCardCommentBean;
import com.yun9.mobile.msg.model.MsgCardComment;

public class MsgCardCommentItemView  extends BaseRelativeLayout{
	private Context mContext;

	private ImageView ivUserPicture;
	private TextView tvUserName;
	private TextView tvComment;
	private TextView tvTime;
	private TextView tvDevice;
	private MsgCardCommentBean msgCardCommentBean;

	public MsgCardCommentItemView(Context context) {
		super(context);
		this.mContext = context;
		this.initView();

	}

	public MsgCardCommentItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.msgcard_comment, this);

		ivUserPicture = (ImageView) findViewById(R.id.msgcard_user_picture);
		tvUserName = (TextView) findViewById(R.id.msgcard_user_name);
		tvComment = (TextView) findViewById(R.id.msgcard_comment);
		tvTime = (TextView) findViewById(R.id.msgcard_time);
		tvDevice = (TextView) findViewById(R.id.msgcard_device);
	}

	public void load(MsgCardCommentBean bean) {
		this.msgCardCommentBean = bean;
		MsgCardComment comment = bean.getComment();
		tvUserName.setText(bean.getUser().getName());
		if (MsgCardComment.Type.DICE.equals(comment.getType())) {	// 如果是掷骰子
			Drawable d = getResources().getDrawable(R.drawable.dice_easyicon_net_35);
			d.setBounds(0, 0, 30, 30); //必须设置图片大小，否则不显示
			Drawable d2 = getResources().getDrawable(R.drawable.dice_easyicon_net_32);
			d2.setBounds(0, 0, 30, 30); //必须设置图片大小，否则不显示
			tvComment.setCompoundDrawables(d , null, d2, null);
			tvComment.setText(bean.getUser().getName()+"掷出了："+comment.getContent());
		} else {
			tvComment.setText(comment.getContent());
		}

		tvTime.setText(DateUtil.getDateStr(comment.getCreatedate()));
		tvDevice.setText(comment.getDevicename());
		if (AssertValue.isNotNullAndNotEmpty(bean.getUser().getHeaderfileid())) {
			ivUserPicture.setImageResource(R.drawable.ic_launcher);
			MyImageLoader.getInstance().displayImage(bean.getUser().getHeaderfileid(), ivUserPicture);
		} else {
			ivUserPicture.setImageResource(R.drawable.ic_launcher);
		}
	}

	public ImageView getIvUserPicture() {
		return ivUserPicture;
	}

	public TextView getTvUserName() {
		return tvUserName;
	}

	public TextView getTvComment() {
		return tvComment;
	}

	public TextView getTvTime() {
		return tvTime;
	}

	public TextView getTvDevice() {
		return tvDevice;
	}

	public MsgCardCommentBean getMsgCardCommentBean() {
		return msgCardCommentBean;
	}

	public void setMsgCardCommentBean(MsgCardCommentBean msgCardCommentBean) {
		this.msgCardCommentBean = msgCardCommentBean;
	}

}
