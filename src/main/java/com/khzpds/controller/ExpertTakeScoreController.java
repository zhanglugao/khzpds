package com.khzpds.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;

/***
 * 专家复赛评分
 * @author hasee
 *
 */
@RequestMapping("/expertTakeScore")
@Controller
public class ExpertTakeScoreController extends BaseController {
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/manage/expertTakeScore/expert_takeScore_list");
	}
	
	
	
}
