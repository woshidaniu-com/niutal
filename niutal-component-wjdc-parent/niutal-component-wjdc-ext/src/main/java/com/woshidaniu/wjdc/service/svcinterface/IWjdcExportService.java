/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.svcinterface;

import java.io.File;
/**
 * @author ：康康（1571）
 * 问卷调查导出服务
 * 
 * */
public interface IWjdcExportService {
	/**
	 * @description	： 问卷答题详情
	 * @param wjid
	 * @return
	 * @throws Exception
	 */
	public File getDtxqById(String wjid) throws Exception;
	
	/**
	 * @description	： 导出文件统计
	 * @param wjid
	 * @return
	 * @throws Exception
	 */
	public File exportWjtj(String wjid)  throws Exception;
	
	/**
	 * @description	： 问卷答题详情-西安交通大学_10698
	 * @param wjid
	 * @return
	 * @throws Exception
	 */
	public File exportDtxqById_10698(String wjid) throws Exception;
}
