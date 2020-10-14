/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.entities;

import java.util.Arrays;
import java.util.List;

/**
 * 公用导出模型
 * @author Penghui.Qu
 */
public class ExportModel {

	public static final String WJGS_XLS = "xls";
	
	// 导出处理编号，指导出的业务编号。与数据库中配置一致。命名规则请遵循“系统名称_模块代码_功能点”
	private String dcclbh;
	// 保存业务功能中查询出的结果集。支持两种，一个是List<HashMap<String,Object>>,
	// 一个是List<Model>，业务model，程序取到值，用反射判断
	private List dataList;
	// 教师职工号，此参数为了实现自定义导出字段配置快照
	private String zgh;
	// 前后端传递使用。指未选中的字段，字符数组
	private String[] unselectCol;
	// 前后端传递使用。指已选中的字段，字符数组
	private String[] selectCol;
	// 前后端传递使用。指已选中的字段，用逗号分割
	private String selectZd;
	// 前后端传递使用。指未选中的字段，用逗号分割
	private String unselectZd;
	// 暂未使用
	private String backUrl;
	//只支持Excel格式导出(导出文件格式，xls,dbf等)
	private String exportWjgs = WJGS_XLS;
	//偏好设置id
	private String exportPHID;
	//偏好设置名称
	private String exportPHMC;
	//角色
	private String js;
	
	public String getDcclbh() {
		return dcclbh;
	}

	public void setDcclbh(String dcclbh) {
		this.dcclbh = dcclbh;
	}

	@SuppressWarnings("rawtypes")
	public List getDataList() {
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getZgh() {
		return zgh;
	}

	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String[] getUnselectCol() {
		return unselectCol;
	}

	public void setUnselectCol(String[] unselectCol) {
		this.unselectCol = unselectCol;
	}

	public String[] getSelectCol() {
		return selectCol;
	}

	public void setSelectCol(String[] selectCol) {
		this.selectCol = selectCol;
	}

	public String getSelectZd() {
		return selectZd;
	}

	public void setSelectZd(String selectZd) {
		this.selectZd = selectZd;
	}

	public String getUnselectZd() {
		return unselectZd;
	}

	public void setUnselectZd(String unselectZd) {
		this.unselectZd = unselectZd;
	}

	public String getExportWjgs() {
		return exportWjgs;
	}

	public void setExportWjgs(String exportWjgs) {
		this.exportWjgs = exportWjgs;
	}

	public String getExportPHID() {
		return exportPHID;
	}

	public void setExportPHID(String exportPHID) {
		this.exportPHID = exportPHID;
	}

	public String getExportPHMC() {
		return exportPHMC;
	}

	public void setExportPHMC(String exportPHMC) {
		this.exportPHMC = exportPHMC;
	}
	public String getJs() {
		return js;
	}
	
	public void setJs(String js) {
		this.js = js;
	}

	@Override
	public String toString() {
		return "ExportModel [dcclbh=" + dcclbh + ", dataList=" + dataList + ", zgh=" + zgh + ", unselectCol=" + Arrays.toString(unselectCol) + ", selectCol=" + Arrays.toString(selectCol) + ", selectZd=" + selectZd + ", unselectZd=" + unselectZd + ", backUrl=" + backUrl + ", exportWjgs=" + exportWjgs + ", exportPHID=" + exportPHID + ", exportPHMC=" + exportPHMC + ", js=" + js + "]";
	}
}