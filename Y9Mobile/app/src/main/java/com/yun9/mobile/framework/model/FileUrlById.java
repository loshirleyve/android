package com.yun9.mobile.framework.model;

import java.net.URL;

public class FileUrlById
{
    private FileInfo fileinfo;
    private URL url;

    public FileUrlById()
    {
    }

    public FileUrlById(FileInfo fileInfo, URL url)
    {
        this.fileinfo = fileInfo;
        this.url = url;
    }

    public FileInfo getFileInfo()
    {
        return fileinfo;
    }

    public void setFileInfo(FileInfo fileinfo)
    {
        this.fileinfo = fileinfo;
    }

    public URL getUrl()
    {
        return url;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }
}
