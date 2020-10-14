package com.woshidaniu.struts2.interceptor;

 

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.template.TemplateEngineManager;
import org.apache.struts2.util.TextProviderHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.LocaleUtils;
import com.woshidaniu.beanutils.reflection.AnnotationUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.annotation.MethodAccess;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.util.request.WebRequestUtils;
/**
 * 
 *@类名称: BaseAbstractInterceptor.java
 *@类描述：基本抽象拦截器对象类
 *@创建人：kangzhidong
 *@创建时间：Oct 16, 2015 5:02:14 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public abstract class BaseAbstractInterceptor extends MethodFilterInterceptor {

	protected CommonModel model = new CommonModel();
	protected List<Object> empty = Collections.emptyList();
	protected TemplateEngineManager templateEngineManager;
	//action接口的直接实现类就是ActionSupport
	protected ActionSupport action = null;
	/* 
	 //cn.itcast.aop.UserAction@15b5783，动作类的对象  
	   System.out.println("invocation.getAction() : "+invocation.getAction());  
	     
	   //cn.itcast.aop.UserAction@15b5783，与invocation.getAction()方法获取的是同一的对象  
	   System.out.println("invocation.getProxy().getAction() : "+invocation.getProxy().getAction());  
	     
	   //userAction_save，自定义配置文件中的action标签的name属性的值  
	   System.out.println("invocation.getProxy().getActionName() : "+invocation.getProxy().getActionName());  
	     
	   //save，对应动作类指定要执行的方法名  
	   System.out.println("invocation.getProxy().getMethod() : "+invocation.getProxy().getMethod());  
	     
	   //  /aop，自定义配置文件中的package标签的namespace属性的值  
	   System.out.println("invocation.getProxy().getNamespace() : "+invocation.getProxy().getNamespace());  
	     
	   //null  返回结果  
	   System.out.println("invocation.getResult() : "+invocation.getResult());  
   */
	@Inject(value="struts.interceptor.excludeMethods")
	protected String excludeMethodNames = null;
	
	protected String interceptRequired(ActionInvocation invocation,String Key,String ...required) throws Exception{
		//请求的request对象
        HttpServletRequest request = ServletActionContext.getRequest();
		if(required == null){
			//执行下一个拦截器
			return invocation.invoke();
		}
		//检查必选参数
		for(String key :required){
			//指定值
			String requiredVal = request.getParameter(key);
			if(BlankUtils.isBlank(requiredVal)){
				List<Object> args = new ArrayList<Object>();
				args.add(key);
				//判断是否ajax请求
				if( WebRequestUtils.isAjaxRequest(request)){
					invocation.getStack().set(Result.DATA, TextProviderHelper.getText(Key, "", args , invocation.getStack()));
					return Result.DATA;
				}else{
					invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText(Key, "", args , invocation.getStack()));
					return Result.EX_WARN;
				}
			}
		}
		//执行下一个拦截器
		return invocation.invoke();
	}
	
	@Override
    public String intercept(ActionInvocation invocation) throws Exception {
		//获取request请求对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//国际化语言切换
		ActionContext.getContext().setLocale(LocaleUtils.interceptLocale(request));
		//判断是否执行拦截
        if (applyInterceptor(invocation)) {
            return doIntercept(invocation);
        } 
        return invocation.invoke();
    }
 
	@Override
	protected boolean applyInterceptor(ActionInvocation invocation) {
		//过滤全局忽略拦截
		String method = invocation.getProxy().getMethod();
        Set<String> excludeMethods = TextParseUtil.commaDelimitedStringToSet(excludeMethodNames);
        if(excludeMethods.contains(method)){
        	return false;
        }
        return super.applyInterceptor(invocation);
    }
	
	/**
	 * 
	 * @description	： 根据ActionInvocation中的信息获取操作代码
	 * @author 		： <a href="mailto:hnxyhcwdl1003@163.com">kangzhidong</a>
	 * @date 		：2015-6-9 上午11:16:50
	 * @param invocation
	 * @return
	 */
	protected String getOptCode(ActionInvocation invocation){
		//action 将要执行的方法名称
		String methodName = invocation.getProxy().getMethod();
		//action 将要执行的方法对象
		Method method = ReflectionUtils.getMethod(invocation.getProxy().getAction().getClass(), methodName);
		//权限注解
		MethodAccess methodAccess = AnnotationUtils.findAnnotation(method, MethodAccess.class);
		//操作权限
		String opt_code = null;
		//有注解
		if(methodAccess != null){
			opt_code = methodAccess.value();
		}
		if(BlankUtils.isBlank(opt_code) && !BlankUtils.isBlank(methodName)){
			 for(int i=0; i < methodName.length();i++){
	             char c	=	methodName.charAt(i);
	             //大写
                 if (!(c>=97 && c<=122)){
	            	 opt_code	=	methodName.substring(0, i);
	                 break;
	             }
	         }
			 if(methodName.length()>0 && opt_code == null){
				 opt_code = methodName;
            }
		}
        return opt_code;
    }
 
	/**
	 * @return the excludeMethodNames
	 */
	public String getExcludeMethodNames() {
		return excludeMethodNames;
	}

	/**
	 * @param excludeMethodNames the excludeMethodNames to set
	 */
	public void setExcludeMethodNames(String excludeMethodNames) {
		this.excludeMethodNames = excludeMethodNames;
	}
	
	@Inject
    public void setTemplateEngineManager(TemplateEngineManager mgr) {
        this.templateEngineManager = mgr;
    }

	/**
	 * @return the templateEngineManager
	 */
	public TemplateEngineManager getTemplateEngineManager() {
		return templateEngineManager;
	}
	
	/**
	 * @return the model
	 */
	public CommonModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(CommonModel model) {
		this.model = model;
	}

	/**
	 * @return the empty
	 */
	public List<Object> getEmpty() {
		return empty;
	}

	/**
	 * @param empty the empty to set
	 */
	public void setEmpty(List<Object> empty) {
		this.empty = empty;
	}

	/**
	 * @return the action
	 */
	public ActionSupport getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(ActionSupport action) {
		this.action = action;
	}
	
	
}
