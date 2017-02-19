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

import com.alibaba.fastjson.JSON;
import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.base.PageParameter;
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

@Controller
@RequestMapping("/reviewResult")
public class ReviewResultController extends BaseController{
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	
	@RequestMapping("/index")
	public ModelAndView index(String type,HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		if(StringUtils.isNotBlank(type)){
			request.setAttribute("type", type);
		}
		return new ModelAndView(getRootPath(request)+"/manage/reviewResult/review_result_list");
	}
	
	/***
	 * 查看项目
	 * @param id
	 * @return
	 */
	@RequestMapping("/toShowItem")
	public ModelAndView toShowItem(String id,String type,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isNotBlank(type)){
			if("school".equals(type)){
				return new ModelAndView("redirect:/schoolBatchApply/toApplyList?id="+id);
			}else{
				return null;
			}
		}else{
			ActivityInfoInfo activity=activityInfoService.findById(id);
			
			CompetitionItemInfo findInfo=new CompetitionItemInfo();
			findInfo.setActivityId(id);
			List<CompetitionItemInfo> items=competitionItemService.findByParam(findInfo);
			request.setAttribute("activity", activity);
			request.setAttribute("items", items);
			if(StringUtils.isNotBlank(type)){
				request.setAttribute("type", type);
			}
			return new ModelAndView(getRootPath(request)+"/manage/reviewResult/review_result_item");
		}
	}
	
	/***
	 * 查询用户报名数据
	 * @param itemId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getApplyData")
	public void getApplyData(String applyStatus,String approveResult,String itemId,String applyGroup,String applyYearGroup,String orgId,String userName,String realName,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		
		PageParameter page=this.getPageParameter2(request);
		Map<String,String> searchMap=new HashMap<String, String>();
		if(StringUtils.isNotBlank(itemId))searchMap.put("itemId", itemId);
		if(StringUtils.isNotBlank(applyGroup))searchMap.put("applyGroup", applyGroup);
		if(StringUtils.isNotBlank(applyYearGroup))searchMap.put("applyYearGroup", applyYearGroup);
		if(StringUtils.isNotBlank(orgId))searchMap.put("orgId", orgId);
		if(StringUtils.isNotBlank(userName))searchMap.put("userName", userName);
		if(StringUtils.isNotBlank(realName))searchMap.put("realName", realName);
		if(StringUtils.isNotBlank(applyStatus))searchMap.put("applyStatus", applyStatus);
		if(StringUtils.isNotBlank(approveResult))searchMap.put("approveResult", approveResult);
		//searchMap.put("applyStatus", DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING);
		/*searchMap.put("orderField", " apply");
		searchMap.put("orderType", " desc");*/
		page.setOrderField(" apply.create_time");
		page.setOrderType(" desc");
		page.setSearch(searchMap);
		List<Map<String,String>> dataMap=userCompetitionItemApplyService.findBySearchMapPage(page);
		//for(Map<String,String> )
		//
		CompetitionItemInfo item=competitionItemService.findById(itemId);
		String applyGroupParentId="";
		String applyYearGroupParentId="";
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(item.getType())){
			applyGroupParentId=DictionaryConst.KE_HUAN_HUA_CAN_SAI_ZU;
			applyYearGroupParentId=DictionaryConst.KE_HUAN_HUA_NIAN_LING_ZU;
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(item.getType())){
			applyYearGroupParentId=DictionaryConst.KE_HUAN_WEI_SHI_PIN_NIAN_LING_ZU;
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(item.getType())){
			applyGroupParentId=DictionaryConst.KE_HUAN_XIAO_SHUO_CAN_SAI_ZU;
			applyYearGroupParentId=DictionaryConst.KE_HUAN_XIAO_SHUO_NIAN_LING_ZU;
		}
		Map<String,String> applyGroupMap=dictionaryService.getDicMapByParentId(applyGroupParentId);
		Map<String,String> applyYearGroupMap=dictionaryService.getDicMapByParentId(applyYearGroupParentId);
		
		result.put("datas",dataMap);
		result.put("total_count",page.getTotalCount());
		result.put("applyGroupMap", applyGroupMap);
		result.put("applyYearGroupMap", applyYearGroupMap);
		this.writeJson(response, result);
	}
}
