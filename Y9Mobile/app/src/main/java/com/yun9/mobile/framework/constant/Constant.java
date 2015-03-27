package com.yun9.mobile.framework.constant;

import com.yun9.mobile.framework.util.DateUtil;

public class Constant {

	/**
	 * 
	 * 传递给写动态界面的消息头
	 * */
	public static String KEY_MSG_HEAD = "MSG_HEAD";
	
	public static String getMsgHeadJingYingLog() {
		StringBuffer sb = new StringBuffer("#经营日志#\r\n");
		sb.append("日期：").append(DateUtil.getCurrentTime("yyyy年MM月dd日")).append("（周"+DateUtil.getDayOfWeek()+"）\r\n");
		sb.append("班次：早班/晚班\r\n");
		sb.append("门店：\r\n");
		sb.append("卖场状况：现场拍摄图片\r\n");
		sb.append("销售额：\r\n卖场活动：\r\n货品情况：");
		System.out.println("经营日志......................."+sb);
		return sb.toString();
	}
	
	public static String getMsgHeadShopReport() {
		StringBuffer sb = new StringBuffer("#巡店报告#\r\n");
		sb.append("日期：").append(DateUtil.getCurrentTime("yyyy年MM月dd日")).append("（周"+DateUtil.getDayOfWeek()+"）\r\n");
		sb.append("地点：\r\n");
		sb.append("卖场氛围：现场拍摄图片\r\n");
		sb.append("卖场活动：\r\n实时销售状况：\r\n员工状态：\r\n货品状况：\r\n");
		sb.append("已实施处置：");
		System.out.println("巡店报告......................."+sb);
		return sb.toString();
	}
}
