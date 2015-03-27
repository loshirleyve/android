package com.yun9.mobile.calendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;

import com.yun9.mobile.calendar.entity.ScheduleInfo;
import com.yun9.mobile.calendar.entity.ShiftInfo;
import com.yun9.mobile.framework.model.server.hr.ModelQueryUserScheduleInfo;

/**
 * 排班工具类
 * @author Kass
 *
 */
@SuppressLint("SimpleDateFormat")
public class ScheduleUtils {
	/**
	 * 获取这天的排班信息
	 * @param selectedDate
	 * @param list
	 * @return
	 */
	public static ShiftInfo getScheduleInfo(String selectedDate, List<ModelQueryUserScheduleInfo> list) {
		ShiftInfo scheduleInfo;
		String date;
		String startTime = null;
		String endTime = null;
		if (list != null) {
			scheduleInfo = new ShiftInfo();
			for (int i = 0; i < list.size(); i ++) {
				date = TimeUtils.getDateToString(Long.parseLong(list.get(i).getWorkdate()));
				if (selectedDate.equals(date)) {
					scheduleInfo.setDate(date);
					scheduleInfo.setLabel(list.get(i).getShiftlabel());
					if ("上班".equals(list.get(i).getCheckname())) {
						startTime = list.get(i).getChecktime();
						break;
					}
				}
			}
			for (int i = 0; i < list.size(); i ++) {
				date = TimeUtils.getDateToString(Long.parseLong(list.get(i).getWorkdate()));
				if (selectedDate.equals(date)) {
					if ("下班".equals(list.get(i).getCheckname())) {
						endTime = list.get(i).getChecktime();
						break;
					}
				}
			}
			scheduleInfo.setWorkTime(startTime + "-" + endTime);
			if (scheduleInfo.getDate() != null) {
				return scheduleInfo;
			}
		}
		return null;
	}
	
	/**
	 * 获取这天的排班信息列表
	 * @param selectedDate
	 * @param list
	 * @return
	 */
	public static List<ShiftInfo> getShiftInfo(String selectedDate, List<ModelQueryUserScheduleInfo> list) {
		List<ShiftInfo> shiftList = new ArrayList<ShiftInfo>();
		List<ScheduleInfo> scheduleList = new ArrayList<ScheduleInfo>();
		ScheduleInfo scheduleInfo;
		String date;
		if (list != null) {
			for (int i = 0; i < list.size(); i ++) {
				scheduleInfo = new ScheduleInfo();
				date = TimeUtils.getDateToString(Long.parseLong(list.get(i).getWorkdate()));
				if (selectedDate.equals(date)) {
					scheduleInfo.setDate(date);
					scheduleInfo.setLabel(list.get(i).getShiftlabel());
					scheduleInfo.setCheckName(list.get(i).getCheckname());
					scheduleInfo.setCheckTime(list.get(i).getChecktime());
					scheduleList.add(scheduleInfo);
				}
			}
			shiftList = getShiftList(scheduleList);
			return shiftList;
		}
		return null;
	}
	
	/**
	 * 获取班次信息
	 * @param list
	 * @return
	 */
	public static List<ShiftInfo> getShiftList(List<ScheduleInfo> list) {
		List<ShiftInfo> shiftList = new ArrayList<ShiftInfo>();
		List<String> labelList = new ArrayList<String>();
		ShiftInfo shiftInfo;
		String label;
		String startTime = null;
		String endTime = null;
		for (int i = 0; i < list.size(); i ++) {
			if ("上班".equals(list.get(i).getCheckName())) {
				label = list.get(i).getLabel();
				labelList.add(label);
			}
		}
		for (int i = 0; i < labelList.size(); i ++) {
			label = labelList.get(i).toString();
			shiftInfo = new ShiftInfo();
			for (int a = 0; a < list.size(); a ++) {
				if (label.equals(list.get(a).getLabel()) && "上班".equals(list.get(a).getCheckName())) {
					shiftInfo.setDate(list.get(a).getDate());
					shiftInfo.setLabel(list.get(a).getLabel());
					startTime = list.get(a).getCheckTime();
				}
			}
			for (int a = 0; a < list.size(); a ++) {
				if (label.equals(list.get(a).getLabel()) && "下班".equals(list.get(a).getCheckName())) {
					endTime = list.get(a).getCheckTime();
				}
			}
			shiftInfo.setWorkTime(startTime + "-" + endTime);
			shiftList.add(shiftInfo);
		}
		return shiftList;
	}
	
	/**
	 * 获取这天是否在指定的某天之前
	 * @param selectedDate
	 * @param markDate
	 * @return
	 */
	public static boolean isBeforeMarkDate(String selectedDate, String markDate) {
		boolean isBefore = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(selectedDate);
			isBefore = date.before(sdf.parse(markDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isBefore;
	}
	
	/**
	 * 获取这天是否在指定的某天之后
	 * @param date
	 * @return
	 */
	public static boolean isAfterMarkDate(String selectedDate, String markDate) {
		boolean isAfter = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(selectedDate);
			isAfter = date.after(sdf.parse(markDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isAfter;
	}
	
}
