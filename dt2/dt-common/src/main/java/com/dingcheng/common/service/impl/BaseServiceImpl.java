package com.dingcheng.common.service.impl;

import java.util.List;
import java.util.Map;

import com.dingcheng.common.dao.BaseDao;
import com.dingcheng.common.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	
	/**
	 * 由业务类实现
	 * @return
	 */
	public abstract BaseDao<T> getBaseDao();
	
	/**
	 * 根据id,查询单个
	 * @param id
	 * @return
	 */
    public T get(Integer id){
    	return getBaseDao().get(id);
    }
    
    /**
     * 插入单个对象
     * @param model
     * @return
     */
    public int create(T model){
    	return getBaseDao().insert(model);
    }
    
    /**
     * 根据主键更新对象,更新对象所有字段
     * @param model
     * @return
     */
    public int update(T model){
    	return getBaseDao().update(model);
    }
    
    /**
     * 根据主键删除
     * @param o
     * @return
     */
    public int delete(Integer id){
    	return getBaseDao().delete(id);
    }
    
    /**
     * 根据对象信息删除
     * 例如,传入 对象 User, id=1,name=a , 则删除 id=1,name=a的记录
     * 如果传入对象 User,name=a, 则删除 name=a的记录
     * @param o
     * @return
     */
    public int delete(T model){
    	return getBaseDao().delete(model);
    }
    
	/**
	 * 查询所有记录
	* @return
	 */
	public List<T> findAll(){
		return getBaseDao().findAll();
	}
    
    /**
     * 根据属性名和属性值查询对象
     * @param name	属性名
     * @param value	属性值
     * @return	符合条件的对象列表
     */
	public List<T> findBy(String name, Object value){
		return getBaseDao().findBy(name, value);
	}
	
	
	/**
	 * 根据map 查询对象
	 * @param map	key=属性名,value=属性值
	 * @return	符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map){
		return getBaseDao().findByMap(map);
	}
	
	/**
	 * 根据属性名和属性值查询单个对象
	 * @param name	属性名
	 * @param value	属性值
	 * @return	符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueBy(String name, Object value){
		return getBaseDao().findUniqueBy(name, value);
	}
	
	/**
	 * 根据map 查询单个对象
	 * @param name	属性名
	 * @param value	属性值
	 * @return	符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueByMap(Map<String, Object> map){
		return getBaseDao().findUniqueByMap(map);
	}
}
