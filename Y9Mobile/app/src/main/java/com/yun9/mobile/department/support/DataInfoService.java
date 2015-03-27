package com.yun9.mobile.department.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yun9.mobile.department.callback.AsyncHttpInstCallback;
import com.yun9.mobile.department.callback.AsyncHttpOrgCallback;
import com.yun9.mobile.department.callback.AsyncHttpOrgCardCallback;
import com.yun9.mobile.department.callback.AsyncHttpPasswordCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserBeanCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserContactCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserNaviCallback;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Inst;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.OrgModel;
import com.yun9.mobile.framework.model.SysMdNavi;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.model.UserContact;
import com.yun9.mobile.framework.model.UserPassword;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： DataInfoService 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-11-5下午5:46:54 修改人：ruanxiaoyu 修改时间：2014-11-5下午5:46:54 修改备注：
 * 
 * @version
 * 
 */
public class DataInfoService {
	
	public List<Org> transfromOrgList(Org org) {
		if (AssertValue.isNotNull(org)) {
			List<Org> orgs = new ArrayList<Org>();
			orgs.add(org);
			transfrom(orgs, org);
			return orgs;
		}
		return null;
	}

	public void transfrom(List<Org> orgs, Org org) {
		if (AssertValue.isNotNullAndNotEmpty(org.getChildren())) {
			List<Org> orglist = org.getChildren();
			for (Org org1 : orglist) {
				orgs.add(org1);
				transfrom(orgs, org1);
			}
		}
	}

	public List<OrgModel> transfromOrgModel(List<Org> orgs) {
		List<OrgModel> orgModels = new ArrayList<OrgModel>();
		if (AssertValue.isNotNullAndNotEmpty(orgs))
		{
			for (Org org : orgs) {
				try{
					OrgModel orgmodel = new OrgModel(Long.parseLong(org.getId().trim()),
							Long.valueOf(org.getParentid().trim()), org.getName());
					orgModels.add(orgmodel);
				}
				catch (Exception e) {
					System.err.println(e);
				}
				
			}
		}
		return orgModels;
	}

	public Map<String, Org> transfromOrg(List<Org> orgs) {
		Map<String, Org> orgMaps = new HashMap<String, Org>();
		if (AssertValue.isNotNullAndNotEmpty(orgs))
		{
			for (Org org : orgs) {
				orgMaps.put(org.getId(), org);
			}
		}
		return orgMaps;
	}

	// 获取当前机构
	public Inst getUserInst() {
		SessionManager sessionManager = BeanConfig.getInstance()
				.getBeanContext().get(SessionManager.class);
		Inst inst = sessionManager.getAuthInfo().getInstinfo();
		return inst;
	}

	// 获取当前用戶
	public User getUser() {
		SessionManager sessionManager = BeanConfig.getInstance()
				.getBeanContext().get(SessionManager.class);
		User user = sessionManager.getAuthInfo().getUserinfo();
		return user;
	}

	// 获取当前机构下面的用户信息 联系方式 部门信息的回调函数
	public void getUserBeanCallBack(Map<String, String> params,
			final AsyncHttpUserBeanCallback callback) {
		getAllUser(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<UserBean> users = (List<UserBean>) response.getPayload();
				callback.handler(users);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 获取当前组织结构下面的用户信息 联系方式 部门信息 的回调函数
	public void getOrgUserBeanCallBack(Map<String, String> params,
			final AsyncHttpUserBeanCallback callback) {
		getOrgUserBean(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<UserBean> users = (List<UserBean>) response.getPayload();
				callback.handler(users);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}
	
	// 获取当前组织结构下面的用户信息 
	public void getOrgUserCallBack(Map<String, String> params,
			final AsyncHttpUserCallback callback) {
		getOrgUser(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<User> users = (List<User>) response.getPayload();
				callback.handler(users);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}

	// 获取当前机构下面的的所有组织 的回调函数
	public void getAllOrgCallBack(Map<String, String> params,
			final AsyncHttpOrgCallback callback) {
		getAllOrg(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				Org sysInstDimOrgs = (Org) response.getPayload();
				if (AssertValue.isNotNull(sysInstDimOrgs))
					callback.handler(transfromOrgList(sysInstDimOrgs));
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 获取当前机构下面的parentid为0的组织 的回调函数
	public void getOrgCallBack(Map<String, String> params,
			final AsyncHttpOrgCallback callback) {
		getOrg(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				Org sysInstDimOrgs = (Org) response.getPayload();
				List<Org> orgs = new ArrayList<Org>();
				if (AssertValue.isNotNull(sysInstDimOrgs))
					orgs.add(sysInstDimOrgs);
				callback.handler(orgs);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 获取当前部门下面的子部门 的回调函数
	public void getSubOrgCallBack(Map<String, Org> params,
			final AsyncHttpOrgCallback callback) {
		getAllSubOrg(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<Org> sysInstDimOrgs = (List<Org>) response.getPayload();
				callback.handler(sysInstDimOrgs);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}

	// 获取用户的联系方式 的回调函数
	public void getUserContactCallBack(Map<String, String> params,
			final AsyncHttpUserContactCallback callback) {
		getUserContact(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<UserContact> userContact = (List<UserContact>) response
						.getPayload();
				callback.handler(userContact);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}

	// 获取用户id查询出他的部门和职位 的回调函数
	public void getUserOrgPositionCallBack(Map<String, String> params,
			final AsyncHttpOrgCallback callback) {
		getUserOrgPosition(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<Org> orgs = (List<Org>) response.getPayload();
				callback.handler(orgs);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}

	// 获取当前机构下面的用户信息 联系方式 部门信息
	public void getAllUser(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource getUserInfoByInstServiceResource = resourceFactory
				.create("GetUserInfoByInstService");

		String instid = getUserInst().getId();
		getUserInfoByInstServiceResource.param("inst", instid);
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				getUserInfoByInstServiceResource.param(key, params.get(key));
			}
		}
		try {
			getUserInfoByInstServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取当前组织结构下面的用户信息 联系方式 部门信息
	public void getOrgUserBean(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource getUserInfoByOrgServiceResource = resourceFactory
				.create("GetUserInfoByOrgService");
		getUserInfoByOrgServiceResource.param("orgid", params.get("orgid"));
		getUserInfoByOrgServiceResource.param("name", params.get("name"));
		try {
			getUserInfoByOrgServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 获取当前组织结构下面的用户信息 联系方式 部门信息
	public void getOrgUser(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysUserByOrgServiceResource = resourceFactory
				.create("SysUserByOrgService");
		sysUserByOrgServiceResource.param("orgid", params.get("orgid"));
		try {
			sysUserByOrgServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 获取当前机构下面的所有部门
	public void getAllOrg(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource queryOrgTreeByInstAndTypeServiceResource = resourceFactory
				.create("QueryOrgTreeByInstAndTypeService");
		queryOrgTreeByInstAndTypeServiceResource.param("type", "dept");
		queryOrgTreeByInstAndTypeServiceResource.param("orgtype", "org");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				queryOrgTreeByInstAndTypeServiceResource.param(key,
						params.get(key));
			}
		}
		try {
			queryOrgTreeByInstAndTypeServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取当前机构下面的parentid为0的组织
	public void getOrg(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysInstDimOrgQueryByInstAndTypeServiceResource = resourceFactory
				.create("SysInstDimOrgQueryByInstAndTypeService");
		sysInstDimOrgQueryByInstAndTypeServiceResource.param("type", "dept");
		sysInstDimOrgQueryByInstAndTypeServiceResource.param("orgtype", "org");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				sysInstDimOrgQueryByInstAndTypeServiceResource.param(key,
						params.get(key));
			}
		}
		try {
			sysInstDimOrgQueryByInstAndTypeServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取当前部门下面的所有子部门
	public void getAllSubOrg(Map<String, Org> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysInstDimOrgsQueryByParentIdServiceResource = resourceFactory
				.create("SysInstDimOrgsQueryByParentIdService");
		sysInstDimOrgsQueryByParentIdServiceResource.param("parentid", params
				.get("org").getId());
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				sysInstDimOrgsQueryByParentIdServiceResource.param(key,
						params.get(key));
			}
		}
		try {
			sysInstDimOrgsQueryByParentIdServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取用户的联系方式
	public void getUserContact(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysUserContactQueryServiceResource = resourceFactory
				.create("SysUserContactQueryService");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				sysUserContactQueryServiceResource.param(key, params.get(key));
			}
		}
		try {
			sysUserContactQueryServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据userid查询出他的部门和职位
	public void getUserOrgPosition(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysInstDimOrgQueryByCardServiceResource = resourceFactory
				.create("SysInstDimOrgQueryByCardService");
		sysInstDimOrgQueryByCardServiceResource.param("relationvalue",
				params.get("userid"));
		sysInstDimOrgQueryByCardServiceResource.param("type",
				params.get("type"));
		try {
			sysInstDimOrgQueryByCardServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改密码 的回调函数
	public void updatePasswordCallback(Map<String, String> params,
			final AsyncHttpPasswordCallback callback) {
		updatePassword(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				UserPassword userPassword = (UserPassword) response
						.getPayload();
				callback.handler(userPassword);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}

	// 根据userid修改密码
	public void updatePassword(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);

		Resource sysUserPasswdUpdatePasswdServiceResource = resourceFactory
				.create("SysUserPasswdUpdatePasswdService");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				sysUserPasswdUpdatePasswdServiceResource.param(key,
						params.get(key));
			}
		}
		try {
			sysUserPasswdUpdatePasswdServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 比较两个map是否相等，在listview点击时
	public boolean equalsMap(Map<String, Org> map1, Map<String, Org> map2) {
		for (String key : map1.keySet()) {
			if (!AssertValue.isNotNull(map2.get(key)))
				return false;
		}
		return true;
	}
	
	
	// 根据编号查询出机构列表 的回调函数
	public void getInstListCallback(Map<String, Object> params,
			final AsyncHttpInstCallback callback) {
		getInstList(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<Inst> insts = (List<Inst>) response
						.getPayload();
				callback.handler(insts);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}
	
	// 根据编号查询出机构列表
	public void getInstList(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource sysInstQueryByUserServiceResource = resourceFactory
				.create("SysInstQueryByUserService");
		sysInstQueryByUserServiceResource.param("userno", params.get("userno").toString());
		try {
			sysInstQueryByUserServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 获取用户导航 的回调函数
	public void getUserNaviCallback(final AsyncHttpUserNaviCallback callback) {
		getUserNavi(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<SysMdNavi> navis = (List<SysMdNavi>) response
						.getPayload();
				callback.handler(navis);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		});
	}
		
	// 获取用户导航
	public void getUserNavi(AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource getNaviByUseridByInstidByDeviceResource = resourceFactory
				.create("GetNaviByUseridByInstidByDevice");
		getNaviByUseridByInstidByDeviceResource.param("userid", this.getUser().getId());
		getNaviByUseridByInstidByDeviceResource.param("instid", this.getUserInst().getId());
		getNaviByUseridByInstidByDeviceResource.param("device", "android");
		try {
			getNaviByUseridByInstidByDeviceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//保存机构纬度卡 的callback
	public void saveOrgCardCallback(Map<String,Object> params,
			final AsyncHttpOrgCardCallback callback) {
		saveOrgCard(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				String result= (String) response
						.getPayload();
				callback.handler(result);
			}

			@Override
			public void onFailure(Response response) {
				String result= (String) response
						.getPayload();
				callback.handler(result);
			}
		});
	}
	
	
	//保存机构纬度卡
	public void saveOrgCard(Map<String,Object> params,AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		List<String> createby=new ArrayList<String>();
		createby.add(getUser().getId());
		params.put("createby",createby);
		Resource saveDimOrgCardServiceResource = resourceFactory
				.create("SaveDimOrgCardService");
		saveDimOrgCardServiceResource.header("","");
		saveDimOrgCardServiceResource.setParams(params);
		try {
			saveDimOrgCardServiceResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
