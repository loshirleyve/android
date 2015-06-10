package com.yun9.wservice.model;

import com.yun9.jupiter.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/10.
 */
public class OrgCompositeInfoBean implements java.io.Serializable {
    private Map<String, Object> hr;
    private Map<String, Object> group;

    private User myself;

    private List<User> userMaps;

    public Map<String, Object> getHr() {
        return hr;
    }

    public void setHr(Map<String, Object> hr) {
        this.hr = hr;
    }

    public Map<String, Object> getGroup() {
        return group;
    }

    public void setGroup(Map<String, Object> group) {
        this.group = group;
    }

    public User getMyself() {
        return myself;
    }

    public void setMyself(User myself) {
        this.myself = myself;
    }

    public List<User> getUserMaps() {
        return userMaps;
    }

    public void setUserMaps(List<User> userMaps) {
        this.userMaps = userMaps;
    }
}
