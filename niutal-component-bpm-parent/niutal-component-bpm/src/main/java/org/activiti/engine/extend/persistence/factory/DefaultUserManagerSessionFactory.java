/**
 * 
 */
package org.activiti.engine.extend.persistence.factory;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

/**
 * @author xiaokang
 *
 */
public class DefaultUserManagerSessionFactory implements SessionFactory {

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.interceptor.SessionFactory#getSessionType()
	 */
	@Override
	public Class<?> getSessionType() {
		return UserIdentityManager.class;
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
