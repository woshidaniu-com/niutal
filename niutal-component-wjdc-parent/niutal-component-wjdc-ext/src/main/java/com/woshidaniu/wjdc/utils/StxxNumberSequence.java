/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.utils;

/**
 * @author ：康康（1571）
 * @description ： 试题选项序列
 */
public class StxxNumberSequence {

	private int stIndex;
	private int xxIndex = 1;
	private boolean debug;

	public StxxNumberSequence(int stIndex, boolean debug) {
		super();
		this.stIndex = stIndex;
		this.debug = debug;
	}
	/**
	 * @description ： 下一个选项序列
	 * @param xxmc
	 * @return
	 */
	public String next(String xxmc) {

		String numberStr = stIndex + "--" + xxIndex;
		xxIndex++;
		if (debug) {
			numberStr = numberStr + "(" + xxmc + ")";
		}
		return numberStr;
	}
}