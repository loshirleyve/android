package com.yun9.jupiter.model;

import com.yun9.jupiter.R;
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
    private String name;
    private String album;
    private String dateAdded;
    private String extensionName;
    private String type;
    private String size;
    private String storageType = FILE_STORAGE_TYPE_LOCAL;
    private int icoResource = R.drawable.small_ico_unkwon;
    private boolean selected = false;

    private List<FileBean> childs;

    private SysFileBean sysFileBean;

    public FileBean() {
    }

    public FileBean(SysFileBean sysFileBean) {
        this.sysFileBean = sysFileBean;
        filePath = sysFileBean.getId();
        absolutePath = sysFileBean.getId();
        thumbnailPath = sysFileBean.getId();
        name = sysFileBean.getName();
        extensionName = sysFileBean.getType();
        dateAdded = DateUtil.getDateStr(sysFileBean.getCreatedate());
        type = FILE_STORAGE_TYPE_YUN;
        storageType = FileBean.FILE_STORAGE_TYPE_YUN;
        size = "";
        id = sysFileBean.getId();
        initIcoResource();
    }

    public FileBean(File file) {
        filePath = file.getPath();
        absolutePath = file.getAbsolutePath();
        thumbnailPath = file.getPath();
        name = FileUtil.getFileNameNoEx(file);
        extensionName = FileUtil.getExtensionName(file);
        dateAdded = DateUtil.getDateStr(file.lastModified());
        type = FILE_TYPE_DOC;
        size = FileUtil.getFileSize(file);
        storageType = FileBean.FILE_STORAGE_TYPE_LOCAL;
        id = UUID.randomUUID().toString();
        initIcoResource();

    }

    public FileBean(String id, String name,String album, String dateAdded, String path, String thumbnailsPath, String type,long size) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.dateAdded = dateAdded;
        this.absolutePath = path;
        this.filePath = "file://" + path;
        this.thumbnailPath = "file://" + thumbnailsPath;
        this.type = type;
        this.size = FileUtil.getFileSize(size);
        this.storageType = FileBean.FILE_STORAGE_TYPE_LOCAL;
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

    public void setSysFileBean(SysFileBean sysFileBean) {
        this.sysFileBean = sysFileBean;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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
}
