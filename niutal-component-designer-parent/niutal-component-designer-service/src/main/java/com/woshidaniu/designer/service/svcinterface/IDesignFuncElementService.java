package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;

/**
 * 
 *@类名称: IDesignFuncElementService.java
 *@类描述：功能页面自定义元素信息操作SERVICE接口:指定设计器生成的功能页面的元素信息，元素分基本html元素和js组件
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:55:52
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementService extends BaseService<DesignFuncElementModel>{

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
	public List<DesignFuncElementModel> getFuncElementList(String func_code,String opt_code);
	
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
	 *@描述：设置元素上绑定的元素关联实体对象数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20下午02:32:04
	 *@param elementModel
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean insertElementEntity(DesignFuncElementModel model);
	
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
	public boolean deleteElementEntity(DesignFuncElementModel model);
	
}
