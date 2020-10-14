package com.woshidaniu.dao.entities;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
* 
* 类名称：AncdModel 
* 类描述：按钮菜单Model
* 创建人：yijd
* 创建时间：2012-4-25 上午10:22:13 
* 修改人：yijd
* 修改时间：2012-4-25 上午10:22:13 
* 修改备注： 
* @version 
*
 */
@Alias(value="AncdModel")
public class AncdModel  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String gnmkdm;//功能模块代码
	private String gnmkmc;//功能模块名称
	private String dyym;//对应页面
	private String czdm;//操作代码
	private String czmc;//操作名称
	private String xssx;//显示顺序
	private String yhm;//用户名
	private String anys;
	private String path;
	//角色代码列表
	private List<String> jsdmList;
	private String gnczid;
	
	
	public String getGnczid() {
		return gnczid;
	}
	public void setGnczid(String gnczid) {
		this.gnczid = gnczid;
	}
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	public String getGnmkmc() {
		return gnmkmc;
	}
	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}
	public String getDyym() {
		return dyym;
	}
	public void setDyym(String dyym) {
		this.dyym = dyym;
	}
	public String getCzdm() {
		return czdm;
	}
	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}
	public String getCzmc() {
		return czmc;
	}
	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getYhm() {
		return yhm;
	}
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getAnys() {
		return anys;
	}
	public void setAnys(String anys) {
		this.anys = anys;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<String> getJsdmList() {
		return jsdmList;
	}
	public void setJsdmList(List<String> jsdmList) {
		this.jsdmList = jsdmList;
	}
	
}
