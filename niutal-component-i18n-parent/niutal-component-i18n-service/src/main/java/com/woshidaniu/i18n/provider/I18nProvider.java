package com.woshidaniu.i18n.provider;

import java.util.List;
import java.util.ResourceBundle;

import com.woshidaniu.i18n.dao.entities.I18nModel;

public interface I18nProvider {

	List<ResourceBundle> getResourceBundle(String expression);	
	
	I18nModel getI18nModel(String module);	
	
}
