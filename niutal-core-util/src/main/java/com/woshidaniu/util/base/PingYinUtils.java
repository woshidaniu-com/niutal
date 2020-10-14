package com.woshidaniu.util.base;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：中文转拼音相关工具类
 * <p>
 * @deprecated
 * @see com.woshidaniu.format.pinyin4j.PingYinUtils
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月8日下午4:09:35
 */
@Deprecated
public final class PingYinUtils {

	private PingYinUtils(){
		super();
	}
	
	
	/**
	 * 
	 * <p>方法说明：汉字转拼音<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:35:12<p>
	 * @param str 需要转换的字符串 
	 * @return 拼音字符串
	 */
	public static String converterToFUllSpell(String str) {
		return converterToFUllSpell(str, HanyuPinyinCaseType.LOWERCASE,HanyuPinyinToneType.WITHOUT_TONE);
	}

	
	/**
	 * 
	 * <p>方法说明：汉字转拼音<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:34:35<p>
	 * @param str 需要转换的字符串 
	 * @param caseType 大小写 {@link net.sourceforge.pinyin4j.format.HanyuPinyinCaseType}
	 * @return 拼音字符串
	 */
	public static String converterToFUllSpell(String str,HanyuPinyinCaseType caseType) {
		return converterToFUllSpell(str, caseType,HanyuPinyinToneType.WITHOUT_TONE);
	}

	
	/**
	 * 
	 * <p>方法说明：汉字转拼音<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:12:24<p>
	 * @param src 需要转换的字符串 
	 * @param caseType 大小写 {@link net.sourceforge.pinyin4j.format.HanyuPinyinCaseType}
	 * @param toneType 声调 {@link net.sourceforge.pinyin4j.format.HanyuPinyinToneType}
	 * @return 拼音字符串
	 */
	public static String converterToFUllSpell(String src,HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType) {
		char[] t1 = src.toCharArray();
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(caseType);// 大写||小写
		format.setToneType(toneType);// 第几声
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0 , j = t1.length; i < j; i++) {
				// 判断是否为汉字字符函数
				if (t1[i] >= 0x4e00 && t1[i] <= 0x9fbb) {
					sb.append(PinyinHelper.toHanyuPinyinStringArray(t1[i], format)[0]);
					sb.append(" ");
				} else {
					sb.append(Character.toString(t1[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return sb.toString();
	}

	
	/**
	 * 
	 * <p>方法说明：汉字转换为汉语拼音首字母（小写），英文字符不变<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:16:00<p>
	 * @param chines 中文字符串
	 * @return 拼音简写字符串
	 */
	public static String converterToFirstSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		// 转化为字符
		char[] nameChar = chines.toCharArray();
		// 汉语拼音格式输出类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置,大小写,音标方式等
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] hanyuPinyinStringArray = null;
		for (int i = 0; i < nameChar.length; i++) {
			// 如果是中文
			if (nameChar[i] >= 0x4e00 && nameChar[i] <= 0x9fbb) {
				try {
					hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if(hanyuPinyinStringArray != null){
						pinyinName.append(hanyuPinyinStringArray[0].charAt(0));
					}else{
						pinyinName.append(nameChar[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
					pinyinName.append(nameChar[i]);
				}
			} else {// 为英文字符
				pinyinName.append(nameChar[i]);
			}
		}
		return pinyinName.toString();
	}

	
	/**
	 *  
	 * <p>方法说明：汉字转换为汉语拼音首字母（大写），英文字符不变<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:38:23<p>
	 * @param chines 待转换字符串
	 * @return 拼音首字母（大写）
	 */
	public static String converterToSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] >= 0x4e00 && nameChar[i] <= 0x9fbb) {
				try {
					String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if(hanyuPinyinStringArray != null){
						pinyinName.append(hanyuPinyinStringArray[0].charAt(0));
					}else{
						pinyinName.append(nameChar[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
					pinyinName.append(nameChar[i]);
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
		}
		return pinyinName.toString();
	}

	
	/**
	 * 
	 * <p>方法说明：将字符串转换成拼音数组<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午5:56:16<p>
	 * @param src 中文字符串
	 * @return 拼音数组
	 */
	public static String[] stringToPinyin(String src) {
		return stringToPinyin(src, false, null);
	}

	
	/**
	 * 
	 * <p>方法说明：将字符串转换成拼音数组<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午7:09:42<p>
	 * @param src 待转换字符
	 * @param separator 多音字拼音之间的分隔符
	 * @return 拼音数组
	 */
	public static String[] stringToPinyin(String src, String separator) {
		return stringToPinyin(src, true, separator);
	}

	
	/**
	 * 
	 * <p>方法说明：将字符串转换成拼音数组<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午7:10:18<p>
	 * @param src 待转换字符
	 * @param isPolyphone 是否查出多音字的所有拼音
	 * @param separator 多音字拼音之间的分隔符
	 * @return 拼音数组
	 */
	public static String[] stringToPinyin(String src, boolean isPolyphone,
			String separator) {
		// 判断字符串是否为空
		if ("".equals(src) || null == src) {
			return null;
		}
		char[] srcChar = src.toCharArray();
		int srcCount = srcChar.length;
		String[] srcStr = new String[srcCount];

		for (int i = 0; i < srcCount; i++) {
			if (srcChar[i] >= 0x4e00 && srcChar[i] <= 0x9fbb) {
				srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
			} else {
				srcStr[i] = String.valueOf(srcChar[i]);
			}
			
		}
		return srcStr;
	}

	
	
	/**
	 * 
	 * <p>方法说明：将单个字符转换成拼音<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月12日下午7:10:55<p>
	 * @param src 待转换字符
	 * @param isPolyphone 是否查出多音字
	 * @param separator 多音字分隔符
	 * @return 拼音字符
	 */
	public static String charToPinyin(char src, boolean isPolyphone,
			String separator) {
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		StringBuffer tempPinying = new StringBuffer();
		// 如果是中文
		try {
			// 转换得出结果
			String[] strs = PinyinHelper.toHanyuPinyinStringArray(src,defaultFormat);
			// 是否查出多音字，默认是查出多音字的第一个字符
			if (isPolyphone && null != separator) {
				for (int i = 0; i < strs.length; i++) {
					tempPinying.append(strs[i]);
					if (strs.length != (i + 1)) {
						// 多音字之间用特殊符号间隔起来
						tempPinying.append(separator);
					}
				}
			} else {
				tempPinying.append(strs[0]);
			}

		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		return tempPinying.toString();
	}


}