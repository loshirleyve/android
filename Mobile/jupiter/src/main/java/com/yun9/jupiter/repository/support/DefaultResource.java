package com.yun9.jupiter.repository.support;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Repository;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;

import java.util.HashMap;
import java.util.Map;


public class DefaultResource implements Resource {

    private Repository repository;

    private ResourceFactory resourceFactory;

    private Map<String, Object> params;

    private Map<String, Object> header;

    private Page page = new Page();

    private boolean fromService;

    public DefaultResource(Repository repository,
                           ResourceFactory resourceFactory) {
        this.repository = repository;
        this.resourceFactory = resourceFactory;
    }

    @Override
    public void invok(AsyncHttpResponseCallback callback) {
        this.resourceFactory.invok(this, callback);
    }

    @Override
    public void invokSync(AsyncHttpResponseCallback callback) {
        resourceFactory.invokSync(this, callback);
    }

    public Repository getRepository() {
        return repository;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Resource param(String key, Object value) {

        if (!AssertValue.isNotNull(params)) {
            this.params = new HashMap<String, Object>();
        }

        if (AssertValue.isNotNull(key) && AssertValue.isNotNull(value)) {
            this.params.put(key, value);
        }

        return this;
    }

    @Override
    public Resource header(String key, Object value) {

        if (!AssertValue.isNotNull(header)) {
            this.header = new HashMap<String, Object>();
        }

        if (AssertValue.isNotNull(key) && AssertValue.isNotNull(value)) {
            this.header.put(key, value);
        }

        return this;
    }

    public boolean isFromService() {
        return fromService;
    }

    public void setFromService(boolean fromService) {
        this.fromService = fromService;
    }

    public Page page() {
        return page;
    }
}
