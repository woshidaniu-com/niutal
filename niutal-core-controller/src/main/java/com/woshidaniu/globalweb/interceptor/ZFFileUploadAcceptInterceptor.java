package com.woshidaniu.globalweb.interceptor;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.woshidaniu.spring.web.servlet.handler.FileUploadAcceptInterceptor;

/**
 * 
 *@类名称	: ZFFileUploadAcceptInterceptor.java
 *@类描述	： 继承FileUploadAcceptInterceptor实现参数自动注入
 *@创建人	：kangzhidong
 *@创建时间	：2017年8月21日 上午10:19:01
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class ZFFileUploadAcceptInterceptor extends FileUploadAcceptInterceptor implements InitializingBean {

	/** 指定所上传文件的总大小。注意maxUploadSize属性的限制不是针对单个文件，而是单次请求中所有文件的容量之和 ；单位：字节 */
	@Value("#{runtimeProps['springmvc.upload.maxUploadSize']}")  
	protected String maxUploadSizeAsString = "-1";
	/** 指定所上传单个文件的大小。单位：字节 */
	@Value("#{runtimeProps['springmvc.upload.maxUploadSizePerFile']}")  
	protected String maxUploadSizePerFileAsString = "-1";
	/** 指定运行上传的文件 mimetypes */
	@Value("#{runtimeProps['springmvc.upload.content.type.allowed']}")  
	protected String allowedTypes = "*";
	/** 指定允许上传的文件后缀 */
	@Value("#{runtimeProps['springmvc.upload.file.extension.allowed']}")  
	protected String allowedExtensions = "*";
	/** 指定是否使用严格模式校验文件内容是否匹配文件类型和mineType **/
	@Value("#{runtimeProps['springmvc.upload.file.useStrict']}")  
	protected String useStrict = "false";
	
	@Override
	public void afterPropertiesSet() throws Exception {

		super.setMaxUploadSize(Long.valueOf(maxUploadSizeAsString));
		super.setMaxUploadSizePerFile(Long.valueOf(maxUploadSizePerFileAsString));
		super.setAllowedTypes(allowedTypes);
		super.setAllowedExtensions(allowedExtensions);
		super.setUseStrict(BooleanUtils.toBoolean(useStrict));
		
	}
	
	public String getMaxUploadSizeAsString() {
		return maxUploadSizeAsString;
	}

	public void setMaxUploadSizeAsString(String maxUploadSizeAsString) {
		this.maxUploadSizeAsString = maxUploadSizeAsString;
	}
	
	public String getMaxUploadSizePerFileAsString() {
		return maxUploadSizePerFileAsString;
	}
	
	public void setMaxUploadSizePerFileAsString(String maxUploadSizePerFileAsString) {
		this.maxUploadSizePerFileAsString = maxUploadSizePerFileAsString;
	}

	public String getAllowedTypes() {
		return allowedTypes;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
	}

	public String getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public String getUseStrict() {
		return useStrict;
	}

	public void setUseStrict(String useStrict) {
		this.useStrict = useStrict;
	}
	
}
