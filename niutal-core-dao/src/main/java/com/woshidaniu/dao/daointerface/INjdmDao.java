package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.NjdmModel;

/**
 * 
 * 类描述：部门管理
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 *
 */
@Repository("njdmDao")
public interface INjdmDao extends BaseDao<NjdmModel>{
	
	List<NjdmModel> cxNjdmList();
	
	
	/**
	 * @description	： 查询数据列表
	 * @author 		： 康康（1571）
	 * @date 		：2018年7月18日 下午3:11:05
	 * @return
	 */
	List<NjdmModel> cxDataItemList();
 
}
