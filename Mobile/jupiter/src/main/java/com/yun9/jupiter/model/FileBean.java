package com.yun9.jupiter.model;

import com.yun9.jupiter.R;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Leon on 15/6/13.
 */
public class FileBean implements java.io.Serializable {

    public static final String FILE_TYPE_IMAGE = "image";

    public static final String FILE_TYPE_DOC = "doc";

    public static final String FILE_STORAGE_TYPE_YUN = "yun";
    public static final String FILE_STORAGE_TYPE_LOCAL = "local";

    public static final String FILE_LEVEL_USER = "user";

    public static final String FILE_LEVEL_SYSTEM = "system";

    public static final String FILE_STATE_SUCCESS = "success";

    public static final String FILE_STATE_FAILURE = "failure";

    public static final String FILE_STATE_NONE = "none";


    public static String TYPE_TXT = "txt";
    public static String TYPE_PDF = "pdf";
    public static String TYPE_DOC = "doc";
    public static String TYPE_DOCX = "docx";
    public static String TYPE_PPT = "ppt";
    public static String TYPE_PPTX = "pptx";
    public static String TYPE_XLS = "xls";
    public static String TYPE_XLSX = "xlsx";
    public static String TYPE_WPS = "wps";

    private String id;
    private String filePath;
    private String absolutePath;
    private String thumbnailPath;
    private String userid;
    private String instid;
    private String name;
    private String album;
    private String dateAdded;
    private String extensionName;
    private String type;
    private String size;
    private String level = FILE_LEVEL_USER;
    private String storageType = FILE_STORAGE_TYPE_LOCAL;
    private String url;
    private int icoResource = R.drawable.small_ico_unkwon;
    private boolean selected = false;
    private boolean camera;
    private String state = FILE_STATE_NONE;

    private List<FileBean> childs;

    private SysFileBean sysFileBean;

    public FileBean() {
    }

    public FileBean(SysFileBean sysFileBean) {
        this.setSysFileBean(sysFileBean);
    }

    public FileBean(File file) {
        filePath = "file://" + file.getPath();
        absolutePath = file.getAbsolutePath();
        thumbnailPath = "file://" + file.getPath();
        name = FileUtil.getFileNameNoEx(file);
        extensionName = FileUtil.getExtensionName(file);
        dateAdded = file.lastModified()+"";
        type = FILE_TYPE_DOC;
        size = FileUtil.getFileSize(file);
        storageType = FileBean.FILE_STORAGE_TYPE_LOCAL;
        id = UUID.randomUUID().toString();
        initIcoResource();

    }

    public FileBean(String id, String name, String userid, String instid, String album, String dateAdded, String path, String thumbnailsPath, String type, long size) {
        this.id = id;
        this.name = name;
        this.extensionName = FileUtil.getExtensionName(name);
        this.userid = userid;
        this.instid = instid;
        this.album = album;
        this.dateAdded = dateAdded;
        this.absolutePath = path;
        this.filePath = "file://" + path;
        this.thumbnailPath = "file://" + thumbnailsPath;
        this.type = type;
        this.size = FileUtil.getFileSize(size);
        this.storageType = FileBean.FILE_STORAGE_TYPE_LOCAL;
    }

    public void setSysFileBean(SysFileBean sysFileBean) {
        this.userid = sysFileBean.getCreateby();
        this.instid = sysFileBean.getInstid();
        this.sysFileBean = sysFileBean;
        filePath = sysFileBean.getId();
        absolutePath = sysFileBean.getId();
        thumbnailPath = sysFileBean.getId();
        name = sysFileBean.getName();
        extensionName = sysFileBean.getType();
        dateAdded = DateUtil.getDateStr(sysFileBean.getCreatedate());
        type = sysFileBean.getFiletype();
        storageType = FileBean.FILE_STORAGE_TYPE_YUN;
        size = "";
        id = sysFileBean.getId();
        CacheFile cacheFile = FileCache.getInstance().getFile(sysFileBean.getId());
        if (AssertValue.isNotNull(cacheFile)){
            this.setUrl(cacheFile.getUrl());
            this.setSize(FileUtil.getFileSize(cacheFile.getFilesize()));
        }
        initIcoResource();
    }

    private void initIcoResource() {
        if (FileBean.TYPE_PDF.equals(this.type)) {
            icoResource = R.drawable.small_ico_pdf;
        } else if (FileBean.TYPE_TXT.equals(this.type)) {
            icoResource = R.drawable.small_ico_txt;
        } else if (FileBean.TYPE_DOC.equals(this.type) || FileBean.TYPE_DOCX.equals(this.type)) {
            icoResource = R.drawable.small_ico_doc;
        } else if (FileBean.TYPE_PPT.equals(this.type) || FileBean.TYPE_PPTX.equals(this.type)) {
            icoResource = R.drawable.small_ico_ppt;
        } else if (FileBean.TYPE_WPS.equals(this.type)) {
            icoResource = R.drawable.small_ico_wps;
        } else if (FileBean.TYPE_XLS.equals(this.type) || FileBean.TYPE_XLSX.equals(this.type)) {
            icoResource = R.drawable.small_ico_xls;
        } else {
            icoResource = R.drawable.small_ico_unkwon;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<FileBean> getChilds() {
        return childs;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getIcoResource() {
        return icoResource;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public void setIcoResource(int icoResource) {
        this.icoResource = icoResource;
    }

    public SysFileBean getSysFileBean() {
        return sysFileBean;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setChilds(List<FileBean> childs) {
        this.childs = childs;
    }


    public void putChild(FileBean fileBean) {
        if (!AssertValue.isNotNull(childs)) {
            childs = new ArrayList<>();
        }

        childs.add(fileBean);

    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
