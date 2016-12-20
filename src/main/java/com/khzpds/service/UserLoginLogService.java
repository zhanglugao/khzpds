package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserLoginLogDao;
import com.khzpds.vo.UserLoginLogInfo;
@Service
public class UserLoginLogService extends IBaseService<UserLoginLogInfo> {
    private UserLoginLogDao userLoginLogDao;
    
    @Autowired  
    public void setUserLoginLogRepository(UserLoginLogDao repository) {  
        setRepository(repository);  
        userLoginLogDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

