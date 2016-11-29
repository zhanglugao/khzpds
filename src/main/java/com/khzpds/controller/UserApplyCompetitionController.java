package com.khzpds.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.util.DateUtil;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

/***
 * 用户报名
 * @author zhanglugao 
 *
 */
@RequestMapping("/userApply")
@Controller
public class UserApplyCompetitionController extends BaseController{
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private DictionaryService dictionaryService;
	
	/***
	 * 撤销报名
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelApply")
	public void cancelApply(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		UserCompletionItemApplyInfo apply=userCompetitionItemApplyService.findById(id);
		if(apply==null||!DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING.equals(apply.getApplyStatus())){
			result.put("status", "1");
			result.put("error_desc", "只有已报名状态的报名记录可以撤销");
			this.writeJson(response, result);
			return;
		}
		CompetitionItemInfo item=competitionItemService.findById(apply.getCompetitionItemId());
		if(item==null||new Date().after(item.getUserApplyEndtime())){
			result.put("status", "1");
			result.put("error_desc", "报名已经截至，无法撤销");
			this.writeJson(response, result);
			return;
		}
		apply.setApplyStatus(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_QU_XIAO);
		userCompetitionItemApplyService.update(apply);
		result.put("status", "0");
		this.writeJson(response, result);
	}
	/**
	 * 查询用户报名记录列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		UserCompletionItemApplyInfo applyfind=new UserCompletionItemApplyInfo();
		applyfind.setUserId(getCurrentSessionInfo(request).getUserId());
		List<UserCompletionItemApplyInfo> applys=userCompetitionItemApplyService.findByParamSort(applyfind," create_time desc");
		Map<String,String> typeMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING);
		Map<String,String> statusMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI);
		for(UserCompletionItemApplyInfo apply:applys){
			apply.setCompetitionType(typeMap.get(apply.getCompetitionType()));
			apply.setApplyStatus(statusMap.get(apply.getApplyStatus()));
		}
		result.put("applyList", applys);
		this.writeJson(response, result);
	}
	
	/***
	 * 跳转报名页  需要根据type判断是否存在正在运行中的活动下的已发布状态的比赛项目
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toApply")
	public ModelAndView toApply(String type,String id,HttpServletRequest request,HttpServletResponse response){
		String dest=null;
		//查询已发布状态的活动下的对应type的比赛项目
		UserCompletionItemApplyInfo applyInfo=null;
		CompetitionItemInfo item=null;
		if(StringUtils.isBlank(id)){
			List<CompetitionItemInfo> items=competitionItemService.findPublishedCompetitionItem(type);
			if(items!=null&&items.size()>0){
				item=items.get(0);
				//判断用户是否已经报名
				UserCompletionItemApplyInfo applyFind=new UserCompletionItemApplyInfo();
				applyFind.setUserId(getCurrentSessionInfo(request).getUserId());
				applyFind.setCompetitionItemId(items.get(0).getId());
				List<UserCompletionItemApplyInfo> applys=userCompetitionItemApplyService.findByParam(applyFind);
				if(applys!=null&&applys.size()>0){
					applyInfo=applys.get(0);
				}else{
					request.setAttribute("item", items.get(0));
				}
			}
		}else{
			applyInfo=userCompetitionItemApplyService.findById(id);
			type=applyInfo.getCompetitionType();
		}
		if(applyInfo!=null){
			Date birthday=applyInfo.getBirthday();
			if(birthday!=null){
				request.setAttribute("birthday", DateUtil.formatDate2String(birthday, "yyyyMM"));
			}
			type=applyInfo.getCompetitionType();
			request.setAttribute("applyInfo", applyInfo);
			//状态=已报名 或者 时间>报名截止时间  不可修改
			if(item==null){
				item=competitionItemService.findById(applyInfo.getCompetitionItemId());
			}
			if(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING.equals(applyInfo.getApplyStatus())||new Date().after(item.getUserApplyEndtime())){
				request.setAttribute("ifReadonly", true);
			}
		}
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(type)){
			dest="novel-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
			dest="draw-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
			dest="video-sign";
		}
		request.setAttribute("type", type);
		return new ModelAndView(getRootPath(request)+"/open/competition/"+dest);
	}
	
	/***
	 * 比赛项目报名
	 * @param applyInfo
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/userApplyCompetitionItem")
	public void userApplyCompetitionItem(UserCompletionItemApplyInfo applyInfo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> result=new HashMap<String, Object>();
		applyInfo.setApplyStatus(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING);
		applyInfo.setUserName(getCurrentSessionInfo(request).getUserName());
		applyInfo.setUserId(getCurrentSessionInfo(request).getUserId());
		String birthday1=request.getParameter("birthday1");
		Date date=DateUtil.getDate(birthday1, "yyyyMM");
		
		String filePathHidden=request.getParameter("filePathHidden");
		String fileNameHidden=request.getParameter("fileNameHidden");
		applyInfo.setFilePath(URLDecoder.decode(filePathHidden, "utf-8"));
		applyInfo.setFileName(fileNameHidden);
		
		applyInfo.setBirthday(date);
		if(StringUtils.isBlank(applyInfo.getId())){
			applyInfo.setId(UUIDUtil.getUUID());
			applyInfo.setCreateTime(new Date());
			userCompetitionItemApplyService.add(applyInfo);
		}else{
			userCompetitionItemApplyService.update(applyInfo);
		}
		result.put("status", "0");
		this.writeJson(response, result);
	}
}
