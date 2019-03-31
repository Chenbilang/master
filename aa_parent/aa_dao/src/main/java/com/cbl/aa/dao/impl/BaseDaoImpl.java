package com.cbl.aa.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cbl.aa.dao.BaseDao;

/**
 * 通用的DAO的实现类
 * 
 * @author jt
 *
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class clazz;

	// 提供构造方法：在构造方法中传入具体类型的class
	/**
	 * 不想子类上有构造方法，必须在父类中提供无参数的构造，在无参构造中获得具体类型的Class。 具体类型的Class是参数类型中的实际类型参数。
	 */
	public BaseDaoImpl() {
		// 反射：第一步获得Class
		Class clazz = this.getClass();// 正在被调用那个类的Class,CustomerDaoImpl
		// 查看JDK的API。Type是Java编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
		Type type = clazz.getGenericSuperclass();// 参数化类型：BaseDaoImpl<Customer>
		/* System.out.println(type); */
		// 得到这个type就是一个参数化的类型， 将type强转成参数化的类型:
		ParameterizedType pType = (ParameterizedType) type;
		// 通过参数化类型获得实际类型参数:得到一个实际类型参数的数组？如果是Map<String,Integer>，就有两个
		Type[] types = pType.getActualTypeArguments(); // 实际类型参数 Customer
		// 只获得第一个实际类型参数即可。
		this.clazz = (Class) types[0];// 得到Customer
	}

	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public T findById(Serializable id) {
		return (T) this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	// 查询所有的方法
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find(
				"from " + clazz.getSimpleName());
	}

	@Override
	// 统计个数的方法
	public Integer findCount(DetachedCriteria detachedCriteria) {
		// 设置统计个数的条件:

		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate()
				.findByCriteria(detachedCriteria);
		if (list.size() > 0) {
			return list.get(0).intValue();
		}
		return null;
	}

	@Override
	// 分页查询
	public List<T> findByPage(DetachedCriteria detachedCriteria, Integer begin,
			Integer pageSize) {

		detachedCriteria.setProjection(null);

		return (List<T>) this.getHibernateTemplate().findByCriteria(
				detachedCriteria, begin, pageSize);
	}

	// 条件查询
	public List<T> getList(T t) {
		DetachedCriteria dc=getDetachedCriteria(t);
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}

	public DetachedCriteria getDetachedCriteria(T t) {

		return null;
	}

	@Override
	public List<Object[]> findGroudByOne(DetachedCriteria detachedCriteria,String property) {
		String proID;
		if(!property.equals("spot_time")){
			proID=property+"_id";
			detachedCriteria.setProjection(Projections.projectionList().add(Projections.groupProperty(proID)).add(Projections.groupProperty(property)));
		}else {
			detachedCriteria.setProjection(Projections.projectionList().add(Projections.groupProperty(property)));
		}
		
		return (List<Object[]>) this.getHibernateTemplate()
		.findByCriteria(detachedCriteria);
	}

}
