package com.yun9.jupiter.util;

/**
 * Created by huangbinglong on 7/7/15.
 */
public interface PatternPoll {

    /**
     * 纯数字，不包含小数点
     */
    String DIGIT = "^\\d+$";

    /**
     * 数字或字母，字母不区分大小写
     */
    String NUMBER_OR_CAPTION = "^[A-Za-z0-9]+$";

    /**
     * 电话号码：
     * 012-87654321、0123-87654321、0123－7654321
     */
    String PHONE = "\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)|\\d{11}";
}
