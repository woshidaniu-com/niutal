/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.zxzx.dao.entities.YhbkModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.dao.entities.ZxhfModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.IYhbkService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 ZxwtController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午7:23:13
 * @功能描述 在线咨询-咨询问题
 * 
 */
@Controller("zxzxZxwtAction")
@RequestMapping("/zxzx/zxwt")
public class ZxwtController extends BaseController{

	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;

	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService service;
	
	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService cjwtService;
	
	@Autowired
	@Qualifier("zxzxYhbkService")
	private IYhbkService yhbkService;
	
	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;
	
	public static final String EXPORT_ID = "zxzx_zxwt";
	
	/**
	 * 查询咨询问题
	 * @return
	 */
	@RequestMapping("/cxZxwt.zf")
	public String cxZxwt(ZxwtModel model, HttpServletRequest request){
		List<kzdkModel> kzdkList = kzdkService.getModelList(new kzdkModel());
		request.setAttribute("kzdkList", kzdkList);
		return "/zxzx/zxwt/cxZxwt";
	}
	
	@ResponseBody
	@RequestMapping("/cxZxwtList.zf")
	public Object cxZxwtList(ZxwtModel model){
		try{
			User user = getUser();
			model.setLoginUser(user.getYhm());
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<ZxwtModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch(Exception ex){
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 回复咨询问题
	 * @return
	 */
	@RequestMapping("/hfZxwt.zf")
	public String hfZxwt(ZxwtModel model, HttpServletRequest request){
		try {
			ZxwtModel queryModel = service.getModel(model.getZxid());
			if(queryModel != null){
				model.setZxsj(queryModel.getZxsj());
				model.setZxr(queryModel.getZxr());
				model.setkzdt(queryModel.getkzdt());
				model.setZxid(queryModel.getZxid());
				model.setZxnr(queryModel.getZxnr());
				model.setDjl(queryModel.getDjl());
				model.setHfzt(queryModel.getHfzt());
				model.setSfgk(queryModel.getSfgk());
				model.setkzdkModel(queryModel.getkzdkModel());
				model.setZxrModel(queryModel.getZxrModel());
				model.setZxhfModel(queryModel.getZxhfModel());
			}
			request.setAttribute("model", model);
			return "/zxzx/zxwt/hfZxwt";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	/**
	 * 回复保存咨询问题
	 */
	@ResponseBody
	@RequestMapping("/hfBcZxwt.zf")
	public Object hfBcZxwt(ZxwtModel model){
		try{
			Object message;
			ZxwtModel updateModel = new ZxwtModel();
			ZxhfModel zxhfModel = model.getZxhfModel();
			zxhfModel.setHfid(UniqID.getInstance().getUniqIDHash());
			zxhfModel.setHfr(getUser().getYhm());
			zxhfModel.setHfsj(DateUtils.format(new Date()));
			zxhfModel.setZxid(model.getZxid());
			updateModel.setZxhfModel(zxhfModel);
			updateModel.setSfgk(model.getSfgk());
			updateModel.setZxid(model.getZxid());
			boolean submitResult = service.submitHfxx(updateModel);
			if(submitResult){
				message = getJSONMessage(MESSAGE_STATUS_SUCCESS, "ZXZX_I001");
			}else{
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I002");
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 查看咨询问题
	 * @return
	 */
	@RequestMapping("/ckZxwt.zf")
	public String ckZxwt(ZxwtModel model, HttpServletRequest request){
		try {
			ZxwtModel queryModel = service.getModel(model.getZxid());
			if(queryModel != null){
				model.setZxsj(queryModel.getZxsj());
				model.setZxr(queryModel.getZxr());
				model.setkzdt(queryModel.getkzdt());
				model.setZxid(queryModel.getZxid());
				model.setZxnr(queryModel.getZxnr());
				model.setDjl(queryModel.getDjl());
				model.setHfzt(queryModel.getHfzt());
				model.setSfgk(queryModel.getSfgk());
				model.setkzdkModel(queryModel.getkzdkModel());
				model.setZxrModel(queryModel.getZxrModel());
				model.setZxhfModel(queryModel.getZxhfModel());
			}
			request.setAttribute("model", model);
			return "/zxzx/zxwt/ckZxwt";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	/**
	 * 设置保存为常见问题
	 * 修改当前cjwt状态为“1”
	 * 复制一条数据到cjwt表并带上zxid字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/zsBcCjwt.zf")
	public Object zsBcCjwt(ZxwtModel model){
		try {
			Object message;
			ZxwtModel queryModel = service.getModel(model.getZxid());
			if(queryModel != null){
				boolean result = service.szCjwt(queryModel);
				if(result){
					message = getJSONMessage(MESSAGE_STATUS_SUCCESS, "ZXZX_I004");
				}else{
					message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I005");
				}
			}else{
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I003");
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 删除保存咨询问题
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/scBcZxwt.zf")
	public Object scBcZxwt(ZxwtModel model){
		try {
			Object message;
			if(model.getZxid() != null){
				String[] zxids = model.getZxid().split(",");
				String delCjwt = model.getDelCjwt();
				List<String> zxidList = new ArrayList<String>();
				for (String zxid : zxids) {
					zxidList.add(zxid);
				}
				boolean batchDelete = service.batchDelete(zxidList);
				
				//删除常见问题信息表中的数据
				if("1".equals(delCjwt)){
					batchDelete = cjwtService.batchDeleteByZxid(zxidList);
				}
				message = (batchDelete ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL).getJson();
			}else{
				message = MessageKey.PARAMATER_ERROR.getJson();
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@RequestMapping("/batchZxwt.zf")
	public String batchZxwt(ZxwtModel model, HttpServletRequest request){
		//查看板块列表
		List<YhbkModel> yhbkList = yhbkService.getYhbkList(getUser().getYhm());
		request.setAttribute("yhbkList", yhbkList);
		return "/zxzx/zxwt/batchZxwt";
	}
	
	
	/**
	 * 导出咨询的问题
	 * @return
	 */
	@RequestMapping("/exportZxwt.zf")
	public ResponseEntity<byte[]> exportZxwt(HttpServletRequest request){
        try{
        	String[] bkdms = request.getParameterValues("bkdm");
			File file = service.getBatchZxwtFile(bkdms, getUser().getYhm());
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
	
	@ResponseBody
	@RequestMapping("/batchZxwtUpload.zf")
	public Object batchZxwtUpload(@RequestParam("uploadfile") CommonsMultipartFile file){
		InputStream inputStream = null;
		Object message;
		try {
			inputStream = file.getInputStream();
			service.handleBatchZxwtFile(inputStream, getUser().getYhm());
			message = MessageKey.SAVE_SUCCESS.getJson();
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}finally{
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	/**
	 * 导出
	 * @return
	 */
	@RequestMapping("/export.zf")
	public ResponseEntity<byte[]> export(ZxwtModel model){
        try{
            //SearchParser.parseMybatisSQL(model.getSearchModel());
            ExportModel exportModel = new ExportModel();
			exportModel.setZgh(getUser().getYhm());
			exportModel.setDcclbh(EXPORT_ID);
			exportModel.setDataList(service.getExportData(model));
			File file = exportService.getExportFile(exportModel);
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

	public IExportService getExportService() {
		return exportService;
	}

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	public IZxwtService getService() {
		return service;
	}

	public void setService(IZxwtService service) {
		this.service = service;
	}

	public ICjwtService getCjwtService() {
		return cjwtService;
	}

	public void setCjwtService(ICjwtService cjwtService) {
		this.cjwtService = cjwtService;
	}

}
