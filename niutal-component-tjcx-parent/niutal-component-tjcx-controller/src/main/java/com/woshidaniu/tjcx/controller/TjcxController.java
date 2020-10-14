package com.woshidaniu.tjcx.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxService;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/niutal/tjcx/tjcx")
public class TjcxController extends BaseController {

	@Autowired
	protected ITjcxService service;
	@Autowired
	protected ITjcxCommon tjcxCommon;
	@Autowired
	protected ITjxmService tjxmService;
	@Autowired
	protected IKzszService kzszService;
	
//	@Autowired
//	@Qualifier("exportExcel")
//	private IExportService exportService;
	
	@Autowired
	@Qualifier("exportExcelPOI")
	protected IExportService exportServicePOI;
	
	protected static String TJCX_GNMK_BS	= "TJCX_GNMK";
	protected static String DCQZ	= "tjcx_";//导出前缀
//	private static int DEFAULT_EXPORT_MAX = 5000;//单个导出文件数据最大条数
	
	@RequestMapping("/tjcx.zf")
	public String tjcx(HttpServletRequest request) {
		User user = getUser();
		String gnmk = request.getParameter("gnmk");
		if(gnmk == null){
			gnmk = (String)request.getSession().getAttribute(TJCX_GNMK_BS);
		}
		KzszModel model = new KzszModel();
		model.setGnmk(gnmk);
		model.setCzy(user.getYhm());
		request.setAttribute("model", model);
		return "/tjcx/tjcx";
	}

	/**
	 * 
	 * @描述:统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午01:59:23
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/tjcxlb.zf")
	public String tjcxlb(HttpServletRequest request,KzszModel model) {
		String gltjmc = model.getGltjmc();
		if (StringUtils.isNotBlank(model.getKzszid())) {
			model = kzszService.getModel(model);
			String xmdm = model.getXmdm();
			TjxmModel tjxmModel = new TjxmModel();
			tjxmModel.setXmdm(xmdm);
			TjxmModel tjxmModel2 = tjxmService.getModel(tjxmModel);
			request.setAttribute("gnmk", tjxmModel2.getGnmk());
			request.setAttribute("tjcxlbxq", "1");
		}else{
			request.setAttribute("gnmk", model.getGnmk());
		}
		request.setAttribute("xmdm", model.getXmdm());
		request.setAttribute("gltj", model.getGltj());
		request.setAttribute("gltjmc", gltjmc);
		request.setAttribute("czy", model.getCzy());
		return "/tjcx/tjcxlb";
	}

	@ResponseBody
	@RequestMapping(value="/tjcxlb.zf", params="doType=query")
	public Object tjcxlb(HttpServletRequest request, String doType, KzszModel model){
		if (StringUtils.isNotBlank(model.getKzszid())) {
			model = kzszService.getModel(model);
		}
		try {
			User user = getUser();
			model.setCzy(user.getYhm());
			List<ExportConfigModel> exportConfigList = tjcxCommon.getExportConfigList(model);
			return exportConfigList;
		} catch (Exception e) {
			logException(e);
		}
		return MessageKey.DO_FAIL.getJson();
	}
	
	/**
	 * 
	 * @描述:统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午01:59:23
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/tjcxsj.zf", params="doType=query")
	public Object tjcxsj(KzszModel model) {
		try {
			QueryModel queryModel = model.getQueryModel();
			User user = getUser();
			model.setCzy(user.getYhm());
			queryModel.setItems(service.getTjcxsj(queryModel,model));
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	
	/**
	 * 导出
	 * @return
	 */
	@RequestMapping("/export.zf")
	public ResponseEntity<byte[]> export(KzszModel model){
		try{
			File file = null;
			User user = getUser();
			model.setCzy(user.getYhm());
			
			String curBbl = model.getCurBbl();
			String newGltj = model.getNewGltj();
			String gltj = model.getGltj();
			if(newGltj != null && !newGltj.trim().equals("")){
				if(gltj == null || gltj.trim().equals("")){
					gltj = newGltj;
				}else{
					gltj += " and " + newGltj;
				}
			}
			
			//需要加上当前的过滤条件
			if(curBbl != null && !curBbl.trim().equals("")){
				if(gltj != null && !gltj.trim().equals("")){
					gltj += " and " + curBbl;
				}else{
					gltj = curBbl;
				}
			}
			
			model.setGltj(gltj);
			if(log.isDebugEnabled()){
				log.debug("统计查询列表导出过滤条件：" + model.getGltj());
			}
			ExportModel exportModel = new ExportModel();
			exportModel.setZgh(user.getYhm());
			exportModel.setDcclbh(DCQZ + model.getXmdm());
			
			//先查询有几条需要导出的数据
			
			//1.如果数据量超过5000条，直接下载
			
			//2.如果数据量超过5000条，zip打包下载
			
//			int tjcxsjCountNoPage = service.getTjcxsjCountNoPage(model);
//			
//			if(DEFAULT_EXPORT_MAX >= tjcxsjCountNoPage){
//				exportModel.setDataList(service.getTjcxsjNoPage(model));
//				file = exportServicePOI.getExportFile(exportModel);
//			}else{
//				exportModel.setDataList(service.getTjcxsjNoPage(model));
//				file = exportServicePOI.getExportFile(exportModel);
//			}
			
			exportModel.setDataList(service.getTjcxsjNoPage(model));
			file = exportServicePOI.getExportFile(exportModel);
			
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			// Http响应头
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
			
		} catch(Exception e){
			logException(e);
		}
		return null;
	}

	public ITjcxService getService() {
		return service;
	}

	public void setService(ITjcxService service) {
		this.service = service;
	}

//	public IExportService getExportService() {
//		return exportService;
//	}
//
//	public void setExportService(IExportService exportService) {
//		this.exportService = exportService;
//	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}

	public ITjxmService getTjxmService() {
		return tjxmService;
	}

	public void setTjxmService(ITjxmService tjxmService) {
		this.tjxmService = tjxmService;
	}

	public IKzszService getKzszService() {
		return kzszService;
	}

	public void setKzszService(IKzszService kzszService) {
		this.kzszService = kzszService;
	}

	public IExportService getExportServicePOI() {
		return exportServicePOI;
	}

	public void setExportServicePOI(IExportService exportServicePOI) {
		this.exportServicePOI = exportServicePOI;
	}

}
