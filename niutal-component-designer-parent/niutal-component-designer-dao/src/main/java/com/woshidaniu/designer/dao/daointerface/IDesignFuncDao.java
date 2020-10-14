package com.woshidaniu.designer.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;

/**
 * 
 *@类名称: IDesignFuncDao.java
 *@类描述： 功能设计操作dao：为指定功能代码+操作代码维护操作功能数据
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:34:59
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncDao extends BaseDao<DesignFuncModel>{

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
	public DesignFuncModel getFuncModel(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
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
     *@描述：获取系统 所有功能模块列表 :针对于admin
     *@创建人:kangzhidong
     *@创建时间:2015-5-7下午06:49:23
     *@return
     *@修改人:
     *@修改时间:
     *@修改描述:
     */
	public List<DesignFuncMenuModel> getSystemFuncList(@Param("func_code")String func_code);
	
	/**
	 * 
	 *@描述：根据功能模块代码功能模块信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19上午10:44:51
	 *@param func_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncMenuModel getSystemFuncModel(@Param("func_code")String func_code);
	
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
	public int insertMenu(DesignFuncMenuModel model);
	
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
	public int insertFuncMenu(DesignFuncMenuModel model);
	
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
	public int deleteFuncMenu(DesignFuncMenuModel model);
	
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
	public int updateMenu(DesignFuncMenuModel model);
	
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
	public int updateFuncMenu(DesignFuncMenuModel model);
	
	/**
	 * 
	 *@描述：获取系统 所有功能模块操作按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午06:48:50
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncMenuButtonModel> getSystemFuncOptList(@Param("localeKey")String localeKey);
	
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
	public List<DesignFuncMenuButtonModel> getSystemFuncOptMoldelList(@Param("localeKey")String localeKey,@Param("func_code")String func_code);
	
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
	public DesignFuncMenuButtonModel getSystemFuncOptModel(@Param("localeKey")String localeKey,@Param("func_code")String func_code,@Param("opt_code")String opt_code);

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
	public int insertFuncOpt(DesignFuncMenuButtonModel model);
	
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
	public int deleteFuncOpt(DesignFuncMenuButtonModel model);
	
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
	public int updateFuncOpt(DesignFuncMenuButtonModel model);

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
	public List<DesignFuncMenuButtonModel> getFuncOptLinkList(@Param("func_code")String func_code);
	
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
	public int insertFuncOptLink(DesignFuncMenuButtonModel model);
	
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
	public int updateFuncOptLink(DesignFuncMenuButtonModel model);
	
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
	public int deleteFuncOptLink(DesignFuncMenuButtonModel model);
	
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
	public List<String> getFuncCodeListOfRole(@Param(value="jsdm")String jsdm);
	
	/**
	 * 
	 *@描述：根据SQL语句查询数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26上午10:59:48
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getTableDataList(Map<String, Object> rootMap);
	
}
