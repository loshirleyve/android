package com.yun9.mobile.framework.support;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.HttpFactory;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.AssertValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2014/11/4.
 */
public class DefaultCheckOnWorkAttendFactory implements CheckOnWorkAttendFactory,Bean, Injection {
    private ResourceFactory resourceFactory;
    private HttpFactory httpFactory;

    @Override
    public void CheckIn(CheckInInParm InputParm, AsyncHttpResponseCallback callback) {

        if(!AssertValue.isNotNull(InputParm) || !AssertValue.isNotNull(callback))
        {
            throw new IllegalArgumentException("输入参数为空");
        }

        Map<String,Object> params = new HashMap<String, Object>();
        Map<String, Object> header = new HashMap<String, Object>();
        header.put(CheckOnWorkAttendFactory.DATAKEY_INSTIID, InputParm.getInstid());
        params.put(CheckOnWorkAttendFactory.DATAKEY_USERID,InputParm.getUserid());
        params.put(CheckOnWorkAttendFactory.DATAKEY_WORKDATE, InputParm.getWorkdate());
        params.put(CheckOnWorkAttendFactory.DATAKEY_SHIFTID,InputParm.getShiftid());
        params.put(CheckOnWorkAttendFactory.DATAKEY_CHECKDATETIME, InputParm.getCheckdatetime());
        params.put(CheckOnWorkAttendFactory.DATAKEY_CHECKLOCATIONX,InputParm.getChecklocationx());
        params.put(CheckOnWorkAttendFactory.DATAKEY_CHECKLOCATIONY, InputParm.getChecklocationy());
        params.put(CheckOnWorkAttendFactory.DATAKEY_CHECKLOCATIONLABEL,InputParm.getChecklocationlabel());
        params.put(CheckOnWorkAttendFactory.DATAKEY_CREATEBY, InputParm.getCreateby());

        doCheckIn(params, header,callback);
    }



    private void doCheckIn(Map<String,Object> params,Map<String,Object> header,AsyncHttpResponseCallback callback){
        Resource service = resourceFactory.create("BizHrCheckService");
        service.setHeader(header);
        service.setParams(params);
        service.invok(callback);
    }

    @Override
    public void getSchedulingWork(ScheDulingWorkInParm inputParm, AsyncHttpResponseCallback callback) {
        if(!AssertValue.isNotNull(inputParm) || !AssertValue.isNotNull(callback))
        {
            throw new IllegalArgumentException("输入参数为空");
        }

        
        Map<String, Object> header = new HashMap<String, Object>();
        Map<String,Object> params = new HashMap<String, Object>();
        
        header.put(CheckOnWorkAttendFactory.SW_DATAKEY_INSTIID, inputParm.getInstid());
        params.put(CheckOnWorkAttendFactory.SW_DATAKEY_USERID, inputParm.getUserid());
        params.put(CheckOnWorkAttendFactory.SW_DATAKEY_WORKDATE, inputParm.getWorkdate());
        
        doGetSchedulingWork(params, header, callback);
    }

    private void doGetSchedulingWork(Map<String,Object> params,Map<String,Object> header,AsyncHttpResponseCallback callback){
        Resource service = resourceFactory.create("BizHrFindMySchedulingService");
        service.setHeader(header);
        service.setParams(params);
        service.invok(callback);
    }

    @Override
    public Class<?> getType() {
        return CheckOnWorkAttendFactory.class;
    }

    @Override
    public void injection(BeanContext beanContext) {
        resourceFactory = beanContext.get(ResourceFactory.class);

        httpFactory = beanContext.get(HttpFactory.class);
    }
}
