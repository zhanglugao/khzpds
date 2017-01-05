package com.khzpds.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.khzpds.base.BaseController;
import com.khzpds.base.BusinessConfig;
import com.khzpds.base.PageParameter;
import com.khzpds.base.SessionInfo;
import com.khzpds.service.ContentCategoryService;
import com.khzpds.service.ManagerOrgService;
import com.khzpds.service.MenuService;
import com.khzpds.service.RoleService;
import com.khzpds.service.UserInfoService;
import com.khzpds.service.UserLoginOperateLogService;
import com.khzpds.service.UserRoleService;
import com.khzpds.util.EmailTool;
import com.khzpds.util.UUIDUtil;
import com.khzpds.util.VerifyCodeUtil;
import com.khzpds.vo.ContentCategoryInfo;
import com.khzpds.vo.ManagerOrgInfo;
import com.khzpds.vo.RoleInfo;
import com.khzpds.vo.UserInfoInfo;
import com.khzpds.vo.UserLoginOperateLogInfo;
import com.khzpds.vo.UserRoleInfo;
/**
 * 
 * @author zhanglugao
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ManagerOrgService managerOrgService;
	@Autowired
	private ContentCategoryService contentCategoryService;
	@Autowired
	private UserLoginOperateLogService userLoginOperateLogService;
	
	/***
	 * 学员登陆主界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/openIndex")
	public ModelAndView openIndex(HttpServletRequest request){
		return new ModelAndView(getRootPath(request)+"/open/user/user-index");
	}
	/***
	 * 后台用户管理主界面
	 * @return
	 */
	@RequestMapping("/index")  
    public ModelAndView getIndex(HttpServletRequest request){ 
		return new ModelAndView(getRootPath(request)+"/manage/user/user_list");
    }
	/***
	 * 注册页
	 * @return
	 */
	@RequestMapping("/register")
	public void register(UserInfoInfo user,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		SessionInfo session=getCurrentSessionInfo(request);
		String verifyCode=request.getParameter("verifyCode")==null?"":request.getParameter("verifyCode");
		if(session==null||!verifyCode.equals(session.getVerifyCode())){
			result.put("status", "1");
			result.put("error_desc", "验证码错误或已失效");
			this.writeJson(response, result);
			return;
		}else{
			
			UserInfoInfo findInfo=new UserInfoInfo();
			findInfo.setUserName(user.getUserName());
			List<UserInfoInfo>users=userInfoService.findByParam(findInfo);
			if(users!=null&&users.size()>0){
				result.put("status", "1");
				result.put("error_desc", "用户已存在，请更换会员名称");
				this.writeJson(response, result);
				return;
			}else{
				findInfo.setMail(user.getMail());
				findInfo.setUserName(null);
				users=userInfoService.findByParam(findInfo);
				if(users!=null&&users.size()>0){
					result.put("status", "1");
					result.put("error_desc", "邮箱已被注册，请更换");
					this.writeJson(response, result);
					return;
				}
				System.out.println(user);
				user.setId(UUIDUtil.getUUID());
				user.setCreateTime(new Date());
				user.setStatus("0");
				userInfoService.add(user);
				session=userInfoService.setSession(user);
				request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, session);
				result.put("status", "0");
				result.put("jump_url", "/user/openIndex");
			}
		}
		this.writeJson(response, result);
	}
	
	/***
	 * 发送验证码
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/sendVerifyCode")
	public void sendVerifyCode(String mail,HttpServletRequest request,HttpServletResponse response) {
		//校验邮箱被注册没
		try{
			UserInfoInfo findInfo=new UserInfoInfo();
			findInfo.setMail(mail);
			List<UserInfoInfo>users=userInfoService.findByParam(findInfo);
			if(users!=null&&users.size()>0){
				this.writeString(response, "mailRepeat");
				return;
			}
			request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, null);
			SessionInfo sessionInfo=getCurrentSessionInfo(request);
			String verifyCode=VerifyCodeUtil.createRandom(true, 6);
			if(sessionInfo==null){
				sessionInfo=new SessionInfo();
				sessionInfo.setSendMail(mail);
				sessionInfo.setSendVerifyCodeTime(new Date());
				sessionInfo.setVerifyCode(verifyCode);
				request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, sessionInfo);
			}
			//发送邮件
			 boolean res= EmailTool.SendEmail(mail, "科幻创意大赛-注册", "您的注册验证码是:"+verifyCode+"，请在30分钟内输入完成注册。\r\n（这是一封自动发送的邮件，请不要直接回复）");
		     if(res){
		    	 this.writeString(response, "success");
		     }else{
		    	 this.writeString(response, "fail");
		     }
			//this.writeString(response, "success");
		}catch(Exception e){
			e.printStackTrace();
			this.writeString(response, "fail");		
		}
		
	}
	
	/***
	 * 登录
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping("/login")
	public void login(String userName,String password,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
			result.put("status", "1");
			result.put("error_desc", "用户名或密码为空");
			this.writeJson(response, result);
			return;
		}else{
			UserInfoInfo findInfo=new UserInfoInfo();
			findInfo.setUserName(userName);
			findInfo.setPassword(password);
			List<UserInfoInfo>users=userInfoService.findByParam(findInfo);
			if(users==null||users.size()==0){
				result.put("status", "1");
				result.put("error_desc", "用户名或密码错误");
				this.writeJson(response, result);
				return;
			}else{
				//校验成功
				SessionInfo session=userInfoService.setSession(users.get(0));
				request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, session);
				result.put("status", "0");
				if(session.getMenus()!=null&&session.getMenus().size()>0){
					result.put("jump_url", session.getMenus().get(0).getUrl());
				}else{
					result.put("jump_url", "/user/openIndex");
				}
				//记录登录日志
				UserLoginOperateLogInfo log=new UserLoginOperateLogInfo();
				log.setId(UUIDUtil.getUUID());
				log.setOperateTime(new Date());
				log.setResourceId(users.get(0).getId());
				log.setResourceType("用户登录");
				log.setType("登录");
				log.setUserId(users.get(0).getId());
				userLoginOperateLogService.add(log);
			}
		}
		this.writeJson(response, result);
	}
	
	
	/***
	 * 获取登录状态 0 未登录  1已登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loginStatus")
	public void getLoginStatus(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		SessionInfo session=getCurrentSessionInfo(request);
		if(session==null||session.getIfLogin()==null||!session.getIfLogin()){
			result.put("result", "0");
		}else{
			result.put("result", "1");
			result.put("userName", session.getUserName());
		}
		writeJson(response, result);
	}
	
	/***
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, null);
		return "redirect:/login.html";
	}
	
	/***
	 * 更改会员名称
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changeUserInfo")
	public void changeUserInfo(String userName,String password,String realName,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		
		//判断userName的合法性
		UserInfoInfo findInfo=new UserInfoInfo();
		findInfo.setUserName(userName);
		List<UserInfoInfo> users=userInfoService.findByParam(findInfo);
		if(users!=null&&users.size()>0&&!users.get(0).getId().equals(getCurrentSessionInfo(request).getUserId())){
			result.put("status", "1");
			result.put("error_desc", "用户已存在，请更换会员名称");
			this.writeJson(response, result);
			return;
		}
		UserInfoInfo user=userInfoService.findById(getCurrentSessionInfo(request).getUserId());
		user.setUserName(userName);
		user.setPassword(password);
		user.setRealName(realName);
		userInfoService.update(user);
		SessionInfo session=userInfoService.setSession(user);
		request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, session);
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 找回密码
	 * @param userName
	 * @param email
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findPassword")
	public void findPassword(String userName,String email,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(email)){
			result.put("status", "1");
			result.put("error_desc","用户名或邮箱为空");
			this.writeJson(response, result);return;
		}
		UserInfoInfo findInfo=new UserInfoInfo();
		findInfo.setUserName(userName);
		findInfo.setMail(email);
		List<UserInfoInfo> users=userInfoService.findByParam(findInfo);
		if(users==null||users.size()==0){
			result.put("status", "1");
			result.put("error_desc","用户名或邮箱错误");
			this.writeJson(response, result);return;
		}
		
		try{
			//发送邮件
			 boolean res= EmailTool.SendEmail(email, "科幻创意大赛-密码找回", "您要找回的用户名:"+userName+"，您的密码是:"+users.get(0).getPassword()+"  \r\n（这是一封自动发送的邮件，请不要直接回复）");
		     if(res){
		    	result.put("status", "0");
		    	this.writeJson(response, result);
		     }else{
		    	result.put("status", "1");
				result.put("error_desc","发送邮件失败，请重试或联系管理员");
				this.writeJson(response, result);
		     }
		}catch(Exception e){
			e.printStackTrace();
			result.put("status", "1");
			result.put("error_desc","发送邮件失败，请重试或联系管理员");
			this.writeJson(response, result);
		}
	}
	
	@RequestMapping("/getData")
	public void getData(String name,String mail,String realName,String type,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(type))type="0";
		Map<String,Object> result=new HashMap<String, Object>();
		
		PageParameter page=getPageParameter2(request);
		
		Map<String,String> search=new HashMap<String, String>();
		if(StringUtils.isNotBlank(name)) search.put("name", name);
		if(StringUtils.isNotBlank(mail)) search.put("mail", mail);
		if(StringUtils.isNotBlank(realName)) search.put("realName", realName);
		
		search.put("type", type);
		
		page.setSearch(search);
		
		List<UserInfoInfo> users=userInfoService.findByIndexForPage(page);
		if("1".equals(type)){
			for(UserInfoInfo user:users){
				UserRoleInfo findInfo=new UserRoleInfo();
				findInfo.setUserId(user.getId());
				List<UserRoleInfo> roles=userRoleService.findByParam(findInfo);
				StringBuffer sb=new StringBuffer();
				StringBuffer sb2=new StringBuffer();
				for(UserRoleInfo userRole:roles){
					RoleInfo role=roleService.findById(userRole.getRoleId());
					if("".equals(sb.toString())){
						sb.append(userRole.getRoleId());
						sb2.append(role.getName());
					}else{
						sb.append(","+userRole.getRoleId());
						sb2.append(","+role.getName());
					}
				}
				user.setRoleIds(sb.toString());
				user.setRoleNames(sb2.toString());
				
				ManagerOrgInfo orgFind=new ManagerOrgInfo();
				orgFind.setManagerId(user.getId());
				List<ManagerOrgInfo> managerOrgs=managerOrgService.findByParam(orgFind);
				StringBuffer sb3=new StringBuffer();
				StringBuffer sb4=new StringBuffer();
				for(ManagerOrgInfo managerOrg:managerOrgs){
					ContentCategoryInfo orgInfo=contentCategoryService.findById(managerOrg.getOrgId());
					if("".equals(sb3.toString())){
						sb3.append(managerOrg.getOrgId());
						sb4.append(orgInfo.getName());
					}else{
						sb3.append(","+managerOrg.getOrgId());
						sb4.append(","+orgInfo.getName());
					}
				}
				
				user.setOrgId(sb3.toString());
				user.setOrgName(sb4.toString());
			}
		}
		result.put("rows", users);
		result.put("total_page", page.getTotalPage());
		this.writeJson(response, result);
	}
	
	
	@RequestMapping("/add")
	public void add(UserInfoInfo user,String type,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		//判断userName的合法性
		UserInfoInfo findInfo=new UserInfoInfo();
		findInfo.setUserName(user.getUserName());
		List<UserInfoInfo> users=userInfoService.findByParam(findInfo);
		if(users!=null&&users.size()>0&&!users.get(0).getId().equals(user.getId())){
			result.put("status", "1");
			result.put("error_desc", "用户已存在，请更换会员名称");
			this.writeJson(response, result);
			return;
		}
		
		findInfo.setUserName(null);
		findInfo.setMail(user.getMail());
		users=userInfoService.findByParam(findInfo);
		if(users!=null&&users.size()>0&&!users.get(0).getId().equals(user.getId())){
			result.put("status", "1");
			result.put("error_desc", "邮箱已被注册，请更换");
			this.writeJson(response, result);
			return;
		}
		
		if(StringUtils.isBlank(type)){
			type="0";
		}
		if("0".equals(type)){
			if(StringUtils.isBlank(user.getId())){
				user.setId(UUIDUtil.getUUID());
				user.setPassword("000000");
				userInfoService.add(user);
			}else{
				userInfoService.update(user);
			}
		}else if("1".equals(type)){
			String roleIds=request.getParameter("roleIds");
			boolean ifAdd=false;
			if(StringUtils.isBlank(user.getId())){
				ifAdd=true;
				user.setId(UUIDUtil.getUUID());
				user.setPassword("111111");
			}
			String[] roleArr=roleIds.split(",");
			List<UserRoleInfo> userRoleList=new ArrayList<UserRoleInfo>();
			for(String roleId:roleArr){
				UserRoleInfo userRole=new UserRoleInfo();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleList.add(userRole);
			}
			String orgId=request.getParameter("orgId");
			List<ManagerOrgInfo> managerOrgList=new ArrayList<ManagerOrgInfo>();
			if(StringUtils.isNotBlank(orgId)&&!"undefined".equals(orgId)){
				ManagerOrgInfo managerOrg=new ManagerOrgInfo();
				managerOrg.setManagerId(user.getId());
				managerOrg.setOrgId(orgId);
				managerOrgList.add(managerOrg);
			}
			userInfoService.updateManagerInfo(user,userRoleList,managerOrgList,ifAdd);
			
		}
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	@RequestMapping("/resetpwd")
	public void resetpwd(String id,String type,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(type)){
			type="0";
		}
		Map<String,Object> result=new HashMap<String, Object>();
		UserInfoInfo user=userInfoService.findById(id);
		if(user==null){
			result.put("status", "1");
			result.put("error_desc", "用户已不存在");
			this.writeJson(response, result);return;
		}
		if("0".equals(type)){
			user.setPassword("000000");
		}else if("1".equals(type)){
			user.setPassword("111111");
		}
		userInfoService.update(user);
		result.put("status", "0");
		this.writeJson(response, result);
	}

	/***
	 * 管理员管理首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manageIndex")
	public ModelAndView manageIndex(HttpServletRequest request,HttpServletResponse response){
		//查出所有的角色
		List<RoleInfo> roles=roleService.findByParam(new RoleInfo());
		request.setAttribute("roles",JSON.toJSONString(roles));
		return new ModelAndView(getRootPath(request)+"/manage/manager/manager_list");
	}
	
	
	/**
	 * 管理员个人资料编辑页面
	 * @return
	 */
	@RequestMapping("/managerEditIndex")
	public ModelAndView managerInfoEdit(HttpServletRequest request,HttpServletResponse response){
		UserInfoInfo user=userInfoService.findById(getCurrentSessionInfo(request).getUserId());
		request.setAttribute("editUser", user);
		return new ModelAndView(getRootPath(request)+"/manage/manager/manager_edit");
	}
}
