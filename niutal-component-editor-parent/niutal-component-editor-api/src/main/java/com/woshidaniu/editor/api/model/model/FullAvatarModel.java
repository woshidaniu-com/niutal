package com.woshidaniu.editor.api.model;

import java.io.Serializable;

/**
 * 
 *@类名称:FullAvatarModel.java
 *@类描述： 富头像上传编辑器动态参数model
 *@创建人：kangzhidong
 *@创建时间：2015-3-27 上午11:49:42
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class FullAvatarModel implements Serializable {

	// ------------上传路径参数---------------------

	// 上传图片的接口。该接口需返回一个json字符串，且会原样输出到 callback 回调函数 的参数对象的属性content中
	protected String upload_url;
	// 生成的头像图片的质量，取值范围1-100，数值越大生成的图片越清晰，相对地文件也会越大。
	protected String quality = "100";

	// ------------原图片参数---------------------

	// 默认加载的原图片的url
	protected String src_url;
	// 选择的本地图片文件所允许的最大值，必须带单位，如888Byte，88KB，8MB
	protected String src_size = "2MB";
	// 是否上传原图片的选项：2-显示复选框由用户选择，0-不上传，1-上传,2 -显示复选框由用户选择
	protected String src_upload = "0";

	// ------------处理后的头像参数---------------------

	// 定义头像尺寸：表示一组或多组头像的尺寸。其间用"|"号分隔。如：100*100|50*50|32*32
	protected String avatar_sizes = "150*200";
	// 头像尺寸的提示文本。多个用"|"号分隔，与上一项对应。如：100*100像素|50*50像素|32*32像素
	protected String avatar_sizes_desc = "150*200像素";
	// 头像的表单域名称，多个用"|"号分隔，与 avatar_sizes 项对应。如：__avatar1|__avatar2|__avatar3
	protected String avatar_field_names = "_avatar1";
	
	

	public String getUpload_url() {
		return upload_url;
	}

	public void setUpload_url(String uploadUrl) {
		upload_url = uploadUrl;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getSrc_url() {
		return src_url;
	}

	public void setSrc_url(String srcUrl) {
		src_url = srcUrl;
	}

	public String getSrc_size() {
		return src_size;
	}

	public void setSrc_size(String srcSize) {
		src_size = srcSize;
	}

	public String getSrc_upload() {
		return src_upload;
	}

	public void setSrc_upload(String srcUpload) {
		src_upload = srcUpload;
	}

	public String getAvatar_sizes() {
		return avatar_sizes;
	}

	public void setAvatar_sizes(String avatarSizes) {
		avatar_sizes = avatarSizes;
	}

	public String getAvatar_sizes_desc() {
		return avatar_sizes_desc;
	}

	public void setAvatar_sizes_desc(String avatarSizesDesc) {
		avatar_sizes_desc = avatarSizesDesc;
	}

	public String getAvatar_field_names() {
		return avatar_field_names;
	}

	public void setAvatar_field_names(String avatarFieldNames) {
		avatar_field_names = avatarFieldNames;
	}

}
