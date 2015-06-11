package com.yun9.wservice.model;

import com.yun9.jupiter.model.Dim;
import com.yun9.jupiter.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/10.
 */
public class OrgCompositeInfoBean implements java.io.Serializable {
    private List<Dim> dim;

    private User myself;

    private List<User> userMaps;

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

    public List<Dim> getDim() {
        return dim;
    }

    public void setDim(List<Dim> dim) {
        this.dim = dim;
    }
}
