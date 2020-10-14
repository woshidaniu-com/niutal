package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.DesignZdybdFlszModel;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;
/**
 * 
 *@类名称:IDesignZdybdService.java
 *@类描述：基于自定义表单表结构的自定义功能Service接口
 *@创建人：kangzhidong
 *@创建时间：Oct 27, 2015 9:00:21 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignZdybdService extends BaseService<DesignZdybdZddyModel> {

	/**
	 * 
	 *@描述：根据功能代码获取分类列表数据
	 *@创建人:kangzhidong
	 *@创建时间:Oct 27, 20154:01:24 PM
	 *@param func_code : 功能代码
	 *@param func_type : 功能类型 1:增加;2:修改;3:查看
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignZdybdFlszModel> getFlszListByGndm(String func_code,String func_type);
	
	/**
	 * 
	 *@描述：根据功能代码获取所有分类下的字段列表
	 *@创建人:kangzhidong
	 *@创建时间:Oct 27, 20159:20:23 AM
	 *@param func_code : 功能代码
	 *@param func_type : 功能类型 1:增加;2:修改;3:查看
	 *@param jsdm	   : 角色代码
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignZdybdZddyModel> getZdyzdListByGndm(String func_code,String func_type,String jsdm);
	
}
