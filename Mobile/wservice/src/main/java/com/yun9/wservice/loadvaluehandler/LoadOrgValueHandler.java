package com.yun9.wservice.loadvaluehandler;

import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.model.Org;
import com.yun9.wservice.cache.OrgCache;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class LoadOrgValueHandler implements FormUtilFactory.LoadValueHandler {

    @Override
    public void load(String id, FormUtilFactory.LoadValueCompleted callback) {
        Org org = OrgCache.getInstance().getOrg(id);
        if (org == null) {
            // 从服务器获取
            return;
        }
        callback.callback(org);
    }
}
