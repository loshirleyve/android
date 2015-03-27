package com.yun9.mobile.msg.view;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.msg.model.MyMsgCard;

public class MsgCardView extends BaseRelativeLayout {

	private Context mContext;
	private Scroller mScroller;
	private GestureDetector mGestureDetector;

	private ImageView ivUserPicture;
	private TextView tvUserName;
	private TextView tvSubject;
	private TextView tvContent;
	private TextView tvTime;
	private TextView tvDevice;
	private MyMsgCard msgCard;

	public MsgCardView(Context context) {
		super(context);
		this.mContext = context;
		this.initView();

	}

	public MsgCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.msgcard_detail, this);

		this.setClickable(true);
		this.setLongClickable(true);
		mScroller = new Scroller(mContext);
		mGestureDetector = new GestureDetector(mContext, onGestureListener);

		ivUserPicture = (ImageView) findViewById(R.id.msgcard_user_picture);
		tvUserName = (TextView) findViewById(R.id.msgcard_user_name);
		tvContent = (TextView) findViewById(R.id.msgcard_context);
		tvTime = (TextView) findViewById(R.id.msgcard_time);
		tvSubject = (TextView) findViewById(R.id.msgcard_subject);
		tvDevice = (TextView) findViewById(R.id.msgcard_device);

	}

	private OnGestureListener onGestureListener = new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			int disY = (int) ((distanceY - 0.5) / 2);
			beginScroll(0, disY);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
	};

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			System.out.println("computeScroll()---> " + "mScroller.getCurrX()="
					+ mScroller.getCurrX() + "," + "mScroller.getCurrY()="
					+ mScroller.getCurrY());
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 必须执行postInvalidate()从而调用computeScroll()
			// 其实,在此调用invalidate();亦可
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// 手指抬起时回到最初位置
			prepareScroll(0, 0);
			break;
		default:
			// 其余情况交给GestureDetector手势处理
			return mGestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	// 设置滚动的相对偏移
	protected void beginScroll(int dx, int dy) {
		System.out.println("smoothScrollBy()---> dx=" + dx + ",dy=" + dy);
		// 第一,二个参数起始位置;第三,四个滚动的偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy);
		System.out.println("smoothScrollBy()---> " + "mScroller.getFinalX()="
				+ mScroller.getFinalX() + "," + "mScroller.getFinalY()="
				+ mScroller.getFinalY());

		// 必须执行invalidate()从而调用computeScroll()
		invalidate();
	}

	// 滚动到目标位置
	protected void prepareScroll(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		beginScroll(dx, dy);
	}

	public void load(MyMsgCard msgCard) {
		this.msgCard = msgCard;
		tvUserName.setText(msgCard.getFormuser().getName());
		tvContent.setText(new SpannableStringUtil().fromMsgCardContent(msgCard.getMain().getContent(),tvContent));
		tvSubject.setText(msgCard.getMain().getSubject());
		tvTime.setText(DateUtil.getDateStr(msgCard.getMain().getCreatedate()));
		tvDevice.setText(msgCard.getMain().getDevicename());
		if (AssertValue.isNotNullAndNotEmpty(msgCard.getFormuser().getHeaderfileid())) {
			MyImageLoader.getInstance().displayImage(msgCard.getFormuser().getHeaderfileid(), ivUserPicture);
		}
	}

	public ImageView getIvUserPicture() {
		return ivUserPicture;
	}

	public TextView getTvUserName() {
		return tvUserName;
	}

	public TextView getTvSubject() {
		return tvSubject;
	}

	public TextView getTvContent() {
		return tvContent;
	}

	public TextView getTvTime() {
		return tvTime;
	}

	public TextView getTvDevice() {
		return tvDevice;
	}

	public MyMsgCard getMsgCard() {
		return msgCard;
	}

}
