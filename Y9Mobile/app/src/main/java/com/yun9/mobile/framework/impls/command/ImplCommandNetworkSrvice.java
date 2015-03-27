package com.yun9.mobile.framework.impls.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;

public class ImplCommandNetworkSrvice implements CommandNetworkService{

	
	String TYPE_TXT = "txt";
	String TYPE_PDF = "pdf";
	String TYPE_DOC = "doc";
	String TYPE_DOCX = "docx";
	String TYPE_PPT = "ppt";
	String TYPE_PPTX = "pptx";
	String TYPE_XLS = "xls";
	String TYPE_XLSX = "xlsx";
	String TYPE_WPS = "wps";
	
	private Context context;
	
	private FileFactory fileFactory;
	
	private List<String> documentTypes;
	
	public ImplCommandNetworkSrvice() {

		super();
		
		init();
	}
	
	private void init(){
    	fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
    	
    	initDocumentType();
	}
	
	private void initDocumentType(){
		documentTypes = new ArrayList<String>();
		
		documentTypes.add(TYPE_TXT);
		documentTypes.add(TYPE_PDF);
		documentTypes.add(TYPE_DOC);
		documentTypes.add(TYPE_DOCX);
		documentTypes.add(TYPE_PPT);
		documentTypes.add(TYPE_PPTX);
		documentTypes.add(TYPE_XLS);
		documentTypes.add(TYPE_XLSX);
		documentTypes.add(TYPE_WPS);
	}

	@Override
	public void getLatestNetDocuments(String num, AsyncHttpResponseCallback callback) {
	
		
		String TYPE_TXT = "txt";
		String TYPE_PDF = "pdf";
		String TYPE_DOC = "doc";
		String TYPE_DOCX = "docx";
		String TYPE_PPT = "ppt";
		String TYPE_PPTX = "pptx";
		String TYPE_XLS = "xls";
		String TYPE_XLSX = "xlsx";
		String TYPE_WPS = "wps";
		
		List<String> types = new ArrayList<String>();
		types.add(TYPE_TXT);
		types.add(TYPE_PDF);
		types.add(TYPE_DOC);
		types.add(TYPE_DOCX);
		types.add(TYPE_PPT);
		types.add(TYPE_PPTX);
		types.add(TYPE_XLS);
		types.add(TYPE_XLSX);
		types.add(TYPE_WPS);
		
		getLatestNetDocuments(num, types, callback);
	}
	
	
	@Override
	public void getLatestNetDocuments(String num, List<String> types, AsyncHttpResponseCallback callback) {
		fileFactory.getLatestNetDocuments(num, types, callback);
	}

	@Override
	public void uploadImgFileUserLevel(File file, AsyncHttpResponseCallback callback) {
		fileFactory.uploadImgFileUserLevel(file, callback);
	}

	@Override
	public void uploadImgFileSystemLevel(File file, AsyncHttpResponseCallback callback) {
		fileFactory.uploadImgFileSystemLevel(file, callback);
	}

	@Override
	public void uploadImgFileUserLevelSync(File file,AsyncHttpResponseCallback callback) {
		fileFactory.uploadImgFileUserLevelSync(file, callback);
	}

	@Override
	public void uploadDocumentFileUserLevelSync(File file, AsyncHttpResponseCallback callback) {
		fileFactory.uploadDocumentFileUserLevelSync(file, callback);
	}

	@Override
	public void loadMoreNetDocument(String lastdownid, String limitrow, String level, AsyncHttpResponseCallback callback) {
		loadMoreNetFile(lastdownid, limitrow, documentTypes, CommandNetworkService.FILETYPE_DOC, level, callback);
	}
	
	
	@Override
	public void loadMoreNetDocumentUserLevel(String lastdownid, String limitrow, AsyncHttpResponseCallback callback) {
		loadMoreNetDocument(lastdownid, limitrow, LEVEL_USER, callback);
	}
	

	@Override
	public void loadMoreNetFile(String lastdownid, String limitrow, List<String> types, String filetype, String level, AsyncHttpResponseCallback callback) {
		fileFactory.loadMoreNetFile(lastdownid, limitrow, types, filetype, level, callback);
	}

	@Override
	public void deleteNetFileSync(List<String> fileIds, AsyncHttpResponseCallback callback) {
		fileFactory.deleteNetFileSync(fileIds, callback);
	}
	
}
