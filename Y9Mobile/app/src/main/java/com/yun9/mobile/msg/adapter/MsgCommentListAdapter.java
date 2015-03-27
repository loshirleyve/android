package com.yun9.mobile.msg.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.msg.bean.MsgCardCommentBean;
import com.yun9.mobile.msg.model.MsgCardComment;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   MsgCommentListAdapter
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-24下午3:09:53
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-24下午3:09:53  
 * 修改备注：    
 * @version     
 *     
 */
public class MsgCommentListAdapter extends BaseAdapter {

	private List<MsgCardCommentBean> commentlist;
	private LayoutInflater listContainer; // 视图容器
	private CommentItemView commentitemview;
	public final class CommentItemView { // 自定义控件集合
		public TextView username;
		public TextView comment;
	}
	
	public MsgCommentListAdapter(Context context,List<MsgCardCommentBean> commentlist) {
		this.commentlist=commentlist;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		if (convertView == null) {
			commentitemview = new CommentItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.msgcard_comment_listitem,
					null);
			commentitemview.username = (TextView) convertView
					.findViewById(R.id.msg_username);
			commentitemview.comment = (TextView) convertView
					.findViewById(R.id.msg_comment);
			convertView.setTag(commentitemview);
		} else {
			commentitemview = (CommentItemView) convertView.getTag();
		}
		
		commentitemview.username.setText(commentlist.get(position).getUser().getName());
		commentitemview.comment.setText(commentlist.get(position).getComment().getContent());
		if (MsgCardComment.Type.DICE.equals(commentlist.get(position).getComment().getType())) {	// 如果是掷骰子
			Drawable d = convertView.getContext().getResources().getDrawable(R.drawable.dice_easyicon_net_35);
			d.setBounds(0, 0, 30, 30); //必须设置图片大小，否则不显示
			commentitemview.comment.setCompoundDrawables(d , null, null, null);
		} else {
			commentitemview.comment.setCompoundDrawables(null , null, null, null);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		if(AssertValue.isNotNullAndNotEmpty(commentlist))
		{
			return commentlist.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return commentlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	
}   
