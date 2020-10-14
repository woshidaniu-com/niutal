package com.woshidaniu.wjdc.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.wjdc.dao.entites.WjgnysModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IWjgnysService;

/**
 *问卷功能约束 
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjgnysAction extends BaseAction implements ModelDriven<WjgnysModel>{
	
	private static final long serialVersionUID = 1L;
	private WjgnysModel model=new WjgnysModel();
	@Autowired
	private IWjgnysService service;
	@Autowired
	private IWjBaseService iWjBaseService;
	

	public WjgnysModel getModel() {
		return model;
	}


	public final IWjgnysService getService() {
		return service;
	}


	public final void setService(IWjgnysService service) {
		this.service = service;
	}


	public final IWjBaseService getiWjBaseService() {
		return iWjBaseService;
	}


	public final void setiWjBaseService(IWjBaseService iWjBaseService) {
		this.iWjBaseService = iWjBaseService;
	}
	
	

}
