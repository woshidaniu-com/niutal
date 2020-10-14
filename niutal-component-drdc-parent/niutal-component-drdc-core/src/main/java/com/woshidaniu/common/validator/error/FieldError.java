package com.woshidaniu.common.validator.error;

import org.apache.commons.lang3.StringUtils;

/**
 * 属性错误信息实体。即属性对应的错误信息。
 * 
 * @author Jiangdong.Yi
 * 
 */
public class FieldError {
	private Object entity;
	private String field;
	private String errMessage;
	private String defaultMessage = "不是合法的数据！";

	/**
	 * 构造函数。其中的参数只有errMessage可以为空，因为它有默认值！其它参数都不能为空！
	 * 
	 * @param entity
	 *            验证的实体类。
	 * @param field
	 *            对应的属性。这里只能是一个。
	 * @param errMessage
	 *            指定的验证信息。
	 */
	public FieldError(Object entity, String field, String errMessage) {
		if (entity == null) {
			throw new IllegalArgumentException("argument for entity must not be null!");
		}
		if (StringUtils.isEmpty(field)) {
			throw new IllegalArgumentException("argument field[Object:" + entity.getClass

().getSimpleName()
					+ "] not be null!");
		}
		this.entity = entity;
		this.field = field;
		this.errMessage = errMessage;
	}

	/**
	 * 获取对应的实体对象。
	 * 
	 * @return 返回对应的实体对象。
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * 用class的getName()方法获取实体的名称。
	 * 
	 * @return 返回实体的名称。
	 */
	public String getObjectName() {
		return entity.getClass().getName();
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 获取验证的报错信息。如果用户指定的报错信息为空，则返回默认的报错信息。
	 * 
	 * @return 返回验证的报错信息。
	 */
	public String getErrMessage() {
		return StringUtils.isEmpty(errMessage) ? defaultMessage : errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		FieldError e = (FieldError) o;
		return getField().equals(e.getField()) && getErrMessage().equals(e.getErrMessage());
	}
}
