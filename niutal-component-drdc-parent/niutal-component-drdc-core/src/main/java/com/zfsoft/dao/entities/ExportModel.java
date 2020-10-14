package com.woshidaniu.dao.entities;

import java.util.List;

/**
 * 公用导出模型
 * 
 * @author Penghui.Qu
 * 
 */
public class ExportModel {

	private String dcclbh;// 导出处理编号，指导出的业务编号。与数据库中配置一致。命名规则请遵循“系统名称_模块代码_功能点”

	// 保存业务功能中查询出的结果集。支持两种，一个是List<HashMap<String,Object>>,
	// 一个是List<Model>，业务model，程序取到值，用反射判断
	private List dataList;
	private String zgh;// 教师职工号，此参数为了实现自定义导出字段配置快照
	private String[] unselectCol;// 前后端传递使用。指未选中的字段，字符数组
	private String[] selectCol;// 前后端传递使用。指已选中的字段，字符数组
	private String selectZd;// 前后端传递使用。指已选中的字段，用逗号分割
	private String unselectZd;// 前后端传递使用。指未选中的字段，用逗号分割
	private String backUrl;// 暂未使用
	private String exportWjgs;//导出文件格式，xls,dbf等
	
	private List<ExportConfigModel> colConfig;    //字段配置

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

	public void setColConfig(List<ExportConfigModel> colConfig) {
		this.colConfig = colConfig;
	}

	public List<ExportConfigModel> getColConfig() {
		return colConfig;
	}
}
