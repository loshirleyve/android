package com.yun9.mobile.framework.session;

import android.content.Context;

import com.yun9.mobile.framework.model.AuthInfo;
import com.yun9.mobile.framework.model.Device;
import com.yun9.mobile.framework.model.Inst;

public interface SessionManager {

	public static final String FIRST = "first";

	public static final String LOGIN = "login";

	public static final String AUTHINFO = "authInfo";

	public boolean isFirst();

	public void setFirst(boolean first);

	public void setLocalString(String key, String value);

	public void setLocalBoolean(String key, boolean value);

	public String getLocalString(String key, String defaultVal);

	public Boolean getLocalBoolean(String key, boolean defaultVal);

	public void clean();

	public Device getDevice();

	public void loadDeviceInfos(Context cxt);

	public void setLocationParams();

	public void loadLocationParams();

	public void cleanLocalParams();

	public AuthInfo getAuthInfo();

	public void setAuthInfo(AuthInfo authInfo);

	public boolean isLogin();

	public void setLogin(boolean login);

	public void changeInst(Inst oldInst, Inst newInst);
}
