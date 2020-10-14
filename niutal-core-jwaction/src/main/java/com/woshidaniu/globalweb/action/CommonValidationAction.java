package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.entities.ValidationModel;
import com.woshidaniu.service.common.ICommonValidationService;

/**
 * 
 *@类名称:CommonValidationAction.java
 *@类描述：公共的验证action
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:21:01
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class CommonValidationAction extends CommonBaseAction implements ModelDriven<ValidationModel>  {

	
	protected ValidationModel model = new ValidationModel();
	@Resource
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
	
	/**
	 * 
	 *@描述：邮箱根域名验证
	 *@创建人:zfankai
	 *@创建时间:2016-1-7上午09:20:09
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxValidEmail() {
		try {
			boolean result = service.validEmail(model);
			getValueStack().set(DATA,result?"1":"0");
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

