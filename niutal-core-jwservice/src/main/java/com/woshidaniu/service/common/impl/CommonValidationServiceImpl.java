/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.woshidaniu.daointerface.ICommonValidationDao;
import com.woshidaniu.daointerface.IEmailSettingDao;
import com.woshidaniu.entities.ValidationModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.IDCardUtils;
import com.woshidaniu.service.common.ICommonValidationService;

/**
 *@类名称:CommonValidationServiceImpl.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:30:21
 *@版本号:v1.0
 */
public class CommonValidationServiceImpl implements ICommonValidationService {
	@Resource
	private ICommonValidationDao dao; 
	@Resource
	private Map<String,String> table_validation;
	@Resource
	private IEmailSettingDao settingDao;
	
	@Override
	public boolean unique(ValidationModel model) {
		String table = table_validation.get(model.getTable());
		model.setTable(table);
		if(BlankUtils.isBlank(model.getFiled_value())){
			int count = getDao().getMultiCount(model);
			return count==0;
		}else{
			int count = getDao().getCount(model);
			return count==0;
		}
	}
	
	public String isIDCard(ValidationModel model){
		//校验身份证号码
		IDCardUtils.isIDCard(model.getFiled_value());
		return IDCardUtils.getCodeError();
	}
	
	@Override
	public boolean validEmail(ValidationModel model) {
		int i = dao.getYxlxYZ(model);
		return i>0;
	}

	public ICommonValidationDao getDao() {
		return dao;
	}

	public void setDao(ICommonValidationDao dao) {
		this.dao = dao;
	}

	public Map<String, String> getTable_validation() {
		return table_validation;
	}

	public void setTable_validation(Map<String, String> tableValidation) {
		table_validation = tableValidation;
	}

}
