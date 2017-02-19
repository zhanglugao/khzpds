package com.khzpds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.base.PageParameter;
import com.khzpds.service.ActivityInfoService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

/***
 * 学校批量报名
 * @author hasee
 *
 */
@Controller
@RequestMapping("/schoolBatchApply")
public class SchoolBatchApplyController extends BaseController{

	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private UserCompletionItemApplyService userCompletionItemApplyService;
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping("/toApplyList")
	public ModelAndView toApplyList(String id,HttpServletRequest request,HttpServletResponse response){
		ActivityInfoInfo activity=activityInfoService.findById(id);
		if(DictionaryConst.HUO_DONG_ZHUANG_TAI_YI_FA_BU.equals(activity.getStatus())){
			//已发布状态  可以编辑或者添加新的报名表
			request.setAttribute("ifCanAdd", "1");
		}
		return new ModelAndView(getRootPath(request)+"/manage/schoolBatchApply/school_batch_apply_list");
	}
	
	/****
	 * 学校批量报名数据获取
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		PageParameter page=this.getPageParameter2(request);
		Map<String,String> searchMap=new HashMap<String, String>();
		searchMap.put("activityId", id);
		searchMap.put("userId", getCurrentSessionInfo(request).getUserId());
		page.setSearch(searchMap);
		List<UserCompletionItemApplyInfo> applyInfos=userCompletionItemApplyService.findByParamForPage(page);
		Map<String,String> typeMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING);
		Map<String,String> statusMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI);
		for(UserCompletionItemApplyInfo apply:applyInfos){
			apply.setApplyStatus(statusMap.get(apply.getApplyStatus()));
			apply.setArtist(typeMap.get(apply.getCompetitionType()));
		}
		result.put("rows", applyInfos);
		result.put("total_count", page.getTotalCount());
		this.writeJson(response, result);
	}
}
