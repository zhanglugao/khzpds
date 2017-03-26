package com.khzpds.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.khzpds.service.ActivityMarkingSetupService;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserApplyMarkingResultService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.ActivityMarkingSetupInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.UserApplyMarkingResultInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

/***
 * 专家复赛评分
 * @author hasee
 *
 */
@RequestMapping("/expertTakeScore")
@Controller
public class ExpertTakeScoreController extends BaseController {
	
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private UserApplyMarkingResultService userApplyMarkingResultService;
	@Autowired
	private ActivityMarkingSetupService activityMarkingSetupService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		return new ModelAndView(getRootPath(request)+"/manage/expertTakeScore/expert_takeScore_list");
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
		return new ModelAndView(getRootPath(request)+"/manage/expertTakeScore/expert_takeScore_item");
	}
	
	/***
	 * 获取打分详情
	 * @param id
	 */
	@RequestMapping("/getTakeScoreInfo")
	public ModelAndView getTakeScoreInfo(String id,String ifCanAdd,HttpServletRequest request,HttpServletResponse response){
		UserCompletionItemApplyInfo applyInfo=userCompetitionItemApplyService.findById(id);
		if(applyInfo==null){
			return null;
		}
		UserApplyMarkingResultInfo findInfo=new UserApplyMarkingResultInfo();
		findInfo.setApplyId(id);
		if("1".equals(ifCanAdd)){
			findInfo.setMarkingUser(this.getCurrentSessionInfo(request).getUserId());
		}
		List<UserApplyMarkingResultInfo> results=userApplyMarkingResultService.findByParamSort(findInfo);
		if(results.size()==0){
			//查询该计划下的打分项
			ActivityMarkingSetupInfo setUpFind=new ActivityMarkingSetupInfo();
			setUpFind.setActivityId(applyInfo.getActivityId());
			setUpFind.setItemType(applyInfo.getCompetitionType());
			List<ActivityMarkingSetupInfo> setUpInfos=activityMarkingSetupService.findByParamSort(setUpFind);
			if(setUpInfos.size()==0){
				setUpFind.setActivityId("default");
				setUpInfos=activityMarkingSetupService.findByParamSort(setUpFind);
			}
			for(ActivityMarkingSetupInfo setUp:setUpInfos){
				UserApplyMarkingResultInfo result=new UserApplyMarkingResultInfo();
				result.setActivityId(applyInfo.getActivityId());
				result.setApplyId(applyInfo.getId());
				result.setSetUpId(setUp.getId());
				result.setSetUpName(setUp.getName());
				result.setTotalScore(setUp.getScore());
				results.add(result);
			}
		}else{
			for(UserApplyMarkingResultInfo result:results){
				ActivityMarkingSetupInfo setUp=activityMarkingSetupService.findById(result.getVdef1());
				result.setSetUpId(setUp.getId());
				result.setSetUpName(setUp.getName());
				result.setTotalScore(setUp.getScore());
			}
		}
		if("1".equals(ifCanAdd)){
			request.setAttribute("ifCanAdd", "1");
		}
		request.setAttribute("results", results);
		return new ModelAndView(getRootPath(request)+"/manage/expertTakeScore/expert_takeScore_detail");
	}
	
	/***
	 * 打分
	 */
	@RequestMapping("/takeScore")
	public void takeScore(String datas,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String, Object>();
		String[] dataArr=datas.split(",");
		List<UserApplyMarkingResultInfo> results=new ArrayList<UserApplyMarkingResultInfo>();
		int num=0;
		double totalScore=0d;
		String itemId=null;
		UserCompletionItemApplyInfo applyInfo=null;
		for(String data:dataArr){
			num++;
			String[] arr=data.split("#");
			Double score=Double.parseDouble(arr[1]);
			String[] ids=arr[0].split("_");
			String applyId=ids[0];
			String setUpId=ids[1];
			if(applyInfo==null){
				applyInfo=userCompetitionItemApplyService.findById(applyId);
			}
			UserApplyMarkingResultInfo resultInfo=new UserApplyMarkingResultInfo();
			resultInfo.setActivityId(applyInfo.getActivityId());
			resultInfo.setApplyId(applyId);
			resultInfo.setGetScore(score);
			resultInfo.setId(UUIDUtil.getUUID());
			resultInfo.setItemId(applyInfo.getCompetitionItemId());
			resultInfo.setItemType(applyInfo.getCompetitionType());
			resultInfo.setMarkingTime(new Date());
			resultInfo.setMarkingUser(this.getCurrentSessionInfo(request).getUserId());
			resultInfo.setVdef1(setUpId);
			resultInfo.setVdef2(num+"");
			resultInfo.setVdef3(this.getCurrentSessionInfo(request).getUserName());
			results.add(resultInfo);
			totalScore+=resultInfo.getGetScore();
			itemId=applyInfo.getCompetitionItemId();
		}
		CompetitionItemInfo item=competitionItemService.findById(itemId);
		if(item!=null&&StringUtils.isNotBlank(item.getVdef2())){
			if(totalScore<Integer.parseInt(item.getVdef2())){
				applyInfo.setVdef5("-1");
				userCompetitionItemApplyService.update(applyInfo);
			}
		}
		userApplyMarkingResultService.addList(results);
		result.put("status", "0");
		this.writeJson(response, result);
	}
	
	/***
	 * 校验能否打分 标注有2  
	 * 1：是否达到限制打分人数
	 * 2：本用户是否已打过分
	 * @param id
	 * @param response
	 */
	@RequestMapping("/validateTake")
	public void validateTake(String id,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String, Object>();
		UserCompletionItemApplyInfo apply=userCompetitionItemApplyService.findById(id);
		CompetitionItemInfo item=competitionItemService.findById(apply.getCompetitionItemId());
		UserApplyMarkingResultInfo findInfo=new UserApplyMarkingResultInfo();
		findInfo.setApplyId(apply.getId());
		List<UserApplyMarkingResultInfo> list=userApplyMarkingResultService.findByParam(findInfo);
		Set<String> set=new HashSet<String>();
		for(UserApplyMarkingResultInfo resultInfo:list){
			set.add(resultInfo.getMarkingUser());
		}
		if(StringUtils.isNotBlank(item.getVdef1())){
			if(Integer.parseInt(item.getVdef1())<=set.size()){
				result.put("status", "1");
				result.put("error_desc", "该作品限制打分专家人数为"+item.getVdef1()+"，已不可再打分");
				this.writeJson(response, result);
				return;
			}
		}
		if(set.contains(this.getCurrentSessionInfo(request).getUserId())){
			result.put("status", "1");
			result.put("error_desc", "您也对该作品打分");
			this.writeJson(response, result);
			return;
		}
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
