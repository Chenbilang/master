package com.cbl.aa.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cbl.aa.dao.SpotDao;
import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Spot;

public class SpotDaoImpl extends BaseDaoImpl<Spot> implements SpotDao{ 


	@Override
	public List<String> getGroupBytime(DetachedCriteria detachedCriteria,
			String group) {
		detachedCriteria.setProjection(Projections.projectionList().add(Projections.groupProperty(group)));
		return (List<String>) this.getHibernateTemplate()
				.findByCriteria(detachedCriteria);
	}

	@Override
	public List<Spot> getOrderByOne(DetachedCriteria detachedCriteria,
			String group) {
		detachedCriteria.addOrder(Order.desc(group));
		
		return (List<Spot>) this.getHibernateTemplate()
				.findByCriteria(detachedCriteria,0,10);
	}
	
}
