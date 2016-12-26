package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserVisitLogDao;
import com.khzpds.vo.UserVisitLogInfo;
@Service
public class UserVisitLogService extends IBaseService<UserVisitLogInfo> {
    private UserVisitLogDao userLoginLogDao;
    
    @Autowired  
    public void setUserLoginLogRepository(UserVisitLogDao repository) {  
        setRepository(repository);  
        userLoginLogDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

