/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

/**
 * @author 982
 * 插入模板， 配置具体数据如何插入数据库 不配置，则插入默认配置表
 */
public class CrpzModel{
	
	// sjczid 数据操作id
	private String sjczid;
	// 导入配置id
	private String drmkdm;
	// 1、map类型 map:serviceName 或者实现类全路径
	// 2、array类型 array:serviceName 或者实现类全路径
	// 3、自己根据格式化后的数据进行sql执行插入
	// 例如: insert into A(key.key2,key3) values(#name,#pass,'aaaa');
	// 多条执行 分号分隔
	private String crpz;
	// "1  仅插入、2  仅更新、	3  插入并更新(提示)、4  插入并更新(无提示)"
	private String crfs;
	// 扩展类支持sping bean id、类全路径" varchar2(200) 200 FALSE FALSE
	private String kzl;
	
	public String getSjczid() {
		return sjczid;
	}

	public void setSjczid(String sjczid) {
		this.sjczid = sjczid;
	}

	public String getCrfs() {
		return crfs;
	}

	public void setCrfs(String crfs) {
		this.crfs = crfs;
	}

	public String getKzl() {
		return kzl;
	}

	public void setKzl(String kzl) {
		this.kzl = kzl;
	}

	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getCrpz() {
		return crpz;
	}

	public void setCrpz(String crpz) {
		this.crpz = crpz;
	}

	@Override
	public String toString() {
		return "CrpzModel [sjczid=" + sjczid + ", drmkdm=" + drmkdm + ", crpz=" + crpz + ", crfs=" + crfs + ", kzl="
				+ kzl + "]";
	}
}
