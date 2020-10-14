package com.woshidaniu.workflow.service.impl;

import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.workflow.dao.ISpBusinessDao;
import com.woshidaniu.workflow.enumobject.BusinessEnum;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpBusiness;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.service.ISpBusinessService;

/**
 * 类说明：业务管理SERVICE实现类
 * 
 * @author yingjie.fan
 * @version 1.0
 */
public class SpBusinessServiceImpl extends BaseInterfaceServiceImpl implements ISpBusinessService {
	/* @model: 注入SpBusiness */
	public ISpBusinessDao spBusinessDao;

	public void setSpBusinessDao(ISpBusinessDao spBusinessDao) {
		this.spBusinessDao = spBusinessDao;
	}

	@Override
	public void insert(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException {
		try {
			int result = spBusinessDao.getCountByNameAndType(spBusiness);
			if (result > 0) {
				throw new WorkFlowException("业务名称和业务类型相同的记录已经存在，不能执行新增操作！");
			} else {
				if (calssIds != null) {
					String classPrivilegeStr = "";
					if (calssIds != null && calssIds.length > 0) {
						for (int i = 0; i < calssIds.length; i++) {
							classPrivilegeStr += calssIds[i] + "-" + calssPrivilege[i] + ",";
						}
					}
					spBusiness.setClassesPrivilege(StringUtils.removeLast(classPrivilegeStr));
				}
				spBusinessDao.insert(spBusiness);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.woshidaniu.workflow.service.ISpBusinessService#insert(com.woshidaniu.workflow.model.SpBusiness)
	 */
	
	@Override
	public void insert(SpBusiness spBusiness) throws WorkFlowException {
		try {
			if(spBusiness != null && StringUtils.isNotEmpty(spBusiness.getRelDetail())){
				spBusinessDao.deleteByRelDetail(spBusiness.getRelDetail());
				spBusinessDao.insert(spBusiness);
			}else{
				throw new WorkFlowException("SpBusiness Object is null");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException {
		try {
			if (calssIds != null) {
				String classPrivilegeStr = "";
				if (calssIds != null && calssIds.length > 0) {
					for (int i = 0; i < calssIds.length; i++) {
						classPrivilegeStr += calssIds[i] + "-" + calssPrivilege[i] + ",";
					}
				}
				spBusiness.setClassesPrivilege(StringUtils.removeLast(classPrivilegeStr));
			}
			spBusinessDao.update(spBusiness);		
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String bId) throws WorkFlowException {
		try {
			spBusinessDao.delete(bId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.woshidaniu.workflow.service.ISpBusinessService#deleteByRelDetail(java.lang.String)
	 */
	
	@Override
	public void deleteByRelDetail(String relDetail) throws WorkFlowException {
		try {
			spBusinessDao.deleteByRelDetail(relDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpBusiness> findSpBusiness(SpBusiness spBusiness) throws WorkFlowException {
		try {
			if (spBusiness != null) {
				return spBusinessDao.findSpBusiness(spBusiness);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpBusiness findSpBusinessById(String bid) {
		return spBusinessDao.findSpBusinessById(bid).get(0);
	}

	@Override
	public SpBusiness findSpBusinessByIdAndBType(String bid, String bType) {
		SpBusiness spbusiness = null;
		List<SpBusiness> list = spBusinessDao.findSpBusinessById(bid);
		if(list != null && list.size() > 0){
			for(SpBusiness sb:list){
				if(StringUtils.isNotEmpty(sb.getBillType())){ 
					if(sb.getBillType().equals(bType)){
						spbusiness = sb;
						break;
					}
				}else{
					spbusiness = sb;
					break;
				}
			}
		}		
		return spbusiness;
	}

	@Override
	public SpBusiness findSpBusinessByBcode(String bcode) {
		SpBusiness sb = new SpBusiness();
		sb.setBcode(bcode);
		try {
			List<SpBusiness> list = spBusinessDao.findSpBusiness(sb);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.woshidaniu.workflow.service.ISpBusinessService#findSpBusinessByRelDetail(java.lang.String)
	 */
	
	@Override
	public SpBusiness findSpBusinessByRelDetail(String relDetail) {
		SpBusiness result=null;
		if(StringUtils.isNotEmpty(relDetail)){
			String bcode = BusinessEnum.SH_GRXX.getKey()+ "_" + relDetail;
			result=this.findSpBusinessByBcode(bcode);
		}
		if(result==null){
			throw new WorkFlowException("不存在记录");
		}else{
			return result;
		}
	}
	
	public List<SpBusiness> getBusinessType(SpBusiness spBusiness){
	    return spBusinessDao.getBusinessType(spBusiness);
	}

	
	public List<Map<String, String>> getPagedBusinessType(SpProcedure model) {
		return spBusinessDao.getPagedBusinessType(model);
	}

	@Override
	public List<Map<String, String>> getYwdl() {
		return spBusinessDao.getYwdl();
	}

}
