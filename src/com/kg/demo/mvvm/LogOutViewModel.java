
package com.kg.demo.mvvm;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import com.kg.demo.model.UserDetail;

public class LogOutViewModel implements Serializable {

	private static final long serialVersionUID = -7175016273916206044L;
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Init
	public void init() {
		Subject sub = SecurityUtils.getSubject();
		Session session = sub.getSession();
		setUserName(((UserDetail) session.getAttribute("loggedUser")).getName());
	} 

	@Command
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}

		Executions.sendRedirect("/index.zul");
	} 
} 
