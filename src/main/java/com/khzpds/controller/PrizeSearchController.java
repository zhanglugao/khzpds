package com.khzpds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khzpds.base.BaseController;
import com.khzpds.base.PageParameter;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.vo.UserApplyVoteInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

@Controller
@RequestMapping("/prizeSearch")
public class PrizeSearchController extends BaseController{
	
	@Autowired
	private UserCompletionItemApplyService userCompletionItemApplyService;
	
	@RequestMapping("/getData")
	public void getData(String itemId,String itemType,String applyGroup,String applyYearGroup,String level,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		Map<String,String> search=new HashMap<String, String>();
		if(StringUtils.isNotBlank(applyGroup)){
			search.put("applyGroup", applyGroup);
		}
		if(StringUtils.isNotBlank(applyYearGroup)){
			search.put("applyYearGroup", applyYearGroup);
		}
		if(StringUtils.isNotBlank(itemId)){
			search.put("competitionItemId", itemId);
		}
		if(StringUtils.isNotBlank(itemType)){
			search.put("competitionType", itemType);
		}
		if(StringUtils.isNotBlank(level)){
			search.put("vdef7", level);
		}
		search.put("vdef6", "1");//获奖
		PageParameter page=this.getPageParameter2(request);
		page.setSearch(search);
		page.setOrderField(" vdef1");
		page.setOrderType(" asc");
		List<UserCompletionItemApplyInfo> list=userCompletionItemApplyService.findByParamForPage(page);
		for(UserCompletionItemApplyInfo applyInfo:list){
			//得到票数
			if(applyInfo.getFilePath()!=null){
				applyInfo.setFilePath(applyInfo.getFilePath().replace("\\", "/"));
			}
		}
		result.put("rows", list);
		this.writeJson(response, result);
	}
}
