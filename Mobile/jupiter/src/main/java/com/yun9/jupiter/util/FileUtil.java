package com.yun9.jupiter.util;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Leon on 15/6/16.
 */
public class FileUtil {

    public static void searchFile(File file, List<File> files) {

        if (file.isDirectory()) {
            File[] resultfiles = searchFile4Type(file);
            if (AssertValue.isNotNullAndNotEmpty(resultfiles)) {
                for (int i = 0; i < resultfiles.length; i++) {
                    files.add(resultfiles[i]);
                }
            }

            File[] childFiles = file.listFiles();
            if (AssertValue.isNotNullAndNotEmpty(childFiles)) {
                for (int i = 0; i < childFiles.length; i++) {
                    searchFile(childFiles[i], files);
                }
            }
        }
    }

    public static File[] searchFile4Type(File fileDir) {
        File[] files = fileDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                String doc = ".doc";
                String docx = ".docx";
                String pdf = ".pdf";
                // String txt = ".txt";
                String xls = ".xls";
                String xlsx = ".xlsx";
                String ppt = ".ppt";
                String pptx = ".pptx";
                String wps = ".wps";
                if (filename.endsWith(doc) ||
                        filename.endsWith(docx) ||
                        filename.endsWith(pdf) ||
                        filename.endsWith(xls) ||
                        filename.endsWith(xlsx) ||
                        filename.endsWith(ppt) ||
                        filename.endsWith(pptx) ||
                        filename.endsWith(wps)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        return files;
    }

    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getExtensionName(File file) {
        try {
            String fileName = file.getName();
            if ((fileName != null) && (fileName.length() > 0)) {
                int dot = fileName.lastIndexOf('.');
                if ((dot > -1) && (dot < (fileName.length() - 1))) {
                    return fileName.substring(dot + 1);
                }
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取不带扩展名的文件名
     *
     * @param file
     * @return
     */
    public static String getFileNameNoEx(File file) {
        try {
            String fileName = file.getName();
            if ((fileName != null) && (fileName.length() > 0)) {
                int dot = fileName.lastIndexOf('.');
                if ((dot > -1) && (dot < (fileName.length()))) {
                    return fileName.substring(0, dot);
                }
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String getFileSize(File file) {
        return getFileSize(file.length());
    }

    public static String getFileSize(long size) {
        String unit = null;
        long MB = 1024 * 1024;
        long KB = 1024;
        float fileSize = 0.0f;
        if (size >= MB) {
            fileSize = (float) size / MB;
            unit = "MB";
        } else if (size >= KB) {
            fileSize = (float) size / KB;
            unit = "KB";
        } else {
            fileSize = (float) size;
            unit = "B";
        }

        BigDecimal b = new BigDecimal(fileSize);
        fileSize = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String sizeStr = fileSize + unit;
        return sizeStr;
    }
}
