package com.woshidaniu.globalweb.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.globalweb.PWUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IHandleService;
import com.woshidaniu.service.svcinterface.IMbglService;
import com.woshidaniu.service.svcinterface.ISjyglService;

/**
 * @className: HandleController
 * @description: 对外调用接口
 * @author : Hanyc
 * @date: 2018-12-05 17:34:25
 * @version V1.0
 */
@Controller
@RequestMapping("/pwgl/api")
public class HandleController extends BaseController{

	@Autowired
	protected IHandleService service;
	@Autowired
	protected IMbglService mbglService;
	@Autowired
	protected ISjyglService sjyglService;

	protected static final String X_FRAME_OPTIONS = "X-Frame-Options";

	/**
	 * @description: 打印
	 * @author : Hanyc
	 * @date : 2018-12-05 17:45:40
	 * @param model 包含模版的id
	 * @param request 必须包含主键，主键参数名大写
	 * @return
	 */
	@RequestMapping("/print")
	public String print(PWModel model, HttpServletRequest request, HttpServletResponse resp){
		try{
			QueryModel query = model.getQueryModel();
			if(query == null){
				SearchParser.parseMybatisSQL(model);
				query = model.getQueryModel();
			}
			//模版model
			model = mbglService.getModel(model);
			model.setQueryModel(query);
			String r = ERROR_VIEW;
			String key = null;
			switch (PWUtils.checkFile(model.getDrpath())) {
			case PWUtils.IS_PDF:
				key = service.getMergeFilePath(model, request, PWUtils.IS_PDF);
				r = "/globalweb/comp/pwgl/handle/pdf";
				break;
			case PWUtils.IS_DOC:
				key = service.getMergeFilePath(model, request, PWUtils.IS_DOC);
				r = "/globalweb/comp/pwgl/handle/pdf";
				break;
			case PWUtils.IS_DOCX:
				key = service.getMergeFilePath(model, request, PWUtils.IS_DOCX);
				r = "/globalweb/comp/pwgl/handle/pdf";
				break;
			default:
				break;
			}
			request.setAttribute("key", key);
			if(!StringUtils.isEmpty(PWUtils.PW_X_FRAME_OPTIONS)){
				resp.setHeader(X_FRAME_OPTIONS, PWUtils.PW_X_FRAME_OPTIONS);
			}
			return r;
		}catch(Exception e){
			super.logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * @description: 下载
	 * @author : Hanyc
	 * @date : 2018-12-05 17:45:34
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dl")
	public ResponseEntity<byte[]> dl(PWModel model, HttpServletRequest request){
		try{
			QueryModel query = model.getQueryModel();
			if(query == null){
				SearchParser.parseMybatisSQL(model);
				query = model.getQueryModel();
			}
			//模版model
			model = mbglService.getModel(model);
			model.setQueryModel(query);
			//模版model
			model = mbglService.getModel(model);
			File file = null;
			List<File> files = null;
			//文本替换
			switch (PWUtils.checkFile(model.getDrpath())) {
			case PWUtils.IS_PDF:
				files = service.getPdfFiles(model, request);
				break;
			case PWUtils.IS_DOC:
				files = service.getDocFiles(model, request);
				break;
			case PWUtils.IS_DOCX:
				files = service.getDocxFiles(model, request);
				break;
			default:
				break;
			}
			if(files.size() > 0){
				file = new File(PWUtils.JAVA_IO_TMPDIR, String.valueOf(System.currentTimeMillis()) + ".zip");
				PWUtils.compressFiles(file, files);
			}
			if(null != file){
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", file.getName());
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			}
		}catch(Exception e){
			super.logException(e);
		}
		return null;
	}

	/**
	 * @description: 下载
	 * @author : Hanyc
	 * @date : 2018-12-05 17:45:34
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dlop")
	public ResponseEntity<byte[]> dlop(PWModel model, HttpServletRequest request){
		try{
			QueryModel query = model.getQueryModel();
			if(query == null){
				SearchParser.parseMybatisSQL(model);
				query = model.getQueryModel();
			}
			//模版model
			model = mbglService.getModel(model);
			model.setQueryModel(query);
			//模版model
			model = mbglService.getModel(model);
			String filePath = null;
			//文本替换
			switch (PWUtils.checkFile(model.getDrpath())) {
			case PWUtils.IS_PDF:
				filePath = service.getMergeFilePath4Dlop(model, request, PWUtils.IS_PDF);
				break;
			case PWUtils.IS_DOC:
				filePath = service.getMergeFilePath4Dlop(model, request, PWUtils.IS_DOC);
				break;
			case PWUtils.IS_DOCX:
				filePath = service.getMergeFilePath4Dlop(model, request, PWUtils.IS_DOCX);
				break;
			default:
				break;
			}
			if(null != filePath){
				File file = new File(filePath);
				if(file.exists()){
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", file.getName());
					return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
				}
			}
			return null;
		}catch(Exception e){
			super.logException(e);
		}
		return null;
	}

	/**
	 * @description: TODO
	 * @author : Hanyc
	 * @date : 2018-12-13 19:42:34
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showPdf")
	public ResponseEntity<byte[]> showPdf (String key, HttpServletResponse resp){
		try{
			String path = service.getValue(key);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			if(!StringUtils.isEmpty(PWUtils.PW_X_FRAME_OPTIONS)){
				resp.setHeader(X_FRAME_OPTIONS, PWUtils.PW_X_FRAME_OPTIONS);
			}
			return PWUtils.getData(path, headers);
		}catch(Exception e){
			this.logException(e);
		}
		return null;
	}

	/**
	 * @description: TODO
	 * @author : Hanyc
	 * @date : 2018-12-13 19:42:34
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/printHtml")
	public void printHtml(PWModel model, HttpServletRequest request, HttpServletResponse resp){
		try{
			QueryModel query = model.getQueryModel();
			if(query == null){
				SearchParser.parseMybatisSQL(model);
				query = model.getQueryModel();
			}
			//模版model
			model = mbglService.getModel(model);
			model.setQueryModel(query);
			String content = null;
			switch (PWUtils.checkFile(model.getDrpath())) {
			case PWUtils.IS_PDF:
				content = service.getMergeFileHtml(model, request, PWUtils.IS_PDF);
				break;
			case PWUtils.IS_DOC:
				content = service.getMergeFileHtml(model, request, PWUtils.IS_DOC);
				break;
			case PWUtils.IS_DOCX:
				content = service.getMergeFileHtml(model, request, PWUtils.IS_DOCX);
				break;
			default:
				break;
			}
			if(!StringUtils.isEmpty(PWUtils.PW_X_FRAME_OPTIONS)){
				resp.setHeader(X_FRAME_OPTIONS, PWUtils.PW_X_FRAME_OPTIONS);
			}
			resp.setContentType("multipart/form-data");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html");
			ServletOutputStream out = resp.getOutputStream();
			out.write(content.getBytes());
			out.flush();
		}catch(Exception e){
			this.logException(e);
		}
	}
}