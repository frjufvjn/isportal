package com.hansol.isportal.dbservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hansol.isportal.dbservice.service.MainService;

/**
 * @author PJW
 *
 */
@Controller
public class DbServiceController {
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping("today.do")
	public void today(@RequestParam Map<String,Object> paramMap, ModelMap model) {
		model.put("today", mainService.getToday());
	}
	
	@RequestMapping({"hello.do"})
	public void hello(@RequestParam Map<String,Object> paramMap, ModelMap model) {
		model.put("title", "Hello World!!");
	}
	
	@RequestMapping({"board.do"})
	public void test(@RequestParam Map<String,Object> paramMap, ModelMap model) {
		model.put("title", "Hello World!!");
	}
}
