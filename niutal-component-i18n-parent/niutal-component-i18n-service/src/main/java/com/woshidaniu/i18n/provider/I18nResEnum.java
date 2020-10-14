package com.woshidaniu.i18n.provider;

import java.util.Locale;


public enum I18nResEnum {
	
	RES_JS("js"),
	RES_PROPERTIES("properties");

	private final String type;

	private I18nResEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	static I18nResEnum valueOfIgnoreCase(String parameter) {
		return valueOf(parameter.toUpperCase(Locale.ENGLISH).trim());
	}
	
}
