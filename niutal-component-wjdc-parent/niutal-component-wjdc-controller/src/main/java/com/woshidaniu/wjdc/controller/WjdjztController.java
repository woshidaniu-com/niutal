/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.search.core.SearchModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.wjdc.dao.entites.WjdjztModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjdjztService;

/**
 * @description	： 答问卷卷状态Controller
 * @author 		：康康（1571）
 */
@Controller
@RequestMapping(value = "/wjdc/wjdjzt")
public class WjdjztController extends BaseController{
	
	private static final String QUERY_PARAM_OBJECT_SESSION_KEY = WjdjztController.class.getName() + ".QUERY_PARAM_OBJECT_SESSION_KEY";
	
	@Autowired
	private IWjdjztService wjdjztService;
	
	@Autowired
	private IBmdmService bmdmService;

	/**
	 * @description	： 答卷状态查询
	 * @return
	 */
	@RequiresPermissions("wjdjzt:cx")
	@RequestMapping(value = "/cxdjzt")
	public String cxdjzt(Model model) {
		
		List<Map<String,String>> wjList = this.wjdjztService.getYffWjList();
		model.addAttribute("wjList",wjList);
		
		List<BmdmModel> bmList= bmdmService.queryModel(Collections.<String, String>emptyMap());
	    model.addAttribute("bmList",bmList);
		
		return "/wjdc/wjdjzt/cxWjdjzt";
	}
	
	@RequiresPermissions("wjdjzt:cx")
	@RequestMapping(value = "/getDjztList")
	@ResponseBody
	public Object getDjztList(WjdjztModel model,HttpSession httpSession) {
		try {
			SearchModel searchModel = model.getSearchModel();
			
			//尝试解析wjid
			//{"wjid":["488ca039a6e0b1c2fa60f72d4f60fc3f"],"bmdm_id":["01100000"]}
			String arrayKey = "wjid";
			if(searchModel.getSelectType() != null && searchModel.getSelectType().contains(arrayKey)) {
				parseWjidInSelectType(arrayKey,searchModel.getSelectType(),model);
			}
			
			SearchParser.parseMybatisSQL(model);
			
			List<Map<String,String>> list = this.wjdjztService.getPagedDjztList(model);
			
			httpSession.setAttribute(QUERY_PARAM_OBJECT_SESSION_KEY, model);
			
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(list);
			
			return queryModel;
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	private void parseWjidInSelectType(String arrayKey,String selectType,WjdjztModel model) {
		JSONObject jsonObject = JSONObject.parseObject(selectType);
		JSONArray jsonArray = jsonObject.getJSONArray(arrayKey);
		if(jsonArray == null || jsonArray.isEmpty()) {
			return;
		}
		if(jsonArray.size() > 1) {
			return;
		}
		
		String wjid = jsonArray.getString(0);
		model.setWjid(wjid);
	}

	@RequiresPermissions("wjdjzt:dc")
	@RequestMapping(value = "/exportDjztList")
	@ResponseBody
	public ResponseEntity<byte[]> exportDjztList(HttpSession httpSession) {
		WjdjztModel model = (WjdjztModel) httpSession.getAttribute(QUERY_PARAM_OBJECT_SESSION_KEY);
		try {
			File file = this.wjdjztService.exportDjztList(model);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String("答卷状态详情.xls".getBytes(), "ISO8859-1"));
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
			headers.setContentLength(bytes.length);
			return new ResponseEntity<byte[]>(bytes , headers , HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
			return null;
		}
	}
}
