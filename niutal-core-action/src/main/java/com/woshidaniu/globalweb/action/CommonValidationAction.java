package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.dao.entities.ValidationModel;
import com.woshidaniu.service.common.ICommonValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 
 *@类名称:CommonValidationAction.java
 *@类描述：公共的验证action
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:21:01
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommonValidationAction extends BaseAction implements ModelDriven<ValidationModel>  {

	
	private ValidationModel model = new ValidationModel();
	
	@Autowired
	private ICommonValidationService service;

	 
	
	/**
	 * 
	 *@描述：查询唯一性验证
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-17下午08:22:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxUnique(){
		try {
			boolean unique = getService().unique(model);
			getValueStack().set(DATA,unique?"1":"0");
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}

	public ICommonValidationService getService() {
		return service;
	}

	public void setService(ICommonValidationService service) {
		this.service = service;
	}

	public ValidationModel getModel() {
		return model;
	}

	
}

