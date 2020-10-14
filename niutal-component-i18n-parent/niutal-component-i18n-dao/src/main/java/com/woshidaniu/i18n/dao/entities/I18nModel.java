package com.woshidaniu.i18n.dao.entities;

import java.io.Serializable;
import java.util.List;

import com.woshidaniu.basemodel.PairModel;

/**
 * 
 * @类名称:I81nModel.java
 * @类描述：国际化Model
 * @创建人：kangzhidong
 * @创建时间：Feb 15, 2016 5:13:14 PM
 * @修改人：
 * @修改时间：
 * @版本号:v1.0
 */
@SuppressWarnings("serial")
public class I18nModel implements Serializable {

	/** 模块名称：通常指功能模块代码 */
	protected String module;
	/** 国际化资源文件名称 */
	protected String resource;
	/** 国际化信息集合 */
	protected List<PairModel> i18nList;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public List<PairModel> getI18nList() {
		return i18nList;
	}

	public void setI18nList(List<PairModel> i18nList) {
		this.i18nList = i18nList;
	}

}
