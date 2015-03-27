package com.yun9.mobile.framework.interfaces.command;

import java.io.File;
import java.util.List;

import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface CommandNetworkService {
	
	String FILETYPE_IMG = FileFactory.FILETYPE_IMG;
	String FILETYPE_DOC = FileFactory.FILETYPE_DOCUMENT;
	String FLODERID_DEFAULT = FileFactory.FLODERID_DEFAULT;
 	String LEVEL_USER = FileFactory.LEVEL_USER;
 	String LEVEL_SYSTEM = FileFactory.LEVEL_SYSTEM;
	
 	
	public void loadMoreNetDocumentUserLevel(String lastdownid, String limitrow, AsyncHttpResponseCallback callback);
	
	public void loadMoreNetDocument(String lastdownid, String limitrow, String level, AsyncHttpResponseCallback callback);
	
	public void loadMoreNetFile(String lastdownid, String limitrow,  List<String> types, String filetype, String level, AsyncHttpResponseCallback callback);
	
	public void getLatestNetDocuments(String num, List<String> types, AsyncHttpResponseCallback callback);
	
	public void getLatestNetDocuments(String num, AsyncHttpResponseCallback callback);
	
	public void uploadDocumentFileUserLevelSync(File file, AsyncHttpResponseCallback callback);
	
	public void uploadImgFileUserLevel(File file, AsyncHttpResponseCallback callback);
	
	public void uploadImgFileSystemLevel(File file, AsyncHttpResponseCallback callback);

	public void uploadImgFileUserLevelSync(File file, AsyncHttpResponseCallback callback);
	
	public void deleteNetFileSync(List<String> fileIds, AsyncHttpResponseCallback callback);
}
