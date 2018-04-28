package com.dingcheng.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseDao<T> {
	/**
	 * 根据id,查询单个
	 * @param id
	 * @return
	 */
    public T get(Serializable id);
    
    /**
     * 插入单个对象
     * @param model
     * @return
     */
    public int insert(T model);
    
    /**
     * 根据主键更新对象
     * @param model
     * @return
     */
    public int update(T model);
    
    /**
     * 根据主键删除
     * @param o
     * @return
     */
    public int delete(Integer id);
    
    /**
     * 根据对象信息删除
     * 例如,传入 对象 User, id=1,name=a , 则删除 id=1,name=a的记录
     * 如果传入对象 User,name=a, 则删除 name=a的记录
     * @param model
     * @return
     */
    public int delete(T model);
    
	/**
	 * 查询所有记录
	* @return
	 */
	public List<T> findAll();
    
    /**
     * 根据属性名和属性值查询对象
     * @param name	属性名
     * @param value	属性值
     * @return	符合条件的对象列表
     */
	public List<T> findBy(String name, Object value);
	
	/**
	 * 根据map 查询对象
	 * @param map	key=属性名,value=属性值
	 * @return	符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map);
	
	/**
	 * 根据属性名和属性值查询单个对象
	 * @param name	属性名
	 * @param value	属性值
	 * @return	符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueBy(String name, Object value);
	
	/**
	 * 根据map 查询单个对象
	 * @param name	属性名
	 * @param value	属性值
	 * @return	符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueByMap(Map<String, Object> map);
	
}
