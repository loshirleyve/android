package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/26.
 */
public class ProductPhase implements java.io.Serializable {

    private String name;
    private String cycle;
    private int times;
    private String description;
    private String cyclevalue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCyclevalue() {
        return cyclevalue;
    }

    public void setCyclevalue(String cyclevalue) {
        this.cyclevalue = cyclevalue;
    }
}
