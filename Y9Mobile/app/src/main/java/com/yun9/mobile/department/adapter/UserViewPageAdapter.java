package com.yun9.mobile.department.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.fragment.UserDetailPopView;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.roundimage.RoundImageView;
/**
 * 
 * 项目名称：yun9mobile 类名称： UserViewPageAdapter 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-10-20上午10:07:19 修改人：ruanxiaoyu 修改时间：2014-10-20上午10:07:19 修改备注：
 * 
 * @version
 * 
 */
public class UserViewPageAdapter extends BaseAdapter {
	private boolean isselect;
	private Context context; // 运行上下文
	private List<UserBean> listItems; // 用户信息列表
	private LayoutInflater listContainer; // 视图容器
	private ProgressDialog progressDialog;
	private Map<Integer, View> _cache = new HashMap<Integer, View>();
	private int viewId = 0;

	public final class UListItemView { // 自定义控件集合
		public RoundImageView userImage;
		public TextView userId;
		public TextView userName;
		public TextView signature;//个性签名
		public TextView userPhone;
		public ImageView imageRight;// 点击是图标
		public ImageView imageRightSel1;// 选择是图标
		public ImageView imageRightSel2;// 选中是图标
		public TextView isSel;// 判断选中的状态
		public UserBean userBean;
	}
	private UListItemView listItemView;

	private Activity baseActivity;
	private View baseView;
	private int state;// 記住選擇和取消按鈕的狀態值
	private Map<String, User> selItems;
	
	private int selectUserOrOrg;
	
	public UserViewPageAdapter(Context context,View baseView,ProgressDialog progressDialog,Activity baseActivity,int selectUserOrOrg) {
		this.context = context;
		this.baseView=baseView;
		this.baseActivity=baseActivity;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.progressDialog=progressDialog;
		this.selectUserOrOrg=selectUserOrOrg;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!AssertValue.isNotNullAndNotEmpty(selItems))
			selItems = new HashMap<String, User>();
		// 自定义视图
		if (convertView == null) {
			listItemView = new UListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.user_listview_items,
					null);
			convertView.setId(viewId++);
			_cache.put(convertView.getId(), convertView);
			// 获取控件对象
			listItemView.userId = (TextView) convertView.findViewById(R.id.uid);
			listItemView.userImage = (RoundImageView) convertView
					.findViewById(R.id.userImage);
			listItemView.userName = (TextView) convertView
					.findViewById(R.id.userName);
			listItemView.signature = (TextView) convertView
					.findViewById(R.id.signature);
			listItemView.imageRight = (ImageView) convertView
					.findViewById(R.id.image_right);
			listItemView.imageRightSel1 = (ImageView) convertView
					.findViewById(R.id.image_right_sel1);
			listItemView.imageRightSel2 = (ImageView) convertView
					.findViewById(R.id.image_right_sel2);
			listItemView.isSel = (TextView) convertView
					.findViewById(R.id.isSel);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
			convertView.setOnClickListener(popWindow);

		} else {
			listItemView = (UListItemView) convertView.getTag();
		}
		listItemView.userId.setText(listItems.get(position).getUser().getId());
		// 设置文字和图片
		MyImageLoader.getInstance().displayImage(listItems.get(position).getUser().getHeaderfileid(), listItemView.userImage);
		listItemView.userName.setText(listItems.get(position).getUser().getName());
		if(listItems.get(position).getUser().getSignature()==null)
		{	listItemView.signature.setText("他什么也没说......");}
		else
		{
			if(listItems.get(position).getUser().getSignature().length()>15)
			{
				String signature=listItems.get(position).getUser().getSignature().substring(0,15);
				listItemView.signature.setText(signature+"......");
			}
			else
			listItemView.signature.setText(listItems.get(position).getUser().getSignature());
		}
		listItemView.userBean = listItems.get(position);
		if (isselect == true) {
			listItemView.isSel.setText("1");
			listItemView.imageRight.setVisibility(View.VISIBLE);
		} else {
			if (this.getState() == 1) {
				if (selItems != null
						&& selItems.containsKey(listItemView.userId.getText()
								.toString())) {
					listItemView.isSel.setText("0");
					listItemView.imageRightSel2.setVisibility(View.VISIBLE);
				} else {
					listItemView.isSel.setText("1");
					listItemView.imageRightSel1.setVisibility(View.VISIBLE);
					listItemView.imageRightSel2.setVisibility(View.GONE);
					listItemView.imageRight.setVisibility(View.GONE);
				}
			} else {
				listItemView.isSel.setText("1");
				listItemView.imageRight.setVisibility(View.VISIBLE);
			}
		}
		progressDialog.dismiss();
		return convertView;
	}
	
	public void getUserImage(String fileid,ImageView imageView)
	{
		MyImageLoader.getInstance().displayImage(fileid, imageView);
	}


	// 用户信息
	OnClickListener popWindow = new OnClickListener() {
		@Override
		public void onClick(View v) {
			UListItemView item = (UListItemView) v.getTag();
			if (getState() == 0) {
				UserDetailPopView popview=new UserDetailPopView(context,baseView,baseActivity);
				popview.show(item.userBean);
			} else {
				// 选择用户控制按钮
				// 选择项
				if(selectUserOrOrg==UserConstant.SELECT_USER)
				{
					if (item.isSel.getText().equals("1")) {
						item.imageRightSel2.setVisibility(View.VISIBLE);
						selItems.put(item.userId.getText().toString(), item.userBean.getUser());
						item.isSel.setText("0");
					} else {
						item.imageRightSel2.setVisibility(View.GONE);
						item.imageRightSel1.setVisibility(View.VISIBLE);
						selItems.remove(item.userId.getText().toString());
						item.isSel.setText("1");
					}
				}
				
				else if(selectUserOrOrg==UserConstant.SELECT_USER_RADIO)//单选
				{
					if (item.isSel.getText().equals("1")) {
						for (Map.Entry<Integer, View> e : get_cache().entrySet()) {
							UListItemView itemview = (UListItemView) e.getValue().getTag();
							itemview.imageRight.setVisibility(View.GONE);
							itemview.imageRightSel1.setVisibility(View.VISIBLE);
							itemview.imageRightSel2.setVisibility(View.GONE);
							itemview.isSel.setText("1");
						}
						item.imageRightSel2.setVisibility(View.VISIBLE);
						getSelItems().clear();
						selItems.put(item.userId.getText().toString(), item.userBean.getUser());
						item.isSel.setText("0");
					} else if(item.isSel.getText().equals("0")){
						item.imageRightSel2.setVisibility(View.GONE);
						item.imageRightSel1.setVisibility(View.VISIBLE);
						getSelItems().clear();
						item.isSel.setText("1");
					}
				}
			}

		}
	};
	@Override
	public int getCount() {
		if (AssertValue.isNotNullAndNotEmpty(listItems)) {
			return listItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public boolean isIsselect() {
		return this.isselect;
	}

	public void setIsselect(boolean isselect) {
		this.isselect = isselect;
	}

	public List<UserBean> getListItems() {
		return listItems;
	}

	public void setListItems(List<UserBean> listItems) {
		this.listItems = listItems;
	}

	public Map<Integer, View> get_cache() {
		return _cache;
	}

	public void set_cache(Map<Integer, View> _cache) {
		this._cache = _cache;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Map<String, User> getSelItems() {
		return selItems;
	}

	public void setSelItems(Map<String, User> selItems) {
		this.selItems = selItems;
	}

}
