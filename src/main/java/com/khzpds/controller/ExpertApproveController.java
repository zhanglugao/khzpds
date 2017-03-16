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
import com.khzpds.service.RoleService;
import com.khzpds.service.UserApplyMarkingResultService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.service.UserInfoService;
import com.khzpds.service.UserRoleService;
import com.khzpds.util.DateUtil;
import com.khzpds.util.TransHtmlHelper;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.RoleInfo;
import com.khzpds.vo.UserApplyMarkingResultInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;
import com.khzpds.vo.UserInfoInfo;
import com.khzpds.vo.UserRoleInfo;

/***
 * 专家首轮审批管理
 * @author hasee
 *
 */
@Controller
@RequestMapping("/expertApprove")
public class ExpertApproveController extends BaseController{
	
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserApplyMarkingResultService userApplyMarkingResultService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> dicMap=dictionaryService.getDicMapByParentId(DictionaryConst.HUO_DONG_ZHUANG_TAI);
		request.setAttribute("status", JSON.toJSONString(dicMap));
		return new ModelAndView(getRootPath(request)+"/manage/expertApprove/expert_approve_list");
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
		//判断用户角色是否是管理员
		UserRoleInfo userRoleFind=new UserRoleInfo();
		userRoleFind.setUserId(this.getCurrentSessionInfo(request).getUserId());
		//List<UserRoleInfo> userRoles=userRoleService.findByParam(userRoleFind);
		boolean ifAdmin=false;
		/*if("admin".equals(this.getCurrentSessionInfo(request).getUserName())){
			ifAdmin=true;
		}
		if(!ifAdmin){
			for(UserRoleInfo userRoleInfo:userRoles){
				RoleInfo role=roleService.findById(userRoleInfo.getRoleId());
				if("科普处管理员".equals(role.getName())){
					ifAdmin=true;
				}
			}
		}*/
		request.setAttribute("ifAdmin", ifAdmin);
		return new ModelAndView(getRootPath(request)+"/manage/expertApprove/expert_approve_item");
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
/*		List<UserCompletionItemApplyInfo> list=new ArrayList<UserCompletionItemApplyInfo>();
*/		for(String applyId:idArray){
			UserCompletionItemApplyInfo info=userCompetitionItemApplyService.findById(applyId);
			info.setApproveStatus(result);
			info.setApproveType(type);
			info.setApproveTime(new Date());
			info.setApproveUserId(getCurrentSessionInfo(request).getUserId());
			info.setApproveUserName(getCurrentSessionInfo(request).getUserName());
			String number=null;
			if("1".equals(result)){
				if(StringUtils.isBlank(info.getVdef1())){
						if(StringUtils.isNotBlank(info.getCompetitionType())){
							//得到序号的最大值
							String maxNo=userCompetitionItemApplyService.findMaxApplyNumber(info.getActivityId(),info.getCompetitionItemId(),info.getCompetitionType(),info.getApplyGroup());
							if(StringUtils.isBlank(maxNo)){
								maxNo="0";
							}
							if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(info.getCompetitionType())){
								number=DateUtil.formatDate2String(new Date(), "yyyy")+"2";
								if(DictionaryConst.KE_HUAN_HUA_CAN_SAI_ZU_SHOU_HUI_ZU.equals(info.getApplyGroup())){
									number=number+"1";
								}else if(DictionaryConst.KE_HUAN_HUA_CAN_SAI_ZU_DIAN_NAO_HUI_TU_ZU.equals(info.getApplyGroup())){
									number=number+"2";
								}
							}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(info.getCompetitionType())){
								number=DateUtil.formatDate2String(new Date(), "yyyy")+"1";
								if(DictionaryConst.KE_HUAN_XIAO_SHUO_CAN_SAI_ZU_WEI_XING_XIAO_SHUO.equals(info.getApplyGroup())){
									number=number+"1";
								}else if(DictionaryConst.KE_HUAN_XIAO_SHUO_CAN_SAI_ZU_ZHONG_PIAN_XIAO_SHUO.equals(info.getApplyGroup())){
									number=number+"2";
								}
							}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(info.getCompetitionType())){
								number=DateUtil.formatDate2String(new Date(), "yyyy")+"3"+"1";
							}
							Integer num=Integer.parseInt(maxNo);
							num++;
							String numString=num+"";
							if(numString.length()==1){
								numString="00"+num;
							}else if(numString.length()==2){
								numString="0"+num;
							}
							number+=numString;
							info.setVdef1(number);
							info.setVdef2(number.substring(6, number.length()));
						}
				}
			}
			userCompetitionItemApplyService.update(info);
			//list.add(info);
		}
		//userCompetitionItemApplyService.updateMuti(list);
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
	public void getApplyData(String ifShowScore,String applyStatus,String approveResult,String itemId,String applyGroup,String applyYearGroup,String orgId,String userName,String realName,HttpServletRequest request,HttpServletResponse response){
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
		for(Map<String,String> map:dataMap	){
			map.put("ideaDesc", TransHtmlHelper.replaceStringToHtml(map.get("ideaDesc")));
			if(StringUtils.isNotBlank(ifShowScore)){
				//
				UserApplyMarkingResultInfo findInfo=new UserApplyMarkingResultInfo();
				findInfo.setItemId(itemId);
				findInfo.setApplyId(map.get("id").toString());
				List<UserApplyMarkingResultInfo> results=userApplyMarkingResultService.findByParam(findInfo);
				map.put("markingUser", "");
				map.put("markingTime", "");
				map.put("totalScore", "0");
				if(results!=null&&results.size()>0){
					UserInfoInfo user=userInfoService.findById( results.get(0).getMarkingUser());
					if(user!=null){
						map.put("markingUser",user.getUserName());
					}
					if(results.get(0).getMarkingTime()!=null){
						map.put("markingTime", DateUtil.formatDate2String(results.get(0).getMarkingTime(), "yyyy-MM-dd HH:mm:ss"));
					}
					Double totalScore=0d;
					for(UserApplyMarkingResultInfo result1:results){
						if(result1.getGetScore()!=null){
							totalScore+=result1.getGetScore();
						}
					}
					map.put("totalScore", totalScore.toString());
				}
			}
		}
		result.put("datas",dataMap);
		result.put("total_count",page.getTotalCount());
		result.put("applyGroupMap", applyGroupMap);
		result.put("applyYearGroupMap", applyYearGroupMap);
		this.writeJson(response, result);
	}
}
