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
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.service.UserLoginOperateLogService;
import com.khzpds.util.DateUtil;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

/***
 * 活动管理controller
 * @author zhanglugao 
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController{
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private UserLoginOperateLogService userLoginOperateLogService;
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
			result.put("total_page", page.getTotalPage());
			result.put("total_count", page.getTotalCount());
			
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
	
	/**
	 * to编辑页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,HttpServletRequest request,HttpServletResponse response){
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
		
		return new ModelAndView(getRootPath(request)+"/manage/activity/activity_edit");
	}
	
	/***
	 * 结束活动
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/turnEnd")
	public void turnEnd(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(activity==null){
			result.put("status", "1");
			result.put("error_desc", "要中止的活动已不存在，请刷新页面重试");
			this.writeJson(response, result);return;
		}
		activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_JIE_SHU);
		activity.setEndTime(new Date());
		if(activity.getStartTime().after(new Date())){
			activity.setStartTime(new Date());
		}
		activity.setUpdateTime(new Date());
		activity.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		CompetitionItemInfo findInfo=new CompetitionItemInfo();
		findInfo.setActivityId(id);
		List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo);
		for(CompetitionItemInfo item:items){
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_JIE_SHU);
			item.setUpdateTime(new Date());
			item.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		}
		activityInfoService.updateActivity(activity,items,this.getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 废弃活动
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/terminate")
	public void terminate(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(activity==null){
			result.put("status", "1");
			result.put("error_desc", "要废弃的活动已不存在，请刷新页面重试");
			this.writeJson(response, result);return;
		}
		activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FEI_QI);
		activity.setEndTime(new Date());
		if(activity.getStartTime().after(new Date())){
			activity.setStartTime(new Date());
		}
		activity.setUpdateTime(new Date());
		activity.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		CompetitionItemInfo findInfo=new CompetitionItemInfo();
		findInfo.setActivityId(id);
		List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo);
		for(CompetitionItemInfo item:items){
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_FEI_QI);
			item.setUpdateTime(new Date());
			item.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		}
		activityInfoService.updateActivity(activity,items,this.getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/**
	 * 删除活动
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void delete(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(activity==null){
			result.put("status", "1");
			result.put("error_desc", "要删除的活动已不存在，请刷新页面重试");
			this.writeJson(response, result);return;
		}
		UserCompletionItemApplyInfo applyFind=new UserCompletionItemApplyInfo();
		applyFind.setActivityId(id);
		List<UserCompletionItemApplyInfo> applys=userCompetitionItemApplyService.findByParam(applyFind);
		if(applys!=null&&applys.size()>0){
			result.put("status", "1");
			result.put("error_desc", "活动下的项目已经有用户报名，无法删除");
			this.writeJson(response, result);return;
		}
		CompetitionItemInfo findInfo=new CompetitionItemInfo();
		findInfo.setActivityId(id);
		List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo);
		activityInfoService.deleteActivity(id,items,this.getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 发布活动
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/publish")
	public void publish(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		//判断有没有正常运行中的活动
		ActivityInfoInfo findInfo=new ActivityInfoInfo();
		findInfo.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU);
		List<ActivityInfoInfo> infos=activityInfoService.findByParam(findInfo);
		if(infos!=null&&infos.size()>0){
			//有未发布
			result.put("status", "1");
			result.put("error_desc", "存在已发布的活动，无法发布");
			this.writeJson(response, result);return;
		}
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(activity==null){
			result.put("status", "1");
			result.put("error_desc", "要发布的活动已不存在，请刷新页面重试");
			this.writeJson(response, result);return;
		}
		activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU);
		activity.setStartTime(new Date());
		if(activity.getEndTime().before(activity.getStartTime())){
			activity.setEndTime(DateUtil.addDate(activity.getStartTime(), 120));
		}
		activity.setUpdateTime(new Date());
		activity.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		CompetitionItemInfo findInfo2=new CompetitionItemInfo();
		findInfo2.setActivityId(id);
		List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo2);
		for(CompetitionItemInfo item:items){
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_FA_BU);
			item.setPublishTime(new Date());
			item.setUpdateTime(new Date());
			item.setUpdateUser(this.getCurrentSessionInfo(request).getUserId());
		}
		activityInfoService.updateActivity(activity,items,this.getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 添加活动 
	 * 添加活动的同时会添加
	 * @param activity
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addActivity")
	public void addActivity(ActivityInfoInfo activity,Date firstReviewEndtime,Date secondReviewEndtime,Date firstReviewStarttime,Date userApplyEndtime,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		activity.setCreateTime(new Date());
		activity.setCreateUser(getCurrentSessionInfo(request).getUserName());
		activity.setId(UUIDUtil.getUUID());
		activity.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_WEI_FA_BU);
		if(activity.getStartTime().before(new Date())){
			//判断是否有已发布状态的活动
			ActivityInfoInfo findInfo=new ActivityInfoInfo();
			findInfo.setStatus(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU);
			List<ActivityInfoInfo> infos=activityInfoService.findByParam(findInfo);
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
			item.setUserApplyEndtime(userApplyEndtime);
			item.setFirstReviewStarttime(firstReviewStarttime);
			ciList.add(item);
		}
		activityInfoService.addActivityAndCompetitionItem(activity,ciList,this.getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/**
	 * 更新姓名
	 * @param id
	 * @param name
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editName")
	public void editName(String id,String name,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(activity==null){
			result.put("status", "1");
			result.put("error_desc", "活动已不存在");
			this.writeJson(response, result);return;
		}
		activity.setName(name);
		activityInfoService.update(activity);
		userLoginOperateLogService.addLog(activity.getId(), "活动", "修改", getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/**
	 * 更改项目状态
	 * @param id
	 * @param type
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changeItemStatus")
	public void changeItemStatus(String id,String type,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		CompetitionItemInfo item=competitionItemService.findById(id);
		if(item==null){
			result.put("status", "1");
			result.put("error_desc", "项目已不存在，请刷新重试");
			this.writeJson(response, result);return;
		}
		if("0".equals(type)){
			//结束报名
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_BAO_MING_JIE_SHU);
			item.setUserApplyEndtime(new Date());
		}
		if("1".equals(type)){
			//启动一轮评审
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_LUN_PING_SHEN_ZHONG);
			item.setFirstReviewStarttime(new Date());
		}
		if("2".equals(type)){
			//结束一轮评审
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_LUN_PING_SHEN_JIE_SHU);;
			item.setFirstReviewEndtime(new Date());
		}
		if("3".equals(type)){
			//开始二轮评审
			item.setStatus(DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_ER_LUN_PING_SHEN_JIE_SHU);
			item.setSecondReviewEndtime(new Date());
		}
		
		competitionItemService.update(item);
		userLoginOperateLogService.addLog(id, "项目", "修改", getCurrentSessionInfo(request).getUserId());
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	@RequestMapping("/setItemTakeScoreLimit")
	public void setItemTakeScoreLimit(String itemId,String vdef1,String vdef2,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		CompetitionItemInfo item=competitionItemService.findById(itemId);
		item.setVdef1(vdef1);
		item.setVdef2(vdef2);
		competitionItemService.update(item);
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
