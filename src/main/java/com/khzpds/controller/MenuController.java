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
import com.khzpds.service.MenuService;
import com.khzpds.service.UserLoginOperateLogService;
import com.khzpds.vo.MenuInfo;

/***
 * 菜单管理
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController{

	@Autowired
	private MenuService menuService;
	@Autowired
	private UserLoginOperateLogService userLoginOperateLogService;
	
	/**
	 * 主页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/manage/menu/menu_list");
	}
	
	/**
	 * 列表查询
	 * @param name
	 * @param url
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(String name,String url,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		PageParameter page=this.getPageParameter2(request);
		Map<String,String> search=new HashMap<String, String>();
		if(StringUtils.isNotBlank(name))search.put("name", name);
		if(StringUtils.isNotBlank(url))search.put("url", url);
		page.setSearch(search);
		List<MenuInfo> menuList=menuService.findByIndexPage(page);
		
		for(MenuInfo menu:menuList){
			if(2==menu.getLevel()){
				MenuInfo parentMenu=menuService.findById(menu.getParentId());
				menu.setParentName(parentMenu.getName());
			}
		}
		
		result.put("total_page", page.getTotalPage());
		result.put("total_count", page.getTotalCount());
		result.put("rows", menuList);
		this.writeJson(response, result);
	}
	
	/**
	 * 添加/编辑
	 * @param menu
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(MenuInfo menu,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		
		menuService.addOrUpdateMenu(menu,getCurrentSessionInfo(request).getUserId());
		
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/**
	 * 删除
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void delete(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		MenuInfo menu=menuService.findById(id);
		if("1".equals(menu.getVdef1())){
			result.put("status", "1");
			result.put("error_desc", "删除父级菜单需要删除其所有子菜单");
			this.writeJson(response, result);return;
		}
		
		menuService.deleteMenu(menu,getCurrentSessionInfo(request).getUserId());
		
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/****
	 * 加载父级节点数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loadParentData")
	public void loadParentData(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		
		MenuInfo menu=new MenuInfo();
		menu.setLevel(1);
		List<MenuInfo> menus=menuService.findByParamSort(menu);
		result.put("rows", menus);
		
		this.writeJson(response, result);
	}
}
