package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;

/**
 * 
 *@类名称: IDesignToolbarButtonDao.java
 *@类描述：功能页面:对应自定义操作按钮设置DAO
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午08:50:57
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignToolbarButtonDao extends BaseDao<DesignToolbarButtonModel>{

	/**
	 * 
	 *@描述：功能代码+操作代码查询自定义功能的操作安娜
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午09:16:13
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignToolbarButtonModel> getToolbarButtonList(DesignToolbarButtonModel model);


	/**
	 * 
	 *@描述：根据功能代码查询该功能的报表操作按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午02:45:22
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignToolbarButtonModel> getReportButtonList(DesignToolbarButtonModel model);
	
}
