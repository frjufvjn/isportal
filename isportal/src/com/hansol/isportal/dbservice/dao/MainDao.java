package com.hansol.isportal.dbservice.dao;

import java.util.List;
import java.util.Map;

public abstract interface MainDao
{
	public abstract String getToday();
	public abstract List getList(Map<String, Object> paramMap);
	public abstract List<Object> getHeader(Map<String, Object> paramMap);
	public abstract List<Object> getRequest(Map<String, Object> paramMap);
	public abstract List<Object> getResponse(Map<String, Object> paramMap);
	public abstract List<Object> getResponseList(Map<String, Object> paramMap);
	public abstract String getSQL(Map<String, Object> paramMap);
}