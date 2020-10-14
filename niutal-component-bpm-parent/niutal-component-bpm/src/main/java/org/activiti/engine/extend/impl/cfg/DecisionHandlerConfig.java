/**
 * 
 */
package org.activiti.engine.extend.impl.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.task.DecisionHandler;
import org.activiti.engine.extend.task.DecisionHandlerInvocationFactory;
import org.activiti.engine.extend.task.DecisionType;
import org.activiti.engine.extend.task.impl.DecisionLogHandler;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：decision处理器配置
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午1:49:42
 */
public class DecisionHandlerConfig implements InitializingBean {

	/**
	 * decision 类型
	 */
	protected Map<String, DecisionType> decisionTypes;

	/**
	 * decision handler 调用工厂
	 */
	protected DecisionHandlerInvocationFactory decisionHandlerInvocationFactory;

	/**
	 * decision处理器
	 */
	protected Map<String, DecisionHandler> decisionHandlers;

	/**
	 * 前置处理器
	 */
	protected List<DecisionHandler> customPreHandlers;

	/**
	 * 默认前置处理器
	 */
	protected List<DecisionHandler> preHandlers;
	/**
	 * 后置处理器
	 */
	protected List<DecisionHandler> customPostHandlers;

	/**
	 * 默认后置处理器
	 */
	protected List<DecisionHandler> postHanders;

	public DecisionHandlerConfig() {

	}

	public void init() {
		if (decisionHandlers == null) {
			decisionHandlers = new HashMap<String, DecisionHandler>();
		}

		// 加入默认的处理器【日志处理器】、【调用处理器】
		if (preHandlers == null) {
			preHandlers = new ArrayList<DecisionHandler>();
		}

		if (customPreHandlers != null) {
			preHandlers.addAll(customPreHandlers);
		}

		preHandlers.add(new DecisionLogHandler());

		if (postHanders == null) {
			postHanders = new ArrayList<DecisionHandler>();
		}

		//让各自的handler去处理，这块代码个性化比较强，不便同意设置
		//postHanders.add(new DecisionPersistenceHandler());

		if (customPostHandlers != null) {
			postHanders.addAll(customPostHandlers);
		}

		if (decisionHandlerInvocationFactory != null) {
			decisionHandlerInvocationFactory.setDecisionHandlerConfig(this);
		}
	}

	public Map<String, DecisionHandler> getDecisionHandlers() {
		return decisionHandlers;
	}

	public void setDecisionHandlers(List<DecisionHandler> handlers) {
		if (this.decisionHandlers == null) {
			this.decisionHandlers = new HashMap<String, DecisionHandler>();
		}
		if(handlers != null){
			for (DecisionHandler decisionHandler : handlers) {
				this.decisionHandlers.put(decisionHandler.getType(), decisionHandler);
			}
		}
	}
	
	public List<DecisionHandler> getCustomPreHandlers() {
		return customPreHandlers;
	}

	public void setCustomPreHandlers(List<DecisionHandler> customPreHandlers) {
		this.customPreHandlers = customPreHandlers;
	}

	public List<DecisionHandler> getCustomPostHandlers() {
		return customPostHandlers;
	}

	public void setCustomPostHandlers(List<DecisionHandler> customPostHandlers) {
		this.customPostHandlers = customPostHandlers;
	}

	public List<DecisionHandler> getPreHandlers() {
		return preHandlers;
	}

	public List<DecisionHandler> getPostHanders() {
		return postHanders;
	}

	public DecisionHandlerInvocationFactory getDecisionHandlerInvocationFactory() {
		return decisionHandlerInvocationFactory;
	}

	public void setDecisionHandlerInvocationFactory(DecisionHandlerInvocationFactory decisionHandlerInvocationFactory) {
		this.decisionHandlerInvocationFactory = decisionHandlerInvocationFactory;
	}

	public Map<String, DecisionType> getDecisionTypes() {
		return decisionTypes;
	}

	public void setDecisionTypes(List<DecisionType> decisionTypes) {
		if(this.decisionTypes == null){
			this.decisionTypes = new LinkedHashMap<String, DecisionType>();
		}
		if(decisionTypes != null){
			for (DecisionType decisionType : decisionTypes) {
				this.decisionTypes.put(decisionType.getKey(), decisionType);
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

}
