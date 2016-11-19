package com.khzpds.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.service.UserService;
import com.khzpds.vo.User;

/**
 * 功能概要：UserController
 * 
 * @author linbingwen
 * @since  2015�?�?8�?
 */
@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/")  
    public ModelAndView getIndex(){    
		ModelAndView mav = new ModelAndView("index"); 
		User user = userService.selectUserById(1);
	    mav.addObject("user", user); 
        return mav;  
    }  
}
