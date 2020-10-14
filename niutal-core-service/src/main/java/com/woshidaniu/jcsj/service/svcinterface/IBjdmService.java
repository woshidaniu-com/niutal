package com.woshidaniu.jcsj.service.svcinterface;


import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.jcsj.dao.entities.BjdmModel;

/**
 * 班级代码维护
 * @author Administrator
 *
 */
public interface IBjdmService extends BaseService<BjdmModel> {
	
	
	/**
	 * 批量删除班级
	 * @param ids
	 * @return
	 */
	public String scBjdm(String ids);

}
