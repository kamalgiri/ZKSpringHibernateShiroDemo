
package com.kg.demo.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.kg.demo.model.Role;
import com.kg.demo.model.UserDetail;
import com.kg.demo.service.RoleService;
import com.kg.demo.service.UserService;
import com.kg.demo.util.Encrypt;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProfileController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 2953810053429303634L;

	@Wire
	Combobox role;

	@Wire
	Textbox name, passWord;

	@Wire
	Button save, update;

	@Wire
	Label responseMsg;

	
	@WireVariable
	RoleService roleService;

	
	@WireVariable
	UserService userService;

	@SuppressWarnings("rawtypes")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		List<Role> roleList = roleService.getAllRoles();
		ListModelList<Role> rolesModel = new ListModelList<Role>(roleList);

		
		if (role != null) {
			role.setItemRenderer(new ComboitemRenderer() {

				@Override
				public void render(Comboitem item, Object data, int index)
						throws Exception {
					item.setLabel(((Role) data).getName());
					item.setDescription(((Role) data).getDescription());

				}

			});
			role.setModel(rolesModel);
		} 

	} 

	
	@Listen("onClick= #save")
	public void save() {

		String roleName = role.getValue();
		String userName = name.getValue();
		String password = passWord.getValue();

		Role role = new Role();
		role.setName(roleName);

		UserDetail userDetail = new UserDetail();
		userDetail.setName(userName);
		userDetail.setPassword(new Encrypt().simpleHash(password));
		userDetail.setRole(role);

		userService.saveUser(userDetail);

		Executions.sendRedirect("/login.zul");

	} 

	
	@Listen("onChange= #passWord")
	public void clear() {
		responseMsg.setValue("");
	}

	
	@Listen("onClick= #update")
	public void update() {
		
		Subject sub = SecurityUtils.getSubject();
		Session ses = sub.getSession();
		UserDetail userDetail = (UserDetail) ses.getAttribute("loggedUser");
		
		String password = passWord.getValue();

		userDetail.setPassword(new Encrypt().simpleHash(password));

		int updateStatus = userService.updateUser(userDetail);

		System.out.println("User Updated Successfully" + updateStatus);
		if (updateStatus == 1) {
			responseMsg.setValue("Password Changed");
		} else {
			responseMsg.setValue("Error Changing password");
		}

	} 
} 
