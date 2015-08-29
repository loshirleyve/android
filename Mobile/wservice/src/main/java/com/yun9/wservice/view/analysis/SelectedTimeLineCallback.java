package com.yun9.wservice.view.analysis;

import com.yun9.wservice.model.DateSection;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/28.
 */
public interface SelectedTimeLineCallback extends Serializable {

    void callback(DateSection timeLine);
}
