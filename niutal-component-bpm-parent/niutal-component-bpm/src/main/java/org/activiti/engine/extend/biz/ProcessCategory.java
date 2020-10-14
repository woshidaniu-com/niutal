/**
 * 
 */
package org.activiti.engine.extend.biz;

import java.io.Serializable;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：流程类别
 * <p>
 * @className:org.activiti.engine.extend.biz.ProcessCategory.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月5日下午2:03:40
 */
public interface ProcessCategory extends Serializable {

	String getId();
	
	String getName();
	
	String getDescription();

}
