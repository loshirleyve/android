package com.yun9.wservice.manager.support;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.view.common.SimpleBrowserCommand;
import com.yun9.wservice.widget.Y9UrlSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangbinglong on 16/1/15.
 */
public class TextViewHelper {

    private static Pattern URL_PATTERN = Pattern.compile("(http:|https:)//[A-Za-z0-9\\._\\?%&+\\-=/#:]*", Pattern.CASE_INSENSITIVE);

    public static void replaceUrlWithClickSpan(Activity activity, TextView textView, String replcaseText) {
        if (!AssertValue.isNotNullAndNotEmpty(replcaseText)) {
            return;
        }
        String text = textView.getText().toString();
        if (AssertValue.isNotNullAndNotEmpty(text)) {
            List<Integer> startIndexList = new ArrayList<>();
            List<String> urls = new ArrayList<>();
            String resultString = replaceUrlLoop(text, startIndexList, urls, replcaseText);
            if (!text.equals(resultString)) {
                textView.setText(resultString);
                SpannableStringBuilder style = new SpannableStringBuilder(resultString);
                for (int i = 0; i < startIndexList.size(); i++) {
                    Y9UrlSpan y9UrlSpan = new Y9UrlSpan(activity, new SimpleBrowserCommand("", urls.get(i)));
                    style.setSpan(y9UrlSpan, startIndexList.get(i), startIndexList.get(i) + replcaseText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                textView.setText(style);
                textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点
            }
        }
    }

    private static String replaceUrlLoop(String text, List<Integer> startIndexList, List<String> urls, String replcaseText) {
        Matcher matcher = URL_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();
        if (matcher.find()) {
            startIndexList.add(matcher.start());
            urls.add(matcher.group());
            matcher.appendReplacement(result, replcaseText);
        }
        matcher.appendTail(result);
        if (matcher.find()) {
            return replaceUrlLoop(result.toString(), startIndexList, urls, replcaseText);
        }
        if (AssertValue.isNotNullAndNotEmpty(result.toString())) {
            return result.toString();
        }
        return text;

    }
}
