package com.cbl.aa.action;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.cbl.aa.biz.EmpBiz;
import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;
import com.cbl.aa.entity.Tree;


/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private EmpBiz empBiz;

	public void setEmpBiz(EmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}
	
	private String oldPwd;//旧密码
	private String newPwd;//新密码

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * 修改密码调用的方法
	 */
	public void updatePwd(){
		Emp loginUser = getLoginUser();
		//session是否会超时，用户是否登陆过了
		if(null == loginUser){
			ajaxReturn(false, "亲，您还没有登陆");
			return;
		}
		try {
			empBiz.updatePwd(loginUser.getUuid(), oldPwd, newPwd);
			ajaxReturn(true, "修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改密码失败");
		}
	}
	
	/**
	 * 重置密码调用的方法
	 */
	public void updatePwd_reset(){
		
		try {
			empBiz.updatePwd_reset(getId(), newPwd);
			ajaxReturn(true, "重置密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "重置密码失败");
		}
	}
	public Date birthdayTemp;

	public void setBirthdayTemp(Date birthdayTemp) {
		this.birthdayTemp = birthdayTemp;
	}

	private String checkedStr;//勾选中角色的ID字符串，以逗号分割
	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	/**
	 * 获取用户角色
	 */
	public void readEmpRoles(){
		List<Tree> roleList = empBiz.readEmpRoles(getId());
		write(JSON.toJSONString(roleList));
	}
	
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(), checkedStr);
			ajaxReturn(true, "更新用户角色成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新用户角色失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取用户的菜单权限
	 */
	public void getMenusByEmpuuid(){
		if(null != getLoginUser()){
			List<Menu> menuList = empBiz.getMenusByEmpuuid(getLoginUser().getUuid());
			write(JSON.toJSONString(menuList));
		}
	}
	public DetachedCriteria getDetachedCriteria(Emp emp,DetachedCriteria detachedCriteria){
		if(emp!=null){
			if(null != emp.getUsername() && emp.getUsername().trim().length()>0){
				detachedCriteria.add(Restrictions.like("username", emp.getUsername(), MatchMode.ANYWHERE));
			}
			if(null != emp.getPwd() && emp.getPwd().trim().length()>0){
				detachedCriteria.add(Restrictions.like("pwd", emp.getPwd(), MatchMode.ANYWHERE));
			}
			if(null != emp.getName() && emp.getName().trim().length()>0){
				detachedCriteria.add(Restrictions.like("name", emp.getName(), MatchMode.ANYWHERE));
			}
			if( null != emp.getGender()){
				detachedCriteria.add(Restrictions.eq("gender", emp.getGender()));
			}
			

		}
		
		return detachedCriteria;
	}
}
