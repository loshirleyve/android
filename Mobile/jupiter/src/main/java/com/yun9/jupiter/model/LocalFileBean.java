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
public class LocalFileBean implements java.io.Serializable {

    public static final String FILE_TYPE_IMAGE = "image";

    public static final String FILE_TYPE_DOC = "doc";

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
    private String dateAdded;
    private String extensionName;
    private String type;
    private String size;
    private int icoResource;
    private boolean selected = false;

    private List<LocalFileBean> childs;

    public LocalFileBean() {
    }

    public LocalFileBean(File file) {
        filePath = file.getPath();
        absolutePath = file.getAbsolutePath();
        name = FileUtil.getFileNameNoEx(file);
        extensionName = FileUtil.getExtensionName(file);
        dateAdded = DateUtil.getDateStr(file.lastModified());
        type = FILE_TYPE_DOC;
        size = FileUtil.getFileSize(file);
        id = UUID.randomUUID().toString();
        initIcoResource();

    }

    private void initIcoResource() {
        if (LocalFileBean.TYPE_PDF.equals(this.type)) {
            icoResource = R.drawable.small_ico_pdf;
        } else if (LocalFileBean.TYPE_TXT.equals(this.type)) {
            icoResource = R.drawable.small_ico_txt;
        } else if (LocalFileBean.TYPE_DOC.equals(this.type) || LocalFileBean.TYPE_DOCX.equals(this.type)) {
            icoResource = R.drawable.small_ico_doc;
        } else if (LocalFileBean.TYPE_PPT.equals(this.type) || LocalFileBean.TYPE_PPTX.equals(this.type)) {
            icoResource = R.drawable.small_ico_ppt;
        } else if (LocalFileBean.TYPE_WPS.equals(this.type)) {
            icoResource = R.drawable.small_ico_wps;
        } else if (LocalFileBean.TYPE_XLS.equals(this.type) || LocalFileBean.TYPE_XLSX.equals(this.type)) {
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

    public List<LocalFileBean> getChilds() {
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

    public void setIcoResource(int icoResource) {
        this.icoResource = icoResource;
    }

    public void setChilds(List<LocalFileBean> childs) {
        this.childs = childs;
    }

    public void putChild(LocalFileBean localFileBean) {
        if (!AssertValue.isNotNull(childs)) {
            childs = new ArrayList<>();
        }

        childs.add(localFileBean);

    }
}
