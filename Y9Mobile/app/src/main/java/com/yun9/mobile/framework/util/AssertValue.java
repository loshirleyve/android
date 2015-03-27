package com.yun9.mobile.framework.util;

import java.util.Collection;
import java.util.Map;

/**
 * Argument assertion utilities.
 * <p/>
 * <b>Copied from <a href="http://www.milyn.org">milyn.org</a></b>.
 * 
 * @author tfennelly
 * */

public abstract class AssertValue {

	/**
	 * Assert that the argument is not null.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is null.
	 */
	public static boolean isNotNull(Object arg) {
		if (arg == null) {
			return false;
		}
		return true;
	}

	/**
	 * Assert that the argument is not empty.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is not null, but is empty.
	 */
	public static boolean isNotEmpty(String arg) {
		if (arg != null && arg.trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Assert that the argument is neither null nor empty.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is null or empty.
	 */
	public static boolean isNotNullAndNotEmpty(String arg) {
		if (arg == null || arg.trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Assert that the argument is neither null nor empty.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is null or empty.
	 */
	public static boolean isNotNullAndNotEmpty(Collection arg) {
		if (arg == null || arg.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Assert that the argument is neither null nor empty.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is null or empty.
	 */
	public static boolean isNotNullAndNotEmpty(Object[] arg) {
		if (arg == null || arg.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Assert that the argument is neither null nor empty.
	 * 
	 * @param arg
	 *            Argument.
	 * @param argName
	 *            Argument name.
	 * @throws IllegalArgumentException
	 *             Argument is null or empty.
	 */
	public static boolean isNotNullAndNotEmpty(Map arg) {
		if (arg == null || arg.isEmpty()) {
			return false;
		}
		return true;
	}
}
