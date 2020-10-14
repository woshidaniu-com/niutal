package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.annotation.HTMLEncode;
import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;

/**
 * 类名称：JcsjModel 类描述：基础数据MODEL 创建人：xucy 创建时间：2012-4-13 下午01:46:43 修改人：xucy 修改时间：2012-4-13 下午01:46:43 修改备注：
 * 
 * @version
 */
@Alias(value="JcsjModel")
public class JcsjModel extends ModelBase {

	private static final long serialVersionUID = -8728142318468077120L;
	private String lx;      // 类型代码
    @HTMLEncode
    private String dm;      // 基础数据代码
    @HTMLEncode
    private String mc;      // 基础数据名称

    private String lxdm;
    private String lxmc;    // 类型名称
    private String yz;//验证字段
    
    private String pkValue;

    private String xtjb;    // 系统级别，yw,xt
    
    private String zt; //启用状态 0,1
    private SearchModel searchModel;
   
    
    public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public JcsjModel(String pkValue) {
		super();
		this.pkValue = pkValue;
	}

	public JcsjModel() {
		super();
	}

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

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getYz() {
		return yz;
	}

	public void setYz(String yz) {
		this.yz = yz;
	}

}
