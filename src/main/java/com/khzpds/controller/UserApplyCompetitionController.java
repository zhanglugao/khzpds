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
import com.khzpds.service.UserCompletionItemApplyService;
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
	
	/***
	 * 跳转报名页  需要根据type判断是否存在正在运行中的活动下的已发布状态的比赛项目
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toApply")
	public ModelAndView toApply(String type,HttpServletRequest request,HttpServletResponse response){
		String dest=null;
		//查询已发布状态的活动下的对应type的比赛项目
		List<CompetitionItemInfo> items=competitionItemService.findPublishedCompetitionItem(type);
		if(items!=null&&items.size()>0){
			request.setAttribute("item", items.get(0));
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
	 */
	@RequestMapping("/userApplyCompetitionItem")
	public void userApplyCompetitionItem(UserCompletionItemApplyInfo applyInfo,HttpServletRequest request,HttpServletResponse response){
		System.out.println(applyInfo);
	}
}
