package com.yun9.mobile.camera.impl.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.yun9.mobile.camera.domain.DmNetDocument;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.interfaces.A4FragmentNetDocument;
import com.yun9.mobile.camera.interfaces.presenter.Pre4uiNetDocumentFragment;
import com.yun9.mobile.camera.interfaces.presenter.Ui4PreNetDocumentFragment;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkService;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.util.AssertValue;

public class ImplPre4uiNetDocumentFragment implements Pre4uiNetDocumentFragment{

	private final String NETDOCUMENTNUM = "20";
	
	private Ui4PreNetDocumentFragment ui;
	private Context context;
	private CommandNetworkService command;
	private List<DmNetDocument> netDocuments;
	
	private A4FragmentNetDocument a4Fragment;
	/**
	 * @param ui
	 * @param context
	 */
	public ImplPre4uiNetDocumentFragment(Ui4PreNetDocumentFragment ui,Context context, A4FragmentNetDocument a4Fragment) {
		super();
		this.ui = ui;
		this.context = context;
		this.a4Fragment = a4Fragment;
		
	}
	
	@Override
	public void onCreate() {
		init();
	}

	
	private void init(){
		FactoryCommandNetworkService factoryCommand = new FactoryCommandNetworkServiceYiDianTong();
		
		command = factoryCommand.creatCommandNetworkService();
		
		netDocuments = ui.getNetDocuments();
	}
	

	@Override
	public void onPullDownToRefresh() {
		getLatestNetDocuments(NETDOCUMENTNUM);
	}
	
	
	private void getLatestNetDocuments(String num){
		
		command.getLatestNetDocuments(num, new AsyncHttpResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				
				List<FileByUserId> fileInfos = (List<FileByUserId>) response.getPayload();
				if(!AssertValue.isNotNullAndNotEmpty(fileInfos)){
					ui.onPullDownRefreshComplete();
				}
				int num = ui.addNetDocument(netDocuments, fileInfos);
				ui.onPullDownRefreshComplete();
				if(num > 0){
					Collections.sort(netDocuments);
				}
				ui.notifyDataSetChanged();
				ui.showToast("刷新了 " + num);
			}
			
			@Override
			public void onFailure(Response response) {
				ui.showToast("失败");
			}
		});
	}

	@Override
	public void onPullUpToRefresh() {
		loadMoreDocument(NETDOCUMENTNUM);
	}

	private void loadMoreDocument(String num){
		String lastdownid;
		if(netDocuments != null){
			lastdownid = netDocuments.get(netDocuments.size() - 1).getFileInfo().getId();
		}else{
			lastdownid = "0";
		}
		
		command.loadMoreNetDocumentUserLevel(lastdownid , num, new AsyncHttpResponseCallback() {
			
			@Override
			public void onSuccess(Response response) {
				List<FileByUserId> fileInfos = (List<FileByUserId>) response.getPayload();
				if(!AssertValue.isNotNullAndNotEmpty(fileInfos)){
					ui.onPullDownRefreshComplete();
				}
				int num = ui.addNetDocument(netDocuments, fileInfos);
				
				ui.onPullUpRefreshComplete();
				if(num > 0){
					Collections.sort(netDocuments);
				}
				ui.notifyDataSetChanged();
				ui.showToast("加载了 " + num);
			}
			
			@Override
			public void onFailure(Response response) {
				ui.showToast("失败");
			}
		});
	}

	@Override
	public void OnItemClickListener(AdapterView<?> parent, View view, int position, long id) {
		ModeAlbum mode = getModeAlbum();
		if(mode == ModeAlbum.BROWSE){
			// TODU OnItemClickListener BROWSE
			go2Browse();
		}else if(mode == ModeAlbum.CHOICE){
			go2Choice(position, view);
		}
	}
	
	private void go2Browse() {
		ui.showToast("未开放");
	}

	private void go2Choice(int position, View view) {
		boolean stat = isChecked(position);
		boolean isWillChecked = !stat;
		if(isWillChecked){
			if(getSelectableDocumentNum() <= 0){
				ui.showToast("最多可以选择" + getMaxChooseDocumentNum());
    			return ;
			}else{
				updateCheck(position, true, view);
			}
		}else{
			
			updateCheck(position, false, view);
		}
	}

	private void updateCheck(int position, boolean isCheck, View view) {
		setCheck(position, isCheck);
		ui.setChecked(position, isCheck, view);
		if(isCheck){
			a4Fragment.notifyAddDocument(1);
		}else{
			a4Fragment.notifyRemoveDocument(1);
		}
	}

	private void setCheck(int position, boolean isCheck) {
		 netDocuments.get(position).setChecked(isCheck);
	}

	private int getMaxChooseDocumentNum() {
		return a4Fragment.getMaxChooseDocumentNum();
	}

	private int getSelectableDocumentNum() {
		return a4Fragment.getSelectableDocumentNum();
	}

	private boolean isChecked(int position) {
		return netDocuments.get(position).isChecked();
	}

	private ModeAlbum getModeAlbum(){
		return a4Fragment.getAlbumMode();
	}

	@Override
	public void deleteSelectedNetFileSync(final CallBackYiDianTong callBack) {
		
		final List<String> selectedIds = getSelectedIds();
		if(!AssertValue.isNotNullAndNotEmpty(selectedIds)){
			callBack.onSuccess(0);
		}
		
		command.deleteNetFileSync(selectedIds, new AsyncHttpResponseCallback() {
			
			@Override
			public void onSuccess(Response response) {
				
				// TODU 删除文件
				List<DmNetDocument> deleteDocuments = new ArrayList<DmNetDocument>();
				for(DmNetDocument document : netDocuments){
					for(String id : selectedIds){
						if(id.equals(document.getFileInfo().getId())){
							deleteDocuments.add(document);
						}
					}
				}
				
				a4Fragment.notifyRemoveDocument(deleteDocuments.size());
				netDocuments.removeAll(deleteDocuments);
				ui.notifyDataSetChanged();
				
				callBack.onSuccess(selectedIds.size());
				
			}
			
			@Override
			public void onFailure(Response response) {
				callBack.onSuccess(0);
			}
		});
	}

	private List<String> getSelectedIds() {
		
		List<String> selectedIds = new ArrayList<String>();
		for(DmNetDocument document : netDocuments){
			if(document.isChecked()){
				selectedIds.add(document.getFileInfo().getId());
			}
		}
		
		return selectedIds;
	}
}
