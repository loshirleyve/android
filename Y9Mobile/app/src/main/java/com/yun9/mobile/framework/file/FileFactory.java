package com.yun9.mobile.framework.file;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

import java.io.File;
import java.util.List;

public interface FileFactory {
	
  	String FLODERID_DEFAULT = "1";
 	String LEVEL_USER = "user";
 	String LEVEL_SYSTEM = "system";
	String FILETYPE_IMG = "img";
  	String FILETYPE_DOCUMENT = "doc";
	

	@Deprecated
	public void genFileUrlById(String fileId, AsyncHttpResponseCallback callback);
	
	@Deprecated
    public void genFileUrlByUesrId(String lastupid,String limitrow,String instid,String uesrId, AsyncHttpResponseCallback callback);

    public void getLatestNetPhoto(String limitrow, AsyncHttpResponseCallback callback);
	
    @Deprecated
    public void loadMoreFileInfoByUserId(String lastdownid, String limitrow, String instid, String uesrId, final AsyncHttpResponseCallback callback);

    public void loadMoreNetPhoto(String lastdownid, String limitrow, final AsyncHttpResponseCallback callback);
    

    public void getLatestNetDocuments(String limitrow, List<String> types, AsyncHttpResponseCallback callback);
    
    /**
     * 删除用户云端文件
     * @param fileid
     */
    public void deleteUserFile(String fileid, AsyncHttpResponseCallback callback);

    public void deleteNetPhoto(List<String> fileIds, AsyncHttpResponseCallback callback);
    
    
    public void deleteNetFileSync(List<String> fileIds, AsyncHttpResponseCallback callback);
    

    public void uploadImgFileUserLevel(File file, AsyncHttpResponseCallback callback);
    
    public void uploadImgFileSystemLevel(File file, AsyncHttpResponseCallback callback);
  
    public void uploadImgFileUserLevelSync(File file, AsyncHttpResponseCallback callback);
    
    public void uploadDocumentFileUserLevelSync(File file, AsyncHttpResponseCallback callback);
    
    
	public void loadMoreNetFile(String lastdownid, String limitrow,  List<String> types, String filetype, String level, AsyncHttpResponseCallback callback);
}
