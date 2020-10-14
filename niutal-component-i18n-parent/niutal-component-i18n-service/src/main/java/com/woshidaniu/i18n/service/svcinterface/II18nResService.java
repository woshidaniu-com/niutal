package com.woshidaniu.i18n.service.svcinterface;

import java.io.File;
import java.util.List;

import com.woshidaniu.common.exception.BusinessException;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.i18n.dao.entities.I18nResModel;

/**
 * 
 * @类名称:II18nResService.java
 * @类描述：国际化资源文件维护
 * @创建人：WuXinfeng
 * @创建时间：2017年1月11日 下午4:41:23
 * @版本号:v1.0
 */
public interface II18nResService extends BaseService<I18nResModel>{

	/**
	 * 
	 * @描述：分页查询资源内容信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:53:01
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	public List<I18nResModel> getPagedI18nList(I18nResModel model);

	/**
	 * 
	 * @描述：单个查询资源内容信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:53:41
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param res_oid
	 * @return
	 */
	public I18nResModel getI18nModel(String res_oid);

	/**
	 * 
	 * @描述：增加资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:20
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean insertI18n(I18nResModel model);

	/**
	 * 
	 * @描述：修改资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:32
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean updateI18n(I18nResModel model);

	/**
	 * 
	 * @描述：删除资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:42
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean deleteI18n(I18nResModel model);

	/**
	 * 
	 * @描述：分页查询资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:53:01
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	public List<I18nResModel> getPagedResourceList(I18nResModel model);

	/**
	 * 
	 * @描述：单个查询资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:53:41
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param res_oid
	 * @return
	 */
	public I18nResModel getModelResource(String res_oid);

	/**
	 * 
	 *@描述		：获取指定的国际化资源文件信息
	 *@创建人		: kangzhidong
	 *@创建时间	: Jan 18, 20179:51:01 AM
	 *@param res_co	de
	 *@param res_type
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public I18nResModel getI18nResource(String res_code,String res_type);

	
	/**
	 * 
	 * @描述：增加资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:20
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean insertResource(I18nResModel model);

	/**
	 * 
	 * @描述：修改资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:32
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean updateResource(I18nResModel model);

	/**
	 * 
	 * @描述：删除资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:54:42
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	boolean deleteResource(I18nResModel model);

	/**
	 * 
	 * @描述：分页查询资源文件对应资源内容信息列表
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月17日上午11:51:21
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param res_oid
	 * @return
	 */
	public List<I18nResModel> getPagedResourceI18n(String res_oid);

	/**
	 * 
	 * @描述：查询资源文件对应资源内容信息列表
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:56:38
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param res_oid
	 * @return
	 */
	public List<I18nResModel> getResourceI18nList(String res_oid);

	/**
	 * 
	 * @描述：查询资源文件代码信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:57:38
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public List<I18nResModel> getResourceMapList();

	/**
	 * 
	 * @描述：查询功能代码信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:57:54
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public List<I18nResModel> getGnmkdmMapList();

	/**
	 * 
	 * @描述：模糊查询资源文件代码信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午5:17:56
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	public List<BaseMap> getI18nResCodeList(I18nResModel model);

	/**
	 * 
	 * @描述：发布资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午5:21:51
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	public boolean publishResource(I18nResModel model) throws BusinessException;

	/**
	 * 
	 * @描述：生成资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午5:22:01
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	public List<File> downloadResource(I18nResModel model) throws BusinessException;
	
}
