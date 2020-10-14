package com.woshidaniu.designer.utils;

import java.io.File;
import java.util.Map;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.freemarker.utils.FormatUtils;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.web.context.WebContext;

public class FuncPageUtils {
	
	public static File buildFuncScript(DesignFuncModel model,Map<String,Object> rootMap) throws Exception {
		//应用程序根目录
		String webRootDir = WebContext.getServletContext().getRealPath(File.separator);
		//动态生成的静态文件存储目录
		File dynamicDir = DirectoryUtils.getExistDir(webRootDir + "/WEB-INF/pages/dynamic" );
		File sqlscriptFile =  new File(dynamicDir, model.getFunc_code() + "-" + model.getOpt_code() + ".sql");
		//如果指定的自定义界面则删除自定义jsp
		if(sqlscriptFile!=null && sqlscriptFile.exists() && sqlscriptFile.isFile()){
			sqlscriptFile.delete();
		}
		rootMap.remove("data_sql");
		FormatUtils.toStatic(rootMap,"classpath:template/design/sqlscript/func-init-sql.ftl",sqlscriptFile);
		return sqlscriptFile;
		 
	}
	
	public static void buildPageWhenNotFound(DesignFuncModel funcModel) throws Exception{
		if(!BlankUtils.isBlank(funcModel)){
			//应用程序根目录
			String webRootDir = WebContext.getServletContext().getRealPath(File.separator);
			//动态生成的静态文件存储目录
			File dynamicDir = DirectoryUtils.getExistDir(webRootDir + "/WEB-INF/pages/dynamic" );
			File dynamicFile =  new File(dynamicDir, funcModel.getFunc_code() + "-" + funcModel.getOpt_code() + ".jsp");
			//如果指定的自定义界面则生成自定义jsp
			if(dynamicFile == null || !dynamicFile.exists()){
				DesignFuncMenuModel menuModel = new DesignFuncMenuModel();
				menuModel.setFunc_code(funcModel.getFunc_code());
				menuModel.setFunc_type(funcModel.getFunc_type());
				menuModel.setOpt_code(funcModel.getOpt_code());
				FuncPageUtils.buildFuncPage(menuModel);
			}
		}
	}
	
	public static void buildFuncPage(DesignFuncMenuModel model) throws Exception {
		//应用程序根目录
		String webRootDir = WebContext.getServletContext().getRealPath(File.separator);
		//动态生成的静态文件存储目录
		File dynamicDir = DirectoryUtils.getExistDir(webRootDir + "/WEB-INF/pages/dynamic" );
		File dynamicFile =  new File(dynamicDir, model.getFunc_code() + "-" + model.getOpt_code() + ".jsp");
		//如果指定的自定义界面则删除自定义jsp
		if(dynamicFile!=null && dynamicFile.exists() && dynamicFile.isFile()){
			dynamicFile.delete();
		}
		//系统自定义功能类型；默认：数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'报表打印',4:'数据导出',5:'数据删除'
		if("3".equals(model.getFunc_type())){
			FormatUtils.toStatic(model, FuncPageUtils.class ,"template/design","func-page-report.ftl" ,dynamicFile);
		}else{
			FormatUtils.toStatic(model, FuncPageUtils.class ,"template/design",("cx".equalsIgnoreCase(model.getOpt_code())) ? "func-page-index.ftl" : "func-page-sub.ftl" ,dynamicFile);
		}
	}

	public static  void deleteFuncPage(DesignFuncModel model) {
		//应用程序根目录
		String webRootDir = WebContext.getServletContext().getRealPath(File.separator);
		//动态生成的静态文件存储目录
		File dynamicDir = DirectoryUtils.getExistDir(webRootDir + "/WEB-INF/pages/dynamic" );
		File dynamicFile =  new File(dynamicDir, model.getFunc_code() + "-" + model.getOpt_code() + ".jsp");
		//如果指定的自定义界面则删除自定义jsp
		if(dynamicFile!=null && dynamicFile.exists() && dynamicFile.isFile()){
			dynamicFile.delete();
		}
	}
}
