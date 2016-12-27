package com.khzpds.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
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
//--CustomEnd*****///
}

