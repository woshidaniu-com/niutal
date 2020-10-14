/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：咨询板块controller
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxkzdkResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月19日下午4:55:50
 */

@Controller
@RequestMapping("/zxzx/web/kzdk")
public class ZxzxkzdkResource extends AbstractZxzxWebResource {

	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;

	@ResponseBody
	@RequestMapping(value = "list/json", method = RequestMethod.GET)
	public Object getkzdksJson() {
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		ArrayNode array = getObjectMapper().createArrayNode();
		if (kzdkList != null) {
			for (kzdkModel kzdk : kzdkList) {
				String bkdm = kzdk.getBkdm();
				String bkmc = kzdk.getBkmc();
				Integer zxwtCount = kzdk.getZxwtCount();
				
				ObjectNode node = getObjectMapper().createObjectNode();
				node.put("dm", bkdm);
				node.put("mc", bkmc);
				node.put("count", zxwtCount);
				array.add(node);
			}
		}
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "zxlist/json", method = RequestMethod.GET)
	public Object getXskzdksJson() {
		List<kzdkModel> kzdkList = kzdkService.getkzdkModelListWeb();
		ArrayNode array = getObjectMapper().createArrayNode();
		if (kzdkList != null) {
			for (kzdkModel kzdk : kzdkList) {
				String bkdm = kzdk.getBkdm();
				String bkmc = kzdk.getBkmc();
				String xsxs = kzdk.getXsxs();
				
				ObjectNode node = getObjectMapper().createObjectNode();
				node.put("dm", bkdm);
				node.put("mc", bkmc);
				node.put("xsxs", xsxs);
				array.add(node);
			}
		}
		return array;
	}
}
