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
import com.khzpds.service.ActivityInfoService;
import com.khzpds.vo.ActivityInfoInfo;

/***
 * 活动管理controller
 * @author zlg
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController{
	@Autowired
	private ActivityInfoService activityInfoService;
	/***
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/manage/activity/activity_list");
	}
	
	/***
	 * 查询列表
	 * @param name
	 * @param year
	 * @param status
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(String name,String year,String status,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		try{
			PageParameter page=getPageParameter2(request);
			Map<String,String> search=new HashMap<String, String>();
			if(StringUtils.isNotBlank(name)){
				search.put("year", year);
			}if(StringUtils.isNotBlank(name)){
				search.put("name", name);
			}if(StringUtils.isNotBlank(status)){
				search.put("status", status);
			}
			page.setSearch(search);
			page.setOrderField("create_time");
			page.setOrderType("desc");
			List<ActivityInfoInfo> activityList=activityInfoService.findByParamLikeForPage(page);
			
			result.put("rows", activityList);
			result.put("total_page", page.getTotalCount());
			
			this.writeJson(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
