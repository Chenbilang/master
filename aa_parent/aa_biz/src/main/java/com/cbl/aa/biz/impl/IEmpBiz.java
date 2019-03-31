package com.cbl.aa.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.cbl.aa.biz.EmpBiz;
import com.cbl.aa.dao.EmpDao;
import com.cbl.aa.dao.MenuDao;
import com.cbl.aa.dao.RoleDao;
import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;
import com.cbl.aa.entity.Role;
import com.cbl.aa.entity.Tree;
import com.cbl.aa.exception.ErpException;

/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
@Transactional
public class IEmpBiz extends IBaseBiz<Emp> implements EmpBiz {

	private int hashIterations = 2;
	
	private EmpDao empDao;
	
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}
	private RoleDao roleDao;
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private MenuDao menuDao;
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	private Jedis jedis;
	

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * 用户登陆
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username, String pwd){
		//查询前先加密
		pwd = encrypt(pwd, username);
		System.out.println(pwd);
		return empDao.findByUsernameAndPwd(username, pwd);
	}

	/**
	 * 修改密码
	 */
	public void updatePwd(Long uuid, String oldPwd, String newPwd) {
		//取出员工信息
		Emp emp = empDao.findById(uuid);
		//加密旧密码
		String encrypted = encrypt(oldPwd, emp.getUsername());
		//旧密码是否正确的匹配
		if(!encrypted.equals(emp.getPwd())){
			//抛出 自定义异常
			throw new ErpException("旧密码不正确");
		}		
		empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
	}
	
	/**
	 * 新增员工
	 */
	public void add(Emp emp){
		//String pwd = emp.getPwd();
		// source: 原密码
		// salt:   盐 =》扰乱码
		// hashIterations: 散列次数，加密次数
		//Md5Hash md5 = new Md5Hash(pwd, emp.getUsername(), hashIterations);
		//取出加密后的密码
		//设置初始密码
		
		String newPwd = encrypt(emp.getUsername(), emp.getUsername());
		
		//设置成加密后的密码
		emp.setPwd(newPwd);
		//保存到数据库中
		super.add(emp);
	}
	
	/**
	 * 重置密码
	 */
	public void updatePwd_reset(Long uuid, String newPwd){
		//取出员工信息
		Emp emp = empDao.findById(uuid);
		empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
	}
	
	/**
	 * 获取用户角色
	 * @param uuid
	 * @return
	 */
	public List<Tree> readEmpRoles(Long uuid){
		List<Tree> treeList = new ArrayList<Tree>();
		//获取用户信息
		Emp emp = empDao.findById(uuid);
		//获取用户下的角色列表
		List<Role> empRoles = emp.getRoles();
		//获取所有角色列表
		List<Role> rolesList = roleDao.findAll();
		Tree t1 = null;
		for(Role role : rolesList){
			t1 = new Tree();
			//转换成string类型
			t1.setId(String.valueOf(role.getUuid()));
			t1.setText(role.getName());
			//判断是否需要勾选中,用户是否拥有这个角色
			if(empRoles.contains(role)){
				t1.setChecked(true);
			}
			treeList.add(t1);
		}
		return treeList;
	}
	
	/**
	 * 更新用户的角色
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateEmpRoles(Long uuid, String checkedStr){
		//获取用户信息
		Emp emp = empDao.findById(uuid);
		//清空该用户下的所有角色
		emp.setRoles(new ArrayList<Role>());
		
		String[] ids = checkedStr.split(",");
		Role role = null;
		for(String id : ids){
			role = roleDao.findById(Long.valueOf(id));
			//设置用户的角色
			emp.getRoles().add(role);
		}
	}

	@Override
	public List<Menu> getMenusByEmpuuid(Long uuid) {
		// 1. 尝试着从缓存里取出menuList
//		String menuListJson = jedis.get("menuList_" + uuid);
	  
		List<Menu> menuList = empDao.getMenusByEmpuuid(uuid);
//		if (null != menuListJson) {
//			System.out.println("从缓存中取出menuList");
//			// 3. 缓存中已经存在，取出后再转成List对象
//			menuList = JSON.parseArray(menuListJson, Menu.class);
//		} else {
//			System.out.println("从数据库查询出menuList");
//			// 第一次查询
//			// 2. Jedis不支持对象的存储，支持字符串的存储，所以，当第一次存入缓存的时候，转成JSON字符串存进去
//			menuList = empDao.getMenusByEmpuuid(uuid);
//			jedis.set("menuList_" + uuid, JSON.toJSONString(menuList));
//		}
		return menuList;
	}

	@Override
	public Menu readMenusByEmpuuid(Long uuid) {
		//获取所有的菜单，做模板用的
		
		Menu root = menuDao.findById("0");
		
		//用户下的菜单集合
		List<Menu> empMenus = empDao.getMenusByEmpuuid(uuid);
		//根菜单，用于匹配模板，返回数据
		Menu menu = cloneMenu(root);
		
		//循环匹配模板
		//一级菜单
		Menu _m1 = null;
		Menu _m2 = null;
		for(Menu m1 : root.getMenus()){
			//先克隆是防止可能用户没有二级菜单，该一级菜单也被加入menu
			//所以需要有二级菜单使_m1.getMenus()不为空，才能加入_m1
			_m1 = cloneMenu(m1);
			//二级菜单循环
			for(Menu m2 : m1.getMenus()){
				//用户包含有这个菜单
				if(empMenus.contains(m2)){
					//复制菜单
					_m2 = cloneMenu(m2);
					//加入到上级菜单下
					_m1.getMenus().add(_m2);
				}
			}
			//有二级菜单我们才加进来
			if(_m1.getMenus().size() > 0){
				//把一级菜单加入到根菜单下
				menu.getMenus().add(_m1);
			}
		}
		return menu;
	}
	
	/**
	 * 复制menu
	 * @param src
	 * @return
	 */
	private Menu cloneMenu(Menu src){
		Menu menu = new Menu();
		menu.setIcon(src.getIcon());
		menu.setMenuid(src.getMenuid());
		menu.setMenuname(src.getMenuname());
		menu.setUrl(src.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}
	/**
	 * 加密
	 * @param source
	 * @param salt
	 * @return
	 */
	private String encrypt(String source, String salt){
		Md5Hash md5 = new Md5Hash(source, salt, hashIterations);
		return md5.toString();
	}

}
