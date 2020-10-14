package com.woshidaniu.common.aop.aware;

import java.lang.reflect.Method;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Before;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Exceptional;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.aop.annotations.Validation;

/**
 * 
 *@类名称	: Invocation.java
 *@类描述	：方法回调参数对象
 *@创建人	：kangzhidong
 *@创建时间	：Mar 21, 2016 10:57:32 AM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class Invocation{

	/**
	 * 调用此方法的对象
	 */
	private Object target;
	/**
	 * 调用此方法的方法
	 */
	private Method method;
	/**
	 * 调用此方法的方法的参数
	 */
	private Object[] args;
	/**
	 * 调用此方法的方法的返回值
	 */
	private Object returnValue;
	/**
	 * 调用此方法的方法抛出的异常
	 */
	private RuntimeException  throwable;
	
	/**
	 * 日志信息注解
	 */
	private After after;
	private Before before;
	private Comment comment;
	private Logger logger;
	private Exceptional exceptional;
	/**
	 * 验证信息注解
	 */
	private Validation validation;
	
	public Invocation() {
		super();
	}
	
	public Invocation(Object target, Method method, Object[] args) {
		super();
		this.target = target;
		this.method = method;
		this.args = args;
	}

	public Invocation(Object target, Method method,  Object[] args,
			RuntimeException throwable) {
		super();
		this.target = target;
		this.method = method;
		this.args = args;
		this.throwable = throwable;
	}

	public Invocation(Object target, Method method, Object[] args,
			Object returnValue) {
		this.target = target;
		this.method = method;
		this.args = args;
		this.returnValue = returnValue;
	}
	
	public After getAfter() {
		return after;
	}

	public void setAfter(After after) {
		this.after = after;
	}

	public Before getBefore() {
		return before;
	}

	public void setBefore(Before before) {
		this.before = before;
	}

	public Exceptional getExceptional() {
		return exceptional;
	}

	public void setExceptional(Exceptional exceptional) {
		this.exceptional = exceptional;
	}

	public Object getTarget() {

		return target;
	}

	public void setTarget(Object target) {

		this.target = target;
	}

	public Method getMethod() {

		return method;
	}

	public void setMethod(Method method) {

		this.method = method;
	}

	public Object[] getArgs() {

		return args;
	}

	public void setArgs(Object[] args) {

		this.args = args;
	}

	public Object getReturnValue() {

		return returnValue;
	}

	public void setReturnValue(Object returnValue) {

		this.returnValue = returnValue;
	}


	public RuntimeException getThrowable() {
		return throwable;
	}

	public void setThrowable(RuntimeException throwable) {
		this.throwable = throwable;
	}


	public Comment getComment() {
		return comment;
	}


	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Validation getValidation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	
	
}
