package com.yun9.mobile.framework.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.AssetManager;

import com.thoughtworks.xstream.XStream;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Initialization;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.conf.PropertiesFactory;
import com.yun9.mobile.framework.exception.RepositoryException;
import com.yun9.mobile.framework.resources.Repository;
import com.yun9.mobile.framework.resources.RepositoryFactory;
import com.yun9.mobile.framework.resources.RepositoryParam;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;

public class DefaultRepositoryFactory implements RepositoryFactory, Bean,
		Injection, Initialization {

	private Logger logger = Logger.getLogger(DefaultRepositoryFactory.class);

	private BeanContext beanContext;

	private PropertiesFactory propertiesFactory;

	private Map<String, Repository> repositorys = new HashMap<String, Repository>();

	@Override
	public Repository get(String name) {
		if (AssertValue.isNotNullAndNotEmpty(repositorys)) {
			return repositorys.get(name);
		} else {
			return null;
		}
	}

	@Override
	public void injection(BeanContext beanContext) {
		this.beanContext = beanContext;
		propertiesFactory = this.beanContext.get(PropertiesFactory.class);
	}

	@Override
	public Class<?> getType() {
		return RepositoryFactory.class;
	}

	private void load() throws IOException {
		AssetManager assetManager = beanContext.getApplicationContext()
				.getResources().getAssets();

		String[] repositoryConfigs = assetManager.list("repository");

		if (AssertValue.isNotNullAndNotEmpty(repositoryConfigs)) {
			for (int i = 0; i < repositoryConfigs.length; i++) {
				String repositoryFile = "repository/" + repositoryConfigs[i];
				logger.d("load repository file:" + repositoryFile);

				List<Repository> repositorys = this.parse(repositoryFile);
				if (AssertValue.isNotNullAndNotEmpty(repositorys)) {
					for (Repository repository : repositorys) {
						logger.d("put repository:" + repository.getName());
						this.repositorys.put(repository.getName(), repository);
					}
				}
			}
		}
	}

	private List<Repository> parse(String fileName) throws IOException {
		AssetManager assetManager = beanContext.getApplicationContext()
				.getResources().getAssets();

		InputStream is = null;
		try {
			is = assetManager.open(fileName);
			XStream xstream1 = new XStream();
			xstream1.alias("repositorys", List.class);
			xstream1.alias("repository", Repository.class);
			xstream1.alias("param", RepositoryParam.class);
			@SuppressWarnings("unchecked")
			List<Repository> repositorys = (List<Repository>) xstream1
					.fromXML(is);

			this.initRepository(repositorys);
			this.validateRepository(repositorys);
			return repositorys;
		} finally {
			is.close();
		}
	}

	private void validateRepository(List<Repository> repositorys) {
		if (AssertValue.isNotNullAndNotEmpty(repositorys)) {
			for (Repository repository : repositorys) {
				if (!AssertValue.isNotNullAndNotEmpty(repository.getName())) {
					throw new RepositoryException(
							"repository name is not null!");
				}

				if (!AssertValue.isNotNullAndNotEmpty(repository.getAction())) {
					throw new RepositoryException("repository "
							+ repository.getName() + " action is not null!");
				}

				if (!AssertValue.isNotNullAndNotEmpty(repository.getType())) {
					throw new RepositoryException("repository "
							+ repository.getName() + " type is not null!");
				}

				if (!AssertValue.isNotNullAndNotEmpty(repository.getBaseUrl())) {
					throw new RepositoryException("repository "
							+ repository.getName() + " base url is not null!");
				}

				if (!AssertValue.isNotNullAndNotEmpty(repository
						.getContentType())) {
					throw new RepositoryException("repository "
							+ repository.getName()
							+ " content type is not null!");
				}

				if (!AssertValue.isNotNullAndNotEmpty(repository.getToken())) {
					throw new RepositoryException("repository "
							+ repository.getName() + " token is not null!");
				}

			}
		}
	}

	private List<Repository> initRepository(List<Repository> repositorys) {
		if (AssertValue.isNotNullAndNotEmpty(repositorys)) {
			for (Repository repository : repositorys) {

				if (AssertValue.isNotNullAndNotEmpty(repository.getType())) {
					String baseUrl = this.propertiesFactory
							.getString("app.config.resource."
									+ repository.getType() + ".baseUrl");
					String token = this.propertiesFactory
							.getString("app.config.resource."
									+ repository.getType() + ".token");
					String contentType = this.propertiesFactory
							.getString("app.config.resource."
									+ repository.getType() + ".contentType");
					repository.setBaseUrl(baseUrl);
					repository.setToken(token);
					repository.setContentType(contentType);

					if (AssertValue.isNotNull(repository.getAction())) {
						repository.setAction(repository.getAction().trim());
					}

					if (AssertValue.isNotNull(repository.getBaseUrl())) {
						repository.setBaseUrl(repository.getBaseUrl().trim());
					}

					if (AssertValue.isNotNull(repository.getToken())) {
						repository.setToken(repository.getToken().trim());
					}

					if (AssertValue.isNotNull(repository.getName())) {
						repository.setName(repository.getName().trim());
					}

					if (AssertValue.isNotNull(repository.getOutput())
							&& AssertValue.isNotNullAndNotEmpty(repository
									.getOutput().getClassname())) {
						repository.getOutput().setClassname(
								(repository.getOutput().getClassname().trim()));
					}
				}
			}
		}
		return repositorys;
	}

	@Override
	public void init(BeanContext beanContext) {
		logger.d("RepositoryFactory init.");
		try {
			this.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RepositoryException(e);
		}
	}
}
