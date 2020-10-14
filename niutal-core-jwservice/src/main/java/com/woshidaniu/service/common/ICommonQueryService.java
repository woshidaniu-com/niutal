package com.woshidaniu.service.common;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.query.BaseMap;

/**
 * 
 *@类名称		: ICommonQueryService.java
 *@类描述		：  与业务无关的公共基础查询service接口
 *@创建人		： kangzhidong
 *@创建时间	： 2016年4月20日 上午10:54:15
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface ICommonQueryService {
	
	/**
	 * 
	 * @description: 生成guid
	 * @author : kangzhidong
	 * @date : 2014-4-3
	 * @time : 下午06:49:49 
	 * @return
	 */
	public String getSysGuid();
	
	/**
	 * 
	 *@描述：返回数据库当前的时间。格式为：yyyy-MM-dd HH24:mi:ss
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-26下午07:28:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String getDatabaseTime();
	
	/**
	 * 
	 *@描述：根据传入格式返回数据库相应格式的时间值
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-26下午07:23:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param dataFormat : (如：yyyy-MM-dd HH24:mi:ss)
	 *@return
	 */
	public String getDatabaseTime(String dataFormat);
	
	public List<Map<String,String>> getResultList(String tableName,String tabCol,String tabColV);
	
	/**
	 * 
	 *@描述		：  根据指定的表名称+列信息查询List，注：拼装后查询语句为： select ${tabCol},${tabColV} from ${tableName} order by ${tabCol}
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午1:27:30
	 *@param tableName 表名称
	 *@param key       表中字段 ，最终按照此字段排序
	 *@param value     表中字段
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getTableList(String tableName,String tabCol,String tabColV);
	
	/**
	 * 
	 *@描述		：  根据指定的表名称+列信息查询List，注：拼装后查询语句为： select ${columns} from ${tableName} order by ${sort} ${order}
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午1:27:30
	 *@param tableName	表名称
	 *@param columns	查询列
	 *@param sort		排序字段
	 *@param order		排序方式 ： asc,desc
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getTableList(String tableName,String[] columns,String sort,String order);

	/**
	 * 
	 *@描述		：  根据指定的表名称+列信息分页查询List，注：拼装后查询语句为： select ${tabCol},${tabColV} from ${tableName} order by ${key}
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午1:27:30
	 *@param tableName 表名称
	 *@param key       表中字段 ，最终按照此字段排序
	 *@param value     表中字段
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getPagedTableList(String tableName,String tabCol,String tabColV);
	
	/**
	 * 
	 *@描述		： 根据指定的表名称+列信息分页查询List，注：拼装后查询语句为： select ${columns} from ${tableName} order by ${sort} ${order}
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午1:30:07
	 *@param tableName	表名称
	 *@param columns	查询列
	 *@param sort		排序字段
	 *@param order		排序方式 ： asc,desc
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getPagedTableList(String tableName,String[] columns,String sort,String order);
	
	
	/**
	 * 
	 *@描述		：获取允许的邮箱后缀集合
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 21, 201612:03:57 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getEmailPostfixList();
	
}
