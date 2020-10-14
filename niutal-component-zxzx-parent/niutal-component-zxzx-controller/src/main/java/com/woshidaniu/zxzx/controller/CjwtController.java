/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 CjwtController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午2:01:41
 * @功能描述 在线咨询-常见问题
 * 
 */
@Controller("zxzxCjwtAction")
@RequestMapping("/zxzx/cjwt")
public class CjwtController extends BaseController {

	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService service;

	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;

	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService zxwtService;

	/**
	 * 查询
	 * 
	 * @return
	 */
	@RequestMapping("/cxCjwt.zf")
	public String cxCjwt(HttpServletRequest request) {
		try {
			kzdkModel kzdkModel = new kzdkModel();
			kzdkModel.setSfqy("1");
			List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
			request.setAttribute("bkdmList", bkdmlList);
			return "/zxzx/cjwt/cxCjwt";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/cxCjwtList.zf")
	public Object cxCjwtList(CjwtModel model) {
		try {
			QueryModel queryModel = model.getQueryModel();
			List<CjwtModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 增加
	 * 
	 * @return
	 */
	@RequestMapping("/zjCjwt.zf")
	public String zjCjwt(HttpServletRequest request) {
		try {
			kzdkModel kzdkModel = new kzdkModel();
			kzdkModel.setSfqy("1");
			List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
			request.setAttribute("bkdmList", bkdmlList);
			return "/zxzx/cjwt/zjCjwt";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * 增加-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/zjBcCjwt.zf")
	public Object zjBcCjwt(CjwtModel model, HttpServletRequest request) {
		try {
			Object message;
			boolean updateResult = Boolean.FALSE;
			// 检查重复
			//CjwtModel queryModel = new CjwtModel();
			//queryModel.setWtbt(model.getWtbt());
			//int count = service.getCount(queryModel);
			//if (count == 0) {
				model.setWtid(UniqID.getInstance().getUniqIDHash());
				model.setCjsj(DateUtils.format(new Date()));
				updateResult = service.insert(model);
				message = (updateResult ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL).getJson();
			//} else {
			//	message = getJSONMessage(MESSAGE_STATUS_FAIL, "I99015", new String[] { model.getWtbt() });
			//}
			request.setAttribute("model", model);
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/xgCjwt.zf")
	public String xgCjwt(CjwtModel model, HttpServletRequest request) {
		try{
			CjwtModel queryModel = service.getModel(model.getWtid());
			if (queryModel != null) {
				model.setWtid(queryModel.getWtid());
				model.setWtbt(queryModel.getWtbt());
				model.setBkdm(queryModel.getBkdm());
				model.setSffb(queryModel.getSffb());
				model.setWtnr(queryModel.getWtnr());
				model.setWthf(queryModel.getWthf());
				model.setCjsj(queryModel.getCjsj());
				
				kzdkModel kzdkModel = new kzdkModel();
				kzdkModel.setSfqy("1");
				List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
				request.setAttribute("bkdmList", bkdmlList);
				request.setAttribute("model", model);
			}
			return "/zxzx/cjwt/xgCjwt";
		}catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
		
	}

	/**
	 * 修改-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/xgBcCjwt.zf")
	public Object xgBcCjwt(CjwtModel model, HttpServletRequest request) {
		try {
			Object message;
			boolean updateResult = Boolean.FALSE;
			// 检查重复
			//CjwtModel queryModel = new CjwtModel();
			//queryModel.setWtid(model.getWtid());
			//queryModel.setWtbt(model.getWtbt());
			//int count = service.getCount(queryModel);
			//if (count == 0) {
				updateResult = service.update(model);
				message = (updateResult ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL).getJson();
			//} else {
			//	message = getJSONMessage(MESSAGE_STATUS_FAIL, "I99015", new String[] { model.getWtbt() });
			//}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 删除-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/scBcCjwt.zf")
	public Object scBcCjwt(CjwtModel model, HttpServletRequest request) {
		try {
			if (model.getWtid() != null) {
				String[] wtids = model.getWtid().split(",");
				List<String> wtidList = new ArrayList<String>();
				for (String wtid : wtids) {
					wtidList.add(wtid);
				}
				service.batchDelete(wtidList);
			}
			return MessageKey.DELETE_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 启用/停用
	 * 
	 * @return
	 */
	@RequestMapping("/qytyCjwt.zf")
	public String qytyCjwt(CjwtModel model, HttpServletRequest request) {
		try {
			CjwtModel queryModel = service.getModel(model.getWtid());
			if (queryModel != null) {
				model.setWtid(queryModel.getWtid());
				model.setWtbt(queryModel.getWtbt());
				model.setSffb(queryModel.getSffb());
			}
			request.setAttribute("model", model);
			return "/zxzx/cjwt/qytyCjwt";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * 启用/停用 保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qytyBcCjwt.zf")
	public Object qytyBcCjwt(CjwtModel model, HttpServletRequest request) {
		try {
			Object message;
			boolean updateResult = Boolean.FALSE;
			updateResult = service.update(model);
			message = updateResult?MessageKey.MODIFY_SUCCESS.getJson():MessageKey.MODIFY_FAIL.getJson();
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
}
