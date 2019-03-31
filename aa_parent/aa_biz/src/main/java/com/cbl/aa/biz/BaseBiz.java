package com.cbl.aa.biz;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface BaseBiz<T> {

	/**
	 * 查询所有部门列表
	 */
	List<T> getList();

	/**
	 * 新增部门
	 * @param dep
	 */
	void add(T t);

	void delete(Long uuid);

	void update(T t);

	T get(Long uuid);

	

	Integer getList(DetachedCriteria detachedCriteria, Integer page,
			Integer rows, List<T> list);

	T get(String uuid);
	List<Object[]> getGroupOne(DetachedCriteria detachedCriteria,String property);	
}
