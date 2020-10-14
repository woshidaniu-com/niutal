/**
 * 
 */
package com.woshidaniu.licenseManagement.service.svcinterface;

import java.io.File;

import net.sf.json.JSONObject;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.licenseManagement.dao.entities.LicenseModel;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：license service 接口
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年6月14日上午10:40:08
 */
public interface ILicenseService extends BaseService<LicenseModel>{

	boolean delete(String authid);
	
	/**
	 * 
	 * <p>方法说明：生产license文件，需要调用本地方法加密<p>
	 * <p>作者：<a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月14日上午10:43:46<p>
	 */
	File createLicenseFile(LicenseModel model);
	
	/**
	 * 
	 * <p>方法说明：TODO<p>
	 * <p>作者：<a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月14日上午10:46:27<p>
	 */
	JSONObject getPlainLicenseJSONObject(LicenseModel model);
	
}
