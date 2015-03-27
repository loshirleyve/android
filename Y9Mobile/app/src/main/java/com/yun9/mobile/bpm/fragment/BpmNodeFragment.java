package com.yun9.mobile.bpm.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.bpm.activity.BpmEditNodeActivity;
import com.yun9.mobile.bpm.model.ProcessDefInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeInfo;
import com.yun9.mobile.bpm.model.SysInstProcess;
import com.yun9.mobile.bpm.model.SysInstProcessUserdef;
import com.yun9.mobile.bpm.view.BpmNodeItemView;
import com.yun9.mobile.framework.base.activity.BaseFragment;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;

public class BpmNodeFragment extends BaseFragment {
	
	private LinearLayout mBaseView;
	private LinearLayout llAddNode;
	private LinearLayout llUseTemplate;
	private LinearLayout nodeListView;
	private TextView tvDelete;
	private View mPopView;
	private PopupWindow mPopupWindow;
	private ProgressDialog progressDialog;
	private List<ProcessDefNodeInfo> nodes;
	private SysInstProcess sysInstProcess;
	private int editNodeIndex = -1;
	
	public static final int BPM_NODE_FRAGMENT_REQ_CODE = 120;
	
	private Context context;
	

	public BpmNodeFragment(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mBaseView = (LinearLayout) inflater.inflate(R.layout.fragment_bpm_node, null);
		mPopView = LayoutInflater.from(this.context).inflate(
				R.layout.bpm_node_frag_popupmenu, null);
		tvDelete = (TextView) mPopView.findViewById(R.id.tv_delete);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		return mBaseView;
	}

	@Override
	protected void initWidget() {
		llAddNode = (LinearLayout) mBaseView.findViewById(R.id.ll_add_node);
		llUseTemplate = (LinearLayout) mBaseView.findViewById(R.id.ll_use_template);
		nodeListView = (LinearLayout) mBaseView.findViewById(R.id.lv_node_list);
	}

	@Override
	protected void bindEvent() {
		llAddNode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BpmNodeFragment.this.mContext,
						BpmEditNodeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("totalNodeSize", nodes.size());
				intent.putExtras(bundle);
				startActivityForResult(intent, BPM_NODE_FRAGMENT_REQ_CODE);
			}
		});
		tvDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editNodeIndex != -1) {
					nodes.remove(editNodeIndex);
					editNodeIndex = -1;
					reDrawNode();
					mPopupWindow.dismiss();
				}
			}
		});
		llUseTemplate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
                .setMessage("将清空现有的节点配置！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressDialog = TipsUtil.openDialog(null, false, context);
						Resource resource = ResourceUtil.get("SysInstProcessUserdefQueryService");
						resource.param("instprocessid", sysInstProcess.getId());
						resource.invok(new AsyncHttpResponseCallback() {
							@Override
							public void onSuccess(Response response) {
								@SuppressWarnings("unchecked")
								List<SysInstProcessUserdef> ps = (List<SysInstProcessUserdef>) response.getPayload();
								if (AssertValue.isNotNullAndNotEmpty(ps)) {
									ProcessDefInfo processDefInfo = JsonUtil.jsonToBean(ps.get(0).getParams(), ProcessDefInfo.class);
									nodes.clear();
									if (AssertValue.isNotNull(processDefInfo.getNodes())) {
										nodes.addAll(processDefInfo.getNodes());
										reDrawNode();
									}
								}
								progressDialog.dismiss();
							}
							@Override
							public void onFailure(Response response) {
								progressDialog.dismiss();
								TipsUtil.showToast("获取模板失败！", context);
							}
						});
					}
                	
                }).setNegativeButton("取消",null)
                .show(); 
			}
		});
	}
	
	/**
	 * 加载节点数据
	 * 该方法必须由使用方调用，仅当执行该方法时，节点信息才会绘制
	 * @param sysInstProcess
	 * @param processConfigInfo
	 */
	public void load(SysInstProcess sysInstProcess,ProcessDefInfo processConfigInfo) {
		this.nodes = processConfigInfo.getNodes();
		this.sysInstProcess = sysInstProcess;
		if (!AssertValue.isNotNull(nodes)) {
			nodes = new ArrayList<ProcessDefNodeInfo>();
			processConfigInfo.setNodes(nodes);
		}
		if (!sysInstProcess.isDynamicNode()) {
			llAddNode.setVisibility(View.GONE);
			llUseTemplate.setVisibility(View.GONE);
		}
		drawNode();
	}

	private void drawNode() {
		for (int i = 0;i < nodes.size();i++) {
			drawNode(i,nodes.get(i));
		}
	}

	private void drawNode(final int index, final ProcessDefNodeInfo node) {
		BpmNodeItemView view = new BpmNodeItemView(context);
		view.load(node);
		if (sysInstProcess.isDynamicNode()) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(BpmNodeFragment.this.mContext,
							BpmEditNodeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("node", node);
					bundle.putSerializable("index", index);
					intent.putExtras(bundle);
					startActivityForResult(intent, BPM_NODE_FRAGMENT_REQ_CODE);
				}
			});
			view.setLongClickable(true);
			view.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
							.parseColor("#b0000000")));
					mPopupWindow.showAtLocation(mBaseView, Gravity.BOTTOM, 0, 0);
					mPopupWindow.setAnimationStyle(R.style.main_pop);
					mPopupWindow.setOutsideTouchable(true);
					mPopupWindow.setFocusable(true);
					mPopupWindow.update();
					editNodeIndex = index;
					return true;
				}
			});
		}
		this.nodeListView.addView(view,index);
	}

	public List<ProcessDefNodeInfo> getNodes() {
		if (!AssertValue.isNotNull(nodes)) {
			throw new RuntimeException("节点信息未初始化！");
		}
		return nodes;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==BPM_NODE_FRAGMENT_REQ_CODE && resultCode == BpmEditNodeActivity.BPM_EDIT_NODE_RETURN_CODE) {
			ProcessDefNodeInfo node = (ProcessDefNodeInfo) data.getExtras().get("node");
			int index = data.getExtras().getInt("index");
			if (index != -1) {
				replace(index,node);
			} else {
				nodes.add(node);
			}
			reDrawNode();
		}
	}

	private void reDrawNode() {
		nodeListView.removeAllViews();
		drawNode();
	}

	private void replace(int index, ProcessDefNodeInfo node) {
		ProcessDefNodeInfo oldOne = nodes.get(index);
		oldOne.setName(node.getName());
		oldOne.setAssigns(node.getAssigns());
	}
	
}
