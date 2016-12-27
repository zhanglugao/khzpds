package com.khzpds.controller;

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

import com.khzpds.base.BaseController;
import com.khzpds.service.UserVisitLogService;
import com.khzpds.util.DateUtil;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.UserVisitLogInfo;

@RequestMapping("/userVisitLog")
@Controller
public class UserVisitLogController extends BaseController{
	
	@Autowired
	private UserVisitLogService userVisitLogService;
	
	@RequestMapping("/visitLog")
	public void visiLog(String ref,String visitType,HttpServletRequest  request,HttpServletResponse response){
		if(ref==null)ref="";
		if(visitType==null)visitType="0";
		UserVisitLogInfo log=new UserVisitLogInfo();
		log.setId(UUIDUtil.getUUID());
		log.setReferSite(ref);
		log.setVisitType(visitType);
		log.setVisitTime(new Date());
		if(getCurrentSessionInfo(request)!=null&&getCurrentSessionInfo(request).getUserId()!=null){
			log.setUserId(getCurrentSessionInfo(request).getUserId());
		}else{
			userVisitLogService.add(log);
		}
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/manage/userVisitLog/user_visit_log_index");
	}
	
	@RequestMapping("/getData")
	public void getData(String num,HttpServletRequest request,HttpServletResponse response){
		int day=Integer.parseInt(num);
		Map<String,Object> result=new HashMap<String, Object>();
		Date now=new Date();
		String endTime=DateUtil.formatDate2String(now);
		String startTime=DateUtil.formatDate2String(DateUtil.addDate(now, day));
		
		Map<String,String> searchMap=new HashMap<String, String>();
		searchMap.put("startTime", startTime);
		searchMap.put("endTime", endTime);
		
		int visitNum=userVisitLogService.findVisitNumBySearchMap(searchMap);
		result.put("visitNum", visitNum);
		
		List<Map<String,String>> refMap=userVisitLogService.findRefNumBySearchMap(searchMap);
		
		result.put("refResult", refMap);
		
		searchMap.put("visitType", "0");
		int pcNum=userVisitLogService.findVisitNumBySearchMap(searchMap);
		result.put("pcNum", pcNum);
		
		this.writeJson(response, result);	
		
	}
	
	public static void main(String[] args) {
		Date now=new Date();
		Date d=DateUtil.addDate(now, -7);
		System.out.println(DateUtil.formatDate2String(d));
	}
}
