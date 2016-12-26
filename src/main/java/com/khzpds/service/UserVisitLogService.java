package com.khzpds.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserVisitLogDao;
import com.khzpds.vo.UserVisitLogInfo;
@Service
public class UserVisitLogService extends IBaseService<UserVisitLogInfo> {
    private UserVisitLogDao userVisitLogDao;
    
    @Autowired  
    public void setUserLoginLogRepository(UserVisitLogDao repository) {  
        setRepository(repository);  
        userVisitLogDao=repository;
    }

	
    
    //--CustomBegin***///
    public List<UserVisitLogInfo> findBySearchMap(Map<String, String> searchMap) {
		return userVisitLogDao.findBySearchMap(searchMap);
	}  
    public int findVisitNumBySearchMap(Map<String, String> searchMap) {
		return userVisitLogDao.findVisitNumBySearchMap(searchMap);
	}
	public List<Map<String, String>> findRefNumBySearchMap(
			Map<String, String> searchMap) {
		return userVisitLogDao.findRefNumBySearchMap(searchMap);
	}
//--CustomEnd*****///






}

