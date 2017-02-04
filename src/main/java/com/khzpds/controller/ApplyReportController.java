package com.khzpds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.vo.ActivityInfoInfo;

@Controller
@RequestMapping("/applyReport")
public class ApplyReportController extends BaseController {

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ActivityInfoService activityService;
	@Autowired
	private UserCompletionItemApplyService userCompletionItemApplyService;
	/***
	 * 报名查询统计首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		return new ModelAndView(getRootPath(request)+"/manage/report/apply_report_index");
	}
	
	/***
	 * 详细活动报表数据
	 * @param id	活动id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView detail(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		ActivityInfoInfo activity=activityService.findById(id);
		request.setAttribute("activity", activity);
		
		List<Map<String,Object>> reportData=userCompletionItemApplyService.findReportDataByActivityId(id);
		
		for(Map<String,Object> map:reportData){
			String key="data"+map.get("itemType")+map.get("applyGroup")+map.get("applyYearGroup");
			String value=map.get("num").toString();
			result.put(key, value);
		}
		
		request.setAttribute("result", result);
		
		return new ModelAndView(getRootPath(request)+"/manage/report/apply_report_detail");
	}
}
