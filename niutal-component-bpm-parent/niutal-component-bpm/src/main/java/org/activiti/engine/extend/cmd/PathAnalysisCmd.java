package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：用于分析在给定条件下流程路径走向
 * 
 * 该功能请慎用，需要满足特定条件的！！
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.PathAnalysisCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月10日下午3:49:58
 */
public class PathAnalysisCmd extends AbstractCommand<List<PvmActivity>> {

	private static final Pattern PATTERN = Pattern.compile("\\'(\\$\\{([\\S\\s]+)\\})\\'");
	/**
	 * 流程定义
	 */
	protected String processDefinitionId;

	/**
	 * 流程参数
	 */
	protected Map<String, Object> variables;

	public PathAnalysisCmd(String processDefinitionId, Map<String, Object> variables) {
		super();
		this.processDefinitionId = processDefinitionId;
		this.variables = variables;
	}

	@Override
	protected List<PvmActivity> doExecute(CommandContext commandContext) {
		List<PvmActivity> activities = new ArrayList<PvmActivity>();

		ProcessDefinitionEntity processDefinitionEntity = commandContext.getProcessEngineConfiguration()
				.getDeploymentManager().findDeployedProcessDefinitionById(processDefinitionId);

		PvmActivity initial = processDefinitionEntity.getInitial();

		NoExecutionImpl variableScope = new NoExecutionImpl();

		variableScope.setVariables(variables);

		reseolvePath(initial, variableScope, activities);

		return activities;
	}

	protected String getActiveValue(String originalValue, String propertyName, ObjectNode elementProperties) {
		String activeValue = originalValue;
		if (elementProperties != null) {
			JsonNode overrideValueNode = elementProperties.get(propertyName);
			if (overrideValueNode != null) {
				if (overrideValueNode.isNull()) {
					activeValue = null;
				} else {
					activeValue = overrideValueNode.asText();
				}
			}
		}
		return activeValue;
	}

	protected void reseolvePath(PvmActivity activiti, VariableScope scope, List<PvmActivity> activities) {
		
		if (activiti != null) {
			String activiType = (String) activiti.getProperty(BpmnParse.ATTRIBUTE_TYPE);
			String defaultSeqFlow = (String) activiti.getProperty("default");
			
			if(BpmnParse.ELEMENT_TASK_USER.equals(activiType)){
				activities.add(activiti);
			}
			List<PvmTransition> outgoingTransitions = activiti.getOutgoingTransitions();
			
			boolean transitionTaken = false;
			
			PvmTransition defaultTransition = null;
			
			for (PvmTransition outgoingTransition : outgoingTransitions) {
				String conditionExpression = (String) outgoingTransition
						.getProperty(BpmnParse.PROPERTYNAME_CONDITION_TEXT);

				String transitionId = outgoingTransition.getId();
				
				//默认通道
				if(transitionId != null && transitionId.equals(defaultSeqFlow)){
					defaultTransition = outgoingTransition;
					continue;
				}

				//如果没有条件，接受此路径
				if (conditionExpression == null) {
					reseolvePath(outgoingTransition.getDestination(), scope, activities);
					transitionTaken = true;
					continue;
				}

				Matcher matcher = PATTERN.matcher(conditionExpression);
				matcher.find();
				String expressionText = matcher.group(1);
				//如果有效路径为空，接受此路径
				if (expressionText == null || expressionText.trim().length() == 0) {
					reseolvePath(outgoingTransition.getDestination(), scope, activities);
					transitionTaken = true;
					continue;
				}

				expressionText = expressionText.replace("\\", "");
				
				PvmActivity destination = outgoingTransition.getDestination();

				Expression expression = Context.getProcessEngineConfiguration().getExpressionManager()
						.createExpression(expressionText);

				Boolean result = (Boolean) expression.getValue(scope);
				//如果计算为‘true’，接受此路径
				if (result) {
					reseolvePath(destination, scope, activities);
					transitionTaken = true;
					continue;
				}
			}

			if((!transitionTaken) && (defaultTransition!=null)){
				reseolvePath(defaultTransition.getDestination(), scope, activities);
			}
			
		}
	}

	static class NoExecutionImpl extends ExecutionImpl {
		private static final long serialVersionUID = 1L;
	}

}
