/**
 * 
 */
package com.woshidaniu.globalweb.shiro;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.session.Session;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.woshidaniu.common.log.User;
import com.woshidaniu.service.svcinterface.IAccountStateService;
import com.woshidaniu.shiro.CheckAttemptsAccountException;
import com.woshidaniu.shiro.InvalidStateException;
import com.woshidaniu.shiro.authc.DefaultAccount;
import com.woshidaniu.shiro.realm.RealmListener;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author zhidong kang
 * @desc 用于在登录出现出现错误的时候记录缓存
 */
public class AuthenticationFailedRealmListener implements RealmListener {

	static final String niutal_PWD_RETRY_CACHE_NAME = "niutal_PWD_RETRY_CACHE";

	static final String niutal_ACCT_LOCK_CACHE_NAME = "niutal_ACCT_LOCK_CACHE";

	private CacheManager cacheManager;

	private IAccountStateService accountStateService;

	private Cache retryCache;
	private Cache acctLockCache;

	private int maxRetryToLock = 3;
	private int maxLockToDisable = 3;

	public AuthenticationFailedRealmListener() {
		super();

		Integer configMaxRetry = MessageUtil.getInt("niutal.max.pwd.retry.to.lock");
		if (null != configMaxRetry)
			maxRetryToLock = configMaxRetry;

		Integer configMaxLock = MessageUtil.getInt("niutal.max.locks.to.disable.acct");
		if (null != configMaxLock)
			maxLockToDisable = configMaxLock;
	}

	protected void initCache() {
		retryCache = getCacheManager().getCache(niutal_PWD_RETRY_CACHE_NAME);
		acctLockCache = getCacheManager().getCache(niutal_ACCT_LOCK_CACHE_NAME);
	}

	public int getMaxRetryToLock() {
		return maxRetryToLock;
	}

	public void setMaxRetryToLock(int maxRetryToLock) {
		this.maxRetryToLock = maxRetryToLock;
	}

	public int getMaxLockToDisable() {
		return maxLockToDisable;
	}

	public void setMaxLockToDisable(int maxLockToDisable) {
		this.maxLockToDisable = maxLockToDisable;
	}

	public Cache getRetryCache() {
		return retryCache;
	}

	public void setRetryCache(Cache retryCache) {
		this.retryCache = retryCache;
	}

	public Cache getAcctLockCache() {
		return acctLockCache;
	}

	public void setAcctLockCache(Cache acctLockCache) {
		this.acctLockCache = acctLockCache;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public IAccountStateService getAccountStateService() {
		return accountStateService;
	}

	public void setAccountStateService(IAccountStateService accountStateService) {
		this.accountStateService = accountStateService;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.shiro.realm.RealmListener#onAuthenticationFail(org.apache.
	 * shiro.authc.AuthenticationToken,
	 * org.apache.shiro.authc.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFail(AuthenticationToken token, AuthenticationException ex) {
		if (null == token || !(token.getPrincipal() instanceof Serializable))
			return;

		/**
		 * 如果账户被停用，不处理，把错误往上抛
		 */
		if (ex instanceof InvalidStateException) {
			throw ex;
		}

		/**
		 * 如果账户已经被锁定，封装异常往上抛
		 */
		if (ex instanceof CheckAttemptsAccountException)
			throw new ExcessiveAttemptsException("该账户已被锁定。", ex);

		/**
		 * 其他情况需要记录到缓存中备用
		 */
		storeRetryCache((Serializable) token.getPrincipal());

	}

	@Override
	public void onBeforeAuthentication(AuthenticationToken token) {
		if (null == token || !(token.getPrincipal() instanceof Serializable))
			return;
		Serializable principal = (Serializable) token.getPrincipal();
		int retryCount = getRetryCacheValue(principal);

		/**
		 * 需要报错禁用
		 */
		if (retryCount >= maxRetryToLock) {

			throw new CheckAttemptsAccountException(
					"检测到该账户已经尝试登陆" + retryCount + "次，已经超过最大密码重试次数" + maxRetryToLock + "。");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.shiro.realm.RealmListener#onAuthenticationSuccess(org.apache.
	 * shiro.authc.AuthenticationInfo, org.apache.shiro.session.Session)
	 */
	@Override
	public void onAuthenticationSuccess(AuthenticationInfo info, Session session) {
		/**
		 * 如果用户成功登陆，清楚缓存，重新计数
		 */
		DefaultAccount account = (DefaultAccount)info;
		User sessionUser = (User) account.getPrincipals().getPrimaryPrincipal();
		clearRetryCache(sessionUser.getYhm());
	}

	/**
	 * 
	 * @param principal
	 */
	protected void disableAccount(String principal) {
		accountStateService.disableAccount(principal);
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	protected void storeRetryCache(Serializable principal) {
		Integer retryCount = getRetryCache().get(principal, Integer.class);

		if (null == retryCount)
			retryCount = 1;
		else
			retryCount++;

		getRetryCache().put(principal, retryCount);

		/**
		 * 如果重试到指定次数，需要报错禁用
		 */
		if (retryCount == maxRetryToLock) {
			storeLockCache(principal);
			throw new ExcessiveAttemptsException("该账户已经尝试登陆" + retryCount + "次，处于安全考虑，暂时锁定该账户。");
		}
	}

	/**
	 * 
	 * @param principal
	 */
	protected void clearRetryCache(Serializable principal){
		getRetryCache().evict(principal);
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	protected int storeLockCache(Serializable principal) {
		Integer lockCount = getAcctLockCache().get(principal, Integer.class);

		if (null == lockCount)
			lockCount = 1;
		else
			lockCount++;

		getAcctLockCache().put(principal, lockCount);

		/**
		 * 如果锁定次数超过指定数量，停用该账户,并清楚相关缓存，后面不会再用到
		 */
		if (lockCount >= maxLockToDisable) {
			disableAccount((String) principal);
			
			clearLockCache(principal);
		}
		
		return lockCount;
	}

	/**
	 * 
	 * @param principal
	 */
	protected void clearLockCache(Serializable principal){
		getAcctLockCache().evict(principal);
	}
	
	/**
	 * 
	 * @param principal
	 * @return
	 */
	protected int getRetryCacheValue(Serializable principal) {
		Integer integer = getRetryCache().get(principal, Integer.class);
		return integer == null ? 0 : integer;
	}

}
