/**
 * 
 */
package com.woshidaniu.licenseManagement.service.impl;

import java.io.File;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.licenseManagement.dao.daointerface.ILicenseDao;
import com.woshidaniu.licenseManagement.dao.entities.LicenseModel;
import com.woshidaniu.licenseManagement.service.svcinterface.ILicenseService;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年6月14日上午10:48:53
 */
@Service("licenseManagementService")
public class LicenseServiceImpl extends BaseServiceImpl<LicenseModel, ILicenseDao> implements ILicenseService {
	
	private static final String KEY = "";
	
	
	
	@Override
	@Autowired
	@Qualifier("licenseManagementDao")
	public void setDao(ILicenseDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean delete(String authid) {
		return dao.delete(authid) > 0;
	}
	
	/* (non-Javadoc)
	 * @see com.woshidaniu.licenseManagement.service.svcinterface.ILicenseService#createLicenseFile(com.woshidaniu.licenseManagement.dao.entities.LicenseModel)
	 */
	@Override
	public File createLicenseFile(LicenseModel model) {
		StringBuffer s_1 = new StringBuffer();
		s_1.append(model.getAuthId()).append(model.getUsage()).append(model.getUsageCount());
		
		JSONObject licenseJSON = getPlainLicenseJSONObject(model);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.licenseManagement.service.svcinterface.ILicenseService#getPlainLicenseJSONObject(com.woshidaniu.licenseManagement.dao.entities.LicenseModel)
	 */
	@Override
	public JSONObject getPlainLicenseJSONObject(LicenseModel model) {
		return null;
	}

	

}
