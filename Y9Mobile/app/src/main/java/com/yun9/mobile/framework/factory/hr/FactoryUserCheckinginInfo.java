package com.yun9.mobile.framework.factory.hr;

import com.yun9.mobile.framework.impls.hr.implUserCheckinginInfo;
import com.yun9.mobile.framework.interfaces.hr.UserCheckinginInfo;


/**
 * 
 * 获取用户考勤信息工厂
 * @author lhk
 *
 */
public class FactoryUserCheckinginInfo {
	public static UserCheckinginInfo create(){
		return new implUserCheckinginInfo();
	}
}
