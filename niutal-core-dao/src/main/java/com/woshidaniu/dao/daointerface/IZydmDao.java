package com.woshidaniu.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.ZydmModel;

/**
 * 
 * 类描述：部门管理
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 *
 */
@Repository("zydmDao")
public interface IZydmDao extends BaseDao<ZydmModel>{

	
	public List<ZydmModel> queryModel(Map<String,String> map);
	
	
}
