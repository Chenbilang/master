package com.cbl.aa.biz.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cbl.aa.biz.SpotBiz;
import com.cbl.aa.dao.SpotDao;
import com.cbl.aa.entity.Spot;

public class ISpotBiz extends IBaseBiz<Spot> implements SpotBiz{

	private SpotDao spotDao;

	public void setSpotDao(SpotDao spotDao) {
		this.spotDao = spotDao;
		super.setBaseDao(this.spotDao);
	}

	@Override
	public List<String> getGroupBytime(DetachedCriteria detachedCriteria, String group) {
		// TODO Auto-generated method stub
		return (List<String>) spotDao.getGroupBytime(detachedCriteria, group);
	}

	@Override
	public List<Spot> getOrderByOne(DetachedCriteria detachedCriteria,
			String group) {
		return spotDao.getOrderByOne(detachedCriteria, group);
	}
	
}
