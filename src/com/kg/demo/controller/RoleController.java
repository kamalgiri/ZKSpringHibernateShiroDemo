package com.kg.demo.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.kg.demo.service.RoleService;
import com.kg.demo.service.UserService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RoleController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -1084376710248301851L;

	// private RoleService roleService = (RoleService) SpringUtil
	// .getBean("roleService");
	// private UserService userService = (UserService) SpringUtil
	// .getBean("userService");

	@WireVariable(value="roleService")
	RoleService roleService;

	@WireVariable(value="userService")
	UserService userService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// roleService.initRoles();

		if (userService.isAdminUserExist()) {
			Executions.sendRedirect("/login.zul");
		} else {
			Executions.sendRedirect("/profile.zul");
		}

	}
}
