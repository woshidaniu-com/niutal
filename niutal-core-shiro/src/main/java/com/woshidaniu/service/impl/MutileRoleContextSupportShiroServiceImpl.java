package com.woshidaniu.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.log.User;
import com.woshidaniu.service.svcinterface.ISystemConfigService;

/**
 * 支持多角色上下文的ShiroServiceImpl
 * @author 1571
 */
@Service("mutileRoleContextSupportShiroService")
public class MutileRoleContextSupportShiroServiceImpl extends ShiroServiceImpl{
	
	private static final Logger log = LoggerFactory.getLogger(MutileRoleContextSupportShiroServiceImpl.class);
	
	@Autowired
	private ISystemConfigService systemConfigService;

	@Override
	public Set<String> getPermissionsInfo(Object principal) {
		
		if(this.systemConfigService.isEnableMutileRoleContext()){
			
			User user = (User) principal;
			String yhm = user.getYhm();
			String yhlx = user.getYhlx();
			List<String> jsdms = user.getJsdms();
			
			Set<String> allRolePermissionInfoSet = new HashSet<String>();
			
			for(String jsdm : jsdms){
				
				Set<String> set = this.doGetPermissionsInfo(yhm, yhlx, jsdm);
				
				log.debug("get permission size {} for user[yhm={},yhlx={},jsdm={}] in MutileRoleContext",set.size(),yhm,yhlx,jsdm);
				
				allRolePermissionInfoSet.addAll(set);
			}
			
			log.debug("return total permission size {} for user[yhm={},yhlx={}] in MutileRoleContext",allRolePermissionInfoSet.size(),yhm,yhlx);
			
			return allRolePermissionInfoSet;
		}else{
			return super.getPermissionsInfo(principal);
		}
	}
	
	@Override
	public Set<String> getRolesInfo(Object principal) {
		
		if(this.systemConfigService.isEnableMutileRoleContext()){
			
			Set<String> roles = new HashSet<String>();
			
			User user = (User) principal;
			roles.add(user.getJsdm());
			roles.addAll(user.getJsdms());
			
			return roles;
		}else{
			return super.getRolesInfo(principal);
		}
	}
}
