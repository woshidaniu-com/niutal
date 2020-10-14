package com.woshidaniu.workflow.service.impl;

import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.workflow.dao.IPendingAffairInfoDao;
import com.woshidaniu.workflow.enumobject.PendingAffairBusinessTypeEnum;
import com.woshidaniu.workflow.model.PendingAffairInfo;
import com.woshidaniu.workflow.service.IPendingAffairService;

/**
 * 
 * 类描述：待办事宜实现类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-19 上午09:42:35
 */
public class PendingAffairServiceImpl implements IPendingAffairService {
	private IPendingAffairInfoDao pendingAffairInfoDao;

	public void setPendingAffairInfoDao(IPendingAffairInfoDao pendingAffairInfoDao) {
		this.pendingAffairInfoDao = pendingAffairInfoDao;
	}

	public List<PendingAffairInfo> getListByUser(User user) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setUserId(user.getYhm());
		return this.getNewList(pendingAffairInfoDao.findByUserId(query));
	}

	@Override
	public List<PendingAffairInfo> getListByRoles(List<String> roleIds) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setQueryRoleIds(roleIds);
		return this.getNewList(pendingAffairInfoDao.findByRoleIds(query));
	}

	/**
	 * 
	 * 方法描述：重新组装LIST
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-13 上午11:10:14
	 */
	private List<PendingAffairInfo> getNewList(List<PendingAffairInfo> list) {
		Map<String, String> typeMap = PendingAffairBusinessTypeEnum.getMap();
		if (list != null && list.size() > 0) {
			for (PendingAffairInfo pa : list) {
				if (pa != null && StringUtils.isNotEmpty(pa.getAffairType())
						&& StringUtils.isNotEmpty(typeMap.get(pa.getAffairType()))) {
					pa.setAffairName(typeMap.get(pa.getAffairType()).replace("@@", String.valueOf(pa.getSumNumber())));
				}
			}
		}
		return list;
	}

	@Override
	public List<PendingAffairInfo> getListByQuery(PendingAffairInfo query) {
		return pendingAffairInfoDao.findList(query);
	}

	@Override
	public PendingAffairInfo getById(String id) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setId(id);
		return pendingAffairInfoDao.findById(query);
	}

	@Override
	public void addPendingAffairInfo(PendingAffairInfo entity) {
		pendingAffairInfoDao.insert(entity);
	}

	@Override
	public void modifyPendingAffairInfo(PendingAffairInfo pendingAffairInfo) {
		pendingAffairInfoDao.update(pendingAffairInfo);
	}

	@Override
	public void modifyByYwId(PendingAffairInfo pendingAffairInfo) {
		pendingAffairInfoDao.modifyByYwId(pendingAffairInfo);
	}

	@Override
	public void removePendingAffairInfo(String id) {
		PendingAffairInfo query = getById(id);
		pendingAffairInfoDao.delete(query);
	}

	@Override
	public void removePendingAffairInfoByBid(String bid) {
		PendingAffairInfo query = new PendingAffairInfo();
		query.setBusinessId(bid);
		pendingAffairInfoDao.delete(query);
	}
}
