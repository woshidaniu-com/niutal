/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.service;

import org.activiti.engine.repository.Model;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:com.woshidaniu.component.bpm.form.management.service.FormWebService.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月22日下午3:14:11
 */
public interface FormWebService {

	/**
	 * 
	 * <p>方法说明：创建model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午3:14:43<p>
	 * @param modelName
	 * @param modelDesc
	 */
	void createModel(String modelName, String modelDesc);
	
	/**
	 * 
	 * <p>方法说明：保存mode.source<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午4:21:38<p>
	 * @param modelId
	 * @param modelData
	 */
	void addModelData(String modelId, byte[] modelData);
	
	/**
	 * 
	 * <p>方法说明：保存mode.source<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午4:21:38<p>
	 * @param modelId
	 * @param modelData
	 */
	void addModelData(String modelId, String modelData);
	
	/**
	 * 
	 * <p>方法说明：保存mode<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午4:23:34<p>
	 * @param modelName
	 * @param modelDesc
	 * @param modelData
	 */
	void creataModel(String modelName, String modelDesc, byte[] modelData);
	
	/**
	 * 
	 * <p>方法说明：保存mode<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午4:23:34<p>
	 * @param modelName
	 * @param modelDesc
	 * @param modelData
	 */
	void creataModel(String modelName, String modelDesc, String modelData);
	
	/**
	 * 
	 * <p>方法说明：保存model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午3:22:53<p>
	 * @param modelId
	 * @param modelName
	 * @param modelDesc
	 * @param modelData
	 */
	void saveModel(String modelId, String modelName, String modelDesc, String modelData);
	
	/**
	 * 
	 * <p>方法说明：保存model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年6月5日上午10:46:46<p>
	 * @param modelId
	 * @param modelName
	 * @param modelDesc
	 */
	void saveModel(String modelId, String modelName, String modelDesc);
	
	/**
	 * 
	 * <p>方法说明：保存model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午3:22:53<p>
	 * @param modelId
	 * @param modelName
	 * @param modelDesc
	 * @param modelData
	 */
	void saveModel(String modelId, String modelData);
	
	/**
	 * 
	 * <p>方法说明：删除Model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午3:15:53<p>
	 * @param modelId
	 */
	void deleteModel(String modelId);
	
	/**
	 * 
	 * <p>方法说明：查询Model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月22日下午3:16:31<p>
	 * @param modelId
	 */
	Model getModel(String modelId);
	
	/**
	 * 
	 * <p>方法说明：部署Model<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年6月5日上午9:22:54<p>
	 * @param modelId
	 */
	void deployModel(String modelId);
	
}
