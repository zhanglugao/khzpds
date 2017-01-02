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
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.ItemOrgLimitService;
import com.khzpds.service.ManagerOrgService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.ItemOrgLimitInfo;
import com.khzpds.vo.ManagerOrgInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

@RequestMapping("/orgApprove")
@Controller
public class OrgApproveController extends BaseController{
	
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private ManagerOrgService managerOrgService;
	@Autowired
	private ItemOrgLimitService itemOrgLimitService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		return new ModelAndView(getRootPath(request)+"/manage/orgApprove/org_approve_list");
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
		request.setAttribute("activity", activity);
		request.setAttribute("items", items);
		return new ModelAndView(getRootPath(request)+"/manage/orgApprove/org_approve_item");
	}
	
	/***
	 * 初审
	 * @param result
	 * @param type
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/approve")
	public void approve(String result,String type,String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> data=new HashMap<String, Object>();
		String[] idArray=id.split(",");
		List<UserCompletionItemApplyInfo> list=new ArrayList<UserCompletionItemApplyInfo>();
		int limitNum=-1;
		boolean f=false;
		String itemId=null;
		for(String applyId:idArray){
			UserCompletionItemApplyInfo info=userCompetitionItemApplyService.findById(applyId);
			if(!f){
				itemId=info.getCompetitionItemId();
				ManagerOrgInfo findInfo=new ManagerOrgInfo();
				findInfo.setManagerId(getCurrentSessionInfo(request).getUserId());
				List<ManagerOrgInfo> infos=managerOrgService.findByParam(findInfo);
				if(infos.size()==1){
					ItemOrgLimitInfo limitFind=new ItemOrgLimitInfo();
					limitFind.setItemId(info.getCompetitionItemId());
					limitFind.setOrgId(infos.get(0).getOrgId());
					List<ItemOrgLimitInfo> limitInfos=itemOrgLimitService.findByParam(limitFind);
					if(limitInfos.size()>0){
						limitNum=Integer.parseInt(limitInfos.get(0).getLimitNum());
					}
				}
				f=true;
			}
			info.setApproveStatus(result);
			info.setApproveType(type);
			info.setApproveTime(new Date());
			info.setApproveUserId(getCurrentSessionInfo(request).getUserId());
			info.setApproveUserName(getCurrentSessionInfo(request).getUserName());
			list.add(info);
		}
		if(limitNum!=-1){
			//判断数目是否超限
			UserCompletionItemApplyInfo findInfo=new UserCompletionItemApplyInfo();
			findInfo.setApproveUserId(getCurrentSessionInfo(request).getUserId());
			findInfo.setCompetitionItemId(itemId);
			List<UserCompletionItemApplyInfo> list2=userCompetitionItemApplyService.findByParam(findInfo);
			if(list2.size()+list.size()>limitNum){
				data.put("status", "1");
				data.put("error_desc", "您已审核"+list2.size()+"幅作品，本次操作待审核"+list.size()+"幅作品，超出限额"+limitNum+"，操作无法继续");
				this.writeJson(response, data);
				return;
			}
		}
		userCompetitionItemApplyService.updateMuti(list);
		data.put("status", "0");
		this.writeJson(response, data);
	}
	
	/***
	 * 查询用户报名数据
	 * @param itemId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getApplyData")
	public void getApplyData(String itemId,String applyGroup,String applyYearGroup,String orgId,String userName,String realName,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		
		Map<String,String> searchMap=new HashMap<String, String>();
		
		searchMap.put("applyStatus", DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING);
		
		if(StringUtils.isNotBlank(itemId))searchMap.put("itemId", itemId);
		if(StringUtils.isNotBlank(applyGroup))searchMap.put("applyGroup", applyGroup);
		if(StringUtils.isNotBlank(applyYearGroup))searchMap.put("applyYearGroup", applyYearGroup);
		if(StringUtils.isBlank(orgId)){
			//查出用户所属组织机构
			ManagerOrgInfo findInfo=new ManagerOrgInfo();
			findInfo.setManagerId(getCurrentSessionInfo(request).getUserId());
			List<ManagerOrgInfo> infos=managerOrgService.findByParam(findInfo);
			if(infos.size()==1){
				searchMap.put("orgId", infos.get(0).getOrgId());
				//
			}
		}else{
			searchMap.put("orgId", orgId);
		}
		if(StringUtils.isNotBlank(userName))searchMap.put("userName", userName);
		if(StringUtils.isNotBlank(realName))searchMap.put("realName", realName);
		
		/*searchMap.put("orderField", " apply");
		searchMap.put("orderType", " desc");*/
		List<Map<String,String>> dataMap=userCompetitionItemApplyService.findBySearchMap(searchMap);
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
		result.put("applyGroupMap", applyGroupMap);
		result.put("applyYearGroupMap", applyYearGroupMap);
		this.writeJson(response, result);
	}
}
