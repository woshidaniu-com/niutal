package com.woshidaniu.dao.entities;

import java.io.File;

import com.woshidaniu.common.query.ModelBase;

/**
 * 导入Model
 * @author Jiangdong.Yi
 *
 */
public class ImportModel extends ModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//导入配置
	private String dryzbh;	//导入验证编号
	private String drmkdm;	//导入模块代码
	private String drmkmc;	//导入模块名称
	private String drmkyzsm;	//导入模块验证说明
	private String drbm;	//导入表明
	private String drbzwmc;	//导入表中文名称
	private String zdm;	//字段名
	private String zdmc;	//字段名称
	private String zdlx;	//字段类型
	private String xssx;	//显示顺序
	private String yzcs;	//验证参数
	private String drmbzdmc;	//导入模板字段名称
	private String sfhbyz;	//是否合并验证
	
	//导入验证
	private String yzmc;	//验证名称
	private String yzlmc;	//验证类名称
	private String yzsm;	//验证说明,用于描述验证行为
	private String cshcssy;	//初始化参数索引
	private String yzlbm;	//验证类包名称
	
	//非业务属性
	private File importFile;	//导入文件
	private String drzs;	//导入总数
	private String cgs;	//成功数
	private String cws;	//错误数
	private String hbdrs;	//合并导入数
	private Object value;	//导入数据
	
	private String drwjgs; // 导入文件格式
	private String drms; // 导入模式 ：默认为insert操作，1为update操作
	private File updateFile;
	private String isThreadImport;//是否是扩展导入标志 ，1是 否则否

	public String getDryzbh() {
		return dryzbh;
	}
	public void setDryzbh(String dryzbh) {
		this.dryzbh = dryzbh;
	}
	public String getDrmkdm() {
		return drmkdm;
	}
	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}
	public String getDrmkmc() {
		return drmkmc;
	}
	public void setDrmkmc(String drmkmc) {
		this.drmkmc = drmkmc;
	}
	public String getDrmkyzsm() {
		return drmkyzsm;
	}
	public void setDrmkyzsm(String drmkyzsm) {
		this.drmkyzsm = drmkyzsm;
	}
	public String getDrbm() {
		return drbm;
	}
	public void setDrbm(String drbm) {
		this.drbm = drbm;
	}
	public String getDrbzwmc() {
		return drbzwmc;
	}
	public void setDrbzwmc(String drbzwmc) {
		this.drbzwmc = drbzwmc;
	}
	public String getZdm() {
		return zdm;
	}
	public void setZdm(String zdm) {
		this.zdm = zdm;
	}
	public String getZdmc() {
		return zdmc;
	}
	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}
	public String getZdlx() {
		return zdlx;
	}
	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getYzmc() {
		return yzmc;
	}
	public void setYzmc(String yzmc) {
		this.yzmc = yzmc;
	}
	public String getYzlmc() {
		return yzlmc;
	}
	public void setYzlmc(String yzlmc) {
		this.yzlmc = yzlmc;
	}
	public File getImportFile() {
		return importFile;
	}
	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	public String getDrzs() {
		return drzs;
	}
	public void setDrzs(String drzs) {
		this.drzs = drzs;
	}
	public String getCgs() {
		return cgs;
	}
	public void setCgs(String cgs) {
		this.cgs = cgs;
	}
	public String getCws() {
		return cws;
	}
	public void setCws(String cws) {
		this.cws = cws;
	}
	public String getYzsm() {
		return yzsm;
	}
	public void setYzsm(String yzsm) {
		this.yzsm = yzsm;
	}
	public String getYzcs() {
		return yzcs;
	}
	public void setYzcs(String yzcs) {
		this.yzcs = yzcs;
	}
	public String getCshcssy() {
		return cshcssy;
	}
	public void setCshcssy(String cshcssy) {
		this.cshcssy = cshcssy;
	}
	public String getYzlbm() {
		return yzlbm;
	}
	public void setYzlbm(String yzlbm) {
		this.yzlbm = yzlbm;
	}
	public String getDrmbzdmc() {
		return drmbzdmc;
	}
	public void setDrmbzdmc(String drmbzdmc) {
		this.drmbzdmc = drmbzdmc;
	}
	public String getHbdrs() {
		return hbdrs;
	}
	public void setHbdrs(String hbdrs) {
		this.hbdrs = hbdrs;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getSfhbyz() {
		return sfhbyz;
	}
	public void setSfhbyz(String sfhbyz) {
		this.sfhbyz = sfhbyz;
	}
	public String getDrwjgs() {
		return drwjgs;
	}
	public void setDrwjgs(String drwjgs) {
		this.drwjgs = drwjgs;
	}
	public String getDrms() {
		return drms;
	}
	public void setDrms(String drms) {
		this.drms = drms;
	}
	public File getUpdateFile() {
		return updateFile;
	}
	public void setUpdateFile(File updateFile) {
		this.updateFile = updateFile;
	}
	public String getIsThreadImport() {
		return isThreadImport;
	}
	public void setIsThreadImport(String isThreadImport) {
		this.isThreadImport = isThreadImport;
	}
	
}
