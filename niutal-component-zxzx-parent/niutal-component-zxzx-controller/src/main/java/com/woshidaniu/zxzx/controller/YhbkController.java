
package com.woshidaniu.zxzx.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.zxzx.dao.entities.YhbkModel;
import com.woshidaniu.zxzx.service.svcinterface.IYhbkService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;

/**
 * 
 * @类名 YhbkController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月23日 下午6:05:19
 * @功能描述 在线咨询-用户板块
 *
 */
@Controller("zxzxYhbkAction")
@RequestMapping("/zxzx/yhbk")
public class YhbkController extends BaseController {

	@Autowired
	@Qualifier("zxzxYhbkService")
	private IYhbkService service;

	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;

	@Autowired
	private IBmdmService bmdmService;

	/**
	 * 查询用户板块信息
	 * 
	 * @return
	 */
	@RequestMapping("/cxYhbk.zf")
	public String cxYhbk(YhbkModel model) {
		return "/zxzx/yhbk/cxYhbk";
	}

	@ResponseBody
	@RequestMapping("/cxYhbkList.zf")
	public Object cxYhbkList(YhbkModel model) {
		try {
			QueryModel queryModel = model.getQueryModel();
			List<YhbkModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 查询版块用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cxBkyhxxAjax.zf")
	public Object cxBkyhxxAjax(YhbkModel model) {
		try {
			QueryModel queryModel = model.getQueryModel();
			List<YhglModel> pagedList = service.getPagedListYfpYhxx(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 分配用户板块信息
	 * 
	 * @return
	 */
	@RequestMapping("/fpBkyh.zf")
	public String fpBkyh(YhbkModel model, HttpServletRequest request) {
		try {
			//List<kzdkModel> kzdkList = kzdkService.getModelList(new kzdkModel());
			List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
			request.setAttribute("jgdmsList", jgdmsList);
			//request.setAttribute("kzdkList", kzdkList);
			request.setAttribute("model", model);
			return "/zxzx/yhbk/fpBkyh";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/cxYfpYhxxList.zf")
	public Object cxYfpYhxxList(YhbkModel model) {
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<YhglModel> pagedList = service.getPagedListYfpYhxx(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 增加板块用户
	 * 
	 * @return
	 */
	@RequestMapping("/zjBkyh.zf")
	public String zjBkyh(YhbkModel model, HttpServletRequest request) {
		try {
			List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
			request.setAttribute("jgdmsList", jgdmsList);
			request.setAttribute("model", model);
			return "/zxzx/yhbk/zjBkyh";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/cxWfpYhxxList.zf")
	public Object cxWfpYhxxList(YhbkModel model) {
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<YhglModel> pagedList = service.getPagedListWfpYhxx(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 增加保存版块用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/zjBcBkyh.zf")
	public Object zjBcBkyh(YhbkModel model) {
		try {
			if (model.getBkdm() == null) {
				return getJSONMessage(MESSAGE_STATUS_FAIL, "I99002M", new String[] { "参数错误【版块代码为空】" });
			} else {
				List<YhbkModel> modelList = new ArrayList<YhbkModel>();
				String[] zghs = model.getZgh().split(",");
				for (String s : zghs) {
					YhbkModel queryModel = new YhbkModel();
					queryModel.setBkdm(model.getBkdm());
					queryModel.setZgh(s);
					modelList.add(queryModel);
				}
				boolean result = service.batchInsert(modelList);
				return (result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL).getJson();
			}
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 删除保存版块用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/scBcBkyh.zf")
	public Object scBcBkyh(YhbkModel model) {
		try {
			if (model.getBkdm() == null) {
				return getJSONMessage(MESSAGE_STATUS_FAIL, "I99002M", new String[] { "参数错误【版块代码为空】" });
			} else {
				List<YhbkModel> modelList = new ArrayList<YhbkModel>();
				String[] zghs = model.getZgh().split(",");
				for (String s : zghs) {
					YhbkModel queryModel = new YhbkModel();
					queryModel.setBkdm(model.getBkdm());
					queryModel.setZgh(s);
					modelList.add(queryModel);
				}
				boolean result = service.batchDelete(modelList);
				return (result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL).getJson();
			}
		} catch (Exception ex) {
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	public IYhbkService getService() {
		return service;
	}

	public void setService(IYhbkService service) {
		this.service = service;
	}
}
