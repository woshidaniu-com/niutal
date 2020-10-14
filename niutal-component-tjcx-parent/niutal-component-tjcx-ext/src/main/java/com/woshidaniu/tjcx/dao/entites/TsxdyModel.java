package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 特殊项定义表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class TsxdyModel  implements Serializable{
	private static final long serialVersionUID = 1649375380151982317L;
	private String tsxmc;// 特殊项名称
	private String xmdm;// 项目ID
	private String tsxsm;// 特殊项说明
	private String xssx;// 显示顺序： 两位，不足补0 如：01,02....
	// 小数位数
	// 1.为空，则以默认方式显示，平均值保留两位小数，其他值以计算结果显示；
	// 2.为纯数字，表示所有值均包含相同的小数位数，如值为2，表示最大值、最小值、总和、平均值均保留两位小数，不足则添0；
	// 3.min:2,max:2,avg:2,sum:2，分别进行设置，必须以逗号、分号分割。标识符必须为：“min,max,avg,sum”
	private String xsws;

	public TsxdyModel() {
		super();
	}

	public TsxdyModel(String xmdm) {
		super();
		this.xmdm = xmdm;
	}

	public String getTsxmc() {
		return tsxmc;
	}

	public void setTsxmc(String tsxmc) {
		this.tsxmc = tsxmc;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getTsxsm() {
		return tsxsm;
	}

	public void setTsxsm(String tsxsm) {
		this.tsxsm = tsxsm;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getXsws() {
		return xsws;
	}

	public void setXsws(String xsws) {
		this.xsws = xsws;
	}

}
