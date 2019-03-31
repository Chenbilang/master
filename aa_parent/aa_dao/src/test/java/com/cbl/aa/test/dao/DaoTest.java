package com.cbl.aa.test.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cbl.aa.dao.SpotDao;
import com.cbl.aa.entity.Spot;

public class DaoTest {
	
	@Test
	public void testDep(){
		ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext_dao.xml","classpath:applicationContext_datasource.xml"});
		SpotDao spotDao=(SpotDao) ac.getBean("spotDao");
		Spot spot=spotDao.findById(1L);
		System.out.println(spot);
		DetachedCriteria detachedCriteria=DetachedCriteria
				.forClass(spot.getClass());
		Integer findCount = spotDao.findCount(detachedCriteria);
		System.out.println(findCount);
	}
	
}
