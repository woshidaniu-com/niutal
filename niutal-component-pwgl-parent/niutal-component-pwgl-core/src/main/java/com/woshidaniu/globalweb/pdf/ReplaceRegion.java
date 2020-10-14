/**********************************************************************
 * <pre>
 * FILE : ReplaceRegion.java
 * CLASS : ReplaceRegion
 *
 * AUTHOR : caoxu-yiyang@qq.com
 *
 * FUNCTION : TODO
 *
 *
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|   DATE   |   NAME  | REASON  | CHANGE REQ.
 *----------------------------------------------------------------------
 * 		    |2016年11月9日|caoxu-yiyang@qq.com| Created |
 * DESCRIPTION:
 * </pre>
 ***********************************************************************/

package com.woshidaniu.globalweb.pdf;

/**
 * 需要替换的区域
 * @user : caoxu-yiyang@qq.com
 * @date : 2016年11月9日
 */
public class ReplaceRegion {

	private String aliasName;
	private Float x;
	private Float y;
	private Float w;
	private Float h;
	private Integer pn;

	public ReplaceRegion(String aliasName){
		this.aliasName = aliasName;
	}

	/**
	 * 替换区域的别名
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @return
	 */
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getW() {
		return w;
	}
	public void setW(Float w) {
		this.w = w;
	}
	public Float getH() {
		return h;
	}
	public void setH(Float h) {
		this.h = h;
	}
	public Integer getPn() {
		return pn;
	}
	public void setPn(Integer pn) {
		this.pn = pn;
	}
}