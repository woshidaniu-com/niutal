package com.woshidaniu.common.factory;

import org.springframework.beans.BeansException;

import com.woshidaniu.basicutils.BlankUtils;

/**
 * 服务工厂（用于取spring容器中定义的bean）
 * @see com.woshidaniu.spring.factory.ServiceFactory
 */
public class ServiceFactory extends com.woshidaniu.spring.factory.ServiceFactory {
	
	/**
	 * 
	 *@描述		: 扩展默认的Spring容器对象获取方法，
	 *			如： 通用的注解名称为 xxxService,某学校的个性化实现名称为 xxxService100121
	 *			   此处的数字为学校代码，故通过组合默认名称和学校代码，以便在运行期间获取个性化实现类
	 *@创建人		: kangzhidong
	 *@创建时间	: Feb 23, 201711:12:09 AM
	 *@param svrCode
	 *@param xxdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static Object getRealService(String svrCode,String xxdm ) {
		if(BlankUtils.isBlank(svrCode)){
			return null;
		}
		Object result = null;
		try {
			String tmpSvrCode = svrCode + xxdm;
			try {
				if(getSpringContext() != null ){
					result = getSpringContext().getBean(tmpSvrCode);
				}
				if ( result == null && getBeanFactory() != null){
					result = getBeanFactory().getBean(tmpSvrCode);
				}
			} catch (Exception e) {
				//LOG.debug("没有找到命名为["+tmpSvrCode+"]的对象，说明该对象无个性化实现：",e.getMessage());
				if(result == null){
					if(getSpringContext() != null ){
						result = getSpringContext().getBean(svrCode);
					}
					if ( result == null && getBeanFactory() != null){
						result = getBeanFactory().getBean(svrCode);
					}
				}
			}
		} catch (BeansException e) {
			//LOG.debug(e.getMessage());
		}
		return result;
	}
	 

}
