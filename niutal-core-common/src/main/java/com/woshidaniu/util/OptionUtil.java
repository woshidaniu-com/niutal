/**
 * @部门:学工产品事业部
 * @日期：2013-4-18 下午04:44:40 
 */  
package com.woshidaniu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：固定选项统一管理<br>
 *   <i>已弃用该类，固定选项采用基础数据表管理<i>
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月17日上午9:46:04
 */
@Deprecated
public class OptionUtil {

	public final static String ISNOT = "isNot";//是否
	public final static String QYTY = "qyty";//启用停用
	public final static String JFZT = "jfzt";//缴费状态
	public final static String SHZT = "shzt";//审核状态
	public final static String KGZT = "kgzt";//开关状态
	public final static String LXDSHZT = "lxdshzt";//离校单审核状态
	public final static String LXDYSHZT = "lxdyshzt";//离校单已审核状态
	public final static String ZXZT = "zxzt";//依赖数据自动审核执行状态
	public final static String SJZT = "sjzt";//依赖数据审核状态
	public final static String XSLXDHJKSHZT = "xslxdhjkshzt";//学生离校单环节可审核状态
	public final static String LXLCSHZT = "lxlcshzt";  //证书发放离校流程审核状态
	public final static String ZSFFZT = "zsffzt";  //证书发放状态
	public final static String HFZT = "hfzt"; //回复状态
	
	private String[][] values = null;
	private final static String DM = "dm";
	private final static String MC = "mc";
	
	public static String GJCX_CXBZ = "gjcx_default_cache";
	public static String[] GJCX_OPTIONS = {};
	public static String GJCX_HTML = "HTML";//高级查询-全部html
	public static String GJCX_NOMORE = "NOMORE";//高级查询-html不包含more
	public static String GJCX_MORE = "MORE";//高级查询-更多html
	
	private Log logger = LogFactory.getLog(OptionUtil.class);
	
	/**
	 * 
	 * <p>方法说明：按类型获取选项集合<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午9:45:33<p>
	 * @param type 类型
	 * @return List<HashMap<String,String>>
	 */
	public List<HashMap<String,String>> getOptions(String type){
		
		if (ISNOT.equalsIgnoreCase(type)) {
			values = new String[][] { { "1", "是" }, { "0", "否" } };
		} else if (QYTY.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "启用" }, { "1", "停用" }};
		} else if (JFZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "未缴费" }, { "1", "已缴费" }};
		} else if (SHZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "未审核" }, { "1", "通过" }, { "2", "未通过" }};
		} else if (KGZT.equalsIgnoreCase(type)) {
			values = new String[][] {  { "1", "开启" } ,{ "0", "关闭" }};
		} else if (LXDSHZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "未审核" }, { "1", "通过" },{ "2", "审核中" }, { "-1", "不通过" }};
		} else if (ZXZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "未执行" }, { "1", "已执行" }};
		} else if (SJZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "不通过" }, { "1", "通过" }};
		} else if (LXDYSHZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "-1", "不通过" }, { "1", "通过" }};
		} else if (XSLXDHJKSHZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "不可审核", "不可审核" }, { "可审核", "可审核" }};
		} else if (LXLCSHZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "1", "审核通过" }, { "-1", "审核不通过" }};
		} else if (ZSFFZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "未发放", "未发放" }, { "部分发放", "部分发放" },{"已发放","已发放"}};
		} else if (HFZT.equalsIgnoreCase(type)) {
			values = new String[][] { { "0", "未回复" }, { "1", "已回复" }};
		} 

		return creatList();
	}
	
	//创建列表项
	private List<HashMap<String,String>> creatList(){
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		if (values == null){
			logger.error("未定义该类型选项！");
			throw new NullPointerException();
		}
		
		for (int i = 0 ; i < values.length ; i++){
			
			HashMap<String,String> map = new HashMap<String, String>();
			
			map.put(DM, values[i][0]);
			map.put(MC, values[i][1]);
			
			list.add(map);
		}
		
		return list;
	}
	
}
