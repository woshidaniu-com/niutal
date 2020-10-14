package com.woshidaniu.entities;



/**
 * 
 *@类名称:XxzywhModel.java
 *@类描述：学信专业维护
 *@创建人：gc
 *@创建时间：2015-11-6 下午01:49:59
 *@版本号:v1.0
 */
public class XxzywhModel extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xxzydmb_id;//id
	private String pcdm;//批次代码
	private String pcmc;//批次名称
	private String zydm;//专业代码
	private String zymc;//专业名称
	private String xkdm;//学科代码
	private String xkmc;//学科名称
	private String mldm;//门类代码
	private String mlmc;//门类名称
	
	
	public String getXxzydmb_id() {
		return xxzydmb_id;
	}
	public void setXxzydmb_id(String xxzydmbId) {
		xxzydmb_id = xxzydmbId;
	}
	public String getPcmc() {
		return pcmc;
	}
	public void setPcmc(String pcmc) {
		this.pcmc = pcmc;
	}
	public String getPcdm() {
		return pcdm;
	}
	public void setPcdm(String pcdm) {
		this.pcdm = pcdm;
	}
	public String getZydm() {
		return zydm;
	}
	public void setZydm(String zydm) {
		this.zydm = zydm;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public String getXkdm() {
		return xkdm;
	}
	public void setXkdm(String xkdm) {
		this.xkdm = xkdm;
	}
	public String getXkmc() {
		return xkmc;
	}
	public void setXkmc(String xkmc) {
		this.xkmc = xkmc;
	}
	public String getMldm() {
		return mldm;
	}
	public void setMldm(String mldm) {
		this.mldm = mldm;
	}
	public String getMlmc() {
		return mlmc;
	}
	public void setMlmc(String mlmc) {
		this.mlmc = mlmc;
	}
	
	

}
