package com.yun9.mobile.framework.personelservice;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

/**
 * Created by User on 2014/11/4.
 *
 * 考勤相关接口
 */
public interface CheckOnWorkAttendFactory {

    // 打卡 DATA KEY
    public static final String DATAKEY_INSTIID = "instid";
    public static final String DATAKEY_USERID = "userid";
    public static final String DATAKEY_WORKDATE = "workdate";
    public static final String DATAKEY_SHIFTID= "shiftid";
    public static final String DATAKEY_CHECKDATETIME = "checkdatetime";
    public static final String DATAKEY_CHECKLOCATIONX = "checklocationx";
    public static final String DATAKEY_CHECKLOCATIONY = "checklocationy";
    public static final String DATAKEY_CHECKLOCATIONLABEL = "checklocationlabel";
    public static final String DATAKEY_CREATEBY = "createby";
    /**
     * 打卡
     */
    public void CheckIn(CheckInInParm InputParm, AsyncHttpResponseCallback callback);

    public class CheckInInParm {

        /**
         * 机构id；必须的；当前用户的机构id
         * */
        private String instid;

        /**
         * 用户id
         * */
        private String userid;
        /**
         * 工作日期；必须的；要获取的班次的日期，必须是时间戳
         * */
        private long workdate;

        /**
         * 班次id，通过“获取用户考情班次服务”获取的班次
         * */
        private String shiftid;

        /**
         * 打卡时间，时间戳
         * */
        private long checkdatetime;

        /**
         * 打卡时的经度
         * */
        private String checklocationx;

        /**
         * 打卡时的纬度
         * */
        private String checklocationy;


        /**
         *打卡时的地理位置
         * */
        private String checklocationlabel;

        /**
         * 操作人，可以使用当前用户id
         * */
        private String createby;

        public CheckInInParm(String instid, String userid, long workdate, String shiftid, Long checkdatetime, String checklocationx, String checklocationy, String checklocationlabel, String createby) {
            this.instid = instid;
            this.userid = userid;
            this.workdate = workdate;
            this.shiftid = shiftid;
            this.checkdatetime = checkdatetime;
            this.checklocationx = checklocationx;
            this.checklocationy = checklocationy;
            this.checklocationlabel = checklocationlabel;
            this.createby = createby;
        }

        public String getInstid() {
            return instid;
        }

        public void setInstid(String instid) {
            this.instid = instid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public long getWorkdate() {
            return workdate;
        }

        public void setWorkdate(long workdate) {
            this.workdate = workdate;
        }

        public String getShiftid() {
            return shiftid;
        }

        public void setShiftid(String shiftid) {
            this.shiftid = shiftid;
        }

        public Long getCheckdatetime() {
            return checkdatetime;
        }

        public void setCheckdatetime(Long checkdatetime) {
            this.checkdatetime = checkdatetime;
        }

        public String getChecklocationx() {
            return checklocationx;
        }

        public void setChecklocationx(String checklocationx) {
            this.checklocationx = checklocationx;
        }

        public String getChecklocationy() {
            return checklocationy;
        }

        public void setChecklocationy(String checklocationy) {
            this.checklocationy = checklocationy;
        }

        public String getChecklocationlabel() {
            return checklocationlabel;
        }

        public void setChecklocationlabel(String checklocationlabel) {
            this.checklocationlabel = checklocationlabel;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }
    }

    
    // 班次 DATA KEY
    public static final String SW_DATAKEY_INSTIID = "instid";
    public static final String SW_DATAKEY_USERID = "userid";
    public static final String SW_DATAKEY_WORKDATE = "workdate";
  

    /**
    * 获取班次
    * */
    public void getSchedulingWork(ScheDulingWorkInParm inputParm, AsyncHttpResponseCallback callback);



    public class ScheDulingWorkInParm{

        /**
         * 机构id；必须的；当前用户的机构id
         * */
        private String instid;

        /**
         * 用户id；必须的；当前用户的id
         * */
        private String userid;

        /**
         * 工作日期；必须的；要获取的班次的日期，必须是时间戳
         * */
        private long workdate;

        public ScheDulingWorkInParm(long workdate, String userid, String instid) {
            this.workdate = workdate;
            this.userid = userid;
            this.instid = instid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public long getWorkdate() {
            return workdate;
        }

        public void setWorkdate(long workdate) {
            this.workdate = workdate;
        }

        public String getInstid() {
            return instid;
        }

        public void setInstid(String instid) {
            this.instid = instid;
        }
    }
}
