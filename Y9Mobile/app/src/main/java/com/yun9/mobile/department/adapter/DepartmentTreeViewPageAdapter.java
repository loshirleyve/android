package com.yun9.mobile.department.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.treenode.bean.Node;
import com.yun9.mobile.treenode.bean.TreeListViewAdapter;

/**
 * 
 * 项目名称：yun9mobile 类名称： DepartmentGroupViewPageAdapter 类描述： 创建人： ruanxiaoyu
 * 创建时间： 2014-10-20下午3:24:56 修改人：ruanxiaoyu 修改时间：2014-10-20下午3:24:56 修改备注：
 * 
 * @version
 * 
 */
public class DepartmentTreeViewPageAdapter<T> extends TreeListViewAdapter<T>
{
	public DepartmentTreeViewPageAdapter(ListView mTree, Context context,
			List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
	}

	private boolean isDselect;
	private Map<String,Org> orgItems; // 部门信息列表

	public final class DListItemView { // 自定义控件集合
		public TextView id;
		public TextView name;
		public TextView levelid;
		public ImageView imageRight;// 点击是图标
		public ImageView imageRightSel1;// 选择是图标
		public ImageView imageRightSel2;// 选中是图标
		public TextView isSel;// 判断选中的状态
		public Org org;
	}
	private DListItemView listItemView;
	private OnClickListener clickitem;

	private int stateD;// 記住選擇和取消按鈕的狀態值
	private Map<String, Org> selorgItems;
	private Map<Integer, View> _Dcache = new HashMap<Integer, View>();// 缓存
	private int viewId = 0;


	@Override
	public View getConvertView(Node node, int position, View convertView,
			ViewGroup parent) {
		if (!AssertValue.isNotNullAndNotEmpty(selorgItems))
			selorgItems = new HashMap<String, Org>();
		// 自定义视图
		if (convertView == null) {
			listItemView = new DListItemView();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.department_listview_items, parent, false);
			convertView.setId(viewId++);
			_Dcache.put(convertView.getId(), convertView);// 放入缓存中

			// 获取控件对象
			listItemView.id = (TextView) convertView
					.findViewById(R.id.departmentId);
			listItemView.name = (TextView) convertView
					.findViewById(R.id.departmentName);
			listItemView.levelid = (TextView) convertView
					.findViewById(R.id.levelid);
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
			convertView.setOnClickListener(clickitem);
		} else {
			listItemView = (DListItemView) convertView.getTag();
		}
		listItemView.id.setText(String.valueOf(node.getId()).trim());
		listItemView.name.setText(node.getName().trim());
		listItemView.levelid.setText(String.valueOf(node.getLevel()).trim());
		listItemView.org = orgItems.get(String.valueOf(node.getId()).trim());
		if (isDselect == true) {
			listItemView.isSel.setText("1");
			listItemView.imageRight.setVisibility(View.VISIBLE);
		} else {
			if (stateD == 1) {
				if (selorgItems != null
						&& selorgItems.containsKey(listItemView.id.getText()
								.toString())) {
					listItemView.isSel.setText("0");
					listItemView.imageRightSel2.setVisibility(View.VISIBLE);
					listItemView.imageRightSel1.setVisibility(View.GONE);
					listItemView.imageRight.setVisibility(View.GONE);
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
		return convertView;
	}
	

	public OnClickListener getClickitem() {
		return clickitem;
	}

	public void setClickitem(OnClickListener clickitem) {
		this.clickitem = clickitem;
	}

	public Map<String,Org> getOrgItems() {
		return orgItems;
	}

	public void setOrgItems(Map<String,Org> orgItems) {
		this.orgItems = orgItems;
	}

	public boolean isDselect() {
		return isDselect;
	}

	public void setDselect(boolean isDselect) {
		this.isDselect = isDselect;
	}

	public int getStateD() {
		return stateD;
	}

	public void setStateD(int stateD) {
		this.stateD = stateD;
	}

	public Map<String, Org> getSelorgItems() {
		return selorgItems;
	}

	public void setSelorgItems(Map<String, Org> selorgItems) {
		this.selorgItems = selorgItems;
	}

	public Map<Integer, View> get_Dcache() {
		return _Dcache;
	}

	public void set_Dcache(Map<Integer, View> _Dcache) {
		this._Dcache = _Dcache;
	}


}
