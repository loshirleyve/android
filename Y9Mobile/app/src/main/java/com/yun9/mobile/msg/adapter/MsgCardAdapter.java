package com.yun9.mobile.msg.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.mobile.R;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.view.MsgCardItemView;
import com.yun9.mobile.msg.view.MsgCardOtherView;

public class MsgCardAdapter extends BaseAdapter {
	protected static final String TAG = "NewsAdapter";
	private Context mContext;
	private List<MyMsgCard> msgCards;

	public MsgCardAdapter(Context context, List<MyMsgCard> lists) {
		this.mContext = context;
		this.msgCards = lists;
	}

	@Override
	public int getCount() {
		if (msgCards != null) {
			return msgCards.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return msgCards.get(position);
	}

	public List<MyMsgCard> getMsgCards() {
		return msgCards;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		MyMsgCard msgCard = msgCards.get(position);

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(mContext,
					R.layout.fragment_msgcard_item, null);

			holder.msgCardView = (MsgCardItemView) convertView
					.findViewById(R.id.msgcard_item_view);
			holder.msgCardOtherView = (MsgCardOtherView) convertView
					.findViewById(R.id.msgcard_other_view);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.msgCardView.load(msgCard);
		holder.msgCardOtherView.load(msgCard);
		return convertView;
	}

	class Holder {
		MsgCardItemView msgCardView;
		MsgCardOtherView msgCardOtherView;
	}

}
