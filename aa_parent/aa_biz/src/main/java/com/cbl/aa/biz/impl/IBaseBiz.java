package com.cbl.aa.biz.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.cbl.aa.biz.BaseBiz;
import com.cbl.aa.dao.BaseDao;
import com.cbl.aa.dao.EmpDao;


@Transactional
public class IBaseBiz<T> implements BaseBiz<T> {

	/** 数据访问注入*/
	private BaseDao<T> baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<T> getList() {
		return baseDao.findAll();
	}

	@Override
	public void add(T t) {
		baseDao.save(t);
	}

	@Override
	public void delete(Long uuid) {
		T t=baseDao.findById(uuid);
		baseDao.delete(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T get(Long uuid) {
		return baseDao.findById(uuid);
	}

	@Override
	public Integer getList(DetachedCriteria detachedCriteria, Integer page,
			Integer rows, List<T> list) {
	
		Integer totalCount=baseDao.findCount(detachedCriteria);
		
		Integer begin=rows*(page-1);
		
		list.addAll(baseDao.findByPage(detachedCriteria,begin,rows));
	
		return totalCount;
	}

	@Override
	public T get(String uuid) {
		return baseDao.findById(uuid);
    }
	

	/**
	 * 获取员工名称
	 * @param uuid 员工编号
	 * @param empNameMap 缓存员工编号与员工的名称 
	 * @return 返回员工名称
	 */
	public String getEmpName(Long uuid, Map<Long, String> empNameMap, EmpDao empDao){
		if(null == uuid){
			return null;
		}
		//从缓存中根据员工编号取出员工名称
		String empName = empNameMap.get(uuid);
		if(null == empName){
			//如果没有找员工的名称，则进行数据库查询
			empName = empDao.findById(uuid).getName();
			//存入缓存中
			empNameMap.put(uuid, empName);
		}
		return empName;
	}
	



	@Override
	public List<Object[]> getGroupOne(DetachedCriteria detachedCriteria, String property) {
		// TODO Auto-generated method stub
		return (List<Object[]>) baseDao.findGroudByOne(detachedCriteria, property);
		
	}}
