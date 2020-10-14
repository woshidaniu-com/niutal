package com.woshidaniu.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.woshidaniu.util.base.StringUtil;
/**
 * Created by IntelliJ IDEA.
 * User: zhaosheng
 * Date: 12-8-7
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss格式正则
     * */
    public static final String FORMAT_ONE_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    /**
     * yyyy-MM-dd HH:mm:ss格式SimpleDateFormat对象
     * */
    public static final SimpleDateFormat FORMAT_ONE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * yyyy-MM-dd格式正则
     * */
    public static final String FORMAT_TWO_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";

    /**
     * yyyy-MM-dd格式SimpleDateFormat对象
     * */
    public static final SimpleDateFormat FORMAT_TWO = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * yyyy/MM/dd HH:mm:ss格式正则
     * */
    public static final String FORMAT_THREE_REG = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";

    /**
     * yyyy/MM/dd HH:mm:ss格式SimpleDateFormat对象
     * */
    public static final SimpleDateFormat FORMAT_THREE = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * yyyy/MM/dd格式正则
     * */
    public static final String FORMAT_FOUR_REG = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";

    /**
     * yyyy/MM/dd格式SimpleDateFormat对象
     * */
    public static final SimpleDateFormat FORMAT_FOUR = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * yyyy-MM-dd HH:mm格式正则
     * */
    public static final String FORMAT_FIVE_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}$";

    /**
     * yyyy-MM-dd HH:mm格式SimpleDateFormat对象
     * */
    public static final SimpleDateFormat FORMAT_FIVE = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static HashMap<String,SimpleDateFormat> PRESET_FORMAT = new HashMap<String,SimpleDateFormat>();

    static {
        PRESET_FORMAT.put("1",FORMAT_ONE);
        PRESET_FORMAT.put("2",FORMAT_TWO);
        PRESET_FORMAT.put("3",FORMAT_THREE);
        PRESET_FORMAT.put("4",FORMAT_FOUR);
        PRESET_FORMAT.put("5",FORMAT_FIVE);
    }

    /**
     * 根据传入的格式化格式对当前日间进行格式后返回<br/>
     * 如果format为null或空则返回yyyy-MM-dd HH:mm:ss格式的日期<br/>
     * format可传入自定义的日期格式,也可使用预置格式,如下:<br/>
     *  <ul>
     *      <li>1    yyyy-MM-dd HH:mm:ss</li>
     *      <li>2    yyyy-MM-dd</li>
     *      <li>3   yyyy/MM/dd HH:mm:ss </li>
     *      <li>4   yyyy/MM/dd   </li>
     *      <li>5   yyyy-MM-dd HH:mm </li>
     *  </ul>
     * @param format 格式
     * @return 当前日期经格式化后的字符串形式
     * */
    public static String getCustomFomratCurrentDate(String format) {
        if(StringUtil.isNotEmpty(format)){
            SimpleDateFormat presetFormat = PRESET_FORMAT.get(format);
            if(presetFormat != null){
                return presetFormat.format(new Date());
            }
            SimpleDateFormat custom = new SimpleDateFormat(format);
            return custom.format(new Date());
        }
        return FORMAT_ONE.format(new Date());
    }
    /**
     * 根据传入的格式化格式对传入的日期对象进行格式后返回<br/>
     * 如果format为null或空则返回yyyy-MM-dd HH:mm:ss格式的日期<br/>
     * format可传入自定义的日期格式,也可使用预置格式,如下:<br/>
     *  <ul>
     *      <li>1    yyyy-MM-dd HH:mm:ss</li>
     *      <li>2    yyyy-MM-dd</li>
     *      <li>3   yyyy/MM/dd HH:mm:ss </li>
     *      <li>4   yyyy/MM/dd   </li>
     *      <li>5   yyyy-MM-dd HH:mm </li>
     *  </ul>
     *  @param date 将被格式化的日期
     * @param format 格式
     * @return 当前日期经格式化后的字符串形式
     * */
    public static String getCustomFomratCurrentDate(Date date,String format) {
        if(StringUtil.isNotEmpty(format)){
            SimpleDateFormat presetFormat = PRESET_FORMAT.get(format);
            if(presetFormat != null){
                return presetFormat.format(date);
            }
            SimpleDateFormat custom = new SimpleDateFormat(format);
            return custom.format(date);
        }
        return FORMAT_ONE.format(date);
    }
    /**
     * 根据传入的格式化格式对传入的日期字符串进行格式后返回<br/>
     * 如果format为null或空则返回yyyy-MM-dd HH:mm:ss格式的日期<br/>
     * format可传入自定义的日期格式,也可使用预置格式,如下:<br/>
     *  <ul>
     *      <li>1    yyyy-MM-dd HH:mm:ss</li>
     *      <li>2    yyyy-MM-dd</li>
     *      <li>3   yyyy/MM/dd HH:mm:ss </li>
     *      <li>4   yyyy/MM/dd   </li>
     *      <li>5   yyyy-MM-dd HH:mm </li>
     *  </ul>
     *  @param date 将被格式化的日期
     * @param format 格式
     * @return 当前日期经格式化后的字符串形式
     * @throws Exception 
     * */
    public static String getCustomFomratCurrentDate(String date,String format) throws Exception {
        if(StringUtil.isNotEmpty(format)){
            SimpleDateFormat presetFormat = PRESET_FORMAT.get(format);
            if(presetFormat != null){
                return presetFormat.format(parse(date));
            }
            SimpleDateFormat custom = new SimpleDateFormat(format);
            return custom.format(parse(date));
        }
        return FORMAT_ONE.format(date);
    }

    /**
     * 将日期字符串解析成日期对象，支持一下格式
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd
     * <li>yyyy/MM/dd HH:mm:ss
     * <li>yyyy/MM/dd
     * </p>
     *
     * @param dateString 字符串形式的日期
     * @return  字符串格式日期经相应的格式格式化后的Date形式<br/>
     * 如果传入的日期跟内置日期格式不匹配,抛出异常
     * @throws Exception 
     */
    public static Date parse(String dateString) throws Exception {
        Date date = null;
        try {
            Pattern p1 = Pattern.compile(FORMAT_ONE_REG);
            Matcher m1 = p1.matcher(dateString);
            if (m1.matches()) {
                date = FORMAT_ONE.parse(dateString);
            } else {
                Pattern p2 = Pattern.compile(FORMAT_TWO_REG);
                Matcher m2 = p2.matcher(dateString);
                if (m2.matches()) {
                    date = FORMAT_TWO.parse(dateString);
                } else {
                    Pattern p3 = Pattern.compile(FORMAT_THREE_REG);
                    Matcher m3 = p3.matcher(dateString);
                    if (m3.matches()) {
                        date = FORMAT_THREE.parse(dateString);
                    } else {
                        Pattern p4 = Pattern.compile(FORMAT_FOUR_REG);
                        Matcher m4 = p4.matcher(dateString);
                        if (m4.matches()) {
                            date = FORMAT_FOUR.parse(dateString);
                        } else {
                            Pattern p5 = Pattern.compile(FORMAT_FIVE_REG);
                            Matcher m5 = p5.matcher(dateString);
                            if (m5.matches()) {
                                date = FORMAT_FIVE.parse(dateString);
                            }
                        }
                    }
                }
            }
        } catch (ParseException e) {
            throw new Exception("非法日期字符串，解析失败：" + dateString, e);
        }
        return date;
    }

    /**
     * 得到内置日期的格式<br/>
     * <b>得到的内容如下:<br/></b>
     * <ul>
     *     <li>0->yyyy-MM-dd</li>
     *     <li>1->yyyy年MM月dd日</li>
     *     <li>2->yyyy年MM月</li>
     *     <li>3->MM月dd日</li>
     *     <li>7->yyyy-MM-dd</li>
     *     <li>8->EEE(周次 如  周一)</li>
     * </ul>
     * @return 返回日期格式的map集合
     * */
    public static Map<String, String> getDateFormatMap() {
        Map<String, String> dateFormatMap = new HashMap<String, String>();
        dateFormatMap.put("0", "yyyy-MM-dd");
        dateFormatMap.put("1", "yyyy年MM月dd日");
        dateFormatMap.put("2", "yyyy年MM月");
        dateFormatMap.put("3", "MM月dd日");
        dateFormatMap.put("7", "yyyy-MM-dd");
        dateFormatMap.put("8", "EEE");
        return dateFormatMap;
    }

    /**
     * 得到内置时间的格式 <br/>
     * <b>得到的内容如下:<br/></b>
     * <ul>
     *     <li>0->HH:mm:ss</li>
     *     <li>1->HH:mm:ss.SSS</li>
     *     <li>2->HH:mm</li>
     *     <li>3->h:m a</li>
     *     <li>4->h:m:s a</li>
     *     <li>5->HH时mm分ss秒</li>
     *     <li>6->HH时mm分</li>
     *     <li>7->a h时m分</li>
     *     <li>8->a h时m分s秒</li>
     * </ul>
     * @return 返回时间格式的map集合
     * */
    public static Map<String, String> getTimeFormatMap() {
        Map<String, String> timeFormatMap = new HashMap<String, String>();
        timeFormatMap.put("0", "HH:mm:ss");
        timeFormatMap.put("1", "HH:mm:ss.SSS");
        timeFormatMap.put("2", "HH:mm");
        timeFormatMap.put("3", "h:m a");
        timeFormatMap.put("4", "h:m:s a");
        timeFormatMap.put("5", "HH时mm分ss秒");
        timeFormatMap.put("6", "HH时mm分");
        timeFormatMap.put("7", "a h时m分");
        timeFormatMap.put("8", "a h时m分s秒");
        return timeFormatMap;
    }

    /**
    * <p/>将日期格式化成字符串：yyyy-MM-dd</p>
    *
    * @param date 将被格式的Date对象
    * @return 被格式化后的日期字符串形式
    */
    public static String format(Date date) {
        return FORMAT_TWO.format(date);
    }

    /**
    * 将日期格式化成字符串,如果时间是 00:00:00则去掉时间
    *
    * @param date 将被格式的Date对象
    * @return 格式化后的字符串
    */
    public static String formatBySituation(Date date) {
        String ds = FORMAT_ONE.format(date);
        return ds.endsWith("00:00:00") ? ds.substring(0, 10) : ds;
    }
    /**
     * 将long类型的日期格式化成yyyy-MM-dd HH:mm:ss格式的日期字符串形式
     * @param date 将被格式化的long类型日期
     * @return 格式化后的字符串形式日期
     * */
    public static String format(long date) {
        return FORMAT_ONE.format(date);
    }

    /**
     * <p/>
     * 将日期格式化成相应格式的字符串，如：
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd
     * <li>yyyy/MM/dd HH:mm:ss
     * <li>yyyy/MM/dd
     * </p>
     *
     * @param date
     * @param pattern
     * @return java.lang.String
     */
    public static String format(Date date, String pattern) {
        if (date == null) return "";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获取字符串日期中的年
     * @param date 源日期
     * @return year 源日期中的年份信息
     * @throws Exception 
     */
    public static int getYear(String date) throws Exception {
        try {
            return getYear(FORMAT_TWO.parse(date));
        } catch (ParseException e) {
            throw new Exception("日期转换出错", e);
        }
    }

    /**
     * 取得日期对象中的年
     *
     * @param date 源日期对象
     * @return year 源日期对象中的年份信息
     */
    public static int getYear(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 取得日期对象中的月
     *
     * @param date 源日期对象
     * @return month 源日期对象中的月份信息
     */
    public static int getMonth(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得字符串日期中的月
     *
     * @param date 源日期
     * @return month 源日期中的月份信息
     * @throws Exception 
     */
    public static int getMonth(String date) throws Exception {
        try {
            return getMonth(FORMAT_TWO.parse(date));
        } catch (ParseException e) {
            throw new Exception("日期转换出错", e);
        }
    }

    /**
     * 取得日期对象中的日
     *
     * @param date 源日期对象
     * @return month 源日期对象中的日信息
     */
    public static int getDate(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 取得字符串日期中的日
     *
     * @param date 源日期
     * @return month 源日期中的日信息
     * @throws Exception 
     */
    public static int getDate(String date) throws Exception {
        try {
            return getDate(FORMAT_TWO.parse(date));
        } catch (ParseException e) {
            throw new Exception("日期转换出错", e);
        }
    }

    /**
     * 取得日期格式中的小时。24小时制
     *
     * @param date 源日期对象
     * @return 源日期对象中的小时信息
     */
    public static int getHour(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 取得日期格式中的分钟。
     *
     * @param date 源日期对象
     * @return 源日期对象中的分钟信息
     */
    public static int getMinute(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 取得日期格式中的分钟。
     *
     * @param date 源日期对象
     * @return 源日期对象中的秒信息
     */
    public static int getSecond(Date date) {
        if (date == null)
            return 0;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 取得字符串日期中的小时。
     *
     * @param date 源日期
     * @return 源日期中的小时信息
     * @throws Exception 
     */
    public static int getHour(String date) throws Exception {
        try {
            return getHour(FORMAT_ONE.parse(date));
        } catch (ParseException e) {
            throw new Exception("日期转换出错", e);
        }
    }

    /**
     * 取得Calendar实例
     * @return Calendar实例
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 根据日期偏移天数取得日期。<br/>
     * offset > 0 ,往后延迟offset天， <br/>
     * offset < 0 向前推进 offset天  <br/>
     *
     * @param date 基日期
     * @param offset 日期天数偏移量
     * @return 偏移后的日期
     */
    public static Date getDate(Date date, int offset) {
        if (date == null)
            return date;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return calendar.getTime();
    }

    /**
     * 得到给定日期的中文形式 二0一二-0八-0七
     * @param dateString 日期的字符串形式
     * @return 转成中文后的日期
     * */
    public static String dealYearForChinese(String dateString) {
        StringBuffer dataChinese = new StringBuffer();
        String[] dateArray = dateString.split("");
        for (String s1 : dateArray) {
            if ("1".equals(s1)) {
                dataChinese.append("一");
            } else if ("2".equals(s1)) {
                dataChinese.append("二");
            } else if ("3".equals(s1)) {
                dataChinese.append("三");
            } else if ("4".equals(s1)) {
                dataChinese.append("四");
            } else if ("5".equals(s1)) {
                dataChinese.append("五");
            } else if ("6".equals(s1)) {
                dataChinese.append("六");
            } else if ("7".equals(s1)) {
                dataChinese.append("七");
            } else if ("8".equals(s1)) {
                dataChinese.append("八");
            } else if ("9".equals(s1)) {
                dataChinese.append("九");
            } else {
                dataChinese.append(s1);
            }
        }
        return dataChinese.toString();
    }

    /**
     * 将经 dealYearForChinese(String dateString) 转化后的类似于 一二 这样的月转成 一十二
     * @param monthChinese 月份中文
     * @return 将转换后的月份返回
     * */
    public static String dealMonthDayForChinese(String monthChinese) {
        String[] ss = monthChinese.split("");
        StringBuffer sb = new StringBuffer();
        sb.append(ss[1]).append("十").append(ss[2]);
        return sb.toString().replace("0", "");
    }
    /**
     * 将日期的数字形式转成汉字   12转成一十二
     * @param m 数字形式日期
     * @return 汉字日期
     * */
    public static String dealMonthForChinese(int m) {
        if (m < 10)
            return dealYearForChinese(String.valueOf(m));
        else
            return dealMonthDayForChinese(dealYearForChinese(String.valueOf(m)));
    }

    public static String dealDayForChinese(int day) {
        if (day < 10)
            return dealYearForChinese(String.valueOf(day));
        else
            return dealMonthDayForChinese(dealYearForChinese(String.valueOf(day)));
    }

    /**
     * 将给定的日期按照传入的内置日期格式码对日期进行转化,转成字符串格式
     * @param date 将被转化的日期
     * @param formatCode 内置日期格式码,详情如下<br/>
     * <ul>
     *     <li>0->yyyy-MM-dd</li>
     *     <li>1->yyyy年MM月dd日</li>
     *     <li>2->yyyy年MM月</li>
     *     <li>3->MM月dd日</li>
     *     <li>7->yyyy-MM-dd</li>
     *     <li>8->EEE(周次 如  周一)</li>
     *     <li>4->二0一二年八月二十五日</li>
     *     <li>5->二0一二年八月</li>
     *     <li>6->八月二十五日</li>
     * </ul>
     *  @return 格式化后的日期
     *
     * */
    public static String getDateString(Date date, String formatCode) {
        Map<String, String> dateFormatMap = getDateFormatMap();
        if (!"4".equals(formatCode) && !"5".equals(formatCode) && !"6".equals(formatCode)) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormatMap.get(formatCode));
            return format.format(date);
        } else {
            StringBuffer dateString = new StringBuffer();
            String y = dealYearForChinese(String.valueOf(getYear(date)));
            String m = dealMonthForChinese(getMonth(date));
            String day = dealMonthForChinese(getDate(date));
            if ("4".equals(formatCode)) {
                dateString.append(y).append("年").append(m).append("月").append(day).append("日");
            } else if ("5".equals(formatCode)) {
                dateString.append(y).append("年").append(m).append("月");
            } else if ("6".equals(formatCode)) {
                dateString.append(m).append("月").append(day).append("日");
            }
            return dateString.toString();
        }
    }

    /**
     * 得到给定时间码对应的内置时间格式,将给定的日期对象按照此格式进行转换
     * @param date 将被转换的时间
     * @param timeCode 内置时间格式码,详情如下:<br/>
     * <ul>
     *     <li>0->HH:mm:ss</li>
     *     <li>1->HH:mm:ss.SSS</li>
     *     <li>2->HH:mm</li>
     *     <li>3->h:m a</li>
     *     <li>4->h:m:s a</li>
     *     <li>5->HH时mm分ss秒</li>
     *     <li>6->HH时mm分</li>
     *     <li>7->a h时m分</li>
     *     <li>8->a h时m分s秒</li>
     *     <li>9->一十三时三十二分(24小时制)</li>
     *     <li>10->下午一时三十二分(12小时制)</li>
     * </ul>
     * @return 格式化后的时间
     * */
    public static String getTimeString(Date date, String timeCode) {
        Map<String, String> dateFormatMap = getTimeFormatMap();
        if (!"3".equals(timeCode) && !"4".equals(timeCode) && !"9".equals(timeCode) && !"10".equals(timeCode)) {
            SimpleDateFormat sdf_ = new SimpleDateFormat(dateFormatMap.get(timeCode));
            return sdf_.format(date);
        } else if ("3".equals(timeCode) || "4".equals(timeCode)) {
            SimpleDateFormat sdf_ = new SimpleDateFormat(dateFormatMap.get(timeCode), Locale.ENGLISH);
            return sdf_.format(date);
        } else {
            StringBuffer timeString = new StringBuffer();
            int hour1 = getHour(date);
            int hour2 = hour1 % 12;

            int minute = getMinute(date);
            if ("9".equals(timeCode)) {
                timeString.append(dealMonthForChinese(hour1)).append("时").append(dealMonthForChinese(minute)).append("分");
            } else if ("10".equals(timeCode)) {
                if (hour1 < 12) {
                    timeString.append("上午");
                } else {
                    timeString.append("下午");
                }
                if (hour2 == 0) {
                    timeString.append("0");
                } else {
                    timeString.append(dealMonthForChinese(hour2));
                }
                timeString.append("时").append(dealMonthForChinese(minute)).append("分");
            }
            return timeString.toString();
        }
    }
    /**
     * 将给定的日期按照给定的格式格式化成字符串类型
     * @param date 要被格式化的日期
     * @param formatJson 格式化的格式<br/>
     * 格式:{date:3,time:4},formatJson的值详情查看getDateFormatMap和getDateString这两个方法<br/>
     * <b><br/>以下是formatJson中日期格式码(date)的值所代表的格式:<br/></b>
     * <ul>
     * <li>0 yyyy-MM-dd    </li>
     * <li>1 yyyy年MM月dd日 </li>
     * <li>2 yyyy年MM月   </li>
     * <li>3 MM月dd日   </li>
     * <li>7 yyyy-MM-dd  </li>
     * <li>8 EEE    返回当前周几   e.g.星期一  </li>
     * <li>4 中文 年月日 e.g.二0一二年八月一十三日  </li>
     * <li>5 中文 年月   e.g.二0一二年八月  </li>
     * <li>6 中文 月日   e.g.八月一十三日 </li>
     * </ul>
     *
     * <b>formatJson中时间格式码(time),详情如下:<br/></b>
     * <ul>
     *     <li>0->HH:mm:ss</li>
     *     <li>1->HH:mm:ss.SSS</li>
     *     <li>2->HH:mm</li>
     *     <li>3->h:m a</li>
     *     <li>4->h:m:s a</li>
     *     <li>5->HH时mm分ss秒</li>
     *     <li>6->HH时mm分</li>
     *     <li>7->a h时m分</li>
     *     <li>8->a h时m分s秒</li>
     *     <li>9->一十三时三十二分(24小时制)</li>
     *     <li>10->下午一时三十二分(12小时制)</li>
     * </ul>
     * @return 格式化后的字符串格式的日期<br/>
     * e.g.formatJson 为 {date:0,time:3} 返回 2012-08-15 1:38 PM
     * */
    public static String getFormatDate(Date date, String formatJson) {
        StringBuffer dateString = new StringBuffer();
        JSONObject format = JSONObject.fromObject(formatJson);
        String date_ = format.getString("date");
        dateString.append(getDateString(date, date_));
        if (format.containsKey("time")) {
            dateString.append(" ").append(getTimeString(date, format.getString("time")));
        }
        return dateString.toString();
    }

    /**获取当前时间戳 ,等同于DateUtils.getCustomFomratCurrentDate("yyyyMMddHHmmssSSS")
    * @return 当前时间的时间戳
    * */
    public static String getTimeSuffix() {
        return getTimeSuffix(new Date());
    }

    /**
   * 获取指定时间的时间戳 等同于
     * @param date 指定将获取时间戳的时间对象
     * @return 指定时间的时间戳
   * */
    public static String getTimeSuffix(Date date) {
        StringBuffer sb = new StringBuffer();
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        sb.append(calendar.get(Calendar.YEAR));

        if (calendar.get(Calendar.MONTH) < 9) {
            sb.append(0);
            sb.append(calendar.get(Calendar.MONTH) + 1);
        } else
            sb.append(calendar.get(Calendar.MONTH) + 1);

        if (calendar.get(Calendar.DATE) < 10) {
            sb.append(0);
            sb.append(calendar.get(Calendar.DATE));
        } else
            sb.append(calendar.get(Calendar.DATE));

        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            sb.append(0);
            sb.append(calendar.get(Calendar.HOUR_OF_DAY));
        } else
            sb.append(calendar.get(Calendar.HOUR_OF_DAY));

        if (calendar.get(Calendar.MINUTE) < 10) {
            sb.append(0);
            sb.append(calendar.get(Calendar.MINUTE));
        } else {
            sb.append(calendar.get(Calendar.MINUTE));
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            sb.append(0);
            sb.append(calendar.get(Calendar.SECOND));
        } else {
            sb.append(calendar.get(Calendar.SECOND));
        }
        sb.append(calendar.get(Calendar.MILLISECOND));
        return sb.toString();
    }

    /**
     * Parse a string and return the date value in the specified format<br/>
     * 根据指定的格式strFormat,将给定的字符串形式的日期转换成date类型<br/>
     * 如果strFormat为null,则使用 <b>yyyy-MM-dd HH:mm:ss</b> 格式<br/>
     * 转换使用SimpleDateFormat类完成
     *
     *
     * @param strFormat 日期格式
     * @param dateValue 要转换的日期字符串
     * @return 转换以后的日期
     */
    public static Date parseDate(String strFormat, String dateValue) {
        if (dateValue == null)
            return null;
        if (strFormat == null)
            strFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }
        return newDate;
    }

    public static Date parseDate(String dateValue) {
        return parseDate(null, dateValue);
    }

    /**
     * 取得指定日期N天后的日期
     *
     * @param date 指定日期
     * @param days 天数
     * @return 增加天数后的日期
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}
