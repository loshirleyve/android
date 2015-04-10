package com.yun9.wservice.repository.support;

import com.yun9.wservice.bean.Bean;
import com.yun9.wservice.bean.BeanContext;
import com.yun9.wservice.bean.Initialization;
import com.yun9.wservice.bean.Injection;
import com.yun9.wservice.http.AsyncHttpResponseCallback;
import com.yun9.wservice.http.HttpFactory;
import com.yun9.wservice.repository.Repository;
import com.yun9.wservice.repository.RepositoryFactory;
import com.yun9.wservice.repository.Resource;
import com.yun9.wservice.repository.ResourceFactory;
import com.yun9.wservice.util.Logger;

public class DefaultResourceFactory implements ResourceFactory, Injection,
        Initialization, Bean {

	private Logger logger = Logger.getLogger(DefaultResourceFactory.class);

	private BeanContext beanContext;

	private HttpFactory httpFactory;

	private RepositoryFactory repositoryFactory;

	@Override
	public Class<?> getType() {
		return ResourceFactory.class;
	}

	@Override
	public void injection(BeanContext beanContext) {
		this.beanContext = beanContext;
		repositoryFactory = this.beanContext.get(RepositoryFactory.class);
		httpFactory = this.beanContext.get(HttpFactory.class);
	}

	@Override
	public void init(BeanContext beanContext) {
		logger.d("resource init.");
	}

	@Override
	public Resource create(String name) {
		Repository repository = repositoryFactory.get(name);
		DefaultResource resource = new DefaultResource(repository, this);

		return resource;
	}

	@Override
	public void invok(Resource resource, AsyncHttpResponseCallback callback) {
		httpFactory.post(resource, callback);
	}

	@Override
	public void invokSync(Resource resource, AsyncHttpResponseCallback callback) {
		httpFactory.postSync(resource, callback);
	}

}
