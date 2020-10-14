package com.woshidaniu.designer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woshidaniu.basicutils.BlankUtils;

/**
 * 
 *@类名称:FuncSQLUtils.java
 *@类描述：功能SQL工具类
 *@创建人：kangzhidong
 *@创建时间：2015-5-15 下午03:56:40
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public abstract class FuncSQLUtils {
	
	private static StringBuffer buffer = new StringBuffer();
	
	/**
	 * 匹配如下格式的字符串
	 *  <#if kkbm_id?exists && kkbm_id?length gt 0 >
	 *  	and t2.kkbm_id = <#noparse>#{paramMap.kkbm_id}</#noparse>
	 *  </#if>
	 */
	private static Pattern pattern_term = Pattern.compile("(((<#if)([^\\(\\)]*?)(>))([^\\(\\)]*?)(</#if>))+");
	
	public static boolean isHasTerm(String data_sql,String field_name){
		pattern_term = Pattern.compile("((<#if[\\s]+"+field_name+"\\?exists)([^\\(\\)]*?)(</#if>))+");
		// 匹配每段数据范围规则内容中的func
		Matcher matcher = pattern_term.matcher(data_sql);
		if (matcher.find()) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 *@描述：移除SQL中匹配的条件
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15下午04:20:03
	 *@param data_sql
	 *@param field_name
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String removeTerm(String data_sql,String field_name){
		pattern_term = Pattern.compile("((<#if[\\s]+"+field_name+"\\?exists)([^\\(\\)]*?)(</#if>))+");
		// 匹配每段数据范围规则内容中的func
		Matcher matcher = pattern_term.matcher(data_sql);
		// 查找匹配的{func()}片段
		while (matcher.find()) {
			// 获取匹配的{}的内容
			String full_segment = matcher.group(1);
			// 取得{}内容开始结束位置
			int begain = data_sql.indexOf(full_segment);
			int end = begain + full_segment.length();
			//剪去匹配的部分
			data_sql = data_sql.substring(0, begain) + data_sql.substring(end);
		}
		return data_sql;
	}
	
	
	public static String buildTerm(String field_name,String field_filtertype,String field_alias){
		field_filtertype = field_filtertype == null ? "1" : field_filtertype;
		buffer.delete(0, buffer.length());
		//换行
		buffer.append("\r\n");
		buffer.append(" <#if ").append(field_name).append("?exists && ").append(field_name).append("?length gt 0 > ");
		buffer.append("\r\n");
		buffer.append(" and ").append(!BlankUtils.isBlank(field_alias)?(field_alias+"."+field_name):field_name);
		//筛选类型：1：等值筛选;2:全模糊筛选;3:前缀模糊筛选;4:后缀模糊筛选;
		if("2".equals(field_filtertype)){
			buffer.append("  like '%'||<#noparse>#{paramMap.").append(field_name).append("}</#noparse>||'%' ");
		}else if("3".equals(field_filtertype)){
			buffer.append("  like '%'||<#noparse>#{paramMap.").append(field_name).append("}</#noparse> ");
		}else if("4".equals(field_filtertype)){
			buffer.append("  like <#noparse>#{paramMap.").append(field_name).append("}</#noparse>||'%' ");
		}else{
			buffer.append("  = <#noparse>#{paramMap.").append(field_name).append("}</#noparse> ");
		}
		buffer.append("\r\n");
		buffer.append(" </#if>");
		return buffer.toString();
	}
	
}
