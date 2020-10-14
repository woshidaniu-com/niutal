/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：咨询问题
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxTopicResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月19日下午5:22:52
 */
@Controller
@RequestMapping("/zxzx/web/topic")
public class ZxzxTopicResource extends AbstractZxzxWebResource {

	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService zxwtService;

	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService cjwtService;
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;

	@ResponseBody
	@RequestMapping(value = "getTopics/json", method = RequestMethod.GET)
	public Object getTopicsJson(ZxwtModel model) {
		
		QueryModel queryModel = model.getQueryModel();
		
		Map<String,String> configMap = csszService.getConfig();
		String sfyxwdlcxwt = configMap.get(ZxzxConstant.CSSZ_SFYXWDLCXWT);
		//不允许未登录查询问题 且 未登录
		if(!"1".equals(sfyxwdlcxwt) && !SubjectUtils.isAuthenticated()) {
			List<ZxwtModel> emptyList = Collections.emptyList();
			queryModel.setItems(emptyList);
			return queryModel;
		}
		
		// 查询约束
		if (model.getWebSearchBkdmValue() != null && (!"".equals(model.getWebSearchBkdmValue().trim()))) {
			model.setBkdms(Arrays.asList(model.getWebSearchBkdmValue().split(",")));
		}
		if (model.getWebSearchValue() != null) {
			model.setWebSearch(model.getWebSearchValue());
		}
		List<ZxwtModel> zxwtList = zxwtService.getPagedListWeb(model);
		queryModel.setItems(zxwtList);
		return queryModel;
	}

	@ResponseBody
	@RequestMapping(value = "getFAQs/json", method = RequestMethod.GET)
	public Object getFAQsJson(ZxwtModel model) {
		
		Map<String,String> configMap = csszService.getConfig();
		String sfyxwdlcxwt = configMap.get(ZxzxConstant.CSSZ_SFYXWDLCXWT);
		//不允许未登录查询问题 且 未登录
		if(!"1".equals(sfyxwdlcxwt) && !SubjectUtils.isAuthenticated()) {
			List<ZxwtModel> emptyList = Collections.emptyList();
			return emptyList;
		}
		CjwtModel queryModel = new CjwtModel();
		
		queryModel.setQueryModel(model.getQueryModel());
		List<CjwtModel> listWeb = cjwtService.getListWeb(queryModel);
		return listWeb;
	}

	public IZxwtService getZxwtService() {
		return zxwtService;
	}

	public void setZxwtService(IZxwtService zxwtService) {
		this.zxwtService = zxwtService;
	}

	public ICjwtService getCjwtService() {
		return cjwtService;
	}

	public void setCjwtService(ICjwtService cjwtService) {
		this.cjwtService = cjwtService;
	}
	
}
