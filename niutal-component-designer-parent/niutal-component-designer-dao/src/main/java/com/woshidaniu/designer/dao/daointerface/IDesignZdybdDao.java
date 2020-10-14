package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignZdybdFlszModel;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;

/**
 * 
 *@类名称:IDesignZdybdDao.java
 *@类描述：基于自定义表单表结构的自定义功能Dao接口
 *@创建人：kangzhidong
 *@创建时间：Oct 27, 2015 9:32:47 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignZdybdDao extends BaseDao<DesignZdybdZddyModel> {

	/**
	 * 
	 *@描述：根据功能代码获取分类列表数据
	 *@创建人:kangzhidong
	 *@创建时间:Oct 27, 20154:01:24 PM
	 *@param gndm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignZdybdFlszModel> getFlszListByGndm(@Param("func_code")String func_code,@Param("func_type")String func_type);
	
	/**
	 * 
	 *@描述：根据功能代码获取所有分类下的字段列表
	 *@创建人:kangzhidong
	 *@创建时间:Oct 27, 20159:20:23 AM
	 *@param gndm
	 *@param jsdm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignZdybdZddyModel> getZdyzdListByGndm(@Param("func_code")String func_code,@Param("func_type")String func_type,@Param("jsdm")String jsdm);

}
