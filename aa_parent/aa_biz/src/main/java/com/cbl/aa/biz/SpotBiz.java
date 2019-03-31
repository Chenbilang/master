package com.cbl.aa.biz;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;
import com.cbl.aa.entity.Spot;
import com.cbl.aa.entity.Tree;


/**
 * 景点业务逻辑接口
 * @author Administrator
 *
 */
public interface SpotBiz extends BaseBiz<Spot>{

	List<String> getGroupBytime(DetachedCriteria detachedCriteria, String group);
	List<Spot> getOrderByOne(DetachedCriteria detachedCriteria, String group);
}

