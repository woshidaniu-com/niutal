package com.woshidaniu.service.common;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.TimeSettingModel;

/**
 * 
 *@类名称:ITimeSettingService.java
 *@类描述：功能开放时间控制service接口
 *@创建人：kangzhidong
 *@创建时间：2015-2-10 上午09:03:45
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface ICommonTimeSettingService extends BaseService<TimeSettingModel> {


	/**
	 * 
	 *@描述：更新 指定功能代码+操作代码对应的功能的 使用状态 信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10上午11:54:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateStatus(TimeSettingModel model);
	
	/**
	 * 
	 *@描述：新增或更新 指定功能代码+操作代码对应的功能的开放时间信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10上午11:54:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateTimeSetting(TimeSettingModel model);

	/**
	 * 
	 *@描述：查询角色功能菜单+开放时间信息组成的 树形显示结果结果数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10上午11:53:57
	 *@修改人:wwb
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<TimeSettingModel> getTreeGridModelList(TimeSettingModel model); 
	
	/**
	 * 
	 *@描述：查询指定功能代码+操作代码对应的功能的开放时间信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-9下午07:24:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmKey
	 *@param czdm
	 *@return
	 */
	public TimeSettingModel getTimeSetting(String gnmkdmKey,String czdm);
	
	/**
	 * 
	 *@描述：得到所有受到时间控制的 功能模块代码+操作代码  集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6下午03:42:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<String>  getTimeControlList();
	
	/**
	 * 
	 *@描述：判断指定功能代码+操作代码对应的功能是否开启时间控制
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6下午04:08:27
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmKey
	 *@param czdm
	 *@return
	 */
	public boolean isOnTimeControl(String gnmkdmKey,String czdm);
	
	/**
	 * 
	 *@描述：判断指定功能代码+操作代码对应的功能是否开放
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10上午09:27:59
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmKey
	 *@param czdm
	 *@return
	 */
	public boolean isInTimeSetting(String gnmkdmKey,String czdm);
	
	/**
	 * 
	 *@描述：判断指定功能代码+操作代码对应的功能是否开放；判断逻辑交有开发人员判断
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10上午09:16:23
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmKey
	 *@param czdm
	 *@return
	 */
	public boolean isInTimeSetting(String gnmkdmKey,String czdm,ITimeSettingCallback callback);
	
	/**
	 * 
	 *@描述：保存时间设置信息 表格形式
	 *@创建人:wwb
	 *@创建时间:2015-2-10下午06:03:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateSettingTime(TimeSettingModel model);
	
}
