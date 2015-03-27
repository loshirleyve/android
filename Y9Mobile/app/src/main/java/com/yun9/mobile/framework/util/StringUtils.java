package com.yun9.mobile.framework.util;

import java.io.UnsupportedEncodingException;

public class StringUtils {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String checkByte4(String source, String charset)
			throws UnsupportedEncodingException {
		if (!AssertValue.isNotNullAndNotEmpty(source)) {
			return null;
		}
		if (!AssertValue.isNotNullAndNotEmpty(charset)) {
			charset = DEFAULT_CHARSET;
		}
		StringBuffer sb = new StringBuffer();
		byte[] t1 = source.getBytes(charset);
		for (int i = 0; i < t1.length;) {
			byte tt = t1[i];
			if ((tt & 0xF8) == 0xF0) {
				byte[] ba = new byte[4];
				ba[0] = tt;
				ba[1] = t1[i + 1];
				ba[2] = t1[i + 2];
				ba[3] = t1[i + 3];
				i++;
				i++;
				i++;
				i++;
				sb.append(new String(ba)).append(",");
			} else {
				i++;
			}
		}
		if (sb.length() == 0) {
			return null;
		} else {
			return sb.substring(0, sb.length() - 1);
		}

	}

	public static Object escapeNull(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

}
