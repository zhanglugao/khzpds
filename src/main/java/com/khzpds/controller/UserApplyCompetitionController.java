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
		if(StringUtils.isBlank(id)){
			List<CompetitionItemInfo> items=competitionItemService.findPublishedCompetitionItem(type);
			if(items!=null&&items.size()>0){
				request.setAttribute("item", items.get(0));
			}
		}else{
			UserCompletionItemApplyInfo applyInfo=userCompetitionItemApplyService.findById(id);
			if(applyInfo!=null){
				Date birthday=applyInfo.getBirthday();
				if(birthday!=null){
					request.setAttribute("birthday", DateUtil.formatDate2String(birthday, "yyyyMM"));
				}
				type=applyInfo.getCompetitionType();
				request.setAttribute("applyInfo", applyInfo);
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
		if(applyInfo.getId()==null){
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
