package com.khzpds.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;

/***
 * 用户报名
 * @author zhanglugao 
 *
 */
@RequestMapping("/userApply")
@Controller
public class UserApplyCompetitionController extends BaseController{

	@RequestMapping("toApply")
	public ModelAndView toApply(String type,HttpServletRequest request,HttpServletResponse response){
		String dest=null;
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(type)){
			dest="novel-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
			dest="draw-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
			dest="video-sign";
		}
		return new ModelAndView(getRootPath(request)+"/open/competition/"+dest);
	}
}
