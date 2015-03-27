package com.yun9.mobile.camera.util;

import java.io.Serializable;

public class PicerMap implements Serializable
{
    private int from;
    private String url;

    public PicerMap()
    {
    }

    public PicerMap(int from, String url)
    {

        this.from = from;
        this.url = url;
    }

    public int getFrom()
    {

        return from;
    }

    public void setFrom(int from)
    {
        this.from = from;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
