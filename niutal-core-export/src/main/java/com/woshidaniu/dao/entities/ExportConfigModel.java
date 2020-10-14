/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.entities;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * 导出配置模型，与数据层字段匹配
 * @author Penghui.Qu
 */
public class ExportConfigModel {

	protected String dcclbh;
	protected String dcclmc;
	protected String zd;
	protected String zdmc;
	protected String xssx;
	protected String zgh;
	protected String sfmrzd;
	protected String zt;
	
	private SearchModel searchModel = new SearchModel();
	public QueryModel queryModel = new QueryModel();
	
	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public QueryModel getQueryModel() {
		return queryModel;
	}
	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}
	public String getDcclbh() {
		return dcclbh;
	}
	public void setDcclbh(String dcclbh) {
		this.dcclbh = dcclbh;
	}
	public String getDcclmc() {
		return dcclmc;
	}
	public void setDcclmc(String dcclmc) {
		this.dcclmc = dcclmc;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getZdmc() {
		return zdmc;
	}
	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getSfmrzd() {
		return sfmrzd;
	}
	public void setSfmrzd(String sfmrzd) {
		this.sfmrzd = sfmrzd;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	@Override
	public String toString() {
		return "ExportConfigModel [dcclbh=" + dcclbh + ", dcclmc=" + dcclmc + ", zd=" + zd + ", zdmc=" + zdmc + ", xssx=" + xssx + ", zgh=" + zgh + ", sfmrzd=" + sfmrzd + ", zt=" + zt + "]";
	}
}