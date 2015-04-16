package com.kg.demo.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.kg.demo.model.UserDetail;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginController extends SelectorComposer<Component> implements
		Initiator {

	private static final long serialVersionUID = 3839424661935574447L;

	@Wire
	private Textbox name, passWord;

	@Wire
	private Button login;
	@Wire
	private Label error;


	@Listen("onClick= #login")
	public void authenticate() {

		String userName = name.getValue();
		String password = passWord.getValue();

		UserDetail userDetail = new UserDetail();
		userDetail.setName(userName);
		userDetail.setPassword(password);

	

		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,
				password, true);
		try {
			token.setRememberMe(true);
			user.login(token);
			Executions.sendRedirect("/layout/main.zul");
		} catch (AuthenticationException ae) {
			error.setValue("Authentication Failed" + ae);
		}

	}

	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		Selectors.wireVariables(page, this,
				Selectors.newVariableResolvers(getClass(), null));
	}

}
