package com.woshidaniu.entities;

import java.io.File;

public class XwglModel extends CommonModel {

	private static final long serialVersionUID = 1L;
	
	
	/*通知公告面向对象控制id串*/
	protected String xsdx_xnms;
	protected String xsdx_xqms;
	protected String xsdx_xzlbs;
	protected String xsdx_kkbm_ids;		
	protected String xsdx_jg_ids;
	protected String xsdx_xqh_ids;
	protected String xsdx_ccdms;
	protected String xsdx_zyh_ids;
	protected String xsdx_zyfx_ids;
	protected String xsdx_bh_ids;
	protected String xsdx_xslbms;
	protected String xsdx_xbms;
	protected String xsdx_njdm_ids;
	protected String gwdx_jsdms;
	protected String gw_xzlbs;
	protected String gr_xsdx_xzlbs;
	protected String gr_xsdx_xh_ids;
	protected String mxdxczbj;//面向对象操作标记 0：没改动    1：有变化
	
	/*面向对象显示字段*/
	protected String keyid;			//KEYID
	protected String xbmc;      		//性别名称
	protected String xslbmc;      	//学生类别名称
	protected String xslbm;      		//学生类别名称
	protected String ccmc;      		//性别名称
	protected String xzlbmc;			//限制类别名称
	protected String zhxzdxxx;		//组合限制对象信息
	protected String gwxzdxxx;		//岗位对象信息
	protected String xh_ids;
	protected String jgh_ids;
	
	private String cjlrxn;
	private String cjlrxq;
	private String bkcjlrxn;
	private String bkcjlrxq;
	private String pjxnm;
	private String pjxqm;
	private String jxb_id;//教学班ID
	private String cj;//成绩
    private String cjsfzf;//成绩是否作废
	private String jd;//绩点
	private String cjbz;//成绩备注
	private String kcmc;//课程名称
	private String xf;//学分
	private String xnmmc;//学年码名称
	private String xqmmc;//学期码名称
	private String jsxm;//教师姓名
	private String xb;//性别
	//'成绩查询结果控制（0：不限制成绩查询，1：仅限制【正考成绩录入学年学期】成绩查询，2：仅限制【补考成绩录入学年学期】成绩查询，3：同时限制【补考成绩录入学年学期】和【正考成绩录入学年】成绩查询）
	private String xscjcxkz;
	
	
	private String xmblxh;	//项目比例序号
	private String xmblmc;	//项目比例名称
	private String xmbl;	//项目比例
	private String xmcj;	//项目成绩
	private String jgh_id;	//教工号ID
	private String kcxzmc;  //课程性质名称
	
	private String kkbmmc;//开课部门名称
	private String kclbmc;//课程类别名称
	private String kcgsmc;//课程归属名称
	
	private String kclbdm;//课程类别代码
	private String kch;//课程号
	private String cjbzdm;//成绩备注代码
	private String njdm;//年级代码
	private String bjdm;//班级代码
	private String kcxzdm;//课程性质代码
	private String kcgsdm;//课程归属代码
	
	private String jsxx;//角色信息
	private String zt;//成绩通过状态  0：全部   1：通过   -1：不通过
	private String ccdm;
	private String cjcxkzzt;	
	private String cjrsFlg;//成绩人数标识 0 无 1 有	
	private String sfxwkc;//是否学位课程
	private String jxbmc;   //教学班名称
	private String kcbj;
	private String xhxm;//模糊查询的学号姓名 
	private String jg_id_cx;//学生成绩查询jg条件
	private String zpcj;
	
	//新闻显示记录数
	private String limit;
	private String xwbh; // 标题
	private String xwbt; // 标题
	private String fbsj;// 发布时间
	private String fbr;// 发布人
	private String fbdx;// 发布对象
	private String fbnr;// 新闻内容
	private String xwnr;// 新闻内容
	private String sffb;// 是否发布
	private String sffbmc;// 是否发布(中文名称)
	private String sfzd;// 新闻是否置顶
	private String sfzdmc;// 新闻是否置顶(中文名称)
	private String fbrxm;
	private String gglb; // 公告类别S
	private String gglbmc;// 公告类别名称
	private String sfyd; // 是否阅读
	public File myFile;  // myFile属性用来封装上传的文件
	public String myFileContentType;// myFileContentType属性用来封装上传文件的类型
	public String myFileFileName;// myFileFileName属性用来封装上传文件的文件名
	public String  fjm;                    //附件名
	public String  fjsclj;                 //附件路径
	private String xwfbr;  //新闻发布人 
	
	/**
	 * 角色表Id
	 */
	private String jsb_id;
	/**
	 * 阅读人
	 */
	private String ydr;
	/**
	 * 阅读时间
	 */
	private String ydsj;
	/**
	 * 阅读数
	 */
	private String yds;

	private String[] fbdxs; // 发布对象集合
	private String fbdxmcs; // 发布对象名称集合
	private String sfzx; // 是否最新
	private String yhlx; // 用户类型

	private String xqh_id; // 校区
	private String jg_id; // 机构
	private String zyh_id; // 专业
	private String njdm_id; // 年级代码id
	private String bh_id; // 班号
	private String xbm; // 性别码
	private String xslbdm; // 学生类别代码
	private String pyccdm; // 培养层次代码
	private String mxdxlb; // 面向对象类别
	private String mxdxbh; // 面向对象编号
	private String groupId; // 拼装面向对象id
	private String xzlb;
	private String xh_id;
	private String xnm;
	private String xqm;
	private String kkbm_id;

	private String jsdm;

	private String yhm;

	
	
	public String getXsdx_xnms() {
		return xsdx_xnms;
	}

	public void setXsdx_xnms(String xsdx_xnms) {
		this.xsdx_xnms = xsdx_xnms;
	}

	public String getXsdx_xqms() {
		return xsdx_xqms;
	}

	public void setXsdx_xqms(String xsdx_xqms) {
		this.xsdx_xqms = xsdx_xqms;
	}

	public String getXsdx_xzlbs() {
		return xsdx_xzlbs;
	}

	public void setXsdx_xzlbs(String xsdx_xzlbs) {
		this.xsdx_xzlbs = xsdx_xzlbs;
	}

	public String getXsdx_kkbm_ids() {
		return xsdx_kkbm_ids;
	}

	public void setXsdx_kkbm_ids(String xsdx_kkbm_ids) {
		this.xsdx_kkbm_ids = xsdx_kkbm_ids;
	}

	public String getXsdx_jg_ids() {
		return xsdx_jg_ids;
	}

	public void setXsdx_jg_ids(String xsdx_jg_ids) {
		this.xsdx_jg_ids = xsdx_jg_ids;
	}

	public String getXsdx_xqh_ids() {
		return xsdx_xqh_ids;
	}

	public void setXsdx_xqh_ids(String xsdx_xqh_ids) {
		this.xsdx_xqh_ids = xsdx_xqh_ids;
	}

	public String getXsdx_ccdms() {
		return xsdx_ccdms;
	}

	public void setXsdx_ccdms(String xsdx_ccdms) {
		this.xsdx_ccdms = xsdx_ccdms;
	}

	public String getXsdx_zyh_ids() {
		return xsdx_zyh_ids;
	}

	public void setXsdx_zyh_ids(String xsdx_zyh_ids) {
		this.xsdx_zyh_ids = xsdx_zyh_ids;
	}

	public String getXsdx_zyfx_ids() {
		return xsdx_zyfx_ids;
	}

	public void setXsdx_zyfx_ids(String xsdx_zyfx_ids) {
		this.xsdx_zyfx_ids = xsdx_zyfx_ids;
	}

	public String getXsdx_bh_ids() {
		return xsdx_bh_ids;
	}

	public void setXsdx_bh_ids(String xsdx_bh_ids) {
		this.xsdx_bh_ids = xsdx_bh_ids;
	}

	public String getXsdx_xslbms() {
		return xsdx_xslbms;
	}

	public void setXsdx_xslbms(String xsdx_xslbms) {
		this.xsdx_xslbms = xsdx_xslbms;
	}

	public String getXsdx_xbms() {
		return xsdx_xbms;
	}

	public void setXsdx_xbms(String xsdx_xbms) {
		this.xsdx_xbms = xsdx_xbms;
	}

	public String getXsdx_njdm_ids() {
		return xsdx_njdm_ids;
	}

	public void setXsdx_njdm_ids(String xsdx_njdm_ids) {
		this.xsdx_njdm_ids = xsdx_njdm_ids;
	}

	public String getGwdx_jsdms() {
		return gwdx_jsdms;
	}

	public void setGwdx_jsdms(String gwdx_jsdms) {
		this.gwdx_jsdms = gwdx_jsdms;
	}

	public String getGw_xzlbs() {
		return gw_xzlbs;
	}

	public void setGw_xzlbs(String gw_xzlbs) {
		this.gw_xzlbs = gw_xzlbs;
	}

	public String getGr_xsdx_xzlbs() {
		return gr_xsdx_xzlbs;
	}

	public void setGr_xsdx_xzlbs(String gr_xsdx_xzlbs) {
		this.gr_xsdx_xzlbs = gr_xsdx_xzlbs;
	}

	public String getGr_xsdx_xh_ids() {
		return gr_xsdx_xh_ids;
	}

	public void setGr_xsdx_xh_ids(String gr_xsdx_xh_ids) {
		this.gr_xsdx_xh_ids = gr_xsdx_xh_ids;
	}

	public String getMxdxczbj() {
		return mxdxczbj;
	}

	public void setMxdxczbj(String mxdxczbj) {
		this.mxdxczbj = mxdxczbj;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getXbmc() {
		return xbmc;
	}

	public void setXbmc(String xbmc) {
		this.xbmc = xbmc;
	}

	public String getXslbmc() {
		return xslbmc;
	}

	public void setXslbmc(String xslbmc) {
		this.xslbmc = xslbmc;
	}

	public String getXslbm() {
		return xslbm;
	}

	public void setXslbm(String xslbm) {
		this.xslbm = xslbm;
	}

	public String getCcmc() {
		return ccmc;
	}

	public void setCcmc(String ccmc) {
		this.ccmc = ccmc;
	}

	public String getXzlbmc() {
		return xzlbmc;
	}

	public void setXzlbmc(String xzlbmc) {
		this.xzlbmc = xzlbmc;
	}

	public String getZhxzdxxx() {
		return zhxzdxxx;
	}

	public void setZhxzdxxx(String zhxzdxxx) {
		this.zhxzdxxx = zhxzdxxx;
	}

	public String getGwxzdxxx() {
		return gwxzdxxx;
	}

	public void setGwxzdxxx(String gwxzdxxx) {
		this.gwxzdxxx = gwxzdxxx;
	}

	public String getXh_ids() {
		return xh_ids;
	}

	public void setXh_ids(String xh_ids) {
		this.xh_ids = xh_ids;
	}

	public String getJgh_ids() {
		return jgh_ids;
	}

	public void setJgh_ids(String jgh_ids) {
		this.jgh_ids = jgh_ids;
	}

	public String getCjlrxn() {
		return cjlrxn;
	}

	public void setCjlrxn(String cjlrxn) {
		this.cjlrxn = cjlrxn;
	}

	public String getCjlrxq() {
		return cjlrxq;
	}

	public void setCjlrxq(String cjlrxq) {
		this.cjlrxq = cjlrxq;
	}

	public String getBkcjlrxn() {
		return bkcjlrxn;
	}

	public void setBkcjlrxn(String bkcjlrxn) {
		this.bkcjlrxn = bkcjlrxn;
	}

	public String getBkcjlrxq() {
		return bkcjlrxq;
	}

	public void setBkcjlrxq(String bkcjlrxq) {
		this.bkcjlrxq = bkcjlrxq;
	}

	public String getPjxnm() {
		return pjxnm;
	}

	public void setPjxnm(String pjxnm) {
		this.pjxnm = pjxnm;
	}

	public String getPjxqm() {
		return pjxqm;
	}

	public void setPjxqm(String pjxqm) {
		this.pjxqm = pjxqm;
	}

	public String getJxb_id() {
		return jxb_id;
	}

	public void setJxb_id(String jxb_id) {
		this.jxb_id = jxb_id;
	}

	public String getCj() {
		return cj;
	}

	public void setCj(String cj) {
		this.cj = cj;
	}

	public String getCjsfzf() {
		return cjsfzf;
	}

	public void setCjsfzf(String cjsfzf) {
		this.cjsfzf = cjsfzf;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getCjbz() {
		return cjbz;
	}

	public void setCjbz(String cjbz) {
		this.cjbz = cjbz;
	}

	public String getKcmc() {
		return kcmc;
	}

	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}

	public String getXf() {
		return xf;
	}

	public void setXf(String xf) {
		this.xf = xf;
	}

	public String getXnmmc() {
		return xnmmc;
	}

	public void setXnmmc(String xnmmc) {
		this.xnmmc = xnmmc;
	}

	public String getXqmmc() {
		return xqmmc;
	}

	public void setXqmmc(String xqmmc) {
		this.xqmmc = xqmmc;
	}

	public String getJsxm() {
		return jsxm;
	}

	public void setJsxm(String jsxm) {
		this.jsxm = jsxm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getXscjcxkz() {
		return xscjcxkz;
	}

	public void setXscjcxkz(String xscjcxkz) {
		this.xscjcxkz = xscjcxkz;
	}

	public String getXmblxh() {
		return xmblxh;
	}

	public void setXmblxh(String xmblxh) {
		this.xmblxh = xmblxh;
	}

	public String getXmblmc() {
		return xmblmc;
	}

	public void setXmblmc(String xmblmc) {
		this.xmblmc = xmblmc;
	}

	public String getXmbl() {
		return xmbl;
	}

	public void setXmbl(String xmbl) {
		this.xmbl = xmbl;
	}

	public String getXmcj() {
		return xmcj;
	}

	public void setXmcj(String xmcj) {
		this.xmcj = xmcj;
	}

	public String getJgh_id() {
		return jgh_id;
	}

	public void setJgh_id(String jgh_id) {
		this.jgh_id = jgh_id;
	}

	public String getKcxzmc() {
		return kcxzmc;
	}

	public void setKcxzmc(String kcxzmc) {
		this.kcxzmc = kcxzmc;
	}

	public String getKkbmmc() {
		return kkbmmc;
	}

	public void setKkbmmc(String kkbmmc) {
		this.kkbmmc = kkbmmc;
	}

	public String getKclbmc() {
		return kclbmc;
	}

	public void setKclbmc(String kclbmc) {
		this.kclbmc = kclbmc;
	}

	public String getKcgsmc() {
		return kcgsmc;
	}

	public void setKcgsmc(String kcgsmc) {
		this.kcgsmc = kcgsmc;
	}

	public String getKclbdm() {
		return kclbdm;
	}

	public void setKclbdm(String kclbdm) {
		this.kclbdm = kclbdm;
	}

	public String getKch() {
		return kch;
	}

	public void setKch(String kch) {
		this.kch = kch;
	}

	public String getCjbzdm() {
		return cjbzdm;
	}

	public void setCjbzdm(String cjbzdm) {
		this.cjbzdm = cjbzdm;
	}

	public String getNjdm() {
		return njdm;
	}

	public void setNjdm(String njdm) {
		this.njdm = njdm;
	}

	public String getBjdm() {
		return bjdm;
	}

	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}

	public String getKcxzdm() {
		return kcxzdm;
	}

	public void setKcxzdm(String kcxzdm) {
		this.kcxzdm = kcxzdm;
	}

	public String getKcgsdm() {
		return kcgsdm;
	}

	public void setKcgsdm(String kcgsdm) {
		this.kcgsdm = kcgsdm;
	}

	public String getJsxx() {
		return jsxx;
	}

	public void setJsxx(String jsxx) {
		this.jsxx = jsxx;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getCcdm() {
		return ccdm;
	}

	public void setCcdm(String ccdm) {
		this.ccdm = ccdm;
	}

	public String getCjcxkzzt() {
		return cjcxkzzt;
	}

	public void setCjcxkzzt(String cjcxkzzt) {
		this.cjcxkzzt = cjcxkzzt;
	}

	public String getCjrsFlg() {
		return cjrsFlg;
	}

	public void setCjrsFlg(String cjrsFlg) {
		this.cjrsFlg = cjrsFlg;
	}

	public String getSfxwkc() {
		return sfxwkc;
	}

	public void setSfxwkc(String sfxwkc) {
		this.sfxwkc = sfxwkc;
	}

	public String getJxbmc() {
		return jxbmc;
	}

	public void setJxbmc(String jxbmc) {
		this.jxbmc = jxbmc;
	}

	public String getKcbj() {
		return kcbj;
	}

	public void setKcbj(String kcbj) {
		this.kcbj = kcbj;
	}

	public String getXhxm() {
		return xhxm;
	}

	public void setXhxm(String xhxm) {
		this.xhxm = xhxm;
	}

	public String getJg_id_cx() {
		return jg_id_cx;
	}

	public void setJg_id_cx(String jg_id_cx) {
		this.jg_id_cx = jg_id_cx;
	}

	public String getZpcj() {
		return zpcj;
	}

	public void setZpcj(String zpcj) {
		this.zpcj = zpcj;
	}

	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getXnm() {
		return xnm;
	}

	public void setXnm(String xnm) {
		this.xnm = xnm;
	}

	public String getXqm() {
		return xqm;
	}

	public void setXqm(String xqm) {
		this.xqm = xqm;
	}

	public String getKkbm_id() {
		return kkbm_id;
	}

	public void setKkbm_id(String kkbmId) {
		kkbm_id = kkbmId;
	}

	public String getXh_id() {
		return xh_id;
	}

	public void setXh_id(String xhId) {
		xh_id = xhId;
	}

	public String getXzlb() {
		return xzlb;
	}

	public void setXzlb(String xzlb) {
		this.xzlb = xzlb;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMxdxlb() {
		return mxdxlb;
	}

	public void setMxdxlb(String mxdxlb) {
		this.mxdxlb = mxdxlb;
	}

	public String getMxdxbh() {
		return mxdxbh;
	}

	public void setMxdxbh(String mxdxbh) {
		this.mxdxbh = mxdxbh;
	}

	public String getXqh_id() {
		return xqh_id;
	}

	public void setXqh_id(String xqhId) {
		xqh_id = xqhId;
	}

	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jgId) {
		jg_id = jgId;
	}

	public String getZyh_id() {
		return zyh_id;
	}

	public void setZyh_id(String zyhId) {
		zyh_id = zyhId;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}

	public String getBh_id() {
		return bh_id;
	}

	public void setBh_id(String bhId) {
		bh_id = bhId;
	}

	public String getXbm() {
		return xbm;
	}

	public void setXbm(String xbm) {
		this.xbm = xbm;
	}

	public String getXslbdm() {
		return xslbdm;
	}

	public void setXslbdm(String xslbdm) {
		this.xslbdm = xslbdm;
	}

	public String getPyccdm() {
		return pyccdm;
	}

	public void setPyccdm(String pyccdm) {
		this.pyccdm = pyccdm;
	}

	public String getFbrxm() {
		return fbrxm;
	}

	public void setFbrxm(String fbrxm) {
		this.fbrxm = fbrxm;
	}

	public String getXwnr() {
		return xwnr;
	}

	public void setXwnr(String xwnr) {
		this.xwnr = xwnr;
	}

	public String getXwbh() {
		return xwbh;
	}

	public void setXwbh(String xwbh) {
		this.xwbh = xwbh;
	}

	public String getFbdx() {
		return fbdx;
	}

	public void setFbdx(String fbdx) {
		this.fbdx = fbdx;
	}

	public String getXwbt() {
		return xwbt;
	}

	public void setXwbt(String xwbt) {
		this.xwbt = xwbt;
	}

	public String getFbsj() {
		return fbsj;
	}

	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}

	public String getFbr() {
		return fbr;
	}

	public void setFbr(String fbr) {
		this.fbr = fbr;
	}

	public String getFbnr() {
		return fbnr;
	}

	public void setFbnr(String fbnr) {
		this.fbnr = fbnr;
	}

	public String getSffb() {
		return sffb;
	}

	public void setSffb(String sffb) {
		this.sffb = sffb;
	}

	public String getSfzd() {
		return sfzd;
	}

	public void setSfzd(String sfzd) {
		this.sfzd = sfzd;
	}

	public String[] getFbdxs() {
		return fbdxs;
	}

	public void setFbdxs(String[] fbdxs) {
		this.fbdxs = fbdxs;
	}

	public String getFbdxmcs() {
		return fbdxmcs;
	}

	public void setFbdxmcs(String fbdxmcs) {
		this.fbdxmcs = fbdxmcs;
	}

	public String getSfzx() {
		return sfzx;
	}

	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getYdr() {
		return ydr;
	}

	public void setYdr(String ydr) {
		this.ydr = ydr;
	}

	public String getJsb_id() {
		return jsb_id;
	}

	public void setJsb_id(String jsbId) {
		jsb_id = jsbId;
	}

	public String getYdsj() {
		return ydsj;
	}

	public void setYdsj(String ydsj) {
		this.ydsj = ydsj;
	}

	public String getYds() {
		return yds;
	}

	public void setYds(String yds) {
		this.yds = yds;
	}

	public String getSffbmc() {
		return sffbmc;
	}

	public void setSffbmc(String sffbmc) {
		this.sffbmc = sffbmc;
	}

	public String getSfzdmc() {
		return sfzdmc;
	}

	public void setSfzdmc(String sfzdmc) {
		this.sfzdmc = sfzdmc;
	}

	public String getGglb() {
		return gglb;
	}

	public void setGglb(String gglb) {
		this.gglb = gglb;
	}

	public String getGglbmc() {
		return gglbmc;
	}

	public void setGglbmc(String gglbmc) {
		this.gglbmc = gglbmc;
	}

	public String getSfyd() {
		return sfyd;
	}

	public void setSfyd(String sfyd) {
		this.sfyd = sfyd;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getFjm() {
		return fjm;
	}

	public void setFjm(String fjm) {
		this.fjm = fjm;
	}

	public String getFjsclj() {
		return fjsclj;
	}

	public void setFjsclj(String fjsclj) {
		this.fjsclj = fjsclj;
	}

	public String getXwfbr() {
		return xwfbr;
	}

	public void setXwfbr(String xwfbr) {
		this.xwfbr = xwfbr;
	}
	
	
}
