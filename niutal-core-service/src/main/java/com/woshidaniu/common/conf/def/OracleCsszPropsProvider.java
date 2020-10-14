package com.woshidaniu.common.conf.def;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.woshidaniu.api.data.CsszPropsProvider;
import com.woshidaniu.dao.entities.CsszModel;
import com.woshidaniu.service.svcinterface.ICsszService;

public class OracleCsszPropsProvider implements CsszPropsProvider {

	@Resource
	protected ICsszService csszService;
	
	@Override
	public List list(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params) {

		CsszModel model = new CsszModel();
		
		model.setSsmk(ssmk);
		model.setSsgnmkdm(ssgnmkdm);
		model.setZs(zs);
		
		return getCsszService().getModelList(model);
	}
	
	@Override
	public void update(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params) {
		
		CsszModel model = new CsszModel();
		
		model.setSsmk(ssmk);
		model.setSsgnmkdm(ssgnmkdm);
		model.setZs(zs);
		
		getCsszService().saveCssz(model, params);
		
	}
	
	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}
	

}
