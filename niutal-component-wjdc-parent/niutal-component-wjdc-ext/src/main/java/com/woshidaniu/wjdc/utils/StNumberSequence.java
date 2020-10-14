/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.utils;

/**
 * @author ：康康（1571）
 * @description ： 试题数字序列
 */
public class StNumberSequence {

	private boolean debug;
	private int stIndex = 1;

	public StNumberSequence(boolean debug) {
		super();
		this.debug = debug;
	}
	/**
	 * @description ： 获取下一个数字序列
	 * @return
	 */
	public String next(String stmc) {
		String numberStr = stIndex + "";
		if (this.debug) {
			numberStr = numberStr + "(" + stmc + ")";
		}
		stIndex++;
		return numberStr;
	}
	/**
	 * @description ： 获取下一个数字序列
	 * @return
	 */
	public String next() {
		return this.next("");
	}
	/**
	 * @description ： 创建一个选项数字序列
	 * @return
	 */
	public StxxNumberSequence createXxNumberSequence() {
		return new StxxNumberSequence(stIndex, debug);
	}
}
