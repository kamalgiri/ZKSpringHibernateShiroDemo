
package com.kg.demo.service;

import java.util.List;

import com.kg.demo.model.UserDetail;

public interface UserService {
	boolean isAdminUserExist();

	int saveUser(UserDetail userDetail);

	boolean isUserAuthenticated(UserDetail userDetail);

	UserDetail getLoggedinUserInfo(String name);

	UserDetail getUserDetail(int id);

	int updateUser(UserDetail userDetail);

	List<UserDetail> getAllUsers();

	int deleteUser(UserDetail userDetail);
}
