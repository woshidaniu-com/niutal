/**
 * 
 */
package org.activiti.editor.language.json.converter;

import java.util.Map;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.SequenceFlow;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：扩展SequenceFlowJsonConverter
 * <p>
 * 
 * @className:org.activiti.editor.language.json.converter.ExtSequenceFlowJsonConverter.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月1日上午10:44:44
 */
public class ExtSequenceFlowJsonConverter extends SequenceFlowJsonConverter {

	public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
		fillBpmnTypes(convertersToJsonMap);
	}

	public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_SEQUENCE_FLOW, ExtSequenceFlowJsonConverter.class);
	}

	public static void fillBpmnTypes(
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
		convertersToJsonMap.put(SequenceFlow.class, ExtSequenceFlowJsonConverter.class);
	}

	@Override
	public void convertToJson(BaseElement baseElement, ActivityProcessor processor, BpmnModel model,
			FlowElementsContainer container, ArrayNode shapesArrayNode, double subProcessX, double subProcessY) {

		SequenceFlow sequenceFlow = (SequenceFlow) baseElement;
		ObjectNode flowNode = BpmnJsonConverterUtil.createChildShape(sequenceFlow.getId(), STENCIL_SEQUENCE_FLOW, 172,
				212, 128, 212);
		ArrayNode dockersArrayNode = objectMapper.createArrayNode();
		ObjectNode dockNode = objectMapper.createObjectNode();
		dockNode.put(EDITOR_BOUNDS_X, model.getGraphicInfo(sequenceFlow.getSourceRef()).getWidth() / 2.0);
		dockNode.put(EDITOR_BOUNDS_Y, model.getGraphicInfo(sequenceFlow.getSourceRef()).getHeight() / 2.0);
		dockersArrayNode.add(dockNode);

		if (model.getFlowLocationGraphicInfo(sequenceFlow.getId()).size() > 2) {
			for (int i = 1; i < model.getFlowLocationGraphicInfo(sequenceFlow.getId()).size() - 1; i++) {
				GraphicInfo graphicInfo = model.getFlowLocationGraphicInfo(sequenceFlow.getId()).get(i);
				dockNode = objectMapper.createObjectNode();
				dockNode.put(EDITOR_BOUNDS_X, graphicInfo.getX());
				dockNode.put(EDITOR_BOUNDS_Y, graphicInfo.getY());
				dockersArrayNode.add(dockNode);
			}
		}

		dockNode = objectMapper.createObjectNode();
		dockNode.put(EDITOR_BOUNDS_X, model.getGraphicInfo(sequenceFlow.getTargetRef()).getWidth() / 2.0);
		dockNode.put(EDITOR_BOUNDS_Y, model.getGraphicInfo(sequenceFlow.getTargetRef()).getHeight() / 2.0);
		dockersArrayNode.add(dockNode);
		flowNode.put("dockers", dockersArrayNode);
		ArrayNode outgoingArrayNode = objectMapper.createArrayNode();
		outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(sequenceFlow.getTargetRef()));
		flowNode.put("outgoing", outgoingArrayNode);
		flowNode.put("target", BpmnJsonConverterUtil.createResourceNode(sequenceFlow.getTargetRef()));

		ObjectNode propertiesNode = objectMapper.createObjectNode();
		propertiesNode.put(PROPERTY_OVERRIDE_ID, sequenceFlow.getId());
		if (StringUtils.isNotEmpty(sequenceFlow.getName())) {
			propertiesNode.put(PROPERTY_NAME, sequenceFlow.getName());
		}

		if (StringUtils.isNotEmpty(sequenceFlow.getDocumentation())) {
			propertiesNode.put(PROPERTY_DOCUMENTATION, sequenceFlow.getDocumentation());
		}

		
		if (StringUtils.isNotEmpty(sequenceFlow.getConditionExpression())) {
			ObjectNode conditionExpressionNode = objectMapper.createObjectNode();
			conditionExpressionNode.put("evaluateExpression", sequenceFlow.getConditionExpression());
			propertiesNode.put(PROPERTY_SEQUENCEFLOW_CONDITION, conditionExpressionNode);
		}

		if (StringUtils.isNotEmpty(sequenceFlow.getSourceRef())) {
			FlowElement sourceFlowElement = container.getFlowElement(sequenceFlow.getSourceRef());
			if (sourceFlowElement != null) {
				String defaultFlowId = null;
				if (sourceFlowElement instanceof ExclusiveGateway) {
					ExclusiveGateway parentExclusiveGateway = (ExclusiveGateway) sourceFlowElement;
					defaultFlowId = parentExclusiveGateway.getDefaultFlow();

				} else if (sourceFlowElement instanceof Activity) {
					Activity parentActivity = (Activity) sourceFlowElement;
					defaultFlowId = parentActivity.getDefaultFlow();
				}

				if (defaultFlowId != null && defaultFlowId.equals(sequenceFlow.getId())) {
					propertiesNode.put(PROPERTY_SEQUENCEFLOW_DEFAULT, true);
				}
			}
		}

		if (sequenceFlow.getExecutionListeners().size() > 0) {
			BpmnJsonConverterUtil.convertListenersToJson(sequenceFlow.getExecutionListeners(), true, propertiesNode);
		}

		flowNode.put(EDITOR_SHAPE_PROPERTIES, propertiesNode);
		shapesArrayNode.add(flowNode);
	}
	
	@Override
	protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode,
			Map<String, JsonNode> shapeMap) {
		SequenceFlow flow = new SequenceFlow();

		String sourceRef = BpmnJsonConverterUtil.lookForSourceRef(elementNode.get(EDITOR_SHAPE_ID).asText(),
				modelNode.get(EDITOR_CHILD_SHAPES));
		if (sourceRef != null) {
			flow.setSourceRef(sourceRef);
			JsonNode targetNode = elementNode.get("target");
			if (targetNode != null && targetNode.isNull() == false) {
				String targetId = targetNode.get(EDITOR_SHAPE_ID).asText();
				if (shapeMap.get(targetId) != null) {
					flow.setTargetRef(BpmnJsonConverterUtil.getElementId(shapeMap.get(targetId)));
				}
			}
		}

		JsonNode conditionNode = getProperty(PROPERTY_SEQUENCEFLOW_CONDITION, elementNode);
		if (conditionNode != null) {

			if (conditionNode.isTextual() && conditionNode.isNull() == false) {
				flow.setConditionExpression(conditionNode.asText());

			} else if (conditionNode.get("evaluateExpression") != null) {
				JsonNode expressionNode = conditionNode.get("evaluateExpression");
				flow.setConditionExpression(expressionNode.asText());
			}
		}

		return flow;
	}


}
