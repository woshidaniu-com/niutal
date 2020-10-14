/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.comm;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;

/**
 * 导入综合配置
 */
public class ImportConfig {
	//是主键
	public static String _SFZJ_YES = "1";
	//非主键
	public static String _SFZJ_NO = "0"; 
	//唯一
	public static String _SFWY_YES = "1";
	//非唯一
	public static String _SFWY_NO = "0";
	//校验方式切换阀值
	public static int Thresholds = 50;
	public static int _Thresholds = 300;
	// 插入方式
	public static String _CRFS_INSERT = "1";
	// 修改方式
	public static String _CRFS_UPDATE = "2";
	// 插入并修改方式
	public static String _CRFS_INSERT_UPDATE = "3";
	// 错误提示，插入条数
	public static Integer _CWTS_INSERT = -1;
	// 错误提示，更新条数
	public static Integer _CWTS_UPDATE = -2;
	// 信息提示，是否验证通过
	public static Integer _CWTS_SFYZTG = -3;
	/**
	 * 获取主键缓存key
	 * @param cla 验证（生成）主键类
	 * @param drlpzModel 导入列配置
	 * @return
	 */
	public static String getPrimaryCacheKey(Class<?> cla, DrlpzModel drlpzModel) {
		return cla.toString() + drlpzModel.getDrl() + "Primary";
	}
}
