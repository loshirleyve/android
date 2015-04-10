package com.yun9.wservice.util;

import java.util.Collection;
import java.util.Map;

/**
 * Argument assertion utilities.
 * <p/>
 * <b>Copied from <a href="http://www.milyn.org">milyn.org</a></b>.
 * 
 * @author tfennelly
 */

public abstract class AssertArgument {

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
	public static Object isNotNull(Object arg, String argName)
			throws IllegalArgumentException {
		if (arg == null) {
			throw new IllegalArgumentException("null  '" + argName
					+ "' arg in method call.");
		}
		return arg;
	}

	public static Object isNotNull(Object arg, String argName, Class<?> clazz) {
		return isNotNull(arg, "[" + clazz.getName() + "]" + argName);
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
	public static String isNotEmpty(String arg, String argName)
			throws IllegalArgumentException {
		if (arg != null && arg.trim().equals("")) {
			throw new IllegalArgumentException("Not null, but empty '"
					+ argName + "' arg in method call.");
		}
		return arg;
	}

	public static String isNotEmpty(String arg, String argName, Class<?> clazz)
			throws IllegalArgumentException {
		return isNotEmpty(arg, "[" + clazz.getName() + "]" + argName);
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
	public static String isNotNullAndNotEmpty(String arg, String argName)
			throws IllegalArgumentException {
		if (arg == null || arg.trim().equals("")) {
			throw new IllegalArgumentException("null or empty '" + argName
					+ "' arg in method call.");
		}
		return arg;
	}

	public static String isNotNullAndNotEmpty(String arg, String argName,
			Class<?> clazz) throws IllegalArgumentException {
		return isNotNullAndNotEmpty(arg, "[" + clazz.getName() + "]" + argName);
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
	public static Collection isNotNullAndNotEmpty(Collection arg, String argName)
			throws IllegalArgumentException {
		if (arg == null || arg.isEmpty()) {
			throw new IllegalArgumentException("null or empty '" + argName
					+ "' arg in method call.");
		}
		return arg;
	}

	public static Collection isNotNullAndNotEmpty(Collection arg,
			String argName, Class<?> clazz) throws IllegalArgumentException {
		return isNotNullAndNotEmpty(arg, "[" + clazz.getName() + "]" + argName);
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
	public static Object[] isNotNullAndNotEmpty(Object[] arg, String argName)
			throws IllegalArgumentException {
		if (arg == null || arg.length == 0) {
			throw new IllegalArgumentException("null or empty '" + argName
					+ "' arg in method call.");
		}
		return arg;
	}

	public static Object[] isNotNullAndNotEmpty(Object[] arg, String argName,
			Class<?> clazz) throws IllegalArgumentException {
		return isNotNullAndNotEmpty(arg, "[" + clazz.getName() + "]" + argName);
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
	public static Map isNotNullAndNotEmpty(Map arg, String argName)
			throws IllegalArgumentException {
		if (arg == null || arg.isEmpty()) {
			throw new IllegalArgumentException("null or empty '" + argName
					+ "' arg in method call.");
		}
		return arg;
	}

	public static Map isNotNullAndNotEmpty(Map arg, String argName,
			Class<?> clazz) throws IllegalArgumentException {
		return isNotNullAndNotEmpty(arg, "[" + clazz.getName() + "]" + argName);
	}
}
