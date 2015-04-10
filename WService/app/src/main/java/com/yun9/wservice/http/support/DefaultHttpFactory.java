package com.yun9.wservice.http.support;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.SyncHttpClient;


import com.yun9.wservice.bean.Bean;
import com.yun9.wservice.bean.BeanContext;
import com.yun9.wservice.bean.Injection;
import com.yun9.wservice.conf.PropertiesFactory;
import com.yun9.wservice.http.AsyncHttpResponseCallback;
import com.yun9.wservice.http.HttpException;
import com.yun9.wservice.http.HttpFactory;
import com.yun9.wservice.http.Request;
import com.yun9.wservice.http.RequestParams;
import com.yun9.wservice.http.Response;
import com.yun9.wservice.http.ResponseOriginal;
import com.yun9.wservice.repository.RepositoryOutput;
import com.yun9.wservice.repository.RepositoryParam;
import com.yun9.wservice.repository.Resource;
import com.yun9.wservice.repository.support.DefaultRequest;
import com.yun9.wservice.repository.support.DefaultRequestParams;
import com.yun9.wservice.repository.support.DefaultResponse;
import com.yun9.wservice.sys.SessionManager;
import com.yun9.wservice.util.AssertArgument;
import com.yun9.wservice.util.AssertValue;
import com.yun9.wservice.util.JsonUtil;
import com.yun9.wservice.util.Logger;
import com.yun9.wservice.util.SystemMethod;

public class DefaultHttpFactory implements HttpFactory, Bean, Injection {

	private AsyncHttpClient client;
	private SyncHttpClient syncHttpClient;

	private BeanContext beanContext;

	private PropertiesFactory propertiesFactory;

	private SessionManager sessionManger;

	private Logger logger = Logger.getLogger(DefaultHttpFactory.class);

	@Override
	public void post(Resource resource, final AsyncHttpResponseCallback callback) {
		AssertArgument.isNotNull(callback, "callback");
		AssertArgument.isNotNull(resource, "resource");

		AsyncHttpClient client = this.getAsyncHttpClient(resource);

		final Request request = this.createRequest(resource);

		String encoding = propertiesFactory.getString("app.config.encoding",
				"utf-8");

		String jsonRequest = this.requestToJson(request);

		logger.d("request json:" + jsonRequest);

		HttpEntity entity = null;
		try {
			entity = new StringEntity(jsonRequest, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException(e);
		}

		// 检查网络状态
		if (!SystemMethod.isOpenNetwork(beanContext.getApplicationContext())) {
			if (AssertValue.isNotNull(callback)) {
				Response response = new DefaultResponse();
				response.setCause(beanContext.getApplicationContext()
						.getResources()
						.getString(com.yun9.wservice.R.string.network_error));
				response.setCode("300");
				callback.onFailure(response);
			}
			return;
		}

		client.post(beanContext.getApplicationContext(), request.getResource()
				.getRepository().getBaseUrl(), entity, request.getResource()
				.getRepository().getContentType(),
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						
						try {
							String result = new String(responseBody, "utf-8");
							Log.i("", result);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
						Response response = createResponse(request, statusCode,
								headers, responseBody, null);
						if (AssertValue.isNotNull(callback)) {
							if ("100".equals(response.getCode())) {
								callback.onSuccess(response);
							} else {
								callback.onFailure(response);
							}
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						if (AssertValue.isNotNull(callback)) {
							Response response = createResponse(request,
									statusCode, headers, responseBody, error);
							callback.onFailure(response);
						}
					}
				});
	}

	
	
	
	
	
	
	private AsyncHttpClient getAsyncHttpClient(Resource resource) {
		if (resource.isFromService()) {
			AsyncHttpClient c = new SyncHttpClient();
			c.setTimeout(propertiesFactory.getInt(
					"app.config.http.timeout", 60 * 1000));
			c.setUserAgent(propertiesFactory.getString(
					"app.config.http.useragent", "android app"));

			c.setCookieStore(new PersistentCookieStore(beanContext
					.getApplicationContext()));
			return c;
		} else if (client == null) {
			synchronized (DefaultHttpFactory.class) {
				if (client == null) {
					client = new AsyncHttpClient();
					client.setTimeout(propertiesFactory.getInt(
							"app.config.http.timeout", 60 * 1000));
					client.setUserAgent(propertiesFactory.getString(
							"app.config.http.useragent", "android app"));

					client.setCookieStore(new PersistentCookieStore(beanContext
							.getApplicationContext()));
				}
			}
		}
		return client;
	}

	private AsyncHttpClient getAsyncHttpClient() {

		if (client == null) {
			synchronized (DefaultHttpFactory.class) {
				if (client == null) {
					client = new AsyncHttpClient();
					client.setTimeout(propertiesFactory.getInt(
							"app.config.http.timeout", 60 * 1000));
					client.setUserAgent(propertiesFactory.getString(
							"app.config.http.useragent", "android app"));

					client.setCookieStore(new PersistentCookieStore(beanContext
							.getApplicationContext()));
				}
			}
		}
		return client;
	}

	@Override
	public Class<?> getType() {
		return HttpFactory.class;
	}

	@Override
	public void injection(BeanContext beanContext) {
		this.beanContext = beanContext;
		this.propertiesFactory = this.beanContext.get(PropertiesFactory.class);
		this.sessionManger = this.beanContext.get(SessionManager.class);
	}

	private Request createRequest(Resource resource) {
		Request request = new DefaultRequest();
		RequestParams requestParams = new DefaultRequestParams();
		request.setRequestParams(requestParams);
		request.setResource(resource);

		requestParams.setAction(resource.getRepository().getAction());
		requestParams.setToken(resource.getRepository().getToken());

		if (AssertValue.isNotNull(resource.getRepository().getParams())) {
			for (RepositoryParam repositoryParam : resource.getRepository()
					.getParams()) {
				requestParams.data(repositoryParam.getKey(),
						repositoryParam.getValue());
			}
		}

		if (AssertValue.isNotNull(resource.getParams())) {
			for (Map.Entry<String, Object> entity : resource.getParams()
					.entrySet()) {
				requestParams.data(entity.getKey(), entity.getValue());
			}
		}

		// 初始化heander信息,当前用户，当前机构，设备类型
		requestParams
				.header(RequestParams.DEVICEID,
						sessionManger.getDevice().getDeviceid())
				.header(RequestParams.DEVICEMODEL,
						sessionManger.getDevice().getModel())
				.header(RequestParams.DEVICESERIAL,
						sessionManger.getDevice().getSerial());

		if (AssertValue.isNotNull(sessionManger.getAuthInfo())
				&& AssertValue.isNotNull(sessionManger.getAuthInfo()
						.getInstinfo())) {
			requestParams.header(RequestParams.INSTID, sessionManger
					.getAuthInfo().getInstinfo().getId());
		}

		if (AssertValue.isNotNull(sessionManger.getAuthInfo())
				&& AssertValue.isNotNull(sessionManger.getAuthInfo()
						.getUserinfo())) {
			requestParams.header(RequestParams.USERID, sessionManger
					.getAuthInfo().getUserinfo().getId());
		}

		if (AssertValue.isNotNullAndNotEmpty(resource.getHeader())) {
			for (Map.Entry<String, Object> entity : resource.getHeader()
					.entrySet()) {
				requestParams.header(entity.getKey(), entity.getValue());
			}

		}

		return request;
	}

	private Response createResponse(Request request, int statusCode,
			Header[] headers, byte[] responseBody, Throwable error) {

		Response response = new DefaultResponse();
		try {
			ResponseOriginal responseOriginal = response.createOriginal();

			response.setRequest(request);
			response.setOriginal(responseOriginal);

			responseOriginal.setStatusCode(statusCode);
			responseOriginal.setHeaders(headers);
			responseOriginal.setResponseBody(responseBody);
			responseOriginal.setError(error);

			if (AssertValue.isNotNull(responseBody) && statusCode == 200) {
				String json = new String(responseBody, "UTF-8");
				JSONObject jsonObj = new JSONObject(json);
				response.setCause(jsonObj.getString("cause"));
				response.setCode(jsonObj.getString("code"));
				response.setData(jsonObj.getString("data"));
				// logger.d("Response json:" + json);
				// logger.d("Data json:" + response.getData().toString());
			} else {
				if (AssertValue.isNotNull(error)) {
					response.setCause(error.getMessage());
				}
				response.setCode(statusCode + "");
				response.setData(null);
			}

			// 解析payload
			this.builderPayload(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.exception(e);
			response.setCode("500");
		}

		return response;

	}

	private String requestToJson(Request request) {
		if (AssertValue.isNotNull(request)) {
			// 将参数转换为json
			Gson gson = new Gson();
			String requestJson = gson.toJson(request.getRequestParams());
			return requestJson;
		} else {
			return "{}";
		}
	}

	private Response builderPayload(Response response)
			throws ClassNotFoundException, JSONException {

		RepositoryOutput output = null;
		try {
			output = response.getRequest().getResource().getRepository()
					.getOutput();
		} catch (Exception e) {
			return response;
		}

		if (AssertValue.isNotNullAndNotEmpty(response.getData())) {
			Gson gson = new Gson();

			if (AssertValue.isNotNull(output)
					&& AssertValue.isNotNullAndNotEmpty(output.getClassname())) {
				Class<?> outputClass = Class.forName(output.getClassname());

				if ("list".equals(output.getType())) {
					Object payload = JsonUtil.jsonToBeanList(
                            response.getData(), outputClass);
					response.setPayload(payload);
				} else {
					Object payload = JsonUtil.jsonToBean(response.getData(),
							outputClass);
					response.setPayload(payload);
				}

			} else {
				Map<?, ?> payload = gson
						.fromJson(response.getData(), Map.class);
				response.setPayload(payload);
			}
		}

		return response;
	}

	@Override
	public void get(Request request, AsyncHttpResponseCallback callback) {
		// TODO Auto-generated method stub
	}

	

	
	@Override
	public void uploadFile(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback) {
		AssertArgument.isNotNull(callback, "callback");
		AssertArgument.isNotNull(file, "file");
		// 检查网络状态
		if (!SystemMethod.isOpenNetwork(beanContext.getApplicationContext())) {
			if (AssertValue.isNotNull(callback)) {
				Response response = new DefaultResponse();
				response.setCause(beanContext.getApplicationContext()
						.getResources()
						.getString(com.yun9.wservice.R.string.network_error));
				response.setCode("300");
				callback.onFailure(response);
			}
			return;
		}

		AsyncHttpClient client = this.getAsyncHttpClient();

		String baseUrl = propertiesFactory.getString("app.config.resource.yun9.file.upload.baseUrl", "utf-8");
		String url = baseUrl + "?floderid=" + floderid + "&userid=" + userid
				+ "&instid=" + instid + "&level=" + level
				+ "&filetype=" + filetype
				+ "&descr=" + descr;
		String contentType = "multipart/form-data";
		String uploadFileKey = "22223";
		// logger.d(url);

		com.loopj.android.http.RequestParams params = new com.loopj.android.http.RequestParams();
		try {
			params.put(uploadFileKey, file, contentType);
		} catch (Exception e) {
			throw new RuntimeException("never appear");
		}

		client.post(beanContext.getApplicationContext(), url, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {
						Response response = createResponse(null, i, headers, bytes, null);

//						try {
//							String resultValue = new String(bytes, "utf-8");
//							Log.i("resultValue", resultValue);
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//						}

						if ("100".equals(response.getCode())) {
							callback.onSuccess(response);
						} else {
							callback.onFailure(response);
						}
					}

					@Override
					public void onFailure(int i, Header[] headers,
							byte[] bytes, Throwable throwable) {
						Response response = createResponse(null, i, headers,
								bytes, throwable);
						callback.onFailure(response);
					}
				});
	}

	@Override
	public void uploadFileSync(String userid, String instid, String floderid,String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback) {
		AssertArgument.isNotNull(callback, "callback");
		AssertArgument.isNotNull(file, "file");
		// 检查网络状态
		if (!SystemMethod.isOpenNetwork(beanContext.getApplicationContext())) {
			if (AssertValue.isNotNull(callback)) {
				Response response = new DefaultResponse();
				response.setCause(beanContext.getApplicationContext()
						.getResources()
						.getString(com.yun9.wservice.R.string.network_error));
				response.setCode("300");
				callback.onFailure(response);
			}
			return;
		}

		SyncHttpClient client = getSyncHttpClient();

		String baseUrl = propertiesFactory.getString("app.config.resource.yun9.file.upload.baseUrl", "utf-8");
		String url = baseUrl + "?floderid=" + floderid + "&userid=" + userid
				+ "&instid=" + instid + "&level=" + level
				+ "&filetype=" + filetype
				+ "&descr=" + descr;
		String contentType = "multipart/form-data";
		String uploadFileKey = "22223";
		// logger.d(url);

		com.loopj.android.http.RequestParams params = new com.loopj.android.http.RequestParams();
		try {
			params.put(uploadFileKey, file, contentType);
		} catch (Exception e) {
			throw new RuntimeException("never appear");
		}

		client.post(beanContext.getApplicationContext(), url, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {
						Response response = createResponse(null, i, headers, bytes, null);

//						try {
//							String resultValue = new String(bytes, "utf-8");
//							Log.i("resultValue", resultValue);
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//						}

						if ("100".equals(response.getCode())) {
							callback.onSuccess(response);
						} else {
							callback.onFailure(response);
						}
					}

					@Override
					public void onFailure(int i, Header[] headers,
							byte[] bytes, Throwable throwable) {
						Response response = createResponse(null, i, headers,
								bytes, throwable);
						callback.onFailure(response);
					}
				});
	}
	
	private SyncHttpClient getSyncHttpClient() {

		if (syncHttpClient == null) {
			synchronized (DefaultHttpFactory.class) {
				if (syncHttpClient == null) {
					syncHttpClient = new SyncHttpClient();
					syncHttpClient.setTimeout(propertiesFactory.getInt(
							"app.config.http.timeout", 60 * 1000));
					syncHttpClient.setUserAgent(propertiesFactory.getString(
							"app.config.http.useragent", "android app"));

					syncHttpClient.setCookieStore(new PersistentCookieStore(beanContext
							.getApplicationContext()));
				}
			}
		}
		return syncHttpClient;
	}

	@Override
	public void postSync(Resource resource, final AsyncHttpResponseCallback callback) {
		AssertArgument.isNotNull(callback, "callback");
		AssertArgument.isNotNull(resource, "resource");

		SyncHttpClient client = getSyncHttpClient();

		final Request request = createRequest(resource);

		String encoding = propertiesFactory.getString("app.config.encoding",
				"utf-8");

		String jsonRequest = this.requestToJson(request);

		logger.d("request json:" + jsonRequest);

		HttpEntity entity = null;
		try {
			entity = new StringEntity(jsonRequest, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException(e);
		}

		// 检查网络状态
		if (!SystemMethod.isOpenNetwork(beanContext.getApplicationContext())) {
			if (AssertValue.isNotNull(callback)) {
				Response response = new DefaultResponse();
				response.setCause(beanContext.getApplicationContext()
						.getResources()
						.getString(com.yun9.wservice.R.string.network_error));
				response.setCode("300");
				callback.onFailure(response);
			}
			return;
		}

		client.post(beanContext.getApplicationContext(), request.getResource()
				.getRepository().getBaseUrl(), entity, request.getResource()
				.getRepository().getContentType(),
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						
						try {
							String result = new String(responseBody, "utf-8");
							Log.i("", result);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
						Response response = createResponse(request, statusCode,
								headers, responseBody, null);
						if (AssertValue.isNotNull(callback)) {
							if ("100".equals(response.getCode())) {
								callback.onSuccess(response);
							} else {
								callback.onFailure(response);
							}
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						if (AssertValue.isNotNull(callback)) {
							Response response = createResponse(request,
									statusCode, headers, responseBody, error);
							callback.onFailure(response);
						}
					}
				});
	}

}
