/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;

/**
 * @类名 kzdkController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 下午4:54:52
 * @功能描述 在线咨询-板块信息
 * 
 */
@Controller("zxkzdkxxAction")
@RequestMapping("/zxzx/kzdk")
public class kzdkController extends BaseController{

	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService service;

	/**
	 * 查询
	 * @return
	 */
	@RequestMapping("/cxkzdk.zf")
	public String cxkzdk(kzdkModel model){
		return "/zxzx/kzdk/cxkzdk";
	}
	
	@ResponseBody
	@RequestMapping("/cxkzdkList.zf")
	public Object cxkzdkList(kzdkModel model){
		try{
			QueryModel queryModel = model.getQueryModel();
			List<kzdkModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch(Exception ex){
			logException(ex);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 增加
	 * @return
	 */
	@RequestMapping("/zjkzdk.zf")
	public String zjkzdk(){
		return "/zxzx/kzdk/zjkzdk";
	}
	
	/**
	 * 增加-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/zjBckzdk.zf")
	public Object zjBckzdk(kzdkModel model){
		try{
			Object message;
			boolean updateResult = Boolean.FALSE;
			//检查重复
			kzdkModel queryModel = new kzdkModel();
			queryModel.setBkmc(model.getBkmc());
			List<kzdkModel> isExist = service.getModelList(queryModel);
			if(isExist.size() == 0){
				model.setBkdm(UniqID.getInstance().getUniqIDHash());
				Integer maxXsxs = service.getMaxXsxs();
				model.setXsxs(((maxXsxs==null ? 0 : maxXsxs) +1)+"");
				updateResult = service.insert(model);
				message = (updateResult ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL).getJson();
			}else{
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "I99015", new String[] { "参数错误【版块代码为空】" });
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("/xgkzdk.zf")
	public String xgkzdk(kzdkModel model, HttpServletRequest request){
		try {
			kzdkModel queryModel = service.getModel(model.getBkdm());
			if(queryModel != null){
				model.setBkdm(queryModel.getBkdm());
				model.setBkmc(queryModel.getBkmc());
				model.setSfqy(queryModel.getSfqy());
				model.setXsxs(queryModel.getXsxs());
				request.setAttribute("model", model);
			}
			return "/zxzx/kzdk/xgkzdk";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	/**
	 * 修改-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/xgBckzdk.zf")
	public Object xgBckzdk(kzdkModel model){
		try{
			Object message;
			boolean updateResult = Boolean.FALSE;
			//检查重复
			kzdkModel queryModel = new kzdkModel();
			queryModel.setBkmc(model.getBkmc());
			queryModel.setBkdm(model.getBkdm());
			int count = service.getCount(queryModel);
			if(count == 0){
				updateResult = service.update(model);
				message = (updateResult ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL).getJson();
			}else{
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "I99015", new String[]{model.getBkmc()});
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 启用停用
	 * @return
	 */
	@RequestMapping("/qytykzdk.zf")
	public String qytykzdk(kzdkModel model, HttpServletRequest request){
		try {
			kzdkModel queryModel = service.getModel(model.getBkdm());
			if(queryModel != null){
				model.setBkdm(queryModel.getBkdm());
				model.setBkmc(queryModel.getBkmc());
				model.setSfqy(queryModel.getSfqy());
				model.setXsxs(queryModel.getXsxs());
				request.setAttribute("model", model);
			}
			return "/zxzx/kzdk/qytykzdk";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	/**
	 * 启用停用-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qytyBckzdk.zf")
	public Object qytyBckzdk(kzdkModel model){
		try{
			Object message;
			boolean updateResult = Boolean.FALSE;
			updateResult = service.update(model);
			message = (updateResult ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL).getJson();
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * 删除-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/scBckzdk.zf")
	public Object scBckzdk(kzdkModel model){
		//需要先判断是否存在于【教师-板块表信息】
		//需要先判断是否存在于【常见咨询问题表】
		try {
			Object message;
			if(model.getBkdm() != null){
				String[] bkdms = model.getBkdm().split(",");
				List<String> bkdmList = new ArrayList<String>();
				for (String bkdm : bkdms) {
					int checkCanDelete = service.checkCanDelete(bkdm);
					if(checkCanDelete == 0){
						bkdmList.add(bkdm);
					}
				}
				boolean batchDelete = Boolean.FALSE;
				if(bkdmList.size() > 0){
					batchDelete = service.batchDelete(bkdmList);
					message = (batchDelete ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL).getJson();
				}else{
					message = getJSONMessage(MESSAGE_STATUS_FAIL,"ZXZX_I014");
				}
			}else{
				message = MessageKey.PARAMATER_ERROR.getJson();
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 顺序设置
	 * @return
	 */
	@RequestMapping("/sxszkzdk.zf")
	public String sxszkzdk(HttpServletRequest request){
		try {
			//只查询启用的咨询板块
			kzdkModel queryModel = new kzdkModel();
			queryModel.setSfqy("1");
			List<kzdkModel> modelList = service.getModelList(queryModel);
			request.setAttribute("kzdkList", modelList);
			return "/zxzx/kzdk/sxszkzdk";
		} catch (Exception ex) {
			logException(ex);
			return ERROR_VIEW;
		}
	}
	
	/**
	 * 顺序设置-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sxszBckzdk.zf")
	public Object sxszBckzdk(kzdkModel model){
		try{
			Object message;
			if(model.getPostData().size() > 0){
				boolean updateResult = service.kzdkXssz(model.getPostData());
				message = (updateResult ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL).getJson();
			}else{
				message = MessageKey.PARAMATER_ERROR.getJson();
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	public IkzdkService getService() {
		return service;
	}

	public void setService(IkzdkService service) {
		this.service = service;
	}

}
