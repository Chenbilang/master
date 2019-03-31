package com.cbl.aa.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cbl.aa.entity.Spot;

public interface SpotDao extends BaseDao<Spot>{

	List<String> getGroupBytime(DetachedCriteria detachedCriteria, String group);
    List<Spot> getOrderByOne(DetachedCriteria detachedCriteria, String group);
}
