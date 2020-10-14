/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.ruler.plugin.AbstractCombinationValidateRule;

public class PluginModel {
	
	private final Logger log = LoggerFactory.getLogger(PluginModel.class);
	//导入模块代码
	private String drmkdm;
	//导入列，逗号分隔
	private String drls;
	
	private String yzpz;
	
	private String cssz;
	//是否启用
	private String sfqy;
	//提示信息
	private String tsxx;
	
	private AbstractCombinationValidateRule rule;
	//导入列名称列表
	private String[] drlmcs;
	
	public void doConfig(List<DrlpzModel> drlpzModelList){
		if(isConfiged()) {
			return;
		}
		doDrlConfig(drlpzModelList);
		doYzpzConfig();
	}
	
	private boolean isConfiged() {
		return this.rule != null;
	}
	
	private void doDrlConfig(List<DrlpzModel> drlpzModelList){
		String[] drlsArr = null;
		if(StringUtils.isNotBlank(drls)){
			drlsArr = StringUtils.split(drls, ",");
			drlmcs = new String[drlsArr.length];
		}
		for (int i = 0; i < drlsArr.length; i++) {
			String drldm = drlsArr[i];
			for (DrlpzModel drlpz : drlpzModelList) {
				if(StringUtils.equalsIgnoreCase(drlpz.getDrl(), drldm)){
					drlmcs[i] = drlpz.getDrlmc();
				}
			}
		}
	}
	
	private void doYzpzConfig(){
		
		if(StringUtils.isNotBlank(yzpz)){
			Class<?> clazz = null;
			try {
				clazz = Class.forName(yzpz);
			} catch (ClassNotFoundException e1) {
				throw new IllegalStateException("没有找到导入模块代码为"+ this.drmkdm +"的插件配置的yzpz["+ yzpz +"]参数配置的这个类");
			}
			Object instance = null;
			try {
				instance = ConstructorUtils.invokeConstructor(clazz, null);
			} catch (Exception e1) {
				throw new IllegalStateException("导入模块代码为"+ this.drmkdm +"的插件实例化异常",e1);
			}
			if(instance instanceof AbstractCombinationValidateRule) {
				rule = (AbstractCombinationValidateRule) instance;
			}else {
				throw new IllegalStateException("导入模块代码为"+ this.drmkdm +"的插件配置的yzpz这个类不是"+ AbstractCombinationValidateRule.class.getName() +"的子类");
			}
		}else {
			throw new IllegalStateException("导入模块代码为"+ this.drmkdm +"的插件配置的yzpz是null");
		}
		
		if(StringUtils.isNotBlank(cssz)){
			Map<String,String> csszMap = new HashMap<String, String>();
			String[] split = StringUtils.split(cssz, ",");
			for (String s : split) {
				String tv = s.trim();
				String key = StringUtils.substringBefore(tv, "=");
				String value = StringUtils.substringAfter(tv, "=");
				csszMap.put(key, value);
			}
			
			if(rule != null){
				rule.setInitialMap(csszMap);
			}
		}
	}
	
	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getDrls() {
		return drls;
	}

	public void setDrls(String drls) {
		this.drls = drls;
	}

	public String getYzpz() {
		return yzpz;
	}

	public void setYzpz(String yzpz) {
		this.yzpz = yzpz;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getTsxx() {
		return tsxx;
	}

	public void setTsxx(String tsxx) {
		this.tsxx = tsxx;
	}

	public AbstractCombinationValidateRule getRule() {
		return rule;
	}

	public void setRule(AbstractCombinationValidateRule rule) {
		this.rule = rule;
	}

	public String[] getDrlmcs() {
		return drlmcs;
	}

	public void setDrlmcs(String[] drlmcs) {
		this.drlmcs = drlmcs;
	}

	public String getCssz() {
		return cssz;
	}

	public void setCssz(String cssz) {
		this.cssz = cssz;
	}

	@Override
	public String toString() {
		return "PluginModel [drmkdm=" + drmkdm + ", drls=" + drls + ", yzpz=" + yzpz + ", cssz=" + cssz + ", sfqy="
				+ sfqy + ", tsxx=" + tsxx + ", rule=" + rule + ", drlmcs=" + Arrays.toString(drlmcs) + "]";
	}
}
