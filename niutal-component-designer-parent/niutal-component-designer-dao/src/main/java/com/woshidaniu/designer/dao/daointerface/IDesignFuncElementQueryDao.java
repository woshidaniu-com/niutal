package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.designer.dao.entities.DesignAutoCompleteFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;

/**
 * 
 *@类名称: IDesignQueryFieldDao.java
 *@类描述：功能页面:对应自定义字段设计操作dao:指定设计器生成的功能页面的页面字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午02:53:49
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementQueryDao extends BaseDao<DesignFuncElementQueryModel>{
	
	
	/**
	 * 
	 *@描述：根据组件model查询查询条件列名称
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-14下午04:30:34
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<String> getQueryFieldNameList(DesignFuncElementQueryModel model);
	
	
	/**
	 * 
	 *@描述：根据【功能代码+操作代码 】查询【自定义查询条件】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午10:17:31
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementQueryModel> getFuncElementQueryList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
	/**
	 * 
	 *@描述：查询功能代码+操作代码对应的自定义字段
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午09:16:13
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementQueryFieldModel> getFuncElementQueryFieldList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);

	/**
	 * 
	 *@描述：根据查询区域对象查询该区域关联查询条件集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-14上午11:53:17
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementQueryFieldModel> getFuncElementQueryFieldList2(DesignFuncElementQueryModel model);

	/**
	 * 
	 *@描述：根据【功能代码+操作代码 】查询查询条件对应的自定义自动完成字段
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-24下午04:29:03
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignAutoCompleteFieldModel> getDesignAutoFieldList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
	
	/**
	 * 
	 *@描述：根据SQL查询数据结果集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午11:48:14
	 *@param querySQL
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFieldDataListByScope(@Param("querySQL")String querySQL);

	/**
	 * 
	 *@描述：增加【查询条件字段】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15下午03:17:17
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int insertQueryField(DesignFuncElementQueryFieldModel model);
	
	/**
	 * 
	 *@描述：删除【查询条件字段】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15下午03:17:17
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int deleteQueryField(DesignFuncElementQueryFieldModel model);
	
	/**
	 * 
	 *@描述：更新【查询条件字段】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15下午03:17:17
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int updateQueryField(DesignFuncElementQueryFieldModel model);
	
}
