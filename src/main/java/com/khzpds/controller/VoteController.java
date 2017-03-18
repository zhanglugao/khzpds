package com.khzpds.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.base.SessionInfo;
import com.khzpds.base.SystemConfig;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.UserApplyVoteService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.UserApplyVoteInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

@RequestMapping("/vote")
@Controller
public class VoteController extends BaseController{
	
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompletionItemService;
	@Autowired
	private UserApplyVoteService userApplyVoteService;
	
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
			request.setAttribute("itemType", itemType);
		}
		request.setAttribute("lookdir", SystemConfig.getLookDir());
		return model;
	}
	
	/***
	 * 查询可投票的报名数据
	 * @param applyGroup
	 * @param applyYearGroup
	 * @param itemId
	 * @param itemType
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getVoteData")
	public void getVoteData(String applyGroup,String applyYearGroup,String itemId,String itemType,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		UserCompletionItemApplyInfo findInfo=new UserCompletionItemApplyInfo();
		if(StringUtils.isNotBlank(applyGroup)){
			findInfo.setApplyGroup(applyGroup);
		}
		if(StringUtils.isNotBlank(applyYearGroup)){
			findInfo.setApplyYearGroup(applyYearGroup);
		}
		if(StringUtils.isNotBlank(itemId)){
			findInfo.setCompetitionItemId(itemId);
		}
		if(StringUtils.isNotBlank(itemType)){
			findInfo.setCompetitionType(itemType);
		}
		findInfo.setApproveStatus("1");
		List<UserCompletionItemApplyInfo> list=userCompletionItemService.findByParam(findInfo);
		for(UserCompletionItemApplyInfo applyInfo:list){
			//得到票数
			UserApplyVoteInfo voteFind=new UserApplyVoteInfo();
			voteFind.setApplyId(applyInfo.getId());
			int count=userApplyVoteService.findByParamCount(voteFind);
			applyInfo.setVoteNum(count);
			if(applyInfo.getFilePath()!=null){
				applyInfo.setFilePath(applyInfo.getFilePath().replace("\\", "/"));
			}
		}
		result.put("rows", list);
		this.writeJson(response, result);
	}
	
	@RequestMapping("/vote")
	public void vote(String applyId,String openId,String applyGroup,String applyYearGroup,HttpServletRequest request,HttpServletResponse response){
		//投票的规则 一个ip或一个openid或一个userId 每一组只能投一票
		Map<String,Object> result=new HashMap<String, Object>();
		String ip=getIpAddr(request);
		SessionInfo session=getCurrentSessionInfo(request);
		String userId=null;
		if(session!=null){
			userId=session.getUserId();
		}
		UserApplyVoteInfo voteFind=new UserApplyVoteInfo();
		if(StringUtils.isNotBlank(openId))voteFind.setOpenId(openId);
		if(StringUtils.isNotBlank(ip))voteFind.setIp(ip);
		if(StringUtils.isNotBlank(userId))voteFind.setUserId(userId);
		if(StringUtils.isNotBlank(applyGroup))voteFind.setVdef1(applyGroup);
		if(StringUtils.isNotBlank(applyYearGroup))voteFind.setVdef2(applyYearGroup);
		List<UserApplyVoteInfo> voteList=userApplyVoteService.findByParamOr(voteFind);
		if(voteList.size()>0){
			result.put("status", "1");
			result.put("error_desc", "该组您也投过票，无法再次投票");
			this.writeJson(response, result);
			return;
		}
		UserApplyVoteInfo voteInfo=new UserApplyVoteInfo();
		UserCompletionItemApplyInfo applyInfo=userCompletionItemService.findById(applyId);
		if(applyInfo==null){
			result.put("status", "1");
			result.put("error_desc", "要投票的作品已不存在");
			this.writeJson(response, result);
			return;
		}
		voteInfo.setActivityId(applyInfo.getActivityId());
		voteInfo.setApplyId(applyId);
		voteInfo.setId(UUIDUtil.getUUID());
		voteInfo.setIp(ip);
		voteInfo.setItemId(applyInfo.getCompetitionItemId());
		voteInfo.setOpenId(openId);
		voteInfo.setUserId(userId);
		voteInfo.setVoteTime(new Date());
		voteInfo.setVdef1(applyInfo.getApplyGroup());
		voteInfo.setVdef2(applyInfo.getApplyYearGroup());
		userApplyVoteService.add(voteInfo);
		result.put("status", "0");
		this.writeJson(response, result);
		
	}
	
	public String getIpAddr(HttpServletRequest request) {  
       String ip = request.getHeader("x-forwarded-for");  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getRemoteAddr();  
       }  
       return ip;  
   }  
}
