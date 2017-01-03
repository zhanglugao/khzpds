package com.khzpds.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.ContentCategoryService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.ItemOrgLimitService;
import com.khzpds.service.ManagerOrgService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.ContentCategoryInfo;
import com.khzpds.vo.ItemOrgLimitInfo;

@RequestMapping("/activityItemLimit")
@Controller
public class ActivityItemLimitController extends BaseController{
	
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private ContentCategoryService contentCategoryService;
	@Autowired
	private ManagerOrgService managerOrgService;
	@Autowired
	private ItemOrgLimitService itemOrgLimitService;
	
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
		CompetitionItemInfo item=competitionItemService.findById(id);
		request.setAttribute("item", item);
		//查询出所有的管理员以及设置好 限额信息
		ContentCategoryInfo parentCategory=contentCategoryService.getRootCategory(getCurrentSessionInfo(request).getUserName(),null);
		ContentCategoryInfo findInfo=new ContentCategoryInfo();
		findInfo.setParentId(parentCategory.getId());
		List<ContentCategoryInfo> orgList=contentCategoryService.findByParamSort(findInfo,null);
		
		ItemOrgLimitInfo itemOrgLimitFind=new ItemOrgLimitInfo();
		itemOrgLimitFind.setItemId(id);
		List<ItemOrgLimitInfo> infoList=itemOrgLimitService.findByParam(itemOrgLimitFind);
		for(ContentCategoryInfo org:orgList){
			for(ItemOrgLimitInfo orgLimitInfo:infoList){
				if((org.getId()+"").equals(orgLimitInfo.getOrgId())){
					org.setAdditional(orgLimitInfo.getLimitNum());
					break;
				}
			}
		}
		request.setAttribute("orgList", orgList);
		
		return new ModelAndView(getRootPath(request)+"/manage/activityItemLimit/activity_item_change_limit");
	}
	
	@RequestMapping("/changeLimit")
	public void changeLimit(String data,String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		System.out.println(data);
		JSONArray array=JSON.parseArray(data);
		List<ItemOrgLimitInfo> itemOrgLimitList=new ArrayList<ItemOrgLimitInfo>();
		if(array!=null){
			for(Object object:array){
				JSONObject obj=(JSONObject) object;
				String orgId=obj.get("id").toString();
				String limitNum=obj.get("value").toString();
				ItemOrgLimitInfo info=new ItemOrgLimitInfo();
				info.setCreateTime(new Date());
				info.setCreateUser(getCurrentSessionInfo(request).getUserId());
				info.setId(UUIDUtil.getUUID());
				info.setItemId(id);
				info.setLimitNum(limitNum);
				info.setOrgId(orgId);
				itemOrgLimitList.add(info);
			}
			itemOrgLimitService.addItemOrgListInfo(id,itemOrgLimitList);
		}else{
			result.put("status", "1");
			result.put("error_desc", "参数data错误，data值为:"+data);
			this.writeJson(response, result);
			return;
		}
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
