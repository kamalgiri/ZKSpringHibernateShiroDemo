package com.kg.demo.dao;

import java.util.List;

import org.hibernate.Session;

public interface GenericDao {
	Session getCurrentSession();

	int save(Object obj);

	int saveAll(List<? extends Object> objList);

	int delete(Object obj);

	int update(Object obj);

	List<? extends Object> getAll(Class<?> clz);
}
