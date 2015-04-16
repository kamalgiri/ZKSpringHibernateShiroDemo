package com.kg.demo.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kg.demo.dao.GenericDao;
import com.kg.demo.util.SessionFactoryUtil;

@Component("commonDao")
public class GenericDaoImpl implements GenericDao {

	@Autowired
	private SessionFactoryUtil sessionFactoryUtil;

	@Override
	public Session getCurrentSession() {
		return sessionFactoryUtil.getSessionFactory().openSession();
	}

	@Override
	public int save(Object obj) {
		int status = 0;

		Session session = getCurrentSession();
		session.beginTransaction();
		try {
			session.save(obj);
			session.getTransaction().commit();
			session.flush();
			status = 1;
		} catch (Exception e) {
			session.getTransaction().rollback();
			status = 0;
		} finally {
			session.close();
		}
		return status;
	}

	@Override
	public int saveAll(List<? extends Object> objList) {
		int status = 0;

		Session session = getCurrentSession();
		session.beginTransaction();
		try {
			for (Object obj : objList) {
				session.save(obj);
			}
			session.getTransaction().commit();
			session.flush();
			status = 1;
		} catch (Exception e) {
			session.getTransaction().rollback();
			status = 0;
		} finally {

			session.close();

		}
		return status;
	}

	@Override
	public int delete(Object obj) {
		int status = 0;
		Session session = getCurrentSession();
		session.beginTransaction();
		try {
			session.delete(obj);
			session.getTransaction().commit();
			session.flush();
			status = 1;
		} catch (Exception e) {
			session.getTransaction().rollback();
			status = 0;
		} finally {
			session.close();
		}
		return status;
	}

	@Override
	public int update(Object obj) {
		int status = 0;
		Session session = getCurrentSession();
		session.beginTransaction();
		try {
			session.update(obj);
			session.getTransaction().commit();
			session.flush();
			status = 1;
		} catch (Exception e) {
			session.getTransaction().rollback();
			status = 0;
		} finally {
			session.close();
		}
		return status;
	}

	@Override
	public List<?> getAll(Class<?> clz) {
		Session session = getCurrentSession();
		session.beginTransaction();
		Criteria criteria = (Criteria) session.createCriteria(clz);
		List<?> objList = criteria.list();
		session.close();
		return objList;
	}
}
