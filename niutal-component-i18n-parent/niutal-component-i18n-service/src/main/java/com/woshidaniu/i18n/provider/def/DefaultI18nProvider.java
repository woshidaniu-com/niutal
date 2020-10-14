package com.woshidaniu.i18n.provider.def;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.enhanced.utils.ResourceBundleUtils;

import com.woshidaniu.i18n.dao.entities.I18nModel;
import com.woshidaniu.i18n.dao.entities.I18nResModel;
import com.woshidaniu.i18n.provider.I18nProvider;
import com.woshidaniu.i18n.provider.I18nResEnum;
import com.woshidaniu.i18n.service.svcinterface.II18nResService;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.web.context.WebContext;
/**
 * 
 *@类名称		： DefaultI18nProvider.java
 *@类描述		：默认I18n数据提供者实现：该对象默认作为自定义i18n标签的数据提供实现
 *@创建人		：kangzhidong
 *@创建时间	：Jan 18, 2017 9:25:10 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public class DefaultI18nProvider implements I18nProvider {

	protected static Logger LOG = LoggerFactory.getLogger(DefaultI18nProvider.class);

	@Resource
	protected II18nResService service;
	
	/**
	 * 根据表达式加载资源文件
	 * @param expression : properties文件表达式 ；如："classpath*:i18n/message*_zh_CN.properties"
	 */
	@Override
	public List<ResourceBundle> getResourceBundle(String expression) {
		return ResourceBundleUtils.getResourceBundles(expression);
	}
	
	@Override
	public I18nModel getI18nModel(String module) {
		final Locale locale = WebContext.getLocale();
		I18nResModel resModel = getService().getI18nResource(module,I18nResEnum.RES_JS.getType());
		if(resModel == null || resModel.getRes_path() == null){
			return null;
		}
		I18nModel model = new I18nModel();
		//初始数据
		model.setModule(module);
		//文件名称，不包含后缀
		String baseName = FilenameUtils.getBaseName(resModel.getRes_path());
		//后缀名称，包含.
		String extension = FilenameUtils.getFullExtension(resModel.getRes_path());
		//国际化js文件相对路径
		String fileName = FilenameUtils.getFullPath(resModel.getRes_path()) + baseName + "_" + locale.toString() + extension;
		model.setResource(fileName);
		return model;
	}

	public II18nResService getService() {
		return service;
	}

	public void setService(II18nResService service) {
		this.service = service;
	}

	
	
}
