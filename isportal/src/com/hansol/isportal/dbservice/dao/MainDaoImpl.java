package com.hansol.isportal.dbservice.dao;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class MainDaoImpl extends SqlSessionDaoSupport implements MainDao {
	
	public String getToday() {
		return (String)getSqlSession().selectOne("main.getToday");
	}

	public List getList(Map<String, Object> paramMap) {
		return getSqlSession().selectList("main.getList");
	}

	public List<Object> getHeader(Map<String, Object> paramMap) {
		return getSqlSession().selectList("main.getHeader", paramMap);
	}

	public List<Object> getRequest(Map<String, Object> paramMap) {
		return getSqlSession().selectList("main.getRequest", paramMap);
	}

	public List<Object> getResponse(Map<String, Object> paramMap) {
		return getSqlSession().selectList("main.getResponse", paramMap);
	}

	public List<Object> getResponseList(Map<String, Object> paramMap) {
		return getSqlSession().selectList("main.getResponseList", paramMap);
	}
	
	public String getSQL(Map<String, Object> paramMap) {
		return getSqlSession().selectOne("main.getSQL", paramMap);
	}
}