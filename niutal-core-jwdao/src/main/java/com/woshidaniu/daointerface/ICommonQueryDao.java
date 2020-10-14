package com.woshidaniu.daointerface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.entities.CommonModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.CommonModel;

/**
 * 
 *@类名称		: ICommonQueryDao.java
 *@类描述		： 与业务无关的公共基础查询Dao接口
 *@创建人		：kangzhidong
 *@创建时间	：2016年4月20日 下午1:20:01
 *@修改人		：
 *@修改时间	：
 *@版本号		:v1.0
 */
@Component("commonQueryDao")
public interface ICommonQueryDao extends BaseDao<CommonModel> {

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

	public List<Map<String,String>> getResultList(@Param(value="tableName") String tableName,@Param(value="tabCol")String tabCol,@Param(value="tabColV")String tabColV);
	
	public List<BaseMap> getSelectList(@Param(value="tableName") String tableName,@Param(value="tabCol")String tabCol,@Param(value="tabColV")String tabColV);
	
	public List<BaseMap> getTableList(Map<String,Object> map);
	
	public List<BaseMap> getPagedSelectList(@Param(value="tableName") String tableName,@Param(value="tabCol")String tabCol,@Param(value="tabColV")String tabColV);
	
	public List<BaseMap> getPagedTableList(Map<String,Object> map);


}
