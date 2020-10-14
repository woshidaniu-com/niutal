package com.woshidaniu.datarange.dao.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.basicutils.BlankUtils;

/**
 * 
 * 类名称：SjfwdxbModel 
 * 类描述：数据范围对象 
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class SjfwdxModel extends CommonModel {

	private static final long serialVersionUID = 2159801764630979101L;
	private String sjfwdx_id; // 数据范围对象ID
	private String bm; 		   // 表名
	private String zddm;       // 字段代码
	private String zdmc;       // 字段名称
	private String zwmc;       // 中文名称
	private String zwpy;       // 中文拼音
	private String xssx;       // 显示顺序
	private String sfqy; 	   // 是否启用
	private String zdcx; 	   // 是否自动查询
	private String kzdx;	   // 控制对象
	//private Integer resultType; //myPagination分页插件特殊使用，用于一个页面多种分页的情况
	
	/**以下页面查询用**/
	private String ls_bmdm;    //所属学院
	private String ls_zydm;	   //所属专业
	private String ls_bjdm;	   //所属班级
	private String ls_xh;	   //学号/姓名
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
	
	public QueryModel queryModel = new QueryModel();
	
	public SjfwdxModel() {
		
	}
	
	public SjfwdxModel(String sjfwdxId, String bm, String zddm, String zdmc,
			String zwmc, String xssx, String sfqy, String zdcx) {
		super();
		sjfwdx_id = sjfwdxId;
		this.bm = bm;
		this.zddm = zddm;
		this.zdmc = zdmc;
		this.zwmc = zwmc;
		this.xssx = xssx;
		this.sfqy = sfqy;
		this.zdcx = zdcx;
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

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public String getLs_bmdm() {
		try {
			return BlankUtils.isBlank(ls_bmdm) ? null : URLDecoder.decode(ls_bmdm, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return ls_bmdm;
		}
	}

	public void setLs_bmdm(String lsBmdm) {
		ls_bmdm = lsBmdm;
	}

	public String getLs_zydm() {
		try {
			return BlankUtils.isBlank(ls_zydm) ? null : URLDecoder.decode(ls_zydm, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return ls_zydm;
		}
	}

	public void setLs_zydm(String lsZydm) {
		ls_zydm = lsZydm;
	}

	
	public String getLssjjgid() {
		return lssjjgid;
	}

	public void setLssjjgid(String lssjjgid) {
		this.lssjjgid = lssjjgid;
	}


	public String getZwpy() {
		return zwpy;
	}

	public void setZwpy(String zwpy) {
		this.zwpy = zwpy;
	}

	public String getCydm_id_bmlb() {
		return cydm_id_bmlb;
	}

	public void setCydm_id_bmlb(String cydmIdBmlb) {
		cydm_id_bmlb = cydmIdBmlb;
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

	public String getZdcx() {
		return zdcx;
	}

	public void setZdcx(String zdcx) {
		this.zdcx = zdcx;
	}

	public String getLs_bjdm() {
		return ls_bjdm;
	}

	public void setLs_bjdm(String lsBjdm) {
		ls_bjdm = lsBjdm;
	}

	public String getLs_xh() {
		try {
			return BlankUtils.isBlank(ls_xh) ? null : URLDecoder.decode(ls_xh, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return ls_xh;
		}
	}

	public void setLs_xh(String lsXh) {
		ls_xh = lsXh;
	}

	public String getKzdx() {
		return kzdx;
	}

	public void setKzdx(String kzdx) {
		this.kzdx = kzdx;
	}
	
	
}
