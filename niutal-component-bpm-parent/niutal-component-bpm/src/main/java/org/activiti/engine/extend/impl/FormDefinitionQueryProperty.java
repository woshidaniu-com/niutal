package org.activiti.engine.extend.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.query.QueryProperty;

public class FormDefinitionQueryProperty implements QueryProperty {

	private static final long serialVersionUID = 1L;

	private static final Map<String, FormDefinitionQueryProperty> properties = new HashMap<String, FormDefinitionQueryProperty>();

	public static final FormDefinitionQueryProperty FORM_DEFINITION_KEY = new FormDefinitionQueryProperty(
			"RES.KEY_");
	public static final FormDefinitionQueryProperty FORM_DEFINITION_CATEGORY = new FormDefinitionQueryProperty(
			"RES.CATEGORY_");
	public static final FormDefinitionQueryProperty FORM_DEFINITION_ID = new FormDefinitionQueryProperty(
			"RES.ID_");
	public static final FormDefinitionQueryProperty FORM_DEFINITION_VERSION = new FormDefinitionQueryProperty(
			"RES.VERSION_");
	public static final FormDefinitionQueryProperty FORM_DEFINITION_NAME = new FormDefinitionQueryProperty(
			"RES.NAME_");
	public static final FormDefinitionQueryProperty DEPLOYMENT_ID = new FormDefinitionQueryProperty(
			"RES.DEPLOYMENT_ID_");
	public static final FormDefinitionQueryProperty FORM_DEFINITION_TENANT_ID = new FormDefinitionQueryProperty(
			"RES.TENANT_ID_");

	private String name;

	public FormDefinitionQueryProperty(String name) {
	    this.name = name;
	    properties.put(name, this);
	  }

	public String getName() {
		return name;
	}

	public static FormDefinitionQueryProperty findByName(String propertyName) {
		return properties.get(propertyName);
	}
}
