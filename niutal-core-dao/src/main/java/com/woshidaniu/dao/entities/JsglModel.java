package com.woshidaniu.dao.entities;



import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：角色对象
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月15日下午7:00:40
 */
@Alias(value="JsglModel")
public class JsglModel implements Serializable {

	private static final long serialVersionUID = 8666379269724783798L;
	
	private String jsmc;//角色名称
	private String jsdm;//角色代码
	private String jssm;//角色说明
	private String jsdj;//角色等级
	private String qyzt;//启用状态
	private List<YhglModel> yhxxList;
	private int yhs;
	
	private SearchModel searchModel;
	public QueryModel queryModel = new QueryModel();
	
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJssm() {
		return jssm;
	}
	public void setJssm(String jssm) {
		this.jssm = jssm;
	}
	public String getJsdj() {
		return jsdj;
	}
	public void setJsdj(String jsdj) {
		this.jsdj = jsdj;
	}
	public String getQyzt() {
		return qyzt;
	}
	public void setQyzt(String qyzt) {
		this.qyzt = qyzt;
	}
	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public List<YhglModel> getYhxxList() {
		return yhxxList;
	}
	public void setYhxxList(List<YhglModel> yhxxList) {
		this.yhxxList = yhxxList;
	}
	public int getYhs() {
		return yhs;
	}
	public void setYhs(int yhs) {
		this.yhs = yhs;
	}
	public QueryModel getQueryModel() {
		return queryModel;
	}
	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}
	
	
}
