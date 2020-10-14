package org.activiti.engine.extend.event.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：TODO
 *	 <br>class：org.activiti.engine.extend.event.callback.CustomEventCallback.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public interface CustomEventListener {

	public void onAuditorRevocation(ActivitiEvent event);
	
	public void onRebootAuditorRevocation(ActivitiEvent event);
	
	public void onInitiatorRevocation(ActivitiEvent event);

	public void onAuditorProcessCancellation(ActivitiEvent event);

	public void onInitiatorSuspension(ActivitiEvent event);
	
	public void onInitiatorActivation(ActivitiEvent event);
	
	public void onProcessFallback(ActivitiEvent event);
}
