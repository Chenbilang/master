package com.cbl.aa.action;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cbl.aa.biz.RoleBiz;
import com.cbl.aa.entity.Role;
import com.cbl.aa.entity.Tree;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private RoleBiz roleBiz;

	public void setRoleBiz(RoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}

	/**
	 * 获取角色菜单权限
	 */
	public void readRoleMenus(){
		List<Tree> menus = roleBiz.readRoleMenus(getId());
		write(JSON.toJSONString(menus));
	}
	
	private String checkedStr;//勾选中菜单的ID字符串，以逗号分割
	public String getCheckedStr() {
		return checkedStr;
	}
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
	
	/**
	 * 更新角色权限
	 */
	public void updateRoleMenus(){
		try {
			System.out.println("aciton中 id="+getId()+",checkedStr="+checkedStr);
			roleBiz.updateRoleMenus(getId(), checkedStr);
			ajaxReturn(true, "更新角色菜单成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新角色菜单失败");
			e.printStackTrace();
		}
	}
}
