package com.woshidaniu.globalweb.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basemodel.QueryModel;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IMbglService;
import com.woshidaniu.service.svcinterface.ISjyglService;

/**
 * @className: MbglController
 * @description: 模版管理控制层
 * @author : Hanyc
 * @date: 2018-12-03 09:28:24
 * @version V1.0
 */
@Controller
@RequestMapping("/pwgl/mbgl")
public class MbglController extends BaseController {

	@Autowired
	protected IMbglService service;
	@Autowired
	protected ISjyglService sjyglService;

	/** @fields : PAGE_ROOT_DIR : 页面文件目录 */
	private static final String PAGE_ROOT_DIR = "/globalweb/comp/pwgl/mbgl";

	private static final String[] TYPE = new String[]{".PDF",".DOCX"};

	/**
	 * @description: 查询
	 * @author : Hanyc
	 * @date : 2018-12-03 09:23:17
	 * @param request
	 * @return
	 */
	@RequiresPermissions("PWmb:cx")
	@RequestMapping("/cx")
	public String cx(HttpServletRequest request){
		try{
			List<SjbzModel> sjyList = sjyglService.getSjyList();
			request.setAttribute("sjyList", sjyList);
			return PAGE_ROOT_DIR + "/cx";
		}catch(Exception e){
			super.logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * @description: 异步获取列表数据
	 * @author : Hanyc
	 * @date : 2018-12-03 09:23:13
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWmb:cx")
	@RequestMapping("/getPagedListAjax")
	public Object getPagedListAjax(PWModel model, HttpServletRequest request){
		try{
			SearchParser.parseMybatisSQL(model);
			QueryModel queryModel = model.getQueryModel();
			List<PWModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 增加
	 * @author : Hanyc
	 * @date : 2018-12-04 11:41:52
	 * @param model
	 * @param view
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWmb:zj")
	@RequestMapping("/zj")
	public ModelAndView zj(PWModel model, ModelAndView view){
		try{
			List<SjbzModel> sjyList = sjyglService.getSjyList();
			view.addObject("sjyList", sjyList);
			view.setViewName(PAGE_ROOT_DIR + "/zj");
		}catch(Exception e){
			super.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * @description: 增加保存
	 * @author : Hanyc
	 * @date : 2018-12-04 11:48:13
	 * @param model
	 * @param view
	 * @return
	 */
	@BusinessLog(czmk = "PDF/WORD模板管理", czms = "新增模板。", ywmc = "增加", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequiresPermissions("PWmb:zj")
	@RequestMapping("/zjbc")
	public Object zjbc(PWModel model, MultipartHttpServletRequest request){
		try{
			MultipartFile file = request.getFile("file");
			boolean flag = true;
			if(null != file){
				flag = false;
				String fileName = file.getOriginalFilename().toUpperCase();
				for(String type : TYPE){
					if(fileName.endsWith(type)){
						flag = true;
						break;
					}
				}
			}
			return flag && service.insert(model, request) ? MessageKey.DO_SUCCESS.status(new Object[]{"增加"})
					: MessageKey.DO_FAIL.status(new Object[]{"增加"});
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 修改
	 * @author : Hanyc
	 * @date : 2018-12-04 11:41:52
	 * @param model
	 * @param view
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWmb:xg")
	@RequestMapping("/xg")
	public ModelAndView xg(PWModel model, ModelAndView view){
		try{
			model = service.getModel(model);
			List<SjbzModel> sjyList = sjyglService.getSjyList();

			view.addObject("model", model);
			view.addObject("sjyList", sjyList);
			view.setViewName(PAGE_ROOT_DIR + "/xg");
		}catch(Exception e){
			super.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * @description: 修改保存
	 * @author : Hanyc
	 * @date : 2018-12-04 11:48:13
	 * @param model
	 * @param view
	 * @return
	 */
	@BusinessLog(czmk = "PDF/WORD模板管理", czms = "修改模板。", ywmc = "修改", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("PWmb:xg")
	@RequestMapping("/xgbc")
	public Object xgbc(PWModel model, HttpServletRequest request){
		try{
			boolean flag = true;
			if(request instanceof MultipartHttpServletRequest){
				MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
				if(null != file){
					flag = false;
					String fileName = file.getOriginalFilename().toUpperCase();
					for(String type : TYPE){
						if(fileName.endsWith(type)){
							flag = true;
							break;
						}
					}
				}
			}
			return flag && service.update(model, request) ? MessageKey.DO_SUCCESS.status(new Object[]{"修改"})
					: MessageKey.DO_FAIL.status(new Object[]{"修改"});
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	@RequestMapping("/dc")
	public ResponseEntity<byte[]> dc(PWModel model){
		try{
			File file = service.getModelFile(model);
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
}