package com.cbl.aa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 通用的DAO的接口
 * @author jt
 *
 */
public interface BaseDao<T> {

	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
	public T findById(Serializable id);
	
	// 查询所有
	public List<T> findAll();
	
	// 统计个数的方法
	public Integer findCount(DetachedCriteria detachedCriteria);
	
	// 分页查询的方法:
	public List<T> findByPage(DetachedCriteria detachedCriteria,Integer begin,Integer pageSize);
	public List<T> getList(T t);
	public List<Object[]> findGroudByOne(DetachedCriteria detachedCriteria,String property);
}
