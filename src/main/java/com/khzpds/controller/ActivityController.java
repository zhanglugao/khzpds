package com.khzpds.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.base.PageParameter;
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.DictionaryService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.DictionaryInfo;

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
	@Autowired
	private DictionaryService dictionaryService;
	/***
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
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
			if(StringUtils.isNotBlank(year)){
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
			Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
			for(ActivityInfoInfo acinfo:activityList){
				acinfo.setStatus(dicMap.get(acinfo.getStatus()));
			}
			result.put("rows", activityList);
			result.put("total_page", page.getTotalCount());
			
			this.writeJson(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/***
	 * 添加/编辑 页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView(getRootPath(request)+"/manage/activity/activity_add");
	}
	
	/***
	 * 添加活动 
	 * 添加活动的同时会添加
	 * @param activity
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addActivity")
	public void addActivity(ActivityInfoInfo activity,Date firstReviewEndtime,Date secondReviewEndtime,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		activity.setCreateTime(new Date());
		activity.setCreateUser(getCurrentSessionInfo(request).getUserName());
		activity.setId(UUIDUtil.getUUID());
		activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_WEI_FA_BU);
		if(activity.getStartTime().before(new Date())){
			//判断是否有已发布状态的活动
			ActivityInfoInfo findInfo=new ActivityInfoInfo();
			findInfo.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU);
			List<ActivityInfoInfo> infos=activityInfoService.findByParam(activity);
			if(infos!=null&&infos.size()>0){
				//有未发布
			}else{
				activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU);
			}
		}
		List<CompetitionItemInfo> ciList=new ArrayList<CompetitionItemInfo>();
		for(int i=0;i<3;i++){
			CompetitionItemInfo item=new CompetitionItemInfo();
			item.setActivityId(activity.getId());
			item.setCreateTime(new Date());
			item.setCreateUser(getCurrentSessionInfo(request).getUserName());
			item.setFirstReviewEndtime(firstReviewEndtime);
			item.setId(UUIDUtil.getUUID());
			if(i==0){
				item.setType(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO);
				item.setName("科幻小说");
			}else if(i==1){
				item.setType(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA);
				item.setName("科幻画");
			}else if(i==2){
				item.setType(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN);
				item.setName("科幻微视频");
			}
			if(DictionaryConst.HUO_DONG_ZHUANG_TAI_WEI_FA_BU.equals(activity.getStatus())){
				//未发布
				item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_WEI_FA_BU);
			}else if(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU.equals(activity.getStatus())){
				//已发布
				item.setPublishTime(new Date());
				item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_FA_BU);
			}
			item.setSecondReviewEndtime(secondReviewEndtime);
			ciList.add(item);
		}
		activityInfoService.addActivityAndCompetitionItem(activity,ciList);
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
