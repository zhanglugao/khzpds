package com.khzpds.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khzpds.base.BaseController;
import com.khzpds.service.UserVisitLogService;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.UserVisitLogInfo;

@RequestMapping("/userVisitLog")
@Controller
public class UserVisitLogController extends BaseController{
	
	@Autowired
	private UserVisitLogService userVisitLogService;
	
	@RequestMapping("/visitLog")
	public void visiLog(String ref,String visitType,HttpServletRequest  request,HttpServletResponse response){
		UserVisitLogInfo log=new UserVisitLogInfo();
		log.setId(UUIDUtil.getUUID());
		log.setReferSite(ref);
		log.setVisitType(visitType);
		log.setVisitTime(new Date());
		if(getCurrentSessionInfo(request).getUserId()!=null){
			log.setUserId(getCurrentSessionInfo(request).getUserId());
		}else{
			userVisitLogService.add(log);
		}
	}
}
