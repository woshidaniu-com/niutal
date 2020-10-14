/**
 * 
 */
package org.activiti.engine.extend.pvm;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;

import com.woshidaniu.basicutils.UniqID;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月24日上午10:25:08
 */
public class DynamicTransitionImpl extends TransitionImpl {
	private static final long serialVersionUID = -282474080619265260L;

	public DynamicTransitionImpl(Expression skipExpression, ProcessDefinitionImpl processDefinition) {
		super(null, skipExpression, processDefinition);
		this.id = generateDynamicTransitionId();
	}
	
	public DynamicTransitionImpl(String id, Expression skipExpression, ProcessDefinitionImpl processDefinition) {
		super(id, skipExpression, processDefinition);
	}
	
	public void setSource(ActivityImpl activityImpl){
		this.source = activityImpl;
	}
	
	public void setDestination(ActivityImpl activityImpl){
		this.destination = activityImpl;
	}
	
	protected String generateDynamicTransitionId(){
		return "dynamic-trans-" + UniqID.getInstance().getUniqIDHash().toLowerCase();
	}
}
