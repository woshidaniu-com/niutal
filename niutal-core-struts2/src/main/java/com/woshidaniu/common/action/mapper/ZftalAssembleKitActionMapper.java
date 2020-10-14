package com.woshidaniu.common.action.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.plus.dispatcher.mapper.NamespaceRedirectActionMapper;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.woshidaniu.common.constant.BaseConstant;

/**
 * 类名称：niutalAssembleKitActionMapper
 * 类描述： 重写struts请求URl
 * 首先在struts2请求过来的URl，Namespace后追加学校代码，
 * 如果URl存在判断为个性化URl就可以正常跳转。
 * 如果不存在按照请求过来的URl跳转访问
 * 创建人：majun
 * 创建时间：2015-09-17
 * 修改人：kangzhidong
 * 修改时间：2015-09-17
 * 修改备注：
 */
public class ZftalAssembleKitActionMapper extends NamespaceRedirectActionMapper {
	
	/*@Override
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
    	setSlashesInActionNames("true");
    	if (!isSlashesInActionNames()) {
    		throw new IllegalStateException("This action mapper requires the setting 'slashesInActionNames' to be set to 'true'");
    	}
    	ActionMapping mapping = new ActionMapping();
        String uri = RequestUtils.getUri(request);
        
        String dyym = request.getParameter("dyym");
        if(uri.contains("index_cxFuncIndex") && !BlankUtil.isBlank(dyym)){
        	uri = dyym;
        }
       
        int indexOfSemicolon = uri.indexOf(";");
        uri = (indexOfSemicolon > -1) ? uri.substring(0, indexOfSemicolon) : uri;

        uri = dropExtension(uri, mapping);
        if (uri == null) {
        	return null;
        }
        //根据默认请求设置 namespace,actionName
        parseNameAndNamespace(uri, mapping, configManager);
        //处理特殊参数
        handleSpecialParameters(request, mapping);
        //进行自定义的扩展处理
        doActionMappingInternal(request, uri, mapping, configManager);
        //还回处理后的 ActionMapping对象
        return parseActionName(mapping);
    }*/
	
	@Override
	protected void doActionMappingInternal(HttpServletRequest request,
			String uri, ActionMapping mapping,
			ConfigurationManager configManager) {
		
		//框架默认专属命名空间
		String innerNamespace = mapping.getNamespace().endsWith(BaseConstant.DEFAULT_XXDM) ? mapping.getNamespace() : mapping.getNamespace() + BaseConstant.DEFAULT_XXDM;
		//专属命名空间
		String namespace = mapping.getNamespace().endsWith(this.getNamespace()) ? mapping.getNamespace() : mapping.getNamespace() + this.getNamespace();
		///System.out.println("namespace:" + namespace + "，name:" + mapping.getName());
		 // Try to find the namespace in those defined, defaulting to ""
        Configuration config = configManager.getConfiguration();
        /*
        <package name="cxsz10291" namespace="/cxsz10291" extends="configuration">
        ...
        </package> 
        */
        /**
         * 如上：命名空间和包名基本相同，可以尝试使用处理后的命名空间名获取包信息
         */
        PackageConfig packageConfig = config.getPackageConfig(namespace.substring(1));
        //没有找到则进行循环查找
        if(packageConfig == null){
        	// Find the longest matching namespace, defaulting to the default
            for (PackageConfig cfg : config.getPackageConfigs().values()) {
                String ns = cfg.getNamespace();
                if (ns != null && namespace.equals(ns) ) {
                	parseIndividuationNamespace(mapping,cfg);
                    break;
                }
            }
            //如果仍然没有找到这里使用教务默认专属命名空间
            if(packageConfig == null){
            	//查找有无默认专属命名框架
            	packageConfig = config.getPackageConfig(innerNamespace.substring(1));
            	if(packageConfig != null){
            		parseIndividuationNamespace(mapping,packageConfig);
            	}
            }
        }else{
        	parseIndividuationNamespace(mapping,packageConfig);
        }
	}
	
	@Override
	public String getNamespace() {
		return BaseConstant.XXDM;
	}
	
}
