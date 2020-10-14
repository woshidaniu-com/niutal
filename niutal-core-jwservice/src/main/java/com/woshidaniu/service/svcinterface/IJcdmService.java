package com.woshidaniu.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JcdmModel;

public interface IJcdmService extends BaseService<JcdmModel> {

	/**
	 * @Description:获得单条记录, 返回BaseMap
	 * @param model
	 * @return BaseMap
	 * @throws
	 */
	public BaseMap getObject(JcdmModel model);
	
	/**
	 * @Description:停用/启用
	 * @param model
	 * @return boolean
	 * @throws
	 */
	public boolean handle(JcdmModel model);
	/**
	 * 删除记录
	 * @param t
	 * @return
	 */
	public boolean delete(JcdmModel model);
	/***
	 * 查询验证SQL
	 * @param model
	 * @return
	 */
	public List<Map<String,String>> getValidateList(JcdmModel model);
	/***
	 * 传入需要查询的SQL，返回List
	 * @param model.QuerySql
	 * @return
	 */
	public List<HashMap<String, String>> getList(String QuerySql);
	/**
	 * 
	 *@描述：根据传入格式返回相应格式的时间值
	 *@创建人:wjy
	 *@创建时间:2014-10-24下午03:09:41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param dataFormat
	 *@return
	 */
	public String getNowDate(String dataFormat);
}
