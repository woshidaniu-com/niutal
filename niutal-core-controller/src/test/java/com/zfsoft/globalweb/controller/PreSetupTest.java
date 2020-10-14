package com.woshidaniu.globalweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.woshidaniu.common.log.User;
import com.woshidaniu.web.WebContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

public class PreSetupTest extends AbstractShiroTest {
	protected Subject subject;

	protected MockMvc mockMvc;

	public void preSetup() {

		subject = mock(Subject.class);

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		HttpSession session = new MockHttpSession();
	    session.setAttribute("WW_TRANS_I18N_LOCALE",new Locale("zh","CN"));
		when(request.getRemoteHost()).thenReturn("localhost");
		when(request.getSession()).thenReturn(session);
		when(subject.isAuthenticated()).thenReturn(true);

		User user = new User();
		user.setYhm("unit.test");
		user.setXm("unit.test");

		SimplePrincipalCollection prins = new SimplePrincipalCollection();
		prins.add(user, "");
		when(subject.getPrincipal()).thenReturn(user);
		when(subject.getPrincipals()).thenReturn(prins);
		WebContext.bindRequest(request);
		WebContext.bindResponse(response);

		setSubject(subject);

	}

	/*public Locale stringToLocale(String s) {
		StringTokenizer tempStringTokenizer = new StringTokenizer(s, ",");
		if (tempStringTokenizer.hasMoreTokens()) {
			String l = (String) tempStringTokenizer.nextElement();
		}
		if (tempStringTokenizer.hasMoreTokens()) {
			String c = (String) tempStringTokenizer.nextElement();
		}
		return new Locale(l, c);
	}*/
}
