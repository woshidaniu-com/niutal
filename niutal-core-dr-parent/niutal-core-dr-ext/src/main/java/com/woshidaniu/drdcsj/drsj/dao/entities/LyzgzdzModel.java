/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

/**
 * @author 982
 * 列对应的验证规则
 */
public class LyzgzdzModel{
	// lyzgzdzid varchar2(32) 32 TRUE TRUE TRUE
	private String lyzgzdzid;
	// yzgzid varchar2(32) 32 TRUE TRUE TRUE
	private String yzgzid;
	// 验证规则参数
	private String yzgzcs;
	// 配置规则验证不通过提示信息，不配置使用默认提示 varchar2(100) 100 FALSE FALSE
	private String gzts;
	private String lhyz; 
	// 联合验证，
	// 1为验证列，0为默认非联合验证
	// 如果是联合验证，仅在验证列配置上做验证，后续的配置不做验证（以参数配合第一列验证）
	//private String drlpzid;
	
	//需验证的列名
	private String drl;
	//导入模块代码
	private String drmkdm;
	//联合验证列
	private String lhyzdrl;
	// 对应的验证规则列表
	private YzgzModel yzgzModel;

	public String getLyzgzdzid() {
		return lyzgzdzid;
	}

	public void setLyzgzdzid(String lyzgzdzid) {
		this.lyzgzdzid = lyzgzdzid;
	}

	public String getYzgzid() {
		return yzgzid;
	}

	public void setYzgzid(String yzgzid) {
		this.yzgzid = yzgzid;
	}

	public String getYzgzcs() {
		return yzgzcs;
	}

	public void setYzgzcs(String yzgzcs) {
		this.yzgzcs = yzgzcs;
	}

	public String getGzts() {
		return gzts;
	}

	public void setGzts(String gzts) {
		this.gzts = gzts;
	}

	public YzgzModel getYzgzModel() {
		return yzgzModel;
	}

	public void setYzgzModel(YzgzModel yzgzModel) {
		this.yzgzModel = yzgzModel;
	}

	public String getLhyz() {
		return lhyz;
	}

	public void setLhyz(String lhyz) {
		this.lhyz = lhyz;
	}

	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getLhyzdrl() {
		return lhyzdrl;
	}

	public void setLhyzdrl(String lhyzdrl) {
		this.lhyzdrl = lhyzdrl;
	}

	public String[] getParam() {
		if (null == this.getYzgzcs()) {
			return null;
		} else {
			return this.getYzgzcs().split(",");
		}
	}

	@Override
	public String toString() {
		return "LyzgzdzModel [lyzgzdzid=" + lyzgzdzid + ", yzgzid=" + yzgzid + ", yzgzcs=" + yzgzcs + ", gzts=" + gzts
				+ ", lhyz=" + lhyz + ", drl=" + drl + ", drmkdm=" + drmkdm + ", lhyzdrl=" + lhyzdrl + ", yzgzModel="
				+ yzgzModel + "]";
	}
}
