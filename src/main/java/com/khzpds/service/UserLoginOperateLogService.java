package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserLoginOperateLogDao;
import com.khzpds.vo.UserLoginOperateLogInfo;
@Service
public class UserLoginOperateLogService extends IBaseService<UserLoginOperateLogInfo> {
    private UserLoginOperateLogDao userLoginOperateLogDao;
    
    @Autowired  
    public void setUserLoginOperateLogRepository(UserLoginOperateLogDao repository) {  
        setRepository(repository);  
        userLoginOperateLogDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

