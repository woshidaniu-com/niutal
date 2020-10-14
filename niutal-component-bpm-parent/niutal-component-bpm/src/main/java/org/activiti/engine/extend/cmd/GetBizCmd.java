/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.extend.persistence.entity.BizEntity;
import org.activiti.engine.extend.persistence.entity.BizEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：GetBizCmd
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.GetBizCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午4:17:33
 */
public class GetBizCmd extends AbstractCommand<List<Biz>> {

	protected String bizId;

	public GetBizCmd(String bizId) {
		super();
		this.bizId = bizId;
	}

	@Override
	protected List<Biz> doExecute(CommandContext commandContext) {
		BizEntityManager bizEntityManager = commandContext.getSession(BizEntityManager.class);
		List<Biz> bizList = new ArrayList<Biz>();
		if (this.bizId == null) {
			List<BizEntity> findAllBizEntity = bizEntityManager.findAllBizEntity();
			if (findAllBizEntity != null) {
				bizList.addAll(findAllBizEntity);
			}
		} else {
			BizEntity findBizEntityById = bizEntityManager.findBizEntityById(bizId);
			if (findBizEntityById != null) {
				bizList.add(findBizEntityById);
			}
		}
		return bizList;

	}
}
