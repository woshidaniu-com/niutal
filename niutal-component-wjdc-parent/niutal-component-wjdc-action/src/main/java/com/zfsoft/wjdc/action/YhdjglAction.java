package com.woshidaniu.wjdc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.wjdc.dao.entites.WjpzSjylxModel;
import com.woshidaniu.wjdc.dao.entites.YhdjglModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IYhdjglService;

/**
 * 用户答卷管理
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class YhdjglAction extends BaseAction implements ModelDriven<YhdjglModel>{
	
	private static final long serialVersionUID = 1L;
	private YhdjglModel model=new YhdjglModel();
	
	@Autowired
	private IYhdjglService service;
	@Autowired
	private IWjBaseService baseService;
	
	/**
	 * 查询用户答卷信息
	 */
	public String cxYhdjxx(){
			try {
				if(model.getSjyLxid()==null||"".equals(model.getSjyLxid())){
					WjpzSjylxModel sjyModel=baseService.getWjpzSjylxBySessionLxidModel(getUser().getYhlx());
					if(sjyModel!=null){
						model.setSjyLxid(sjyModel.getLxid());
					}
				}
				
				model.setDjrid(getUser().getYhm());
				
				List<YhdjglModel> wjList=service.getPagedList(model);
				getValueStack().set("wjList", wjList);
			} catch (Exception e) {
				logException(e);
			}
        
		return SUCCESS;
	}

	/**
	 * 跳转到用户答卷页面(移动端调用)
	 * @return
	 */
	public String forwardCxYhdj(){
		return SUCCESS;
	}
	
	/**
	 * 查询用户答卷信息(移动端调用)
	 */
	public String queryYhdjxx(){
			try {
				if(model.getSjyLxid()==null||"".equals(model.getSjyLxid())){
					WjpzSjylxModel sjyModel=baseService.getWjpzSjylxBySessionLxidModel(getUser().getYhlx());
					if(sjyModel!=null){
						model.setSjyLxid(sjyModel.getLxid());
					}
				}
				model.setDjrid(getUser().getYhm());
				model.queryModel.setTotalResult(Integer.MAX_VALUE);
				List<YhdjglModel> wjList=service.getPagedList(model);
				getValueStack().set("data", wjList);
			} catch (Exception e) {
				logException(e);
			}	
		return DATA;
	}
	
	public YhdjglModel getModel() {
		return model;
	}

	public IYhdjglService getService() {
		return service;
	}

	public void setService(IYhdjglService service) {
		this.service = service;
	}

	public IWjBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IWjBaseService baseService) {
		this.baseService = baseService;
	}
}
