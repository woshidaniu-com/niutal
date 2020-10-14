/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.service.common.IPasswordStrengthCheckService;

/**
 * @description	： 标准校验实现
 * 
 * 规则是：
 * 1.密码长度不得少于6位且不大于21位
 * 2.密码至少应包括字母、数字以及特殊符号中两类
 * 
 * @author 		：康康（1571）
 * @date		： 2018年10月9日 下午4:06:00
 * @version 	V1.0
 */
public class StandardPasswordStrengthCheckServiceImpl implements IPasswordStrengthCheckService{

	static final int MIN_PASS_LEN = 6;
	
	static final int MAX_PASS_LEN = 21;
	
	static final String NUMBERIC = "NUMBERIC";
	static final String OTHERS = "OTHERS";
	static final String LETTER = "LETTER";
	
	@Override
	public boolean check(String passwordChars) {
		int len = StringUtils.length(passwordChars);
		if(len == 0 || len < MIN_PASS_LEN || len > MAX_PASS_LEN) 
			return false;

		Map<String,Boolean> typeChecks = new HashMap<String,Boolean>();
		
		typeChecks.put(NUMBERIC, Boolean.FALSE);
		typeChecks.put(LETTER, Boolean.FALSE);
		typeChecks.put(OTHERS, Boolean.FALSE);
		
		for (int i = 0; i < len; i++) {
			char charAt = passwordChars.charAt(i);
			
			if(Character.isDigit(charAt)){
				typeChecks.put(NUMBERIC, Boolean.TRUE);
			}else if(Character.isLetter(charAt)){
				typeChecks.put(LETTER,Boolean.TRUE);
			}else {
				typeChecks.put(OTHERS, Boolean.TRUE);
			}
		}
		
		return isValiad(typeChecks);
	}

	protected boolean isValiad(Map<String,Boolean> typeChecks){
		
		boolean isAccept = typeChecks.get(NUMBERIC) && typeChecks.get(OTHERS);//包含数字或其他
		if(isAccept) {
			return isAccept;
		}
		
		isAccept = typeChecks.get(LETTER) && typeChecks.get(OTHERS);//包含字母或其他
		if(isAccept) {
			return isAccept;
		}
		
		isAccept = typeChecks.get(NUMBERIC) && typeChecks.get(LETTER);//包含数字和字母
		if(isAccept) {
			return isAccept;
		}
		
		return false;
	}

	@Override
	public String[] info() {
		
		return new String[]{
				"密码长度不得少于6位且不大于21位","密码至少应包括字母、数字以及特殊符号中两类"
		};
	}
}
