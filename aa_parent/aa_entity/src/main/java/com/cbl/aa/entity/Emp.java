package com.cbl.aa.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 员工实体类
 * @author Administrator *
 */
public class Emp {	
	private Long uuid;//编号
	private String username;//登陆名
	//不转换json字符串
	@JSONField(serialize=false)
	private String pwd;//登陆密码
	private String name;//真实姓名
	private Long gender;//性别

	@JSONField(serialize=false)
	private List<Role> roles;//用户下所拥有的角色集合
	
	public Long getUuid() {		
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {		
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {		
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGender() {		
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	

}
