package com.woshidaniu.pwdmgr.api.model;

public class BindField {

	protected String alias;
	protected String name;
	protected String desc;
	protected String regex;
	protected String replacement;
	
	protected boolean required;

	public BindField() {
	}
	
	public BindField(String name, String desc, boolean required) {
		this.name = name;
		this.desc = desc;
		this.required = required;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	
}
