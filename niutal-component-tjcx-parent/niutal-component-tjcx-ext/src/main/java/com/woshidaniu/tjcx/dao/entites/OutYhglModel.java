package com.woshidaniu.tjcx.dao.entites;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * 
 * 类名称：YhglModel 类描述： 用户管理MODEL 创建人：Administrator 创建时间：2012-4-10 下午06:46:43
 * 修改人：caozf 修改时间：2012-7-4 下午13:46:43 修改备注：
 * 
 * @version
 * 
 */
public class OutYhglModel extends ModelBase {

	private static final long serialVersionUID = 1479974290911672755L;
	private String zgh;// 职工号
	private String xm;// 姓名
	private String mm;// 密码
	private String lxdh;// 联系电话
	private String dzyx;// 电子邮箱
	private String sfqy;// 是否启用
	private String jgdm;// 机构代码(部门代码)
	private String jgmc;// 机构名称(部门名称)
	private String cydm_id_bmlb;// 机构类别
	private String pkValue;

	private String lxmc;// 用户类型名称

	private String[] cbvjsxx; // 角色信息主键

	private String jsdm;// 角色代码

	private String jsmc;// 角色名称

	private String jss;// 拥有角色数

	private String ymm;// 原密码

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getZgh() {
		return zgh;
	}

	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String[] getCbvjsxx() {
		return cbvjsxx;
	}

	public void setCbvjsxx(String[] cbvjsxx) {
		this.cbvjsxx = cbvjsxx;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getJsmc() {
		return jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getJss() {
		return jss;
	}

	public void setJss(String jss) {
		this.jss = jss;
	}

	public String getYmm() {
		return ymm;
	}

	public void setYmm(String ymm) {
		this.ymm = ymm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getCydm_id_bmlb() {
		return cydm_id_bmlb;
	}

	public void setCydm_id_bmlb(String cydmIdBmlb) {
		cydm_id_bmlb = cydmIdBmlb;
	}

}
