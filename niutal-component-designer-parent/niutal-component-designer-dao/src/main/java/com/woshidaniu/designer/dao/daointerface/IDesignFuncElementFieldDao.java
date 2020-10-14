package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignAutoCompleteFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;

/**
 * 
 *@类名称: IDesignFuncElementFieldDao.java
 *@类描述：功能页面自定义元素关联字段信息操作DAO接口:指定设计器生成的功能页面元素的字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午03:15:03
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementFieldDao extends BaseDao<DesignFuncElementFieldModel>{

	/**
	 * 
	 *@描述：根据 【功能代码+操作代码】 查询对应的功能页面自定义元素关联字段信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午03:04:40
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementFieldModel> getFuncElementFieldList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
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

}
