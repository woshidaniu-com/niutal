package com.woshidaniu.common.validator;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.common.validator.error.ErrorBindingResultImpl;
import com.woshidaniu.common.validator.error.IErrorBindingResult;
import com.woshidaniu.common.validator.exception.ErrorValidationException;
import com.woshidaniu.common.validator.rules.IValidateRule;

/**
 * 验证工具类。 通过java反射机制获取对象中的值作验证。 使用示例：<br>
 * <li>说明：验证框架Utils类是验证的入口。<li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class ValidatorUtils {
	/** 多个验证信息之间的分隔符 */
	public static String VALIDATE_INFO_SPLIT_SYMBAL = ";";

	/**
	 * 使用指定的验证规则对实体中指定属性值进行验证。<br>
	 * <ul>
	 * <li>三个参数均不得为空！</li><br>
	 * <li>对所有指定的属性验证所有的指定规则</li><br>
	 * </ul>
	 * 
	 * @param entity
	 *            指定的实体信息
	 * @param fieldName
	 *            实体中对应的属性名称。可以同时指定多个，即多个属性一起验证。 多个之间用“,”隔开。
	 * @param validateRules
	 *            验证的规则。这些类必须实现自framework下的ValidateRule接口
	 * @return 如果有验证报错信息，则通过IErrorBindingResult返回。
	 */
	public static IErrorBindingResult validateEntity(Object entity,
			String fieldName, Object... validateRules) {
		if (entity == null) {
			throw new IllegalArgumentException(
					"argument for entity must not be null!");
		}
		if (StringUtils.isEmpty(fieldName)) {
			throw new IllegalArgumentException("argument fieldName[Object:"
					+ entity.getClass().getSimpleName() + "] not be null!");
		}
		if (validateRules == null || validateRules.length <1) {
			throw new IllegalArgumentException(
					"argument for validateRules must not be null!");
		}
		IErrorBindingResult result = new ErrorBindingResultImpl(entity);
		String[] fields = fieldName.split(",");
		for (String field : fields) {
			for (Object rule : validateRules) {
				rule = validateAndGetObj(rule);
				IValidateRule r = (IValidateRule) rule;
				if (!r.validate(getEntityValue(entity, field))) {
					result.rejectValue(field, r.getValidateInfo());
				}
			}
		}
		return result;
	}
	
	/**
	 * 使用指定的验证规则对实体中指定属性值进行验证。如果有验证信息，则将验证信息抛出异常<br>
	 * <ul>
	 * <li>三个参数均不得为空！</li><br>
	 * <li>对所有指定的属性验证所有的指定规则</li><br>
	 * </ul>
	 * 
	 * @param entity
	 *            指定的实体信息
	 * @param fieldName
	 *            实体中对应的属性名称。可以同时指定多个，即多
	 * 
	 *            个属性一起验证。多个之间用“,”隔开。
	 * @param validateRules
	 *            验证的规则。这些类必须实现ValidateRule接口
	 * @return 如果有验证报错信息,则通过IErrorBindingResult返回。
	 * @throws ErrorValidationException
	 *             当有验证不通过的信息时，抛出该异常。
	 */
	public static void validateEntityWithException(Object entity,
			String fieldName, Object... validateRules)
			throws ErrorValidationException {
		IErrorBindingResult result = validateEntity(entity, fieldName,
				validateRules);
		if (result.hasErrors()) {
			throw new ErrorValidationException(result);
		}
	}
	
	/**
	 * 用指定的验证规则验证给定的值。将所有验证信息直接返回为字符串。<br>
	 * 这个是用于验证直接给定的内容，不需要是javaBean。<br>
	 * 
	 * @param value
	 *            指定的值
	 * @param validateRules
	 *            指定的验证规则
	 * @return 返回验证信息。如果没有，则返回""。
	 */
	public static String validateValue(Object value, Object... validateRules) {
		if (validateRules == null || validateRules.length < 1) {
			throw new IllegalArgumentException(
					"argument for validateRules must not be null!");
		}
		String result = "";// 验证信息。
		for (Object rule : validateRules) {
			rule = validateAndGetObj(rule);
			IValidateRule r = (IValidateRule) rule;
			if (!r.validate(value)) {
				result += r.getValidateInfo() + VALIDATE_INFO_SPLIT_SYMBAL;
			}
		}
		return result.endsWith(ValidatorUtils.VALIDATE_INFO_SPLIT_SYMBAL) ? result
				.substring(0, result.length() - 1)
				: result;
	}
	
	/**
	 * 用指定的验证规则验证给定的值。将所有验证信息直接返回为字符串。<br>
	 * 这个是用于验证直接给定的内容，不需要是javaBean。<br>
	 * 
	 * @param value
	 *            指定的值
	 * @param parameters
	 * 			     验证数据范围验证的参数。参数是通过构造方法传递。该数组长度为对应的验证规则
	 * 			    构造方法对应长度。参数位置为对应构造参数位置。
	 * @param validateRules
	 *            指定的验证规则
	 * @return 返回验证信息。如果没有，则返回""。
	 */
	public static String validateValueByParameter(Object value,Object[] parameters,
			Object... validateRules) {
		if (parameters == null) {
			throw new IllegalArgumentException(
					"argument for parameters must not be null!");
		}
		if (validateRules == null || validateRules.length < 1) {
			throw new IllegalArgumentException(
					"argument for validateRules must not be null!");
		}
		String result = "";// 验证信息。
		for (Object rule : validateRules) {
			rule = validateAndNewObj(rule, parameters);
			IValidateRule r = (IValidateRule) rule;
			if (!r.validate(value)) {
				result += r.getValidateInfo() + VALIDATE_INFO_SPLIT_SYMBAL;
			}
		}
		return result.endsWith(ValidatorUtils.VALIDATE_INFO_SPLIT_SYMBAL) ? result
				.substring(0, result.length() - 1)
				: result;
	}

	/**
	 * 验证该验证规则是否为规则，同时转换为实现类。
	 * 
	 * @param rule
	 *            具体的验证规则类或对象
	 * @return 返回实例化后的验证规则对象
	 */
	private static Object validateAndGetObj(Object rule) {
		if (rule == null) {
			throw new IllegalArgumentException("rule not be null!");
		}
		try {
			// 如果该对象不是IValidateRule的实现，则认为是IValidateRule实现类的class方式，即类似NotEmpty.class方式。
			if (!(rule instanceof IValidateRule)) {
				rule = ((Class<?>) rule).newInstance();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return rule;
	}
	
	/**
	 * 验证该验证规则是否为规则，同时转换为实现类(通过new 获取新对象)。
	 * @param rule
	 * 				具体的验证规则类或对象
	 * @param parameters
	 * 				验证数据范围验证的参数。参数是通过构造方法传递。该数组长度为对应的验证规则
	 * 			   	 构造方法对应长度。参数位置为对应构造参数位置。
	 * @return 返回实例化后的验证规则对象
	 */
	@SuppressWarnings("unchecked")
	private static Object validateAndNewObj(Object rule,Object[] parameters){
		if (rule == null) {
			throw new IllegalArgumentException("rule not be null!");
		}
		if (parameters == null) {
			throw new IllegalArgumentException("parameters not be null!");
		}
		try {
			//构造参数
			Class[] classs= new Class[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				classs[i] = parameters[i].getClass();
			}
			// 如果该对象不是IValidateRule的实现，则认为是IValidateRule实现类的class方式，即类似NotEmpty.class方式。
			if (!(rule instanceof IValidateRule)) {
				rule = ((Class<?>) rule).getConstructor(classs).newInstance(parameters);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return rule;
	}
	
	/**
	 * 通过java的反射机制获取指定的对象中对应属性的值！支持对象
	 * 
	 * 嵌套值的获取！<br>
	 * 如果参数为空，则直接抛出异常！
	 * 
	 * @param entity
	 *            对象
	 * @param fieldName
	 *            对象中的属性
	 * @return 返回对应的值
	 */
	private static Object getEntityValue(Object entity, String fieldName) {
		if (entity == null) {
			throw new IllegalArgumentException(
					"argument for entity must not be null!");
		}
		if (StringUtils.isEmpty(fieldName)) {
			throw new IllegalArgumentException("argument fieldName[Object:"
					+ entity.getClass().getSimpleName() + "] not be null!");
		}
		try {
			String[] fields = fieldName.split("\\.");
			Object obj = entity;
			for (int i = 0; (fields != null) && i < fields.length - 1; i++) {
				String method = "get".concat(
						fields[i].substring(0, 1).toUpperCase()).concat(
						fields[i].substring(1));
				obj = obj.getClass().getMethod(method).invoke(obj);
			}
			if (fields != null && fields.length > 0) {
				fieldName = fields[fields.length - 1];
			}
			String method = "get".concat(
					fieldName.substring(0, 1).toUpperCase()).concat(
					fieldName.substring(1));
			return obj.getClass().getMethod(method).invoke(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
