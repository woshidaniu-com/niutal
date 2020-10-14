/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.extend.task.CommonMessage;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.PersistentObject;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：常用意见Entity
 * <p>
 * @className:org.activiti.engine.extend.persistence.entity.CommonMessageEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:00:59
 */
public class CommonMessageEntity implements CommonMessage, PersistentObject{
	private static final long serialVersionUID = 7396661951311934777L;
	
	protected String id;
	protected String userId;
	protected String message;
	
	protected User user;
	
	public static CommonMessageEntity create(String userId, String message){
		CommonMessageEntity entity = new CommonMessageEntity();
		entity.setUserId(userId);
		entity.setMessage(message);
		return entity;
	}
	
	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("userId", this.userId);
		persistentState.put("message", this.message);
		return persistentState;
	}
	
	public User getUserEntity(){
		if(user == null && userId != null){
			user = Context.getCommandContext().getUserIdentityManager().findUserById(userId);
		}
		return user;
	}
	
	public void setMessageValueBytes(byte[] messageValueBytes){
		this.message = (messageValueBytes == null ? null : new String(messageValueBytes));
	}
	
	public byte[] getMessageValueBytes(){
		return (message!=null ? message.getBytes() : null);
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	

}
