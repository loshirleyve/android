package com.yun9.mobile.camera.impl.pre4ui;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.mobile.camera.activity.DocumentActivity;
import com.yun9.mobile.camera.domain.DmDocument;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.impl.ImplDocumentActivityEntrance;
import com.yun9.mobile.camera.interfaces.A4FragmentDocument;
import com.yun9.mobile.camera.interfaces.A4FragmentLocalDocument;
import com.yun9.mobile.camera.interfaces.DocumentActivityEntrance;
import com.yun9.mobile.camera.interfaces.presenter4ui.Pre4uiDocumentFragment;
import com.yun9.mobile.camera.interfaces.ui4Presenter.Ui4PreLocalDocumentFragment;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilFile;

public class ImplPre4UiLocalDocumentFragment implements Pre4uiDocumentFragment{
	
	private Ui4PreLocalDocumentFragment ui;
	private LinkedList<File> files;
	private ArrayList<DmDocument> listDmDocument;
	
	private Context context;
	
	private A4FragmentLocalDocument a4FragmentDocument;
	
	
	
	/**
	 * @param ui
	 */
	public ImplPre4UiLocalDocumentFragment(Ui4PreLocalDocumentFragment ui, Context context, A4FragmentLocalDocument a4FragmentDocument) {
		super();
		this.ui = ui;
		this.context = context;
		this.a4FragmentDocument = a4FragmentDocument;
		init();
	}
	
	
	private void init(){
		files = new LinkedList<File>();
		listDmDocument = new ArrayList<DmDocument>();
	}


	@Override
	public void work() {
		
		// 递归查找文档
		findDocument();
	}
	
	private void findDocument() {
		new DocumentsFindAsyncTask().execute();		
	}

	private class DocumentsFindAsyncTask extends AsyncTask<Void , Void, Void>{
		@Override
		protected void onPreExecute() {
			ui.setLoadingVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			getDmDocuments(files);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if(ui == null){
				return ;
			}
			ui.setLoadingVisibility(View.GONE);
			if(AssertValue.isNotNullAndNotEmpty(listDmDocument)){
				// 通知适配器数据变化
				ui.notifyDataSetChanged();
				
			}else{
				// 搜索不到相关文件
			}
		}
	}
	
	// 将递归搜索到的相关文件，转成数据对象
	private void getDmDocuments(List<File> lists){
		UtilFile.searchFile(Environment.getExternalStorageDirectory(), "", lists);
		
		if(AssertValue.isNotNullAndNotEmpty(lists)){
			for(File file : lists){
				DmDocument document = new DmDocument(file);
				listDmDocument.add(document);
			}
		}
	}
	

	@Override
	public List<DmDocument> getDocumentList() {
		return listDmDocument;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ModeAlbum mode = getModeAlbum();
		if(mode == ModeAlbum.BROWSE){
			// go to documentActivity
			String filePath = listDmDocument.get(position).getFile().getAbsolutePath();
			DocumentActivityEntrance entrance = new ImplDocumentActivityEntrance(this.context);
			entrance.go2Document(filePath);
		}else if(mode == ModeAlbum.CHOICE){
			go2Choice(position, view);
		}
	}
	
	private void go2Choice(int position, View view){
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
	private void updateCheck(int position, boolean isCheck, View view){
		
		setCheck(position, isCheck);
		ui.setChecked(position, isCheck, view);
		if(isCheck){
			a4FragmentDocument.notifyAddDocument(1);
		}else{
			a4FragmentDocument.notifyRemoveDocument(1);
		}
	}
	
	
	private int getMaxChooseDocumentNum(){
		return a4FragmentDocument.getMaxChooseDocumentNum();
	}
	
	private int getSelectableDocumentNum(){
		return a4FragmentDocument.getSelectableDocumentNum();
	}
	
	private ModeAlbum getModeAlbum(){
		return a4FragmentDocument.getAlbumMode();
	}
	
	private boolean isChecked(int position){
		return listDmDocument.get(position).isChecked();
	}
	
	private void setCheck(int position, boolean isCheck){
		listDmDocument.get(position).setChecked(isCheck);
	}


	private class uploadLocalDocument implements Runnable{

		CountDownLatch latch;
		DmDocument document;
		CommandNetworkService command;
		
		/**
		 * @param latch
		 * @param document
		 * @param command
		 */
		public uploadLocalDocument(CountDownLatch latch, DmDocument document, CommandNetworkService command) {
			super();
			this.latch = latch;
			this.document = document;
			this.command = command;
		}

		@Override
		public void run() {
			doUploadLocalDocument();
		}
		
		private void doUploadLocalDocument(){
			File file = document.getFile();
			command.uploadDocumentFileUserLevelSync(file, new AsyncHttpResponseCallback() {
				
				@Override
				public void onSuccess(Response response) {
					FileInfo fileInfo = (FileInfo) response.getPayload();
					System.out.println(fileInfo.getId() + "name :" + fileInfo.getName());
					succussNum++;
					System.out.println("succussNum :" + succussNum);
					
					a4FragmentDocument.notifyRemoveDocument(1);
					document.setChecked(false);
					
					latch.countDown();
					
				}
				
				@Override
				public void onFailure(Response response) {
					System.out.println("onFailure");
					
					latch.countDown();
				}
			});
		}
	}
	
	
	private int succussNum;
	@Override
	public void uploadLocalDocument(CallBackYiDianTong callBack) {
		List<DmDocument> documents = getSelectedDocument();
		if(!AssertValue.isNotNullAndNotEmpty(documents)){
			callBack.onSuccess(0);
			return ;
		}
		
		CommandNetworkService command = new FactoryCommandNetworkServiceYiDianTong().creatCommandNetworkService();
		
		// 上传文件
		ExecutorService pool = Executors.newFixedThreadPool(documents.size());
		CountDownLatch latch = new CountDownLatch(documents.size());
		succussNum = 0;
		for(DmDocument document : documents){
			pool.execute(new uploadLocalDocument(latch, document, command));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		pool.shutdown();
		
		ui.notifyDataSetChanged();
		
		callBack.onSuccess(succussNum);
		
	}
	
	private List<DmDocument> getSelectedDocument(){
		if(!AssertValue.isNotNullAndNotEmpty(listDmDocument)){
			return null;
		}
		
		
		List<DmDocument> documents = new LinkedList<DmDocument>();
		for(DmDocument document: listDmDocument){
			if(document.isChecked()){
				documents.add(document);
			}
		}
		return documents;
	}


	@Override
	public void onDestory() {
		ui = null;
		a4FragmentDocument = null;
	}
}
