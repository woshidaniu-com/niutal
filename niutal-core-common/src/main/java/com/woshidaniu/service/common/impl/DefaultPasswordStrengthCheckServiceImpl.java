package com.woshidaniu.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.service.common.IPasswordStrengthCheckService;

/**
 * 
 * @author zhidong.kang
 *
 * @desc 系统默认校验实现，规则是：
 * 1.密码长度不得少于7位且不大于21位；
 * 2.密码至少应包括字母、数字以及特殊符号中两类；
 * 3.密码至少应包括一个大写英文字符和一个小写英文字符
 */
public class DefaultPasswordStrengthCheckServiceImpl implements IPasswordStrengthCheckService {

	static final int MIN_PASS_LEN = 7;
	
	static final int MAX_PASS_LEN = 21;
	
	static final String UPPER_CASE = "UPPER_CASE";
	static final String LOWER_CASE = "LOWER_CASE";
	static final String NUMBERIC = "NUMBERIC";
	static final String OTHERS = "OTHERS";
	
	@Override
	public boolean check(String passwordChars) {
		int len = StringUtils.length(passwordChars);
		if(len == 0 || len < MIN_PASS_LEN || len > MAX_PASS_LEN) 
			return false;

		Map<String,Boolean> typeChecks = new HashMap<String,Boolean>();
		
		typeChecks.put(UPPER_CASE, Boolean.FALSE);
		typeChecks.put(LOWER_CASE, Boolean.FALSE);
		typeChecks.put(NUMBERIC, Boolean.FALSE);
		typeChecks.put(OTHERS, Boolean.FALSE);
		
		for (int i = 0; i < len; i++) {
			char charAt = passwordChars.charAt(i);
			
			if(Character.isDigit(charAt)){
				typeChecks.put(NUMBERIC, Boolean.TRUE);
			}else if(Character.isLetter(charAt)){
				typeChecks.put(Character.isUpperCase(charAt) ? UPPER_CASE : LOWER_CASE, Boolean.TRUE);
			}else {
				typeChecks.put(OTHERS, Boolean.TRUE);
			}
			
		}
		
		return isValiad(typeChecks);
	}

	protected boolean isValiad(Map<String,Boolean> typeChecks){
		return (typeChecks.get(UPPER_CASE) 
				&& typeChecks.get(LOWER_CASE)
				&& typeChecks.get(NUMBERIC)
				&& typeChecks.get(OTHERS))
				||
				(typeChecks.get(UPPER_CASE) 
				&& typeChecks.get(LOWER_CASE)
				&& typeChecks.get(NUMBERIC))
				||
				(typeChecks.get(UPPER_CASE) 
				&& typeChecks.get(LOWER_CASE)
				&& typeChecks.get(OTHERS));
	}

	@Override
	public String[] info() {
		
		return new String[]{
				"密码长度不得少于7位且不大于21位","密码至少应包括字母、数字以及特殊符号中两类","密码至少应包括一个大写英文字符和一个小写英文字符"
				
		};
	}

	
}
