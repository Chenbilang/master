package com.cbl.aa.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cbl.aa.dao.EmpDao;
import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;


/**
 * 员工数据访问类
 * @author Administrator
 *
 */
public class IEmpDao extends BaseDaoImpl<Emp> implements EmpDao {
	
	
	
	/**
	 * 用户登陆
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username, String pwd){
		String hql = "from Emp where username=? and pwd=?";		
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql, username, pwd);
		//能够匹配上，则返回第一个元素
		if(list.size() > 0){
			return list.get(0);
		}
		//如果登陆名或密码不正确
		return null;
	}

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		if(emp1!=null){
			if(null != emp1.getUsername() && emp1.getUsername().trim().length()>0){
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getPwd() && emp1.getPwd().trim().length()>0){
				dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getName() && emp1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
			}
		

		}
	
		
		return dc;
	}
	
	
	/**
	 * 修改密码
	 */
	public void updatePwd(Long uuid, String newPwd) {
		String hql = "update Emp set pwd=? where uuid=?";
		this.getHibernateTemplate().bulkUpdate(hql, newPwd, uuid);
	}

	/**
	 * 查询用户下的菜单权限
	 * @param uuid
	 * @return
	 */
	public List<Menu> getMenusByEmpuuid(Long uuid){
		String hql = "select m from Emp e join e.roles r join r.menus m where e.uuid=?";
		return (List<Menu>) this.getHibernateTemplate().find(hql, uuid);
	}
}
