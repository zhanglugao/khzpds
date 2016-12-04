package com.khzpds.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.BusinessConfig;
import com.khzpds.base.SessionInfo;
import com.khzpds.service.MenuService;
import com.khzpds.service.UserInfoService;
import com.khzpds.util.EmailTool;
import com.khzpds.util.UUIDUtil;
import com.khzpds.util.VerifyCodeUtil;
import com.khzpds.vo.UserInfoInfo;
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
		}else{
			
			UserInfoInfo findInfo=new UserInfoInfo();
			findInfo.setUserName(user.getUserName());
			List<UserInfoInfo>users=userInfoService.findByParam(findInfo);
			if(users!=null&&users.size()>0){
				result.put("status", "1");
				result.put("error_desc", "用户已存在，请更换会员名称");
			}else{
				findInfo.setMail(user.getMail());
				findInfo.setUserName(null);
				users=userInfoService.findByParam(findInfo);
				if(users!=null&&users.size()>0){
					result.put("status", "1");
					result.put("error_desc", "邮箱已被注册，请更换");
				}
				System.out.println(user);
				user.setId(UUIDUtil.getUUID());
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
		}else{
			UserInfoInfo findInfo=new UserInfoInfo();
			findInfo.setUserName(userName);
			findInfo.setPassword(password);
			List<UserInfoInfo>users=userInfoService.findByParam(findInfo);
			if(users==null||users.size()==0){
				result.put("status", "1");
				result.put("error_desc", "用户名或密码错误");
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
		SessionInfo session=getCurrentSessionInfo(request);
		if(session==null||session.getIfLogin()==null||!session.getIfLogin()){
			this.writeString(response, "0");
		}else{
			this.writeString(response, "1");
		}
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
}
