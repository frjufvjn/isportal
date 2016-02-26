package com.hansol.isportal.dbservice.sqlprovider;

import java.util.Map;

import com.hansol.isportal.dbservice.service.DbSvcServiceImpl;

/**
 * @author PJW
 *	XML이 아닌 DB에서 서비스로서 등록된 SQL을 가져옴
 */
public class DbSvcGetSQL {
	
	public String getSql(Map<String,Object> paramMap) throws Exception {
		DbSvcServiceImpl ds = new DbSvcServiceImpl();
		return ds.getSqlBySvcNo(paramMap);
	}	
}
