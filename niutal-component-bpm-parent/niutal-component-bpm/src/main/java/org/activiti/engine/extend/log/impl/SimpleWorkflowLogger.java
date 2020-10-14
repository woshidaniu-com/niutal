package org.activiti.engine.extend.log.impl;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.log.ProcessLogger;
import org.activiti.engine.extend.task.DecisionInfo;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.CommentEntityManager;
import org.activiti.engine.task.Event;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：简单流程日志记录器 <br>
 * class：org.activiti.engine.extend.log.impl.SimpleWorkflowLogger.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class SimpleWorkflowLogger implements ProcessLogger {

	protected static final String LOGGER_NAME = "simpleLogger";

	protected CommentEntityManager commentEntityManager;

	@Override 
	public void log(DecisionInfo decisionInfo) {
		if (getCommentEntityManager() == null)
			throw new ActivitiException("CommentEntityManager没有设置");

		if (decisionInfo != null) {
			CommentEntity comment = createActivitiCommentEntity(decisionInfo);
			if (comment != null) {
				getCommentEntityManager().insert(comment);
			}
		}
	}


	public CommentEntityManager getCommentEntityManager() {
		if(commentEntityManager == null){
			commentEntityManager = Context.getCommandContext().getCommentEntityManager();
		}
		return commentEntityManager;
	}

	protected CommentEntity createActivitiCommentEntity(DecisionInfo decisionInfo) {
		if (decisionInfo == null)
			return null;
		CommentEntity comment = new CommentEntity();
		comment.setUserId(decisionInfo.getUserId());
		comment.setType(LOG_TYPE_PREFIX);
		comment.setTime(Context.getProcessEngineConfiguration().getClock().getCurrentTime());
		comment.setTaskId(decisionInfo.getTaskId());
		comment.setProcessInstanceId(decisionInfo.getProcessInstanceId());
		comment.setAction(Event.ACTION_ADD_COMMENT);
		if(decisionInfo.getDecisionType() != null){
			comment.setMessage(decisionInfo.getDecisionType().getValue());
		}else{
			comment.setMessage(decisionInfo.getDecision());
		}
		
		comment.setFullMessage(decisionInfo.getDecisionMessage());
		return comment;
	}

	@Override
	public String getName() {
		return LOGGER_NAME;
	}
}
