package com.woshidaniu.i18n.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.i18n.dao.entities.I18nResModel;

/**
 * 
 * @类名称:II18nResDao.java
 * @类描述：国际化资源文件维护
 * @创建人：WuXinfeng
 * @创建时间：2017年1月11日 下午4:41:23
 * @版本号:v1.0
 */
public interface II18nDao extends BaseDao<I18nResModel>{
 
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
	/*@I18nSwitch({
		@I18nColumn(column = "res_key", i18n = {
			@I18nLocale(locale = LocaleEnum.zh_CN, column = "res_key" ),
			@I18nLocale(locale = LocaleEnum.en_US, column = "res_key" )
		}),
		@I18nColumn(column = "res_name", i18n = {
			@I18nLocale(locale = LocaleEnum.zh_CN, column = "res_name" ),
			@I18nLocale(locale = LocaleEnum.en_US, column = "res_name" )
		})
	})*/
	public List<I18nResModel> getPagedI18nByScope(I18nResModel model);

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
	public I18nResModel getI18nModel(@Param(value = "res_oid") String res_oid);

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
	int insertI18n(I18nResModel model);

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
	int updateI18n(I18nResModel model);

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
	int deleteI18n(I18nResModel model);

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
	public List<I18nResModel> getPagedResourceByScope(I18nResModel model);

	/**
	 * 
	 * @描述：查询资源文件信息
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午6:12:58
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @return
	 */
	public List<I18nResModel> getAllResourceList(I18nResModel model);

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
	public I18nResModel getModelResource(@Param(value = "res_oid") String res_oid);
	
	/**
	 * 
	 *@描述		：获取指定的国际化资源文件信息
	 *@创建人		: kangzhidong
	 *@创建时间	: Jan 18, 20179:51:01 AM
	 *@param res_code
	 *@param res_type
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public I18nResModel getI18nResource(@Param(value = "res_code") String res_code,@Param(value = "res_type") String res_type);

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
	int insertResource(I18nResModel model);

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
	int updateResource(I18nResModel model);

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
	int deleteResource(I18nResModel model);

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
	public List<I18nResModel> getPagedResourceI18n(
			@Param(value = "res_oid") String res_oid);

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
	public List<I18nResModel> getResourceI18nList(
			@Param(value = "res_oid") String res_oid);

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
	 * @描述：查询国际化国家列表
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月16日下午4:58:08
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public List<I18nResModel> getI18nLocaleList();

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
	 *@描述：修改资源文件路径和发布标记
	 *@创建人:WuXinfeng
	 *@创建时间:2017年1月17日下午5:53:22
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param res_oid
	 *@param res_path
	 *@return
	 */
	public int updatePublishFlag(@Param(value = "res_oid") String res_oid,@Param(value = "res_path") String res_path);
	
}
