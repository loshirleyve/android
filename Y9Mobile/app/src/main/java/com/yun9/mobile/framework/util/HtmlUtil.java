package com.yun9.mobile.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
	private static final Pattern urlPattern = Pattern
			.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
					Pattern.CASE_INSENSITIVE);
	
	/**
	 * 获取内容中包含的URL
	 * @param content
	 * @return
	 */
	public static List<UrlEle> getUrls(String content) {
		List<UrlEle> urls= new ArrayList<UrlEle>();
		if (!AssertValue.isNotNullAndNotEmpty(content)) {
			return urls;
		}
		Matcher m = urlPattern.matcher(content);
		while (m.find()) {
			urls.add(new UrlEle(m.group(), m.start(), m.end()));
		}
		return urls;
	}

	public static class UrlEle {
		private String url;
		private int start;
		private int end;
		
		public UrlEle(String url, int start, int end) {
			super();
			this.url = url;
			this.start = start;
			this.end = end;
		}
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
	}
}
