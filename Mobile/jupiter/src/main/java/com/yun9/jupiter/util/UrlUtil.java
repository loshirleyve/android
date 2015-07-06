package com.yun9.jupiter.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by rxy on 15/7/6.
 */
public class UrlUtil {

    public static String encode(String url) {
        if (url == null){
            return "";
        }
        try {
            return URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String decode(String url) {
        if (url == null){
            return "";
        }
        try {
            return URLDecoder.decode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
