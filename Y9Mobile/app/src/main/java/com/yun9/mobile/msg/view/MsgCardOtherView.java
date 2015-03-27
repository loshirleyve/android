package com.yun9.mobile.msg.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.MsgcardLikeUtil;
import com.yun9.mobile.msg.activity.MsgCardActivity;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.model.MyMsgCardRelationship;

public class MsgCardOtherView extends BaseRelativeLayout {
	private Context mContext;

	private TextView tvForward;
	private TextView tvCommon;
	private TextView tvPraise;
	private ImageView ivPraise;
	private MyMsgCard msgCard;
	private RelativeLayout forwardRL;
	private RelativeLayout commonRL;
	private RelativeLayout praiseRL;

	public MsgCardOtherView(Context context) {
		super(context);
		this.mContext = context;
		this.initView();

	}

	public MsgCardOtherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.msgcard_other, this);

		tvForward=(TextView)findViewById(R.id.forward_tv);
		tvCommon = (TextView) findViewById(R.id.comment_tv);
		tvPraise = (TextView) findViewById(R.id.praise_tv);
		ivPraise = (ImageView) findViewById(R.id.praise_iv);
		forwardRL=(RelativeLayout) findViewById(R.id.forward_rl);
		commonRL = (RelativeLayout) findViewById(R.id.comment_rl);
		praiseRL = (RelativeLayout) findViewById(R.id.praise_rl);
		forwardRL.setOnClickListener(null);
		commonRL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						MsgCardActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgcard", msgCard);
				bundle.putSerializable("from", "comment");
				intent.putExtras(bundle);
				v.getContext().startActivity(intent);
			}
		});

		praiseRL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AssertValue.isNotNull(msgCard.getRelationship()) && msgCard.getRelationship().isLike()) {
					MsgcardLikeUtil.unLike(msgCard.getMain().getId(), new AsyncHttpResponseCallback() {
						@Override
						public void onSuccess(Response response) {
							ivPraise.setImageResource(R.drawable.like);
							msgCard.getRelationship().setLike(false);
							msgCard.setPraisecount(msgCard.getPraisecount() - 1);
							String liketext=msgCard.getPraisecount()==0?"赞":"赞("+msgCard.getPraisecount() + ")";
							tvPraise.setText(liketext);
							Toast.makeText(mContext, "已取消点赞", Toast.LENGTH_SHORT).show();
						}
						@Override
						public void onFailure(Response response) {
							Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (!AssertValue.isNotNull(msgCard.getRelationship())) {
						msgCard.setRelationship(new MyMsgCardRelationship());
					}
					MsgcardLikeUtil.like(msgCard.getMain().getId(), new AsyncHttpResponseCallback() {
						@Override
						public void onSuccess(Response response) {
							ivPraise.setImageResource(R.drawable.liked);
							msgCard.getRelationship().setLike(true);
							msgCard.setPraisecount(msgCard.getPraisecount() + 1);
							String liketext=msgCard.getPraisecount()==0?"赞":"赞("+msgCard.getPraisecount() + ")";
							tvPraise.setText(liketext);
							Toast.makeText(mContext, "点赞成功！", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onFailure(Response response) {
							Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		});
	}

	private void initIvPraise() {
		if (AssertValue.isNotNull(msgCard.getRelationship()) && msgCard.getRelationship().isLike()) {
			ivPraise.setImageResource(R.drawable.liked);
		} else {
			ivPraise.setImageResource(R.drawable.like);
		}
	}

	public void load(MyMsgCard msgCard) {
		String commontext=msgCard.getCommentcount()==0?"评论":"评论("+msgCard.getCommentcount() + ")";
		tvCommon.setText(commontext);
		String liketext=msgCard.getPraisecount()==0?"赞":"赞("+msgCard.getPraisecount() + ")";
		tvPraise.setText(liketext);
		this.msgCard = msgCard;
		initIvPraise();
	}

	public TextView getTvCommon() {
		return tvCommon;
	}

	public void setTvCommon(TextView tvCommon) {
		this.tvCommon = tvCommon;
	}

	public MyMsgCard getMsgCard() {
		return msgCard;
	}

	public void setMsgCard(MyMsgCard msgCard) {
		this.msgCard = msgCard;
	}

	public TextView getTvPraise() {
		return tvPraise;
	}

	public void setTvPraise(TextView tvPraise) {
		this.tvPraise = tvPraise;
	}

}
