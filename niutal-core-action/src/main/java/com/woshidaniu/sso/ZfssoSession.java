package com.woshidaniu.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.niuca.tp.cas.client.ZfssoBean;
import com.woshidaniu.niuca.tp.cas.client.ZfssoSetsessionService;

public class ZfssoSession implements ZfssoSetsessionService {
	public static final String USERTYPE_STUDENT = "student";
	public static final String USERTYPE_TEACHER = "teacher";

	public Boolean chkUserSession(ZfssoBean zfssobean) {
		Boolean res = Boolean.valueOf(false);

		User user = geSessiontUser(zfssobean);
		if ((user != null) && (user.getYhm() != null)
				&& (user.getYhm().equalsIgnoreCase(zfssobean.getYhm()))) {
			res = Boolean.valueOf(true);
		}
		return res;
	}

	public Boolean setUserSession(ZfssoBean zfssobean) {
		Boolean res = Boolean.valueOf(false);
		HttpServletRequest request = zfssobean.getRequest();

		User user = getUserInfo(zfssobean.getYhm());
		if ((user != null) && (!StringUtils.isEmpty(user.getYhm()))) {
			setSession(user, request);
			res = Boolean.valueOf(true);
		}
		return res;
	}

	private User geSessiontUser(ZfssoBean zfssobean) {
		HttpServletRequest request = zfssobean.getRequest();
		User user = request.getSession().getAttribute("user") != null ? (User) request
				.getSession().getAttribute("user")
				: null;
		return user;
	}

	private User getUserInfo(String yhm) {
		ILoginService loginService = (ILoginService) ServiceFactory
				.getService("loginService");
		LoginModel loginModel = new LoginModel();
		loginModel.setYhm(yhm);
		User user = loginService.cxYhxx(loginModel);
		return user;
	}

	private void setSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();

		session.setAttribute("user", user);
		session.setAttribute("userType", user.getYhlx());
	}
}