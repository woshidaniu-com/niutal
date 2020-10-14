package com.woshidaniu.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：时间工具
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月23日上午9:42:09
 */
@Deprecated
public class TimeUtil {

	private static final String systime_format = "yyyy-MM-dd HH:mm:ss";
	private static final String sysday_format = "yyyy-MM-dd";
	
	private TimeUtil() {
		super();
	}


	/**
	 * 
	 * <p>方法说明：取当前系统时间（格式  年-月-日 时:分:秒）<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:39:00<p>
	 * @return java.lang.String
	 */
	public static String getDateTime() {
		SimpleDateFormat f = new SimpleDateFormat(systime_format);
		String time = f.format(new Date());
		return time;
	}

	
	
	/**
	 * 
	 * <p>方法说明：取当前系统时间-指定格式<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:41:08<p>
	 * @param f 时间格式
	 * @return java.lang.String
	 */
	public static String getDateTime(SimpleDateFormat f) {
		String time = f.format(new Date());
		return time;
	}
	
	
	/**
	 * 
	 * <p>方法说明：取当前系统时间-指定格式<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:41:08<p>
	 * @param f 时间格式
	 * @return java.lang.String
	 */
	public static String getDateTime(String f){
		SimpleDateFormat format = new SimpleDateFormat(f);
		return format.format(new Date());
	}
	
	
	/**
	 * 
	 * <p>方法说明：取系统当前日期（格式 年-月-日）<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:44:04<p>
	 * @return java.lang.String
	 */
	public static String getDay() {
		SimpleDateFormat f = new SimpleDateFormat(sysday_format);
		String time = f.format(new Date());
		return time;
	}

	
	
	/**
	 * 
	 * <p>方法说明：格式化日期<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:45:01<p>
	 * @param date 日期
	 * @param f 格式
	 * @return java.lang.String
	 */
	public static String getDay(Date date, String f){
		SimpleDateFormat format = new SimpleDateFormat(f);
		return format.format(date);
	}
	
	
	/**
	 * 
	 * <p>方法说明：格式化日期（年-月-日）<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:45:52<p>
	 * @param date 日期
	 * @return java.lang.String
	 */
	public static String getDay(Date date){
		SimpleDateFormat format = new SimpleDateFormat(sysday_format);
		return format.format(date);
	}
	
	
	/**
	 * 
	 * <p>方法说明：当前小时<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午10:11:50<p>
	 * @return java.lang.String
	 */
	public static String getHour() {
		return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
	}

	
	/**
	 * 
	 * <p>方法说明：当前年份<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午10:04:21<p>
	 * @return java.lang.String
	 */
	public static String getYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	
	/**
	 * 
	 * <p>方法说明：格式化时间戳<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月23日上午9:47:10<p>
	 * @param time 时间戳
	 * @param format 格式
	 * @return java.lang.String
	 */
	public static String getDataTime(long time, String format) {
		Timestamp ts = new Timestamp(time);
		SimpleDateFormat f = new SimpleDateFormat(format);
		String ret = f.format(ts);
		return ret;
	}

}