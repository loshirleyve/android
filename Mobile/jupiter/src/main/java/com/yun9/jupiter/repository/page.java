package com.yun9.jupiter.repository;

/**
 * Created by Leon on 15/7/7.
 */
public class Page implements java.io.Serializable {
    public static final String PAGE_DIR_PULL = "1";
    public static final String PAGE_DIR_PUSH = "0";

    private String size = "10";
    private String dir = PAGE_DIR_PULL;
    private String rowid;

    public String getSize() {
        return size;
    }

    public Page setSize(String size) {
        this.size = size;
        return this;
    }

    public String getDir() {
        return dir;
    }

    public Page setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getRowid() {
        return rowid;
    }

    public Page setRowid(String rowid) {
        this.rowid = rowid;
        return this;
    }
}
