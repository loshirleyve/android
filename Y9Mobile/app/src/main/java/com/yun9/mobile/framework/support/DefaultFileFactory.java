package com.yun9.mobile.framework.support;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.SeekBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.HttpFactory;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;

public class DefaultFileFactory implements FileFactory, Bean, Injection {

    private ResourceFactory resourceFactory;
    
    private HttpFactory httpFactory;
    private SessionManager sessionManager;
    private String userid;
    private String instid;
    
    
    
    private final String KEY_LEVEL = "level";
    private final String KEY_TYPES = "types";
    private final String KEY_FILETYPE = "filetype";
    private final String KEY_LASTDOWNID = "lastdownid";
    private final String KEY_LIMITROW = "limitrow";
    private final String KEY_INSTID = "instid";
    private final String KEY_USERID = "userid";
    
    private final String KEY_FILE_IDS = "ids";
    
	
	private void doUploadFile(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback){
		
		httpFactory.uploadFile(userid, instid, floderid, level, filetype, descr, file, new AsyncHttpResponseCallback() {
			
			@Override
			public void onSuccess(Response response) {
		        try {
                    String jsonData = response.getData();
                    Type listType = new TypeToken<ArrayList<FileInfo>>(){}.getType();
                    ArrayList<FileInfo> fileInfos = new Gson().fromJson(jsonData, listType);

                    FileInfo fileInfo = fileInfos.get(0);
                    response.setPayload(fileInfo);
                    callback.onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("服务器返回Json不匹配");
                }				
			}
			
			@Override
			public void onFailure(Response response) {
				callback.onFailure(response);				
			}
		});
	}
    

    @Override
	public void genFileUrlById(String fileId, final AsyncHttpResponseCallback callback) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id",fileId);
        params.put("expiration", "3600000");
        fileUrlenticate(params,new AsyncHttpResponseCallback()
        {
            @Override
            public void onSuccess(Response response)
            {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Response response)
            {
                callback.onFailure(response);
            }
        });
	}

    @Override
    public void genFileUrlByUesrId(String lastupid, String limitrow, String instid, String uesrId, final AsyncHttpResponseCallback callback)
    {
        Map<String,Object> header = new HashMap<String, Object>();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put(null,null);
        header.put("lastupid",lastupid);
        header.put("limitrow",limitrow);
        header.put("instid",instid);
        header.put("userid",uesrId);
        fileUrlenticateByUserId(header,params,new AsyncHttpResponseCallback()
        {
            @Override
            public void onSuccess(Response response)
            {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Response response)
            {
                callback.onFailure(response);
            }
        });
    }

    @Override
	public void injection(BeanContext beanContext) {
    	
    	sessionManager = beanContext.get(SessionManager.class);
        resourceFactory = beanContext.get(ResourceFactory.class);

        httpFactory = beanContext.get(HttpFactory.class);
	}
    

    
	@Override
	public Class<?> getType() {
		return FileFactory.class;
	}

    private void fileUrlenticate(Map<String,Object> params,AsyncHttpResponseCallback callback)
    {
        Resource sysInstFileGenUrlByIDService = resourceFactory.create("SysInstFileGenUrlByID");
        sysInstFileGenUrlByIDService.setParams(params);
        sysInstFileGenUrlByIDService.invok(callback);
    }

	@Override
	public void loadMoreFileInfoByUserId(String lastdownid, String limitrow, String instid, String uesrId, final AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(null, null);
		header.put("lastdownid", lastdownid);
		header.put("limitrow", limitrow);
		header.put("instid", instid);
		header.put("userid", uesrId);
		fileUrlenticateByUserId(header, params,new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				callback.onSuccess(response);
			}

			@Override
			public void onFailure(Response response) {
				callback.onFailure(response);
			}
		});
	}

	@Override
	public void deleteUserFile(String fileid, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		header.put("userid", userid);
		params.put("id", fileid);	
		doDeleteUserFile(header, params, callback);
	}
	
	private void doDeleteUserFile(Map<String,Object> header,Map<String,Object> params,AsyncHttpResponseCallback callback){
		Resource service = resourceFactory.create("SysInstFileRemoveService");
		service.setHeader(header);
		service.setParams(params);
		service.invok(callback);	
	}

	@Override
	public void getLatestNetPhoto(String limitrow, AsyncHttpResponseCallback callback) {
	    Map<String,Object> header = new HashMap<String, Object>();
        Map<String,Object> params = new HashMap<String, Object>();
        
        String userid = sessionManager.getAuthInfo().getUserinfo().getId();
        String instid = sessionManager.getAuthInfo().getInstinfo().getId();
        params.put("level","user");
        header.put("lastupid","0");
        header.put("limitrow",limitrow);
        header.put("instid",instid);
        header.put("userid",userid);
        fileUrlenticateByUserId(header, params, callback);
	}

	@Override
	public void loadMoreNetPhoto(String lastdownid, String limitrow, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
	    String userid = sessionManager.getAuthInfo().getUserinfo().getId();
        String instid = sessionManager.getAuthInfo().getInstinfo().getId();
        params.put("level","user");
		header.put("lastdownid", lastdownid);
		header.put("limitrow", limitrow);
		header.put("instid", instid);
		header.put("userid", userid);
		fileUrlenticateByUserId(header, params, callback);
	}

	@Override
	public void deleteNetPhoto(List<String> fileIds, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		header.put("userid", userid);
		params.put("ids", fileIds);	
		doDeleteUserFile(header, params, callback);
	}

	@Override
	public void getLatestNetDocuments(String limitrow, List<String> types, AsyncHttpResponseCallback callback) {
	    Map<String,Object> header = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String, Object>();
        
        String userid = sessionManager.getAuthInfo().getUserinfo().getId();
        String instid = sessionManager.getAuthInfo().getInstinfo().getId();
       
        header.put("lastupid","0");
        header.put("limitrow",limitrow);
        header.put("instid",instid);
        header.put("userid",userid);
        
        data.put("level", "user");
        data.put("types", types);
        fileUrlenticateByUserId(header, data, callback);
	}
	
    private void fileUrlenticateByUserId(Map<String,Object> header,Map<String,Object> params,AsyncHttpResponseCallback callback)
    {
        Resource sysInstFileQueryService = resourceFactory.create("SysInstFileQueryService");
        sysInstFileQueryService.setHeader(header);
        sysInstFileQueryService.setParams(params);
        sysInstFileQueryService.invok(callback);
    }

	
    @Override
	public void uploadImgFileUserLevel(File file, AsyncHttpResponseCallback callback) {
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		String instid = sessionManager.getAuthInfo().getInstinfo().getId();
		String floderid = "1";
		String level = "user";
		String filetype = "img";
		String descr = "descr";
		doUploadFile(userid, instid, floderid, level, filetype, descr, file, callback);
	}

	
    @Override
	public void uploadImgFileSystemLevel(File file, AsyncHttpResponseCallback callback) {
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		String instid = sessionManager.getAuthInfo().getInstinfo().getId();
		String floderid = "1";
		String level = "user";
		String filetype = "system";
		String descr = "descr";
		doUploadFile(userid, instid, floderid, level, filetype, descr, file, callback);
	}


	@Override
	public void uploadImgFileUserLevelSync(File file,AsyncHttpResponseCallback callback) {
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		String instid = sessionManager.getAuthInfo().getInstinfo().getId();
		String floderid = "1";
		String level = "user";
		String filetype = "img";
		String descr = "descr";
		doUploadFileSync(userid, instid, floderid, level, filetype, descr, file, callback);
	}
	
	private void doUploadFileSync(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback){
		
		httpFactory.uploadFileSync(userid, instid, floderid, level, filetype, descr, file, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
		        try {
                    String jsonData = response.getData();
                    Type listType = new TypeToken<ArrayList<FileInfo>>(){}.getType();
                    ArrayList<FileInfo> fileInfos = new Gson().fromJson(jsonData, listType);

                    FileInfo fileInfo = fileInfos.get(0);
                    response.setPayload(fileInfo);
                    callback.onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("服务器返回Json不匹配");
                }				
			}
			
			@Override
			public void onFailure(Response response) {
				callback.onFailure(response);				
			}
		});
	}

	@Override
	public void uploadDocumentFileUserLevelSync(File file, AsyncHttpResponseCallback callback) {
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		String instid = sessionManager.getAuthInfo().getInstinfo().getId();
		String floderid = FLODERID_DEFAULT;
		String level = LEVEL_USER;
		String filetype = FILETYPE_DOCUMENT;
		String descr = "descr";
		doUploadFileSync(userid, instid, floderid, level, filetype, descr, file, callback);
	}

	@Override
	public void loadMoreNetFile(String lastdownid, String limitrow, List<String> types, String filetype, String level, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
	    String userid = sessionManager.getAuthInfo().getUserinfo().getId();
        String instid = sessionManager.getAuthInfo().getInstinfo().getId();
        data.put(KEY_LEVEL, level);
        data.put(KEY_TYPES, types);
        data.put(KEY_FILETYPE, filetype);
		header.put(KEY_LASTDOWNID, lastdownid);
		header.put(KEY_LIMITROW, limitrow);
		header.put(KEY_INSTID, instid);
		header.put(KEY_USERID, userid);
		fileUrlenticateByUserId(header, data, callback);
	}


	@Override
	public void deleteNetFileSync(List<String> fileIds, AsyncHttpResponseCallback callback) {
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		header.put(KEY_USERID, userid);
		params.put(KEY_FILE_IDS, fileIds);	
		doDeleteUserFile(header, params, callback);
	}
}
