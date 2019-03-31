package com.cbl.aa.action;

import com.alibaba.fastjson.JSON;
import com.cbl.aa.biz.EmpBiz;
import com.cbl.aa.biz.MenuBiz;
import com.cbl.aa.entity.Menu;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private MenuBiz menuBiz;

	public void setMenuBiz(MenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	private EmpBiz empBiz;
	
	
	public void setEmpBiz(EmpBiz empBiz) {
		this.empBiz = empBiz;
	}


	/**
	 * 获取菜单数据
	 */
	public void getMenuTree(){
		//通过获取主菜单，自关联就会带其下所有的菜单
//		Menu menu = menuBiz.get("0");
		Menu menu = empBiz.readMenusByEmpuuid(getLoginUser().getUuid());
		write(JSON.toJSONString(menu));
	}

}
