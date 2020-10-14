/**
 * 
 */
package com.woshidaniu.common.template.method;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.rjpj.api.ParameterProvider;
import com.woshidaniu.rjpj.api.RequestUrlBuilder;
import com.woshidaniu.rjpj.api.RequestUrlParameter;
import com.woshidaniu.rjpj.api.impl.SimpleRequestUrlBuilderImpl;
import com.woshidaniu.service.svcinterface.ICsszService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author 1036
 * @desc 用于生成软件使用评价链接
 *
 */
@Component("evaluateMethod")
public class EvaluateMethod implements TemplateMethodModelEx {

	@Autowired
	private IYhglService yhglService;
	
	@Autowired
	private ICsszService cssxService;
	
	@Autowired(required=false)
	private ParameterProvider parameterProvider;
	
	/**
	 * 内部实现，当业务系统没有配置的时候采用该实现
	 * @author 1036
	 *
	 */
	class InnerParameterProviderImpl implements ParameterProvider{

		public YhglModel getYgModel() {
			
			if(null == WebContext.getSession().getAttribute("evaluate_yhModel")){
				YhglModel query = new YhglModel();
				query.setZgh(WebContext.getUser().getYhm());
				WebContext.getSession().setAttribute("evaluate_yhModel", yhglService.getModel(query));
			}
			
			return (YhglModel)WebContext.getSession().getAttribute("evaluate_yhModel");
		}

		@Override
		public String getAPIUrl() {
			return MessageUtil.getText("niutal.evaluate.apiurl");
		}

		@Override
		public String getKey() {
			return MessageUtil.getText("niutal.evaluate.key");
		}

		@Override
		public String getFormCode() {
			return MessageUtil.getText("niutal.evaluate.formcode");
		}

		@Override
		public String getXM() {
			if(null == getYgModel()){
				return "";
			}
			return getYgModel().getXm();
		}

		@Override
		public String getSJHM() {
			if(null == getYgModel()){
				return "";
			}
			return getYgModel().getLxdh();
		}

		@Override
		public String getXXDM() {
			String xsdm = cssxService.getValue("xxdm");
			return StringUtils.isEmpty(xsdm) ? "00000" : xsdm;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
	 */
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		
		if(null == parameterProvider){
			parameterProvider = new InnerParameterProviderImpl();
		}
		
		HttpSession session = WebContext.getSession();
		
		if(StringUtils.isEmpty(session.getAttribute("niutal_evaluate_url"))){
			
			//这个是公布在外网环境下的URL地址
			String apiUrl = parameterProvider.getAPIUrl();
			//这个是key，一般就是使用这个,当然,最好还是写到配置文件中
			String key = parameterProvider.getKey();
			
			HashMap<String,String> parameters = new HashMap<String,String>();
			//表单代码,从软件满意度评价系统中获取,可配置到配置文件中
			parameters.put(RequestUrlParameter.FORM_CODE, parameterProvider.getFormCode());
			//身份证号码,从登陆上下文中获取
			parameters.put(RequestUrlParameter.SJHM, parameterProvider.getSJHM());
			//姓名,从登陆上下文中获取
			parameters.put(RequestUrlParameter.XM, parameterProvider.getXM());
			//学校代码,这里是浙江大学,从登陆上下文中获取
			parameters.put(RequestUrlParameter.XXDM, parameterProvider.getXXDM());
			//new这个对象
			RequestUrlBuilder requestUrlBuilder = new SimpleRequestUrlBuilderImpl();
			//结果就是整体的URL
			
			session.setAttribute("niutal_evaluate_url", requestUrlBuilder.build(key, apiUrl, parameters));
		}
		
		return new SimpleScalar(String.valueOf(session.getAttribute("niutal_evaluate_url")));
	}

}
