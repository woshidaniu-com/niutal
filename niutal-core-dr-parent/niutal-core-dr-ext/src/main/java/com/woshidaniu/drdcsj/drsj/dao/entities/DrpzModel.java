/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 982
 * 导入模块实际导入的列信息
 * 
 * @author zhidong (1571)
 * 整理代码
 */
public class DrpzModel{
	
	//---------------------以下是表内字段
	// 导入模块代码
	private String drmkdm;
	// 导入模块名称
	private String drmkmc;
	// 默认对应表名称
	private String drbmc;
	//导入验证模式
	private String dryz;
	
	//--------------------以下是额外字段，在程序中追加或初始化
	// 插入方式
	private String crfs;
	//是否是联合组件配置
	//判断依据是一个导入配置中存在多个主键配置
	private boolean isCompositeId = false;
	//联合主键列名
	private String[] compositeIds = new String[] {};
	//业务扩展配置信息
	private List<PluginModel> pluginModelList = new ArrayList<PluginModel>();
	
	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getDrmkmc() {
		return drmkmc;
	}

	public void setDrmkmc(String drmkmc) {
		this.drmkmc = drmkmc;
	}

	public String getDrbmc() {
		return drbmc;
	}

	public void setDrbmc(String drbmc) {
		this.drbmc = drbmc;
	}

	public String getCrfs() {
		return crfs;
	}

	public void setCrfs(String crfs) {
		this.crfs = crfs;
	}

	public boolean isCompositeId() {
		return isCompositeId;
	}

	public void setCompositeId(boolean isCompositeId) {
		this.isCompositeId = isCompositeId;
	}

	public String[] getCompositeIds() {
		return compositeIds;
	}

	public void setCompositeIds(String[] compositeIds) {
		this.compositeIds = compositeIds;
	}
	
	public List<PluginModel> getPluginModelList() {
		return pluginModelList;
	}

	public void setPluginModelList(List<PluginModel> pluginModelList) {
		this.pluginModelList = pluginModelList;
	}

	public String getDryz() {
		return dryz;
	}

	public void setDryz(String dryz) {
		this.dryz = dryz;
	}

	@Override
	public String toString() {
		return "DrpzModel [drmkdm=" + drmkdm + ", drmkmc=" + drmkmc + ", drbmc=" + drbmc + ", dryz=" + dryz + ", crfs="
				+ crfs + ", isCompositeId=" + isCompositeId + ", compositeIds=" + Arrays.toString(compositeIds)
				+ ", pluginModelList=" + pluginModelList + "]";
	}
}