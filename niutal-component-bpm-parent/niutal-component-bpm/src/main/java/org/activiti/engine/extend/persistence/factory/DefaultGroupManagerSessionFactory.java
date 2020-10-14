/**
 * 
 */
package org.activiti.engine.extend.persistence.factory;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

/**
 * @author xiaokang
 *
 */
public class DefaultGroupManagerSessionFactory implements SessionFactory {

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.interceptor.SessionFactory#getSessionType()
	 */
	@Override
	public Class<?> getSessionType() {
		return GroupIdentityManager.class;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.interceptor.SessionFactory#openSession()
	 */
	@Override
	public Session openSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
