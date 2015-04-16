
package com.kg.demo.mvvm;

import java.io.Serializable;
import java.util.List;

import org.zkoss.bind.Form;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.kg.demo.model.Role;
import com.kg.demo.model.UserDetail;
import com.kg.demo.service.RoleService;
import com.kg.demo.service.UserService;
import com.kg.demo.util.Encrypt;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserViewModel implements Serializable {

	@WireVariable
	private UserService userService;

	@WireVariable
	private RoleService roleService;

	private static final long serialVersionUID = -2092265797101425150L;

	private boolean isValid;

	private boolean mode;

	private ListModelList<UserDetail> userDetailListModel;
	private UserDetail userDetail;

	private List<Role> roleList;
	private ListModelList<Role> rolesModel;

	private String errPassword;
	private String errName;

	public String getErrPassword() {
		return errPassword;
	}

	public void setErrPassword(String errPassword) {
		this.errPassword = errPassword;
	}

	public ListModelList<UserDetail> getUserDetailListModel() {
		return userDetailListModel;
	}

	public void setUserDetailListModel(
			ListModelList<UserDetail> userDetailListModel) {
		this.userDetailListModel = userDetailListModel;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	@Init
	public void init() {

		
		roleList = roleService.getAllRoles();
		rolesModel = new ListModelList<Role>(roleList);

		List<UserDetail> userDetailList = userService.getAllUsers();
		userDetailListModel = new ListModelList<UserDetail>(userDetailList);

		userDetail = new UserDetail();
	}
	@Command
	@NotifyChange({ "errName" })
	public void validateUserForm(@BindingParam("parameter") String parameter) {
		System.out.println(parameter);
		if (parameter.isEmpty()) {
			errName = "Required Field";
		} else {
			errName = "";
		}
	} 

	@Command
	@NotifyChange({ "userDetail", "errPassword" })
	public void saveUser() {

		if (!isValid) {
			System.out.println(errPassword);
			return;
		}
		userDetail.setPassword(new Encrypt().simpleHash(userDetail.getName()));
		int status = userService.saveUser(userDetail);

		if (status == 1) {
			userDetailListModel.add(userDetail);

			Clients.showNotification("User is Saved");

		} else {
			Clients.showNotification("Error saving user");
		}

		userDetail = new UserDetail();

	} 

	@Command
	@NotifyChange({ "userDetail", "mode" })
	public void editUser(@BindingParam("userDetail") UserDetail userDetail) {
		this.userDetail = userDetail;
		mode = true;

	} 

	@Command
	@NotifyChange({ "userDetail" })
	public void deleteUser(@BindingParam("userDetail") UserDetail userDetail) {
		this.userDetail = userDetail;

		int status = userService.deleteUser(userDetail);

		if (status == 1) {

			userDetailListModel.remove(userDetail);
			Clients.showNotification("User is Deleted Successfully");

		} else {
			Clients.showNotification("Error Deleting User");
		}

	} 


	public Validator getUserDetailValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {
				Form fx = (Form) ctx.getProperty().getValue();
				String password = (String) fx.getField("password");

				if (Strings.isBlank(password)) {

					setErrPassword("Password should not be empty");
					Clients.showNotification("password is blank, nothing to do ?");
					isValid = false;
				} else {
					isValid = true;
				}
			}
		};
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public ListModelList<Role> getRolesModel() {
		return rolesModel;
	}

	public void setRolesModel(ListModelList<Role> rolesModel) {
		this.rolesModel = rolesModel;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public boolean isMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

}
