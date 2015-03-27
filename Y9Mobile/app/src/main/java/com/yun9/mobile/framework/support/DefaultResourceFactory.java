package com.yun9.mobile.framework.support;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Initialization;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.HttpFactory;
import com.yun9.mobile.framework.resources.Repository;
import com.yun9.mobile.framework.resources.RepositoryFactory;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.Logger;

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

}
