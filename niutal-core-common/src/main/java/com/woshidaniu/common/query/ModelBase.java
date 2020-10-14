package com.woshidaniu.common.query;

import java.util.Map;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.log.User;


/**
 * 
* 类描述：所有model的基类 
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:51:28 
* 修改人：caozf 
* 修改时间：2012-07-04 上午13:51:28 
* 修改备注：  
* 修改人：kangzhidong 
* 修改时间：2017-05-20 上午13:11:28 
* 修改备注：  合并教务框架的基础对象属性
* @version 
*
 */
@SuppressWarnings("serial")
public class ModelBase  extends com.woshidaniu.basemodel.BaseModel{

	public QueryModel queryModel = new QueryModel();
	public Map<String, Object> logModel = null;
	protected User user = null;
	
	protected String zjr;  //增加人
	protected String zjsj; //增加时间
	protected String xgr;  //修改人
	protected String xgsj; //修改时间
	
	protected String sortNamePro; //queryModel中传值个别功能无效
	protected String selectSql;
	protected String fromSql;
	
	protected String yhm; // 用户名[职工号/学号]，session可获取
	protected String xm; // 姓名，session可获取
	protected String jsdm;// 角色代码
	protected String doType;
	
	//学生/教师公用属性
	
	protected String jg_id; // 所在学院（关联部门代码表ID）
	protected String jgmc;// 部门名称
	protected String bmdm_id;//部门代码
	protected String bmmc;//部门名称
	
	protected String xx_id; // 所属系（关联专业代码表ID)
	protected String xxdm;// 系名称
	
	protected String zyh_id; // 所属专业（关联专业代码表ID)
	protected String zyh;// 专业号
	protected String zydm;// 专业代码
	protected String zymc;// 专业名称

	protected String zyfx_id; // 所属专业方向（关联专业方向代码表ID)
	protected String zyfxdm;// 专业方向代码
	protected String zyfxmc;// 专业方向名称

	protected String bh_id; // 班级代码
	protected String bh; // 班号
	protected String bjdm;// 班级代码
	protected String bjmc;// 班级名称
	protected String bj; // 班级名称

	protected String njdm_id; // 所属年级（关联年级代码表ID）
	protected String njdm; // 年级代码（关联年级代码表ID）
	protected String njmc; // 年级名称

	protected String xqh_id; // 校区号ID
	protected String xqdm_id; // 所属校区（关联校区代码表ID）
	protected String xqdm; // 校区代码
	protected String xqmc; // 校区名称
	
	// 学年码
	protected String xnm;
	//学年码名称
	protected String xnmmc;
	// 学期码
	protected String xqm;
	//学期码名称
	protected String xqmmc;
	// 功能模块代码
	protected String gnmkdm;
	// 功能模块代码
	protected String gnmkdmKey;
	// 操作代码
	protected String czdm;
	
	protected String yhid; // 用户id

	protected String jslxdm;//角色类型代码
	protected String yhjgid;//用户机构ID
	protected String lssjjgid; // 隶属上级机构id
	protected String sfjxbm; // 是否教学部门
	protected String sfst; // 是否实体（1是 0否）
	protected String kkxy; // 开课学院或学生学院（1，开课学院 2，学生学院；3既开课学院又学生学院）
	protected String[] pks;// 批量删除时使用
	
	/**
	 * 带参数构造函数，注入用户信息
	 */
	public void setModelBase(User user){
		this.setZjr(user.getYhm());
		this.setXgr(user.getYhm());
		this.setZjsj(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		this.setXgsj(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
	}
	
	public QueryModel getQueryModel() {
		if(sortNamePro != null){
			queryModel.setSortNamePro(sortNamePro);
		}
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public Map<String, Object> getLogModel() {
		return logModel;
	}

	public void setLogModel(Map<String, Object> logModel) {
		this.logModel = logModel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getZjr() {
		return zjr;
	}

	public void setZjr(String zjr) {
		this.zjr = zjr;
	}

	public String getZjsj() {
		return zjsj;
	}

	public void setZjsj(String zjsj) {
		this.zjsj = zjsj;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getSortNamePro() {
		return sortNamePro;
	}

	public void setSortNamePro(String sortNamePro) {
		this.sortNamePro = sortNamePro;
	}

	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	public String getFromSql() {
		return fromSql;
	}

	public void setFromSql(String fromSql) {
		this.fromSql = fromSql;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jg_id) {
		this.jg_id = jg_id;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdm_id) {
		this.bmdm_id = bmdm_id;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getXx_id() {
		return xx_id;
	}

	public void setXx_id(String xx_id) {
		this.xx_id = xx_id;
	}

	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getZyh_id() {
		return zyh_id;
	}

	public void setZyh_id(String zyh_id) {
		this.zyh_id = zyh_id;
	}

	public String getZyh() {
		return zyh;
	}

	public void setZyh(String zyh) {
		this.zyh = zyh;
	}

	public String getZydm() {
		return zydm;
	}

	public void setZydm(String zydm) {
		this.zydm = zydm;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
	}

	public String getZyfx_id() {
		return zyfx_id;
	}

	public void setZyfx_id(String zyfx_id) {
		this.zyfx_id = zyfx_id;
	}

	public String getZyfxdm() {
		return zyfxdm;
	}

	public void setZyfxdm(String zyfxdm) {
		this.zyfxdm = zyfxdm;
	}

	public String getZyfxmc() {
		return zyfxmc;
	}

	public void setZyfxmc(String zyfxmc) {
		this.zyfxmc = zyfxmc;
	}

	public String getBh_id() {
		return bh_id;
	}

	public void setBh_id(String bh_id) {
		this.bh_id = bh_id;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getBjdm() {
		return bjdm;
	}

	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdm_id) {
		this.njdm_id = njdm_id;
	}

	public String getNjdm() {
		return njdm;
	}

	public void setNjdm(String njdm) {
		this.njdm = njdm;
	}

	public String getNjmc() {
		return njmc;
	}

	public void setNjmc(String njmc) {
		this.njmc = njmc;
	}

	public String getXqh_id() {
		return xqh_id;
	}

	public void setXqh_id(String xqh_id) {
		this.xqh_id = xqh_id;
	}

	public String getXqdm_id() {
		return xqdm_id;
	}

	public void setXqdm_id(String xqdm_id) {
		this.xqdm_id = xqdm_id;
	}

	public String getXqdm() {
		return xqdm;
	}

	public void setXqdm(String xqdm) {
		this.xqdm = xqdm;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXnm() {
		return xnm;
	}

	public void setXnm(String xnm) {
		this.xnm = xnm;
	}

	public String getXnmmc() {
		return xnmmc;
	}

	public void setXnmmc(String xnmmc) {
		this.xnmmc = xnmmc;
	}

	public String getXqm() {
		return xqm;
	}

	public void setXqm(String xqm) {
		this.xqm = xqm;
	}

	public String getXqmmc() {
		return xqmmc;
	}

	public void setXqmmc(String xqmmc) {
		this.xqmmc = xqmmc;
	}

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getGnmkdmKey() {
		return gnmkdmKey;
	}

	public void setGnmkdmKey(String gnmkdmKey) {
		this.gnmkdmKey = gnmkdmKey;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getYhid() {
		return yhid;
	}

	public void setYhid(String yhid) {
		this.yhid = yhid;
	}

	public String getJslxdm() {
		return jslxdm;
	}

	public void setJslxdm(String jslxdm) {
		this.jslxdm = jslxdm;
	}

	public String getYhjgid() {
		return yhjgid;
	}

	public void setYhjgid(String yhjgid) {
		this.yhjgid = yhjgid;
	}

	public String getLssjjgid() {
		return lssjjgid;
	}

	public void setLssjjgid(String lssjjgid) {
		this.lssjjgid = lssjjgid;
	}

	public String getSfjxbm() {
		return sfjxbm;
	}

	public void setSfjxbm(String sfjxbm) {
		this.sfjxbm = sfjxbm;
	}

	public String getSfst() {
		return sfst;
	}

	public void setSfst(String sfst) {
		this.sfst = sfst;
	}

	public String getKkxy() {
		return kkxy;
	}

	public void setKkxy(String kkxy) {
		this.kkxy = kkxy;
	}
	
	public String[] getPks() {
		return pks;
	}

	public void setPks(String[] pks) {
		this.pks = pks;
	}
	
}
