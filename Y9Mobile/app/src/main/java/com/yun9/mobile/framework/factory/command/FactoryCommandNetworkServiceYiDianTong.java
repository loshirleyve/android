package com.yun9.mobile.framework.factory.command;

import com.yun9.mobile.framework.impls.command.ImplCommandNetworkSrvice;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;

public class FactoryCommandNetworkServiceYiDianTong extends FactoryCommandNetworkService {
	@Override
	public CommandNetworkService creatCommandNetworkService() {
		return new ImplCommandNetworkSrvice();
	}
}
