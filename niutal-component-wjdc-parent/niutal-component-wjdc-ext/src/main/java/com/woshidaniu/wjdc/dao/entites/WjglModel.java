/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;
import com.woshidaniu.wjdc.enums.QuestionnaireStatus;
/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
@Alias(value="WjglModel")
public class WjglModel extends ModelBase {

	private static final long serialVersionUID = 1L;
	private String djrid;//答卷人id
	private String wjid;// 问卷id
	private String wjmc;// 问卷名称
	private String wjlx;// 问卷类型
	private QuestionnaireStatus wjzt;// 问卷状态
	private String jsy;// 卷首语
	private String jwy;// 卷尾语
	private String wjzf;// 问卷总分
	private String cjr;// 创建人
	private String cjsj;// 创建时间
	private String gqsj;// 获取时间
	private String fblx;// 发布类型
	private String cjrmc;//创建人名称
	private String wjyxj = "1";//问卷优先级,默认是1
	
	private SearchModel searchModel;
	
	//答卷状态 
	//0：未答卷
	//1.答卷中
	//2.答卷完成
	private int djzt;
	
	//分发状态
	private int ffzt;
	
	private List<FfmxModel> ffmxList;
	
	public int getFfzt() {
		return ffzt;
	}

	public void setFfzt(int ffzt) {
		this.ffzt = ffzt;
	}

	public List<FfmxModel> getFfmxList() {
		return ffmxList;
	}

	public void setFfmxList(List<FfmxModel> ffmxList) {
		this.ffmxList = ffmxList;
	}

	public int getDjzt() {
		return djzt;
	}

	public void setDjzt(int djzt) {
		this.djzt = djzt;
	}

	public String getWjid() {
		return wjid;
	}

	public void setWjid(String wjid) {
		this.wjid = wjid;
	}

	public String getWjmc() {
		return wjmc;
	}

	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}

	public String getWjlx() {
		return wjlx;
	}

	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
	}

	public QuestionnaireStatus getWjzt() {
		return wjzt;
	}

	public void setWjzt(QuestionnaireStatus wjzt) {
		this.wjzt = wjzt;
	}

	public String getJsy() {
		return jsy;
	}

	public void setJsy(String jsy) {
		this.jsy = jsy;
	}

	public String getJwy() {
		return jwy;
	}

	public void setJwy(String jwy) {
		this.jwy = jwy;
	}

	public String getWjzf() {
		return wjzf;
	}

	public void setWjzf(String wjzf) {
		this.wjzf = wjzf;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getGqsj() {
		return gqsj;
	}

	public void setGqsj(String gqsj) {
		this.gqsj = gqsj;
	}

	public String getFblx() {
		return fblx;
	}

	public void setFblx(String fblx) {
		this.fblx = fblx;
	}

	public String getCjrmc() {
		return cjrmc;
	}

	public void setCjrmc(String cjrmc) {
		this.cjrmc = cjrmc;
	}

	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public String getDjrid() {
		return djrid;
	}

	public void setDjrid(String djrid) {
		this.djrid = djrid;
	}

	public String getWjyxj() {
		return wjyxj;
	}

	public void setWjyxj(String wjyxj) {
		this.wjyxj = wjyxj;
	}

	@Override
	public String toString() {
		return "WjglModel [djrid=" + djrid + ", wjid=" + wjid + ", wjmc=" + wjmc + ", wjlx=" + wjlx + ", wjzt=" + wjzt
				+ ", jsy=" + jsy + ", jwy=" + jwy + ", wjzf=" + wjzf + ", cjr=" + cjr + ", cjsj=" + cjsj + ", gqsj="
				+ gqsj + ", fblx=" + fblx + ", cjrmc=" + cjrmc + ", wjyxj=" + wjyxj + ", searchModel=" + searchModel
				+ ", djzt=" + djzt + ", ffzt=" + ffzt + ", ffmxList=" + ffmxList + "]";
	}
}
