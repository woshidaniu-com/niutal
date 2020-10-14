package com.woshidaniu.entities;


/**
 * 类名称：JcsjModel 类描述：基础数据MODEL 创建人：xucy 创建时间：2012-4-13 下午01:46:43 修改人：xucy 修改时间：2012-4-13 下午01:46:43 修改备注：
 * 
 * @version
 */
@SuppressWarnings("serial")
public class JcsjModel extends CommonModel {

    private String lx;      // 类型代码
    private String dm;      // 基础数据代码
    private String mc;      // 基础数据名称
    private String ywmc;      // 基础数据英文名称
    
    private String lxdm;
    private String lxmc;    // 类型名称
    private String pkValue;

    private String xtjb;    // 系统级别，yw,xt

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getLxdm() {
        return lxdm;
    }

    public void setLxdm(String lxdm) {
        this.lxdm = lxdm;
    }

    public String getXtjb() {
        return xtjb;
    }

    public void setXtjb(String xtjb) {
        this.xtjb = xtjb;
    }

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
    
}
