/**
 * 
 */
package org.activiti.engine.extend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangxb
 *
 * @class org.activiti.engine.extend.service.impl.StencilServiceImpl
 */
public class StencilServiceImpl {

	protected ObjectMapper objectMapper = new ObjectMapper();
	
	protected List<String> events;
	
	protected String listenerClassName;
	
	/**
	 * 
	 * @return
	 */
	public String createProcessEventListenerNode(){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"eventListeners\":\"[{\\\"event\\\": \\\"");
		sb.append(StringUtils.join(getEvents(), ","));
		sb.append("\\\", \\\"implementation\\\": \\\"");
		sb.append(getListenerClassName());
		sb.append("\\\", \\\"className\\\": \\\"");
		sb.append(getListenerClassName());
		sb.append("\\\", \\\"delegateExpression\\\": \\\"\\\", \\\"retrowEvent\\\": false, \\\"events\\\":[");
		
		List<String> eventList = new ArrayList<String>();
		for (String event : getEvents()) {
			StringBuffer eventBuf = new StringBuffer();
			eventBuf.append("{\\\"event\\\": \\\"");
			eventBuf.append(event);
			eventBuf.append("\\\"}");
			eventList.add(eventBuf.toString());
		}
		sb.append(StringUtils.join(eventList, ","));
		sb.append("");
		sb.append("]}]\"}");
		return sb.toString();
	}

	
	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public String getListenerClassName() {
		return listenerClassName;
	}

	public void setListenerClassName(String listenerClassName) {
		this.listenerClassName = listenerClassName;
	}
	
	
	
}
