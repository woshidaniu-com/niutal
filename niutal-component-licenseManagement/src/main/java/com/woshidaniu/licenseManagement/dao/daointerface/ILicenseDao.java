/**
 * 
 */
package com.woshidaniu.licenseManagement.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.licenseManagement.dao.entities.LicenseModel;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年6月23日下午3:23:35
 */
@Repository("licenseManagementDao")
public interface ILicenseDao extends BaseDao<LicenseModel>{

	/**
	 * 
	 * <p>方法说明：删除<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年7月22日上午10:02:12<p>
	 */
	int delete(String authid);
	
}
