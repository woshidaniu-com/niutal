package com.woshidaniu.common.validator.rules;

import java.util.HashMap;
import java.util.Map;

public class ImportExtendRule implements IValidateRule {
	public Map<String, String> map=null;
	public ImportExtendRule(HashMap<String, String> map){
		this.map=map;
	}
	@Override
	public String getValidateInfo() {
		return null;
	}

	@Override
	public boolean validate(Object value) {
		return true;
	}

}
