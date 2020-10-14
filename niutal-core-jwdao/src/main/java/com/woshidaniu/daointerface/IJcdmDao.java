package com.woshidaniu.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.JcdmModel;

public interface IJcdmDao  extends BaseDao<JcdmModel>{

	/**
	 * 获得单条记录, 返回BaseMap
	 * @param t
	 * @return
	 */
	public BaseMap getObject(JcdmModel model);
	
	/**
	 * @Description:停用/启用
	 * @param model
	 * @return int
	 * @throws
	 */
	public int handle(JcdmModel model);
	/**
	 * 删除记录
	 * @param t
	 * @return
	 */
	public int delete(JcdmModel model);
	/***
	 * 查询验证SQL
	 * @param model
	 * @return
	 */
	public List<Map<String,String>> getValidateList(JcdmModel model);
	/***
	 * 传入需要查询的SQL，返回List
	 * @param model.QuerySqlString QuerySql
	 * @return
	 */
	public List<HashMap<String, String>> getList(String QuerySql);
	/**
	 * 
	 *@描述： 根据传入格式返回相应格式的时间值
	 *@创建人:wjy
	 *@创建时间:2014-10-24下午03:07:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param dataFormat
	 *@return
	 */
	public String getNowDate(@Param(value="dataFormat")String dataFormat);
}
