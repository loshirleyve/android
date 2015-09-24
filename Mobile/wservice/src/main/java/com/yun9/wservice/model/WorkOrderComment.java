package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/25.
 */
public class WorkOrderComment implements Serializable{

    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_ADD_COMMENT = "addcomment";

    private String senderid;
    private String workorderid;
    private String commenttext;
    private String buyerinstid;
    private Long createdate;
    private String id;
    private double score;
    private List<SubComment> addcomments;

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getWorkorderid() {
        return workorderid;
    }

    public void setWorkorderid(String workorderid) {
        this.workorderid = workorderid;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    public String getBuyerinstid() {
        return buyerinstid;
    }

    public void setBuyerinstid(String buyerinstid) {
        this.buyerinstid = buyerinstid;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<SubComment> getAddcomments() {
        return addcomments;
    }

    public void setAddcomments(List<SubComment> addcomments) {
        this.addcomments = addcomments;
    }

    public static class SubComment implements Serializable{
        private String senderid;
        private String workorderid;
        private String commenttext;
        private String buyerinstid;
        private Long createdate;
        private double score;
        private String id;

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public String getWorkorderid() {
            return workorderid;
        }

        public void setWorkorderid(String workorderid) {
            this.workorderid = workorderid;
        }

        public String getCommenttext() {
            return commenttext;
        }

        public void setCommenttext(String commenttext) {
            this.commenttext = commenttext;
        }

        public String getBuyerinstid() {
            return buyerinstid;
        }

        public void setBuyerinstid(String buyerinstid) {
            this.buyerinstid = buyerinstid;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
