package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/11.
 */
public class Dim implements java.io.Serializable {
    private String id;
    private int num;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
