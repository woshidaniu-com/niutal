/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.datarange;

/**
 * @author 康康（1571）
 * 数据范围提示信息对类
 */
public class DataRangeHitPairInfo {

	/**
	 *数据资源ID 
	 */
	private String zyId;
	
	/**
	 * 数据规则名称
	 */
	private String gzid;
	
	/**
	 * 表名称,真实的表名称,而非表别名 
	 */
	private String table;
	
	/**
	 * 对应字段名称,真实的字段名,而非字段别名
	 */
	private String column;

	public String getZyId() {
		return zyId;
	}

	public void setZyId(String zyId) {
		this.zyId = zyId;
	}

	public String getGzid() {
		return gzid;
	}

	public void setGzid(String gzid) {
		this.gzid = gzid;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "DataRangeHitPairInfo [zyId=" + zyId + ", gzid=" + gzid + ", table=" + table + ", column=" + column
				+ "]";
	}
}
