package com.woshidaniu.dao.daointerface;



import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.XwglModel;

/**
 * 
* 
* 类名称：XwglDao 
* 类描述： 新闻管理
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
@Repository("xwglDao")
public interface IXwglDao extends BaseDao<XwglModel>{
	
	public void getList();
}
