package com.khzpds.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.service.UserInfoService;
import com.khzpds.service.UserService;
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
	@RequestMapping("/query")
	public void queryUser(HttpServletResponse response){
		UserInfoInfo user=userInfoService.findById("1");
		System.out.println(user);
		writeString(response, "success");
	}
}
