/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.dao.entites.DjrffModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.service.svcinterface.IDjrffService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;

/**
 * @description	： 我的问卷Controller
 * @author 		：康康（1571）
 */
@Controller
@RequestMapping(value = "/wjdc/wjgl")
public class WdwjController extends BaseController{

	@Autowired
	private IWjglService wjglService;
	
	@Autowired
	private IDjrffService djrffService;
	
	private boolean isGetWdwjFromDjrffb = true;
	
	@PostConstruct
	public void init() {
		{
			String val =  MessageUtil.getText("niutal.wjdc.isGetWdwjFromDjrffb");
			this.isGetWdwjFromDjrffb = val != null ? Boolean.parseBoolean(val) : this.isGetWdwjFromDjrffb;
			log.info("问卷调查,是否从答卷人分发表获得我的问卷信息:{}",this.isGetWdwjFromDjrffb);
		}
	}
	
	/**
	 * @description	： 我的问卷
	 * @return
	 */
	@RequestMapping("/wdwj")
	public ModelAndView wdwj() {
		ModelAndView view = new ModelAndView();
		try {
			User user = getUser();
			if(this.isGetWdwjFromDjrffb){
				
				List<DjrffModel> list = this.djrffService.queryByZjz(user.getYhm());
				List<WjglModel> wdwjList = new ArrayList<WjglModel>();
				for(DjrffModel djrffModel : list){
					WjglModel wjglModel = wjglService.getModel(djrffModel.getWjid());
					if(wjglModel != null){
						//答卷状态 
						//0：未答卷
						//1.答卷中
						//2.答卷完成
						if("完成".equals(djrffModel.getDjzt())){
							wjglModel.setDjzt(2);
						}else if("答卷中".equals(djrffModel.getDjzt())){
							wjglModel.setDjzt(1);
						}else if("未开始".equals(djrffModel.getDjzt())){
							wjglModel.setDjzt(0);
						}else{
							//nothing todo
						}
						wdwjList.add(wjglModel);
					}
				}
				view.addObject("wdwjList", wdwjList);
			}else{
				List<WjglModel> wdwjList = wjglService.getWdwjList(user.getYhm());
				view.addObject("wdwjList", wdwjList);
			}
			view.setViewName("/wjdc/wjgl/wdwj");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
}
