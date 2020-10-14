/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.impl.context.ExtContext;
import org.activiti.engine.extend.task.DecisionType;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：获取decisiontype 列表
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.GetDecisionTypeCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午10:04:47
 */
public class GetDecisionTypesCmd extends AbstractCommand<List<DecisionType>> {

	@Override
	protected List<DecisionType> doExecute(CommandContext commandContext) {

		List<DecisionType> decisionTypes = new ArrayList<DecisionType>();
		Map<String, DecisionType> decisionTypesMap = ExtContext.getDecisionHandlerConfig().getDecisionTypes();
		if (decisionTypesMap != null) {
			Iterator<String> iterator = decisionTypesMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				decisionTypes.add(decisionTypesMap.get(key));
			}
		}
		return decisionTypes;

	}

}
