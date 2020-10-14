package com.woshidaniu.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.basicutils.BooleanUtils;

/**
 * 
 *@类名称:TimeSettingModel.java
 *@类描述：公共功能开放时间信息模型
 *@创建人：kangzhidong
 *@创建时间：2015-2-10 上午09:33:04
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class TimeSettingModel extends ModelBase {

	/** 功能模块代码 */
	private String gnmkdm;
	/** 操作代码 */
	private String czdm;
	/** 开始时间 */
	private String kssj;
	/** 结束时间 */
	private String jssj;
	/** 是否使用（1：使用；2：停用） */
	private String sfsy;
	/** 提示信息 */
	private String tsxx;
	/** 备注信息 */
	private String bz;
	
	
	/** wwb 增加 **/
	private String gnmkmc;	//功能模块名称
	private String fjgndm;	//父级功能代码
	private String cdjb;	//菜单级别
	private String xssx;	//显示顺序
	private String czmc;	//操作名称
	
	private List<String> gnmkdmList;//功能模块代码列表
	private List<String> czdmList;	//操作代码列表
	private List<String> kssjList;	//开始时间列表
	private List<String> jssjList;	//结束时间列表
	private List<String> sfsyList;	//是否使用列表
	private List<String> tsxxList;	//提示信息列表
	/** wwb 增加 end**/
	
	public String getGnmkmc() {
		return gnmkmc;
	}

	public List<String> getGnmkdmList() {
		return gnmkdmList;
	}

	public void setGnmkdmList(List<String> gnmkdmList) {
		this.gnmkdmList = gnmkdmList;
	}

	public List<String> getCzdmList() {
		return czdmList;
	}

	public void setCzdmList(List<String> czdmList) {
		this.czdmList = czdmList;
	}

	public List<String> getKssjList() {
		return kssjList;
	}

	public void setKssjList(List<String> kssjList) {
		this.kssjList = kssjList;
	}

	public List<String> getJssjList() {
		return jssjList;
	}

	public void setJssjList(List<String> jssjList) {
		this.jssjList = jssjList;
	}

	public List<String> getSfsyList() {
		return sfsyList;
	}

	public void setSfsyList(List<String> sfsyList) {
		this.sfsyList = sfsyList;
	}

	public List<String> getTsxxList() {
		return tsxxList;
	}

	public void setTsxxList(List<String> tsxxList) {
		this.tsxxList = tsxxList;
	}

	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}

	public String getFjgndm() {
		return fjgndm;
	}

	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}

	public String getCdjb() {
		return cdjb;
	}

	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getCzmc() {
		return czmc;
	}

	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}

	private String leafStr = "false";// 是否叶子节点
	@SuppressWarnings("unused")
	private boolean leaf = false;// 是否叶子节点
	private boolean expanded = false;// 默认展开
	
	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getSfsy() {
		return sfsy;
	}

	public void setSfsy(String sfsy) {
		this.sfsy = sfsy;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getTsxx() {
		return tsxx;
	}

	public void setTsxx(String tsxx) {
		this.tsxx = tsxx;
	}

	public String getLeafStr() {
		return leafStr;
	}

	public void setLeafStr(String leafStr) {
		this.leafStr = leafStr;
	}

	public boolean isLeaf() {
		return BooleanUtils.parse(getLeafStr());
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	
}
