package com.yun9.jupiter.repository.support;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.bean.Injection;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.HttpFactory;
import com.yun9.jupiter.repository.Repository;
import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.Logger;

public class DefaultResourceFactory implements ResourceFactory, Injection,
        Initialization, Bean {

	private Logger logger = Logger.getLogger(DefaultResourceFactory.class);

	private BeanManager beanManager;

	private HttpFactory httpFactory;

	private RepositoryManager repositoryManager;

	@Override
	public Class<?> getType() {
		return ResourceFactory.class;
	}

	@Override
	public void injection(BeanManager beanManager) {
		this.beanManager = beanManager;
		repositoryManager = this.beanManager.get(RepositoryManager.class);
		httpFactory = this.beanManager.get(HttpFactory.class);
	}

	@Override
	public void init(BeanManager beanManager) {
		logger.d("resource init.");
	}

	@Override
	public Resource create(String name) {
		Repository repository = repositoryManager.get(name);
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
