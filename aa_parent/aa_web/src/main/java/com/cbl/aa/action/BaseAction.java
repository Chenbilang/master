package com.cbl.aa.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cbl.aa.biz.BaseBiz;
import com.cbl.aa.entity.Emp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected T model;

	public  BaseAction() {
		Class clazz=this.getClass();
		Type type=clazz.getGenericSuperclass();
		ParameterizedType ptype=(ParameterizedType)type;
		Type[] types=ptype.getActualTypeArguments();
		clazz=(Class) types[0];
		try {
			model=(T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
						e.printStackTrace();
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	}
	@Override
	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	private BaseBiz<T> baseBiz;

	public void setBaseBiz(BaseBiz baseBiz) {
		this.baseBiz = baseBiz;
		
	}

	// 接收分页数据:提供了两个参数名称：page 代表当前页数 和 rows 每页显示记录数
	private Integer page = 1;
	private Integer rows = 10;

	public void setPage(Integer page) {
		if (page == null) {
			page = 1;
		}
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		if (rows == null) {
			rows = 10;
		}
		this.rows = rows;
	}

	/**
	 * 用作删除,查找的id
	 */
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	private String group;

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * 查询所有部门
	 */
	public void list() {
		// 调用部门业务查询所有部门信息
		List<T> list = baseBiz.getList();
		// 把部门列表转JSON字符串
		String listString = JSON.toJSONString(list);
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(listString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 条件查询
	 */
	public void getList() {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(model.getClass());
		// 使用离线查询可以在web层就设置条件

		detachedCriteria=getDetachedCriteria(model,detachedCriteria);
		
		List<T> lists = new ArrayList<T>();
	
		Integer total = baseBiz.getList(detachedCriteria, page, rows, lists);

		
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("total", total);
		mapData.put("rows", lists);
		// 把部门列表转JSON字符串
		String listString = JSON.toJSONString(mapData,SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}

	/**
	 * 新增
	 * 
	 * @param jsonString
	 */
	public void add() {
		// {"success":true,"message":""}
		// 返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			baseBiz.add(model);
			rtn.put("success", true);
			rtn.put("message", "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("success", false);
			rtn.put("message", "新增失败");
		}
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter()
					.write(JSON.toJSONString(rtn));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 * 
	 * @param jsonString
	 */
	public void delete() {

		try {
			baseBiz.delete(id);
			ajaxReturn(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "删除失败");
		}
	}

	/**
	 * 通过编辑查询对象
	 */
	public void get() {

		System.out.println("id:"+id);
		model = baseBiz.get(id);
		//转换为json时将date类格式化
		String jsonString = JSON.toJSONStringWithDateFormat(model,"yyyy-MM-dd");
        /*  System.out.println(jsonString);*/
		Map<String, Object> map = JSON.parseObject(jsonString);
		//存储key加上前缀后的值
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for(String key : map.keySet()){
			//if里面有对象（对象用map组装）
			if(map.get(key) instanceof Map){
				//key值进行拼接
				//将对象提取出来
				Map<String,Object> m2 = (Map<String,Object>)map.get(key);
				for(String key2 : m2.keySet()){
					dataMap.put(key + "." + key2, m2.get(key2));
				}
			}else{
				dataMap.put(key,map.get(key));
			}
		}
		String jsonStringAfter = JSON.toJSONString(dataMap);
		/*System.out.println(jsonStringAfter);*/
		write(jsonStringAfter);
	}

	/**
	 * 修改
	 */
	public void update() {
		try {
			baseBiz.update(model);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}
	

	public void ajaxReturn(boolean success, String message) {
		// 返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success", success);
		rtn.put("message", message);
		write(JSON.toJSONString(rtn));
	}

	public void write(String jsonString) {
		try {
			// 响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置编码
			response.setContentType("text/html;charset=utf-8");
			// 输出给页面
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
    public DetachedCriteria getDetachedCriteria(T t,DetachedCriteria detachedCriteria){
		
		return detachedCriteria;
	}
    /**
	 * 获取登陆的用户信息
	 * @return
	 */
	public Emp getLoginUser(){
		return (Emp)SecurityUtils.getSubject().getPrincipal();
	}
	/**
	 * 通过字符串编号查询对象
	 * @param uuid
	 * @return
	 */
	public T get(String uuid){
		return baseBiz.get(uuid);
	}
}
