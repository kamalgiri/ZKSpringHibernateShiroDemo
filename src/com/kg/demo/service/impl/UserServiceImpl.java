package com.kg.demo.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kg.demo.dao.GenericDao;
import com.kg.demo.model.Role;
import com.kg.demo.model.UserDetail;
import com.kg.demo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	GenericDao commonDao;

	@SuppressWarnings("unchecked")
	@Override
	public boolean isAdminUserExist() {
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(UserDetail.class);
		List<UserDetail> userDetails = criteria.list();
		session.close();
		for (UserDetail user : userDetails) {
			if (user.getRole().getName().equals("admin")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int saveUser(UserDetail userDetail) {
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("name", userDetail.getRole().getName()));
		Role role = (Role) criteria.uniqueResult();
		userDetail.setRole(role);
		session.close();
		return commonDao.save(userDetail);

	}

	@Override
	public boolean isUserAuthenticated(UserDetail userDetail) {
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(UserDetail.class);
		criteria.add(Restrictions.eq("name", userDetail.getName()));
		criteria.add(Restrictions.eq("password", userDetail.getPassword()));
		UserDetail ud = (UserDetail) criteria.uniqueResult();
		session.getTransaction().commit();
		session.flush();
		session.close();
		if (ud != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserDetail getLoggedinUserInfo(String name) {
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(UserDetail.class);
		criteria.add(Restrictions.eq("name", name));
		UserDetail ud = (UserDetail) criteria.uniqueResult();
		session.getTransaction().commit();
		session.flush();
		session.close();
		return ud;
	}

	@Override
	public UserDetail getUserDetail(int id) {
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		UserDetail ud = (UserDetail) session.get(UserDetail.class, id);
		return ud;
	}

	@Override
	public int updateUser(UserDetail userDetail) {
		int status = 0;
		Session session = commonDao.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(UserDetail.class);
		criteria.add(Restrictions.eq("name", userDetail.getName()));
		UserDetail userDetailDB = (UserDetail) criteria.uniqueResult();
		session.close();
		userDetailDB.setPassword(userDetail.getPassword());
		status = commonDao.update(userDetailDB);
		return status;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetail> getAllUsers() {
		return (List<UserDetail>) commonDao.getAll(UserDetail.class);
	}

	@Override
	public int deleteUser(UserDetail userDetail) {
		int status = commonDao.delete(userDetail);
		return status;
	}

}
