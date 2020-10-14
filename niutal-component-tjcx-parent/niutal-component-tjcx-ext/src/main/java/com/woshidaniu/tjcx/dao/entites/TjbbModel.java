package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class TjbbModel  implements Serializable{
	private static final long serialVersionUID = -4525876328815740360L;
	// 数据显示用
	private List<CxzdModel> cxzdList = null;// 查询字段
	private List<BbldyModel> bblList = null;// 报表列定义
	private List<KzszModel> kzszList = null;// 快照设置
	private List<TsxdyModel> tsxList = null;// 特殊项定义

	// 报表统计
	@SuppressWarnings("rawtypes")
	private List<HashMap> tjsjList = null;// 统计报表数据
	private HashMap<String, String> bblqzfwMap = null;// 报表列取值范围
	private List<String> tsxJsZdList = null;//特殊项计算字段，avg_je,sum_cj
	private KzszModel cxtj = null;// 查询条件
	private List<YsfModel> allYsfList = null;

	public TjbbModel() {
		super();
	}

	public List<CxzdModel> getCxzdList() {
		return cxzdList;
	}

	public void setCxzdList(List<CxzdModel> cxzdList) {
		this.cxzdList = cxzdList;
	}

	public List<KzszModel> getKzszList() {
		return kzszList;
	}

	public void setKzszList(List<KzszModel> kzszList) {
		this.kzszList = kzszList;
	}

	public List<BbldyModel> getBblList() {
		return bblList;
	}

	public void setBblList(List<BbldyModel> bblList) {
		this.bblList = bblList;
	}

	public List<TsxdyModel> getTsxList() {
		return tsxList;
	}

	public void setTsxList(List<TsxdyModel> tsxList) {
		this.tsxList = tsxList;
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getTjsjList() {
		return tjsjList;
	}

	@SuppressWarnings("rawtypes")
	public void setTjsjList(List<HashMap> tjsjList) {
		this.tjsjList = tjsjList;
	}

	public KzszModel getCxtj() {
		return cxtj;
	}

	public void setCxtj(KzszModel cxtj) {
		this.cxtj = cxtj;
	}

	public HashMap<String, String> getBblqzfwMap() {
		return bblqzfwMap;
	}

	public void setBblqzfwMap(HashMap<String, String> bblqzfwMap) {
		this.bblqzfwMap = bblqzfwMap;
	}

	public List<String> getTsxJsZdList() {
		return tsxJsZdList;
	}

	public void setTsxJsZdList(List<String> tsxJsZdList) {
		this.tsxJsZdList = tsxJsZdList;
	}

	public List<YsfModel> getAllYsfList() {
		return allYsfList;
	}

	public void setAllYsfList(List<YsfModel> allYsfList) {
		this.allYsfList = allYsfList;
	}



}
