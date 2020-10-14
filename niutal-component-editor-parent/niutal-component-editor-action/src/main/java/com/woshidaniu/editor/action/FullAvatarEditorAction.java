package com.woshidaniu.editor.action;
import java.net.URLDecoder;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.editor.api.model.FullAvatarModel;

/**
 * 
 *@类名称		：FullAvatarEditorAction.java
 *@类描述		：FullAvatarEditor 富头像上传编辑器数据处理action
 *@创建人		：kangzhidong
 *@创建时间	：Jun 2, 2016 11:42:12 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class FullAvatarEditorAction extends BaseAction implements ModelDriven<FullAvatarModel> {

	private static final long serialVersionUID = 1L;
	
	protected FullAvatarModel model = new FullAvatarModel();

	/**
	 * 
	 *@描述：跳转至公共的 富头像上传编辑器 图片上传页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-27下午12:03:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxFullAvatarUpload() {
		try {
			model.setUpload_url(URLDecoder.decode(StringUtils.getSafeStr(model.getUpload_url(), ""),"UTF-8"));
			model.setSrc_url(URLDecoder.decode(StringUtils.getSafeStr(model.getSrc_url(), ""),"UTF-8"));
		} catch (Exception e) {
		}
		return SUCCESS;
	}
	
	@Override
	public FullAvatarModel getModel() {
		return model;
	}
	
}
 
