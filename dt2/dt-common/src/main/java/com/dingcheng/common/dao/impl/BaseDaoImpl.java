package com.dingcheng.common.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.dingcheng.common.dao.BaseDao;
import com.dingcheng.common.exception.MySqlException;
import com.dingcheng.common.util.GenericsUtils;

public class BaseDaoImpl<T>  implements BaseDao<T>  {
	private Logger logger = Logger.getLogger(BaseDaoImpl.class);
	
    //从spring注入原有的sqlSessionTemplate
	@Autowired  
    private SqlSessionTemplate sqlSession;  
	
	protected Class<T> entityClass;
	protected String sqlMapNamespace;
	
	public static final String POSTFIX_SELECT = ".select";
	public static final String POSTFIX_INSERT = ".insert";
	public static final String POSTFIX_UPDATE = ".update";
	public static final String POSTFIX_DELETE_PK = ".deleteByPrimaryKey";
	public static final String POSTFIX_DELETE = ".delete";
	public static final String POSTFIX_SELECT_MAP = ".selectByMap";
	
	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}
	
	protected SqlSessionTemplate getSqlSession(){
		return sqlSession;
	}
	
	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 * sqlNamespace 带包名
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		sqlMapNamespace = entityClass.getName();
		logger.debug("entityClass="+entityClass+",sqlMapNamespace="+sqlMapNamespace);
	}
	
	@Override
	public T get(Serializable id) {
		if(id==null) return null;
		return sqlSession.selectOne(sqlMapNamespace+POSTFIX_SELECT,id);
	}

	@Override
	public int insert(T o) {
		return sqlSession.insert(sqlMapNamespace+POSTFIX_INSERT, o);
	}
	
	@Override
	public int update(T o) {
		return sqlSession.update(sqlMapNamespace+POSTFIX_UPDATE, o);
	}

	@Override
	public int delete(Integer id) {
		return sqlSession.delete(sqlMapNamespace+POSTFIX_DELETE_PK, id);
	}

	@Override
	public int delete(T o) {
		return sqlSession.delete(sqlMapNamespace+POSTFIX_DELETE, o);
	}
	
	@Override
	public List<T> findAll() {
		return sqlSession.selectList(sqlMapNamespace+POSTFIX_SELECT);
	}

	@Override
	public List<T> findBy(String name, Object value) {
		Assert.hasText(name);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(name, value);
		return findByMap(map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueBy(String name, Object value) {
		Assert.hasText(name);
		if(value==null) return null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(name, value);
			map.put("findBy", "True");
			return (T) sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
		} catch (Exception e) {
			throw new MySqlException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> findByMap(Map<String, Object> map) {
		if (map == null)
			return findAll();
		map.put("findBy", "true");
		logger.debug(map);
		return sqlSession.selectList(sqlMapNamespace+POSTFIX_SELECT_MAP, map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueByMap(Map<String, Object> map) {
		try {
			map.put("findBy", "true");
			return (T) sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
		} catch (Exception e) {
			throw new MySqlException(e.getMessage(), e);
		}
	}

	
}
