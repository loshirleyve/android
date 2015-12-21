package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class PayRegisterCollectAnalysis implements Serializable {

    private String id;
    private String page;
    private String paymode;
    private List<PayRegisterCollectAnalysisUserDtos> payRegisterCollectAnalysisUserDtos;

    public String getId() {
        return id;
    }

    public PayRegisterCollectAnalysis setId(String id) {
        this.id = id;
        return this;
    }

    public String getPage() {
        return page;
    }

    public PayRegisterCollectAnalysis setPage(String page) {
        this.page = page;
        return this;
    }

    public String getPaymode() {
        return paymode;
    }

    public PayRegisterCollectAnalysis setPaymode(String paymode) {
        this.paymode = paymode;
        return this;
    }

    public List<PayRegisterCollectAnalysisUserDtos> getPayRegisterCollectAnalysisUserDtos() {
        return payRegisterCollectAnalysisUserDtos;
    }

    public PayRegisterCollectAnalysis setPayRegisterCollectAnalysisUserDtos(List<PayRegisterCollectAnalysisUserDtos> payRegisterCollectAnalysisUserDtos) {
        this.payRegisterCollectAnalysisUserDtos = payRegisterCollectAnalysisUserDtos;
        return this;
    }
}
