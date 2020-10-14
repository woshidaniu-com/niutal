package com.woshidaniu.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.enhanced.utils.AspectUtils;

import com.woshidaniu.common.aop.aware.AfterAware;
import com.woshidaniu.common.aop.aware.BeforeAware;
import com.woshidaniu.common.aop.aware.ExceptionAware;
import com.woshidaniu.common.aop.aware.Invocation;

/**
 * 
 *@类名称	: AbstractAspectInterceptor.java
 *@类描述	：基于Spring AOP 的方法切面拦截器
 *@创建人	：kangzhidong
 *@创建时间	：Mar 8, 2016 3:29:04 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public abstract class AbstractAspectInterceptor{
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	/***
	 * 
	 *@描述		：before 切面 : :方法执行前被调用
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 21, 20169:22:36 AM
	 *@param point
	 *@throws Throwable
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void before(JoinPoint point) throws Throwable {
		String mathodName = point.getSignature().getName();
		if("setSelf".equalsIgnoreCase(mathodName)){
    		return;
    	}
		Object target = point.getTarget();
		Object[] args = point.getArgs();
		Method method = AspectUtils.getMethod(point);
    	//如果实现了BeforeAware
		if(method.getDeclaringClass().isInstance(BeforeAware.class)){
			//获得前置通知调用方法doBefore
			Method doBefore = BeanUtils.findMethod(method.getDeclaringClass(),"doBefore",Invocation.class);
			//执行doBefore
			if(doBefore!=null){
				doBefore.invoke(target,new Object[]{new Invocation(target, method, args)});
			}
		}else{
			//调用接口
			this.doBefore(point);
		}
	}
	
	public abstract void doBefore(JoinPoint point) throws Throwable;
	
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {  
    	try {
    		Object result = joinPoint.proceed();
    		String mathodName = joinPoint.getSignature().getName();
    		if("setSelf".equalsIgnoreCase(mathodName)){
        		return result;
        	}
    		return this.doAround(joinPoint);
        }catch (Exception e) {
            e.printStackTrace();
            LOG.warn("invoke(ProceedingJoinPoint) - exception ignored", e); //$NON-NLS-1$ 
        }finally {
        	if (LOG.isDebugEnabled()) {
            	LOG.debug("invoke(ProceedingJoinPoint) - end"); //$NON-NLS-1$
            }
        }
        return null; 
    }  
	
	public abstract Object doAround(ProceedingJoinPoint joinPoint) throws Throwable;
	
	/**
	 * 
	 *@描述		：after 切面 :方法执行完后被调用
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 21, 20169:34:00 AM
	 *@param point
	 *@param returnValue
	 *@throws Throwable
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
    public void afterReturning(JoinPoint point,Object returnValue) throws Throwable {  
    	String mathodName = point.getSignature().getName();
		if("setSelf".equalsIgnoreCase(mathodName)){
    		return;
    	}
		Object target = point.getTarget();
		Object[] args = point.getArgs();
		Method method = AspectUtils.getMethod(point);
		//如果实现了AfterAware
    	if(method.getDeclaringClass().isInstance(AfterAware.class)){
    		//获得后置通知调用方法doAfter
    		Method doAfter = BeanUtils.findMethod(method.getDeclaringClass(), "doAfter",Invocation.class);
    		//执行doAfter
    		if(doAfter!=null){
    			doAfter.invoke(target,new Object[]{new Invocation(target, method, args, returnValue)});
    		}
    	}else{
    		this.doAfterReturning(point,returnValue);
    	}
    }  
	
    public abstract void doAfterReturning(JoinPoint point,Object returnValue) throws Throwable;
    
	/**
	 * 
	 * @description: 异常切面  :方法执行完后如果抛出异常则被调用
	 * @author : kangzhidong
	 * @date : 2014-6-11
	 * @time : 下午03:57:55 
	 * @param point
	 * @param ex
	 * @throws Throwable
	 */
    public void afterThrowing(JoinPoint point,Throwable ex) throws Throwable {  
    	
    	String mathodName = point.getSignature().getName();
		if("setSelf".equalsIgnoreCase(mathodName)){
    		return;
    	}
		Object target = point.getTarget();
		Object[] args = point.getArgs();
		Method method = AspectUtils.getMethod(point);
		//如果实现了ExceptionAware
    	if(method.getDeclaringClass().isInstance(ExceptionAware.class)){
    		//获得异常通知调用方法doException
    		Method doException = BeanUtils.findMethod(method.getDeclaringClass(), "doException",Invocation.class);
    		//执行doException
    		if(doException != null){
    			doException.invoke(target,new Object[]{new Invocation(target, method, args, ex)});
    		}
    	}else{
    		this.doAfterThrowing(point, ex);
    	}
    }
    
    public abstract void doAfterThrowing(JoinPoint point,Throwable ex) throws Throwable;
	
}
