package com.yun9.jupiter.manager.support;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.bean.annotation.BeanInject;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.model.AuthInfo;
import com.yun9.jupiter.model.Device;
import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.manager.SessionManagerException;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.PublicHelp;


public class DefaultSessionManager implements SessionManager, Bean,
        Initialization {

    @BeanInject
	private PropertiesManager propertiesManager;

	private AuthInfo authInfo;

	private Device device;

	private SharedPreferences sp;

	private static final Logger logger = Logger
			.getLogger(DefaultSessionManager.class);

	@Override
	public Class<?> getType() {
		return SessionManager.class;
	}

	@Override
	public boolean isLogin() {
		return this.getLocalBoolean(LOGIN, false);
	}

	@Override
	public void setLogin(boolean login) {
		this.setLocalBoolean(LOGIN, login);
	}

	public void clean() {
		authInfo = null;
	}

	@Override
	public void init(BeanManager beanManager) {
		logger.d("session factory init.");
		String name = propertiesManager.getString("app.config.session.sp.name",
				"Yun9MobileApp");
		this.sp = beanManager.getApplicationContext().getSharedPreferences(
				name, Context.MODE_PRIVATE);

		// 收集设备信息
		this.loadDeviceInfos(beanManager.getApplicationContext());

		this.loadLocationParams();
	}

	public boolean isFirst() {
		return this.getLocalBoolean(FIRST, false);
	}

	public void setFirst(boolean first) {
		this.setLocalBoolean(FIRST, first);
	}

	public void setLocalString(String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void setLocalBoolean(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	@Override
	public String getLocalString(String key, String defaultVal) {
		return this.sp.getString(key, defaultVal);
	}

	@Override
	public Boolean getLocalBoolean(String key, boolean defaultVal) {
		return this.sp.getBoolean(key, defaultVal);
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public void setLocationParams() {
		if (AssertValue.isNotNull(this.authInfo)) {
			String authJson = JsonUtil.beanToJson(this.getAuthInfo());
			logger.d("auth info json set to location:" + authJson);
			this.setLocalString(SessionManager.AUTHINFO, authJson);
		}
	}

	public void loadLocationParams() {
		String authJson = this.getLocalString(SessionManager.AUTHINFO, "");
		logger.d("auth info json form location:" + authJson);

		if (AssertValue.isNotNullAndNotEmpty(authJson)) {
			this.authInfo = JsonUtil.jsonToBean(authJson, AuthInfo.class);
		}
	}

	public void cleanLocalParams() {
		this.setLocalString(SessionManager.AUTHINFO, null);
	}

	@Override
	public void loadDeviceInfos(Context cxt) {
		// 收集设备信息
		Map<String, String> deviceInfo = PublicHelp.collectDeviceInfo(cxt);

		this.device = new Device();

		device.setId(deviceInfo.get("ID"));
		device.setDeviceid(deviceInfo.get("TMDeviceid"));
		device.setModel(deviceInfo.get("MODEL"));
		device.setBoard(deviceInfo.get("BOARD"));
		device.setBrand(deviceInfo.get("BRAND"));
		device.setFingerprint(deviceInfo.get("FINGERPRINT"));
		device.setSerial(deviceInfo.get("SERIAL"));
		device.setOthers(deviceInfo);

	}

	@Override
	public void changeInst(Inst oldInst, Inst newInst) {

		// 新旧机构相同无需切换
		if (oldInst.getId().equals(newInst.getId())) {
			return;
		}

		// 原机构与当前系统回话记录的机构不相同
		if (!oldInst.getId().equals(authInfo.getInstinfo().getId())) {
			throw new SessionManagerException("切换机构错误，原机构与回话记录机构不相同。");
		}

		// 检查新机构是否存在于用户的绑定机构中
		boolean exist = false;

		for (Inst inst : authInfo.getAllinsts()) {
			if (inst.getId().equals(newInst.getId())) {
				exist = true;
			}
		}

		if (exist) {
			authInfo.setInstinfo(newInst);
		}

	}

}
