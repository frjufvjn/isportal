package com.hansol.isportal.dbservice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hansol.isportal.dbservice.sqlprovider.DbSvcGetSQL;

public abstract interface DbSvcDAO {
	
	String SQL_GET_SQL = "select sql_desc from tp_db_svc_memory where db_svc_no = #{db_svc_no}";
	/*
	 * 1. Annotation을 활용한 방법  
	String SQL_SELECT_TEST = "select * from tp_cm_service";
	@Select(SQL_SELECT_TEST)
	public abstract List<Map<String,Object>> getListTest() throws Exception;*/
	
	
	/**
	 * 2. SQL을 가져와서 활용하는 방법 
	 * @return
	 * @throws Exception
	 */
	
	@Select(SQL_GET_SQL)
	public abstract String getSqlBySvcNo(Map paramMap) throws Exception;
	
	@SelectProvider(type = DbSvcGetSQL.class, method="getSql")
	public abstract List<Map<String,Object>> getListParam(Map paramMap) throws Exception;
	
	@UpdateProvider(type = DbSvcGetSQL.class, method="getSql")
	public abstract void doUpdateList(Map paramMap) throws Exception;
	
	@InsertProvider(type = DbSvcGetSQL.class, method="getSql")
	public abstract void doInsertList(Map<String,Object> paramMap) throws Exception;
	
	@SelectProvider(type = DbSvcGetSQL.class, method="getSql")
	public abstract Map<String,Object> doProcedure(Map<String,Object> paramMap) throws Exception;
}
