package com.woshidaniu.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称:XtszModel.java
 * @类描述：系统维护bean
 * @创建人：kangzhidong
 * @创建时间：2015-1-15 下午02:18:39
 * @版本号:v1.0
 */
public class XtszModel extends ModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 所属功能模块代码
	private String ssgnmkdm;
	// 所属模块名称
	private String ssmkmc;

	// 系统设置ID
	private String xtsz_id;
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
	private List<PairModel> zdsjList;
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

	//学校代码
	@Deprecated
	private String xxdm;
	//学校名称
	@Deprecated
	private String xxmc;
	//学校英文名称
	@Deprecated
	private String xxywmc;                                                                           
	//学校地址
	@Deprecated
	private String xxdz;                                                                             
	//邮政编码
	@Deprecated
	private String yzbm;   
	//行政区化码
	@Deprecated
	private String xzqhm; 
	//建校年月
	@Deprecated
	private String jxny;   
	//校庆日
	@Deprecated
	private String xqr; 
	
	//字段值集合
	private List<String> zdzList;
	//存储过程返回的结果
	private String fhjg;

	public String getJxny() {
		return jxny;
	}

	public void setJxny(String jxny) {
		this.jxny = jxny;
	}

	public String getXqr() {
		return xqr;
	}

	public void setXqr(String xqr) {
		this.xqr = xqr;
	}

	public String getLsyg() {
		return lsyg;
	}

	public void setLsyg(String lsyg) {
		this.lsyg = lsyg;
	}

	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getXxmc() {
		return xxmc;
	}

	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}

	public String getXxywmc() {
		return xxywmc;
	}

	public void setXxywmc(String xxywmc) {
		this.xxywmc = xxywmc;
	}

	public String getXxdz() {
		return xxdz;
	}

	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getXzqhm() {
		return xzqhm;
	}

	public void setXzqhm(String xzqhm) {
		this.xzqhm = xzqhm;
	}

	public String getXxbxlxm() {
		return xxbxlxm;
	}

	public void setXxbxlxm(String xxbxlxm) {
		this.xxbxlxm = xxbxlxm;
	}

	public String getXxjbzm() {
		return xxjbzm;
	}

	public void setXxjbzm(String xxjbzm) {
		this.xxjbzm = xxjbzm;
	}

	public String getXxzgbmm() {
		return xxzgbmm;
	}

	public void setXxzgbmm(String xxzgbmm) {
		this.xxzgbmm = xxzgbmm;
	}

	public String getXxzgbmmc() {
		return xxzgbmmc;
	}

	public void setXxzgbmmc(String xxzgbmmc) {
		this.xxzgbmmc = xxzgbmmc;
	}

	public String getZcm() {
		return zcm;
	}

	public void setSsmkmc(String ssmkmc) {
		this.ssmkmc = ssmkmc;
	}

	//学校半血类型码
	private String xxbxlxm; 
	//学校举办者码
	private String xxjbzm;
	//学校主管部门码 
	private String xxzgbmm;
	//学校主管部门名称
	private String xxzgbmmc;
	//历史沿革
	private String lsyg;
	//注册码
	private String zcm;
	
	public String getXtsz_id() {
		return xtsz_id;
	}

	public void setXtsz_id(String xtsz_id) {
		this.xtsz_id = xtsz_id;
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

	public List<PairModel> getZdsjList() {
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

	public void setZcm(String zcm) {
		this.zcm = zcm;
	}
	
}
