package com.cbl.aa.dao;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;


/**
 * 员工数据访问接口
 * @author Administrator
 *
 */
public interface EmpDao extends BaseDao<Emp>{

	/**
	 * 用户登陆
	 * @param username
	 * @param pwd
	 * @return
	 */
	Emp findByUsernameAndPwd(String username, String pwd);
	
	void updatePwd(Long uuid, String newPwd);

	List<Menu> getMenusByEmpuuid(Long uuid);
}

