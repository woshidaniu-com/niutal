package com.woshidaniu.wjdc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.wjdc.dao.entites.WjpzModel;
import com.woshidaniu.wjdc.dao.entites.WjpzSjylxModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IWjpzService;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjpzAction extends BaseAction implements ModelDriven<WjpzModel>{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private IWjpzService service;
	@Autowired
	private IWjBaseService baseService;
	private WjpzModel model=new WjpzModel();
	
	/**
	 * 问卷配置
	 * @return
	 */
	public String wjpz(){
		try {
			List<WjpzSjylxModel> wjpzsjylxList=baseService.getWjpzSjylxList();
			getValueStack().set("wjlxList",wjpzsjylxList);
			if((model.getLxid()==null||"".equals(model.getLxid()))
					&&wjpzsjylxList.size()>0){
				model.setLxid(wjpzsjylxList.get(0).getLxid());
			}
			getValueStack().set("gnlbpzList",service.getGnlbPzxx(model.getLxid()));
			
		} catch (Exception e) {
			logException(e);
		}
		return "wjpz";
	}
	
	/**
	 * 保存问卷配置信息
	 * @return
	 */
	public String saveWjpz(){
		
		try {
			service.saveWjpz(getRequest(), model);
		} catch (Exception e) {
			logException(e);
		}
		return wjpz();
	}

	public WjpzModel getModel() {
		return model;
	}

	public IWjpzService getService() {
		return service;
	}

	public void setService(IWjpzService service) {
		this.service = service;
	}

	public void setModel(WjpzModel model) {
		this.model = model;
	}

	public IWjBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IWjBaseService baseService) {
		this.baseService = baseService;
	}

}
