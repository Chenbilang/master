package com.cbl.aa.biz.impl;

import com.cbl.aa.biz.MenuBiz;
import com.cbl.aa.dao.MenuDao;
import com.cbl.aa.entity.Menu;

/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class IMenuBiz extends IBaseBiz<Menu> implements MenuBiz {

	private MenuDao menuDao;
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}
	
}
