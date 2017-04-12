package com.khzpds.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.UserCompletionItemApplyDao;
import com.khzpds.vo.UserCompletionItemApplyInfo;


@Service
public class UserCompletionItemApplyService extends IBaseService<UserCompletionItemApplyInfo> {
    private UserCompletionItemApplyDao userCompletionItemApplyDao;
    
    @Autowired  
    public void setUserCompletionItemApplyRepository(UserCompletionItemApplyDao repository) {  
        setRepository(repository);  
        userCompletionItemApplyDao=repository;
    }  
    
    //--CustomBegin***///
    public List<UserCompletionItemApplyInfo> findByParamSort(
			UserCompletionItemApplyInfo applyfind, String sort) {
		return userCompletionItemApplyDao.findByParamSort(applyfind,sort);
	} 
	public List<Map<String, String>> findBySearchMap(
			Map<String, String> searchMap) {
		return userCompletionItemApplyDao.findBySearchMap(searchMap);
	}
	public void updateMuti(List<UserCompletionItemApplyInfo> list) {
		// TODO Auto-generated method stub
		for(UserCompletionItemApplyInfo info:list){
			userCompletionItemApplyDao.update(info, null);
		}
	}
	
	public List<Map<String, Object>> findReportDataByActivityId(
			String id) {
		return userCompletionItemApplyDao.findReportDataByActivityId(id);
	}

	public String findMaxApplyNumber(String activityId, String competitionItemId, String itemType, String applyGroup) {
		return userCompletionItemApplyDao.findMaxApplyNumber(activityId,competitionItemId,itemType,applyGroup);
	}
	public List<Map<String, String>> findBySearchMapPage(
			PageParameter page) {
		return userCompletionItemApplyDao.findBySearchMapPage(page);
	}
	public List<UserCompletionItemApplyInfo> findTopVoteApplyInfo(Integer n) {
		return userCompletionItemApplyDao.findTopVoteApplyInfo(n);
	}

	public List<Map<String, String>> findBySearchMapScorePage(PageParameter page) {
		return userCompletionItemApplyDao.findBySearchMapScorePage(page);
	}
//--CustomEnd*****///


	

}

