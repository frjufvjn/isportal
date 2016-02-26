package com.hansol.isportal.dbservice.service;

import java.util.List;
import java.util.Map;

public abstract interface DbSvcService {
	public abstract String getSqlBySvcNo(Map paramMap) throws Exception;
	public abstract List<Map<String,Object>> getListParam(Map paramMap) throws Exception;
}
