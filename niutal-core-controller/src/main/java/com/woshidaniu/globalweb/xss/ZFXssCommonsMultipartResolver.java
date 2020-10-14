/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.xss;

//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;

//import org.apache.commons.fileupload.FileItem;
//import org.owasp.html.HtmlPolicyBuilder;
//import org.owasp.html.PolicyFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 能够清除XSS攻击字符串参数的附件解析器
 * 
 * 1.防止黑客利用附件方式xss的伪造请求
 * 2.spring mvc框架太智能，会把附件请求转换为常规请求
 * 
 * 暂时提交到svn当中,若遇到此类问题,放开本来所有注释,配置此类到config-webmvc.xml文件替换原先的multipartResolver的bean即可解决此类安全问题
 * 
 * @author 		：康康（1571）
 */
public class ZFXssCommonsMultipartResolver /*extends CommonsMultipartResolver*/{
	
	//protected Logger log = LoggerFactory.getLogger(ZFXssCommonsMultipartResolver.class);
	
	//protected PolicyFactory DEFAULT_POLICY = new HtmlPolicyBuilder().toFactory();
	//Xss检查策略工厂
	//protected PolicyFactory policy = DEFAULT_POLICY;
	//需要进行Xss检查的Header
	//protected String[] policyHeaders = null;
	
	//private boolean processXssParameter = false;
	
	//private static final String[] EMPTY_STRING_ARRAY = new String[] {};
	
	/**
	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
		
		MultipartParsingResult originalMultipartParsingResult = super.parseFileItems(fileItems, encoding);
		
		if(processXssParameter) {
			Map<String, String[]> parameters = originalMultipartParsingResult.getMultipartParameters();
			MultiValueMap<String, MultipartFile> mpFiles = originalMultipartParsingResult.getMultipartFiles();
			Map<String, String> mpParamContentTypes = originalMultipartParsingResult.getMultipartParameterContentTypes();
			return new XssProcessedMultipartParsingResult(mpFiles,parameters,mpParamContentTypes);
		}else {
			return originalMultipartParsingResult;
		}
	}
	
	private class XssProcessedMultipartParsingResult extends MultipartParsingResult{

		public XssProcessedMultipartParsingResult(MultiValueMap<String, MultipartFile> mpFiles,Map<String, String[]> mpParams, Map<String, String> mpParamContentTypes) {
			super(mpFiles, mpParams, mpParamContentTypes);
		}

		@Override
		public MultiValueMap<String, MultipartFile> getMultipartFiles() {
			return super.getMultipartFiles();
		}

		@Override
		public Map<String, String[]> getMultipartParameters() {
			
			Map<String, String[]> parameters = super.getMultipartParameters();
			Map<String, String[]> processedParameters = new HashMap<String,String[]>();
			
			Iterator<Entry<String, String[]>> it = parameters.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, String[]> e = it.next();
				String key = e.getKey();
				String[] rawValues = e.getValue();
				if (rawValues == null){
					processedParameters.put(key, EMPTY_STRING_ARRAY);
				}else {
					String[] cleanedValues = new String[rawValues.length];
					for (int i = 0; i < rawValues.length; i++) {
						cleanedValues[i] = xssClean(rawValues[i]);
					}					
					processedParameters.put(key, cleanedValues);
				}
			}
			return processedParameters;
		}

		@Override
		public Map<String, String> getMultipartParameterContentTypes() {
			return super.getMultipartParameterContentTypes();
		}
	}
	
	public String xssClean(String taintedHTML) {
		log.debug("Tainted :" + taintedHTML);
		String cleanHTML = policy.sanitize(taintedHTML);
		log.debug("XSS Clean :" + cleanHTML);
		return cleanHTML;
	}

	public void setProcessXssParameter(boolean processXssParameter) {
		this.processXssParameter = processXssParameter;
	}
	
	**/
}
