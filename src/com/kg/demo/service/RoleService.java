package com.kg.demo.service;

import java.util.List;

import com.kg.demo.model.Role;

public interface RoleService {
	void initRoles();

	List<Role> getAllRoles();
}
