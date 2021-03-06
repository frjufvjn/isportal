package com.hansol.isportal.dbservice.service;

import com.hansol.isportal.dbservice.dao.MainDao;
import java.util.List;
import java.util.Map;

public class MainServiceImpl implements MainService {
	
	private MainDao mainDao;

	public void setMainDao(MainDao mainDao) {
		this.mainDao = mainDao;
	}
	
	public String getToday() {
		return this.mainDao.getToday();
	}

	public List<Object> getList(Map<String, Object> paramMap) {
		return this.mainDao.getList(paramMap);
	}

	public List<Object> getHeader(Map<String, Object> paramMap) {
		return this.mainDao.getHeader(paramMap);
	}

	public List<Object> getRequest(Map<String, Object> paramMap) {
		return this.mainDao.getRequest(paramMap);
	}

	public List<Object> getResponse(Map<String, Object> paramMap) {
		return this.mainDao.getResponse(paramMap);
	}

	public List<Object> getResponseList(Map<String, Object> paramMap) {
		return this.mainDao.getResponseList(paramMap);
	}
	
	public String getSQL(Map<String, Object> paramMap) {
		return this.mainDao.getSQL(paramMap);
	}
}
