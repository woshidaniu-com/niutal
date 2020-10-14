package org.activiti.engine.extend.cfg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.extend.form.FormDeployer;
import org.activiti.engine.extend.impl.bpmn.parser.handler.ExtProcessParseHandler;
import org.activiti.engine.extend.impl.cfg.DecisionHandlerConfig;
import org.activiti.engine.extend.log.ProcessLoggerManager;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;
import org.activiti.engine.extend.service.ExtendFormService;
import org.activiti.engine.extend.service.MultiInstanceService;
import org.activiti.engine.extend.service.impl.DefaultMultiInstanceServiceImpl;
import org.activiti.engine.extend.service.impl.ExtendFormServiceImpl;
import org.activiti.engine.extend.service.impl.ExtendServiceImpl;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CallActivityParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CancelEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CompensateEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.EndEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ErrorEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.EventBasedGatewayParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.EventSubProcessParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ExclusiveGatewayParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.InclusiveGatewayParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.IntermediateCatchEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.IntermediateThrowEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ManualTaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.MessageEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ParallelGatewayParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ReceiveTaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ScriptTaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.SendTaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.SequenceFlowParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ServiceTaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.SignalEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.StartEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.SubProcessParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TaskParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TimerEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * class：org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class ExtendSpringProcessEnginConfiguration extends SpringProcessEngineConfiguration {

	private static Logger log = LoggerFactory.getLogger(ExtendSpringProcessEnginConfiguration.class);

	public static final String EXTEND_MYBATIS_MAPPING_FILE = "org/activiti/extend/db/mapping/mappings.xml";

	protected ExtendServiceImpl extendService = new ExtendServiceImpl(this);

	protected MultiInstanceService multiInstanceService = new DefaultMultiInstanceServiceImpl();

	protected ProcessLoggerManager processLoggerManager;

	protected DecisionHandlerConfig decisionHandlerConfig;

	protected int formDefinitionCacheLimit = -1;

	protected DeploymentCache<FormDefinitionEntity> formDefinitionCache;

	protected ExtendFormDeploymentManager formDeploymentManager;

	protected FormDeployer formDeployer;
	protected List<Deployer> formDeployers;
	protected List<Deployer> customFormDeployers;
	
	protected ExtendFormService extendFormService = new ExtendFormServiceImpl();

	/**
	 * 是否允许已经结束的流程重新被激活,用于审核人员撤销用,默认是不允许
	 */
	protected boolean allowProcessInstanceReboot = false;

	@Override
	public List<BpmnParseHandler> getDefaultBpmnParseHandlers() {
		// Alpabetic list of default parse handler classes
		List<BpmnParseHandler> bpmnParserHandlers = new ArrayList<BpmnParseHandler>();
		bpmnParserHandlers.add(new BoundaryEventParseHandler());
		bpmnParserHandlers.add(new BusinessRuleParseHandler());
		bpmnParserHandlers.add(new CallActivityParseHandler());
		bpmnParserHandlers.add(new CancelEventDefinitionParseHandler());
		bpmnParserHandlers.add(new CompensateEventDefinitionParseHandler());
		bpmnParserHandlers.add(new EndEventParseHandler());
		bpmnParserHandlers.add(new ErrorEventDefinitionParseHandler());
		bpmnParserHandlers.add(new EventBasedGatewayParseHandler());
		bpmnParserHandlers.add(new ExclusiveGatewayParseHandler());
		bpmnParserHandlers.add(new InclusiveGatewayParseHandler());
		bpmnParserHandlers.add(new IntermediateCatchEventParseHandler());
		bpmnParserHandlers.add(new IntermediateThrowEventParseHandler());
		bpmnParserHandlers.add(new ManualTaskParseHandler());
		bpmnParserHandlers.add(new MessageEventDefinitionParseHandler());
		bpmnParserHandlers.add(new ParallelGatewayParseHandler());
		bpmnParserHandlers.add(new ExtProcessParseHandler()); // *************这里替换为个性化的handler*************//
		bpmnParserHandlers.add(new ReceiveTaskParseHandler());
		bpmnParserHandlers.add(new ScriptTaskParseHandler());
		bpmnParserHandlers.add(new SendTaskParseHandler());
		bpmnParserHandlers.add(new SequenceFlowParseHandler());
		bpmnParserHandlers.add(new ServiceTaskParseHandler());
		bpmnParserHandlers.add(new SignalEventDefinitionParseHandler());
		bpmnParserHandlers.add(new StartEventParseHandler());
		bpmnParserHandlers.add(new SubProcessParseHandler());
		bpmnParserHandlers.add(new EventSubProcessParseHandler());
		bpmnParserHandlers.add(new TaskParseHandler());
		bpmnParserHandlers.add(new TimerEventDefinitionParseHandler());
		bpmnParserHandlers.add(new TransactionParseHandler());
		bpmnParserHandlers.add(new UserTaskParseHandler());

		// Replace any default handler if the user wants to replace them
		if (customDefaultBpmnParseHandlers != null) {

			Map<Class<?>, BpmnParseHandler> customParseHandlerMap = new HashMap<Class<?>, BpmnParseHandler>();
			for (BpmnParseHandler bpmnParseHandler : customDefaultBpmnParseHandlers) {
				for (Class<?> handledType : bpmnParseHandler.getHandledTypes()) {
					customParseHandlerMap.put(handledType, bpmnParseHandler);
				}
			}

			for (int i = 0; i < bpmnParserHandlers.size(); i++) {
				// All the default handlers support only one type
				BpmnParseHandler defaultBpmnParseHandler = bpmnParserHandlers.get(i);
				if (defaultBpmnParseHandler.getHandledTypes().size() != 1) {
					StringBuilder supportedTypes = new StringBuilder();
					for (Class<?> type : defaultBpmnParseHandler.getHandledTypes()) {
						supportedTypes.append(" ").append(type.getCanonicalName()).append(" ");
					}
					throw new ActivitiException("The default BPMN parse handlers should only support one type, but "
							+ defaultBpmnParseHandler.getClass() + " supports " + supportedTypes.toString()
							+ ". This is likely a programmatic error");
				} else {
					Class<?> handledType = defaultBpmnParseHandler.getHandledTypes().iterator().next();
					if (customParseHandlerMap.containsKey(handledType)) {
						BpmnParseHandler newBpmnParseHandler = customParseHandlerMap.get(handledType);
						log.info("Replacing default BpmnParseHandler " + defaultBpmnParseHandler.getClass().getName()
								+ " with " + newBpmnParseHandler.getClass().getName());
						bpmnParserHandlers.set(i, newBpmnParseHandler);
					}
				}
			}
		}

		// History
		for (BpmnParseHandler handler : getDefaultHistoryParseHandlers()) {
			bpmnParserHandlers.add(handler);
		}

		return bpmnParserHandlers;
	}

	@Override
	public ProcessEngine buildProcessEngine() {
		ProcessEngine processEngine = super.buildProcessEngine();
		if (processLoggerManager != null) {
			processLoggerManager.init();
		}
		return processEngine;
	}

	protected void initServices() {
		super.initServices();
		initService(extendService);
		initService(multiInstanceService);
		initService(extendFormService);
	}

	/**
	 * 
	 * <p>
	 * 方法说明：
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年6月1日下午2:42:00
	 * <p>
	 */
	protected void initFormDeployers() {
		if (formDeploymentManager == null) {
			formDeploymentManager = new ExtendFormDeploymentManager();
			if (formDefinitionCache == null) {
				if (processDefinitionCacheLimit <= 0) {
					formDefinitionCache = new DefaultDeploymentCache<FormDefinitionEntity>();
				} else {
					formDefinitionCache = new DefaultDeploymentCache<FormDefinitionEntity>(processDefinitionCacheLimit);
				}
			}

		}
		if (this.formDeployers == null) {
			this.formDeployers = new ArrayList<Deployer>();
			this.formDeployers.addAll(getDefaultFormDeployers());
			if (customFormDeployers != null) {
				this.formDeployers.addAll(customFormDeployers);
			}
		}
		formDeploymentManager.setFormDefinitionCache(formDefinitionCache);
		formDeploymentManager.setFormDeployers(formDeployers);
	}

	
	
	public ExtendFormDeploymentManager getFormDeploymentManager() {
		return formDeploymentManager;
	}

	public void setFormDeploymentManager(ExtendFormDeploymentManager formDeploymentManager) {
		this.formDeploymentManager = formDeploymentManager;
	}

	@Override
	protected void initDeployers() {
		super.initDeployers();
		initFormDeployers();
	}

	protected Collection<? extends Deployer> getDefaultFormDeployers() {
		List<Deployer> defaultFormDeployers = new ArrayList<Deployer>();

		if (formDeployer == null) {
			formDeployer = new FormDeployer();
			formDeployer.setIdGenerator(getIdGenerator());
		}

		defaultFormDeployers.add(formDeployer);
		return defaultFormDeployers;
	}

	public ExtendServiceImpl getExtendService() {
		return extendService;
	}

	public ProcessLoggerManager getProcessLoggerManager() {
		return processLoggerManager;
	}

	public void setProcessLoggerManager(ProcessLoggerManager processLoggerManager) {
		this.processLoggerManager = processLoggerManager;
	}

	public MultiInstanceService getMultiInstanceService() {
		return multiInstanceService;
	}

	@Override
	protected InputStream getMyBatisXmlConfigurationSteam() {
		return getResourceAsStream(EXTEND_MYBATIS_MAPPING_FILE);
	}

	public DecisionHandlerConfig getDecisionHandlerConfig() {
		return decisionHandlerConfig;
	}

	public void setDecisionHandlerConfig(DecisionHandlerConfig decisionHandlerConfig) {
		this.decisionHandlerConfig = decisionHandlerConfig;
	}

	public boolean isAllowProcessInstanceReboot() {
		return allowProcessInstanceReboot;
	}

	public void setAllowProcessInstanceReboot(boolean allowProcessInstanceReboot) {
		this.allowProcessInstanceReboot = allowProcessInstanceReboot;
	}

	public ExtendFormService getExtendFormService() {
		return extendFormService;
	}

	public void setExtendFormService(ExtendFormService extendFormService) {
		this.extendFormService = extendFormService;
	}

}
