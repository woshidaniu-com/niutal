/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author 		：康康（1571）
 * @description	： 惰性创建正则表达式并且带缓存的工厂
 * 其实，在HashMap中查找一个对象的时间远远小于编译正则表达式的时间，这就是为什么这个类要这样写的原因
 */
public class PatternFactory {
	
	private static final Map<String,Pattern> cache = new HashMap<String,Pattern>();
	
	private static final String CONFIG_PATTERN_KEY_EMAIL = "niutal.dr.formatter.field.pattern.email";
	private static final String CONFIG_PATTERN_KEY_MOBILE = "niutal.dr.formatter.field.pattern.mobile";
	private static final String CONFIG_PATTERN_KEY_TEL = "niutal.dr.formatter.field.pattern.tel";
	private static final String CONFIG_PATTERN_KEY_ZIP = "niutal.dr.formatter.field.pattern.zip";
	
	private static String CONFIG_PATTERN_VAL_EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	
	/*2019年1月16日已知
	    中国电信号段
	        133,149,153,173,174,177,180,181,189,199
	    中国联通号段
	        130,131,132,145,146,155,156,166,175,176,185,186
	    中国移动号段
	        134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198
	    上网卡专属号段（用于上网和收发短信，不能打电话）
	        如中国联通的是145
	    虚拟运营商
	        电信：1700,1701,1702
	        移动：1703,1705,1706
	        联通：1704,1707,1708,1709,171
	    卫星通信： 1349　　　　　未知号段：141、142、143、144、154
    */
	private static String CONFIG_PATTERN_VAL_MOBILE = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
	private static String CONFIG_PATTERN_VAL_TEL = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,15}$)";
	private static String CONFIG_PATTERN_VAL_ZIP = "[1-9]\\d{5}";
	
	private static Pattern PATTERN_EMIAL;
	private static Pattern PATTERN_MOBILE;
	private static Pattern PATTERN_TEL;
	private static Pattern PATTERN_ZIP;
	
	static {
		String val = MessageUtil.getText(CONFIG_PATTERN_KEY_EMAIL);
		CONFIG_PATTERN_VAL_EMAIL = StringUtils.isNotEmpty(val) ? val : CONFIG_PATTERN_VAL_EMAIL;
	}
	static {
		String val = MessageUtil.getText(CONFIG_PATTERN_KEY_MOBILE);
		CONFIG_PATTERN_VAL_MOBILE = StringUtils.isNotEmpty(val) ? val : CONFIG_PATTERN_VAL_MOBILE;
	}
	static {
		String val = MessageUtil.getText(CONFIG_PATTERN_KEY_TEL);
		CONFIG_PATTERN_VAL_TEL = StringUtils.isNotEmpty(val) ? val : CONFIG_PATTERN_VAL_TEL;
	}
	static {
		String val = MessageUtil.getText(CONFIG_PATTERN_KEY_ZIP);
		CONFIG_PATTERN_VAL_ZIP = StringUtils.isNotEmpty(val) ? val : CONFIG_PATTERN_VAL_ZIP;
	}
	static {
		PATTERN_EMIAL = compile(CONFIG_PATTERN_VAL_EMAIL);
		PATTERN_MOBILE = compile(CONFIG_PATTERN_VAL_MOBILE);
		PATTERN_TEL = compile(CONFIG_PATTERN_VAL_TEL);
		PATTERN_ZIP = compile(CONFIG_PATTERN_VAL_ZIP);
	}
	
	public static Pattern getEmailPattern() {
		return PATTERN_EMIAL;
	}
	public static Pattern getMobilePattern() {
		return PATTERN_MOBILE;
	}
	public static Pattern getTelPattern() {
		return PATTERN_TEL;
	}
	public static Pattern getZipPattern() {
		return PATTERN_ZIP;
	}
	/**
	 * @description	：编译正则表达式 
	 * @param regex
	 * @return
	 */
	public synchronized static Pattern compile(String regex) {
		Pattern pattern = cache.get(regex);
		if(pattern == null) {
			try {
				pattern = Pattern.compile(regex);				
				cache.put(regex, pattern);
			}catch (Exception e) {
				throw new IllegalArgumentException("正则表达式参数["+ regex +"]编译发生异常!!!",e);
			}
		}
		return pattern;
	}
	
	/**
	 * @description	： 编译正则表达式
	 * @param regex
	 * @param flags
	 * @return
	 */
    public static synchronized Pattern compile(String regex, int flags) {
    	//TODO 优化
    	Pattern pattern = Pattern.compile(regex);
    	return pattern;
    }
}
