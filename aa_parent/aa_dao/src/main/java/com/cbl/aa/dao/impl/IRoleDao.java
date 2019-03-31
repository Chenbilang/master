package com.cbl.aa.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cbl.aa.dao.RoleDao;
import com.cbl.aa.entity.Role;

/**
 * 角色数据访问类
 * @author Administrator
 *
 */
public class IRoleDao extends BaseDaoImpl<Role> implements RoleDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Role role1,Role role2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		if(role1!=null){
			if(null != role1.getName() && role1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", role1.getName(), MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

}
