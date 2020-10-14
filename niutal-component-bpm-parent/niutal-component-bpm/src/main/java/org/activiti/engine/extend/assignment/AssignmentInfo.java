package org.activiti.engine.extend.assignment;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

public interface AssignmentInfo {
	String getGroupId();
	
	String getUserId();
	
	String getClazzId();
	
	String getType();
	
	String getTaskDefinitionId();
	
	String getProcessDefinitionId();
	
	//获取优先级
	int getPriority();
	
	//
	User getUserEntity();
	Group getGroupEntity();
	
	/**
	 * 
	 * <p>方法说明：如果是user类型返回getUserEntity()的用户信息, 如果是group类型，则返回该组下所有用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月20日上午9:07:21<p>
	 * @return
	 */
	List<User> resolveUser();
}
