/*
 * System: CoreLib
 * @version     1.00
 * 
 * Copyright (C) 2010, LiHong.
 */
package com.yun9.mobile.framework.task;

import java.util.ArrayList;

import com.yun9.mobile.framework.task.TaskManager.TaskManagerState;

/**
 * The task operation, it wraps the task parameter, etc.
 * 
 * @author LeeHong
 *
 * @date 2012/10/30
 */
public class TaskOperation {
	/**
	 * The task parameter.
	 */
	private Object[] mNextTaskParams = null;

	/**
	 * The task manager status.
	 */
	private TaskManagerState mTaskManagerStatus = TaskManagerState.CONTINUE;

	/**
	 * The constructor method.
	 */
	public TaskOperation() {
	}

	/**
	 * The constructor method.
	 * 
	 * @param nextTaskParams
	 */
	public TaskOperation(Object[] nextTaskParams) {
		mNextTaskParams = nextTaskParams;
	}

	/**
	 * The constructor method.
	 * 
	 * @param operation
	 */
	public TaskOperation(TaskOperation operation) {
		setTaskParams(operation);
	}

	/**
	 * Get the task parameter.
	 */
	public Object[] getTaskParams() {
		return mNextTaskParams;
	}

	/**
	 * Set the task parameter.
	 * 
	 * @param params
	 */
	public void setTaskParams(Object[] params) {
		mNextTaskParams = params;
	}

	/**
	 * Set the task parameters.
	 * 
	 * @param operation
	 */
	public void setTaskParams(TaskOperation operation) {
		if (operation == this) {
			throw new IllegalArgumentException("The argument can NOT be self.");
		}

		if (null == operation) {
			return;
		}

		Object[] params = operation.getTaskParams();
		if (null == params) {
			return;
		}

		ArrayList<Object> paramsList = new ArrayList<Object>();

		if (null != mNextTaskParams) {
			for (Object param : mNextTaskParams) {
				paramsList.add(param);
			}
		}

		for (Object param : params) {
			paramsList.add(param);
		}

		mNextTaskParams = paramsList.toArray();
	}

	/**
	 * @param status
	 *            the mTaskManagerStatus to set
	 */
	public void setTaskManagerStatus(TaskManagerState status) {
		mTaskManagerStatus = status;
	}

	/**
	 * @return the mTaskManagerStatus
	 */
	public TaskManagerState getTaskManagerStatus() {
		return mTaskManagerStatus;
	}

	/**
	 * Append the specified parameter to the end of the parameter list.
	 * 
	 * @param param
	 */
	public void appendTaskParam(Object param) {
		appendTaskParams(new Object[] { param });
	}

	/**
	 * Append the specified parameter to the end of the parameter list.
	 * 
	 * @param params
	 */
	public void appendTaskParams(Object[] params) {
		if (null != params) {
			TaskOperation operation = new TaskOperation(params);
			setTaskParams(operation);
		}
	}
}