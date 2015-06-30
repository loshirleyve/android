package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/24.
 */
public class Payinfo implements Serializable{

    private String source;
    private String sourceValue;
    private String instId;
    private String title;
    private String subtitle;
    private double payableAmount;
    private double balance;
    private double factpayAmount;
    private double surplusAmount;
    private List<PaymodeCategory> paymodeCategorys;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFactpayAmount() {
        return factpayAmount;
    }

    public void setFactpayAmount(double factpayAmount) {
        this.factpayAmount = factpayAmount;
    }

    public double getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(double surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public List<PaymodeCategory> getPaymodeCategorys() {
        return paymodeCategorys;
    }

    public void setPaymodeCategorys(List<PaymodeCategory> paymodeCategorys) {
        this.paymodeCategorys = paymodeCategorys;
    }

    public static class PaymodeCategory implements Serializable {
        public String id;
        public String name;
        public int sort;
        public String imgid;
        public String descr;
        private List<PaymodeInfo> paymodeInfos;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getImgid() {
            return imgid;
        }

        public void setImgid(String imgid) {
            this.imgid = imgid;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public List<PaymodeInfo> getPaymodeInfos() {
            return paymodeInfos;
        }

        public void setPaymodeInfos(List<PaymodeInfo> paymodeInfos) {
            this.paymodeInfos = paymodeInfos;
        }
    }

    public static class PaymodeInfo implements Serializable {
        private String paymodeId;
        private String paymodeNo;
        private String paymodeName;
        private String paymodeImgid;
        private String paymodeTips;
        private String descr;
        private double useAmount;
        private List<BizFinanceAccount> bizFinanceAccounts;

        public String getPaymodeId() {
            return paymodeId;
        }

        public void setPaymodeId(String paymodeId) {
            this.paymodeId = paymodeId;
        }

        public String getPaymodeNo() {
            return paymodeNo;
        }

        public void setPaymodeNo(String paymodeNo) {
            this.paymodeNo = paymodeNo;
        }

        public String getPaymodeName() {
            return paymodeName;
        }

        public void setPaymodeName(String paymodeName) {
            this.paymodeName = paymodeName;
        }

        public String getPaymodeImgid() {
            return paymodeImgid;
        }

        public void setPaymodeImgid(String paymodeImgid) {
            this.paymodeImgid = paymodeImgid;
        }

        public String getPaymodeTips() {
            return paymodeTips;
        }

        public void setPaymodeTips(String paymodeTips) {
            this.paymodeTips = paymodeTips;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public double getUseAmount() {
            return useAmount;
        }

        public void setUseAmount(double useAmount) {
            this.useAmount = useAmount;
        }

        public List<BizFinanceAccount> getBizFinanceAccounts() {
            return bizFinanceAccounts;
        }

        public void setBizFinanceAccounts(List<BizFinanceAccount> bizFinanceAccounts) {
            this.bizFinanceAccounts = bizFinanceAccounts;
        }
    }

    public static class BizFinanceAccount implements Serializable{
        public static final String TYPE_BALANCE = "balance";
        public static final String TYPE_TICKET = "ticket";
        private String createby;
        private String updateby;
        private Long createdate;
        private Long updatedate;
        private int disabled;
        private String remark;
        private String id;
        private String cardno;
        private String type;
        private String owner;
        private String ticketid;
        private double amount;
        private Long expirydate;

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public String getUpdateby() {
            return updateby;
        }

        public void setUpdateby(String updateby) {
            this.updateby = updateby;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }

        public Long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(Long updatedate) {
            this.updatedate = updatedate;
        }

        public int getDisabled() {
            return disabled;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getTicketid() {
            return ticketid;
        }

        public void setTicketid(String ticketid) {
            this.ticketid = ticketid;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Long getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(Long expirydate) {
            this.expirydate = expirydate;
        }
    }
}
