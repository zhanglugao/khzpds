package com.khzpds.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.khzpds.base.BaseController;
import com.khzpds.base.PageParameter;
import com.khzpds.service.MenuService;
import com.khzpds.service.RoleService;
import com.khzpds.service.UserLoginOperateLogService;
import com.khzpds.service.UserRoleService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.MenuInfo;
import com.khzpds.vo.RoleInfo;
import com.khzpds.vo.RoleMenuInfo;
import com.khzpds.vo.UserRoleInfo;

/***
 * 角色管理
 * @author hasee
 *
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserLoginOperateLogService userLoginOperateLogService;
	
	/***
	 * 进入角色首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/manage/role/role_list");
	}
	
	
	/***
	 * 首页查询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(String name,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		PageParameter page=this.getPageParameter2(request);
		Map<String,String> search=new HashMap<String, String>();
		if(StringUtils.isNotBlank(name))search.put("name", name);
		page.setSearch(search);
		List<RoleInfo> roleList=roleService.findByIndexPage(page);
		
		result.put("total_page", page.getTotalPage());
		result.put("total_count", page.getTotalCount());
		result.put("rows", roleList);
		this.writeJson(response, result);
	}
	
	/**
	 * 跳转添加页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(String id,HttpServletRequest request,HttpServletResponse response){
		boolean f=false;
		if(StringUtils.isNotBlank(id)){
			RoleInfo role=roleService.findById(id);
			if(role!=null){
				request.setAttribute("role", role);
				List<MenuInfo> menus=menuService.findByParam(new MenuInfo());
				List<Map<String,String>> maps=menuService.findMenusByRoleId(id);
				for(MenuInfo menu:menus){
					for(Map<String,String> map:maps){
						if(menu.getId().equals(map.get("id"))){
							menu.setRoleId(map.get("roleId"));
							break;
						}
					}
				}
				
				request.setAttribute("menus", JSON.toJSONString(menus));
				f=true;
			}
		}
		if(!f){
			List<MenuInfo> menus=menuService.findByParam(new MenuInfo());
			request.setAttribute("menus", JSON.toJSONString(menus));
		}
		return new ModelAndView(getRootPath(request)
				+"/manage/role/role_add");
	}
	
	/***
	 * 添加/编辑  同步添加分配的菜单
	 * @param id
	 * @param name
	 * @param menuIds
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(String id,String name,String menuIds,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		boolean ifAdd=false;
		RoleInfo role=null;
		if(StringUtils.isBlank(id)){
			ifAdd=true;
			id=UUIDUtil.getUUID();
			role=new RoleInfo();
			role.setId(id);
		}else{
			role=roleService.findById(id);
		}
		if(role==null){
			result.put("status", "1");
			result.put("error_desc", "要编辑的角色已不存在");
			this.writeJson(response, result);
			return;
		}
		role.setName(name);
		String[] menuIdArr=menuIds.split(",");
		List<RoleMenuInfo> roleMenus=new ArrayList<RoleMenuInfo>();
		for(String menuId:menuIdArr){
			RoleMenuInfo roleMenu=new RoleMenuInfo();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(role.getId());
			roleMenus.add(roleMenu);
		}
		roleService.addRole(role,roleMenus,ifAdd,getCurrentSessionInfo(request).getUserId());
		
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 删除角色
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void delete(String id,HttpServletRequest request,HttpServletResponse response ){
		Map<String,Object> result=new HashMap<String, Object>();
		UserRoleInfo findInfo=new UserRoleInfo();
		findInfo.setRoleId(id);
		List<UserRoleInfo> infoList=userRoleService.findByParam(findInfo);
		if(infoList!=null&&infoList.size()>0){
			result.put("status", "1");
			result.put("error_desc", "角色正在使用中，无法删除");
			this.writeJson(response, result);
			return;
		}
		roleService.delete(id);
		userLoginOperateLogService.addLog(id, "角色", "删除", getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
