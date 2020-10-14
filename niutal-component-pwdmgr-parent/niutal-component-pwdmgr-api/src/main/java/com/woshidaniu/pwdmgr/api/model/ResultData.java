package com.woshidaniu.pwdmgr.api.model;

import java.util.Map;
@SuppressWarnings("rawtypes")
public class ResultData {

	protected String status;
	protected String i18nKey;
	protected String i18nMsg;
	protected Object[] i18nArgs;
	protected Map map;
	
	public ResultData(String status, String i18nKey) {
		this.status = status;
		this.i18nKey = i18nKey;
		
	}
	
	public ResultData(String status, String i18nKey, Map map) {
		this.status = status;
		this.i18nKey = i18nKey;
		this.map = map;
	}
	
	public ResultData(String status, String i18nKey,Object[] i18nArgs) {
		this.status = status;
		this.i18nKey = i18nKey;
		this.i18nArgs = i18nArgs;
	}
	
	public ResultData(String status, String i18nKey,Object[] i18nArgs, Map map) {
		this.status = status;
		this.i18nKey = i18nKey;
		this.i18nArgs = i18nArgs;
		this.map = map;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nMsg() {
		return i18nMsg;
	}

	public void setI18nMsg(String i18nMsg) {
		this.i18nMsg = i18nMsg;
	}
	
	public Object[] getI18nArgs() {
		return i18nArgs;
	}

	public void setI18nArgs(Object[] i18nArgs) {
		this.i18nArgs = i18nArgs;
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public static ResultData to(String status, String i18nKey) {
		return new ResultData(status, i18nKey);
	}
	
	public static ResultData to(String status, String i18nKey, Map map) {
		return new ResultData(status, i18nKey, map);
	}
	
	public static ResultData to(String status, String i18nKey,Object[] i18nArgs) {
		return new ResultData(status, i18nKey, i18nArgs);
	}
	
	public static ResultData to(String status, String i18nKey,Object[] i18nArgs, Map map) {
		return new ResultData(status, i18nKey, i18nArgs, map);
	}

}
