package com.woshidaniu.designer.service.svcinterface;

import java.io.File;
import java.util.List;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignReportModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;

/**
 * 
 *@类名称: IDesignFuncService.java
 *@类描述：功能页面:功能设计操作SERVICE接口：为指定功能代码+操作代码维护操作功能数据
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:40:36
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncService extends BaseService<DesignFuncModel>{

	/**
	 * 
	 *@描述：获取最顶级功能模块列表集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午04:43:52
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
    public List<PairModel> getTopFuncList();
    
	/**
	 * 
	 *@描述：根据 功能代码+操作代码 查询 对应的功能设计信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-28上午11:29:26
	 *@param funcCode
	 *@param optCode
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncModel getFuncModel(String func_code,String opt_code);
    
    /**
     * 
     *@描述：查询组织好所属关系的功能菜单和按钮结果集合
     *@创建人:kangzhidong
     *@创建时间:2015-5-7下午07:34:15
     *@return
     *@修改人:
     *@修改时间:
     *@修改描述:
     */
    public DesignFuncMenuModel getFuncMenuModel(String func_code);
    
    /**
     * 
     *@描述：查询系统功能菜单信息
     *@创建人:kangzhidong
     *@创建时间:2015-5-23下午01:27:23
     *@param func_code
     *@return
     *@修改人:
     *@修改时间:
     *@修改描述:
     */
    public DesignFuncMenuModel getSystemFuncModel(String func_code);
    
    /**
	 * 
	 *@描述：查询功能菜单上绑定的 自定义功能信息 
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-22下午03:08:08
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncMenuModel getFuncMenuModel(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：添加【自定义菜单】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:57
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean insertMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：添加【自定义菜单及自定义功能】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:57
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean insertFuncMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：删除【自定义菜单及自定义功能】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:19
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean deleteFuncMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：更新【自定义菜单】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:33
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean updateMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：更新【自定义菜单及自定义功能】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:33
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean updateFuncMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：根据功能模块代码查询对应的功能操作按钮信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-25下午07:07:28
	 *@param func_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncMenuButtonModel> getSystemFuncOptList(String func_code);
	
	/**
	 * 
	 *@描述：根据功能模块代码+操作代码得到功能模块操作按钮信息 
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-23下午01:36:41
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncMenuButtonModel getSystemFuncOptModel(String func_code,String opt_code);
	
	/**
	 * 
	 *@描述：查询功能按钮信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-22下午03:08:56
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncMenuButtonModel getFuncOptModel(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：添加【功能操作按钮】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:57
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean insertFuncOpt(DesignFuncMenuButtonModel model);

	/**
	 * 
	 *@描述：删除【功能操作按钮】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:19
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean deleteFuncOpt(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：更新【功能操作按钮】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:51:33
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean updateFuncOpt(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：查询功能按钮上绑定的自定义功能信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-22下午03:08:56
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncMenuButtonModel getFuncOptLinkModel(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：根据功能代码获取该功能的操作按钮相应的自定义功能信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-2下午01:46:20
	 *@param func_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncMenuButtonModel> getFuncOptLinkList(String func_code);
	
	/**
	 * 
	 *@描述：添加【功能操作按钮】对应功能页面信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19上午10:44:21
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean insertFuncOptLink(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：更新【功能操作按钮】对应功能页面信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19上午10:44:28
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean updateFuncOptLink(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：修改【功能操作按钮】对应功能页面信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19上午10:44:28
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean deleteFuncOptLink(DesignFuncMenuButtonModel model);
	
	/**
	 * 
	 *@描述：查询所有操作代码信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19下午01:53:06
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getOptList();
	
	/**
	 * 
	 *@描述：获取指定角色 所有功能模块集合【菜单功能】
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-25上午11:30:58
	 *@param jsdm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<String> getFuncCodeListOfRole(String jsdm);

	/**
	 * 
	 *@描述：功能功能代码获取该功能的初始化SQL脚本
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26上午10:37:26
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public File getSqlscriptFile(DesignFuncMenuButtonModel model);

	/**
	 * 
	 *@描述：更新功能关联资源信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-28下午07:11:59
	 *@param resourceModel
	 *@param resourceList
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean updateFuncResource(DesignWidgetResourceModel resourceModel,List<DesignWidgetResourceModel> resourceList);
	
	/**
	 * 
	 *@描述：获得功能绑定的高级报表信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3上午11:05:34
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignReportModel getFuncReportModel(String func_guid);
	
}
