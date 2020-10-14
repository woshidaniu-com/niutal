package com.woshidaniu.workflow.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create on 2013-6-24 下午03:34:27
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class ParamUtil {
	private static final String	EMEPTY			= StringUtils.EMPTY;
	private static final String	ERROR			= "ERROR";
	public static final String	EN_CODE_UTF8	= "UTF-8";
	public static final String	EN_CODE_GBK		= "GBK";
	private static Logger		log				= LoggerFactory.getLogger(ParamUtil.class);

	/**
	 * 对于中文参数转码 -- 加碼
	 * 
	 * @param param
	 *            ：参数
	 * @return String
	 */
	public static String encode(String param, String... encs) {
		if (StringUtils.isBlank(param)) {
			return EMEPTY;
		}
		try {
			if (CollectionUtil.isArrayNull(encs)) {
				return URLEncoder.encode(param, EN_CODE_UTF8);
			} else {
				String enc = encs[0];
				return URLEncoder.encode(param, enc);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public static String encodeDouble(String param, String... encs) {
		return encode(encode(param, encs));
	}

	/**
	 * 对于中文参数转码 -- 解码
	 * 
	 * @param param
	 *            ：参数
	 * @return String
	 */
	public static String decode(String param, String... encs) {
		if (StringUtils.isBlank(param)) {
			return EMEPTY;
		}
		try {
			if (CollectionUtil.isArrayNull(encs)) {
				return URLDecoder.decode(param, EN_CODE_UTF8);
			} else {
				String enc = encs[0];
				return URLDecoder.decode(param, enc);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public static String decodeDouble(String param, String... encs) {
		return decode(decode(param, encs));
	}

	/**
	 * 将普通的字符串型转成LIST集合类型
	 * 
	 * @param id
	 *            ："afaf,afaf"
	 * @return List<String>
	 */
	public static List<String> getListIds(String ids) {
		if (StringUtils.isBlank(ids)) {
			return new ArrayList<String>();
		}
		String[] split = StringUtils.split(ids, ",");
		return Arrays.asList(split);
	}

}
