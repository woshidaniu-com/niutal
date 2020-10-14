/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.extend.persistence.entity.BizFieldEntity;
import org.activiti.engine.extend.persistence.entity.BizFieldEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：GetBizFieldListCmd
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.GetBizFieldListCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午4:24:33
 */
public class GetBizFieldListCmd extends AbstractCommand<List<BizField>> {
	protected String bizId;

	public GetBizFieldListCmd(String bizId) {
		super();
		this.bizId = bizId;
	}

	@Override
	protected List<BizField> doExecute(CommandContext commandContext) {

		if (this.bizId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		List<BizField> bizFieldList = new ArrayList<BizField>();
		BizFieldEntityManager bizFieldEntityManager = commandContext.getSession(BizFieldEntityManager.class);
		List<BizFieldEntity> findBizFieldEntitiesByBizId = bizFieldEntityManager.findBizFieldEntitiesByBizId(bizId);
		if (findBizFieldEntitiesByBizId != null) {
			bizFieldList.addAll(findBizFieldEntitiesByBizId);
		}
		return bizFieldList;

	}

}
