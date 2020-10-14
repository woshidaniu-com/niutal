package com.woshidaniu.common.validator;

import com.woshidaniu.common.validator.error.IErrorBindingResult;
import com.woshidaniu.common.validator.rules.NotEmptyRule;
import com.woshidaniu.common.validator.rules.NotNullRule;
import com.woshidaniu.common.validator.rules.SingleEmailRule;


public class Demo {
	public static void main(String[] args) {
		DemoEntity de = new DemoEntity();
		// ��ͨʹ��
		IErrorBindingResult result = ValidatorUtils.validateEntity(de, "str", NotEmptyRule.class);
		System.out.println(result.getFieldErrorMessage("str"));
		// �Զ�����֤��Ϣ
		result = ValidatorUtils.validateEntity(de, "str", new NotEmptyRule("������ָ����֤��Ϣʾ����"));
		System.out.println(result.getFieldErrorMessage("str"));
		// ͬʱ��֤��������
		result = ValidatorUtils.validateEntity(de, "num,date", NotNullRule.class);
		System.out.println(result.getFieldErrorMessage("num") + " || " + result.getFieldErrorMessage("date"));
		// ʹ�ö����֤����
		result = ValidatorUtils.validateEntity(de, "str", NotEmptyRule.class, SingleEmailRule.class);
		System.out.println(result.getFieldErrorMessage("str"));// ����ע�����ӡ��������֤��Ϣ��
		// �������ͬʱʹ�ö������
		result = ValidatorUtils.validateEntity(de, "str,col", NotEmptyRule.class, SingleEmailRule.class);
		System.out.println(result.getFieldErrorMessage("str") + " || " + result.getFieldErrorMessage("col"));

		// =========�����ʹ�ú�����һ��====
		System.out.println(ValidatorUtils.validateValue("hello", NotEmptyRule.class, SingleEmailRule.class));
		System.out.println(ValidatorUtils.validateValue("", NotEmptyRule.class, SingleEmailRule.class));
		System.out.println(ValidatorUtils.validateValue(null, NotEmptyRule.class, SingleEmailRule.class));
		System.out.println(ValidatorUtils.validateValue("hello", NotEmptyRule.class));
	}
}
