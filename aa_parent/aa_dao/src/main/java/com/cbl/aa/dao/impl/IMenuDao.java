package com.cbl.aa.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cbl.aa.dao.MenuDao;
import com.cbl.aa.entity.Menu;

/**
 * 菜单数据访问类
 * @author Administrator
 *
 */
public class IMenuDao extends BaseDaoImpl<Menu> implements MenuDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Menu menu1,Menu menu2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Menu.class);
		return dc;
	}

}
