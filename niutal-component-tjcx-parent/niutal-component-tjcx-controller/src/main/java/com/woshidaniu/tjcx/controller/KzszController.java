package com.woshidaniu.tjcx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 快照设置action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/niutal/tjcx/kzsz")
public class KzszController extends BaseController {
	
	private static final String format = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private IKzszService service;
	
	@RequestMapping("/kzsz.zf")
	public String kzsz() {
		return "/tjcx/kzsz";
	}
	
	@RequestMapping("/kzszUpdate.zf")
	public String kzszUpdate(KzszModel model){
		KzszModel modelInstance = service.getModel(model);	
		try {
			BeanUtils.copyProperties(model, modelInstance);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/tjcx/kzszUpdate";
	}
	
	@RequestMapping("/saveUpdate.zf")
	public String saveUpdate(HttpServletRequest request, KzszModel model){
		User user = getUser();
		KzszModel model2 = service.getModel(model.getKzszid());
		model2.setCzy(user.getYhm());
		model2.setTjsj(DateUtils.format(format));
		model2.setSzmc(model.getSzmc());
		String sfgy = model.getSfgy();
		model2.setSfgy(sfgy);
		if(sfgy != null && sfgy.equals("1")){
			model2.setKzjsr(model.getKzjsr());
		}else{
			model2.setKzjsr("");
		}
		boolean result = service.update(model2);
		request.setAttribute("result", result);
		return "/tjcx/kzszUpdate";
	}
	
	@ResponseBody
	@RequestMapping("/kzszDelete.zf")
	public Object kzszDelete(String ids){
		KzszModel model = new KzszModel();
		try{
			if(StringUtils.isNotBlank(ids)){
				String[] split = StringUtils.split(ids, ",");
				for (String string : split) {
					model.setKzszid(string);
					service.deleteCxkz(model);
				}
			}
			return MessageKey.DELETE_SUCCESS.getJson();
		}catch(Exception e){
			logException(e);
		}
		return MessageKey.DELETE_FAIL.getJson();
	}
	
	@ResponseBody
	@RequestMapping("/kzcx.zf")
	public Object kzcx(KzszModel model) {
		try {
			User user = getUser();
			model.setCzy(user.getYhm());
			QueryModel queryModel = model.getQueryModel();
			List<KzszModel> list = service.getPagedList(model);
			queryModel.setItems(list);
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	/**
	 * 快照修改
	 * 
	 * @return
	 */
	@RequestMapping("/kzxg.zf")
	public String kzxg() {
		return "/tjcx/kzxg";
	}

	/**
	 * 快照详情
	 * 
	 * @return
	 */
	@RequestMapping("/kzxq.zf")
	public String kzxq(KzszModel model) {	
		if(model.getKzszid() != null){
			model = service.getModel(model);
			if(model != null){
				String kzlx = model.getKzlx();
				String bbhxl = model.getBbhxl();
				if (kzlx != null && kzlx.equals("2") || bbhxl != null
						&& !bbhxl.trim().equals("")) {
					return "redirect:/niutal/tjcx/tjbb/tjlbxq.zf?kzszid="+model.getKzszid();
				} else {
					return "redirect:/niutal/tjcx/tjcx/tjcxlb.zf?kzszid="+model.getKzszid();
				}
			}
		}		
		return "redirect:/tjcx/tjcxError.jsp";
	}

	@ResponseBody
	@RequestMapping("/save.zf")
	public Object save(KzszModel model) {
		try {
			User user = getUser();
			model.setCzy(user.getYhm());
			model.setTjsj(DateUtils.format(format));
			service.insert(model);
			return model.getKzszid();
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/operation.zf")
	public Object operation(String oper, String id) {
		try {
			if (oper != null) {
				KzszModel model = new KzszModel();
				if (oper.equals("del")) {
					model.setKzszid(id);
					return service.deleteCxkz(model);
				} else if (oper.equals("edit")) {
					User user = getUser();
					KzszModel model2 = service.getModel(model.getKzszid());
					model2.setCzy(user.getYhm());
					model2
							.setTjsj(DateUtils.format(format));
					model2.setSzmc(model.getSzmc());
					String sfgy = model.getSfgy();
					model2.setSfgy(sfgy);
					if(sfgy != null && sfgy.equals("1")){
						model2.setKzjsr(model.getKzjsr());
					}
					return service.update(model2);
				}
			}
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/delete.zf")
	public Object delete(KzszModel model) {
		try {
			boolean result = service.deleteCxkz(model);
			return result;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	public IKzszService getService() {
		return service;
	}

	public void setService(IKzszService service) {
		this.service = service;
	}
}
