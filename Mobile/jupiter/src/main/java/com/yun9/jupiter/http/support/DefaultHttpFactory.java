package com.yun9.jupiter.http.support;

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


import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.HttpException;
import com.yun9.jupiter.http.HttpFactory;
import com.yun9.jupiter.http.Request;
import com.yun9.jupiter.http.RequestParams;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.http.ResponseOriginal;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.repository.RepositoryOutput;
import com.yun9.jupiter.repository.RepositoryParam;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertArgument;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.mobile.annotation.BeanInject;

public class DefaultHttpFactory implements HttpFactory, Bean, Initialization {

    private AsyncHttpClient client;
    private SyncHttpClient syncHttpClient;


    @BeanInject
    private PropertiesManager propertiesManager;

    @BeanInject
    private DeviceManager deviceManager;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private JupiterApplication appContext;

    private Logger logger = Logger.getLogger(DefaultHttpFactory.class);

    private int network_error_resource;

    @Override
    public void post(final Resource resource, final AsyncHttpResponseCallback callback) {
        AssertArgument.isNotNull(callback, "callback");
        AssertArgument.isNotNull(resource, "resource");

        AsyncHttpClient client = this.getAsyncHttpClient(resource);

        final Request request = this.createRequest(resource);

        String encoding = propertiesManager.getString("app.config.encoding",
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
        if (!PublicHelp.isOpenNetwork(appContext.getApplicationContext())) {
            if (AssertValue.isNotNull(callback)) {
                Response response = new DefaultResponse();
                response.setCause(appContext.getApplicationContext()
                        .getResources()
                        .getString(network_error_resource));


                response.setCode("300");
                callback.onFailure(response);
                callback.onFinally(response);
            }
            return;
        }

        client.post(appContext.getApplicationContext(), request.getResource()
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
                                try {
                                    callback.onSuccess(response);
                                } finally {
                                    callback.onFinally(response);
                                }
                            } else {
                                try {
                                    callback.onFailure(response);
                                } finally {
                                    callback.onFinally(response);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        if (AssertValue.isNotNull(callback)) {
                            Response response = createResponse(request,
                                    statusCode, headers, responseBody, error);
                            try {
                                callback.onFailure(response);
                            } finally {
                                callback.onFinally(response);
                            }
                        }
                    }
                });
    }


    private AsyncHttpClient getAsyncHttpClient(Resource resource) {
        if (resource.isFromService()) {
            AsyncHttpClient c = new SyncHttpClient();
            c.setTimeout(propertiesManager.getInt(
                    "app.config.http.timeout", 60 * 1000));
            c.setUserAgent(propertiesManager.getString(
                    "app.config.http.useragent", "android app"));

            c.setCookieStore(new PersistentCookieStore(appContext
                    .getApplicationContext()));
            return c;
        } else if (client == null) {
            synchronized (DefaultHttpFactory.class) {
                if (client == null) {
                    client = new AsyncHttpClient();
                    client.setTimeout(propertiesManager.getInt(
                            "app.config.http.timeout", 60 * 1000));
                    client.setUserAgent(propertiesManager.getString(
                            "app.config.http.useragent", "android app"));

                    client.setCookieStore(new PersistentCookieStore(appContext
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
                    client.setTimeout(propertiesManager.getInt(
                            "app.config.http.timeout", 60 * 1000));
                    client.setUserAgent(propertiesManager.getString(
                            "app.config.http.useragent", "android app"));

                    client.setCookieStore(new PersistentCookieStore(appContext
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
                        deviceManager.getDevice().getDeviceid())
                .header(RequestParams.DEVICEMODEL,
                        deviceManager.getDevice().getModel())
                .header(RequestParams.DEVICESERIAL,
                        deviceManager.getDevice().getSerial());

        if (AssertValue.isNotNullAndNotEmpty(sessionManager.getInst().getId())) {
            requestParams.header(RequestParams.INSTID, sessionManager.getInst().getId());
        }

        if (AssertValue.isNotNullAndNotEmpty(sessionManager.getUser().getId())) {
            requestParams.header(RequestParams.USERID, sessionManager.getUser().getId());
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
                response.setResponseCache(new DefaultResponseCache(jsonObj.getString("cache")));
                // logger.d("Response json:" + json);
                // logger.d("Data json:" + response.getData().toString());
            } else {
                if (AssertValue.isNotNull(error)) {
                    response.setCause(error.getMessage());
                }
                response.setCode(statusCode + "");
                response.setData(null);
                response.setResponseCache(null);
            }

            // 解析payload
            this.builderPayload(response);

            //缓存数据
            this.cacheData(response);
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

    private void cacheData(Response response) {
        if (AssertValue.isNotNull(response.getResponseCache()) && AssertValue.isNotNullAndNotEmpty(response.getResponseCache().getCacheFiles())) {
            for (Map.Entry<String, CacheFile> entry : response.getResponseCache().getCacheFiles().entrySet()) {
                FileCache.getInstance().putFile(entry.getKey(), entry.getValue());
            }
        }
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
        if (!PublicHelp.isOpenNetwork(appContext.getApplicationContext())) {
            if (AssertValue.isNotNull(callback)) {
                Response response = new DefaultResponse();
                response.setCause(appContext.getApplicationContext()
                        .getResources()
                        .getString(network_error_resource));
                response.setCode("300");
                callback.onFailure(response);
            }
            return;
        }

        AsyncHttpClient client = this.getAsyncHttpClient();

        String baseUrl = propertiesManager.getString("app.config.resource.yun9.file.upload.baseUrl", "utf-8");
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

        client.post(appContext.getApplicationContext(), url, params,
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
    public void uploadFileSync(String userid, String instid, String floderid, String level, String filetype, String descr, File file, final AsyncHttpResponseCallback callback) {
        AssertArgument.isNotNull(callback, "callback");
        AssertArgument.isNotNull(file, "file");
        // 检查网络状态
        if (!PublicHelp.isOpenNetwork(appContext.getApplicationContext())) {
            if (AssertValue.isNotNull(callback)) {
                Response response = new DefaultResponse();
                response.setCause(appContext.getApplicationContext()
                        .getResources()
                        .getString(network_error_resource));
                response.setCode("300");
                callback.onFailure(response);
            }
            return;
        }

        SyncHttpClient client = getSyncHttpClient();

        String baseUrl = propertiesManager.getString("app.config.resource.yun9.file.upload.baseUrl", "utf-8");
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

        client.post(appContext.getApplicationContext(), url, params,
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
                    syncHttpClient.setTimeout(propertiesManager.getInt(
                            "app.config.http.timeout", 60 * 1000));
                    syncHttpClient.setUserAgent(propertiesManager.getString(
                            "app.config.http.useragent", "android app"));

                    syncHttpClient.setCookieStore(new PersistentCookieStore(appContext
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

        String encoding = propertiesManager.getString("app.config.encoding",
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
        if (!PublicHelp.isOpenNetwork(appContext.getApplicationContext())) {
            if (AssertValue.isNotNull(callback)) {
                Response response = new DefaultResponse();
                response.setCause(appContext.getApplicationContext()
                        .getResources()
                        .getString(network_error_resource));
                response.setCode("300");
                callback.onFailure(response);
            }
            return;
        }

        client.post(appContext.getApplicationContext(), request.getResource()
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

    @Override
    public void init(BeanManager beanManager) {
        logger.d("Http Factory init.");
        this.network_error_resource = com.yun9.jupiter.R.string.jupiter_network_error;
    }
}
