package com.woshidaniu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.DymbglModel;
import com.woshidaniu.dao.entities.DymblxModel;
import com.woshidaniu.dao.entities.DysjxModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IDymbglService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 打印模板管理Action
 * @author guoqb[1127]
 * @date 2017-2-27 16:07:20
 */
@Controller
@RequestMapping("/xtgl/dymbgl")
public class DymbglController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(DymbglController.class);

	@Autowired
	private IDymbglService service;
	
	//是否允许一个类型启用多个模板
	private boolean acceptMultipleTemplateForOneType = false;
	
	@PostConstruct
	public void init(){
		{
			String key = "com.woshidaniu.globalweb.controller.DymbglController.acceptMultipleTemplateForOneType";
			String val = MessageUtil.getText(key);
			this.acceptMultipleTemplateForOneType = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.acceptMultipleTemplateForOneType;
			log.info("打印模块,是否允许一个类型启用多个模板{}={}",key,this.acceptMultipleTemplateForOneType);
		}
	}

	/**
	 * 查询打印模板页面
	 * @author guoqb[1127]
	 * @date 2017-2-28 14:40:11
	 * @param request
	 * @return
	 */
	@RequestMapping("/cxDymb")
	@RequiresPermissions("dymb:cx")
	public String cxDymb(HttpServletRequest request) {
		try {
			List<DymblxModel> dymblxList = service.getDymblxList();
			request.setAttribute("dymblxList", dymblxList);
			return "/globalweb/comp/xtgl/dymbgl/cxDymb";
		} catch (Exception e) {
			this.logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * ajax加载已处理统招数据
	 * @author guoqb[1127]
	 * @date 2017-2-14 18:30:34
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("dymb:cx")
	@RequestMapping("/getDymbListAjax")
	public Object getDymbListAjax(DymbglModel model){
		QueryModel queryModel = model.getQueryModel();
		try{
			SearchParser.parseMybatisSQL(model);//高级查询解析
			List<DymbglModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 新增打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-1 16:30:08
	 * @return
	 */
	@RequiresPermissions("dymb:zj")
	@RequestMapping("/zjDymb")
	public ModelAndView zjDymb(ModelAndView view){
		try{
			List<DymblxModel> dymblxList = service.getDymblxList();
			view.addObject("dymblxList", dymblxList);
			view.setViewName("/globalweb/comp/xtgl/dymbgl/zjDymb");
		} catch(Exception e){
			this.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * 修改打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-15 11:29:32
	 * @return
	 */
	@RequiresPermissions("dymb:xg")
	@RequestMapping("/xgDymb")
	public ModelAndView xgDymb(@RequestParam(required=true) String id, ModelAndView view){
		try{
			DymbglModel model = new DymbglModel();
			model.setId(id);
			model = service.getModel(model);
			List<DymblxModel> dymblxList = service.getDymblxList();
			view.addObject("dymblxList", dymblxList);
			view.addObject("model", model);
			List<DysjxModel> dysjxList= service.getDysjxListByDymblxdm(model.getMblxdm());
			view.addObject("dysjxList", dysjxList);
			view.setViewName("/globalweb/comp/xtgl/dymbgl/xgDymb");
		} catch(Exception e){
			this.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * ajax加载数据项
	 * @author guoqb[1127]
	 * @date 2017-3-17 15:12:26
	 * @param mblxdm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeDymblxAjax")
	public Object changeDymblxAjax(@RequestParam(value="op",required=true)String op,DymbglModel model){
		try{
			Map<String, Object> map = service.changeDymblx(op,model);
			return map;
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 新增打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-20 15:43:14
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/xzDymb")
	public Object xzDymb(DymbglModel model){
		try{
			Map<String, Object> map = service.insertDymb(model);
			return map;
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 修改打印模板名称
	 * @author guoqb[1127]
	 * @date 2017-3-20 16:43:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/xgDymbMc")
	public Object xgDymbMc(DymbglModel model){
		try{
			Map<String, Object> map = service.updateDymbMc(model);
			return map;
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 保存打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-9 15:09:13
	 * @param model
	 * @return
	 */
	/*@BusinessLog(czmk = "打印模板设置", czms = "新增打印模板", ywmc = "打印模板管理", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequestMapping("/saveDymb")
	public Object saveDymb(DymbglModel model){
		try{
			String nr = model.getNr();
			nr = nr.replaceAll("&syh;", "\"");
			nr = nr.replaceAll("&xyh;", "<");
			nr = nr.replaceAll("&dyh;", ">");
			model.setNr(nr);
			Map<String, String> map = service.insertDymb(model);
			return map;
		} catch(Exception e){
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}*/

	/**
	 * 修改打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-14 15:05:39
	 * @param model
	 * @return
	 */
	//@BusinessLog(czmk = "打印模板设置", czms = "修改打印模板,模板主键:${model.id}", ywmc = "打印模板管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/updateDymb")
	public Object updateDymb(DymbglModel model){
		try{
			boolean res = service.update(model);
			return res ? MessageKey.MODIFY_SUCCESS.getJson() :MessageKey.MODIFY_FAIL.getJson();
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 
	 * 这种模棱两可的方式废弃，不再使用
	 * 
	 * ajax验证是否存在启用的相同模板类型打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 17:20:50
	 * @param mblxdm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDymbByDymblxAjax")
	public Object getDymbByDymblxAjax(DymbglModel model){
		try{
			if(this.acceptMultipleTemplateForOneType){
				return true;
			}else{
				boolean res = service.getDymbByDymblx(model);
				return res;				
			}
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * ajax获取是否允许启用标识
	 * @param model
	 * @return
	 * 	
	 *  返回true 表示允许启用  
	 *  返回false 不允许启用,页面会提示提示原因
	 */
	@ResponseBody
	@RequestMapping("/getAcceptEnableFlagDymbByDymblxAjax")
	public Object getAcceptEnableFlagDymbByDymblxAjax(DymbglModel model){
		try{
			//若允许一种类型多个模板，则允许启用
			if(this.acceptMultipleTemplateForOneType){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("data", true);
				resultMap.put("message", "允许启用");
				resultMap.put("status", "success");
				return resultMap;
			}else{
				int count = service.getDymbByDymblxCount(model);
				if(count > 0){
					Map<String,Object> resultMap = new HashMap<String,Object>();
					resultMap.put("data", false);
					resultMap.put("message", "该模板类型已存在启用的打印模板,请重新选择!");
					resultMap.put("status", "success");
					return resultMap;
				}else{
					Map<String,Object> resultMap = new HashMap<String,Object>();
					resultMap.put("data", true);
					resultMap.put("message", "允许启用");
					resultMap.put("status", "success");
					return resultMap;
				}
			}
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 启用打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 15:20:34
	 * @param model
	 * @return
	 */
	@BusinessLog(czmk = "打印模板设置", czms = "启用打印模板,模板主键:${model.id}", ywmc = "打印模板管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/qyDymb")
	public Object qyDymb(DymbglModel model){
		try{
			model.setQyzt(IDymbglService.DYMB_QYZT_QY);
			boolean res = service.updateDymbQyzt(model);
			return res ? MessageKey.MODIFY_SUCCESS.getJson() :MessageKey.MODIFY_FAIL.getJson();
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 停用打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 15:20:30
	 * @param model
	 * @return
	 */
	@BusinessLog(czmk = "打印模板设置", czms = "停用打印模板,模板主键:${model.id}", ywmc = "打印模板管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/tyDymb")
	public Object tyDymb(DymbglModel model){
		try{
			model.setQyzt(IDymbglService.DYMB_QYZT_TY);
			boolean res = service.updateDymbQyzt(model);
			return res ? MessageKey.MODIFY_SUCCESS.getJson() :MessageKey.MODIFY_FAIL.getJson();
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 删除打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 16:27:40
	 * @param ids
	 * @return
	 */
	@BusinessLog(czmk = "打印模板设置", czms = "删除打印模板,模板主键:${ids}", ywmc = "打印模板管理", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequiresPermissions("dymb:sc")
	@RequestMapping("/scDymbgl")
	public Object scDymbgl(String ids){
		try{
			boolean result = service.scDymbgl(ids);
			MessageKey message = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return message.getJson();
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * ajax验证验证模板类型是否存在
	 * @author guoqb[1127]
	 * @date 2017-3-27 11:16:10
	 * @param mblxdm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/yzmblxSfczAjax")
	public Object yzmblxSfczAjax(@RequestParam(required = true) String mblxdm){
		try{
			boolean res = service.yzmblxSfcz(mblxdm);
			return res;
		} catch(Exception e){
			this.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 更新背景
	 * @author : Hanyc
	 * @date : 2019-06-13 13:55:11
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadBg")
	public Object uploadBg(MultipartHttpServletRequest req){
		return service.updateBg(req)? MessageKey.MODIFY_SUCCESS.getJson() :MessageKey.MODIFY_FAIL.getJson();
	}

	/**
	 * @description: 获取背景
	 * @author : Hanyc
	 * @date : 2019-06-13 13:55:00
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping("/getBg")
	public void getBg(HttpServletRequest req, HttpServletResponse resp) {
		Object bg = service.getBg(req.getParameter("id"));
		if(null != bg){
			InputStream input = null;
			OutputStream out = null;
			try{
				Blob blob = (Blob) bg;
				input = blob.getBinaryStream();
				out = resp.getOutputStream();
				byte[] data = new byte[(int)blob.length()];
				while(input.read(data) != -1){
					out.write(data);
				}
				/*BufferedImage localBufferedImage = ImageIO.read(input);
				localBufferedImage.getHeight();
				localBufferedImage.getWidth();*/
			}catch(Exception e){
				this.logException(e);
			}finally{
				try {
					if(null != input){
						input.close();
					}
					if(null != out){
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}