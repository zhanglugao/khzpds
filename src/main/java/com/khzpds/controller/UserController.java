package com.khzpds.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.BusinessConfig;
import com.khzpds.base.SessionInfo;
import com.khzpds.service.UserInfoService;
import com.khzpds.service.UserService;
import com.khzpds.util.EmailTool;
import com.khzpds.util.UUIDUtil;
import com.khzpds.util.VerifyCodeUtil;
import com.khzpds.vo.User;
import com.khzpds.vo.UserInfoInfo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/index")  
    public ModelAndView getIndex(){    
		ModelAndView mav = new ModelAndView("index"); 
		User user = userService.selectUserById(1);
	    mav.addObject("user", user); 
        return mav;  
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
			UserInfoInfo info=userInfoService.findByUserName(user.getUserName());
			if(info!=null){
				result.put("status", "1");
				result.put("error_desc", "用户已存在，请更换用户名");
			}else{
				System.out.println(user);
				user.setId(UUIDUtil.getUUID());
				userInfoService.add(user);
				result.put("status", "0");
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
	public void sendVerifyCode(String mail,HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		/*//发送邮件
		 boolean res= EmailTool.SendEmail(mail, "科幻创意大赛", "验证码是:"+verifyCode);
	     if(res){
	    	 this.writeString(response, "success");
	     }else{
	    	 this.writeString(response, "fail");
	     }*/
		this.writeString(response, "success");
	}
	

}
