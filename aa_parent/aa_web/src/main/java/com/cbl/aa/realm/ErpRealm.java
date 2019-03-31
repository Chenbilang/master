package com.cbl.aa.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cbl.aa.biz.EmpBiz;
import com.cbl.aa.entity.Emp;
import com.cbl.aa.entity.Menu;


public class ErpRealm extends AuthorizingRealm {
	
	private EmpBiz empBiz;

	public void setEmpBiz(EmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行了授权的方法...");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		/*info.addStringPermission("部门");
		info.addStringPermission("采购订单的查询");
		info.addStringPermission("采购订单的审核");*/
		//uuid=?怎么来
		Emp emp = (Emp)principals.getPrimaryPrincipal();
		
		//获取当前登陆用户的菜单权限
		List<Menu> menuList = empBiz.getMenusByEmpuuid(emp.getUuid());
		
		//加入授权
		for(Menu m : menuList){
			//这里我们使用menuname来做授权里的key值，那么在配置授权访问的url=perms[菜单名称]
			info.addStringPermission(m.getMenuname());
		}
		return info;
	}

	/**
	 * 认证
	 * @return null:认证失败, AuthenticationInfo实现类，认证成功
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行了认证的方法...");
		//通过令牌得到用户名和密码?UsernamePasswordToken是AuthenticationToken的实现类
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		//得到密码
		String pwd = new String(upt.getPassword());
		//调用登录查询
		Emp emp = empBiz.findByUsernameAndPwd(upt.getUsername(),pwd);
		if(null != emp){
			//构造参数1： 主角=登陆用户
			//参数2：授权码：密码
			//参数3：realm的名称
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp,pwd,getName());
			return info;
		}
		return null;
	}

}
