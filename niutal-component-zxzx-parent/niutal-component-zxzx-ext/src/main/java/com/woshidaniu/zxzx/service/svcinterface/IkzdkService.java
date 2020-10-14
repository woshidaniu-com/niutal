package com.woshidaniu.zxzx.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;

/**
 * 在线咨询板块服务
 */
public interface IkzdkService extends BaseService<kzdkModel>{

	/**
	 * 查询是否存在于常见问题表中和问题咨询表中
	 * @param model
	 * @return
	 */
	Integer checkCanDelete(String bkdm);
	
	boolean kzdkXssz(Map<String,String> data);
	
	Integer getMaxXsxs();
	
	/**
	 * @description	： 查询所有板块列表
	 * @param model
	 * @return
	 */
	List<kzdkModel> getModelListWeb(kzdkModel model);
	
	/**
	 * @description	： 查询咨询板块列表
	 * @param model
	 * @return
	 */
	List<kzdkModel> getkzdkModelListWeb();
	
}
