package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/11.
 */
public class FileBean {

    public static final String FILE_TYPE_IMAGE = "image";

    public static final String FILE_TYPE_DOC = "doc";

    private String id;

    private String name;

    private String url;

    private String type;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
