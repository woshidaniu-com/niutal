/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.utils;
/**
 * @author ：康康（1571）
 * @description ： 试题选项序列
 */
public class WjstUtil {

	private static final String ST_ID_PREFIX = "stid_";
	
	private static final int ST_ID_PREFIX_LENGHT = ST_ID_PREFIX.length();
	
	private static final String STXX_ID_PREFIXX = "xxid_";
	
	private static final int STXX_ID_PREFIXX_LENGHT = STXX_ID_PREFIXX.length();
	
	public static int parseStSequence(String stid) {
		String trimStid = stid.trim();
		int indexof = trimStid.indexOf(ST_ID_PREFIX);
		String xxIndex = trimStid.substring(indexof+ST_ID_PREFIX_LENGHT, trimStid.length());
		int xxIndexNumber = Integer.parseInt(xxIndex);
		int sequence = xxIndexNumber + 1;
		return sequence;
	}
	
	public static int parseStxxSequence(String stxxid) {
		String trimStxxid = stxxid.trim();
		int indexof = trimStxxid.indexOf(STXX_ID_PREFIXX);
		String stxxIndex = trimStxxid.substring(indexof+STXX_ID_PREFIXX_LENGHT, trimStxxid.length());
		int stxxIndexNumber = Integer.parseInt(stxxIndex);
		int sequence = stxxIndexNumber + 1;
		return sequence;
	}
	
	public static void main(String[] args) {
		System.out.println(parseStSequence("stid_0"));
		System.out.println(parseStxxSequence("stid_0_xxid_100"));
	}
}
