package com.woshidaniu.i18n.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.exception.BusinessException;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.i18n.dao.entities.I18nResModel;
import com.woshidaniu.i18n.service.svcinterface.II18nResService;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.util.ResultUtils;
import com.woshidaniu.util.file.ZipHelper;

/**
 * 
 * @类名称:I18nResAction.java
 * @类描述：国际化资源文件维护
 * @创建人：WuXinfeng
 * @创建时间：2017年1月11日 下午4:41:23
 * @版本号:v1.0
 */
@SuppressWarnings("serial")
public class I18nAction extends BaseAction implements ModelDriven<I18nResModel>  { 

	protected I18nResModel model = new I18nResModel();
	@Resource
	protected II18nResService service;
	
	/**
	 * 
	 * @描述：查询国际化资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:26
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxI18nIndex() {
		try {
			
			//LOG.info("i18n.username:" + I18nMessageUtils.getLocaleText("i18n.username"));
			//LOG.info("i18n.username:" + I18nMessageUtils.getText("i18n.username"));
			//LOG.info("module:index;i18n.username:" + I18nMessageUtils.getModuleText("index", "i18n.username"));
			
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(service.getPagedI18nList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			// getValueStack().set("gnmkList", service.getResGnmkdmList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加国际化资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:43
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjI18n() {
		// getValueStack().set("gnmkList", service.getGnmkdmMapList());
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加保存国际化资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:52
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjBcI18n() {
		try {
			service.insertI18n(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99001")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99002")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：修改国际化资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgI18n() {
		try {
			I18nResModel resModel = service.getI18nModel(model.getRes_oid());
			PropertyUtils.copyProperties(model, resModel);
			// getValueStack().set("gnmkList", service.getGnmkdmMapList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：修改保存国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgBcI18n() {
		try {
			service.updateI18n(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99003")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99004")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：删除国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String scI18n() {
		try {
			model.setPks(model.getRes_oid().split(","));
			service.deleteI18n(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99005")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99006")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：查询国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:26
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxI18nResIndex() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(service.getPagedResourceList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			getValueStack().set("codeList", service.getResourceMapList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:43
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjI18nRes() {
		return SUCCESS;
	}
	
	private List<BaseMap> parseI18nMap() {
		String[] res_keys = getRequest().getParameterValues("res_key");
		String[] zh_cns = getRequest().getParameterValues("zh_cn");
		String[] en_uss = getRequest().getParameterValues("en_us");
		
		List<BaseMap> i18nMaps = null;
		if ((!BlankUtils.isBlank(res_keys)) && (res_keys.length > 0)) {
			i18nMaps = new ArrayList<BaseMap>();
			for (int i = 0, iLen = res_keys.length; i < iLen; i++) {
				if (StringUtils.isNotEmpty(res_keys[i])) {
					BaseMap map = new BaseMap();
					map.put("res_key", res_keys[i]);
					map.put("zh_cn", (zh_cns.length <= i ? "" : zh_cns[i]));
					map.put("en_us", (en_uss.length <= i ? "" : en_uss[i]));
					
					i18nMaps.add(map);
				}
				
			}
		}
		return i18nMaps;
	}

	/**
	 * 
	 * @描述：增加保存国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:52
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjBcI18nRes() {
		try {
			model.setI18nMaps(parseI18nMap());
			service.insertResource(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99001")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99002")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：修改国际化资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgI18nRes() {
		try {
			I18nResModel resModel = service.getModelResource(model.getRes_oid());
			PropertyUtils.copyProperties(model, resModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：修改保存国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgBcI18nRes() {
		try {
			model.setI18nMaps(parseI18nMap());
			service.updateResource(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99003")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99004")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：删除国际化资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:45:04
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String scI18nRes() {
		try {
			model.setPks(model.getRes_oid().split(","));
			service.deleteResource(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99005")));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99006")));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：下载资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月12日下午5:54:55
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xzI18nRes() {
		try {
			if (StringUtils.isNotEmpty(model.getRes_oid())) {
				model.setPks(model.getRes_oid().split(","));
			}
			ByteArrayOutputStream outzip = new ByteArrayOutputStream();
			ArchiveOutputStream archOuts = new ArchiveStreamFactory()
					.createArchiveOutputStream(ArchiveStreamFactory.ZIP, outzip);
			try {
				List<File> fileList = service.downloadResource(model);
				String filePath = CollectionUtils.isEmpty(fileList) ? null
						: fileList.get(0).getParentFile().getAbsolutePath()
								+ File.separator;
				ZipHelper.zipFiles(fileList, archOuts, filePath);
				this.fileName = new String(
						("国际资源Resource下载包.zip").getBytes("utf-8"), "ISO8859-1");
				this.inputStream = new ByteArrayInputStream(
						outzip.toByteArray());
				return Result.ZIP;
			} finally {
				IOUtils.closeQuietly(archOuts);
				IOUtils.closeQuietly(outzip);
			}
		} catch (BusinessException e) {
			getValueStack().set(DATA, e.getMessage());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, "资源文件下载失败！") );
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：发布资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月12日下午5:55:09
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String fbI18nRes() {
		try {
			if (StringUtils.isNotEmpty(model.getRes_oid())) {
				model.setPks(model.getRes_oid().split(","));
			}
			service.publishResource(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, "资源文件发布成功！ "));
		} catch (BusinessException e) {
			getValueStack().set(DATA, e.getMessage());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, ResultUtils.statusMap(ERROR, "资源文件发布失败！"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：查询资源文件资源内容
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月11日下午4:44:26
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxResourceI18n() {
		try {
			QueryModel queryModel = model.getQueryModel();
			queryModel
					.setItems(service.getPagedResourceI18n(model.getRes_oid()));
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}

	public String cxResCodeList() {
		try {
			List<BaseMap> modelList = service.getI18nResCodeList(model);
			if (BlankUtils.isBlank(modelList)) {
				modelList = new ArrayList<BaseMap>();
			}
			getValueStack().set(DATA, modelList);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：资源内容选择窗口
	 *@创建人:WuXinfeng
	 *@创建时间:2017年1月17日下午2:45:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxI18nSelected() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(service.getPagedI18nList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public I18nResModel getModel() {
		return model;
	}

	public void setModel(I18nResModel model) {
		this.model = model;
	}

	public II18nResService getService() {
		return service;
	}

	public void setService(II18nResService service) {
		this.service = service;
	}
	
}

