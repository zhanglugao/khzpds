package com.khzpds.controller;

import java.util.List;
import java.util.Map;

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
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;

@RequestMapping("/activityItemLimit")
@Controller
public class ActivityItemLimitController extends BaseController{
	
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private CompetitionItemService competitionItemService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		return new ModelAndView(getRootPath(request)+"/manage/activityItemLimit/activity_item_limit_list");
	}
	
	/***
	 * 查看项目
	 * @param id
	 * @return
	 */
	@RequestMapping("/toShowItem")
	public ModelAndView toShowItem(String id,HttpServletRequest request,HttpServletResponse response){
		ActivityInfoInfo activity=activityInfoService.findById(id);
		
		CompetitionItemInfo findInfo=new CompetitionItemInfo();
		findInfo.setActivityId(id);
		List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo);
		
		for(CompetitionItemInfo item:items){
			item.setType(dictionaryService.findById(item.getType()).getName());
			item.setStatusName(dictionaryService.findById(item.getStatus()).getName());
		}
		
		request.setAttribute("activity", activity);
		request.setAttribute("items", items);
		return new ModelAndView(getRootPath(request)+"/manage/activityItemLimit/activity_item_limit_item");
	}
	
	/***
	 * 前往设置限额界面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSetItemLimit")
	public ModelAndView toSetItemLimit(String id,String activityId,HttpServletRequest request,HttpServletResponse response){
		//查询出所有的管理员以及设置好 限额信息
		
		return new ModelAndView(getRootPath(request)+"/manage/activityItemLimit/activity_item_change_limit");
	}
}
