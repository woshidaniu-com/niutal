package com.woshidaniu.datarange.dao.entities;

import java.util.List;
import java.util.Map;

import com.woshidaniu.entities.CommonModel;

/**
 * 
 * @package com.woshidaniu.dao.entities
 * @className: YhJssjfwModel
 * @description: 用户角色数据范围模型
 * @author : kangzhidong
 * @date : 2014-6-3
 * @time : 上午08:54:36
 */
@SuppressWarnings("serial")
public class YhJssjfwModel extends CommonModel {

	private String rownum_; // 数据行数，作为JQgrid的key
	private String yhsjfwb_id; // 用户数据范围ID
	private String yhm;// 用户名
	private String xm; // 用户姓名
	private String js_id; // 用户角色ID(角色代码)
	
	private String sjfwz_id; // 数据范围组ID
	private String sjfwzmc; // 受数据范围限制的字段名称
	private String sjfwztj; // 受数据范围限制的字段名称的中文描述
	private String kzdx;	// 数据范围控制对象
	
	private String sjfwdx_id; // 数据范围对象表id
	private String bm; // 数据范围对象表名
	private String zddm; // 数据范围对象表字段代码
	private String zwmc; // 数据范围对象表字段名称
	private String zdmc;       // 字段名称
	private String zwpy;       // 中文拼音
	private String xssx;       // 显示顺序
	private String sfqy; 	   // 是否启用
	
	// 数据范围集合
	private List<DataRangeItemModel> dataRanges;
	// 数据范围条件集合，mybatis遍历集合用
	private List<String> lists; // 数据范围条件集合，mybatis遍历集合用
	private String sfqx; // 是否全校数据范围
	private String sfqxy; // 是否全学院数据范围
	private List<String> yhmList;//用户名集合
	//数据范围集合：角色代码：用户此角色对于数据范围
	private List<Map<String,Object>> sjfwList = null;

	
	//private Integer resultType; //myPagination分页插件特殊使用，用于一个页面多种分页的情况
	
	/**以下页面查询用**/
	private String ls_bmdm;    //所属学院
	private String ls_zydm;	   //所属专业
	private String ls_njdm;	   //所属年级
	private String ls_xqdm;	   //所属校区
	private String lssjjgid;	   //隶属上级机构id（关联部门代码ID）
	
	/*1 教学院系
	2 科研机构
	3 公共服务 指图书馆、档案馆、分析测试中心、计算/网络/信息/电教/教育技术中心等学术支撑单位
	4 党务部门 含工会、团委、妇委会
	5 行政机构
	6 附属单位
	7 后勤部门
	8 校办产业
	9 其他*/
	private String cydm_id_bmlb;	   //部门类别
	
	private String jg_id;// 机构代码(部门代码)
	private String jgdm;// 机构代码(部门代码)
	private String jgmc;// 机构名称(部门名称)
	private String jsdm;// 角色代码
	private String jsmc;// 角色名称
	private String jss;// 拥有角色数
	private String sjfw;// 用户角色数据范围
	
	public String getYhsjfwb_id() {
		return yhsjfwb_id;
	}

	public void setYhsjfwb_id(String yhsjfwbId) {
		yhsjfwb_id = yhsjfwbId;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getJs_id() {
		return js_id;
	}

	public void setJs_id(String jsId) {
		js_id = jsId;
	}

	public String getSjfwz_id() {
		return sjfwz_id;
	}

	public void setSjfwz_id(String sjfwzId) {
		sjfwz_id = sjfwzId;
	}

	public String getSjfwzmc() {
		return sjfwzmc;
	}

	public void setSjfwzmc(String sjfwzmc) {
		this.sjfwzmc = sjfwzmc;
	}

	public String getSjfwztj() {
		return sjfwztj;
	}

	public void setSjfwztj(String sjfwztj) {
		this.sjfwztj = sjfwztj;
	}

	public String getSjfwdx_id() {
		return sjfwdx_id;
	}

	public void setSjfwdx_id(String sjfwdxId) {
		sjfwdx_id = sjfwdxId;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getZddm() {
		return zddm;
	}

	public void setZddm(String zddm) {
		this.zddm = zddm;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public List<DataRangeItemModel> getDataRanges() {
		return dataRanges;
	}

	public void setDataRanges(List<DataRangeItemModel> dataRanges) {
		this.dataRanges = dataRanges;
	}

	public List<String> getLists() {
		return lists;
	}

	public void setLists(List<String> lists) {
		this.lists = lists;
	}

	public String getSfqx() {
		return sfqx;
	}

	public void setSfqx(String sfqx) {
		this.sfqx = sfqx;
	}

	public String getSfqxy() {
		return sfqxy;
	}

	public void setSfqxy(String sfqxy) {
		this.sfqxy = sfqxy;
	}

	public List<String> getYhmList() {
		return yhmList;
	}

	public void setYhmList(List<String> yhmList) {
		this.yhmList = yhmList;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
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

	public String getSjfw() {
		return sjfw;
	}

	public void setSjfw(String sjfw) {
		this.sjfw = sjfw;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getRownum_() {
		return rownum_;
	}

	public void setRownum_(String rownum) {
		rownum_ = rownum;
	}

	public List<Map<String,Object>> getSjfwList() {
		return sjfwList;
	}

	public void setSjfwList(List<Map<String,Object>> sjfwList) {
		this.sjfwList = sjfwList;
	}

	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jgId) {
		jg_id = jgId;
	}

	public String getKzdx() {
		return kzdx;
	}

	public void setKzdx(String kzdx) {
		this.kzdx = kzdx;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZwpy() {
		return zwpy;
	}

	public void setZwpy(String zwpy) {
		this.zwpy = zwpy;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getLs_bmdm() {
		return ls_bmdm;
	}

	public void setLs_bmdm(String lsBmdm) {
		ls_bmdm = lsBmdm;
	}

	public String getLs_zydm() {
		return ls_zydm;
	}

	public void setLs_zydm(String lsZydm) {
		ls_zydm = lsZydm;
	}

	public String getLs_njdm() {
		return ls_njdm;
	}

	public void setLs_njdm(String lsNjdm) {
		ls_njdm = lsNjdm;
	}

	public String getLs_xqdm() {
		return ls_xqdm;
	}

	public void setLs_xqdm(String lsXqdm) {
		ls_xqdm = lsXqdm;
	}

	public String getLssjjgid() {
		return lssjjgid;
	}

	public void setLssjjgid(String lssjjgid) {
		this.lssjjgid = lssjjgid;
	}

	public String getCydm_id_bmlb() {
		return cydm_id_bmlb;
	}

	public void setCydm_id_bmlb(String cydmIdBmlb) {
		cydm_id_bmlb = cydmIdBmlb;
	}
	
	
}
