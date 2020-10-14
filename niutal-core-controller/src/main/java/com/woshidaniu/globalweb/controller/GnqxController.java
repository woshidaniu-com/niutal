package com.woshidaniu.globalweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.GnqxModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IJsglService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：功能菜单辅助功能
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]</a>
 * @version 2017年5月31日上午10:06:39
 */
@RestController
@RequestMapping(value ="/xtgl/gnqx")
public class GnqxController extends BaseController {

	@Autowired
	private IJsglService jsglService;
	
	/**
	 * 
	 * <p>方法说明：查询拥有功能权限的角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日上午10:18:58<p>
	 * @param gnczid 功能操作ID
	 * @return 角色列表
	 */
	@GetMapping(value="/getGnqxRole")
	public QueryModel getGnqxRole(@RequestParam String[] gnczid , GnqxModel model){
		QueryModel queryModel = model.getQueryModel();
		List<JsglModel> pagedList = jsglService.getGnqxRole(model);
		queryModel.setItems(pagedList);
		return queryModel;
	}
	
		
	
	/**
	 * 
	 * <p>方法说明：查询拥有功能权限的用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日上午10:22:02<p>
	 * @param gnczid 功能操作ID
	 * @return 用户列表
	 */
	@GetMapping(value="/getGnqxUser")
	public QueryModel getGnqxUser(@RequestParam String[] gnczid , GnqxModel model){
		QueryModel queryModel = model.getQueryModel();
		List<YhglModel> pagedList = jsglService.getGnqxUser(model);
		queryModel.setItems(pagedList);
		return queryModel;
	}
	
	
	/**
	 * 
	 * <p>方法说明：查询拥有二级权限的用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日下午2:55:29<p>
	 * @param gnczid  功能操作ID
	 * @param model
	 * @return 用户列表
	 */
	@GetMapping(value="/getEjqxUser")
	public QueryModel getPagedEjqxUser(@RequestParam String[] gnczid , GnqxModel model){
		QueryModel queryModel = model.getQueryModel();
		List<YhglModel> pagedList = jsglService.getGnqxUser(model);
		queryModel.setItems(pagedList);
		return queryModel;
	}
	
}
