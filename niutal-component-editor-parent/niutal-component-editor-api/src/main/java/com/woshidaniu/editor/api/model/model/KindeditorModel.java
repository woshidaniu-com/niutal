package com.woshidaniu.editor.api.model;

import com.woshidaniu.common.query.ModelBase;

@SuppressWarnings("serial")
public class KindeditorModel extends ModelBase {

	protected String dir;// 目录
	protected String fileName;
	protected String fileContentType;
	protected String path;// 路径
	protected String order;// 排序方式

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getPath() {
		return path;
	   }

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
