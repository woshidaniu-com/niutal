package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;

/**
 * 
 *@类名称: IDesignFuncElementDao.java
 *@类描述：功能页面自定义元素信息操作DAO接口:指定设计器生成的功能页面的元素信息，元素分基本html元素和js组件
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:55:03
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementDao extends BaseDao<DesignFuncElementModel>{
	
	/**
	 * 
	 *@描述：根据 【功能代码+操作代码】 查询对应的功能页面元素信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午03:04:40
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementModel> getFuncElementList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
	/**
	 * 
	 *@描述：查询元素可关联的其他元素
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20上午09:49:27
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementModel> getRelatedElementList(DesignFuncElementModel model);
	
	/**
	 * 
	 *@描述：删除元素上绑定的元素关联实体对象数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20下午02:32:04
	 *@param elementModel
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int deleteElementEntity(DesignFuncElementModel elementModel);
	
	/**
	 * 
	 *@描述：更新【功能页面自定义元素】类型信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午03:51:00
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int updateElementType(DesignFuncElementModel model);
	 
}
