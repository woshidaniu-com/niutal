package com.woshidaniu.i18n.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.exception.BusinessException;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.i18n.dao.daointerface.II18nDao;
import com.woshidaniu.i18n.dao.entities.I18nResModel;
import com.woshidaniu.i18n.event.TextProviderCacheEvictEvent;
import com.woshidaniu.i18n.service.svcinterface.II18nResService;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.util.file.DirectoryUtils;

/**
 * 
 * @类名称:I18nResServiceImpl.java
 * @类描述：国际化资源文件维护
 * @创建人：WuXinfeng
 * @创建时间：2017年1月11日 下午4:41:23
 * @版本号:v1.0
 */
@After
@Logger(model = "N01", business = "N0150")
@Service
public class I18nResServiceImpl extends BaseServiceImpl<I18nResModel, II18nDao> implements II18nResService {
	
	public static final String RES_TYPE_JS = "js";
	public static final String RES_TYPE_PROP = "properties";

	public static final String RES_TYPE_JS_DIR = "/js/globalweb/comp/i18n";
	public static final String RES_TYPE_PROP_DIR = "/WEB-INF/classes/i18n";

	private final Charset utf8 = Charset.forName("UTF-8");

	@Resource
	private II18nDao dao;

	/**
	 * 
	 * @描述：将locale字符串转换为Locale对象
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月17日上午8:46:40
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param locale
	 * @return
	 */
	private Locale toLocale(String locale) {
		Locale loc = null;
		try {
			String[] Arrloc = locale.split("_");
			loc = new Locale(Arrloc[0].toLowerCase(), Arrloc[1].toUpperCase());
		} catch (Exception ex) {
			loc = null;
		}
		return loc;
	}

	/**
	 * 
	 * @描述：获取资源文件的Properties对象
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月17日上午8:47:05
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @param locale
	 * @return
	 */
	private Properties getProperties(I18nResModel model, Locale locale) {
		Properties props = new Properties();
		List<I18nResModel> modelList = dao.getResourceI18nList(model.getRes_oid());
		String localeKey = locale.toString().toLowerCase();
		for (int i = 0, iSize = modelList.size(); i < iSize; i++) {
			I18nResModel resModel = modelList.get(i);
			String propKey = resModel.getRes_key();
			try {
				String propValue = (String) PropertyUtils.getProperty(resModel, localeKey);
				if (StringUtils.isNotEmpty(propKey) && StringUtils.isNotEmpty(propValue)) {
					/*if (!propKey.startsWith(model.getRes_code() + ".")) {
						propKey = model.getRes_code() + "." + propKey;
					}*/
					props.setProperty(propKey, propValue);
				}
			} catch (Exception ex) {
			}
		}
		return props;
	}

	private String getResourceFileDir(I18nResModel model) {
		String fileDir = model.getRes_path();
		if (StringUtils.isNotEmpty(fileDir)) {
			//绝对路径
			File file = DirectoryUtils.getExistDir(fileDir);
			if (!file.exists()) {
				//相对路径
				file = DirectoryUtils.getRealPath(fileDir);
			}
			if (file.exists()) {
				//路径是文件，则取上一级路径
				fileDir = file.isFile() ? file.getParentFile().getAbsolutePath() : fileDir;
			} else {
				fileDir = null;
			}
		}

		if (StringUtils.isEmpty(fileDir)) {
			fileDir = RES_TYPE_JS.equals(model.getRes_type()) ? RES_TYPE_JS_DIR : RES_TYPE_PROP_DIR;
			fileDir = DirectoryUtils.getRealPath(fileDir).getAbsolutePath();
		}
		return fileDir;
	}

	/**
	 * 
	 * @描述：获取资源文件
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月17日上午10:36:20
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @param model
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	private File getResourceFile(I18nResModel model, Locale locale)
			throws IOException {
		StringBuffer filePath = new StringBuffer(getResourceFileDir(model));
		if (StringUtils.isNotEmpty(model.getRes_code())) {
			filePath.append(File.separator).append(model.getRes_code());
		} else {
			filePath.append(File.separator).append("message");
		}
		filePath.append("_").append(locale.toString()).append(".")
				.append(model.getRes_type());
		return new File(filePath.toString());
	}

	/**
	 * 
	 * @描述：获取国际化语言信息列表
	 * @创建人:WuXinfeng
	 * @创建时间:2017年1月17日上午8:48:02
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	private List<Locale> getI18nLocaleList() {
		List<I18nResModel> strList = dao.getI18nLocaleList();
		List<Locale> locList = new ArrayList<Locale>();
		for (int i = 0, iSize = strList.size(); i < iSize; i++) {
			Locale loc = toLocale(strList.get(i).getLocale());
			if (loc != null) {
				locList.add(loc);
			}
		}
		return locList;
	}

	private void writeResource(I18nResModel model, File file, Locale locale)
			throws IOException {
		String comment = model.getRes_code() + locale.toString() + "资源文件";
		Properties props = getProperties(model, locale);
		if (RES_TYPE_JS.equals(model.getRes_type())) {
			StringBuffer content = new StringBuffer("//" + comment);
			content.append("\n").append(";(function($){")
					.append("$.i18n.map = $.i18n.map || {};")
					.append("$.extend($.i18n.map,{");
			Iterator<String> iterator = props.stringPropertyNames().iterator();
			while (iterator.hasNext()) {
				String propKey = iterator.next();
				content.append(StringUtils.quote(propKey)).append(":").append(StringUtils.quote(props.getProperty(propKey).replace("'", "\\'")));
				if (iterator.hasNext()) {
					content.append(",");
				}
			}
			content.append("});").append("}(jQuery))");

			FileUtils.writeByteArrayToFile(file,
					content.toString().getBytes(utf8));
		} else {
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(file);
				props.store(output, comment);
			} finally {
				IOUtils.closeOutput(output);
			}
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}

	@Override
	public List<I18nResModel> getPagedI18nList(I18nResModel model) {
		return dao.getPagedI18nByScope(model);
	}

	@Override
	public I18nResModel getI18nModel(String res_oid) {
		return dao.getI18nModel(res_oid);
	}

	@Override
	public boolean insertI18n(I18nResModel model) {
		return dao.insertI18n(model) > 0;
	}

	@Override
	public boolean updateI18n(I18nResModel model) {
		return dao.updateI18n(model) > 0;
	}

	@Override
	public boolean deleteI18n(I18nResModel model) {
		dao.deleteI18n(model);
		return true;
	}

	@Override
	public List<I18nResModel> getPagedResourceList(I18nResModel model) {
		return dao.getPagedResourceByScope(model);
	}

	@Override
	public I18nResModel getModelResource(String res_oid) {
		return dao.getModelResource(res_oid);
	}
	
	@Override
	public I18nResModel getI18nResource(String res_code,String res_type){
		return dao.getI18nResource(res_code, res_type);
	}

	@Override
	public boolean insertResource(I18nResModel model) {
		dao.insertResource(model);
		return true;
	}

	@Override
	public boolean updateResource(I18nResModel model) {
		dao.updateResource(model);
		return true;
	}

	@Override
	public boolean deleteResource(I18nResModel model) {
		dao.deleteResource(model);
		return true;
	}

	@Override
	public List<I18nResModel> getPagedResourceI18n(String res_oid) {
		if (StringUtils.isEmpty(res_oid)) {
			return new ArrayList<I18nResModel>();
		} else {
			return dao.getPagedResourceI18n(res_oid);
		}
	}

	@Override
	public List<I18nResModel> getResourceI18nList(String res_oid) {
		if (StringUtils.isEmpty(res_oid)) {
			return new ArrayList<I18nResModel>();
		} else {
			return dao.getResourceI18nList(res_oid);
		}
	}

	@Override
	public List<I18nResModel> getResourceMapList() {
		return dao.getResourceMapList();
	}

	@Override
	public List<I18nResModel> getGnmkdmMapList() {
		return dao.getGnmkdmMapList();
	}

	@Override
	public List<BaseMap> getI18nResCodeList(I18nResModel model) {
		return dao.getI18nResCodeList(model);
	}

	private boolean backupResource(File file) throws BusinessException {
		if ((!BlankUtils.isBlank(file)) && file.exists() && file.isFile()) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String dateTime = format.format(new Date());

				String[] fileNames = StringUtils.split(file.getName(), ".");
				fileNames[fileNames.length - 2] = fileNames[fileNames.length - 2]
						+ "_" + dateTime;
				String fileName = StringUtils.join(fileNames, ".");

				String filePath = file.getParentFile().getAbsolutePath()
						+ "_bak";
				filePath = DirectoryUtils.getExistDir(filePath)
						.getAbsolutePath() + File.separator + fileName;

				FileUtils.copyFile(file, new File(filePath));
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BusinessException(file.getAbsolutePath() + " 备份失败！");
			}
		}
		return true;
	}

	private File generateResource(I18nResModel model, Locale locale)
			throws BusinessException {
		File file = null;
		try {
			file = getResourceFile(model, locale);
			if (backupResource(file)) {
				writeResource(model, file, locale);
			}
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException(model.getRes_code() + locale.toString()
					+ "资源文件生成失败！");
		}
		return file;
	}

	private List<File> generateResource(List<I18nResModel> modelList)
			throws BusinessException {
		List<File> fileList = new ArrayList<File>();
		List<Locale> locList = getI18nLocaleList();
		for (int i = 0, iSize = modelList.size(); i < iSize; i++) {
			String resourcePath = null;
			for (int j = 0, ijSize = locList.size(); j < ijSize; j++) {
				File file = generateResource(modelList.get(i), locList.get(j));
				if ((!BlankUtils.isBlank(file)) && file.exists() && file.isFile()) {
					fileList.add(file);
					if (StringUtils.isEmpty(resourcePath)) {
						resourcePath = new StringBuffer(file.getParentFile()
								.getAbsolutePath())
								.append(File.separator)
								.append(file.getName().replace(
										"_" + locList.get(j).toString(), ""))
								.toString();
					}
				}
			}
			if (StringUtils.isNotEmpty(resourcePath)) {
				modelList.get(i).setRes_path(resourcePath);
			}
		}
		return fileList;
	}

	@Override
	public boolean publishResource(I18nResModel model)
			throws BusinessException {
		List<I18nResModel> modelList = dao.getAllResourceList(model);
		for (int i = 0, iSize = modelList.size(); i < iSize; i++) {
			modelList.get(i).setRes_path(null);
		}
		generateResource(modelList);

		String realPath = DirectoryUtils.getRealPath("").getAbsolutePath();
		for (int i = 0, iSize = modelList.size(); i < iSize; i++) {
			String path = modelList.get(i).getRes_path().replace(realPath, "");
			dao.updatePublishFlag(modelList.get(i).getRes_oid(),
					path == null ? "" : path);
		}
		//清理缓存
		getContext().publishEvent(new TextProviderCacheEvictEvent(this, null));
		return true;
	}
	
	@Override
	public List<File> downloadResource(I18nResModel model)
			throws BusinessException {
		String dirPath = null;
		try {
			dirPath = DirectoryUtils.getRootExistDir("i18n_temp")
					.getAbsolutePath();
		} catch (IOException e) {
			throw new BusinessException("生成国际化资源文件失败！");
		}
		List<I18nResModel> modelList = dao.getAllResourceList(model);
		for (int i = 0, iSize = modelList.size(); i < iSize; i++) {
			modelList.get(i).setRes_path(dirPath);
		}
		return generateResource(modelList);
	}
	
}
