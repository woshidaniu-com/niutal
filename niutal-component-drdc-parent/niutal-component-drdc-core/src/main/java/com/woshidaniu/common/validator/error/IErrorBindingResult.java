package com.woshidaniu.common.validator.error;


import java.util.List;

/**
 * <li>错误信息绑定器</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public interface IErrorBindingResult {
	/**
	 * 获取目标对象的名称。通过class的getName()方法获取的。因此，是这个类的完整名称。
	 * 
	 * @return
	 */
	public String getTargetName();

	/**
	 * 获取目标对象。
	 * 
	 * @return
	 */
	public Object getTarget();

	/**
	 * 获取所有的错误信息实体。如果没有，则返回一个长度为零的List集合
	 * 
	 * @return
	 */
	public List<FieldError> getAllErrors();

	/**
	 * 将一个错误绑定器中的所有错误信息绑定到当前的错误绑定器中。注意：如果这两个绑定器中的对象不是同一个，则绑定会失败！ <br>
	 * 如果result为空或没有验证信息时，则不操作。
	 * 
	 * @param result
	 */
	public void addErrors(IErrorBindingResult result);

	/**
	 * 返回是否有验证信息。
	 * 
	 * @return true：有；false：无。
	 */
	public boolean hasErrors();

	/**
	 * 获取指定属性的所有验证信息。如果没有对应的信息，则返回长度为0的List集合。<br>
	 * 如果field为空，则抛出异常。
	 * 
	 * @param field 指定的属性
	 * @return 返回对应的错误信息
	 */
	public List<FieldError> getFieldErrors(String field);

	/**
	 * 获取指定属性的所有验证信息，并通过字符串返回。如果该属性有多个验证信息，则每个之间用中文分号隔开；如果没有对应的验证信息，则返回""。<br>
	 * 如果field为空，则抛出异常。
	 * 
	 * @param field
	 *            指定的属性名称
	 * @return 返回对应属性所有的验证信息
	 */
	public String getFieldErrorMessage(String field);

	/**
	 * 注入一个属性的验证信息。如果该属性对应的同样的信息已经存在，则不被添加进去，防止重复的验证信息！
	 * 如果指定的消息为空，则不操作。
	 * 
	 * @param field
	 *            属性名称
	 * @param errMessage
	 *            验证信息。
	 * 
	 */
	public void rejectValue(String field, String errMessage);
}
