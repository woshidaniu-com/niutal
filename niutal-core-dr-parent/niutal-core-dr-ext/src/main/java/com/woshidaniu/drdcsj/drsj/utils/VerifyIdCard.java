/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.utils;

/**
 * @author 		：康康（1571）
 * @description	： 验证身份证，修改成静态工具类，减少对象创建
 */
public class VerifyIdCard {

	// wi =2(n-1)(mod 11);加权因子
	private static final int[] WI = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// 校验码
	private static final int[] VI = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };

	// 校验身份证的校验码
	public static boolean verify(String idcard) {
		int[] ai = new int[18];
		if (idcard.length() == 15) {
			idcard = uptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getVerify(ai, idcard))) {
			return true;
		}
		return false;
	}

	// 15位转18位
	private static String uptoeighteen(String fifteen) {
		StringBuffer eighteen = new StringBuffer(fifteen);
		eighteen = eighteen.insert(6, "19");
		return eighteen.toString();
	}

	// 计算最后一位校验值
	private static String getVerify(int[] ai, String eighteen) {
		int remain = 0;
		if (eighteen.length() == 18) {
			eighteen = eighteen.substring(0, 17);
		}
		if (eighteen.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eighteen.substring(i, i + 1);
				ai[i] = Integer.valueOf(k);
			}
			for (int i = 0; i < 17; i++) {
				sum += WI[i] * ai[i];
			}
			remain = sum % 11;
		}
		return remain == 2 ? "X" : String.valueOf(VI[remain]);

	}
}
