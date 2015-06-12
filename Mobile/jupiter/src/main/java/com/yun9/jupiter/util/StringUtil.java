package com.yun9.jupiter.util;

/**
 * Created by Leon on 15/6/12.
 */
public class StringUtil {
    public static boolean contains(String source, String target, boolean pinyin) {
        if (AssertValue.isNotNullAndNotEmpty(source) && AssertValue.isNotNullAndNotEmpty(target)) {

            if (source.contains(target)) {
                return true;
            } else {

                if (pinyin) {
                    String pinyinStr = PinYinMaUtil.stringToPinyin(source, true);

                    if (AssertValue.isNotNullAndNotEmpty(pinyinStr) && pinyinStr.contains(target)) {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    public static void main(String arg[]) {
        System.out.println(StringUtil.contains("你好，我是你的朋友", "nhwsndpy", true));

    }
}
