package com.yun9.wservice.model.wrapper;

import com.yun9.wservice.model.Attachment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class AttachmentWrapper implements Serializable{
    private List<Attachment> orderAttachments;

    public List<Attachment> getOrderAttachments() {
        return orderAttachments;
    }

    public void setOrderAttachments(List<Attachment> orderAttachments) {
        this.orderAttachments = orderAttachments;
    }
}
