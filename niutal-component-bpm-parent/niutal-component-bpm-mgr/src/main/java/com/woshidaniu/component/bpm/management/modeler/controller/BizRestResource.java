/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woshidaniu.component.bpm.management.modeler.controller;

import java.util.List;

import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.extend.service.ExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
@Controller
@RequestMapping("/activiti-rest-service")
public class BizRestResource {
	
	@Autowired
	protected ExtendService extService;

	protected ObjectMapper objectMapper = new ObjectMapper();
	
	@ResponseBody
	@RequestMapping(value = "/biz/{bizId}/getBizFileds.zf", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Object getBizFields(@PathVariable String bizId) throws Exception {
		List<BizField> bizFields = null;
		if (bizId != null) {
			bizFields = extService.getBizFiledList(bizId);
		}
		ArrayNode bizFieldsNode = objectMapper.createArrayNode();
		if(bizFields != null){
			for (BizField bizField : bizFields) {
				ObjectNode bizFieldNode = objectMapper.createObjectNode();
				bizFieldNode.put("id", bizField.getId());
				bizFieldNode.put("name", bizField.getName());
				bizFieldNode.put("label", bizField.getLabel());
				bizFieldNode.put("type", bizField.getType());
				bizFieldNode.put("description", bizField.getDescription());
				bizFieldNode.put("bizId", bizField.getBizId());
				if(bizField.getValues() != null){
					bizFieldNode.put("values", objectMapper.readTree(bizField.getValues()));
				}
				bizFieldsNode.add(bizFieldNode);
			}
		}
		return bizFieldsNode;
	}

}
