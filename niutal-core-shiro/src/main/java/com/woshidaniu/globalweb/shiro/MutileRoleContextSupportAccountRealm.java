package com.woshidaniu.globalweb.shiro;

import java.util.Set;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.service.svcinterface.ISystemConfigService;
import com.woshidaniu.shiro.authc.DefaultAccount;
import com.woshidaniu.shiro.realm.DefaultAccountRealm;
import com.woshidaniu.shiro.service.AccountService;

/**
 * 支持多角色上下文的 AccountRealm
 * @author 1571
 *
 */
public class MutileRoleContextSupportAccountRealm extends DefaultAccountRealm{

	@Autowired
	private ISystemConfigService systemConfigService;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(this.systemConfigService.isEnableMutileRoleContext()){
			if(principals == null || principals.isEmpty()){
				return null;
			}
			AccountService accountService = this.getAccountService();
			
			if(principals.asList().size() <= 1){
				
				DefaultAccount account = new DefaultAccount();
				
				Set<String> roles = accountService.getRolesInfo(principals.getPrimaryPrincipal());
				account.setRoles(roles);
				
				Set<String> permissions = accountService.getPermissionsInfo(principals.getPrimaryPrincipal());
				account.setStringPermissions(permissions);
				
				return account;
			}else{
				DefaultAccount account = new DefaultAccount();
				
				Set<String> roles = accountService.getRolesInfo(principals.asSet());
				account.setRoles(roles);
				
				Set<String> permissions = accountService.getPermissionsInfo(principals.asSet());
				account.setStringPermissions(permissions);
				
				return account;
			}
		}else{
			return super.doGetAuthorizationInfo(principals);
		}
	}
}
