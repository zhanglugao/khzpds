package com.khzpds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.PageParameter;
import com.khzpds.service.UserLoginOperateLogService;

@Controller
@RequestMapping("/loginOperateLog")
public class UserLoginOperateLogController extends BaseController{
	
	@Autowired
	private UserLoginOperateLogService userLoginOperateLogService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView(getRootPath(request)+"/manage/userLoginOperateLog/log_index");
	}
	
	@RequestMapping("/getData")
	public void getData(String name,String operateType,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		PageParameter page=this.getPageParameter2(request);
		Map<String,String> search=new HashMap<String,String>();
		if(StringUtils.isNotBlank(name)) search.put("name", name);
		if(StringUtils.isNotBlank(operateType)) search.put("type", operateType);
		page.setSearch(search);
		List<Map<String,String>> list=userLoginOperateLogService.findIndexPage(page);
		result.put("total_count", page.getTotalCount());
		result.put("total_page", page.getTotalPage());
		result.put("rows", list);
		this.writeJson(response, result);
		
	}
}
