package com.woshidaniu.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.woshidaniu.basicutils.StringUtils;


/**
 * 日期时间工具类，进行各种日期时间格式的转化以及格式化
 * 
 * @author zhangqy
 * @version v1.0.0
 */

@Deprecated
public class DateTimeUtil {

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
	private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
	private final static String MONTH_FORMAT = "yyyy-MM";
	private final static String DAY_FORMAT = "yyyyMMdd";


	/**
	 * 
	 * <p>方法说明：取得当前系统时间<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:29:44<p>
	 * @return java.util.Date
	 */
	public static Date getCurrDate() {
		return new Date();
	}

	
	/**
	 * 
	 * <p>方法说明：取得当前系统时间戳<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:30:22<p>
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getCurrTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	

	/**
	 * 
	 * <p>方法说明：格式化日期，格式为yyyy-MM-dd<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:34:33<p>
	 * @param date
	 * @return String
	 */
	public static String getFormatDate(Date date) {
		return getFormatDate(date, DATE_FORMAT);
	}

	
	/**
	 * 
	 * <p>方法说明：格式化日期，格式为yyyy-MM-dd<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:42:10<p>
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFormatDateToDate(Date date) {
		return getFormatDate(getFormatDate(date));
	}

	
	/**
	 * 
	 * <p>方法说明： 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:44:17<p>
	 * @param date 日期
	 * @return String
	 */
	public static String getFormatDateToCn(Date date) {
		return getFormatDate(date, DATE_FORMAT_CN);
	}

	
	/**
	 * 
	 * <p>方法说明：得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:44:41<p>
	 * @param date 日期
	 * @return java.util.Date
	 */
	public static Date getFormatDateToDateToCN(Date date) {
		return getFormatDateToCN(getFormatDateToCn(date));
	}

	
	/**
	 * 
	 * <p>方法说明：得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:46:19<p>
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFormatDate(String date) {
		return getFormatDate(date, DATE_FORMAT);
	}

	
	/**
	 * 
	 * <p>方法说明：得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:45:41<p>
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFormatDateToCN(String date) {
		return getFormatDate(date, DATE_FORMAT_CN);
	}

	/**
	 * 根据格式得到格式化后的日期
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的日期，格式由参数<code>format</code>定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static String getFormatDate(java.util.Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static String getFormatDateTime(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            要格式环的时间
	 * @see #getFormatDateTime(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTimeToTime(java.util.Date currDate) {
		return getFormatDateTime(getFormatDateTime(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTime(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static String getFormatDateTime_CN(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime_CN(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTimeToTime_CN(java.util.Date currDate) {
		return getFormatDateTime_CN(getFormatDateTime_CN(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTime_CN(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
	 *         HH:mm:ss
	 */
	public static String getFormatDateTime(java.util.Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的日期
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的日期，格式由参数<code>format</code>定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
	 *         HH:mm:ss
	 */
	public static Date getFormatDateTime(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * <p>方法说明：获取上月的本日<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:48:37<p>
	 * @return String
	 */
	public static String getDateBeforeMonth(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}
	
	
	/**
	 * 
	 * <p>方法说明：获取前一天日期<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月21日上午9:49:40<p>
	 * @return java.lang.String
	 */
	public static String getDateBeforeDay(){
	    Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
	 * 
	 * @see #getFormatDate(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
	 */
	public static String getCurrDateStr() {
		return getFormatDate(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
	 *         15:23:45
	 */
	public static String getCurrDateTimeStr() {
		return getFormatDateTime(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getCurrDateStr_CN() {
		return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
	 *         15:23:45
	 */
	public static String getCurrDateTimeStr_CN() {
		return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
	}

	/**
	 * 得到系统当前日期的前或者后几天
	 * 
	 * @param iDate
	 *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回系统当前日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * 得到日期的前或者后几天
	 * 
	 * @param iDate
	 *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * 得到格式化后的月份，格式为yyyy-MM，如2006-02
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
	 */
	public static String getFormatMonth(java.util.Date currDate) {
		return getFormatDate(currDate, MONTH_FORMAT);
	}

	/**
	 * 得到格式化后的日，格式为yyyyMMdd，如20060210
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
	 */
	public static String getFormatDay(java.util.Date currDate) {
		return getFormatDate(currDate, DAY_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
	 * 
	 * @return String 返回格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 */
	public static String getLastDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}
	
	/**
	 * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 * 
	 * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}
	
	/**
	 * 得到日期的前或者后几小时
	 * 
	 * @param iHour
	 *            如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
	 */
	public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.HOUR_OF_DAY, iHour);
		return cal.getTime();
	}
	
	/**
	 * 将2007-12-1变成2007-12-01。将2007-9-1变为2007-09-01。
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("null")
	public static String getFormatDateV2(String date){
		if(date == null) {
			return date;
		}
		String[] datearr = StringUtils.split(date,"-");
		StringBuffer ret = new StringBuffer();
		
		if(datearr == null && datearr.length<3) {
			return date;
		}
		
		ret.append(datearr[0]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[1]) < 10 ?"0" + datearr[1]:datearr[1]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[2]) < 10 ?"0" + datearr[2]:datearr[2]);
		return ret.toString();
		
	}

	/**
	 * 从时间串中获取小时数。
	 * 
	 * @param timestr "2007-10-12 13:25:00"
	 * @return
	 */
	public static int getHourFromTimeString(String timestr) {
		if (StringUtils.isBlank(timestr)) {
			return 0;
		}

		return Integer.parseInt(timestr.substring(timestr.length() - 8, timestr
				.length() - 6));
	}
	
	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getFormatDate_CN(java.util.Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate_CN(String)
	 * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDateToDate_CN(java.util.Date currDate) {
		return getFormatDate_CN(getFormatDate_CN(currDate));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(String, String)
	 * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDate_CN(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 判断日期是否在当前周内
	 * @param curDate
	 * @param compareDate
	 * @return
	 */
	public static boolean isSameWeek(Date curDate, Date compareDate) {
		if (curDate == null || compareDate == null) {
			return false;
		}
		
		Calendar calSun = Calendar.getInstance();
		calSun.setTime(getFormatDateToDate(curDate));
		calSun.set(Calendar.DAY_OF_WEEK, 1);
		
		Calendar calNext = Calendar.getInstance();
		calNext.setTime(calSun.getTime());
		calNext.add(Calendar.DATE, 7);
		
		Calendar calComp = Calendar.getInstance();
		calComp.setTime(compareDate);
		if (calComp.after(calSun) && calComp.before(calNext)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 时间查询时,结束时间的 23:59:59
	 */
	public static String addDateEndfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}
		return datestring + " 23:59:59";
	}
	/**
	 * 返回格式化的日期
	 * @param datePre 格式"yyyy-MM-dd HH:mm:ss";
	 * @return
	 */
	public static Date formatEndTime(String datePre){
		if(datePre==null)return null;
		String dateStr = addDateEndfix(datePre);
		return getFormatDateTime(dateStr);
	}
	
	//date1加上compday天数以后的日期与当前时间比较，如果大于当前时间返回true，否则false
	public static Boolean compareDay(Date date1,int compday){
		if(date1==null)return false;
		Date dateComp = getDateBeforeOrAfter(date1,compday);
		Date nowdate = new Date();
		if(dateComp.after(nowdate))
			return true;
		else
			return false;
	}
	
	/**
	 * 进行时段格式转换，对于输入的48位的01串，将进行如下操作：
	 * <li>1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li>
	 * <li>2.将上述的96位的二进制串分成3组，每组32位。</li>
	 * <li>3.将每个32位的二进制串转换成一个8位的16进制串。</li>
	 * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
	 * 
	 * @param timespan
	 *            一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 * @return 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
	 */
	public static String convertBinaryTime2Hex(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String hexStr = Long.toHexString(t);
				if (hexStr.length() < 8) {
					int length = hexStr.length();
					for (int n = 0; n < 8 - length; n++) {
						hexStr = "0" + hexStr;
					}
				}

				ret += hexStr;
				tmp = "";
			}
		}

		return ret;
	}

	/**
	 * 进行时段格式转换，将输入的26位的2进制串转换成48位的二进制串。
	 * 
	 * @param timespan
	 *            一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
	 * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 */
	public static String convertHexTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 16));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}

	/**
	 * 进行时段格式转换，将输入的32位的10进制串转换成48位的二进制串。
	 * 
	 * @param timespan
	 *            一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890c"
	 * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 */
	public static String convertDecTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 10));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}
	
	/**
	 * 进行时段格式转换，对于输入的48位的01串，将进行如下操作：
	 * <li>1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li>
	 * <li>2.将上述的96位的二进制串分成3组，每组32位。</li>
	 * <li>3.将每个32位的二进制串转换成一个10位的10进制串。</li>
	 * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
	 * 
	 * @param timespan
	 *            一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 * @return 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890"
	 */
	public static String convertBinaryTime2Dec(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String decStr = Long.toString(t);
				if (decStr.length() < 10) {
					int length = decStr.length();
					for (int n = 0; n < 10 - length; n++) {
						decStr = "0" + decStr;
					}
				}

				ret += decStr;
				tmp = "";
			}
		}

		return ret;
	}
	
	/**
	 * 计算指定日期+addMonth月+15号 返回格式"2008-02-15"
	 * @param date
	 * @param addMonth
	 * @param monthDay
	 * @return
	 */
	public static String genericSpecdate(Date date,int addMonth,int monthDay){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, monthDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}
	
	public static void main(String[] args) {
		/** ***********Test of convertTimeSpan***************** */
		//System.out.println(getLastDayOfMonth(new Date()));
		System.out.println(genericSpecdate(new Date(),2,15));
	}
	
}
