package com.kg.demo.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.zkoss.zkplus.spring.SpringUtil;

import com.kg.demo.model.Role;
import com.kg.demo.model.UserDetail;
import com.kg.demo.service.UserService;

public class CustomJdbcRealm extends AuthorizingRealm {

	public CustomJdbcRealm() {
		setName("CustomJdbcRealm");
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) {
		UserService userService = (UserService) SpringUtil
				.getBean("userService");

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();

		UserDetail user = userService.getLoggedinUserInfo(userName);

		if (user == null) {

			throw new UnknownAccountException("No account found for user ["
					+ userName + "]");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user.getId(), user.getPassword(), getName());
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("loggedUser", user);
		return info;
	}

	@SuppressWarnings("unused")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg) {
		UserService userService = (UserService) SpringUtil
				.getBean("userService");
		Integer userId = (Integer) arg.fromRealm(getName()).iterator().next();

		UserDetail user = userService.getUserDetail(userId);
		Role userRole = (Role) user.getRole();

		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addRole(userRole.getName());
			return info;
		} else {
			return null;
		}

	}
}
