package com.woshidaniu.editor.action;

import com.woshidaniu.editor.api.model.KindeditorModel;

/**
 * 
 *@类名称		：KindeditorPluginsAction.java
 *@类描述		：KindEditor 编辑器插件管理Action
 *@创建人		：kangzhidong
 *@创建时间	：Jun 2, 2016 11:12:43 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class KindeditorPluginsAction extends KindeditorBaseAction<KindeditorModel> {

	protected KindeditorModel model = new KindeditorModel();
	
	/**
	 * 编辑器插件HTML文件跳转
	 * @return
	 */
	public String plugins(){
		return SUCCESS;
	}

	@Override
	public KindeditorModel getModel() {
		return model;
	}
	
}