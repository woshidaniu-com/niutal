/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.svcinterface;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.woshidaniu.wjdc.dao.entites.WjdjztModel;

public interface IWjdjztService {

	/**
	 * @description	： 判断是否存在问卷
	 * @param wjid
	 * @return
	 */
	int getWjCount(String wjid);
	/**
	 * @description	： 获得答卷状态列表
	 * @param model
	 * @return
	 */
	List<Map<String,String>> getPagedDjztList(WjdjztModel model);

	/**
	 * @description	： 导出答卷状态列表
	 * @param model
	 * @return
	 */
	File exportDjztList(WjdjztModel model) throws Exception;
	
	/**
	 * @description	： 查询已经分发且处于运行状态的问卷
	 * @return
	 */
	List<Map<String,String>> getYffWjList();
}
