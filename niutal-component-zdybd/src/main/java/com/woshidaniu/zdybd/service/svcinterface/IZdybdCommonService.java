package com.woshidaniu.zdybd.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.zdybd.dao.entities.DmmcModel;

/**
 * 
 * @系统名称: 新框架
 * @模块名称: 自定义表单
 * @类功能描述: 通用类接口
 * @作者： ligl
 * @时间： 2014-2-18 上午10:11:31
 * @版本： V1.0
 * @修改记录:
 */
public interface IZdybdCommonService extends BaseService<DmmcModel> {
	/**
	 * 
	 * @描述:取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:11:31
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getSjkqz(String szz) throws Exception;

	/**
	 * 
	 * @描述:取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:11:31
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getFfqz(String szz) throws Exception;

	/**
	 * 
	 * @描述:将1:男,2:女 转化成json格式
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:11:31
	 * @修改记录:
	 * @param dmmc
	 * @return String 返回类型
	 * @throws
	 */
	public String dmmcToJson(String dmmc) throws Exception;
	
	/**
	 * 
	 * <p>方法说明：获取省市县<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年8月15日下午8:38:22<p>
	 */
	public String getSsxJson() throws Exception;
}
