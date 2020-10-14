package com.woshidaniu.util.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：字符串工具类
 *   <br>
 * <p>
 * @deprecated
 * @see com.woshidaniu.basicutils.StringUtils
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月8日上午10:32:09
 */
@Deprecated
public final class StringUtil extends com.woshidaniu.basicutils.StringUtils {
	
	private StringUtil() {
		super();
	}


	
	/**
	 * 
	 * <p>方法说明：判断 Null 或 空字符串<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月8日上午10:19:46<p>
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return str == null || str.isEmpty();
	}



	/**
	 * 
	 * <p>方法说明：去除最后一个字符<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月8日上午10:23:50<p>
	 * @param str
	 * @return String
	 */
	public static String removeLast(String str) {
		if (isNull(str)) {
			return str;
		}
		return str.substring(0, str.length() - 1);

	}

	
	

	/**
	 * 
	 * <p>方法说明：取拼音首字母<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月8日上午10:28:12<p>
	 * @param str 源字符串
	 * @return 拼音首字母组合
	 */
	public static String getFirstPinYin(String str) {

		if (isEmpty(str)) {
			return null;
		}

		try {
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			// 输出设置，大小写，音标方式等
			defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(str
					.trim().toCharArray()[0], defaultFormat);
			return pinyinArr != null ? pinyinArr[0].substring(0, 1) : null;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return null;
		}
	}

	
	
	/**
	 * 
	 * <p>方法说明：字符串为空的替代方案<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月8日上午10:26:22<p>
	 * @param str 源字符串
	 * @param defaultStr 默认字符串
	 * @return String
	 */
	public static  String getSafeStr(String str,String defaultStr){
		return BlankUtil.isBlank(str) ? defaultStr : str;
	}
	
	
	/**
	 * 
	 * <p>方法说明：数字转中文<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月30日下午1:41:05<p>
	 * @param str 输入字符串
	 * @return 输出字符串
	 */
	public static String numToCnByString(String str) {
		StringBuilder sb = new StringBuilder();
		if(str != null){
			char[] charr = str.trim().toCharArray();
			for(char ch : charr){
				sb.append(numToCnByChar(ch));
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * <p>方法说明：数字转中文<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月30日下午1:41:05<p>
	 * @param ch 输入字符
	 * @return 输出字符串
	 */
	public static String numToCnByChar(char ch) {
		String returnch = null;
		switch (ch) {
		case '0':
			returnch = "〇";
			break;
		case '1':
			returnch = "一";
			break;
		case '2':
			returnch = "二";
			break;
		case '3':
			returnch = "三";
			break;
		case '4':
			returnch = "四";
			break;
		case '5':
			returnch = "五";
			break;
		case '6':
			returnch = "六";
			break;
		case '7':
			returnch = "七";
			break;
		case '8':
			returnch = "八";
			break;
		case '9':
			returnch = "九";
			break;
		default:
			returnch = String.valueOf(ch);
			break;
		}
		return returnch;
	}
	
	 /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * Trims tokens and omits empty tokens.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     * <p/>
     * <p>Copied from the Spring Framework while retaining all license, copyright and author information.
     *
     * @param str        the String to tokenize
     * @param delimiters the delimiter characters, assembled as String
     *                   (each of those characters is individually considered as delimiter).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim()
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }
    
    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     * <p/>
     * <p>Copied from the Spring Framework while retaining all license, copyright and author information.
     *
     * @param str               the String to tokenize
     * @param delimiters        the delimiter characters, assembled as String
     *                          (each of those characters is individually considered as delimiter)
     * @param trimTokens        trim the tokens via String's <code>trim</code>
     * @param ignoreEmptyTokens omit empty tokens from the result array
     *                          (only applies to tokens that are empty after trimming; StringTokenizer
     *                          will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens (<code>null</code> if the input String
     *         was <code>null</code>)
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim()
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String[] toStringArray(Collection collection) {
        if (collection == null) {
            return null;
        }
        return (String[]) collection.toArray(new String[collection.size()]);
    }
    
    /**
	 * 
	 *@描述：解析学生标记
	 *@创建人:huyy
	 *@创建时间:2015-6-19下午05:20:02
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xsbj
	 *@return
	 */
	public static String JxXsbj(int xsbj){
    	String returnStr ="";
    	if(xsbj == 0){
    		return "";
    	}
    	int i =0 ;
    	double LoopCount =  Math.round(Math.log(xsbj)/Math.log(2));
    	while(i <= LoopCount){
    		if(((int)Math.pow(2,i)&xsbj)!=0){
    			if(returnStr.equals("")){
    				returnStr = String.valueOf((int)Math.pow(2, i));
    			}else{
    				returnStr +=","+String.valueOf((int)Math.pow(2, i));
    			}
    		}
    		i+=1;
    	}
    	return returnStr;
    }
	
	/**
	 * 字串左方填充特定字符到指定长度
	 * 
	 * @param str
	 * @return
	 */
	public static String leftPadding(String src,int num,String chr) {
		if(src!=null && src.length()<num){
			for(int i=0; i<=num-src.length(); i++){
				src = chr + src;
			}
		}else{
			for(int i=0; i<=num; i++){
				src = chr + src;
			}
		}
		return src;
	}
	
	/**
	 * 字串右方填充特定字符到指定长度
	 * 
	 * @param str
	 * @return
	 */
	public static String rightPadding(String src,int num,String chr) {
		StringBuilder sbu = new StringBuilder(src);
		if(src!=null && src.length()<num){
			for(int i=0; i<=num-src.length(); i++){
				sbu.append(chr);
			}
		}else{
			for(int i=0; i<=num; i++){
				sbu.append(chr);
			}
		}
		return sbu.toString();
	}
	
	 /**
     * 比较2个字符串，忽略大小写
     * 相等 return true 
     * @param str1, str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2){
    	if(str1 == null && str2 != null){return false;}
    	if(str2 == null && str1 != null){return false;}
    	if(str1 == null && str2 == null){return false;}
    	return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
    }
    
}