package com.kg.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kg.demo.dao.GenericDao;
import com.kg.demo.model.Role;
import com.kg.demo.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private GenericDao commonDao;

	@Override
	public void initRoles() {
		Role role1 = new Role();
		role1.setName("admin");
		role1.setDescription("Administrator Role");

		Role role2 = new Role();
		role2.setName("user");
		role2.setDescription("General User Role");

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(role1);
		roleList.add(role2);

		commonDao.saveAll(roleList);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		return (List<Role>) commonDao.getAll(Role.class);
	}

}
