package com.woshidaniu.service.common.conf.def;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.woshidaniu.api.data.CsszPropsProvider;
import com.woshidaniu.entities.XtszModel;
import com.woshidaniu.service.svcinterface.IXtszService;

public class OracleCsszUpdateProvider implements CsszPropsProvider {

	@Resource
	protected IXtszService xtszService;
	
	@Override
	public List list(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params) {
		
		XtszModel model = new XtszModel();
		
		model.setSsmk(ssmk);
		model.setSsgnmkdm(ssgnmkdm);
		model.setZs(zs);
		
		return getXtszService().getModelList(model);
	}
	
	@Override
	public void update(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params) {
		
		XtszModel model = new XtszModel();
		
		model.setSsmk(ssmk);
		model.setSsgnmkdm(ssgnmkdm);
		model.setZs(zs);
		
		
		getXtszService().updateXtsz(model);
		
		
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

}
