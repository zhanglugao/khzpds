package com.khzpds.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.vo.CompetitionItemInfo;

@RequestMapping("/vote")
@Controller
public class VoteController extends BaseController{
	
	@Autowired
	private CompetitionItemService competitionItemService;
	
	@RequestMapping("/votePage")
	public ModelAndView voteDrawPage(String itemType,HttpServletRequest request,HttpServletResponse response){
		ModelAndView model=null;
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(itemType)){
			model=new ModelAndView("/open/vote/vote-draw");
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(itemType)){
			model=new ModelAndView("/open/vote/vote-novel");
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(itemType)){
			model=new ModelAndView("/open/vote/vote-video");
		}
		//查询正在运行中的活动下正在复赛评分中的比赛项目
		String itemStatus=DictionaryConst.BI_SAI_XIANG_MU_ZHUANG_TAI_YI_LUN_PING_SHEN_JIE_SHU;
		List<CompetitionItemInfo> list=competitionItemService.findCompetitionItemByTypeStatus(itemType,itemStatus);
		if(list.size()>0){
			request.setAttribute("itemId", list.get(0).getId());
		}
		return model;
	}
	
	@RequestMapping("/getVoteData")
	public void getVoteData(){
		
	}
}
