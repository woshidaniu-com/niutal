package com.woshidaniu.dao.entities;

import java.io.Serializable;
import java.util.List;

import com.woshidaniu.entities.PairModel;
import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.QueryModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：系统参数设置
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年10月18日下午3:43:05
 */
@Alias(value="csszModel")
public class CsszModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// 所属功能模块代码
	private String ssgnmkdm;
	// 所属模块名称
	private String ssmkmc;

	// 系统设置ID
	private String id;
	// 字段名
	private String zdm;
	// 字段值
	private String zdz;
	/**
	 * 字段类型： 1 ：表示 下拉框，2：表示输入框，3：表示单选框，4：表示多选框
	 */
	private String zdlx;
	/**
	 * 字段来源： 数据库 ：格式如 database:查询SQL 例如 database:select jg_id as key,jg_mc as
	 * value from niutal_xtgl_jgdmb xml数据 ：格式如 basedata:(baseData.xml)文件中的id 例如
	 * basedata:isValid 固定值 ：格式如 fixed:固定值1,固定值2,...(多个用,隔开) 例如
	 * fixed:aaa,bbb,ccc 空 ：
	 */
	private String zdly;
	private List<com.woshidaniu.entities.PairModel> zdsjList;
	/**
	 * 字段值要求; 例如 required:true;stringMaxLength:30;range:[0,100] (多个用;隔开)
	 */
	private String zdzyq;
	// 所属模块
	private String ssmk;
	// 注释
	private String zs;
	// 备注
	private String bz;
	// 字段值集合
	private List<String> zdzList;
	// 存储过程返回的结果
	private String fhjg;

	public QueryModel queryModel = new QueryModel();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSsmkmc(String ssmkmc) {
		this.ssmkmc = ssmkmc;
	}

	public String getZdm() {
		return zdm;
	}

	public void setZdm(String zdm) {
		this.zdm = zdm;
	}

	public String getZdz() {
		return zdz;
	}

	public void setZdz(String zdz) {
		this.zdz = zdz;
	}

	public String getZdlx() {
		return zdlx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getZdly() {
		return zdly;
	}

	public void setZdly(String zdly) {
		this.zdly = zdly;
	}

	public List<com.woshidaniu.entities.PairModel> getZdsjList() {
		return zdsjList;
	}

	public void setZdsjList(List<PairModel> zdsjList) {
		this.zdsjList = zdsjList;
	}

	public String getZdzyq() {
		return zdzyq;
	}

	public void setZdzyq(String zdzyq) {
		this.zdzyq = zdzyq;
	}

	public String getSsmk() {
		return ssmk;
	}

	public void setSsmk(String ssmk) {
		this.ssmk = ssmk;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public List<String> getZdzList() {
		return zdzList;
	}

	public void setZdzList(List<String> zdzList) {
		this.zdzList = zdzList;
	}

	public String getFhjg() {
		return fhjg;
	}

	public void setFhjg(String fhjg) {
		this.fhjg = fhjg;
	}

	public String getSsgnmkdm() {
		return ssgnmkdm;
	}

	public void setSsgnmkdm(String ssgnmkdm) {
		this.ssgnmkdm = ssgnmkdm;
	}

	public String getSsmkmc() {
		return ssmkmc;
	}

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}


}
