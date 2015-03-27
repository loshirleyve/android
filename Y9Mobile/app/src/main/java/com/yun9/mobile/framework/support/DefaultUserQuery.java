package com.yun9.mobile.framework.support;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.SysUserQueryInfo;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;

public class DefaultUserQuery implements SysUserQueryInfo
{
    ResourceFactory repositoryFactory;

    public DefaultUserQuery()
    {
        repositoryFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);

    }

    @Override
    public void getUserQueryInfo(String userId,AsyncHttpResponseCallback callback)
    {
        Resource sysUserQueryService = repositoryFactory.create("SysUserQueryService");
        sysUserQueryService.param("id",userId);
        sysUserQueryService.invok(callback);
    }
}
